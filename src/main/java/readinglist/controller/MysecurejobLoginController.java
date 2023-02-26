package readinglist.controller;


import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import readinglist.dataPG.AdministratorRepository;
import readinglist.dataPG.UserLog;
import readinglist.dataPG.UserLogRepository;
import readinglist.forms.ResetPasswordForm;
import readinglist.service.GenericResponse;

//import mysecurejob.forms.ResetPasswordForm;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

//import mycompany.dataPG.AdministratorRepository;
//import mycompany.forms.ResetPasswordForm;
//import mycompany.service.AdminSecurityService;
//import mycompany.security.PasswordChange;
//import mysecurejob.service.GenericResponse;


@Controller
//@RequestMapping("/")
@RequestMapping("/readinglist")
//@ConfigurationProperties("amazon")
public class MysecurejobLoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MysecurejobLoginController.class);
	
//	@Resource(name="myAdminSecurityService")
//    protected AdminSecurityService adminSecurityService;
//	
	@Autowired
    private AdministratorRepository administratorRepository;
	
	// Entry URLs
    //protected static String loginView = "login/login";
    protected static String loginView = "otherlogin";
    protected static String forgotPasswordView = "login/forgotPassword";
    protected static String forgotUsernameView = "login/forgotUsername";
    protected static String resetPasswordView  = "login/resetPassword";
    //protected static String changePasswordView  = "login/changePassword";
    protected static String changePasswordView  = "changePassword";
    protected static String loginRedirect = "/login";
    protected static String resetPasswordRedirect = "resetPassword";
    
//    @Autowired
//    private UserLogRepository usersRepository;
    
    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

 
	
	@RequestMapping(value="readinglist",method=RequestMethod.GET)
	public String showHome(Model model) {
		
		LOGGER.info("READINGLISTLOGINCONTROLLER-showHome");
		
//		UserLog user = new UserLog();
//		user.setUsername("flo");
//		//user.setFullname("Flo Coud");
//		user.setPassword(bCryptPasswordEncoder.encode("flo"));
//		usersRepository.save(user);
		
//		UserLog user = usersRepository.findByUsername("admin");
//		LOGGER.info("READINGLISTLOGINCONTROLLER-PASSWORD = " + user.getPassword());
		
		
		
		return "/otherlogin";
		//return "/mysecurejobAttacker";
	}
	
	@RequestMapping(value="anonymous",method=RequestMethod.GET)
	public String showAnonymous(Model model) {
		
		LOGGER.info("MYSECURETESTLOGINCONTROLLER-showAnonymous");
		
				
		
						
		//return "/views/categoryTree";
		//return "/homepage";
		return "/anonymous";
	}
	
	
	
	
//	@RequestMapping(value="/forgotPassword", method=RequestMethod.GET)
//    public String forgotPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
//        
//		model.addAttribute("myfrag1", "login/forgotPassword");
//		model.addAttribute("myfrag2", "forgotPassword");
//		
//		LOGGER.info("ADMINLOGINCONTROLLER-FORGOTPASSWORD");
//		
//		//return "/login/forgotPassword";
//		return "/layout/loginLayout";
//    }
	
//	@RequestMapping(value = "/sendResetPassword", method = RequestMethod.POST)
//    public String processSendResetPasswordEmail(HttpServletRequest request, HttpServletResponse response,
//            @RequestParam("username") String username, Model model) {
//			
//		LOGGER.info("ADMINLOGINCONTROLLER-PROCESSFORGOTPASSWORD-username = " + username);
		
		   	
    	
//    	String serverurl = getUrl(request);
//    	String domaineurl = serverurl.concat("adminitis");
//    	request.getSession(true).setAttribute("domaineurl", domaineurl);
//	    domaineurl = (String)request.getSession(true).getAttribute("domaineurl");
//    	//domaineurl = "https://jdcoudf-922:8443/fondettis";
//
//        GenericResponse errorResponse = adminSecurityService.sendResetPasswordNotification(username);
//        if (errorResponse.getHasErrors()) {
//            setErrors(errorResponse, request);
//            //return getForgotPasswordView();
//            return "home";
//            
//        } else {
//            request.getSession(true).setAttribute("forgot_password_username", username);
//            //return redirectToResetPasswordWithMessage("passwordTokenSent");
//            return "home";
//        }
		
//		model.addAttribute("myfrag1", "login/resetPassword");
//		model.addAttribute("myfrag2", "resetPassword");
//		
//		LOGGER.info("ADMINLOGINCONTROLLER-RESETPASSWORD");
//		
//		
//		//return "/login/otherlogin";
//		return "/layout/loginLayout";
//                        
//    }
	
//	@RequestMapping(value="/resetPassword", method=RequestMethod.POST)
//    public String processResetPassword(HttpServletRequest request, HttpServletResponse response, Model model,
//            @ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm) {
//		
//		
//        GenericResponse errorResponse = adminSecurityService.resetPasswordUsingToken(
//                resetPasswordForm.getUsername(),
//                resetPasswordForm.getToken(),
//                resetPasswordForm.getPassword(),
//                resetPasswordForm.getConfirmPassword());
//        if (errorResponse.getHasErrors()) {
//            setErrors(errorResponse, request);
//            return getResetPasswordView();
//        } else {
//            return redirectToLoginWithMessage("passwordReset");
//        }
	
//		return "/login/otherlogin";
//    }
	
//	@RequestMapping(value="/forgotAdminname", method=RequestMethod.GET)
//    public String forgotAdminname(HttpServletRequest request, HttpServletResponse response,Model model) {
//        
//		model.addAttribute("myfrag1", "login/forgotAdminname");
//		model.addAttribute("myfrag2", "forgotAdminname");
//		
//		//return "/login/forgotAdminname";
//		return "/layout/loginLayout";
//    }
	
	//@RequestMapping(value="/forgotAdminname", method=RequestMethod.POST)
    //public String processForgotUserName(HttpServletRequest request,
    //        @RequestParam("emailAddress") String email) {
	//public String processForgotUserName(HttpServletRequest request)
	//    {
//        GenericResponse errorResponse = adminSecurityService.sendForgotUsernameNotification(email);
//        if (errorResponse.getHasErrors()) {
//            setErrors(errorResponse, request);
//            return getForgotUsernameView();
//        } else {
//            return redirectToLoginWithMessage("usernameSent");
//        }
		
		//return "/login/otherlogin";
    //}
	
	
//	@RequestMapping(value="changePassword", method=RequestMethod.GET)
//    public String changePassword(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm, Model model) {
//        
//		//model.addAttribute("myfrag1", "/changePassword");
//		//model.addAttribute("myfrag2", "changePassword");
//		
//		LOGGER.info("CSRFLOGINCONTROLLER-CHANGEPASSWORD-GET");
//		
//		//return "/layout/loginLayout";
//		return "changePassword";
//    }
	
	@RequestMapping(value="/changePassword", method=RequestMethod.GET)
	public String changePassword(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm, Model model, 
    	@PathVariable Map<String, String> pathVars,
 		@RequestParam MultiValueMap<String, String> requestParams) throws Exception 
		{
        
//		model.addAttribute("myfrag1", "login/changePassword");
//		model.addAttribute("myfrag2", "changePassword");
		
		LOGGER.info("MYSECUREJOBLOGINCONTROLLER-CHANGEPASSWORD-GET");
		
		//return "/layout/loginLayout";
		return "/mysecurejobChange";
				
    	}
	
	@RequestMapping(value="/changePassword", method=RequestMethod.POST)
	public String getnewPassword(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm, Model model, 
    	@PathVariable Map<String, String> pathVars,
 		@RequestParam MultiValueMap<String, String> requestParams) throws Exception 
		{
        
//		model.addAttribute("myfrag1", "login/changePassword");
//		model.addAttribute("myfrag2", "changePassword");
		
		LOGGER.info("MYSECUREJOBLOGINCONTROLLER-CHANGEPASSWORD-POST");
		
		//return "/layout/loginLayout";
		return "/otherlogin";
				
    	}
    
	
	
//	@RequestMapping(value="/changePassword", method=RequestMethod.POST)
//    public String processchangePassword(HttpServletRequest request, HttpServletResponse response, Model model,
//            @ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm, BindingResult result) {
//	
//		
//		LOGGER.info("LOGINCONTROLLER-CHANGEPASSWORD-POST");
//		
//		GenericResponse errorResponse = adminSecurityService.changePassword(resetPasswordForm.getUsername(),
//                        									resetPasswordForm.getOldPassword(),
//                        									resetPasswordForm.getPassword(),
//                        									resetPasswordForm.getConfirmPassword());
//						
//        if (errorResponse.getHasErrors()) {
//        	
//            //setErrors(errorResponse, request);
//        	String errorCode = errorResponse.getErrorCodesList().get(0);
//            //request.setAttribute("errorCode", errorCode);
//        	model.addAttribute("errorCode", errorCode);
//        	
//        	LOGGER.info("LOGINCONTROLLER-CHANGEPASSWORD-ERRORS");
//        	model.addAttribute("myfrag1", "login/changePassword");
//    		model.addAttribute("myfrag2", "changePassword");
//    		
//    		return "/layout/loginLayout";
//    		            
//        } else {
//                   	
//        	LOGGER.info("LOGINCONTROLLER-CHANGEPASSWORD-NOERRORS");
//        	
//        	StringBuffer url = new StringBuffer("redirect:").append("/adminitis/login").append("?messageCode=").append("passwordReset");
//                    	
//        	//return redirectToLoginWithMessage("passwordReset");
//            //return "redirect:/adminitis/login?messageCode=passwordReset";
//            //return "/login/otherlogin";
//        	return url.toString();
//        }
//		
//		//return "home";
//    }
    
	
	 // AJOUTE par FLORENT COUDERT
    public String getUrl(HttpServletRequest request) {     
        String url = request.getScheme() + "://" + request.getServerName() + getPort(request, request.getScheme()) + "/";
        LOGGER.info("CSRFLOGINGCONTROLLER-getUrl = " + url);
        if (request.getContextPath() != null && ! "".equals(request.getContextPath())) {
            url = url + request.getContextPath();
        } else {
            url = url;
        }
        //return "https://localhost:8443/fondettis/resetpassword";
        return url;
    }
    
    // AJOUTE par FLORENT COUDERT
    public String getPort(HttpServletRequest request, String scheme) {
        if ("http".equalsIgnoreCase(scheme) && request.getServerPort() != 80) {
            return ":" + request.getServerPort();
        } else if ("https".equalsIgnoreCase(scheme) && request.getServerPort() != 444) {
        	LOGGER.info("MYSECUREJOBLOGINGCONTROLLER-getPort = " + request.getServerPort());
            return ":" + request.getServerPort();
        }
        //return "";  // no port required
        // AJOUTE par FLORENT COUDERT, on retourne en fait :8443 sinon ça marche pas
        return ":" + request.getServerPort();  
    }
    
    protected void setErrors(GenericResponse response, HttpServletRequest request) {
        String errorCode = response.getErrorCodesList().get(0);
        request.setAttribute("errorCode", errorCode);
    }
	
    protected String redirectToResetPasswordWithMessage(String message) {
        StringBuffer url = new StringBuffer("redirect:").append(resetPasswordRedirect).append("?messageCode=").append(message);
        return url.toString();
    }
	
    public static String getForgotPasswordView() {
        return forgotPasswordView;
    }
	
    public static String getChangePasswordView() {
        return changePasswordView;
    }
    
    public static String getResetPasswordView() {
        return resetPasswordView;
    }
    
    protected String redirectToLoginWithMessage(String message) {
        StringBuffer url = new StringBuffer("redirect:").append(loginRedirect).append("?messageCode=").append(message);
        return url.toString();
    }
    
    
	
	
}
