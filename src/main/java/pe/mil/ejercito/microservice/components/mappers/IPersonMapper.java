package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.PersonDto;
import pe.mil.ejercito.microservice.repositories.entities.EpPersonEntity;

import java.util.List;

/**
 * IPersonMapper
 * <p>
 * IPersonMapper interface.
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
public interface IPersonMapper {
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "peName", source = "name")
    @Mapping(target = "peLastname", source = "lastName")
    @Mapping(target = "peDob", source = "dob")
    EpPersonEntity mapperToEntity(PersonDto source);

    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "name", source = "peName")
    @Mapping(target = "lastName", source = "peLastname")
    @Mapping(target = "dob", source = "peDob")
    PersonDto mapperToDto(EpPersonEntity source);

    List<PersonDto> mapperToList(List<EpPersonEntity> entities);
}


