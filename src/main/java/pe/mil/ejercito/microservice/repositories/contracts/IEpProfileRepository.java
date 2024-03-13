package pe.mil.ejercito.microservice.repositories.contracts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.EpProfileEntity;

import java.util.Optional;

/**
 * IEpProfileRepository
 * <p>
 * IEpProfileRepository interface.
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
public interface IEpProfileRepository extends JpaRepository<EpProfileEntity, Long> {

    @NonNull
    @Query(value = "SELECT p FROM EpProfileEntity p INNER JOIN FETCH p.prProfileStatus WHERE p.id = :id")
    Optional<EpProfileEntity> findById(@NonNull Long id);

    @Query(value = "SELECT p FROM EpProfileEntity p INNER JOIN FETCH p.prProfileStatus WHERE p.uuId = :uuId")
    Optional<EpProfileEntity> findByUuId(String uuId);
    @Query(value = "SELECT p FROM EpProfileEntity p " +
            "INNER JOIN FETCH p.prProfileStatus s " +
            "WHERE (:status is null or s.uuId = :status) ",
            countQuery = "SELECT COUNT(p) FROM EpProfileEntity p " +
                    "LEFT JOIN p.prProfileStatus s " +
                    "WHERE (:status is null or s.uuId = :status) ")
    Page<EpProfileEntity> findAll(@Param("status") String status, Pageable pageable);
}