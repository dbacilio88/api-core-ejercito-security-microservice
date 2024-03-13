package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.*;
import pe.mil.ejercito.microservice.dtos.ProfileStatusDto;
import pe.mil.ejercito.microservice.repositories.entities.EpProfileStatusEntity;

import java.util.List;

/**
 * IProfileStatusMapper
 * <p>
 * IProfileStatusMapper interface.
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
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface IProfileStatusMapper {
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "psName", source = "name")
    @Mapping(target = "psDescription", source = "description")
    @Mapping(target = "psCode", source = "code")
    EpProfileStatusEntity mapperToEntity(ProfileStatusDto source);

    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "name", source = "psName")
    @Mapping(target = "description", source = "psDescription")
    @Mapping(target = "code", source = "psCode")
    ProfileStatusDto mapperToDto(EpProfileStatusEntity source);

    List<ProfileStatusDto> mapperToList(Iterable<EpProfileStatusEntity> entities);
}


