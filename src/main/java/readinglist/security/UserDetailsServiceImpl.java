package readinglist.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import readinglist.dataPG.*;

import java.util.List;
import java.util.HashSet;
import java.util.Set;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	
	@Autowired
    private UserLogRepository usersRepository;
	 
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        	
    	LOGGER.warn("JE SUIS dans LOADUSERBYUSERNAME");
    	
    	UserLog user = usersRepository.findByUsername(username);
    	
    	   	
    	   	
        if (user != null) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_READER"));
        
                       
        LOGGER.warn("SecurityConfig : userDetailsService + username = " + user.getUsername());
        LOGGER.warn("SecurityConfig : userDetailsService + password = " + user.getPassword());
        
        
        LOGGER.warn("JE SORS de LOADUSERBYUSERNAME");
        
        MyUserDetails myUserDetails = new MyUserDetails(user);
        return myUserDetails;
            	
        }
        else
        throw new UsernameNotFoundException("User '" + username + "' not found.");
        
		}
		
}


