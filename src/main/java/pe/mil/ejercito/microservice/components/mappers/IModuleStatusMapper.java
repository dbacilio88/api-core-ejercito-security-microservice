package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.ModuleStatusDto;
import pe.mil.ejercito.microservice.repositories.entities.EpModuleStatusEntity;

import java.util.List;

/**
 * IModuleStatusMapper
 * <p>
 * IModuleStatusMapper interface.
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
public interface IModuleStatusMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "msCode", source = "code")
    @Mapping(target = "msName", source = "name")
    @Mapping(target = "msDescription", source = "description")
    EpModuleStatusEntity mapperToEntity(ModuleStatusDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "name", source = "msName")
    @Mapping(target = "description", source = "msDescription")
    @Mapping(target = "code", source = "msCode")
    ModuleStatusDto mapperToDto(EpModuleStatusEntity source);

    List<ModuleStatusDto> mapperToList(Iterable<EpModuleStatusEntity> entities);
}


