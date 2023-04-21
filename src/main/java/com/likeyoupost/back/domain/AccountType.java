package com.likeyoupost.back.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountType {
    NORMAL("일반회원"), REGULAR("정회원"), SPECIAL("특별회원");

    private final String name;

    AccountType(String name) {
        this.name = name;
    }

    @JsonCreator
    public static AccountType of(String name) {
        for (AccountType value : AccountType.values()) {
            if(value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
