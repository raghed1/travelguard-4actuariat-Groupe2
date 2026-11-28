package com.travelguard.reclamations.model;

/** SUBMITTED -> IN_REVIEW -> APPROVED | REJECTED (APPROVED et REJECTED sont finaux). */
public enum ClaimStatus {
    SUBMITTED,
    IN_REVIEW,
    APPROVED,
    REJECTED;

    public boolean isFinal() {
        return this == APPROVED || this == REJECTED;
    }

    public boolean canTransitionTo(ClaimStatus target) {
        return switch (this) {
            case SUBMITTED -> target == IN_REVIEW || target == APPROVED || target == REJECTED;
            case IN_REVIEW -> target == APPROVED || target == REJECTED;
            case APPROVED, REJECTED -> false;
        };
    }
}
