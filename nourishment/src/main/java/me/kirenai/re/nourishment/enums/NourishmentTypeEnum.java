package me.kirenai.re.nourishment.enums;

import lombok.Getter;

@Getter
public enum NourishmentTypeEnum {
    UNIT("UNIT"),
    PERCENTAGE("PERCENTAGE");

    private final String name;

    NourishmentTypeEnum(String name) {
        this.name = name;
    }

    public static NourishmentTypeEnum getNourishmentTypeEnum(String name) {
        for (NourishmentTypeEnum value : NourishmentTypeEnum.values()) {
            if (value.getName().equals(name)) return value;
        }
        return null;
    }

}
