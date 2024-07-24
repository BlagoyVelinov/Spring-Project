package bg.softuni.mycinematicketsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MyCinemaTicketsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCinemaTicketsAppApplication.class, args);
    }

}
