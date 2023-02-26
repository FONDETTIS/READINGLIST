package readinglist.dataPG;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserLogRepository extends JpaRepository<UserLog, Long> {
	
	UserLog findByUsername(String username);
	
	
}


