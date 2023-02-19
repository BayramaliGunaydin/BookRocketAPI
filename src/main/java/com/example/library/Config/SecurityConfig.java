package com.example.library.Config;


import com.example.library.Service.MyUserDetailsService;
import com.example.library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private UserRepository userRepo;

    @Autowired private JWTFilter filter;

    @Autowired private MyUserDetailsService uds;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/register","/login","/","/booknamesearch/**","/bookauthorsearch/**","/booktopicsearch/**","/searchuser/**","/mostlikedbooks").permitAll()
                .antMatchers("/deletelike","/bookpost/**","/bookposts/**","/booklikes","/userlikes/**",
                        "/userposts/**","/saveimage","/sendfriendrequest/**","/acceptfriendrequest/**","/getuserfriends/**","/getuserfriendrequests",
                        "/rejectfriendrequest/**","/getsendedfriendrequests/**","/deletefriendship/**","/seteditor","/addlike/**","/cancelfriendrequest/**","/getuserchats",
                        "/getchatdetails/**","/createchat/**","/sendmessage/**"
                        ).hasAnyRole("USER","EDITOR")
                .antMatchers("/bookupdate","/addbook","/deletebook/**").hasRole("EDITOR")
                .antMatchers("/updaterole/**","/info","/users").hasRole("ADMIN")

                .and()
                .userDetailsService(uds)
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
