package com.example.smartwardrobe.authentication;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class HeaderStringFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(!authorizationHeader.startsWith("Basic ")){
            filterChain.doFilter(request, response);
        }

        String encodedToken = authorizationHeader.replace("Basic ", "");

        try{

            byte[] decodedTokenBytes = Base64.decodeBase64(encodedToken);
            String decodedToken = new String(decodedTokenBytes);

            List<String> tokenParts = List.of(decodedToken.split("/"));
            String username = tokenParts.get(0);
            String authority = tokenParts.get(1);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, null, List.of(new SimpleGrantedAuthority(authority)));
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (Exception e){
            throw new IllegalStateException(String.format("Token %s can not be trusted", encodedToken));
        }

        filterChain.doFilter(request, response);
    }
}
