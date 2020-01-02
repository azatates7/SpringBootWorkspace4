package fileupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"fileupload.controller"})
public class SpringBootApp2 {

    public static void main(String[] args) {
        SpringApplication.run(fileupload.SpringBootApp2.class, args);
    }

}