package cn.edu.sdua.toysrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication(scanBasePackages = "cn.edu.sdua.toysrent")
public class ToysRentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToysRentApplication.class, args);
    }

}
