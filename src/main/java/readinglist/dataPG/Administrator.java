/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package readinglist.dataPG;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import readinglist.common.utils.AdminPresentation;
import readinglist.common.utils.AdminPresentationClass;
import readinglist.common.utils.AdminPresentationCollection;
import readinglist.common.utils.ValidationConfiguration;
import readinglist.common.utils.AdminPresentationOperationTypes;
import readinglist.common.utils.ConfigurationItem;

import readinglist.common.utils.AddMethodType;


//import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
//import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
//import org.broadleafcommerce.common.presentation.AdminPresentationOperationTypes;
//import org.broadleafcommerce.common.presentation.ConfigurationItem;
//import org.broadleafcommerce.common.presentation.ValidationConfiguration;
//import org.broadleafcommerce.common.presentation.client.AddMethodType;
//import org.broadleafcommerce.common.presentation.client.OperationType;
//import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
//import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
//import org.broadleafcommerce.common.sandbox.domain.SandBox;
//import org.broadleafcommerce.common.sandbox.domain.SandBoxImpl;
//import org.broadleafcommerce.openadmin.server.service.type.ContextType;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
//import javax.persistence.Index;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * 
 * @author jfischer
 *
 */


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@Table(name = "administrators", indexes = {@Index(name="adminname_index",columnList = "adminname"),@Index(name="admin_email_index",columnList = "email")})
@Table(name = "administrators")
@Cacheable("blStandardElements")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AdminPresentationClass(friendlyName = "AdminUserImpl_baseAdminUser")
public class Administrator implements Serializable, AdminSecurityContext, AdminMainEntity {
//public class Administrator {

	private static final Logger LOGGER = LoggerFactory.getLogger(Administrator.class);
    
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "AdminUserId")
	@GenericGenerator(
			name="AdminUserId",
			strategy="readinglist.idgenerator.IdOverrideTableGenerator",
			parameters = {
			          @Parameter(name="segment_value", value="AdminUserImpl"),
			          @Parameter(name="entity_name", value="readinglist.dataPG.Administrator")
			}
	)
	@Column(name = "administratorid", updatable = false, nullable = false)
	@AdminPresentation(friendlyName = "AdminUserImpl_Admin_User_ID", group = "AdminUserImpl_Primary_Key", visibility = VisibilityEnum.HIDDEN_ALL)
	private Long id;

//    @Id
//    @Column(name = "administratorid", updatable = false, nullable = false)
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    @AdminPresentation(friendlyName = "AdminUserImpl_Admin_User_ID", group = "AdminUserImpl_Primary_Key", visibility = VisibilityEnum.HIDDEN_ALL)
//    private Long id;
    
    
    @Column(name = "login", nullable=false)
    @AdminPresentation(friendlyName = "AdminUserImpl_Admin_Login", order=2, group = "AdminUserImpl_User", prominent=true)
    protected String login;
    		
    @Column(name = "adminname", nullable=false)
    @Index(name="adminname_index", columnNames={"adminname"})
    @AdminPresentation(friendlyName = "AdminUserImpl_Admin_Name", order = 1, group = "AdminUserImpl_User", prominent = true,
            validationConfigurations = { @ValidationConfiguration(
                    validationImplementation = "macompagnie.forms.RegexPropertyValidator",
                    configurationItems = { @ConfigurationItem(itemName = "regularExpression", itemValue = "\\w+"),
                            @ConfigurationItem(itemName = ConfigurationItem.ERROR_MESSAGE, itemValue = "Only word characters are allowed") }
                    ) })
    protected String adminname;
    
    @Column(name = "password", nullable=false)
    @AdminPresentation(
        friendlyName = "AdminUserImpl_Admin_Password",
        order = 3, 
        group = "AdminUserImpl_User", 
        fieldType = SupportedFieldType.PASSWORD,
        validationConfigurations={
            @ValidationConfiguration(
                            validationImplementation = "macompagnie.forms.MatchesFieldValidator",
                configurationItems = {
                        @ConfigurationItem(itemName = ConfigurationItem.ERROR_MESSAGE, itemValue = "passwordNotMatchError"),
                        @ConfigurationItem(itemName="otherField", itemValue="passwordConfirm")
                }
            )
        }
    )
    protected String password;

    @Column(name = "email", nullable=false)
    @Index(name="admin_email_index", columnNames={"email"})
    @AdminPresentation(friendlyName = "AdminUserImpl_Admin_Email_Address", order=4, group = "AdminUserImpl_User")
    protected String email;

    @Column(name = "phonefixe")
    @AdminPresentation(friendlyName = "AdminUserImpl_Fixed_Phone_Number", order=5, group = "AdminUserImpl_User")
    protected String phonefixe;
    
    @Column(name = "phonemobile")
    @AdminPresentation(friendlyName = "AdminUserImpl_Mobile_Phone_Number", order=5, group = "AdminUserImpl_User")
    protected String phonemobile;

    @Column(name = "activation")
    @AdminPresentation(friendlyName = "AdminUserImpl_Active_Status", order=6, group = "AdminUserImpl_User")
    protected Boolean activation = Boolean.TRUE;

      
    // All roles that this user has 
//    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Adminrole.class)
//    //@ManyToMany(fetch = FetchType.EAGER, targetEntity = Adminrole.class)
//    @JoinTable(name = "adminadministratorrolexref", joinColumns = @JoinColumn(name = "administratorid", referencedColumnName = "administratorid"), inverseJoinColumns = @JoinColumn(name = "adminroleid", referencedColumnName = "adminroleid"))
//    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
//    @BatchSize(size = 50)
//    @AdminPresentationCollection(addType = AddMethodType.LOOKUP, friendlyName = "roleListTitle", manyToField = "allAdministrators",
//                    operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE))
//    protected Set<Adminrole> allRoles = new HashSet<Adminrole>();

//    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Adminpermission.class)
//    //@ManyToMany(fetch = FetchType.EAGER, targetEntity = Adminpermission.class)
//    @JoinTable(name = "adminadministratorpermissionxref", joinColumns = @JoinColumn(name = "administratorid", referencedColumnName = "administratorid"), inverseJoinColumns = @JoinColumn(name = "adminpermissionid", referencedColumnName = "adminpermissionid"))
//    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
//    @BatchSize(size = 50)
//    @AdminPresentationCollection(addType = AddMethodType.LOOKUP, friendlyName = "permissionListTitle", manyToField = "allAdministrators",
//                operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE))
//    protected Set<Adminpermission> allPermissions = new HashSet<Adminpermission>();
	
    
//    @ManyToOne(targetEntity = SandBox.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinTable(name = "adminadministratorsandbox", joinColumns = @JoinColumn(name = "administratorid", referencedColumnName = "administratorid"), inverseJoinColumns = @JoinColumn(name = "sandboxid", referencedColumnName = "sandboxid"))
//    @AdminPresentation(excluded = true)
//    protected SandBox overrideSandBox;
    

   
    public Long getId() {
       return id;
   	}
   
    public void setId(Long id) {
        this.id = id;
    }
  
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
   	    
    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }
    
    public String getPassword() {
        return password;
    }
   
    public void setPassword(String password) {
        this.password = password;
    }
   
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhonefixe() {
        return phonefixe;
    }
    
    public void setPhonefixe(String phonefixe) {
        this.phonefixe = phonefixe;
    }
    
    public String getPhonemobile() {
        return phonemobile;
    }
    
    public void setPhonemobile(String phonemobile) {
        this.phonemobile = phonemobile;
    }
    
    public boolean isActivated()
  	{ 
  		  return activation; 
  	}
  	  
  	public void setActivation(boolean activation) 
  	{ 
  		  this.activation = activation; 
  	}
      
//	public Set<Adminrole> getAllroles() {
//        return allRoles;
//    }
//
//    public void setAllroles(Set<Adminrole> allRoles) {
//        this.allRoles = allRoles;
//    }
//    
//    public Set<Adminpermission> getAllpermissions() {
//        return allPermissions;
//    }
//    
//    public void setAllPermissions(Set<Adminpermission> allPermissions) {
//        this.allPermissions = allPermissions;
//    }
//    
//    //@Override
//    public SandBox getOverrideSandBox() {
//        return overrideSandBox;
//    }
//
//    //@Override
//    public void setOverrideSandBox(SandBox overrideSandBox) {
//        this.overrideSandBox = overrideSandBox;
//    }
    
   

    public ContextType getContextType() {
        return ContextType.GLOBAL;
    }

    public void setContextType(ContextType contextType) {
        //do nothing
    }

    public String getContextKey() {
        return null;
    }

    public void setContextKey(String contextKey) {
        //do nothing
    }
    
    public void checkCloneable(Administrator administrator) throws CloneNotSupportedException, SecurityException, NoSuchMethodException {
        Method cloneMethod = administrator.getClass().getMethod("clone", new Class[]{});
        if (cloneMethod.getDeclaringClass().getName().startsWith("macompagie") && !administrator.getClass().getName().startsWith("macompagnie")) {
            //subclass is not implementing the clone method
            throw new CloneNotSupportedException("Custom extensions and implementations should implement clone.");
        }
    }
    
        
    @Transient
    protected String unencodedPassword;
    
    public String getUnencodedPassword() {
        return unencodedPassword;
    }
    
   	public void setUnencodedPassword(String unencodedPassword) {
      	this.unencodedPassword = unencodedPassword;
  	}

   	
    public Administrator clone() {
        Administrator clone;
        try {
            clone = (Administrator) Class.forName(this.getClass().getName()).newInstance();
            try {
                checkCloneable(clone);
            } catch (CloneNotSupportedException e) {
                LOGGER.warn("Clone implementation missing in inheritance hierarchy outside of Mycompany: " + clone.getClass().getName() + " " + e.getMessage());
            }
            clone.setId(id);
            clone.setAdminname(adminname);
            clone.setLogin(login);
            clone.setPassword(password);
            clone.setEmail(email);
            clone.setPhonefixe(phonefixe);
            clone.setPhonemobile(phonemobile);
            clone.setActivation(activation);

//            if (allRoles != null) {
//                for (Adminrole role : allRoles) {
//                    Adminrole roleClone = role.clone();
//                    clone.getAllroles().add(roleClone);
//                }
//            }
//
//            if (allPermissions != null) {
//                for (Adminpermission permission : allPermissions) {
//                    Adminpermission permissionClone = permission.clone();
//                    clone.getAllpermissions().add(permissionClone);
//                }
//            }

//            if (overrideSandBox != null) {
//                clone.setOverrideSandBox(overrideSandBox.clone());
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return clone;
    }
    
    
    
    @Override
    public String getMainEntityName() {
        return getAdminname();
    }
    
    

}

