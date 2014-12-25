package com.intenthq.horseracing.factory;

import com.intenthq.horseracing.InvalidInputException;
import com.intenthq.horseracing.domain.Horse;
import com.intenthq.horseracing.domain.Race;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.MockAnnotationProcessor;

import java.util.List;

import static com.intenthq.horseracing.InvalidInputException.INVALID_HORSE_NAMES_MESSAGE;
import static com.intenthq.horseracing.InvalidInputException.INVALID_MAX_HORSE_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HorseFactoryTest {
    @Mock
    private Race race;

    private HorseFactory horseFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        horseFactory = new HorseFactory();
    }

    @Test
    public void shouldThrowInvalidInputWhenEmpty() throws Exception {
        String input = "";
        try {
            horseFactory.create(input, race);
            fail();
        } catch (InvalidInputException ex) {
            assertEquals(INVALID_HORSE_NAMES_MESSAGE, ex.getMessage());
        }
    }

    @Test
    public void shouldThrowInvalidInputWhenMoreThanMax() throws Exception {
        String input = "A B";
        when(race.getMaxLanes()).thenReturn(1);
        try {
            horseFactory.create(input, race);
            fail();
        } catch (InvalidInputException ex) {
            assertEquals(INVALID_MAX_HORSE_MESSAGE, ex.getMessage());
        }
    }

    @Test
    public void shouldCreateWhenHappyPath() throws Exception {
        String input = "A, B, C";
        when(race.getMaxLanes()).thenReturn(10);

        List<Horse> horses = horseFactory.create(input, race);

        assertEquals(3, horses.size());
        assertEquals(new Horse("A", 1), horses.get(0));
        assertEquals(new Horse("B", 2), horses.get(1));
        assertEquals(new Horse("C", 3), horses.get(2));

    }
}