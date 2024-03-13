package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.dto.PageableDto;
import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.PersonDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IPersonDomainService
 * <p>
 * IPersonDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */
public interface IPersonDomainService extends
        IGetByIdDomainEntity<Mono<PersonDto>, Long>,
        IGetByUuIdDomainEntity<Mono<PersonDto>, String>,
        ISaveDomainEntity<Mono<PersonDto>, PersonDto>,
        IUpdateDomainEntity<Mono<PersonDto>, PersonDto>,
        IDeleteDomainEntity<Mono<PersonDto>, String> {

    Mono<List<PersonDto>> getAllEntities(String limit, String page, PageableDto pageable);
}