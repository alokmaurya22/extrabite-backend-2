package com.extrabite.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

@Configuration
public class EnvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .directory("./")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
    }

    @Bean
    public Environment environment(Dotenv dotenv) {
        StandardEnvironment environment = new StandardEnvironment();

        safeSet("DB_URL", dotenv.get("DB_URL"));
        safeSet("DB_USERNAME", dotenv.get("DB_USERNAME"));
        safeSet("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        safeSet("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
        safeSet("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
        safeSet("SERVER_PORT", dotenv.get("SERVER_PORT"));

        return environment;
    }

    private void safeSet(String key, String value) {
        if (value != null) {
            System.setProperty(key, value);
        }
    }
}