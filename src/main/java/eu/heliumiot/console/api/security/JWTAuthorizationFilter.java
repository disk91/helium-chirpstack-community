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

import eu.heliumiot.console.service.UserCacheService;
import eu.heliumiot.console.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;

@Service
@DependsOn("flywayConfiguration")
public class JWTAuthorizationFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected class MyGrantedAuthority implements GrantedAuthority {
        private static final long serialVersionUID = 0L;
        protected String authority;

        public MyGrantedAuthority(String auth) {
            this.authority = auth;
        }

        @Override
        public String getAuthority() {
            return authority;
        }
    }

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private UserService userService;

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        // Make sure the request contains a Bearer or it is not for us
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // refuse authentication
            chain.doFilter(request, response);
            return;
        }

        // Verify Bearer and return user or error
        try {

            SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {

                @Override
                @SuppressWarnings("rawtypes")
                public Key resolveSigningKey(JwsHeader header, Claims _claims) {
                    // Examine header and claims
                    String user = _claims.getSubject();
                    UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
                    if ( u == null ) return null;
                    return userService.generateKeyForUser(u.heliumUser);
                }

            };

            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKeyResolver(signingKeyResolver)
                    .build()
                    .parseClaimsJws(authHeader.replace("Bearer ",""));

            if ( jws.getHeader().getAlgorithm().compareToIgnoreCase("HS512") != 0 ) {
                log.error("### Bearer is signed with invalid algo !!! ");
                chain.doFilter(request, response);
                return;
            }

            Claims claims = jws.getBody();
            ArrayList<String> roles = (ArrayList<String>) claims.get("roles");
            ArrayList<MyGrantedAuthority> list = new ArrayList<>();
            if (roles != null) {
                for (String a : roles) {
                    MyGrantedAuthority g = new MyGrantedAuthority(a);
                    list.add(g);
                }
            }
            String user = claims.getSubject();
            UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
            if ( u == null ) {
                log.error("### jwt attempt with non existing user!!! ");
                chain.doFilter(request, response);
                return;
            }

            if ( ! u.user.isAdmin() && roles != null ) {
                for ( String role : roles ) {
                    if ( role.compareToIgnoreCase("ROLE_ADMIN") == 0 ) {
                        log.error("### A simple user try to be identified as an admin !!! ");
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
            if ( u.user.isActive() /* todo ... more test */ ) {
                // accept the authentication
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, list));
            }
        } catch (ExpiredJwtException x) {
            // Expired
        } catch (SignatureException x) {
            // the signature of the JWT is invalid
        } catch (IllegalArgumentException x) {
            // corresponds to a non existing user inside the JWT
            // so can find a signature to be verified
        }
        chain.doFilter(request, response);

    }
}
