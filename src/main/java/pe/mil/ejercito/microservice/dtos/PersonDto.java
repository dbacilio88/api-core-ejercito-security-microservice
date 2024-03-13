package pe.mil.ejercito.microservice.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * PersonDto
 * <p>
 * PersonDto class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto implements Serializable {
    private static final long serialVersionUID = -194574853313200391L;

    private Long id;

    private String uuId;

    @Size(max = 50)
    @NotNull
    @NotBlank
    private String name;

    @Size(max = 50)
    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    private Instant dob;
}