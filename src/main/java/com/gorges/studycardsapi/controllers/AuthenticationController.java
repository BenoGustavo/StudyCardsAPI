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
import com.gorges.studycardsapi.dto.RecoverPasswordRequest;
import com.gorges.studycardsapi.dto.UserDto.Register;
import com.gorges.studycardsapi.jwt.JwtUtil;
import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.models.VerificationTokenEntity;
import com.gorges.studycardsapi.responses.Response;
import com.gorges.studycardsapi.services.AuthenticationService;
import com.gorges.studycardsapi.services.VerificationTokenService;
import com.gorges.studycardsapi.utils.enums.Token;

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
        public ResponseEntity<Response<String>> signup(@RequestBody Register registerDto)
                        throws IllegalArgumentException {

                String confirmationMessage = authenticationService.signup(registerDto);

                Response<String> response = new Response.Builder<String>()
                                .message("Success")
                                .status(200)
                                .data(confirmationMessage)
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

        @PostMapping("/send-recover-password-token")
        public ResponseEntity<Response<String>> sendRecoverPasswordToken(@RequestParam("email") String email) {
                authenticationService.sendRecoverPasswordToken(email);

                Response<String> response = new Response.Builder<String>()
                                .message("Success")
                                .status(200)
                                .data("Recover password token sent to " + email)
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping("/validate-recover-password")
        public ResponseEntity<Response<String>> validateRecoverPasswordToken(@RequestParam("token") String token) {
                tokenService.findByToken(token, Token.PASSWORD_RECOVERY);

                Response<String> response = new Response.Builder<String>()
                                .message("Success")
                                .status(200)
                                .data("Password recovery token is valid")
                                .build();

                return ResponseEntity.ok(response);
        }

        @PostMapping("/recover-password")
        public ResponseEntity<Response<String>> recoverPassword(@RequestBody RecoverPasswordRequest request) {

                VerificationTokenEntity validatedToken = tokenService.findByToken(request.getToken(),
                                Token.PASSWORD_RECOVERY);

                authenticationService.recoverPassword(request.getToken(), request.getPassword());

                Response<String> response = new Response.Builder<String>()
                                .message("Success")
                                .status(200)
                                .data("Password successfully recovered")
                                .build();

                tokenService.deleteToken(validatedToken);

                return ResponseEntity.ok(response);
        }

        @GetMapping("/verify-email")
        public ResponseEntity<Response<String>> verifyEmail(@RequestParam("token") String token) {
                VerificationTokenEntity validatedToken = tokenService.findByToken(token, Token.ACCOUNT_VALIDATION);

                Response<String> response = new Response.Builder<String>()
                                .message("Success")
                                .status(200)
                                .data("Email successfully verified")
                                .build();

                tokenService.deleteToken(validatedToken);
                return ResponseEntity.ok(response);
        }
}
