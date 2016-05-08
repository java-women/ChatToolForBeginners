package javajo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot起動クラスです.
 */
@SpringBootApplication
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        LOGGER.info("\n----------------------------------------------------------\n"
                + "Application is running!\n"
                + "Access URL(local): \thttp://127.0.0.1:8080/chat.html\n"
                + "----------------------------------------------------------");
    }
}