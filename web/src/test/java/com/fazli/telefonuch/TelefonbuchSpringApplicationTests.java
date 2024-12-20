package com.fazli.telefonuch;

import com.fazli.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TelefonbuchSpringApplicationTests {

	@Autowired
	private HomeController homeController;
	@Test
	void contextLoads()  throws Exception {
		assertThat(homeController).isNotNull();
	}

}
