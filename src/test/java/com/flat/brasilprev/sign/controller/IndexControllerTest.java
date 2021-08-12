package com.flat.brasilprev.sign.controller;

import com.flat.brasilprev.sign.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(IndexController.class)
class IndexControllerTest {

    @InjectMocks
    @Autowired
    private IndexController indexController;

    @MockBean
    private UserRepository userRepository;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    void index() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void indexPost() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/")
                .param("nome", "Rodrigo")
                .param("senha", "1234")
                .param("cpf", "123.456.789-10")
                .param("cep", "64066-000");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("message"));
    }

    @Test
    void indexPost_IncorrectData() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/")
                .param("nome", "")
                .param("senha", "")
                .param("cpf", "")
                .param("cep", "");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("nomeError", "Nome muito curto"))
                .andExpect(model().attribute("cpfError", "CPF inválido"))
                .andExpect(model().attribute("senhaError", "Senha muito curta"));
    }
}