package au.com.goielts.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
 
/**
 * The Class CustomSuccessHandler.
 */
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
 
    /** The redirect strategy. */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
     
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    protected void handle(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
  
        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }
  
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
     
    /**
     * Determine target url.
     *
     * @param authentication the authentication
     * @return the string
     */
    public String determineTargetUrl(Authentication authentication) {
        String url="";
         
        Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
         
        List<String> roles = new ArrayList<String>();
 
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
 
        if (isAdmin(roles)) {
            url = "/admin";
        } else if (isStudent(roles)) {
            url = "/course/index";
        }else if (isTeacher(roles)) {
            url = "/course/index";
        } else {
            url="/accessDenied";
        }
        
        return url;
    }
  
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler#setRedirectStrategy(org.springframework.security.web.RedirectStrategy)
     */
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler#getRedirectStrategy()
     */
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
     
    
    /**
     * Checks if is admin.
     *
     * @param roles the roles
     * @return true, if is admin
     */
    private boolean isAdmin(List<String> roles) {
        if (roles.contains("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }
 
    /**
     * Checks if is student.
     *
     * @param roles the roles
     * @return true, if is student
     */
    private boolean isStudent(List<String> roles) {
        if (roles.contains("ROLE_STUDENT")) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if is teacher.
     *
     * @param roles the roles
     * @return true, if is teacher
     */
    private boolean isTeacher(List<String> roles) {
        if (roles.contains("ROLE_TEACHER")) {
            return true;
        }
        return false;
    }
 
 
}