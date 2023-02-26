package readinglist.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.ExampleMatcher;
//import org.springframework.data.domain.ExampleMatcher.StringMatcher;
//import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import readinglist.dataPG.*;

@Configuration
@EnableWebSecurity
//@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

  @Autowired
  private UserDetailsService userDetailsService;
  
  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
  }
  
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//  }
  
  
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
      return new BCryptPasswordEncoder();
  }
  
  @Bean
  public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
      return new MySimpleUrlAuthenticationSuccessHandler();
  }
  
  
 
  
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
    
    // SOURCE INITIAL
    /*
    .authorizeRequests()
    	.antMatchers("/").access("hasRole('ROLE_READER')")
    	.antMatchers("/**").permitAll()
    	.and()
    .formLogin()
    	.loginPage("/login")
    	.failureUrl("/login?error=true");
    */
    
    .authorizeRequests()
    	.antMatchers("/").access("hasRole('ROLE_READER')")
    	//.antMatchers("/readinglist").access("hasRole('ROLE_READER')")
    	//.antMatchers("/**").permitAll()
       	.and()
    .formLogin()
    	.loginPage("/login")
    	.permitAll()
    	//.defaultSuccessUrl("/mysecurejob/readersBooks")
    	.successHandler(myAuthenticationSuccessHandler())
    	//.defaultSuccessUrl("/")
    	.failureUrl("/login?error=true")
    	.and()
    	.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400)
    	.and()
    //.logout()
    	//.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/readinglist/login");
    .logout()    //logout configuration
    	.logoutUrl("/logout") 
    	//.logoutSuccessUrl("/mysecurejob/login");
    	.logoutSuccessUrl("/readinglist/login");
    
    http.csrf().disable();
  }
  
  
  
  
  
//*************************************************************************************************//

  
  
  
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth
//      .inMemoryAuthentication()
//        //.withUser("craig").password("{noop}myspassword").roles("READER")
//        .withUser("craig").password("mypassword").roles("READER")
//        .and()
//        //.withUser("walt").password("{noop}marceline").roles("READER");
//    	.withUser("walt").password("marceline").roles("READER");
//  }
  
  
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(userDetailsService)
      .passwordEncoder(bCryptPasswordEncoder());
      
  }
  
  
  
 
  
//  @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//      auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
//  }
  
  

  
//*************************************************************************************************//  
  
  
  
  
  

/**
@Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
}
**/
  
  /**@Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService());
  }**/
  
  
	
  /**@Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	  throws Exception {
	  auth.userDetailsService(userDetailsService()).passwordEncoder(
	  bCryptPasswordEncoder());
  }
  **/
	 
  
  
  
  /**@Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
  }**/
  
  
   
  /*@Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	//LOGGER.warn("SecurityConfig : userDetailsService1");
    	//Optional<Reader> reader = readerRepository.findById(username);
    	Reader reader = readerRepository.findByUsername(username);
        //UserDetails userDetails = readerRepository.findOne(username);
        //UserDetails userDetails = readerRepository.getOne(username);
        //Reader reader = (Reader)readerRepository.getOne(username);
        //LOGGER.warn("SecurityConfig : userDetailsService2 + username = " + userDetails.getUsername());
        //LOGGER.warn("SecurityConfig : userDetailsService3 + fullname = " + reader.getFullname());
        //LOGGER.warn("SecurityConfig : userDetailsService4 + password = " + userDetails.getPassword());
    	LOGGER.warn("SecurityConfig : userDetailsService1 + id       = " + reader.getId());
        LOGGER.warn("SecurityConfig : userDetailsService2 + username = " + reader.getUsername());
        LOGGER.warn("SecurityConfig : userDetailsService3 + password = " + reader.getPassword());
        //if (userDetails != null) {
        if (reader != null) {
          //LOGGER.warn("SecurityConfig : userDetailsService4  "  + username);
          //return userDetails;
          //return reader;
          Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
          grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_READER"));
          String myusername = reader.getUsername();
          String mypassword = reader.getPassword();
          return new org.springframework.security.core.userdetails.User(reader.getUsername(), "{noop}mypassword", grantedAuthorities);
          //return new org.springframework.security.core.userdetails.User(reader.getUsername(), reader.getPassword(), grantedAuthorities);
        }
        throw new UsernameNotFoundException("User '" + username + "' not found.");
      }
    };*/
    
  
  

  // SOURCE INITIAL
  /*@Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username)
          throws UsernameNotFoundException {
        UserDetails userDetails = readerRepository.findOne(username);
        if (userDetails != null) {
          return userDetails;
        }
        throw new UsernameNotFoundException("User '" + username + "' not found.");
      }
    };
  }*/
 

}
