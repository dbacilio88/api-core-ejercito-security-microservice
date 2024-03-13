package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.dto.PageableDto;
import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.UserDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IUserDomainService
 * <p>
 * IUserDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */
public interface IUserDomainService extends
        IGetByIdDomainEntity<Mono<UserDto>, Long>,
        IGetByUuIdDomainEntity<Mono<UserDto>, String>,
        ISaveDomainEntity<Mono<UserDto>, UserDto>,
        IUpdateDomainEntity<Mono<UserDto>, UserDto>,
        IDeleteDomainEntity<Mono<UserDto>, String> {

    Mono<List<UserDto>> getAllEntities(String status, String profile, String person, String limit, String page, PageableDto pageable);
}