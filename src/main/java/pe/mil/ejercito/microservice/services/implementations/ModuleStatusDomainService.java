package pe.mil.ejercito.microservice.services.implementations;

import com.bxcode.tools.loader.componets.enums.ProcessResult;
import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.componets.exceptions.CommonException;
import com.bxcode.tools.loader.componets.helpers.CommonRequestHelper;
import com.bxcode.tools.loader.services.base.ReactorServiceBase;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pe.mil.ejercito.microservice.components.mappers.IModuleStatusMapper;
import pe.mil.ejercito.microservice.components.validations.IModuleStatusValidation;
import pe.mil.ejercito.microservice.dtos.ModuleStatusDto;
import pe.mil.ejercito.microservice.repositories.contracts.IEpModuleStatusRepository;
import pe.mil.ejercito.microservice.repositories.entities.EpModuleStatusEntity;
import pe.mil.ejercito.microservice.services.contracts.IModuleStatusDomainService;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bxcode.tools.loader.constants.BaseLoggerServicesConstant.*;

/**
 * ModuleStatusDomainService
 * <p>
 * ModuleStatusDomainService class.
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
@Service
public class ModuleStatusDomainService extends ReactorServiceBase implements IModuleStatusDomainService {

    private final IEpModuleStatusRepository moduleStatusRepository;
    private final IModuleStatusMapper moduleStatusMapper;

    protected ModuleStatusDomainService(final IEpModuleStatusRepository moduleStatusRepository,
                                        final IModuleStatusMapper moduleStatusMapper) {
        super("ModuleStatusDomainService");
        this.moduleStatusRepository = moduleStatusRepository;
        this.moduleStatusMapper = moduleStatusMapper;
    }

    @Override
    public Mono<List<ModuleStatusDto>> getAllEntities() {
        final Iterable<EpModuleStatusEntity> moduleStatusEntities = this.moduleStatusRepository.findAll();
        final List<ModuleStatusDto> list = this.moduleStatusMapper.mapperToList(moduleStatusEntities);
        return Mono.just(list)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<ModuleStatusDto> getByIdEntity(Long id) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidId(id))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpModuleStatusEntity> findByIdModuleStatusEntity = this.moduleStatusRepository.findById(id);

        return getModuleStatusDto(
                findByIdModuleStatusEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<ModuleStatusDto> getByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
        }

        final Optional<EpModuleStatusEntity> findByUuIdModuleStatusEntity = this.moduleStatusRepository.findByUuId(uuId);

        return getModuleStatusDto(
                findByUuIdModuleStatusEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<ModuleStatusDto> saveEntity(ModuleStatusDto dto) {
        return doOnSave(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.info(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<ModuleStatusDto> updateEntity(ModuleStatusDto dto) {
        return doOnUpdate(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    @Override
    public Mono<ModuleStatusDto> deleteByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpModuleStatusEntity> deleteModuleStatusEntity = this.moduleStatusRepository.findByUuId(uuId);

        if (deleteModuleStatusEntity.isEmpty()) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
        }

        this.moduleStatusRepository.delete(deleteModuleStatusEntity.get());
        return Mono.just(this.moduleStatusMapper.mapperToDto(deleteModuleStatusEntity.get()))
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<ModuleStatusDto> getModuleStatusDto(Optional<EpModuleStatusEntity> bsEntity, String successMessage, String messageExist) {
        return bsEntity.map(moduleStatusEntity -> Mono.just(this.moduleStatusMapper.mapperToDto(moduleStatusEntity))
                        .doOnSuccess(success -> log.debug(successMessage))
                        .doOnError(throwable -> log.error(throwable.getMessage())))
                .orElseGet(() -> Mono.error(() -> {
                    log.error(messageExist);
                    return new CommonException(messageExist, ResponseEnum.NOT_FOUNT_ENTITY);
                }));
    }

    private Mono<ModuleStatusDto> doOnSave(ModuleStatusDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {
                    final EpModuleStatusEntity saveModuleStatusEntity = this.moduleStatusMapper.mapperToEntity(request);
                    saveModuleStatusEntity.setUuId(UUID.randomUUID().toString());
                    final EpModuleStatusEntity entityResult = this.moduleStatusRepository.save(saveModuleStatusEntity);
                    return Mono.just(this.moduleStatusMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<ModuleStatusDto> doOnUpdate(ModuleStatusDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(request.getUuId()))) {
                        log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
                    }

                    final Optional<EpModuleStatusEntity> updateModuleStatusEntity = this.moduleStatusRepository.findByUuId(request.getUuId());
                    if (updateModuleStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final EpModuleStatusEntity entityUpdate = updateModuleStatusEntity.get();
                    entityUpdate.setMsCode(request.getCode());
                    entityUpdate.setMsName(request.getName());
                    entityUpdate.setMsDescription(request.getDescription());
                    final EpModuleStatusEntity entityResult = this.moduleStatusRepository.save(entityUpdate);
                    return Mono.just(this.moduleStatusMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<ModuleStatusDto> doOnValidateResponse(ModuleStatusDto dto) {
        return IModuleStatusValidation.doOnValidationResponse().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_ERROR, ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}


