package com.lovepago.ssumtago.CustomClass;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.lovepago.ssumtago.Data.Model.RealmDouble;
import com.lovepago.ssumtago.Data.Model.RealmString;

import java.lang.reflect.Type;

import io.realm.RealmList;

/**
 * Created by ParkHaeSung on 2017-05-26.
 */
public class DoubleRealmListConverter implements JsonDeserializer<RealmList<RealmDouble>> {


    @Override
    public RealmList<RealmDouble> deserialize(JsonElement json, Type typeOfT,
                                              JsonDeserializationContext context)
            throws JsonParseException {
        RealmList<RealmDouble> realmDoubles = new RealmList<>();
        JsonArray doubleList = json.getAsJsonArray();

        for (JsonElement doubleElement : doubleList) {
            realmDoubles.add(new RealmDouble(doubleElement.getAsDouble()));
        }

        return realmDoubles;
    }

}