package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.dto.PageableDto;
import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.ProfileDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IProfileDomainService
 * <p>
 * IProfileDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */
public interface IProfileDomainService extends
        IGetByIdDomainEntity<Mono<ProfileDto>, Long>,
        IGetByUuIdDomainEntity<Mono<ProfileDto>, String>,
        ISaveDomainEntity<Mono<ProfileDto>, ProfileDto>,
        IUpdateDomainEntity<Mono<ProfileDto>, ProfileDto>,
        IDeleteDomainEntity<Mono<ProfileDto>, String> {

    Mono<List<ProfileDto>> getAllEntities(String status, String limit, String page, PageableDto pageable);
}