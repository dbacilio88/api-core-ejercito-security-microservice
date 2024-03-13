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
import pe.mil.ejercito.microservice.components.mappers.IProfileMapper;
import pe.mil.ejercito.microservice.components.validations.IProfileValidation;
import pe.mil.ejercito.microservice.dtos.ProfileDto;
import pe.mil.ejercito.microservice.repositories.contracts.IEpProfileRepository;
import pe.mil.ejercito.microservice.repositories.contracts.IEpProfileStatusRepository;
import pe.mil.ejercito.microservice.repositories.entities.EpProfileEntity;
import pe.mil.ejercito.microservice.repositories.entities.EpProfileStatusEntity;
import pe.mil.ejercito.microservice.services.contracts.IProfileDomainService;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bxcode.tools.loader.constants.BaseLoggerServicesConstant.*;

/**
 * ProfileDomainService
 * <p>
 * ProfileDomainService class.
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
public class ProfileDomainService extends ReactorServiceBase implements IProfileDomainService {

    private final IEpProfileStatusRepository profileStatusRepository;
    private final IEpProfileRepository profileRepository;
    private final IProfileMapper profileMapper;

    public ProfileDomainService(final IEpProfileStatusRepository profileStatusRepository,
                                final IEpProfileRepository profileRepository,
                                final IProfileMapper profileMapper) {
        super("ProfileDomainService");
        this.profileStatusRepository = profileStatusRepository;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }


    @Override
    public Mono<ProfileDto> getByIdEntity(Long id) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidId(id))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpProfileEntity> findByIdProfileEntity = this.profileRepository.findById(id);

        return getProfileDto(
                findByIdProfileEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<ProfileDto> getByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
        }

        final Optional<EpProfileEntity> findByUuIdProfileEntity = this.profileRepository.findByUuId(uuId);

        return getProfileDto(
                findByUuIdProfileEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<ProfileDto> saveEntity(ProfileDto dto) {
        return doOnSave(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.info(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<ProfileDto> updateEntity(ProfileDto dto) {
        return doOnUpdate(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    @Override
    public Mono<ProfileDto> deleteByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpProfileEntity> deleteProfileEntity = this.profileRepository.findByUuId(uuId);

        if (deleteProfileEntity.isEmpty()) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
        }

        this.profileRepository.delete(deleteProfileEntity.get());
        return Mono.just(this.profileMapper.mapperToDto(deleteProfileEntity.get()))
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<ProfileDto> getProfileDto(Optional<EpProfileEntity> bsEntity, String successMessage, String messageExist) {
        return bsEntity.map(profileStatusEntity -> Mono.just(this.profileMapper.mapperToDto(profileStatusEntity))
                        .doOnSuccess(success -> log.debug(successMessage))
                        .doOnError(throwable -> log.error(throwable.getMessage())))
                .orElseGet(() -> Mono.error(() -> {
                    log.error(messageExist);
                    return new CommonException(messageExist, ResponseEnum.NOT_FOUNT_ENTITY);
                }));
    }

    private Mono<ProfileDto> doOnSave(ProfileDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {
                    final EpProfileEntity saveProfileEntity = this.profileMapper.mapperToEntity(request);
                    final Optional<EpProfileStatusEntity> findByUuIdProfileStatusEntity = this.profileStatusRepository.findByUuId(request.getStatus());

                    if (findByUuIdProfileStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    saveProfileEntity.setUuId(UUID.randomUUID().toString());
                    saveProfileEntity.setPrProfileStatus(findByUuIdProfileStatusEntity.get());
                    final EpProfileEntity entityResult = this.profileRepository.save(saveProfileEntity);
                    return Mono.just(this.profileMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<ProfileDto> doOnUpdate(ProfileDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(request.getUuId()))) {
                        log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
                    }

                    final Optional<EpProfileEntity> updateByUuIdProfileEntity = this.profileRepository.findByUuId(request.getUuId());

                    if (updateByUuIdProfileEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final Optional<EpProfileStatusEntity> profileStatusEntity = this.profileStatusRepository.findByUuId(request.getStatus());

                    if (profileStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final EpProfileEntity entityUpdate = updateByUuIdProfileEntity.get();

                    entityUpdate.setPrName(request.getName());
                    entityUpdate.setPrProfileStatus(profileStatusEntity.get());

                    final EpProfileEntity entityResult = this.profileRepository.save(entityUpdate);
                    entityResult.setPrProfileStatus(profileStatusEntity.get());
                    return Mono.just(this.profileMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<ProfileDto> doOnValidateResponse(ProfileDto dto) {
        return IProfileValidation.doOnValidationResponse().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_ERROR, ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<List<ProfileDto>> getAllEntities(String status, String limit, String page, PageableDto pageable) {
        Pageable paging = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(limit));
        Page<EpProfileEntity> entityPage = this.profileRepository.findAll(status, paging);
        List<ProfileDto> profiles = this.profileMapper.mapperToList(entityPage.getContent());
        PageableHelper.generatePaginationDetails(entityPage, page, limit, pageable);
        return Mono.just(profiles)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}