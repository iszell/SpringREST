package hu.siz.framework.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Configuration for JPA
 *
 * @author siz
 */
@Slf4j
@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EnableJpaRepositories(basePackages = "hu.siz")
public class JpaConfig {

    /**
     * Provider for {@code ZonedDateTime} auditing fields
     *
     * @return a function returning the current timestamp with timezone
     */
    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now());
    }
}
