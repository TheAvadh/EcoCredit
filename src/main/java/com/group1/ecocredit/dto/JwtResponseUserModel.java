package com.group1.ecocredit.dto;

import com.group1.ecocredit.models.Address;
import com.group1.ecocredit.enums.Role;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Setter
public class JwtResponseUserModel implements UserDetails {
    private Integer id;

    @Getter
    private String firstName;
    @Getter
    private String lastName;

    @Getter
    private String email;

    @Getter
    private String phoneNumber;
    @Getter
    @Embedded
    private Address address;

    private Role role;

    private boolean isEnabled;

    private String sub;

    private Integer iat;
    private Integer exp;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public Integer getUserID() { return this.id; }

}
