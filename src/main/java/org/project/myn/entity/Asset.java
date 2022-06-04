package org.project.myn.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "account")
public class Asset extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public void changeName(String name) {
        this.name = name;
    }
    public void changeDescription(String description) {
        this.description = description;
    }

}
