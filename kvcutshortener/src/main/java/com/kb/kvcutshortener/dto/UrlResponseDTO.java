package com.kb.kvcutshortener.dto;


import com.kb.kvcutshortener.model.UrlMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponseDTO {

    private String urlMontada;

    public UrlResponseDTO(UrlMapping urlMapping) {
        this.urlMontada = "http://localhost:8080/"+ urlMapping.getCode();
    }
}
