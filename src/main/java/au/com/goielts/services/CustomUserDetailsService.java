package au.com.goielts.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.model.Role;
import au.com.goielts.model.User;
 
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
 
    @Autowired
    private UserService userService;
     
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        System.out.println("ssoId : "+email);
        System.out.println("User : "+user);
        if(user==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
//        UserPrincipal principal = (UserPrincipal) user;
//        principal.setEnabled(user.getState().equals("Active"));
//        principal.setAuthorities(getGrantedAuthorities(user));
//        principal.setAccountNonExpired(true);
//        principal.setAccountNonLocked(true);
//        principal.setCredentialsNonExpired(true);
//        return principal;
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), 
                 user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));
    }
 
     
    private Set<GrantedAuthority> getGrantedAuthorities(User user){
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
         
        for(Role role : user.getUserProfiles()){
            System.out.println("UserProfile : "+role);
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getType()));
        }
        System.out.print("authorities :"+authorities);
        return authorities;
    }
     
}