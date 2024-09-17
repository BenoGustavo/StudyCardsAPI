package com.gorges.studycardsapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gorges.studycardsapi.dto.AuthenticationDtos.LoginDto;
import com.gorges.studycardsapi.dto.AuthenticationDtos.TokenDto;
import com.gorges.studycardsapi.dto.UserDto.Register;
import com.gorges.studycardsapi.jwt.JwtUtil;
import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.responses.Response;
import com.gorges.studycardsapi.services.AuthenticationService;
import com.gorges.studycardsapi.services.VerificationTokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
        @Autowired
        private AuthenticationService authenticationService;

        @Autowired
        private VerificationTokenService tokenService;

        @Autowired
        private JwtUtil jwtUtil;

        @PostMapping("/signup")
        public ResponseEntity<Response<UserEntity>> signup(@RequestBody Register registerDto)
                        throws IllegalArgumentException {

                UserEntity userEntity = authenticationService.signup(registerDto);

                Response<UserEntity> response = new Response.Builder<UserEntity>()
                                .message("Success")
                                .status(200)
                                .data(userEntity)
                                .build();

                return ResponseEntity.ok(response);
        }

        @PostMapping("/login")
        public ResponseEntity<Response<TokenDto>> login(@RequestBody LoginDto loginDto) {
                UserEntity authenticatedUser = authenticationService.authenticate(loginDto);

                String jwtToken = jwtUtil.generateToken(authenticatedUser);

                TokenDto loginResponse = TokenDto
                                .builder()
                                .token(jwtToken)
                                .expiresIn(jwtUtil.extractExpirationDate(jwtToken))
                                .build();

                Response<TokenDto> response = new Response.Builder<TokenDto>()
                                .message("Success")
                                .status(200)
                                .data(loginResponse)
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping("/verify-email")
        public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
                tokenService.findByToken(token);

                return ResponseEntity.ok("Email successfully verified");
        }
}
