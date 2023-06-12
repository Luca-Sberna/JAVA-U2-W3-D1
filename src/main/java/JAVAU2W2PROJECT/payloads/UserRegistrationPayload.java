package JAVAU2W2PROJECT.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationPayload {
	@NotNull(message = "Lo username è obbligatorio")
	String username;
	@NotNull(message = "Il nome è obbligatorio")
	@Size(min = 3, max = 30, message = "Nome min 3 caratteri, massimo 30")
	String name;
	@NotNull(message = "Il cognome è obbligatorio")
	String surname;
	@Email(message = "Non hai inserito un indirizzo email valido")
	String email;
	@NotNull(message = "La password è obbligatoria")
	String password;

	public UserRegistrationPayload(@NotNull(message = "Lo username è obbligatorio") String username,
			@NotNull(message = "Il nome è obbligatorio") @Size(min = 3, max = 30, message = "Nome min 3 caratteri, massimo 30") String name,
			@NotNull(message = "Il cognome è obbligatorio") String surname,
			@Email(message = "Non hai inserito un indirizzo email valido") String email,
			@NotNull(message = "La password è obbligatoria") String password) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
	}

}