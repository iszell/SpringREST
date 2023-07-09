package hu.siz.framework.core.converter;

import hu.siz.framework.root.model.RecordStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class RecordStatusConverter implements AttributeConverter<RecordStatus, String> {
    @Override
    public String convertToDatabaseColumn(RecordStatus attribute) {
        return attribute.getDataBaseRepresentation();
    }

    @Override
    public RecordStatus convertToEntityAttribute(String dbData) {
        return Arrays.stream(RecordStatus.values())
                .filter(s -> s.getDataBaseRepresentation().equals(dbData))
                .findAny()
                .orElseGet(null);
    }
}
