package com.intenthq.horseracing.factory;


import com.intenthq.horseracing.InvalidInputException;
import com.intenthq.horseracing.domain.BallToss;
import com.intenthq.horseracing.domain.Horse;
import com.intenthq.horseracing.domain.Race;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.intenthq.horseracing.InvalidInputException.INVALID_BALLTOSS_MESSAGE;
import static com.intenthq.horseracing.InvalidInputException.INVALID_INOUTNOTCOMPLETE_MESSAGE;
import static com.intenthq.horseracing.InvalidInputException.INVALID_NOBALLTOSSED_MESSAGE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RaceFactoryTest{
    @Mock
    private HorseFactory horseFactory;

    @Mock
    private InputLinesReader inputLinesReader;

    private RaceFactory raceFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        raceFactory = new RaceFactory();
    }

    @Test
    public void shouldThrowInvalidInputWhenInputIsNull() throws Exception {
        try {
            raceFactory.create(null);
            fail();
        } catch (InvalidInputException ex) {
            assertThat(ex.getMessage(), is(InvalidInputException.INVALID_MESSAGE));
        }
    }

    @Test
    public void shouldThrowInvalidInputWhenInputIsEmpty() throws Exception {
        try {
            raceFactory.create("");
            fail();
        } catch (InvalidInputException ex) {
            assertThat(ex.getMessage(), is(InvalidInputException.INVALID_MESSAGE));
        }
    }

    @Test
    public void shouldThrowInvalidInputExceptionWhenGetLinesThrows() throws Exception {
        InvalidInputException expected = mock(InvalidInputException.class);

        raceFactory.setInputLinesReader(inputLinesReader);
        String input = "INPUT";

        doThrow(expected).when(inputLinesReader).readLines(input);
        try {
            raceFactory.create(input);
            fail();
        } catch (InvalidInputException ex) {
            assertEquals(expected, ex);
        }
    }

    @Test
    public void shouldThrowInvalidInputWhenWrongBallToss() throws Exception {
        String input = "INPUT";
        raceFactory.setInputLinesReader(inputLinesReader);
        raceFactory.setHorseFactory(horseFactory);

        List<String> linesList = new ArrayList<String>();
        linesList.add("ONE");
        linesList.add("TWO");

        when(inputLinesReader.readLines(input)).thenReturn(linesList);
        try {
            raceFactory.create(input);
            fail();
        } catch (InvalidInputException ex) {
            assertEquals(INVALID_BALLTOSS_MESSAGE, ex.getMessage());
        }
    }

    @Test
    public void shouldThrowInvalidInputWhenNoBallToss() throws Exception {
        String input = "INPUT";
        raceFactory.setInputLinesReader(inputLinesReader);
        raceFactory.setHorseFactory(horseFactory);

        List<String> linesList = new ArrayList<String>();
        linesList.add("ONE");

        when(inputLinesReader.readLines(input)).thenReturn(linesList);
        try {
            raceFactory.create(input);
            fail();
        } catch (InvalidInputException ex) {
            assertEquals(INVALID_NOBALLTOSSED_MESSAGE, ex.getMessage());
        }
    }

    @Test
    public void shouldCreateRaceWhenHappyPath() throws Exception {
        final Race race = mock(Race.class);

        RaceFactory raceFactory = new RaceFactory(){
            @Override
            protected Race create() {
                return race;
            }
        };

        String input = "INPUT";

        List<Horse> expectedHorses = new ArrayList<Horse>();
        expectedHorses.add(new Horse("ONE", 1));
        expectedHorses.add(new Horse("TWO", 2));

        List<String> linesList = new ArrayList<String>();
        linesList.add("ONE TWO");
        linesList.add("1 5");
        linesList.add("2 5");
        linesList.add("1 10");
        linesList.add("2 20");

        raceFactory.setInputLinesReader(inputLinesReader);
        raceFactory.setHorseFactory(horseFactory);

        when(inputLinesReader.readLines(input)).thenReturn(linesList);
        when(horseFactory.create("ONE TWO", race)).thenReturn(expectedHorses);
        when(race.isFrozen()).thenReturn(false, false, false, true);

        Race actual = raceFactory.create(input);

        assertEquals(race, actual);
        verify(race).setHorses(expectedHorses);
        verify(race, times(4)).runHorse(any(BallToss.class));
    }

    @Test
    public void shouldStopRaceWhenFrozen() throws Exception {
        final Race race = mock(Race.class);

        RaceFactory raceFactory = new RaceFactory(){
            @Override
            protected Race create() {
                return race;
            }
        };

        String input = "INPUT";

        List<Horse> expectedHorses = new ArrayList<Horse>();
        expectedHorses.add(new Horse("ONE", 1));
        expectedHorses.add(new Horse("TWO", 2));

        List<String> linesList = new ArrayList<String>();
        linesList.add("ONE TWO");
        linesList.add("1 5");
        linesList.add("2 5");
        linesList.add("1 10");
        linesList.add("2 20");

        raceFactory.setInputLinesReader(inputLinesReader);
        raceFactory.setHorseFactory(horseFactory);

        when(inputLinesReader.readLines(input)).thenReturn(linesList);
        when(horseFactory.create("ONE TWO", race)).thenReturn(expectedHorses);
        when(race.isFrozen()).thenReturn(false, true);

        Race actual = raceFactory.create(input);

        assertEquals(race, actual);
        verify(race, times(2)).runHorse(any(BallToss.class));
    }

    @Test
    public void shouldThrowInvalidInputExceptionWhenNoHorseCompletesRace() throws Exception {
        final Race race = mock(Race.class);

        RaceFactory raceFactory = new RaceFactory(){
            @Override
            protected Race create() {
                return race;
            }
        };

        String input = "INPUT";

        List<Horse> expectedHorses = new ArrayList<Horse>();
        expectedHorses.add(new Horse("ONE", 1));
        expectedHorses.add(new Horse("TWO", 2));

        List<String> linesList = new ArrayList<String>();
        linesList.add("ONE TWO");
        linesList.add("1 5");

        raceFactory.setInputLinesReader(inputLinesReader);
        raceFactory.setHorseFactory(horseFactory);

        when(inputLinesReader.readLines(input)).thenReturn(linesList);
        when(horseFactory.create("ONE TWO", race)).thenReturn(expectedHorses);
        when(race.isFrozen()).thenReturn(false);

        try {
            raceFactory.create(input);
        } catch (InvalidInputException ex) {
            assertEquals(INVALID_INOUTNOTCOMPLETE_MESSAGE, ex.getMessage());
        }
    }

}