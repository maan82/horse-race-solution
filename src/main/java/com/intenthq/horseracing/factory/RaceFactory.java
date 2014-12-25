package com.intenthq.horseracing.factory;

import com.intenthq.horseracing.InvalidInputException;
import com.intenthq.horseracing.domain.BallToss;
import com.intenthq.horseracing.domain.Horse;
import com.intenthq.horseracing.domain.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.intenthq.horseracing.InvalidInputException.INVALID_INOUTNOTCOMPLETE_MESSAGE;
import static com.intenthq.horseracing.InvalidInputException.INVALID_MESSAGE;
import static com.intenthq.horseracing.InvalidInputException.INVALID_NOBALLTOSSED_MESSAGE;

@Component
public class RaceFactory {

    @Autowired
    private InputLinesReader inputLinesReader;

    @Autowired
    private HorseFactory horseFactory;

    protected Race create() {
        return new Race();
    }

    public Race create(String input) throws InvalidInputException {
        if(StringUtils.isEmpty(input)) {
            throw new InvalidInputException(INVALID_MESSAGE);
        } else {
            Race race = create();
            List<String> lines = inputLinesReader.readLines(input);
            List<Horse> horses = horseFactory.create(lines.remove(0), race);
            if (lines.isEmpty()) {
                throw new InvalidInputException(INVALID_NOBALLTOSSED_MESSAGE);
            }
            race.setHorses(horses);
            for (String line : lines) {
                BallToss ballToss = new BallToss(line);
                race.runHorse(ballToss);
                if (race.isFrozen()) {
                    return race;
                }
            }
            throw new InvalidInputException(INVALID_INOUTNOTCOMPLETE_MESSAGE);
        }
    }

    public void setInputLinesReader(InputLinesReader inputLinesReader) {
        this.inputLinesReader = inputLinesReader;
    }

    public void setHorseFactory(HorseFactory horseFactory) {
        this.horseFactory = horseFactory;
    }
}
