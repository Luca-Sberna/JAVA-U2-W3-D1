package JAVAU2W3D1.payloads;

import java.util.UUID;

import JAVAU2W3D1.utils.StatoDispositivo;
import JAVAU2W3D1.utils.TipoDispositivo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DispositivoRegistrationPayload {
	@NotNull(message = "La marca è obbligatoria")
	@Size(min = 3, max = 30, message = "Marca min 3 caratteri, massimo 30")
	String marca;

	@NotNull(message = "Il tipo di dispositivo è obbligatorio")
	TipoDispositivo tipoDispositivo;

	@NotNull(message = "Lo stato del dispositivo è obbligatorio")
	StatoDispositivo statoDispositivo;

	UUID seriale;

	public DispositivoRegistrationPayload(
			@NotNull(message = "La marca è obbligatoria") @Size(min = 3, max = 30, message = "Marca min 3 caratteri, massimo 30") String marca,
			@NotNull(message = "Il tipo di dispositivo è obbligatorio") TipoDispositivo tipoDispositivo,
			@NotNull(message = "Lo stato del dispositivo è obbligatorio") StatoDispositivo statoDispositivo,
			@NotNull(message = "Il seriale del dispositivo è obbligatorio") UUID seriale) {
		super();
		this.marca = marca;
		this.tipoDispositivo = tipoDispositivo;
		this.statoDispositivo = statoDispositivo;
		this.seriale = seriale;
	}

}
