package JAVAU2W2PROJECT;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import JAVAU2W2PROJECT.payloads.UserRegistrationPayload;
import JAVAU2W2PROJECT.services.UsersService;

@Component
public class UsersRunnerFaker implements CommandLineRunner {
	@Autowired
	UsersService usersService;

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Faker faker = new Faker(new Locale("it"));
		for (int i = 0; i < 3; i++) {
			try {
				String username = faker.name().username();
				String name = faker.name().firstName();
				String surname = faker.name().lastName();
				String email = faker.internet().emailAddress();
				String password = faker.internet().password();
				String hashedPassword = passwordEncoder.encode(password);
				UserRegistrationPayload user = new UserRegistrationPayload(username, name, surname, email,
						hashedPassword);
				usersService.create(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
