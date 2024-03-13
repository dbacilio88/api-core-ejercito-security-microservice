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
import pe.mil.ejercito.microservice.components.mappers.IUserMapper;
import pe.mil.ejercito.microservice.components.validations.IUserValidation;
import pe.mil.ejercito.microservice.dtos.UserDto;
import pe.mil.ejercito.microservice.repositories.contracts.IEpPersonRepository;
import pe.mil.ejercito.microservice.repositories.contracts.IEpProfileRepository;
import pe.mil.ejercito.microservice.repositories.contracts.IEpUserRepository;
import pe.mil.ejercito.microservice.repositories.contracts.IEpUserStatusRepository;
import pe.mil.ejercito.microservice.repositories.entities.EpPersonEntity;
import pe.mil.ejercito.microservice.repositories.entities.EpProfileEntity;
import pe.mil.ejercito.microservice.repositories.entities.EpUserEntity;
import pe.mil.ejercito.microservice.repositories.entities.EpUserStatusEntity;
import pe.mil.ejercito.microservice.services.contracts.IUserDomainService;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bxcode.tools.loader.constants.BaseLoggerServicesConstant.*;

/**
 * UserDomainService
 * <p>
 * UserDomainService class.
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
public class UserDomainService extends ReactorServiceBase implements IUserDomainService {

    private final IEpPersonRepository personRepository;
    private final IEpUserStatusRepository userStatusRepository;
    private final IEpUserRepository userRepository;
    private final IEpProfileRepository profileRepository;
    private final IUserMapper userMapper;

    public UserDomainService(final IEpPersonRepository personRepository,
                             final IEpUserStatusRepository userStatusRepository,
                             final IEpUserRepository userRepository,
                             final IEpProfileRepository profileRepository,
                             final IUserMapper userMapper) {
        super("UserDomainService");
        this.personRepository = personRepository;
        this.userStatusRepository = userStatusRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userMapper = userMapper;
    }


    @Override
    public Mono<UserDto> getByIdEntity(Long id) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidId(id))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpUserEntity> persistenceEntity = this.userRepository.findById(id);

        return getUserDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<UserDto> getByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
        }

        final Optional<EpUserEntity> persistenceEntity = this.userRepository.findByUuId(uuId);

        return getUserDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<UserDto> saveEntity(UserDto dto) {
        return doOnSave(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.info(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<UserDto> updateEntity(UserDto dto) {
        return doOnUpdate(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    @Override
    public Mono<UserDto> deleteByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpUserEntity> persistenceEntity = this.userRepository.findByUuId(uuId);

        if (persistenceEntity.isEmpty()) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
        }

        this.userRepository.delete(persistenceEntity.get());
        return Mono.just(this.userMapper.mapperToDto(persistenceEntity.get()))
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<UserDto> getUserDto(Optional<EpUserEntity> bsEntity, String successMessage, String messageExist) {
        return bsEntity.map(userEntity -> Mono.just(this.userMapper.mapperToDto(userEntity))
                        .doOnSuccess(success -> log.debug(successMessage))
                        .doOnError(throwable -> log.error(throwable.getMessage())))
                .orElseGet(() -> Mono.error(() -> {
                    log.error(messageExist);
                    return new CommonException(messageExist, ResponseEnum.NOT_FOUNT_ENTITY);
                }));
    }

    private Mono<UserDto> doOnSave(UserDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    final EpUserEntity persistenceEntity = this.userMapper.mapperToEntity(request);

                    final Optional<EpProfileEntity> profileEntity = this.profileRepository.findByUuId(request.getProfile());

                    if (profileEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final Optional<EpPersonEntity> personEntity = this.personRepository.findByUuId(request.getPerson());

                    if (personEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final Optional<EpUserStatusEntity> userStatusEntity = this.userStatusRepository.findByUuId(request.getStatus());

                    if (userStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    persistenceEntity.setUuId(UUID.randomUUID().toString());
                    persistenceEntity.setUsProfile(profileEntity.get());
                    persistenceEntity.setUsPerson(personEntity.get());
                    persistenceEntity.setUsUserStatus(userStatusEntity.get());
                    persistenceEntity.setUsCreateDate(Instant.now());
                    final EpUserEntity entityResult = this.userRepository.save(persistenceEntity);
                    return Mono.just(this.userMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<UserDto> doOnUpdate(UserDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(request.getUuId()))) {
                        log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
                    }

                    final Optional<EpUserEntity> updateByUuIdUserEntity = this.userRepository.findByUuId(request.getUuId());

                    if (updateByUuIdUserEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final Optional<EpProfileEntity> profileEntity = this.profileRepository.findByUuId(request.getProfile());

                    if (profileEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final Optional<EpPersonEntity> personEntity = this.personRepository.findByUuId(request.getPerson());

                    if (personEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final Optional<EpUserStatusEntity> userStatusEntity = this.userStatusRepository.findByUuId(request.getStatus());

                    if (userStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }


                    final EpUserEntity entityUpdate = updateByUuIdUserEntity.get();
                    entityUpdate.setUsPassword(request.getPassword());
                    entityUpdate.setUsUserStatus(userStatusEntity.get());
                    entityUpdate.setUsProfile(profileEntity.get());
                    entityUpdate.setUsPerson(personEntity.get());
                    entityUpdate.setUsUpdateDate(Instant.now());

                    final EpUserEntity entityResult = this.userRepository.save(entityUpdate);

                    entityResult.setUsUserStatus(userStatusEntity.get());
                    entityResult.setUsProfile(profileEntity.get());
                    entityResult.setUsPerson(personEntity.get());

                    return Mono.just(this.userMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<UserDto> doOnValidateResponse(UserDto dto) {
        return IUserValidation.doOnValidationResponse().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_ERROR, ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<List<UserDto>> getAllEntities(String status, String profile, String person, String limit, String page, PageableDto pageable) {
        Pageable paging = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(limit));
        Page<EpUserEntity> entityPage = this.userRepository.findAll(status, profile, person, paging);
        List<UserDto> users = this.userMapper.mapperToList(entityPage.getContent());
        PageableHelper.generatePaginationDetails(entityPage, page, limit, pageable);
        return Mono.just(users)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}