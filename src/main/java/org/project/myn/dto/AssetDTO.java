package org.project.myn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssetDTO {

    private Long id;

    private String name;
    private String description;
    private String url;

    private LocalDateTime regDate, modDate;

    // 사용자 Account ID
    private Long accountId;
    // 사용자 Account username
    private String accountUsername;
    // 사용자 Account address
    private String accountAddress;

    // 사용자 Collection ID
    private Long collectionId;
    // 사용자 Collection name
    private String collectionName;

}
