package com.intenthq.horseracing;

public class InvalidInputException extends Exception {
    public static final String INVALID_MESSAGE = "Invalid input";
    public static final String INVALID_LANE_MESSAGE = "Invalid lane";
    public static final String INVALID_BALLTOSS_MESSAGE = "Invalid line for ball toss should be : LANE_NUMBER<ONE SPACE>YARDS";
    public static final String INVALID_YARDS_MESSAGE = "Invalid yards in ball toss : only 5, 10, 20, 40, 60 is valid";
    public static final String INVALID_HORSE_NAMES_MESSAGE = "Invalid horses name line";
    public static final String INVALID_MAX_HORSE_MESSAGE = "Maximum 7 horses allowed";
    public static final String INVALID_NOBALLTOSSED_MESSAGE = "No lines for balls tossed";
    public static final String INVALID_INOUTNOTCOMPLETE_MESSAGE = "Invalid input, No horse has covered full distance";


    public InvalidInputException(String message) {
        super(message);
    }

}
