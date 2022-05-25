package org.project.myn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.UserDTO;
import org.project.myn.entity.User;
import org.project.myn.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public String register(UserDTO dto) {
        User user = dtoToEntity(dto);
        repository.save(user);
        return user.getEmail();
    }
}
