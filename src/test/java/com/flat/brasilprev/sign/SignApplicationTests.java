package com.flat.brasilprev.sign;

import com.flat.brasilprev.sign.controller.CepController;
import com.flat.brasilprev.sign.controller.IndexController;
import com.flat.brasilprev.sign.controller.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
class SignApplicationTests {

	@Autowired
	private IndexController indexController;

	@Autowired
	private LoginController loginController;

	@Autowired
	private CepController cepController;

	@Test
	void contextLoads() {
		assertThat(indexController).isNotNull();
		assertThat(loginController).isNotNull();
		assertThat(cepController).isNotNull();
	}

}
