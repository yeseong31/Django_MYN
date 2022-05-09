package org.project.myn.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Accounts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // OpenSea 사용자이름
    private String username;

    // 계정주소
    @Column(nullable = false, unique = true)
    private String address;

    // 이메일
    private String email;

    // 비밀번호
    private String password;

    // 소셜가입여부
    private boolean fromSocial;

    // ClubMemberRole 타입값을 처리하기 위해 Set<ClubMemberRole> 타입 추가
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<AccountsRole> roleSet = new HashSet<>();

    public void addMemberRole(AccountsRole role) {
        roleSet.add(role);
    }

    public void changeUsername(String username) {
        this.username = username;
    }

}
