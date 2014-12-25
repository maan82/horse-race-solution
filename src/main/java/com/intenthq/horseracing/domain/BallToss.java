package com.intenthq.horseracing.domain;

import com.intenthq.horseracing.InvalidInputException;

public class BallToss {
    private int lane;
    private Hole hole;

    public BallToss(String input) throws InvalidInputException {
        try {
            String[] inputTokens = input.split(" ");
            this.lane = new Integer(inputTokens[0]);
            this.hole = Hole.create(new Integer(inputTokens[1]));
        } catch (InvalidInputException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InvalidInputException(InvalidInputException.INVALID_BALLTOSS_MESSAGE);
        }
    }

    public int getLane() {
        return lane;
    }

    public Hole getHole() {
        return hole;
    }
}
