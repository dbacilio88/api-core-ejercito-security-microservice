package pe.mil.ejercito.microservice.dtos;

import com.bxcode.tools.loader.componets.annotations.Uuid;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * ProfileOptionModuleDto
 * <p>
 * ProfileOptionModuleDto class.
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
public class ProfileOptionModuleDto implements Serializable {
    private static final long serialVersionUID = 4321433544529760932L;
    private Long id;
    private String uuId;

    @Size(max = 36, min = 36)
    @NotNull
    @NotBlank
    @Uuid
    private String module;

    @Size(max = 36, min = 36)
    @NotNull
    @NotBlank
    @Uuid
    private String profile;

    @Size(max = 50)
    @NotNull
    @NotBlank
    private String privileges;
    private Instant createDate;
    private Instant updateDate;
}