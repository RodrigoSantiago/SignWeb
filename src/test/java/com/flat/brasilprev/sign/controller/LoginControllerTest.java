package com.flat.brasilprev.sign.controller;

import com.flat.brasilprev.sign.model.User;
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

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @InjectMocks
    @Autowired
    private LoginController indexController;

    @MockBean
    private UserRepository userRepository;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(indexController).build();

        Mockito.when(userRepository.findByNomeAndSenha("Rodrigo", "1234"))
                .thenAnswer(a -> {
                    User user = new User();
                    user.setNome("Rodrigo");
                    user.setSenha("1234");
                    user.setCep("64066-000");
                    user.setCpf("123.456.789-10");
                    user.setBairro("Bairro");
                    user.setCidade("Cidade");
                    user.setUf("UF");
                    return Collections.singletonList(user);
                });
    }

    @Test
    void login() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/login");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    void loginPost() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/login")
                .param("nome", "Rodrigo")
                .param("senha", "1234");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("message"));
    }

    @Test
    void loginPost_IncorrectPassword() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/login")
                .param("nome", "Rodrigo")
                .param("senha", "4321");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attribute("nomeError", "Nome e senha não correspondem ao login"))
                .andExpect(view().name("signup"));
    }
}