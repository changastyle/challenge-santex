package com.santex.profile;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.RedirectView;

@SpringBootApplication
@Configuration
@EntityScan({"com.santex.profile.model"})
@EnableJpaRepositories("com.santex.profile.repo")
@EnableWebMvc
@Controller
@Slf4j
public class ProfileAPI
{
	public static ApplicationContext appContext;
	public static Environment environment;

	public static void main(String[] args) 
	{
		SpringApplication.run(ProfileAPI.class, args);
		String puertoServer = environment.getProperty("local.server.port");
		log.info("CORRIENDO PROFILE MANAGER API EN http://localhost:" + puertoServer + "/api");
		log.info("CORRIENDO PROFILE MANAGER WEB EN http://localhost:" + puertoServer + "/web");

	}

	@RequestMapping("/web")
	public String web()
	{
		return "index";
	}

	@GetMapping(value = "/api")
	public static RedirectView init() {
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("swagger-ui.html");

		return redirectView;
	}


	public static ApplicationContext dameAppContext() {
		return appContext;
	}

	@Autowired
	public void setearEnvironment(Environment environment) {
		ProfileAPI.environment = environment;
	}


}
