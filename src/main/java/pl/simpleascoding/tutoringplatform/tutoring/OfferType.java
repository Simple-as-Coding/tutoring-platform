package pl.simpleascoding.tutoringplatform.tutoring;

import lombok.Getter;

@Getter
public enum OfferType {
    PROPOSITION("Tutoring Proposition"),
    REQUEST("Tutoring Request");

    private final String description;

    OfferType(String desc) {
        this.description = desc;
    }
}
