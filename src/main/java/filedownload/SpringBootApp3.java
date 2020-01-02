package filedownload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"filedownload.controller"})
public class SpringBootApp3 {

    public static void main(String[] args) {
        SpringApplication.run(filedownload.SpringBootApp3.class, args);
    }

}