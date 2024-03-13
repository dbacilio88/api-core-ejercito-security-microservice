package pe.mil.ejercito.microservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application
 * <p>
 * Application class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */

@SpringBootApplication(scanBasePackages = {
        "pe.mil.ejercito.microservice",
        "com.bxcode.tools.loader",
})
public class Application  {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}