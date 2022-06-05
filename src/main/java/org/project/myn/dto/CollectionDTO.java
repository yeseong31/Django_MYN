package org.project.myn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CollectionDTO {

    private Long id;

    private String slug;
    private String name;
    private String url;

    private LocalDateTime regDate, modDate;

}
