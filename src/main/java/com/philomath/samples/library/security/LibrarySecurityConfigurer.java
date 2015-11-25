package com.philomath.samples.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.philomath.samples.library.util.LibraryConstants;

@Configuration
@EnableWebSecurity
public class LibrarySecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenAuthFilter tokenAuthFilter;
	
	@Autowired
	private TokenAuthProvider tokenAuthProvider;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
        http
        // no session management required
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()        
        .authorizeRequests()
        // the following URLs should be permitted without any authentication
        // this includes our static resources 
        // **Note** : Our landing page ("/") is the login page that 
        //			should not be authenticated, so we add it here  
        .antMatchers("/css/**", "/js/**", "/*.html", "/images/**", "/node_modules/**","/").permitAll()
        // all other requests must be authenticated
        .antMatchers("/search").hasRole("PUBLIC")
        .antMatchers("/add").hasRole("LIBRARIAN")
        .and().authorizeRequests()
        .anyRequest().fullyAuthenticated()
        .and()
        // remove basic HTTP authentication - we are writing our own login page 
        //.httpBasic()
        //.and()
        // disable Cross Site Request Forgery token 
        // we do not rely on cookie based auth and are completely stateless so we are safe
        .csrf().disable()
        // authentication for token based authentication
        .authenticationProvider(tokenAuthProvider)
        .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class);
        
       
    }
	
//	@Bean
//	@ConfigurationProperties(prefix="ldap.contextSource")
//	public LdapContextSource contextSource()	{
//		// This method reads application.properties and get the ldap configuration properties
//		LdapContextSource contextSource = new LdapContextSource();
//		return contextSource;
//	}
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		// Here we define the search paths in the LDAP server
//		auth
//		.ldapAuthentication()
//			.userDnPatterns("uid={0},ou=people")
//			.groupSearchBase("ou=groups")
//			.userSearchBase("ou=people")
//			.contextSource(contextSource());
//	}
	
}
