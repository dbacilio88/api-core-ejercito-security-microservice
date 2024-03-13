package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.UserStatusDto;
import pe.mil.ejercito.microservice.repositories.entities.EpUserStatusEntity;

import java.util.List;

/**
 * IUserStatusMapper
 * <p>
 * IUserStatusMapper interface.
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
public interface IUserStatusMapper {

    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "usName", source = "name")
    @Mapping(target = "usDescription", source = "description")
    @Mapping(target = "usCode", source = "code")
    EpUserStatusEntity mapperToEntity(UserStatusDto source);

    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "name", source = "usName")
    @Mapping(target = "description", source = "usDescription")
    @Mapping(target = "code", source = "usCode")
    UserStatusDto mapperToDto(EpUserStatusEntity source);

    List<UserStatusDto> mapperToList(Iterable<EpUserStatusEntity> entities);
}


