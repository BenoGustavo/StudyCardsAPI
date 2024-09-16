package com.gorges.studycardsapi.responses;

import lombok.Data;

@Data
public class Response<T> {
    private int status;
    private String message;
    private ResponseError error;
    private T data;

    private Response(Builder<T> builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.error = builder.error;
        this.data = builder.data;
    }

    public static class Builder<T> {
        private int status;
        private String message;
        private ResponseError error;
        private T data;

        public Builder<T> status(int status) {
            this.status = status;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> error(ResponseError error) {
            this.error = error;
            return this;
        }

        public Builder<T> error(int code, String message) {
            this.error = new ResponseError(code, message);
            return this;
        }

        public Builder<T> error(int code, String message, String description) {
            this.error = new ResponseError(code, message, description);
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Response<T> build() {
            return new Response<>(this);
        }
    }
}
