package JAVAU2W3D1.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JAVAU2W3D1.entities.Dispositivo;

@Repository
public interface DispositiviRepository extends JpaRepository<Dispositivo, UUID> {
	List<Dispositivo> findByUserId(UUID userId);

}
