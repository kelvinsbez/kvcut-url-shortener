package com.kb.kvcutshortener.controller;

import com.kb.kvcutshortener.dto.UrlRequestDTO;
import com.kb.kvcutshortener.dto.UrlResponseDTO;
import com.kb.kvcutshortener.model.UrlMapping;
import com.kb.kvcutshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class UrlMappingController {

    private final UrlService urlService;

    @PostMapping(path = "/api/urls")
    public ResponseEntity<UrlResponseDTO> createShortenedUrl(@RequestBody UrlRequestDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(urlService.createShortenedURL(body.getUrl()));
    }

    @GetMapping(value = "/{code}")
    public ResponseEntity<Void> getRediracted(@PathVariable String code) {
        String url = urlService.getOriginalUrl(code);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }

}
