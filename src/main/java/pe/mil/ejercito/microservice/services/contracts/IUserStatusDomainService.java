package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.UserStatusDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IUserStatusDomainService
 * <p>
 * IUserStatusDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */
public interface IUserStatusDomainService extends
        IGetAllDomainEntity<Mono<List<UserStatusDto>>>,
        IGetByIdDomainEntity<Mono<UserStatusDto>, Long>,
        IGetByUuIdDomainEntity<Mono<UserStatusDto>, String>,
        ISaveDomainEntity<Mono<UserStatusDto>, UserStatusDto>,
        IUpdateDomainEntity<Mono<UserStatusDto>, UserStatusDto>,
        IDeleteDomainEntity<Mono<UserStatusDto>, String> {
}
