package org.project.myn.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Collection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Slug(키워드를 사용한 URL의 일부)
    private String slug;

    // 컬렉션 이름
    @Column(unique = true)
    private String name;

    // OpenSea url
    private String url;

    public void changeName(String name) {
        this.name = name;
    }

}
