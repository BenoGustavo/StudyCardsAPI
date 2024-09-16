package com.gorges.studycardsapi.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import com.gorges.studycardsapi.error.http.BadRequest400Exception;
import com.gorges.studycardsapi.error.http.NotFound404Exception;
import com.gorges.studycardsapi.error.http.Unauthorized401Exception;
import com.gorges.studycardsapi.error.http.UserAlreadyDeletedException;
import com.gorges.studycardsapi.responses.Response;
import com.gorges.studycardsapi.responses.ResponseError;

@ControllerAdvice
public class GlobalExceptionHandler {
        /////////////////////////
        /// HTTP STATUSES///
        ////////////////////////

        @ExceptionHandler(BadRequest400Exception.class)
        public ResponseEntity<Response<BadRequest400Exception>> handleBadRequestException(BadRequest400Exception ex) {
                ResponseError error = new ResponseError(ex.getCode(), ex.getMessage());

                Response<BadRequest400Exception> responseBody = new Response.Builder<BadRequest400Exception>()
                                .message("Bad Request!").error(error).status(ex.getCode()).build();

                return ResponseEntity.status(ex.getCode()).body(responseBody);
        }

        @ExceptionHandler(Unauthorized401Exception.class)
        public ResponseEntity<Response<Unauthorized401Exception>> handleUnauthorizedRequests(
                        Unauthorized401Exception ex) {

                ResponseError error = new ResponseError(ex.getCode(), ex.getMessage());
                Response<Unauthorized401Exception> responseBody = new Response.Builder<Unauthorized401Exception>()
                                .message("Unauthorized").error(error).status(ex.getCode())
                                .build();

                return ResponseEntity.status(ex.getCode()).body(responseBody);
        }

        @ExceptionHandler(NotFound404Exception.class)
        public ResponseEntity<Response<NotFound404Exception>> handleNotFoundRequests(NotFound404Exception ex) {

                ResponseError error = new ResponseError(ex.getCode(), ex.getMessage());
                Response<NotFound404Exception> responseBody = new Response.Builder<NotFound404Exception>()
                                .message("Not Found").error(error).status(ex.getCode()).build();

                return ResponseEntity.status(ex.getCode()).body(responseBody);
        }

        @ExceptionHandler(UsernameNotFoundException.class)
        public ResponseEntity<Response<UsernameNotFoundException>> handleUsernameNotFoundException(
                        UsernameNotFoundException ex) {
                Response<UsernameNotFoundException> responseBody = new Response.Builder<UsernameNotFoundException>()
                                .message("User not found").error(404, ex.getMessage()).status(404).build();

                return ResponseEntity.status(404).body(responseBody);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Response<HttpMessageNotReadableException>> handleHttpMessageNotReadableException(
                        HttpMessageNotReadableException ex) {
                String message = ex.getMessage().split(":")[0];

                Response<HttpMessageNotReadableException> responseBody = new Response.Builder<HttpMessageNotReadableException>()
                                .message("empty payload, check the docs!").error(400, message).status(400).build();

                return ResponseEntity.status(400).body(responseBody);
        }

        @ExceptionHandler(MultipartException.class)
        public ResponseEntity<Response<MultipartException>> handleMultipartException(MultipartException ex) {
                Response<MultipartException> responseBody = new Response.Builder<MultipartException>()
                                .message("invalid multipart request, check the docs!").error(400, ex.getMessage())
                                .status(400).build();

                return ResponseEntity.status(400).body(responseBody);
        }

        @ExceptionHandler(MissingPathVariableException.class)
        public ResponseEntity<Response<MissingPathVariableException>> handleMissingPathVarException(
                        MissingPathVariableException ex) {
                Response<MissingPathVariableException> responseBody = new Response.Builder<MissingPathVariableException>()
                                .message("Missing path variable, perhaps you forgot something on the url")
                                .error(400, ex.getMessage())
                                .status(400).build();

                return ResponseEntity.status(400).body(responseBody);
        }

        @ExceptionHandler(UserAlreadyDeletedException.class)
        public ResponseEntity<Response<UserAlreadyDeletedException>> handleUserAlreadyDeletedException(
                        UserAlreadyDeletedException ex) {
                Response<UserAlreadyDeletedException> responseBody = new Response.Builder<UserAlreadyDeletedException>()
                                .message("User already deleted").error(ex.getCode(), ex.getMessage())
                                .status(ex.getCode()).build();

                return ResponseEntity.status(ex.getCode()).body(responseBody);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Response<IllegalArgumentException>> handleIllegalArgumentException(
                        IllegalArgumentException ex) {
                Response<IllegalArgumentException> responseBody = new Response.Builder<IllegalArgumentException>()
                                .message("Invalid argument").error(400, ex.getMessage()).status(400).build();

                return ResponseEntity.status(400).body(responseBody);
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<Response<AuthenticationException>> handleAuthenticationException(
                        AuthenticationException ex) {
                Response<AuthenticationException> responseBody = new Response.Builder<AuthenticationException>()
                                .message("Authentication error").error(401, ex.getMessage()).status(401).build();

                return ResponseEntity.status(401).body(responseBody);
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<Response<MethodArgumentTypeMismatchException>> handleMethodArgumentTypeMismatchException(
                        MethodArgumentTypeMismatchException ex) {

                if (ex.getRequiredType() == null) {
                        Response<MethodArgumentTypeMismatchException> responseBody = new Response.Builder<MethodArgumentTypeMismatchException>()
                                        .message("Invalid argument type, check the docs or contact the admin!")
                                        .error(HttpStatus.BAD_REQUEST.value(), ex.getMessage())
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .build();

                        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseBody);
                }

                @SuppressWarnings("null")
                Response<MethodArgumentTypeMismatchException> responseBody = new Response.Builder<MethodArgumentTypeMismatchException>()
                                .message("Invalid argument type, check the docs or contact the admin!")
                                .error(HttpStatus.BAD_REQUEST.value(),
                                                ex.getName() + " should be of type "
                                                                + ex.getRequiredType().getSimpleName())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseBody);
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<Response<HttpRequestMethodNotSupportedException>> handleHttpRequestMethodNotSupportedException(
                        HttpRequestMethodNotSupportedException ex) {
                Response<HttpRequestMethodNotSupportedException> responseBody = new Response.Builder<HttpRequestMethodNotSupportedException>()
                                .message("Invalid request method, check the docs or contact the admin!")
                                .error(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMethod()
                                                + " method is not supported for this endpoint, expected methods are: "
                                                + ex.getSupportedHttpMethods(), null)
                                .status(HttpStatus.METHOD_NOT_ALLOWED.value()).build();

                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED.value()).body(responseBody);
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<Response<DataIntegrityViolationException>> handleDataIntegrityViolationException(
                        DataIntegrityViolationException ex) {
                Response<DataIntegrityViolationException> responseBody = new Response.Builder<DataIntegrityViolationException>()
                                .message("Data integrity violation, check the docs or contact the admin!")
                                .error(HttpStatus.BAD_REQUEST.value(),
                                                ex.getMostSpecificCause().getMessage().split("Detail: ")[0])
                                .status(HttpStatus.BAD_REQUEST.value())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseBody);
        }

        @ExceptionHandler(ClassNotFoundException.class)
        public ResponseEntity<Response<ClassNotFoundException>> handleClassNotFoundException(
                        ClassNotFoundException ex) {
                Response<ClassNotFoundException> responseBody = new Response.Builder<ClassNotFoundException>()
                                .message("Class not found, check the docs or contact the admin!")
                                .error(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "")
                                .status(HttpStatus.NOT_FOUND.value()).build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(responseBody);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Response<Exception>> handleException(Exception ex) {
                Response<Exception> responseBody = new Response.Builder<Exception>()
                                .message("Internal server error, contact the admin!")
                                .error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "")
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(responseBody);
        }
}
