package ru.blogic.enums;

public enum LicenseType {
    DUMMY("DUMMY"),
    UZEDO("UZEDO");

    private final String licenseType;

    LicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getLicenseType() {
        return this.licenseType;
    }

    public static class Constants {
        public static final String DUMMY = "DUMMY";
        public static final String UZEDO = "UZEDO";
    }
}
