package com.gorges.studycardsapi.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gorges.studycardsapi.dto.UserDto.Register;
import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.responses.Response;
import com.gorges.studycardsapi.services.UserService;
import com.gorges.studycardsapi.utils.enums.Roles;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/admin/create")
    public ResponseEntity<Response<UserEntity>> create(@RequestBody Register user, @RequestParam Roles role) {
        UserEntity result = userService.create(user, role);

        Response<UserEntity> response = new Response.Builder<UserEntity>()
                .message("Success")
                .status(200)
                .data(result)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/getAll")
    public ResponseEntity<Response<List<UserEntity>>> getAll() {
        List<UserEntity> result = userService.getAll();

        Response<List<UserEntity>> response = new Response.Builder<List<UserEntity>>()
                .message("Success")
                .status(200)
                .data(result)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/getAllActive")
    public ResponseEntity<Response<List<UserEntity>>> getAllActive() {
        List<UserEntity> result = userService.getActiveOnly();

        Response<List<UserEntity>> response = new Response.Builder<List<UserEntity>>()
                .message("Success")
                .status(200)
                .data(result)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/getActiveById")
    public ResponseEntity<Response<UserEntity>> getActiveById(@RequestParam UUID id) {
        UserEntity result = userService.getActiveById(id);

        Response<UserEntity> response = new Response.Builder<UserEntity>()
                .message("Success")
                .status(200)
                .data(result)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Response<UserEntity>> delete(@RequestParam UUID id) {
        UserEntity result = userService.delete(id);

        Response<UserEntity> response = new Response.Builder<UserEntity>()
                .message("Success")
                .status(200)
                .data(result)
                .build();

        return ResponseEntity.ok(response);
    }
}
