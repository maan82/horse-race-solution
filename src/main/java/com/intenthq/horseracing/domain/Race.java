package com.intenthq.horseracing.domain;

import com.intenthq.horseracing.InvalidInputException;

import java.util.*;

import static com.intenthq.horseracing.InvalidInputException.INVALID_LANE_MESSAGE;

public class Race {
    public static final int MAX_LANES = 7;
    public static final int RACE_COURSE_LENGTH = 220;

    private List<Horse> horses = new ArrayList<Horse>();
    private boolean frozen = false;

    Horse getHorseInLane(int lane) throws InvalidInputException {
        try {
            return horses.get(lane - 1);
        } catch (Exception ex) {
            throw new InvalidInputException(INVALID_LANE_MESSAGE);
        }
    }

    public void runHorse(BallToss ballToss) throws InvalidInputException {
        Horse horse = getHorseInLane(ballToss.getLane());
        horse.run(ballToss.getHole().getYards());
        if (horse.getDistanceCovered() >= RACE_COURSE_LENGTH) {
            this.frozen = true;
        }
    }

    public List<Horse> getResult() {
        SortedMap<Integer, List<Horse>> sortedMap = new TreeMap<Integer, List<Horse>>();
        for (Horse horse : this.getHorses()) {
            List<Horse> horses =
                sortedMap.containsKey(horse.getDistanceCovered())
                    ? sortedMap.get(horse.getDistanceCovered())
                    : new ArrayList<Horse>();
                horses.add(horse);
                sortedMap.put(horse.getDistanceCovered(), horses);
        }
        Set<Integer> set = sortedMap.keySet();
        List<Horse> horses = new ArrayList<Horse>();
        int size = this.getHorses().size();
        for (Integer key: set) {
            for (Horse horse: sortedMap.get(key)) {
                horses.add(0, horse);
                horse.setPosition(size--);
            }
        }
        return horses;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void setHorses(List<Horse> horses) {
        this.horses = horses;
    }

    public int getMaxLanes() {
        return MAX_LANES;
    }

    public boolean isFrozen() {
        return frozen;
    }
}
