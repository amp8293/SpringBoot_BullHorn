package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security/login info for BullHorn application.
 *
 * @author amp
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
                //.antMatchers("/").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/").permitAll() //allow everyone to view landing page (where they view messages, too) without having to login
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                // tbw: check if next line is needed
                //.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
                .logout().permitAll()
                // tbw is httpBasic() needed?
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("mary").password("password").roles("USER")
                .and()
                .withUser("dave").password("begreat").roles("ADMIN")
                ;
    }

}
