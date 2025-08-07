package com.Ecommerce.ApliServi.Config.Filter;

import com.Ecommerce.ApliServi.Util.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class jwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public jwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
            String username = jwtUtils.extractUsername(decodedJWT);
            String stringAuthorities = jwtUtils.getClaimFromToken(decodedJWT, "authorities").toString();
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList(stringAuthorities);
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "", authorities);
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // Verificar si es un token de actualización y renovar el token de acceso si es
            // necesario
            if (isRefreshToken(decodedJWT)) {
                // Crear un objeto Authentication utilizando UserDetails
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // Crear el nuevo token de acceso utilizando el objeto Authentication
                String newAccessToken = jwtUtils.createRefreshToken(authentication);
                response.setHeader("Authorization", "Bearer " + newAccessToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    public boolean isRefreshToken(DecodedJWT decodedJWT) {
        String tokenType = jwtUtils.getClaimFromToken(decodedJWT, "token_type").asString();
        return "refresh_token".equals(tokenType);
    }
}