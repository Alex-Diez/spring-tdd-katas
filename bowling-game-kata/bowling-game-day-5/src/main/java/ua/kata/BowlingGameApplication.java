package ua.kata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class BowlingGameApplication {
  public static void main(String[] args) {
    SpringApplication.run(BowlingGameApplication.class, args);
  }

  @Bean
  public Gson gson() {
    return new GsonBuilder().create();
  }
}
