package hu.siz.framework.core.service;

import com.google.common.reflect.ClassPath;
import hu.siz.framework.root.model.EntityPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FieldRepositoryService {
    private static final Map<String, String> EMPTY = new HashMap<>();
    private final Map<String, Map<String, String>> fieldRepository = new HashMap<>();

    @SneakyThrows
    public FieldRepositoryService(@Value("${framework.base-package}") String basePackage) {
        val classLoader = ClassLoader.getSystemClassLoader();
        ClassPath.from(classLoader)
                .getAllClasses()
                .stream()
                .filter(i -> i.getPackageName().startsWith(basePackage))
                .map(this::loadClass)
                .filter(Objects::nonNull)
                .forEach(this::findAnnotationsInClass);
    }

    public String getEntityPathFor(Class<?> clazz, String fieldName) {
        return fieldRepository
                .getOrDefault(clazz.getName(), EMPTY)
                .getOrDefault(fieldName, fieldName);
    }

    private Class<?> loadClass(ClassPath.ClassInfo classInfo) {
        try {
            return classInfo.load();
        } catch (NoClassDefFoundError e) {
            return null;
        }
    }

    private void findAnnotationsInClass(Class<?> c) {
        log.trace("Checking {} for mappings", c.getName());
        var mappings = Arrays.stream(c.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(EntityPath.class))
                .collect(Collectors.toUnmodifiableMap(Field::getName, f -> f.getAnnotation(EntityPath.class).value()));
        if (!mappings.isEmpty()) {
            log.debug("Adding mappings for {}", c.getName());
            fieldRepository.put(c.getName(), mappings);
        }
    }
}