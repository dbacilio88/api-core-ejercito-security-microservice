package pe.mil.ejercito.microservice.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.EpPersonEntity;

import java.util.Optional;

/**
 * IEpPersonRepository
 * <p>
 * IEpPersonRepository interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */
@Repository
public interface IEpPersonRepository extends JpaRepository<EpPersonEntity, Long> {

    Optional<EpPersonEntity> findByUuId(String uuId);
}