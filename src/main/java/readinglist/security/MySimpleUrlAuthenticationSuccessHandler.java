package readinglist.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//import mycompany.controller.CsrfLoginController;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
	//protected final Log logger = LogFactory.getLog(this.getClass());
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MySimpleUrlAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public MySimpleUrlAuthenticationSuccessHandler() {
    	super();
        //LOGGER.warn("FLORENT_COUDERT-Constructor");
    }

    // API

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        
    	LOGGER.warn("READINGLIST-onAuthenticationSuccess");
    	
    	//handle(request, response, authentication);
        //clearAuthenticationAttributes(request);
//    	UsernamePasswordAuthenticationToken authReq =  new UsernamePasswordAuthenticationToken(username, password);
//        Authentication auth = authManager.authenticate(authReq);
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);
//        HttpSession session = request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
    	
    	redirectStrategy.sendRedirect(request, response, "/readinglist/readinglist");
    	
    }

    // IMPL

    protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        
    	//final String targetUrl = determineTargetUrl(authentication);

//        if (response.isCommitted()) {
//            LOGGER.debug("Response has already been committed. Unable to redirect to " + targetUrl);
//            return;
//        }

        //redirectStrategy.sendRedirect(request, response, targetUrl);
        redirectStrategy.sendRedirect(request, response, "/csrftest/mycsrf");
    }

    protected String determineTargetUrl(final Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        if (isUser) {
            return "/homepage.html";
        } else if (isAdmin) {
            return "/console.html";
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the session
     * during the authentication process.
     */
    protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
