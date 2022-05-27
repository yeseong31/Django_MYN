package org.project.myn.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClubMember extends BaseEntity {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNum;

    private String address;

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>();

    public void addMemberRole(ClubMemberRole role) {
        roleSet.add(role);
    }
    public void deleteMemberRole(ClubMemberRole role) { roleSet.remove(role); }

    public void changePassword(String password) { this.password = password; }
    public void changeAddress(String address) { this.address = address; }

}
