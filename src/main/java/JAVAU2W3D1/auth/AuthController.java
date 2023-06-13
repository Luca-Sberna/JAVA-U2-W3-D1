package JAVAU2W3D1.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JAVAU2W3D1.auth.payloads.AuthenticationSuccessfullPayload;
import JAVAU2W3D1.entities.User;
import JAVAU2W3D1.exceptions.UnauthorizedException;
import JAVAU2W3D1.payloads.UserLoginPayload;
import JAVAU2W3D1.payloads.UserRegistrationPayload;
import JAVAU2W3D1.services.UsersService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UsersService usersService;

	@Autowired
	private PasswordEncoder bcrypt;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Validated UserRegistrationPayload body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		User createdUser = usersService.create(body);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationSuccessfullPayload> login(@RequestBody UserLoginPayload body)
			throws NotFoundException {

		// verifico che esiste l'email nel db
		// se esiste controllo che la password e l'utente combacino con l email inserita
		// se combaciano generare un token
		// se fallisce lanciare un eccezione credenziali non valide
		User user = usersService.findByEmail(body.getEmail());

//		if (!body.getPassword().matches(user.getPassword()))
//		throw new UnauthorizedException("Credenziali non valide");
		String plainPW = body.getPassword(); // "1234"
		String hashedPW = user.getPassword(); // "$2a$10$ML/ZNVOjSJl0bOlrpcxeu.ZUq6SraGO1/oKJG4LQFAf8o5ef3leUS"

		if (!bcrypt.matches(plainPW, hashedPW))
			throw new UnauthorizedException("Credenziali non valide");
//		// 3. Se tutto ok --> genero il token
		String token = JWTTools.createToken(user);
		// 4. Altrimenti --> 401 ("Credenziali non valide")

		return new ResponseEntity<>(new AuthenticationSuccessfullPayload(token), HttpStatus.OK);
	}

}