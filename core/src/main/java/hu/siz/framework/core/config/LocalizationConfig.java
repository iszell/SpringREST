package hu.siz.framework.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * Configure localization for the application
 *
 * @author siz
 */
@Configuration
public class LocalizationConfig {

    /**
     * Create a locale resolver based on the {@code accept-language} http header
     *
     * @return a {@link AcceptHeaderLocaleResolver}
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }
}
