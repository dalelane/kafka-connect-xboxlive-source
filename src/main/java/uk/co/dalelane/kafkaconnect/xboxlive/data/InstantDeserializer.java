package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


public class InstantDeserializer implements JsonDeserializer<Instant> {

    private DateTimeFormatter xboxApiTimestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS").withZone(ZoneId.of("Z"));
    private DateTimeFormatter xboxApiShortTimestamp  = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").withZone(ZoneId.of("Z"));

    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String timestampString = json.getAsString();
        try {
            return Instant.from(xboxApiTimestampFormat.parse(timestampString));
        }
        catch (DateTimeParseException dtpe) {
            return Instant.from(xboxApiShortTimestamp.parse(timestampString));
        }
    }
}
