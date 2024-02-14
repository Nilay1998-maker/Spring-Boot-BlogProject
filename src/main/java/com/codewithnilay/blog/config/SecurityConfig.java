package com.codewithnilay.blog.config;

import java.beans.Customizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithnilay.blog.security.CustomUserDetailService;
import com.codewithnilay.blog.security.JwtAuthenticationEntryPoint;
import com.codewithnilay.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	
	//
	public static final String[] PUBLIC_URLS= {
			"/v3/api-docs",
			"/v2/api-docs",
			"/api/v1/auth/**",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**",
			
	};

    @Autowired
    private CustomUserDetailService customUserDetailsService;
    
    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
    
    
    
	@Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    http
         .csrf()
         .disable()
         .authorizeHttpRequests()
         .requestMatchers(HttpMethod.GET).permitAll()
         .requestMatchers(PUBLIC_URLS).permitAll()
         .anyRequest()
         .authenticated()
          .and()
          .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
	}
			
//			 http.csrf(csrf -> csrf.disable())
//	            .authorizeHttpRequests().
//	            requestMatchers("/category/add")
//	            .authenticated()
//	            .requestMatchers("/authenticate","/register").permitAll()
//	            .anyRequest()
//	            .authenticated()
//	            .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
//	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//	    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//	    return http.build();
//         
//    }
		
//		public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//		    return httpSecurity
//		        .csrf(csrf -> csrf.disable())
//		        .authorizeHttpRequests(auth -> auth
//		            .requestMatchers("/token/**").permitAll()
//		            .anyRequest().authenticated()
//		        )
//		        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//		        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
//		        .httpBasic(Customizer.withDefaults())
//		        .build();
//		}
    
//		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		    return http
//		        .csrf(csrf -> csrf.disable())
//		        .authorizeHttpRequests(auth -> auth
//		            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // Permit static resources
//		            .antMatchers("/token/**").permitAll() // Permit specific endpoint
//		            .anyRequest().authenticated()
//		        )
//		        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//		        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
//		        .httpBasic(Customizer.withDefaults())
//		        .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
//		        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//		        .build();
//		}

	
    
    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
    */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception{
//    	return super.autenticationManagerBean();
//    }
//    
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception{
    	return configuration.getAuthenticationManager();
    }
    
    
}
