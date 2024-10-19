package com.picpaysimplificado;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class PicpaysimplificadoApplicationTests {
	@Mock
	ApplicationContext applicationContext;

	@Test
	void applicationContext() throws Exception {
		PicpaysimplificadoApplication.main(new String[]{});
	}

}
