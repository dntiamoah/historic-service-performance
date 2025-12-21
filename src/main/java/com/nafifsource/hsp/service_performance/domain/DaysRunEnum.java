package com.nafifsource.hsp.service_performance.domain;

public enum DaysRunEnum {

    MONDAY("1______"),
    TUESDAY("_1_____"),
    WEDNESDAY("__1____"),
    THURSDAY("___1___"),
    FRIDAY("____1__"),
    SATURDAY("_____1_"),
    SUNDAY("______1");

    public final String label;

    DaysRunEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
