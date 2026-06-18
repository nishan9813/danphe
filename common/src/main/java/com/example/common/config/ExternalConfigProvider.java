package com.example.common.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
@Component
public class ExternalConfigProvider {

    private static final long CHECK_INTERVAL_MS = 30_000;

    private final Path configFilePath;
    private final AtomicReference<ConcurrentHashMap<String, String>> properties =
            new AtomicReference<>(new ConcurrentHashMap<>());
    private final AtomicLong lastCheckAt = new AtomicLong(0);
    private volatile long lastModifiedTime = 0;

    public ExternalConfigProvider(
            @Value("${app.config.path:/opt/personal/apps/config.properties}") String configFilePath) {
        this.configFilePath =
                (configFilePath != null && !configFilePath.isBlank())
                ? Paths.get(configFilePath)
                        : Paths.get("/opt/personal/apps/config.properties");
    }

    @PostConstruct
    void init() {
        loadProperties();
    }

    public String get(String key) {
        reloadIfDue();
        String value = properties.get().get(key);
        return value == null ? null : value.trim();
    }

    public String get(String key, String defaultValue) {
        reloadIfDue();
        String value = properties.get().get(key);
        return value == null ? null : value.trim();
    }

    public boolean isFilePresent() {
        return Files.exists(configFilePath);
    }

    public int getLoadedPropertiesCount() {
        return properties.get().size();
    }

    private void reloadIfDue() {
        long now = System.currentTimeMillis();
        long last = lastCheckAt.get();
        if (now - last < CHECK_INTERVAL_MS) {
            return;
        }if (!lastCheckAt.compareAndSet(last, now)) {
            return;
        }try {
            if (!Files.exists(configFilePath)) {
                return;
            }
            long modified = Files.getLastModifiedTime(configFilePath).toMillis();
            if (modified != lastModifiedTime) {
                loadProperties();
            }
        }catch (IOException e) {
            log.warn("Count not stat config file {} : {}", configFilePath, e.getMessage());
        }
    }


    private void loadProperties(){
        if (!Files.exists(configFilePath)) {
            log.warn("External config file not found: {}", configFilePath);
            return;
        }try (InputStream in = Files.newInputStream(configFilePath)) {
            FileTime modifiedTime = Files.getLastModifiedTime(configFilePath);
            Properties props = new Properties();
            props.load(in);
            ConcurrentHashMap<String , String> newMap = new ConcurrentHashMap<>();
            for (String name : props.stringPropertyNames()) {
                newMap.put(name, props.getProperty(name));
            }
            properties.set(newMap);
            lastModifiedTime = modifiedTime.toMillis();
            log.info("Loaded {} properties from {}", newMap.size(), configFilePath);
        }catch (IOException e) {
            log.error("Failed to load external config from {} : {}", configFilePath, e.getMessage());
        }
    }
}
