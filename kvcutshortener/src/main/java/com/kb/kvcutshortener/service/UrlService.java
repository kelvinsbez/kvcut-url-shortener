package com.kb.kvcutshortener.service;

import com.kb.kvcutshortener.dto.UrlResponseDTO;
import com.kb.kvcutshortener.exception.UrlExpiredException;
import com.kb.kvcutshortener.exception.UrlNotFoundException;
import com.kb.kvcutshortener.model.UrlMapping;
import com.kb.kvcutshortener.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlService {

    private final UrlMappingRepository urlMappingRepository;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    public UrlService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }


    private String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(ALPHABET.length());
            code.append(ALPHABET.charAt(index));
        }
        return code.toString();
    }

    public UrlResponseDTO createShortenedURL(String url) {
        String code = generateCode();
        LocalDateTime date = LocalDateTime.now().plusDays(30);

        while (urlMappingRepository.findByCode(code).isPresent()) {
            code = generateCode();
        }

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setCode(code);
        urlMapping.setUrl(url);
        urlMapping.setExpirationDate(date);

        return new UrlResponseDTO(urlMappingRepository.save(urlMapping));
    }

    public String getOriginalUrl(String code) {
        UrlMapping urlMapping = urlMappingRepository.findByCode(code)
                .orElseThrow(()-> new UrlNotFoundException(code));

        if (LocalDateTime.now().isAfter(urlMapping.getExpirationDate())) {
            urlMappingRepository.deleteById(urlMapping.getId());
            throw new UrlExpiredException();
        }

        return urlMapping.getUrl();
    }
}
