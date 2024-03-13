package pe.mil.ejercito.microservice.repositories.converts;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * BooleanToNumericConverter
 * <p>
 * BooleanToNumericConverter class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 11/03/2024
 */

@Converter(autoApply = true)
public class BooleanToNumericConverter implements AttributeConverter<Boolean, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer integer) {
        return integer != null && integer == 1;
    }
}


