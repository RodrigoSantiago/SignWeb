package com.flat.brasilprev.sign;

import com.flat.brasilprev.sign.model.User;
import com.flat.brasilprev.sign.model.UserRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void userSave() {
        User user = new User();
        user.setNome("Rodrigo");
        user.setSenha("1234");
        user.setCpf("123.456.789-10");
        user.setCep("64066-000");
        user.setCidade("Cidade");
        user.setBairro("Bairro");
        user.setUf("UF");
        userRepository.save(user);

        List<User> users = userRepository.findByNome("Rodrigo");
        assertThat(users.size()).as("Usuário não encontrado no banco").isEqualTo(1);
        assertThat(users.get(0).getNome()).as("Usuário incorreto").isEqualTo("Rodrigo");
    }
}
