package pl.simpleascoding.tutoringplatform.domain.tutoring;

import lombok.Getter;

@Getter
public enum OfferType {
    Proposition("Tutoring Proposition"),
    Request("Tutoring Request");

    private final String description;

    OfferType(String desc) {
        this.description = desc;
    }
}
