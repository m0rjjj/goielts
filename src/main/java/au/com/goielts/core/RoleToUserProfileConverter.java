package au.com.goielts.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import au.com.goielts.model.Role;
import au.com.goielts.services.RoleService;

@Component
public class RoleToUserProfileConverter implements Converter<Object, Role>{
 
    @Autowired
    RoleService roleService;
 
    /**
     * Gets UserProfile by Id
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public Role convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        Role role = roleService.findById(id);
        System.out.println("Role : "+role);
        return role;
    }
     
}