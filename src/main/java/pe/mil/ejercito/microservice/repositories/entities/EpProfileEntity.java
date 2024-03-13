package pe.mil.ejercito.microservice.repositories.entities;

import lombok.*;

import javax.persistence.*;
/**
 * EpProfileEntity
 * <p>
 * EpProfileEntity class.
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
@Entity(name = "EpProfileEntity")
@Table(name = "EP_PROFILE", indexes = {
        @Index(name = "EP_PROFILE_UN1", columnList = "PR_UUID", unique = true)
})
public class EpProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EP_PROFILE_SEQ")
    @SequenceGenerator(name = "EP_PROFILE_SEQ", sequenceName = "EP_PROFILE_SEQ", allocationSize = 1)
    @Column(name = "PR_ID", nullable = false)
    private Long id;

    @Column(name = "PR_UUID", nullable = false, length = 36)
    private String uuId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PR_PROFILE_STATUS", nullable = false)
    @ToString.Exclude
    private EpProfileStatusEntity prProfileStatus;

    @Column(name = "PR_NAME", nullable = false, length = 50)
    private String prName;

}