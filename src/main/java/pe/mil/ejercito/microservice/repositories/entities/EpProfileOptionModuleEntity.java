package pe.mil.ejercito.microservice.repositories.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
/**
 * EpProfileOptionModuleEntity
 * <p>
 * EpProfileOptionModuleEntity class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "EpProfileOptionModuleEntity")
@Table(name = "EP_PROFILE_OPTION_MODULE", indexes = {
        @Index(name = "EP_PROFILE_OPTION_MODULE_UN1", columnList = "POS_UUID", unique = true)
})
public class EpProfileOptionModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EP_PROFILE_OPTION_MODULE_SEQ")
    @SequenceGenerator(name = "EP_PROFILE_OPTION_MODULE_SEQ", sequenceName = "EP_PROFILE_OPTION_MODULE_SEQ", allocationSize = 1)
    @Column(name = "POS_ID", nullable = false)
    private Long id;

    @Column(name = "POS_UUID", nullable = false, length = 36)
    private String uuId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "POS_MODULE_ID", nullable = false)
    @ToString.Exclude
    private EpModuleEntity posModule;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "POS_PROFILE_ID", nullable = false)
    @ToString.Exclude
    private EpProfileEntity posProfile;

    @Column(name = "POS_PRIVILEGES", nullable = false, length = 50)
    private String posPrivileges;

    @Column(name = "POS_CREATE_DATE", nullable = false)
    private Instant posCreateDate;

    @Column(name = "POS_UPDATE_DATE")
    private Instant posUpdateDate;

}