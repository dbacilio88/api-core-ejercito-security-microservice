package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.dto.PageableDto;
import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.ProfileOptionModuleDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IProfileOptionModuleDomainService
 * <p>
 * IProfileOptionModuleDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */
public interface IProfileOptionModuleDomainService extends
        IGetByIdDomainEntity<Mono<ProfileOptionModuleDto>, Long>,
        IGetByUuIdDomainEntity<Mono<ProfileOptionModuleDto>, String>,
        ISaveDomainEntity<Mono<ProfileOptionModuleDto>, ProfileOptionModuleDto>,
        IUpdateDomainEntity<Mono<ProfileOptionModuleDto>, ProfileOptionModuleDto>,
        IDeleteDomainEntity<Mono<ProfileOptionModuleDto>, String> {

    Mono<List<ProfileOptionModuleDto>> getAllEntities(String module, String profile, String limit, String page, PageableDto pageable);
}
