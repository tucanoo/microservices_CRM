package com.tucanoo.crm.core.user.security

import org.springframework.context.annotation.Bean
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer
import org.springframework.stereotype.Component

@Component
class JwtCustomiser {
    @Bean
    fun jwtCustomizer(): OAuth2TokenCustomizer<JwtEncodingContext> {
        return OAuth2TokenCustomizer<JwtEncodingContext> { context: JwtEncodingContext ->
            if (context.tokenType.value == OidcParameterNames.ID_TOKEN) {
                val principal =
                    context.getPrincipal<Authentication>()
                context.claims.claim("role", principal.authorities.first().authority)
            }
        }
    }
}