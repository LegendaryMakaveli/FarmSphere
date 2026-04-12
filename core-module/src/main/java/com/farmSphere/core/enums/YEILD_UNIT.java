package com.farmSphere.core.enums;

public enum YEILD_UNIT {
    KG_PER_HECTARE("kg/ha"),
    TON_PER_HECTARE("ton/ha"),
    BAG_PER_ACRE("bags/acre");

    private final String displayName;

    YEILD_UNIT(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
