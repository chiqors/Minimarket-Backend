package me.chiqors.minimarket_backend.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JSONResponse {
    @JsonProperty("http_code")
    private int httpCode;

    private String message;

    private Object data;

    private List<String> errors;
}
