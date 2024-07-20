package bg.softuni.mycinematicketsapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "movie.api")
public class MovieApiConfig {
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public MovieApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
