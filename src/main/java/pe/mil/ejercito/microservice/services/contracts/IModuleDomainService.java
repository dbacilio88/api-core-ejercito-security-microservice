package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.dto.PageableDto;
import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.ModuleDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IModuleDomainService
 * <p>
 * IModuleDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */
public interface IModuleDomainService extends
        IGetByIdDomainEntity<Mono<ModuleDto>, Long>,
        IGetByUuIdDomainEntity<Mono<ModuleDto>, String>,
        ISaveDomainEntity<Mono<ModuleDto>, ModuleDto>,
        IUpdateDomainEntity<Mono<ModuleDto>, ModuleDto>,
        IDeleteDomainEntity<Mono<ModuleDto>, String> {

    Mono<List<ModuleDto>> getAllEntities(String status, String limit, String page, PageableDto pageable);
}
