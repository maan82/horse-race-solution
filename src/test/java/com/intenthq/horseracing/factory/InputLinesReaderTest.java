package com.intenthq.horseracing.factory;

import com.intenthq.horseracing.HorseRacingController;
import com.intenthq.horseracing.InvalidInputException;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class InputLinesReaderTest {

    private InputLinesReader inputLinesReader;
    private final String lineSeparator = System.getProperty("line.separator");

    @Before
    public void setUp() throws Exception {
        inputLinesReader = new InputLinesReader();
    }

    @Test
    public void shouldCreateListWhenOneLine() throws Exception {
        String lineOne = "ONE";
        List<String> lines = inputLinesReader.readLines(lineOne);

        assertEquals(1, lines.size());
        assertTrue(lineOne.equals(lines.get(0)));
    }

    @Test
    public void shouldThrowInvalidInputExceptionWhenException() throws Exception {
        try {
            inputLinesReader.readLines(null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidInputException);
        }
    }

    @Test
    public void shouldCreateListWhenManyLines() throws Exception {
        String lineOne = "ONE"+lineSeparator+"TWO"+lineSeparator+"THREE";

        List<String> lines = inputLinesReader.readLines(lineOne);

        assertEquals(3, lines.size());
        assertTrue("ONE".equals(lines.get(0)));
        assertTrue("TWO".equals(lines.get(1)));
        assertTrue("THREE".equals(lines.get(2)));
    }

    @Test
    public void shouldCreateListWhenSlashNInLineSeparator() throws Exception {
        String lineOne = "ONE\nTWO\nTHREE";

        List<String> lines = inputLinesReader.readLines(lineOne);

        assertEquals(3, lines.size());
        assertTrue("ONE".equals(lines.get(0)));
        assertTrue("TWO".equals(lines.get(1)));
        assertTrue("THREE".equals(lines.get(2)));
    }

    @Test
    public void shouldCreateListWhenCarriageReturnInLineSeparator() throws Exception {
        String lineOne = "ONE\r\nTWO\r\nTHREE";

        List<String> lines = inputLinesReader.readLines(lineOne);

        assertEquals(3, lines.size());
        assertTrue("ONE".equals(lines.get(0)));
        assertTrue("TWO".equals(lines.get(1)));
        assertTrue("THREE".equals(lines.get(2)));
    }

    @Test
    public void shouldTrimAndCreateListWhenManyLines() throws Exception {
        String lineOne = "ONE "+lineSeparator+" TWO "+lineSeparator+" THREE";

        List<String> lines = inputLinesReader.readLines(lineOne);

        assertEquals(3, lines.size());
        assertTrue("ONE".equals(lines.get(0)));
        assertTrue("TWO".equals(lines.get(1)));
        assertTrue("THREE".equals(lines.get(2)));
    }

}