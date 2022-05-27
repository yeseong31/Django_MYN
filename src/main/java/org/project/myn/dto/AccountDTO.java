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
public class AccountDTO {

    private Long id;

    private String address;
    private String username;

    private LocalDateTime regDate, modDate;

    // 사용자 이메일(아이디)
    private String email;

}
