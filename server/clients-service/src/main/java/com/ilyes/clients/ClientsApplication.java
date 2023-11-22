package com.ilyes.clients;

import com.ilyes.clients.entities.Client;
import com.ilyes.clients.entities.Region;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class ClientsApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(ClientsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repositoryRestConfiguration.exposeIdsFor(Client.class, Region.class);
	}

}
