
package com.example.parks.security;

import com.example.parks.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    public CustomUserDetails(String useremail, String password, Collection<? extends GrantedAuthority> authorities) {
        super(useremail, password, authorities);
    }
}
