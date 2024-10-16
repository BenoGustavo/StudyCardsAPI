package com.gorges.studycardsapi.error.filter;

import java.io.IOException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorges.studycardsapi.error.http.NotFound404Exception;
import com.gorges.studycardsapi.responses.Response;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException | NotFound404Exception ex) {
            setErrorResponse(HttpServletResponse.SC_NOT_FOUND, response, "User not found", ex);
        } catch (io.jsonwebtoken.MalformedJwtException ex) {
            setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, "Invalid JWT token", ex);
        } catch (ServletException ex) {
            setErrorResponse(HttpServletResponse.SC_BAD_REQUEST, response, ex.getMessage(), ex);
        } catch (IOException ex) {
            setErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response, "An unexpected error occurred",
                    ex);
        } catch (Exception ex) {
            setErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response, "An unexpected error occurred",
                    ex);
        }
    }

    private void setErrorResponse(int status, HttpServletResponse response, String message, Exception ex)
            throws IOException {
        Response<Object> errorResponse = new Response.Builder<>()
                .status(status)
                .message(message)
                .error(status, message, ex.getMessage())
                .build();

        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(errorResponse));
        response.getWriter().flush();
        response.getWriter().close();
    }

    private String convertObjectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (IOException e) {
            return "{\"error\": \"Failed to convert object to JSON\"}";
        }
    }
}