package com.intenthq.horseracing.domain;

import com.intenthq.horseracing.InvalidInputException;
import junit.framework.TestCase;
import org.junit.Test;

import static com.intenthq.horseracing.InvalidInputException.INVALID_BALLTOSS_MESSAGE;
import static com.intenthq.horseracing.InvalidInputException.INVALID_YARDS_MESSAGE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class BallTossTest {

    @Test
    public void shouldCreateWhenValidInput() throws Exception {
        BallToss ballToss = new BallToss("1 10");
        assertThat(ballToss.getLane(), is(1));
        assertThat(ballToss.getHole().getYards(), is(10));
    }

    @Test
    public void shouldCreateWhenOtherValidInput() throws Exception {
        BallToss ballToss = new BallToss("5 20");
        assertThat(ballToss.getLane(), is(5));
        assertThat(ballToss.getHole().getYards(), is(20));
    }

    @Test
    public void shouldThrowInvalidInputExceptionWhenInValidInput() throws Exception {
        try {
            new BallToss("1");
            fail();
        } catch (InvalidInputException ex) {
            assertThat(ex.getMessage(), is(INVALID_BALLTOSS_MESSAGE));
        }
    }

    @Test
    public void shouldThrowInvalidInputExceptionWhenInValidLaneInput() throws Exception {
        try {
            new BallToss("A 10");
            fail();
        } catch (InvalidInputException ex) {
            assertThat(ex.getMessage(), is(INVALID_BALLTOSS_MESSAGE));
        }
    }

    @Test
    public void shouldThrowInvalidInputExceptionWhenInValidYardInput() throws Exception {
        try {
            new BallToss("1 2");
            fail();
        } catch (InvalidInputException ex) {
            assertThat(ex.getMessage(), is(INVALID_YARDS_MESSAGE));
        }
    }

}