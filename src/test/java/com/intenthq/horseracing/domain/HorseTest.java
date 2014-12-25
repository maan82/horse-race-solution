package com.intenthq.horseracing.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HorseTest {
    @Test
    public void runShouldRunFirstTime() throws Exception {
        Horse horse = new Horse("A", 1);
        horse.run(10);
        assertThat(horse.getDistanceCovered(), is(10));
    }

    @Test
    public void runShouldRunNormalCase() throws Exception {
        Horse horse = new Horse("A", 1);
        horse.run(10);
        horse.run(3);
        horse.run(8);

        assertThat(horse.getDistanceCovered(), is(21));
    }

}