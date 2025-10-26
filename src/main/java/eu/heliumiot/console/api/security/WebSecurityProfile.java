/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2018.
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 *    and associated documentation files (the "Software"), to deal in the Software without restriction,
 *    including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *    sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies or
 *    substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package eu.heliumiot.console.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// ref : https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
// voir pour cross-origin...


@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
@Configuration
public class WebSecurityProfile {

    @Autowired
    private JWTAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .addFilterAfter(jwtAuthorizationFilter, BasicAuthenticationFilter.class)
            .authenticationProvider(jwtAuthenticationProvider)
            .authorizeHttpRequests((authz) -> authz
                // Allow all OPTIONS
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Allow internal api
                .requestMatchers("/").permitAll()
                .requestMatchers("/internal/3.0/exit").permitAll()
                .requestMatchers("/internal/3.0/health").permitAll()
                // user management
                .requestMatchers("/console/1.0/sign/**").permitAll()
                // public messages
                .requestMatchers("/console/1.0/message/public").permitAll()
                .requestMatchers("/console/1.0/misc/logs").permitAll()
                .requestMatchers("/console/1.0/misc/status").permitAll()
                .requestMatchers("/console/1.0/misc/status/data").permitAll()
                // captcha
                .requestMatchers("/console/1.0/captcha/**").permitAll()
                // swagger documentation
                .requestMatchers("/swagger-doc/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                // prometheus
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated())
            ;

        return http.build();
    }

}

