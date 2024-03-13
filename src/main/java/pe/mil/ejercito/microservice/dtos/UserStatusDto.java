package pe.mil.ejercito.microservice.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * UserStatusDto
 * <p>
 * UserStatusDto class.
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
public class UserStatusDto implements Serializable {
    private static final long serialVersionUID = 1444453970857077107L;

    private Long id;

    private String uuId;

    @Size(max = 30)
    @NotNull
    @NotBlank
    private String code;

    @Size(max = 50)
    @NotNull
    @NotBlank
    private String name;

    @Size(max = 100)
    private String description;
}