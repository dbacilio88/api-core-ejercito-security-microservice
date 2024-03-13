package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.ProfileDto;
import pe.mil.ejercito.microservice.repositories.entities.EpProfileEntity;

import java.util.List;

/**
 * IProfileMapper
 * <p>
 * IProfileMapper interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 22/02/2024
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface IProfileMapper {

    @Mapping(target = "prProfileStatus.uuId", source = "status")
    @Mapping(target = "prName", source = "name")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "id", source = "id")
    EpProfileEntity mapperToEntity(ProfileDto source);

    @Mapping(target = "status", source = "prProfileStatus.uuId")
    @Mapping(target = "name", source = "prName")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "id", source = "id")
    ProfileDto mapperToDto(EpProfileEntity source);

    List<ProfileDto> mapperToList(Iterable<EpProfileEntity> entities);
}


