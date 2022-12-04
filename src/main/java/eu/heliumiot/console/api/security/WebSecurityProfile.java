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

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// ref : https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
// voir pour cross-origin...

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityProfile extends WebSecurityConfigurerAdapter {

    //@Autowired
    //private UserService userService;

    //@Autowired
    //private WebSecurityConfig webSecurityConfig;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*
        JWTAuthenticationFilter autheFilter = new JWTAuthenticationFilter(
                authenticationManager(),
                userService,
                webSecurityConfig
        );
        autheFilter.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/sign/in", "POST")
        );

        JWTAuthorizationFilter authoFilter = new JWTAuthorizationFilter(
                authenticationManager(),
                userService
        );
        authoFilter.setWebSecurityConfig(webSecurityConfig);
*/

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/internal/3.0/exit").permitAll()
                // Springfox swagger url
                .antMatchers("/swagger-**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                //.antMatchers("/swagger-ui/**").permitAll()
                //.antMatchers("/v2/api-docs**").permitAll()
                .antMatchers("/swagger-doc/v2/**").permitAll()
                //.antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()

                .anyRequest().authenticated()

                .and()

                //.addFilter(autheFilter)
                //.addFilter(authoFilter)
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
    */
}
