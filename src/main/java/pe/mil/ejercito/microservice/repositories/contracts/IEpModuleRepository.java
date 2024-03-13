package pe.mil.ejercito.microservice.repositories.contracts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.EpModuleEntity;

import java.util.Optional;

/**
 * IEpModuleRepository
 * <p>
 * IEpModuleRepository interface.
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
public interface IEpModuleRepository extends JpaRepository<EpModuleEntity, Long> {

    @Query(value = "SELECT m FROM EpModuleEntity m LEFT JOIN FETCH m.moModuleStatus WHERE m.uuId = :uuId")
    Optional<EpModuleEntity> findByUuId(String uuId);
    @NonNull
    @Query(value = "SELECT m FROM EpModuleEntity m LEFT JOIN FETCH m.moModuleStatus WHERE m.id = :id")
    Optional<EpModuleEntity> findById(@NonNull Long id);

    @Query(value = "SELECT m FROM EpModuleEntity m " +
            "INNER JOIN FETCH m.moModuleStatus s " +
            "WHERE (:status is null or s.uuId = :status) ",
            countQuery = "SELECT COUNT(m) FROM EpModuleEntity m " +
                    "LEFT JOIN m.moModuleStatus s " +
                    "WHERE (:status is null or s.uuId = :status) ")
    Page<EpModuleEntity> findAll(final @Param("status") String status, Pageable pageable);
}