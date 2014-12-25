package com.intenthq.horseracing;

import com.intenthq.horseracing.domain.Race;
import com.intenthq.horseracing.factory.RaceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HorseRacingController {

    public static final String INPUT_ATT = "input";
    public static final String OUTPUT_ATT = "output";
    public static final String VALIDATION_ATT = "validation";


    @Autowired
    private RaceFactory raceFactory;

    @RequestMapping("/horse-racing")
    public String horseRacing(ModelMap model) {
        return "horse-racing";
    }

    @RequestMapping("/horse-racing/exercise")
    public String exercise(@RequestParam(value="input", required=false) String input, ModelMap model) {
		if (!StringUtils.isEmpty(input)) {
            try {
                Race race = raceFactory.create(input);
                model.addAttribute(OUTPUT_ATT, race.getResult().toString());
            } catch (InvalidInputException e) {
                model.addAttribute(VALIDATION_ATT, e.getMessage());
            }
            model.addAttribute(INPUT_ATT, input);
		}
        return "exercise";
    }

    public void setRaceFactory(RaceFactory raceFactory) {
        this.raceFactory = raceFactory;
    }
}
