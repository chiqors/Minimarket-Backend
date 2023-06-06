package me.chiqors.minimarket_backend.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDateTime, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime != null
                ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
                : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date date) {
        return date != null
                ? LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                : null;
    }
}
