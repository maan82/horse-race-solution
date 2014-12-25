package com.intenthq.horseracing;

import com.intenthq.horseracing.domain.Horse;
import com.intenthq.horseracing.domain.Race;
import com.intenthq.horseracing.factory.RaceFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;

import static com.intenthq.horseracing.InvalidInputException.INVALID_INOUTNOTCOMPLETE_MESSAGE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HorseRacingControllerTest
{
	private static final String SOME_INPUT = "input";

	private ModelMap model = new ModelMap();

	@Mock
	private RaceFactory raceFactory;

	private HorseRacingController horseRacingController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		horseRacingController = new HorseRacingController();
		horseRacingController.setRaceFactory(raceFactory);
	}

	@Test
	public void horseRacingShouldReturnTheViewContainingTheWordingOfTheExercise() throws Exception {
		final String actual = horseRacingController.horseRacing(model);
		assertThat(actual, is("horse-racing"));
	}

	@Test
	public void exerciseShouldReturnTheExerciseView() throws Exception {
		Race race = mock(Race.class);
		when(raceFactory.create(SOME_INPUT)).thenReturn(race);
		when(race.getResult()).thenReturn(new ArrayList<Horse>());
		final String actual = horseRacingController.exercise(SOME_INPUT, model);
		assertThat(actual, is("exercise"));
	}

	@Test
	public void exerciseWithNoInputShouldNotReturnOutput() throws Exception {
		horseRacingController.exercise(null, model);
		assertThat(model.containsAttribute(HorseRacingController.OUTPUT_ATT), is(false));
	}

	@Test
	public void exerciseWithInputShouldReturnSomeOutput() throws Exception {
		Race race = mock(Race.class);
		when(raceFactory.create(SOME_INPUT)).thenReturn(race);
		when(race.getResult()).thenReturn(new ArrayList<Horse>());
		horseRacingController.exercise(SOME_INPUT, model);
		assertThat(model.containsAttribute(HorseRacingController.OUTPUT_ATT), is(true));
	}

	@Test
	public void exerciseWithInputShouldAddTheInputToTheModel() throws Exception {
		Race race = mock(Race.class);
		when(raceFactory.create(SOME_INPUT)).thenReturn(race);
		when(race.getResult()).thenReturn(new ArrayList<Horse>());
		horseRacingController.exercise(SOME_INPUT, model);
		final String input =(String) model.get(HorseRacingController.INPUT_ATT);
		assertThat(input, is(SOME_INPUT));
	}

	@Test
	public void exerciseWithInvalidInputShouldReturnValidation() throws Exception {
		when(raceFactory.create(SOME_INPUT)).thenThrow(new InvalidInputException(INVALID_INOUTNOTCOMPLETE_MESSAGE));
		horseRacingController.exercise(SOME_INPUT, model);
		final String output = (String) model.get(HorseRacingController.VALIDATION_ATT);
		assertThat(output, is(INVALID_INOUTNOTCOMPLETE_MESSAGE));
	}
}
