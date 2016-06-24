package busy.schedule.web;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import busy.role.Role;
import busy.role.RoleService;

@Component
public class RoleFormatter  implements Formatter<List<Role>>{

    @Autowired
    private RoleService  roleService;

    @Override
    public String print(List<Role> objects, Locale locale) {

        StringBuilder stb = new StringBuilder();
        for(Role role: objects) {
            stb.append(role.getId() + " ");
        }
        
        return stb.toString().trim().replace(" ", ",");
    }

    @Override
    public List<Role> parse(String text, Locale locale) throws ParseException {
        
        String[] values = text.split(",");
        Integer[] ids = new Integer[values.length];
        
        for(int i=0; i < values.length; i++) {
            try {
                ids[i] = Integer.parseInt(values[i]);
            } catch (NumberFormatException e) { };
        }
        
        return roleService.findRolesById(ids);
    }
    
}
