package org.project.myn.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "clubMember")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String address;

    @OneToOne(fetch = FetchType.LAZY)
    private ClubMember clubMember;

    public void changeUsername(String username) { this.username = username; }

}
