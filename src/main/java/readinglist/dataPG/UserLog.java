package readinglist.dataPG;



import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@Table(name = "users")
public class UserLog {

  private static final long serialVersionUID = 1L;
  
  //@Autowired
  //private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  
  @Id
  //@Column(name = "id", updatable = false, nullable = false)
  @Column(name = "userlogid", updatable = false, nullable = false)
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  private String username;
  @Column(name = "password")
//  @org.hibernate.annotations.ColumnTransformer(
//      forColumn = "password",
//      write = "bCryptPasswordEncoder.encode(?)"
//  )
  private String password;
  //private String passwordConfirm;
  //private String fullname;
  //private Set<Role> roles;
  //@OneToMany(targetEntity=Book.class, fetch=FetchType.EAGER, mappedBy="user")
  //private List<Book> books;
  
//DEFAULT CONSTRUCTOR ajouté par Florent COUDERT
public UserLog(){}
 
//AUTRE CONSTRUCTOR ajouté par Florent COUDERT
public UserLog(Long id,String username, String password) {
	 this.id = id;
     this.username = username;
     this.password = password;
//     this.fullname = fullname;
}

  
  //@Id
  //@GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
      return id;
  }
  
  public void setId(Long id) {
      this.id = id;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.asList(new SimpleGrantedAuthority("ROLE_READER"));
  }


}



