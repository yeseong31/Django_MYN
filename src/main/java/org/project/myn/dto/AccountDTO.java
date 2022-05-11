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

    private String username;
    private String address;
    private String email;
    private String password;
    private boolean fromSocial;

    private LocalDateTime regDate, modDate;

}
