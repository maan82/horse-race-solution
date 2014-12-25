package com.intenthq.horseracing.domain;

import com.intenthq.horseracing.InvalidInputException;
import junit.framework.TestCase;
import org.junit.Test;

import static com.intenthq.horseracing.InvalidInputException.INVALID_YARDS_MESSAGE;
import static org.junit.Assert.assertEquals;

public class HoleTest {

    @Test
    public void shouldCreateForFive() throws Exception {
        assertEquals(Hole.FIVE, Hole.create(5));
    }

    @Test
    public void shouldCreateForTen() throws Exception {
        assertEquals(Hole.TEN, Hole.create(10));
    }

    @Test
    public void shouldCreateForTwenty() throws Exception {
        assertEquals(Hole.TWENTY, Hole.create(20));
    }

    @Test
    public void shouldCreateForForty() throws Exception {
        assertEquals(Hole.FORTY, Hole.create(40));
    }

    @Test
    public void shouldCreateForSixty() throws Exception {
        assertEquals(Hole.SIXTY, Hole.create(60));
    }

    @Test
    public void shouldThrowInvalidInputExceptionWhenInvalid() throws Exception {
        try {
            Hole.create(1);
        } catch (InvalidInputException ex) {
            assertEquals(INVALID_YARDS_MESSAGE, ex.getMessage());
        }

    }

}