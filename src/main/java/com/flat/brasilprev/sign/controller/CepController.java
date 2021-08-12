package com.flat.brasilprev.sign.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
public class CepController {

    private RestTemplate restTemplate;

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CepController() {
        restTemplate = new RestTemplate();
    }

    @GetMapping("/cep")
    public ResponseEntity<String> readCep(Optional<String> address) {
        if (address.isPresent() && address.get().matches("[0-9]{8}")) {
            String response = restTemplate.getForObject("https://viacep.com.br/ws/" + address.get() + "/json/", String.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
