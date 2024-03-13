package pe.mil.ejercito.microservice.repositories.contracts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.EpUserEntity;

import java.util.Optional;

/**
 * IEpUserRepository
 * <p>
 * IEpUserRepository interface.
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
public interface IEpUserRepository extends JpaRepository<EpUserEntity, Long> {

    @NonNull
    @Query(value = "SELECT u FROM EpUserEntity u LEFT JOIN  FETCH u.usUserStatus LEFT JOIN FETCH u.usPerson LEFT JOIN FETCH u.usProfile WHERE u.id = :id")
    Optional<EpUserEntity> findById(@NonNull Long id);

    @Query(value = "SELECT u FROM EpUserEntity u LEFT JOIN  FETCH u.usUserStatus LEFT JOIN FETCH u.usPerson LEFT JOIN FETCH u.usProfile WHERE u.uuId = :uuId")
    Optional<EpUserEntity> findByUuId(String uuId);

    @Query(value = "SELECT u FROM EpUserEntity u " +
            "INNER JOIN FETCH u.usUserStatus us " +
            "INNER JOIN FETCH u.usPerson p " +
            "INNER JOIN FETCH u.usProfile pr " +
            "WHERE (:status is null or us.uuId = :status) " +
            "AND (:profile is null or pr.uuId = :profile) " +
            "AND (:person is null or p.uuId = :person) ",
            countQuery = "SELECT COUNT(u) FROM EpUserEntity u " +
                    "LEFT JOIN u.usUserStatus us " +
                    "LEFT JOIN u.usPerson p " +
                    "LEFT JOIN u.usProfile pr " +
                    "WHERE (:status is null or us.uuId = :status) " +
                    "AND (:profile is null or pr.uuId = :profile) " +
                    "AND (:person is null or p.uuId = :person) ")
    Page<EpUserEntity> findAll(@Param("status") String status, @Param("profile") String profile, @Param("person") String personId, Pageable pageable);
}