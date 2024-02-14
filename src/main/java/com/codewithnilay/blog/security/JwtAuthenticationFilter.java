package com.codewithnilay.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
    private JwtTockenHelper jwttockenhelper;


    @Autowired
    private UserDetailsService userDetailsService;

	
	
	@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			
			
			// get tocken
			
			String requestTocken = request.getHeader("Authorization");
			
			
			System.out.println(requestTocken);
			
			 //Bearer 2352345235sdfrsfgsdfsdf
			
			String username = null;
	        String token = null;
	        if (requestTocken != null && requestTocken.startsWith("Bearer")) {
	        	
	        	token = requestTocken.substring(7);
	        	System.out.println(token);
	        	System.out.println("Hello");
	        	try {
	
	                username = this.jwttockenhelper.getUsernameFromToken(token);
	
	            } catch (IllegalArgumentException e) {
	                System.out.println("Unable to get jwt tocken");
	            } 
	        	catch (ExpiredJwtException e) {
	                System.out.println(" jwt Tocken has expired");
	            }
	        	catch (MalformedJwtException e) {
	                System.out.println("Invalid jwt tocken");
	            }
	        }
	        else {
	        	System.out.println("Jwt tocken does not begin with Bearer");
	        }
        
        
		// once we get tocken the validate
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

             System.out.println(username);
            //fetch user detail from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            Boolean validateToken = this.jwttockenhelper.validateToken(token, userDetails);
            if (validateToken) {

                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
            else {
            	System.out.println("invalid jwt tocken");
            }
        }
        
        
        else {
        	System.out.println("usename is null or context is not null");
        	
        }
        filterChain.doFilter(request, response);
		
	}

}
