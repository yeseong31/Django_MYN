package org.project.myn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssetDTO {

    private Long id;

    private String name;
    private String description;
    private LocalDate contract_date;
    private String url;

    @Builder.Default
    private List<AssetImageDTO> imageDTOList = new ArrayList<>();

    private AccountDTO accountDTO = new AccountDTO();

    private LocalDateTime regDate, modDate;

}
