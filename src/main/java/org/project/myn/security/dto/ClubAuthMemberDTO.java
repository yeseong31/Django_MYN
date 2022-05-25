package org.project.myn.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User {

    private String email;
    private String phone;
    private String address;
    private boolean fromSocial;

    public ClubAuthMemberDTO(String username, String password,
                             String phone, String address, Boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.phone = phone;
        this.address = address;
        this.fromSocial = false;
    }
}
