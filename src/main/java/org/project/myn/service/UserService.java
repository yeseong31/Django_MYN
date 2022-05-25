package org.project.myn.service;

import org.project.myn.dto.UserDTO;
import org.project.myn.entity.User;

public interface UserService {
    // 등록
    String register(UserDTO dto);
    // 조회

    //수정

    // 삭제

    default User dtoToEntity(UserDTO dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .address(dto.getAddress())
                .phoneNum(dto.getPhone())
                .fromSocial(dto.isFromSocial())
                .build();
    }

    default UserDTO entityToDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress())
                .phone(user.getPhoneNum())
                .fromSocial(user.isFromSocial())
                .regDate(user.getRegDate())
                .modDate(user.getModDate())
                .build();
    }
}
