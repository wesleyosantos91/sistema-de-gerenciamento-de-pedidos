package io.github.wesleyosantos91;

import org.springframework.boot.SpringApplication;

public class TestModuloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
