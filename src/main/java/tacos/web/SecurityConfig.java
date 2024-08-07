package tacos.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.csrf.*;
import org.springframework.util.StringUtils;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;
import tacos.data.UserRepository;


import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;
//import tacos.model.User;

@Configuration
@EnableMethodSecurity
//@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            tacos.model.User user = userRepo.findByUsername(username);

            if (user != null) {
                return user; // Return user details
            }

            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        // set the name of the attribute the CsrfToken will be populated on
        //requestHandler.setCsrfRequestAttributeName(null);
     return http
             .csrf(AbstractHttpConfigurer::disable
                    // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    // .csrfTokenRequestHandler(new SpaServerCsrfTokenRequestHandler())
                     //.ignoringRequestMatchers("/h2-console/**")  // Disable CSRF for H2 console
             )
              .authorizeHttpRequests(
                      authorizedHttp -> {
                          authorizedHttp.requestMatchers("/favicon.svg").permitAll();
                          authorizedHttp.requestMatchers("/css/*").permitAll();
                          authorizedHttp.requestMatchers("/error").permitAll();
                          authorizedHttp.requestMatchers("/h2-console/**").permitAll();
                          authorizedHttp.requestMatchers("/design", "/order").hasRole("ADMIN");
                          //authorizedHttp.requestMatchers("/admin/**").hasRole("ADMIN");
                          authorizedHttp.requestMatchers("/api/ingredients").hasRole("ADMIN");
                          authorizedHttp.requestMatchers(HttpMethod.POST, "/api/ingredients").hasAuthority("SCOPE_writeIngredients");
                          authorizedHttp.requestMatchers(HttpMethod.DELETE, "/api/ingredients").hasAuthority("SCOPE_deleteIngredients");
                          authorizedHttp.requestMatchers("/", "/**").permitAll();
                          //authorizedHttp.requestMatchers("/oauth2/authorize-client", "/error/**");
                          authorizedHttp.anyRequest().authenticated();
                      }

                      )
//              .formLogin(l ->
//                               l.loginPage("/login")
//                               .loginProcessingUrl("/authenticate")
//                               .defaultSuccessUrl("/design"))
              .logout(l -> l.logoutSuccessUrl("/"))
              .oauth2Login(auth ->
                                 auth
                                         .loginPage("/oauth2/authorization/google")
                                         .defaultSuccessUrl("/design", true)
                                         .userInfoEndpoint(userInfoEndpoint ->
                                                userInfoEndpoint.oidcUserService(this.oidcUserService())
                                         )
              )
              .addFilterBefore(new ProhibitFilter(), AuthorizationFilter.class)
              .addFilterBefore(new RobotAuthenticationFilter(), AuthorizationFilter.class)
//              .oauth2ResourceServer(oauth2 ->
//                     oauth2
//                             .jwt(withDefaults())
//             )
              //.authenticationProvider(new MonyAuthenticationProvider())
              .build();
    }
    @Bean
    public OidcUserService oidcUserService() {
        OidcUserService delegate = new OidcUserService();
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) {
                OidcUser oidcUser = delegate.loadUser(userRequest);
                Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // Assign default role
                return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            }
        };
    }
    /*static final class SpaServerCsrfTokenRequestHandler implements CsrfTokenRequestHandler {
        private final XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
            // Always use XorCsrfTokenRequestAttributeHandler to provide BREACH protection of the CsrfToken when it is rendered in the response body.
            delegate.handle(request, response, csrfToken);
        }

        @Override
        public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
            String headerValue = request.getHeader(csrfToken.getHeaderName());
            return StringUtils.hasText(headerValue) ? headerValue : delegate.resolveCsrfTokenValue(request, csrfToken);
        }
    }
    @Bean
    WebFilter csrfCookieWebFilter() {
        return (exchange, chain) -> {
            exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.empty()).subscribe();
            return chain.filter(exchange);
        };
    }*/
//    private JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }
}
