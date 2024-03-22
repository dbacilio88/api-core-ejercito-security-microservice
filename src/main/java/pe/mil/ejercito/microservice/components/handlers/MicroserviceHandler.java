package pe.mil.ejercito.microservice.components.handlers;

import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.dto.Response;
import com.bxcode.tools.loader.dto.errors.ErrorDto;
import com.bxcode.tools.loader.dto.errors.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.sql.SQLException;

/**
 * MicroserviceHandler
 * <p>
 * MicroserviceHandler class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 8/03/2024
 */
@Log4j2
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MicroserviceHandler {

    private final Response response;

    public MicroserviceHandler(Response response) {
        this.response = response;
    }

    @ExceptionHandler({SQLException.class})
    public final ResponseEntity<Object> handlerSQLException(final SQLException ex) {
        log.debug("error handlerSQLException {} ", ex.getMessage());
        final ErrorResponse error = new ErrorResponse();
        ErrorDto dto = new ErrorDto();
        dto.setDetail(ex.getMessage());

        error.setData(dto);
        return this.response
                .getResponse(error, ResponseEnum.INTERNAL_SERVER_ERROR.getCode(), headers());
    }
    @ExceptionHandler({ConnectException.class})
    public final ResponseEntity<Object> handlerConnectException(final ConnectException ex) {
        log.debug("error handlerConnectException {} ", ex.getMessage());
        final ErrorResponse error = new ErrorResponse();
        ErrorDto dto = new ErrorDto();
        dto.setDetail(ex.getMessage());

        error.setData(dto);
        return this.response
                .getResponse(error, ResponseEnum.INTERNAL_SERVER_ERROR.getCode(), headers());
    }

    private HttpHeaders headers() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


}


