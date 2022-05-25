package org.project.myn.entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class User extends BaseEntity {

    @Id
    private String userid;

    private String email;

    private String password;

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> roleSet = new HashSet<>();

    public void addMemberRole(UserRole role) {
        roleSet.add(role);
    }

}
