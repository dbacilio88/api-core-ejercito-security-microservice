package pe.mil.ejercito.microservice.controllers.implementations;

import com.bxcode.tools.loader.controllers.base.ReactorControllerBase;
import com.bxcode.tools.loader.dto.GenericResponse;
import com.bxcode.tools.loader.dto.ProcessResponse;
import com.bxcode.tools.loader.dto.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import pe.mil.ejercito.microservice.controllers.contracts.IUserStatusController;
import pe.mil.ejercito.microservice.dtos.UserStatusDto;
import pe.mil.ejercito.microservice.services.contracts.IUserStatusDomainService;
import reactor.core.publisher.Mono;

import static pe.mil.ejercito.microservice.constants.LoggerConstant.*;
import static pe.mil.ejercito.microservice.constants.ProcessConstant.*;

/**
 * UserStatusController
 * <p>
 * UserStatusController class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */
@Log4j2
@RestController
@RequestMapping(path = MICROSERVICE_PATH_CONTEXT, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserStatusController extends ReactorControllerBase implements IUserStatusController {
    private final IUserStatusDomainService service;

    public UserStatusController(Response response, IUserStatusDomainService service) {
        super(response, "ModuleStatusController");
        this.service = service;
    }


    @Override
    @GetMapping(path = FIND_ALL_USER_STATUS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> doOnFindAllExecute() {
        return this.service.getAllEntities()
                .flatMap(current -> super.response(ProcessResponse.success(new GenericResponse<>(current))))
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.info(MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    @GetMapping(path = FIND_BY_ID_USER_STATUS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> doOnFindByIdExecute(@PathVariable(value = "statusId") Long id) {
        return this.service.getByIdEntity(id)
                .flatMap(current -> super.response(ProcessResponse.success(new GenericResponse<>(current))))
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.info(MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    @GetMapping(path = FIND_BY_UUID_USER_STATUS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> doOnFindByUuIdExecute(@PathVariable(value = "uuId") String uuId) {
        return this.service.getByUuIdEntity(uuId)
                .flatMap(current -> super.response(ProcessResponse.success(new GenericResponse<>(current))))
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.info(MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    @PostMapping(path = CREATE_USER_STATUS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> doOnCreateExecute(UserStatusDto dto) {
        return this.service.saveEntity(dto)
                .flatMap(current -> super.response(ProcessResponse.success(new GenericResponse<>(current))))
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.info(MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_CREATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    @PutMapping(path = UPDATE_USER_STATUS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> doOnUpdateExecute(UserStatusDto dto) {
        return this.service.updateEntity(dto)
                .flatMap(current -> super.response(ProcessResponse.success(new GenericResponse<>(current))))
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.info(MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    @DeleteMapping(path = DELETE_USER_STATUS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> doOnDeleteExecute(@PathVariable(value = "uuId") String uuId) {
        return this.service.deleteByUuIdEntity(uuId)
                .flatMap(current -> super.response(ProcessResponse.success(new GenericResponse<>(current))))
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.info(MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_DELETE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}


