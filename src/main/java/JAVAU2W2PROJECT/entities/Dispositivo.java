package JAVAU2W2PROJECT.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import JAVAU2W2PROJECT.utils.StatoDispositivo;
import JAVAU2W2PROJECT.utils.TipoDispositivo;
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

	public Dispositivo(String marca, TipoDispositivo tipoDispositivo, StatoDispositivo statoDispositivo) {
		this.marca = marca;
		this.tipoDispositivo = tipoDispositivo;
		this.statoDispositivo = statoDispositivo;
		this.user = null;
	}

}
