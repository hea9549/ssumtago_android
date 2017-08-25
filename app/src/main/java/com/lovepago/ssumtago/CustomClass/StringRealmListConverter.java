package com.lovepago.ssumtago.CustomClass;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.lovepago.ssumtago.Data.Model.RealmString;

import java.lang.reflect.Type;

import io.realm.RealmList;

/**
 * Created by ParkHaeSung on 2017-05-26.
 */
public class StringRealmListConverter implements JsonDeserializer<RealmList<RealmString>> {


    @Override
    public RealmList<RealmString> deserialize(JsonElement json, Type typeOfT,
                                              JsonDeserializationContext context)
            throws JsonParseException {
        RealmList<RealmString> realmStrings = new RealmList<>();
        JsonArray stringList = json.getAsJsonArray();

        for (JsonElement stringElement : stringList) {
            realmStrings.add(new RealmString(stringElement.getAsString()));
        }

        return realmStrings;
    }

}