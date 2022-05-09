package org.project.myn.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"owner"})
public class Assets extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // NFT 이름
    private String name;

    // NFT 설명
    @Column(length = 2000)
    private String description;

    // Smart Contract
    private LocalDate contract_date;

    // url
    @Column(unique = true)
    private String url;

    // 소유자 ID (Accounts)
    @ManyToOne(fetch = FetchType.LAZY)
    private Accounts owner;

}
