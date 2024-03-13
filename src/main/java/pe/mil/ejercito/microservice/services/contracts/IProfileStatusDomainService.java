package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.ProfileStatusDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IProfileStatusDomainService
 * <p>
 * IProfileStatusDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */
public interface IProfileStatusDomainService extends
        IGetAllDomainEntity<Mono<List<ProfileStatusDto>>>,
        IGetByIdDomainEntity<Mono<ProfileStatusDto>, Long>,
        IGetByUuIdDomainEntity<Mono<ProfileStatusDto>, String>,
        ISaveDomainEntity<Mono<ProfileStatusDto>, ProfileStatusDto>,
        IUpdateDomainEntity<Mono<ProfileStatusDto>, ProfileStatusDto>,
        IDeleteDomainEntity<Mono<ProfileStatusDto>,String> {
}