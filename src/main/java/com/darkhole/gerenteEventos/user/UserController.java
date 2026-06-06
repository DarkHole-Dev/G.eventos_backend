package com.darkhole.gerenteEventos.user;

import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.darkhole.gerenteEventos.shared.dto.DTO;
import com.darkhole.gerenteEventos.shared.dto.ResultDTO;
import com.darkhole.gerenteEventos.shared.dto.TokenDTO;
import com.darkhole.gerenteEventos.user.dto.RegisterUserDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<ResultDTO> registerUser(@RequestBody RegisterUserDTO json) {
        if (json.name == null || json.email == null || json.password == null) 
            return DTO.BAD_REQUEST("All fields are required");
    

        String userId = userService.registerUser(json.name, json.email, json.password);

        return ResponseEntity.ok().body(TokenDTO.builder().token(userId).build());
    }

    @GetMapping("/login")
    public ResponseEntity<ResultDTO> loginUser(@RequestParam String email, @RequestParam String password) {
        if (email == null || password == null) 
            return DTO.BAD_REQUEST("Email and password are required");
        
        String userId = userService.loginUser(email, password);

        if (userId.isEmpty()) 
            return DTO.UNAUTHORIZED("Invalid email or password");
        

        return ResponseEntity.ok().body(TokenDTO.builder().token(userId).build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResultDTO> updateUser(
        @RequestHeader("Authorization") String token, 
        @RequestParam String nameField, 
        @RequestParam String value
    ) {
        if (token == null || nameField == null || value == null) 
            return DTO.BAD_REQUEST("Token, name field, and value are required");
        
        if (!nameField.equals("name") && !nameField.equals("email") && !nameField.equals("password"))
            return DTO.BAD_REQUEST("Invalid field name");

        return userService.updateUser(token, nameField, value) 
            ? DTO.SUCCESS() 
            : DTO.UNAUTHORIZED("Invalid token");
    }
    
    
    @DeleteMapping("/delete")
    public ResponseEntity<ResultDTO> deleteUser(@RequestHeader("Authorization") String token) {
        if (token == null) 
            return DTO.BAD_REQUEST("Token is required");

        return userService.deleteUser(token) 
            ? DTO.SUCCESS() 
            : DTO.UNAUTHORIZED("Invalid token");
    }



    @PutMapping("/updatePhoto")
    public ResponseEntity<ResultDTO> updatePhoto(
        @RequestHeader("Authorization") String token, 
        @RequestBody MultipartFile file
    ) {
        if (token == null || file == null) 
            return DTO.BAD_REQUEST("Token and file are required");


        String name = file.getOriginalFilename();
        String contentType = file.getContentType();
        InputStream fileContent;
        
        try {
            fileContent = file.getInputStream();
        } catch (Exception e) {
            return DTO.BAD_REQUEST("Invalid file");
        }

        return userService.updateUserImage(token, name, contentType, fileContent) 
            ? DTO.SUCCESS() 
            : DTO.UNAUTHORIZED("Invalid token");
    }


    @GetMapping("/get")
    public ResponseEntity<ResultDTO> getUser(
        @RequestHeader("Authorization") String token
    ) {
        if (token == null) 
            return DTO.BAD_REQUEST("Token is required");
    
        var user = userService.getUser(token);

        if (user.isEmpty()) 
            return DTO.UNAUTHORIZED("Invalid token");

        return ResponseEntity.ok().body(user.get());
    }


    @GetMapping("/image/{imageUrl}")
    public ResponseEntity<Resource> getUserImage(
        @PathVariable String imageUrl
    ) {
        if (imageUrl == null) 
            return ResponseEntity.badRequest().build();

        return userService.getUserImage(imageUrl)
            .map(resource -> ResponseEntity.ok().body(resource))
            .orElse(ResponseEntity.notFound().build());
    }
}