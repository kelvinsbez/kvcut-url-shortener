package com.kb.kvcutshortener.exception;

public class UrlExpiredException extends RuntimeException {

    public UrlExpiredException() {
        super("Url expirada.");
    }
}
