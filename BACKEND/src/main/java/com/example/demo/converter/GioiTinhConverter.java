package com.example.demo.converter;

import com.example.demo.entity.account.GioiTinh;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GioiTinhConverter implements AttributeConverter<GioiTinh, String> {

    @Override
    public String convertToDatabaseColumn(GioiTinh gioiTinh) {
        if (gioiTinh == null) {
            return null;
        }
        return gioiTinh.getValue();
    }

    @Override
    public GioiTinh convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return GioiTinh.fromString(dbData);
    }
}

