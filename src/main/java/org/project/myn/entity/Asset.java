package org.project.myn.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"owner", "collection"})
public class Asset extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    private Account owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Collection collection;

    public void changeName(String name) {
        this.name = name;
    }
    public void changeDescription(String description) { this.description = description; }

}
