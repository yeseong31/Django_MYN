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
public class ClubMemberDTO {

    private String email;

    private String password;
    private String phoneNum;
    private String address;
    private boolean fromSocial;

    private LocalDateTime regDate, modDate;

}
