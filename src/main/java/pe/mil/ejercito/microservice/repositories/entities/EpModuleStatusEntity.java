package pe.mil.ejercito.microservice.repositories.entities;

import lombok.*;

import javax.persistence.*;
/**
 * EpModuleStatusEntity
 * <p>
 * EpModuleStatusEntity class.
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
@Entity(name = "EpModuleStatusEntity")
@Table(name = "EP_MODULE_STATUS", indexes = {
        @Index(name = "EP_MODULE_STATUS_UN1", columnList = "MS_UUID", unique = true),
        @Index(name = "EP_MODULE_STATUS_UN2", columnList = "MS_CODE", unique = true),
        @Index(name = "EP_MODULE_STATUS_UN3", columnList = "MS_NAME", unique = true)
})
public class EpModuleStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EP_MODULE_STATUS_SEQ")
    @SequenceGenerator(name = "EP_MODULE_STATUS_SEQ", allocationSize = 1)
    @Column(name = "MS_ID", nullable = false)
    private Long id;

    @Column(name = "MS_UUID", nullable = false, length = 36)
    private String uuId;

    @Column(name = "MS_CODE", nullable = false, length = 30)
    private String msCode;

    @Column(name = "MS_NAME", nullable = false, length = 50)
    private String msName;

    @Column(name = "MS_DESCRIPTION", length = 100)
    private String msDescription;

}