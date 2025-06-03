package com.example.demo.entity.account;

public enum GioiTinh {
    NAM("Nam"),
    NU("Nữ"),
    KHAC("Khác");

    private final String value;

    GioiTinh(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GioiTinh fromString(String text) {
        for (GioiTinh gt : GioiTinh.values()) {
            if (gt.value.equalsIgnoreCase(text)) {
                return gt;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + text);
    }
}

