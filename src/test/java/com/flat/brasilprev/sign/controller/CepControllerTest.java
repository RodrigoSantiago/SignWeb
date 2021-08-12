package com.flat.brasilprev.sign.controller;

import com.flat.brasilprev.sign.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CepController.class)
class CepControllerTest {

    @InjectMocks
    @Autowired
    private CepController cepController;

    @MockBean
    private RestTemplate restTemplate;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(cepController).build();
        cepController.setRestTemplate(restTemplate);

        Mockito.when(restTemplate.getForObject("https://viacep.com.br/ws/12345000/json/", String.class))
                .thenAnswer(a -> {
                    return "{Valid CEP}";
                });
    }

    @Test
    void readCep() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/cep");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));
    }

    @Test
    void readCep_CorrectValue() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/cep?address=12345000");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{Valid CEP}"));
    }

    @Test
    void readCep_IncorrectValue() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/cep?address=000");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));
    }
}