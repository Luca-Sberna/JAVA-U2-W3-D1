package JAVAU2W3D1.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import JAVAU2W3D1.converters.SerialeConverter;
import JAVAU2W3D1.entities.Dispositivo;
import JAVAU2W3D1.payloads.DispositivoRegistrationPayload;
import JAVAU2W3D1.repository.DispositiviRepository;
import JAVAU2W3D1.repository.UsersRepository;

@Service
public class DispositiviService {
	@Autowired
	private DispositiviRepository dr;

	@Autowired
	UsersRepository ur;

	@Autowired
	private SerialeConverter converter;

	public Dispositivo create(DispositivoRegistrationPayload d) {
		UUID seriale = d.getSeriale();
		String serialeString = seriale.toString();
		String serialeCodificato = converter.convertToDatabaseColumn(serialeString);

		Dispositivo nuovoDispositivo = new Dispositivo(d.getMarca(), d.getTipoDispositivo(), d.getStatoDispositivo(),
				serialeCodificato);
		return dr.save(nuovoDispositivo);
	}

	public List<Dispositivo> find() {
		return dr.findAll();
	}

	public Dispositivo findById(UUID id) throws Exception {
		return dr.findById(id).orElseThrow(() -> new Exception("postazione non trovata"));
	}

	public Dispositivo findByIdAndUpdate(UUID id, DispositivoRegistrationPayload body) throws Exception {
		Dispositivo found = this.findById(id);
		found.setMarca(body.getMarca());
		found.setStatoDispositivo(body.getStatoDispositivo());
		found.setTipoDispositivo(body.getTipoDispositivo());
		return dr.save(found);

	}

	public void findByIdAndDelete(UUID id) throws Exception {
		Dispositivo found = this.findById(id);
		dr.delete(found);
	}

	public long count() {
		return dr.count();
	}

	public Page<Dispositivo> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sortBy));
		return dr.findAll(pageable);
	}

	public Dispositivo saveDispositivo(DispositivoRegistrationPayload body) {
		return saveDispositivo(body);
	}

}
