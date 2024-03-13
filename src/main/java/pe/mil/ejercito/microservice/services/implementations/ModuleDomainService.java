package pe.mil.ejercito.microservice.services.implementations;

import com.bxcode.tools.loader.componets.enums.ProcessResult;
import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.componets.exceptions.CommonException;
import com.bxcode.tools.loader.componets.helpers.CommonRequestHelper;
import com.bxcode.tools.loader.dto.PageableDto;
import com.bxcode.tools.loader.services.base.ReactorServiceBase;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.mil.ejercito.microservice.components.helper.PageableHelper;
import pe.mil.ejercito.microservice.components.mappers.IModuleMapper;
import pe.mil.ejercito.microservice.components.validations.IModuleValidation;
import pe.mil.ejercito.microservice.dtos.ModuleDto;
import pe.mil.ejercito.microservice.repositories.contracts.IEpModuleRepository;
import pe.mil.ejercito.microservice.repositories.contracts.IEpModuleStatusRepository;
import pe.mil.ejercito.microservice.repositories.entities.EpModuleEntity;
import pe.mil.ejercito.microservice.repositories.entities.EpModuleStatusEntity;
import pe.mil.ejercito.microservice.services.contracts.IModuleDomainService;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bxcode.tools.loader.constants.BaseLoggerServicesConstant.*;

/**
 * ModuleDomainService
 * <p>
 * ModuleDomainService class.
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
public class ModuleDomainService extends ReactorServiceBase implements IModuleDomainService {

    private final IEpModuleRepository moduleRepository;
    private final IEpModuleStatusRepository moduleStatusRepository;
    private final IModuleMapper moduleMapper;

    protected ModuleDomainService(final IEpModuleRepository moduleRepository,
                                  final IEpModuleStatusRepository moduleStatusRepository,
                                  final IModuleMapper moduleMapper) {
        super("ModuleStatusDomainService");
        this.moduleRepository = moduleRepository;
        this.moduleStatusRepository = moduleStatusRepository;
        this.moduleMapper = moduleMapper;
    }

    @Override
    public Mono<ModuleDto> getByIdEntity(Long id) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidId(id))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpModuleEntity> findByIdModuleEntity = this.moduleRepository.findById(id);

        return getModuleDto(
                findByIdModuleEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<ModuleDto> getByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
        }

        final Optional<EpModuleEntity> findByUuIdModuleEntity = this.moduleRepository.findByUuId(uuId);

        return getModuleDto(
                findByUuIdModuleEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<ModuleDto> saveEntity(ModuleDto dto) {
        return doOnSave(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.info(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<ModuleDto> updateEntity(ModuleDto dto) {
        return doOnUpdate(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    @Override
    public Mono<ModuleDto> deleteByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpModuleEntity> deleteModuleEntity = this.moduleRepository.findByUuId(uuId);

        if (deleteModuleEntity.isEmpty()) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
        }

        this.moduleRepository.delete(deleteModuleEntity.get());
        return Mono.just(this.moduleMapper.mapperToDto(deleteModuleEntity.get()))
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<ModuleDto> getModuleDto(Optional<EpModuleEntity> bsEntity, String successMessage, String messageExist) {
        return bsEntity.map(moduleStatusEntity -> Mono.just(this.moduleMapper.mapperToDto(moduleStatusEntity))
                        .doOnSuccess(success -> log.debug(successMessage))
                        .doOnError(throwable -> log.error(throwable.getMessage())))
                .orElseGet(() -> Mono.error(() -> {
                    log.error(messageExist);
                    return new CommonException(messageExist, ResponseEnum.NOT_FOUNT_ENTITY);
                }));
    }

    private Mono<ModuleDto> doOnSave(ModuleDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    final EpModuleEntity saveModuleEntity = this.moduleMapper.mapperToEntity(request);

                    final Optional<EpModuleStatusEntity> moduleStatusEntity = this.moduleStatusRepository.findByUuId(request.getStatus());

                    if (moduleStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    saveModuleEntity.setUuId(UUID.randomUUID().toString());
                    saveModuleEntity.setMoModuleStatus(moduleStatusEntity.get());
                    saveModuleEntity.setMoCreateDate(Instant.now());
                    final EpModuleEntity entityResult = this.moduleRepository.save(saveModuleEntity);
                    return Mono.just(this.moduleMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<ModuleDto> doOnUpdate(ModuleDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(request.getUuId()))) {
                        log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
                    }

                    final Optional<EpModuleEntity> updateModuleEntity = this.moduleRepository.findByUuId(request.getUuId());

                    if (updateModuleEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final Optional<EpModuleStatusEntity> moduleStatusEntity = this.moduleStatusRepository.findByUuId(request.getStatus());

                    if (moduleStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final EpModuleEntity entityUpdate = updateModuleEntity.get();
                    entityUpdate.setMoModuleStatus(moduleStatusEntity.get());
                    entityUpdate.setMoName(request.getName());
                    entityUpdate.setMoIsMenu(request.getIsMenu());
                    entityUpdate.setMoIsComponent(request.getComponent());
                    entityUpdate.setMoIcon(request.getIcon());
                    entityUpdate.setMoPath(request.getPath());
                    entityUpdate.setMoOrder(request.getOrder());
                    entityUpdate.setMoGroup(request.getGroup());
                    entityUpdate.setMoUpdateDate(Instant.now());
                    final EpModuleEntity entityResult = this.moduleRepository.save(entityUpdate);
                    entityResult.setMoModuleStatus(moduleStatusEntity.get());
                    return Mono.just(this.moduleMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<ModuleDto> doOnValidateResponse(ModuleDto dto) {
        return IModuleValidation.doOnValidationResponse().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_ERROR, ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<List<ModuleDto>> getAllEntities(String status, String limit, String page, PageableDto pageable) {
        Pageable paging = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(limit));
        Page<EpModuleEntity> entityPage = this.moduleRepository.findAll(status, paging);
        List<ModuleDto> modules = this.moduleMapper.mapperToList(entityPage.getContent());
        PageableHelper.generatePaginationDetails(entityPage, page, limit, pageable);
        return Mono.just(modules)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}


