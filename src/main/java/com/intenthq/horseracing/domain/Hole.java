package com.intenthq.horseracing.domain;

import com.intenthq.horseracing.InvalidInputException;

import static com.intenthq.horseracing.InvalidInputException.INVALID_YARDS_MESSAGE;

public enum Hole {

    FIVE(5), TEN(10), TWENTY(20), FORTY(40), SIXTY(60);

    private int yards;

    Hole(int yards) {
        this.yards = yards;
    }

    public int getYards() {
        return this.yards;
    }

    public static Hole create(int yards) throws InvalidInputException {
        if (FIVE.getYards() == yards) {
            return FIVE;
        } else if (TEN.getYards() == yards ) {
            return TEN;
        } else if (TWENTY.getYards() == yards ) {
            return TWENTY;
        } else if (FORTY.getYards() == yards ) {
            return FORTY;
        } else if (SIXTY.getYards() == yards ) {
            return SIXTY;
        } else {
            throw new InvalidInputException(INVALID_YARDS_MESSAGE);
        }
    }
}
