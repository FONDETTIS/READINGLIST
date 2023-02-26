package readinglist.dataPG;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
	
	//Florent COUDERT
	//Reader findByReader(String username);
	//UserDetails findByUsername(String username);
	Administrator findById(Long id);
	Administrator findByLogin(String login);
	Administrator findByAdminname(String adminname);
	
	
//	@Modifying(clearAutomatically = true)
//	@Transactional
//	@Query("update Address address set address.isdefault = true where address.id = :id")
//	void makeCustomerAddressDefault(@Param("id") Long id);


//	@Modifying(clearAutomatically = true)
//	@Transactional
//	@Query("update Address address set address.isdefault = false where address.id = :id")
//	//void makeCustomerAddressNonDefault(@Param("id") Long id);
//	void makeCustomerAddressNonDefault(@Param("id") Long id);

//	@Transactional
//	@Query("select allRoles from Administrator administrator where administrator.id = ?1")
//	Set<Adminrole> findAllRolesAdministrator(Long id);	

}


