package com.co2AutomaticCrm.HelpUtils.DeserializeUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class NumericBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        try {
            Integer i = Integer.parseInt(jsonParser.getText());
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }

        return true;
    }
}
