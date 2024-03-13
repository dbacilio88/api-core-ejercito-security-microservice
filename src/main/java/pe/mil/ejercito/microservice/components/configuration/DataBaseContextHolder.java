package pe.mil.ejercito.microservice.components.configuration;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * DataBaseContextHolder
 * <p>
 * DataBaseContextHolder class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 8/03/2024
 */
@UtilityClass
@Log4j2
public class DataBaseContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static String getCurrentDatabase() {
        log.debug("init get current database");
        return contextHolder.get();
    }

    public static void setCurrentDatabase(String key) {
        log.debug("init set current database");
        contextHolder.set(key);
    }

    public static void removeCurrentDatabase() {
        log.debug("init remove current database");
        contextHolder.remove();
    }
}


