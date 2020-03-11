package com.interviewrestapi.errors;

import java.util.*;

public class ApiError {

    private final String apiVersion;
    private final ErrorBlock error;

    public ApiError(final String apiVersion, final String code, final String message, final String domain,
                    final String reason, final String errorMessage) {
        this.apiVersion = apiVersion;
        this.error = new ErrorBlock(code, message, domain, reason, errorMessage);
    }

    public static ApiError fromDefaultAttributeMap(final String apiVersion,
                                                   final Map<String, Object> defaultErrorAttributes) {
        // original attribute values are documented in org.springframework.boot.web.servlet.error.DefaultErrorAttributes
        return new ApiError(
                apiVersion,
                ((Integer) defaultErrorAttributes.get("status")).toString(),
                (String) defaultErrorAttributes.getOrDefault("message", "no message available"),
                (String) defaultErrorAttributes.getOrDefault("path", "no domain available"),
                (String) defaultErrorAttributes.getOrDefault("error", "no reason available"),
                (String) defaultErrorAttributes.get("message")
        );
    }

    // utility method to return a map of serialized root attributes,
    // see the last part of the guide for more details
    public Map<String, Object> toAttributeMap() {
        Map<String, Object> apiVersion = new HashMap<>();
        apiVersion.put("apiVersion", this.apiVersion);
        apiVersion.put("error", error);
        return apiVersion;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public ErrorBlock getError() {
        return error;
    }

    private static final class ErrorBlock {

        private final String code;
        private final String message;
        private final List<Error> errors;

        public ErrorBlock(final String code, final String message, final String domain,
                          final String reason, final String errorMessage) {
            this.code = code;
            this.message = message;
            this.errors = Arrays.asList(new Error(domain, reason, errorMessage));
        }

        private ErrorBlock(final String code, final String message, final List<Error> errors) {
            this.code = code;
            this.message = message;
            this.errors = errors;
        }

        public static ErrorBlock copyWithMessage(final ErrorBlock s, final String message) {
            return new ErrorBlock(s.code, message, s.errors);
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public List<Error> getErrors() {
            return errors;
        }

    }

    private static final class Error {
        private final String domain;
        private final String reason;
        private final String message;

        public Error(final String domain, final String reason, final String message) {
            this.domain = domain;
            this.reason = reason;
            this.message = message;
        }

        public String getDomain() {
            return domain;
        }

        public String getReason() {
            return reason;
        }

        public String getMessage() {
            return message;
        }
    }
}