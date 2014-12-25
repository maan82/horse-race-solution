package com.intenthq.horseracing.domain;

import com.intenthq.horseracing.InvalidInputException;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import static com.intenthq.horseracing.InvalidInputException.INVALID_LANE_MESSAGE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RaceTest {

    @Test
    public void getHorseInLanesShouldReturnHorseWhenOnlyOneHorse() throws Exception {
        Horse expected = new Horse("A", 1);
        List<Horse> horses = new ArrayList<Horse>();
        horses.add(expected);
        Race race = new Race();
        race.setHorses(horses);
        assertEquals(expected, race.getHorseInLane(1));
    }

    @Test
    public void getHorseInLanesShouldThrowInvalidInputExceptionWhenNoHorseForLane() throws Exception {
        try {
            new Race().getHorseInLane(2);
            fail();
        } catch (InvalidInputException ex) {
            assertEquals(INVALID_LANE_MESSAGE, ex.getMessage());
        }
    }

    @Test
    public void runHorseShouldGetAndRunHorse() throws Exception {
        Horse expected = mock(Horse.class);
        List<Horse> horses = new ArrayList<Horse>();
        horses.add(expected);
        horses.add(mock(Horse.class));
        BallToss ballToss = mock(BallToss.class);

        when(ballToss.getHole()).thenReturn(Hole.TWENTY);
        when(ballToss.getLane()).thenReturn(1);

        Race race = new Race();
        race.setHorses(horses);
        race.runHorse(ballToss);

        verify(expected).run(Hole.TWENTY.getYards());
    }

    @Test
    public void runHorseShouldFreezeRaceWhenAHorseWins() throws Exception {
        Horse expected = mock(Horse.class);
        List<Horse> horses = new ArrayList<Horse>();
        horses.add(expected);
        horses.add(mock(Horse.class));
        BallToss ballToss = mock(BallToss.class);

        when(ballToss.getHole()).thenReturn(Hole.TWENTY);
        when(ballToss.getLane()).thenReturn(1);
        when(expected.getDistanceCovered()).thenReturn(225);

        Race race = new Race();
        race.setHorses(horses);
        race.runHorse(ballToss);

        verify(expected).run(Hole.TWENTY.getYards());
        verify(expected).getDistanceCovered();
        assertTrue(race.isFrozen());
    }

    @Test
    public void runHorseShouldFreezeRaceWhenAHorseWinsEdgeCase() throws Exception {
        Horse expected = mock(Horse.class);
        List<Horse> horses = new ArrayList<Horse>();
        horses.add(expected);
        horses.add(mock(Horse.class));
        BallToss ballToss = mock(BallToss.class);

        when(ballToss.getHole()).thenReturn(Hole.TWENTY);
        when(ballToss.getLane()).thenReturn(1);
        when(expected.getDistanceCovered()).thenReturn(220);

        Race race = new Race();
        race.setHorses(horses);
        race.runHorse(ballToss);

        verify(expected).run(Hole.TWENTY.getYards());
        verify(expected).getDistanceCovered();
        assertTrue(race.isFrozen());
    }

    @Test
    public void runHorseShouldNotFreezeRaceWhenNoHorseWins() throws Exception {
        Horse expected = mock(Horse.class);
        List<Horse> horses = new ArrayList<Horse>();
        horses.add(expected);
        horses.add(mock(Horse.class));
        BallToss ballToss = mock(BallToss.class);

        when(ballToss.getHole()).thenReturn(Hole.TWENTY);
        when(ballToss.getLane()).thenReturn(1);
        when(expected.getDistanceCovered()).thenReturn(219);

        Race race = new Race();
        race.setHorses(horses);
        race.runHorse(ballToss);

        verify(expected).run(Hole.TWENTY.getYards());
        verify(expected).getDistanceCovered();
        assertFalse(race.isFrozen());
    }

    @Test
    public void getResultShouldSortNormalCase() throws Exception {
        Horse horse1 = mock(Horse.class);
        Horse horse2 = mock(Horse.class);
        Horse horse3 = mock(Horse.class);

        List<Horse> horses = new ArrayList<Horse>();
        horses.add(horse1);
        horses.add(horse2);
        horses.add(horse3);

        Race race = new Race();
        race.setHorses(horses);

        when(horse1.getDistanceCovered()).thenReturn(100);
        when(horse2.getDistanceCovered()).thenReturn(80);
        when(horse3.getDistanceCovered()).thenReturn(120);

        List<Horse> actual = race.getResult();

        assertEquals(horse3, actual.get(0));
        assertEquals(horse1, actual.get(1));
        assertEquals(horse2, actual.get(2));

        verify(horse1).setPosition(2);
        verify(horse2).setPosition(3);
        verify(horse3).setPosition(1);

    }

    @Test
    public void getResultShouldSortWhenTwoHorsesHaveSameScore() throws Exception {
        Horse horse1 = mock(Horse.class);
        Horse horse2 = mock(Horse.class);
        Horse horse3 = mock(Horse.class);

        List<Horse> horses = new ArrayList<Horse>();
        horses.add(horse1);
        horses.add(horse2);
        horses.add(horse3);

        Race race = new Race();
        race.setHorses(horses);

        when(horse1.getDistanceCovered()).thenReturn(100);
        when(horse2.getDistanceCovered()).thenReturn(80);
        when(horse3.getDistanceCovered()).thenReturn(80);

        List<Horse> actual = race.getResult();

        assertEquals(horse1, actual.get(0));
        assertEquals(horse3, actual.get(1));
        assertEquals(horse2, actual.get(2));

        verify(horse1).setPosition(1);
        verify(horse2).setPosition(3);
        verify(horse3).setPosition(2);
    }

}
