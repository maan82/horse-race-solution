package com.intenthq.horseracing.factory;

import com.intenthq.horseracing.InvalidInputException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class InputLinesReader {

    public List<String> readLines(String input) throws InvalidInputException {
        try {
            BufferedReader reader = new BufferedReader(new StringReader(input));
            List<String> linesList = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                linesList.add(line.trim());
            }
            return linesList;
        } catch (Exception ex) {
            throw new InvalidInputException(InvalidInputException.INVALID_MESSAGE);
        }
    }

}
