package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.ModuleStatusDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IModuleStatusDomainService
 * <p>
 * IModuleStatusDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */
public interface IModuleStatusDomainService extends
        IGetAllDomainEntity<Mono<List<ModuleStatusDto>>>,
        IGetByIdDomainEntity<Mono<ModuleStatusDto>, Long>,
        IGetByUuIdDomainEntity<Mono<ModuleStatusDto>, String>,
        ISaveDomainEntity<Mono<ModuleStatusDto>, ModuleStatusDto>,
        IUpdateDomainEntity<Mono<ModuleStatusDto>, ModuleStatusDto>,
        IDeleteDomainEntity<Mono<ModuleStatusDto>,String>{
}
