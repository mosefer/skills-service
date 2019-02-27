package skills.service.auth

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.context.annotation.Configuration
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource

import javax.servlet.http.HttpServletRequest

@Component
@Configuration('securityConfig')
@ConfigurationProperties(prefix = "skills.authorization")
@Slf4j
class SecurityConfiguration {

    @Value('${skills.authorization.authMode:#{T(skills.service.auth.AuthMode).DEFAULT_AUTH_MODE}}')
    AuthMode authMode

    @Component
    static class PortalWebSecurityHelper {
        HttpSecurity configureHttpSecurity(HttpSecurity http) {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/favicon.ico", "/icons/**", "/static/**", "/error", "/oauth/**", "/login*", "/performLogin", "/createAccount", "/app/userInfo", "/app/users/validExistingUserId/*", "/app/oAuthProviders", "index.html").permitAll()
                    .antMatchers('/admin/**').hasRole('PROJECT_ADMIN')
                    .anyRequest().authenticated()

//            http.antMatcher("/app/**").cors().configurationSource(new CorsConfigurationSource() {
//                @Override
//                CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                    new CorsConfiguration(
//                            allowCredentials: true,
//                            allowedHeaders: [CorsConfiguration.ALL],
//                            allowedMethods: [CorsConfiguration.ALL],
//                            allowedOrigins: [CorsConfiguration.ALL],
//                    )
//                }
//            })
            return http
        }
    }

    static class PkiAuth implements Condition {
        @Override
        boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            AuthMode authMode = AuthMode.getFromContext(context)
            return authMode == AuthMode.PKI
        }
    }

    static class FormAuth implements Condition {
        @Override
        boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            AuthMode authMode = AuthMode.getFromContext(context)
            return authMode == AuthMode.FORM
        }
    }
}
