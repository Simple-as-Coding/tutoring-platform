package pl.simpleascoding.tutoringplatform.domain.tutoring;

import lombok.Getter;

@Getter
public enum TutoringEnum {
    Proposition("Tutoring Proposition"),
    Request("Tutoring Request");

    private final String description;

    TutoringEnum(String desc) {
        this.description = desc;
    }
}
