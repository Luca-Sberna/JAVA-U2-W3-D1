package JAVAU2W3D1;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import JAVAU2W3D1.entities.Dispositivo;
import JAVAU2W3D1.payloads.DispositivoRegistrationPayload;
import JAVAU2W3D1.repository.DispositiviRepository;
import JAVAU2W3D1.repository.UsersRepository;
import JAVAU2W3D1.services.DispositiviService;
import JAVAU2W3D1.services.UsersService;
import JAVAU2W3D1.utils.StatoDispositivo;
import JAVAU2W3D1.utils.TipoDispositivo;

@Component
public class DispositiviRunnerFaker implements CommandLineRunner {
	@Autowired
	DispositiviService ds;

	@Autowired
	DispositiviRepository dr;

	@Autowired
	UsersRepository ur;

	@Autowired
	UsersService us;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));
		for (int i = 0; i < 3; i++) {
			try {
				String marca = faker.company().name();
				TipoDispositivo tipoDispositivo = faker.options().option(TipoDispositivo.class);
				StatoDispositivo statoDispositivo = faker.options().option(StatoDispositivo.class);
				UUID seriale = UUID.randomUUID();

				DispositivoRegistrationPayload dispositivo = new DispositivoRegistrationPayload(marca, tipoDispositivo,
						statoDispositivo, seriale);

				ds.create(dispositivo);

			} catch (Exception e) {
				System.out.println(e);
			}

		}

		// logica per la corretta assegnazione di uno stato ad un dispositivo assegnato
		// ad un utente

		List<Dispositivo> dispositivi = dr.findAll();
		Random random = new Random();
		for (Dispositivo dispositivo : dispositivi) {
			if (dispositivo.getUser() != null) {
				// Il dispositivo è assegnato a un utente
				// Imposta lo stato su IN_MANUTENZIONE o ASSEGNATO
				StatoDispositivo statoDispositivo = random.nextBoolean() ? StatoDispositivo.IN_MANUTENZIONE
						: StatoDispositivo.ASSEGNATO;
				dispositivo.setStatoDispositivo(statoDispositivo);
			} else {
				// Il dispositivo non è assegnato a nessun utente
				// Imposta lo stato su DISMESSO o DISPONIBILE
				StatoDispositivo statoDispositivo = random.nextBoolean() ? StatoDispositivo.DISMESSO
						: StatoDispositivo.DISPONIBILE;
				dispositivo.setStatoDispositivo(statoDispositivo);
			}
			dr.save(dispositivo);
		}

	}
}
