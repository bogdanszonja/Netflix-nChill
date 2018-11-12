package com.codecool.netflixandchill.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.codecool.netflixandchill.security.SecurityConstants.HEADER_STRING;
import static com.codecool.netflixandchill.security.SecurityConstants.SECRET;
import static com.codecool.netflixandchill.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        logger.info(header);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            logger.info("Json Web Token is not present of has wrong prefix!");
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        try {
            if (token != null) {
                token = token.substring(7);
                Object user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody();
                logger.info(user);

                if (((Claims) user).getSubject() != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }

                return null;
            }
        } catch (ExpiredJwtException e) {
            logger.info("Json Web Token expired!");
        } catch (MalformedJwtException e) {
            logger.info("Malformed Json Web Token!");
        }

        return null;
    }

}
