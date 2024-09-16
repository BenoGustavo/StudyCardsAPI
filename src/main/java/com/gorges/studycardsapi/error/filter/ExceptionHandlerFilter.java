package com.gorges.studycardsapi.error.filter;

import java.io.IOException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gorges.studycardsapi.error.http.NotFound404Exception;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException | NotFound404Exception ex) {
            setErrorResponse(HttpServletResponse.SC_NOT_FOUND, response, ex.getMessage());
        } catch (ServletException ex) {
            setErrorResponse(HttpServletResponse.SC_BAD_REQUEST, response, ex.getMessage());
        } catch (IOException ex) {
            setErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response, "An unexpected error occurred");
        }
    }

    private void setErrorResponse(int status, HttpServletResponse response, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}