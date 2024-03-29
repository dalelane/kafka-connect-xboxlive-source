package uk.co.dalelane.kafkaconnect.xboxlive.data;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


public class InstantDeserializer implements JsonDeserializer<Instant> {

    private DateTimeFormatter xboxApiTimestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.[SSSSSSS][SSSSSS][SSSSS][SSSS][SSS]").withZone(ZoneId.of("Z"));

    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Instant.from(xboxApiTimestampFormat.parse(json.getAsString()));
    }
}
