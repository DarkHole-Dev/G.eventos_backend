package com.darkhole.gerenteEventos.user;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.darkhole.gerenteEventos.shared.database.CDN;
import com.darkhole.gerenteEventos.shared.database.entity.UserEntity;
import com.darkhole.gerenteEventos.shared.database.repository.UserRepository;
import com.darkhole.gerenteEventos.user.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CDN cdn;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public String registerUser(String name, String email, String password) {
        return userRepository.save(UserEntity.builder()
            .name(name)
            .email(email)
            .password(passwordEncoder.encode(password))
            .build()
        ).getId();
    }

    public String loginUser(String email, String password) {
        String userId = userRepository.findByEmail(email)
            .filter(user -> passwordEncoder.matches(password, user.getPassword()))
            .map(UserEntity::getId)
            .orElse("");

        return userId;
    }

    public boolean updateUser(String userId, String nameField, String value) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) return false;

        switch (nameField) {
            case "name":
                user.setName(value);
                break;
            case "email":
                user.setEmail(value);
                break;
            case "password":
                user.setPassword(passwordEncoder.encode(value));
                break;
            default:
                return false;
        }

        userRepository.save(user);

        return true;
    }

    public boolean deleteUser(String userId) {
        if (!userRepository.existsById(userId)) return false;
        UserEntity user = userRepository.findById(userId).get();
        cdn.delete(user.getImageUrl());
        userRepository.deleteById(userId);
        return true;
    }


    public boolean updateUserImage(String userId, String fileName, String contentType, InputStream fileContent) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) return false;

        if (!user.getImageUrl().isEmpty()) cdn.delete(user.getImageUrl());
        
        user.setImageUrl(cdn.upload(fileName, contentType, fileContent));
        userRepository.save(user);

        return true;
    }



    public Optional<UserDTO> getUser(String userId) {
        return userRepository.findById(userId).map(user -> UserDTO.builder()
            .name(user.getName())
            .email(user.getEmail())
            .imageUrl(user.getImageUrl())
            .build()
        );
    }


    public Optional<Resource> getUserImage(String userId) {
        if (!userRepository.existsById(userId)) return Optional.empty();
        
        String imageUrl = userRepository.findById(userId).get().getImageUrl();
        return cdn.download(imageUrl);
    }

}
