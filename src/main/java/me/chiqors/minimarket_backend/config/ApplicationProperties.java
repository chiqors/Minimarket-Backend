package me.chiqors.minimarket_backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationProperties {
    @Value("${host}")
    private String host;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Value("${logs.directory}")
    private String logsDirectory;

    @Value("${log.file.prefix}")
    private String logFilePrefix;

    @Value("${log.file.extension}")
    private String logFileExtension;

    @Value("${max.log.file.size}")
    private Integer maxLogFileSize;
}
