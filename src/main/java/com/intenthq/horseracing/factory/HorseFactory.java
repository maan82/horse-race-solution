package com.intenthq.horseracing.factory;

import com.intenthq.horseracing.InvalidInputException;
import com.intenthq.horseracing.domain.Horse;
import com.intenthq.horseracing.domain.Race;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.intenthq.horseracing.InvalidInputException.INVALID_HORSE_NAMES_MESSAGE;
import static com.intenthq.horseracing.InvalidInputException.INVALID_MAX_HORSE_MESSAGE;

@Component
public class HorseFactory {

    public List<Horse> create(String input, Race race) throws InvalidInputException {
        if (input.isEmpty()) {
            throw new InvalidInputException(INVALID_HORSE_NAMES_MESSAGE);
        }
        String[] tokens = input.split(", ");
        int totalNames = tokens.length;
        if (totalNames >= race.getMaxLanes()) {
            throw new InvalidInputException(INVALID_MAX_HORSE_MESSAGE);
        }
        List<Horse> horses = new ArrayList<Horse>();
        for(int i = 0; i < totalNames; i++ ) {
            horses.add(new Horse(tokens[i], i+1));
        }
        return horses;

    }

}
