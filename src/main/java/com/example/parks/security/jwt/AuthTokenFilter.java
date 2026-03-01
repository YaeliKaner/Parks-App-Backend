//
//package com.example.parks.security.jwt;
//
//import com.example.parks.security.CustomUserDetailsService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//
//import java.io.IOException;
//
//
//public class AuthTokenFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtUtils jwtUtils;
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        try{
//            String jwt=jwtUtils.getJwtFromCookies(httpServletRequest);
//            if(jwt !=null && jwtUtils.validateJwtToken(jwt)){
//                String userEmail=jwtUtils.getUserEmailFromJwtToken(jwt);
//                UserDetails userDetails= userDetailsService.loadUserByUsername(userEmail);
//
//                UsernamePasswordAuthenticationToken authentication=
//                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            }
//
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }
//
//        filterChain.doFilter(httpServletRequest,httpServletResponse);
//    }
//
//}
package com.example.parks.security.jwt;

import com.example.parks.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component   // <<< חשוב! שיהיה Bean של Spring
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = jwtUtils.getJwtFromCookies(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                String userEmail = jwtUtils.getUserEmailFromJwtToken(jwt);
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(userEmail);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println("AuthTokenFilter error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
