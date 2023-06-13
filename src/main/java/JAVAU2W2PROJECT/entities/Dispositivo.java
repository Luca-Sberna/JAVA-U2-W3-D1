package JAVAU2W2PROJECT.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import JAVAU2W2PROJECT.converters.SerialeConverter;
import JAVAU2W2PROJECT.utils.StatoDispositivo;
import JAVAU2W2PROJECT.utils.TipoDispositivo;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "dispositivi")
@NoArgsConstructor
@JsonIgnoreProperties({ "seriale" })
public class Dispositivo {
	@Id
	@GeneratedValue
	private UUID id;
	private String marca;
	@Enumerated(EnumType.STRING)
	private TipoDispositivo tipoDispositivo;
	@Enumerated(EnumType.STRING)
	private StatoDispositivo statoDispositivo;

	@ManyToOne
	@JsonIgnore
	private User user;

	@Convert(converter = SerialeConverter.class)
	private UUID seriale;

	public Dispositivo(String marca, TipoDispositivo tipoDispositivo, StatoDispositivo statoDispositivo, UUID seriale) {
		this.marca = marca;
		this.tipoDispositivo = tipoDispositivo;
		this.statoDispositivo = statoDispositivo;
		this.user = null;
		this.seriale = seriale;
	}

}
