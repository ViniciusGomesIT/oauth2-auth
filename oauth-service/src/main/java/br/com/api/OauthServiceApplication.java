package br.com.api;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.api.entity.Role;
import br.com.api.entity.User;
import br.com.api.reposiroty.UserRepository;


@SpringBootApplication
public class OauthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initUsers(userRepository, passwordEncoder);
		};
	}

	private void initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		User admin = new User();
		User user = new User();
		
		String emailAdmin = "vinigomes47@bol.com.br";
		String emailUser = "user_email@email.com";
		
		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");
		
		Role roleUser = new Role();
		roleUser.setName("ROLE_USER");
		
		admin.setEmail(emailAdmin);
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setRoles(Arrays.asList(roleAdmin));
		
		user.setEmail(emailUser);
		user.setPassword(passwordEncoder.encode("123456"));
		user.setRoles(Arrays.asList(roleUser));
		
		Optional<User> findAdmin = userRepository.findByEmailIgnoreCase(emailAdmin);
		Optional<User> findUser = userRepository.findByEmailIgnoreCase(emailUser);
		
		if ( !findAdmin.isPresent() ) {
			userRepository.save(admin);
		}
		
		if ( !findUser.isPresent() ) {
			userRepository.save(user);
		}
	}

}
