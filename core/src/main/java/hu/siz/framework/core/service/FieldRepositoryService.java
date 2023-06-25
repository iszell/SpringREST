package hu.siz.framework.core.service;

import hu.siz.framework.root.model.EntityPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.File;
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
    private String packageName = "hu.siz";

    public FieldRepositoryService() {
        Arrays.stream(ClassLoader.getSystemClassLoader().getDefinedPackages())
                .map(Package::getName)
                .filter(name -> name.startsWith(packageName))
                .forEach(this::processPackageClasses);
    }

    public String getEntityPathFor(Class<?> clazz, String fieldName) {
        return fieldRepository
                .getOrDefault(clazz.getName(), EMPTY)
                .getOrDefault(fieldName, fieldName);
    }

    private void processPackageClasses(String packageName) {
        val classLoader = ClassLoader.getSystemClassLoader();
        var packageURL = classLoader
                .getResource(packageName.replace('.', '/'));

        if (packageURL != null) {
            File packageDir = new File(packageURL.getPath());
            if (packageDir.isDirectory()) {
                Arrays.stream(Objects.requireNonNull(packageDir.listFiles()))
                        .map(File::getName)
                        .filter(n -> n.endsWith(".class"))
                        .map(n -> loadClass(packageName, n))
                        .filter(Objects::nonNull)
                        .forEach(this::findAnnotationsInClass);
            }
        }
    }

    @SneakyThrows
    private static Class<?> loadClass(String packageName, String className) {
        if (className.endsWith(".class")) {
            className = packageName + '.' + className.substring(0, className.length() - 6);
            return ClassLoader.getSystemClassLoader().loadClass(className);
        }
        return null;
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