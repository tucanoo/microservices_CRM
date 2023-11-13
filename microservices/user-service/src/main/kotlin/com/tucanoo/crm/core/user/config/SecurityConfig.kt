package com.tucanoo.crm.core.user.config

//import com.tucanoo.crm.security.CustomUserDetailsService

import com.tucanoo.crm.core.user.data.UserRepository
import com.tucanoo.crm.core.user.security.CustomAuthHandler
import com.tucanoo.crm.core.user.services.CustomUserDetailsService
import com.tucanoo.crm.core.user.security.SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME_DAYS
import com.tucanoo.crm.core.user.security.SecurityConstants.TOKEN_EXPIRATION_TIME_DAYS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.handler.HandlerMappingIntrospector
import java.time.Duration
import java.util.*


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("\${REDIRECT_URL:http://localhost}")
    lateinit var redirectUrl: String

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var jwtDecoder: JwtDecoder

    @Autowired
    lateinit var customUserDetailsService: CustomUserDetailsService

    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter {
        return AuthTokenFilter(customUserDetailsService, jwtDecoder)
    }

    // security config for the authorization server endpoints
    @Bean
    @Order(1)
    @Throws(java.lang.Exception::class)
    fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)

        http
            .cors(Customizer.withDefaults())
            .csrf { obj: CsrfConfigurer<HttpSecurity> ->
                obj
                    .disable()
            }
            .exceptionHandling { exceptions: ExceptionHandlingConfigurer<HttpSecurity?> ->
                exceptions
                    .authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
            }
            .oauth2ResourceServer { resourceServer ->
                resourceServer
                    .jwt(Customizer.withDefaults())
            }
            .getConfigurer(OAuth2AuthorizationServerConfigurer::class.java)
            .authorizationEndpoint { authorizationEndpoint ->
                authorizationEndpoint
                    .authorizationResponseHandler(CustomAuthHandler()) // use our custom handler
            }
            .oidc(Customizer.withDefaults())

        return http.build()
    }

    // security config for the web endpoints (form based login)
    // note MVC pattern is explicitly used as we're using H2 database which causes an issue determining which type of request matcher to use
    @Bean
    @Order(2)
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity, mvc: MvcRequestMatcher.Builder): SecurityFilterChain {
        http
            .cors(Customizer.withDefaults())
            .csrf { obj: CsrfConfigurer<HttpSecurity> ->
                obj
                    .disable()
            }
            .formLogin { form: FormLoginConfigurer<HttpSecurity?> ->
                form
                    .loginPage("/login")
                    .permitAll()
            }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(mvc.pattern("/login")).permitAll()
                    .requestMatchers(mvc.pattern("/actuator/**")).permitAll()
                    .requestMatchers(mvc.pattern("/error")).permitAll()
                    .requestMatchers(mvc.pattern("/oauth2/**")).permitAll()
                    .requestMatchers(mvc.pattern("/user/**")).permitAll()   // this filter chain should ignore the calls to the user endpoints
                    .anyRequest().authenticated()
            }

        return http.build()
    }


    @Bean
    fun mvc(introspector: HandlerMappingIntrospector?): MvcRequestMatcher.Builder? {
        return MvcRequestMatcher.Builder(introspector)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun registeredClientRepository(): RegisteredClientRepository {
        val oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("frontend")
            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri(redirectUrl)
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .clientSettings(
                ClientSettings.builder()
                    .requireAuthorizationConsent(false)
                    .requireProofKey(true)
                    .build()
            )
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenTimeToLive(Duration.ofDays(TOKEN_EXPIRATION_TIME_DAYS))
                    .refreshTokenTimeToLive(Duration.ofDays(REFRESH_TOKEN_EXPIRATION_TIME_DAYS))
                    .build()
            )
            .build()
        return InMemoryRegisteredClientRepository(oidcClient)
    }


    @Bean
    fun userDetailsService(): UserDetailsService? {
        return CustomUserDetailsService(userRepository)
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider? {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService())
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }

    // Limit the CORS to running under default profile for testing locally.
    @Bean
    @Profile("default")
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings {
        return AuthorizationServerSettings.builder().issuer("http://user-service:7002").build()
    }
}