package com.kb.kvcutshortener.exception;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String code) {
        super("Código não encontrado de: " + code);
    }
}
