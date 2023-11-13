package com.tucanoo.crm.core.user.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.util.StringUtils
import org.springframework.web.util.UriComponentsBuilder

/* The default behaviour of spring auth' server is to maintain the authenticated user details in session.
   This is not desirable, as if we want to login as the different users, we need to invalidate the session first, else the authorization server would not show the login page and immediately redirect back to the client app.
 */
class CustomAuthHandler : AuthenticationSuccessHandler {

    private val redirectStrategy = DefaultRedirectStrategy()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        if (authentication is OAuth2AuthorizationCodeRequestAuthenticationToken) {
            var result = authentication;
            var code = result.authorizationCode?.tokenValue
            var builder = UriComponentsBuilder
                .fromUriString(result.redirectUri.toString())
                .queryParam(OAuth2ParameterNames.CODE, code);
            if (StringUtils.hasText(result.state)) {
                builder.queryParam(OAuth2ParameterNames.STATE, result.state);
            }

            // invalidate the session, so any subsequent login goes through the login flow
            val oldSession : HttpSession?  = request.getSession(false);
            oldSession?.invalidate();

            redirectStrategy.sendRedirect(request, response, builder.toUriString());
        }
    }
}
