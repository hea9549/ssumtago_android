package com.lovepago.ssumtago.CustomClass;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.lovepago.ssumtago.Data.Model.RealmDouble;
import com.lovepago.ssumtago.Data.Model.RealmDoubleArray;
import com.lovepago.ssumtago.Data.Model.RealmString;

import java.lang.reflect.Type;

import io.realm.RealmList;

/**
 * Created by ParkHaeSung on 2017-05-26.
 */
public class Double2DimArrayConverter implements JsonDeserializer<RealmList<RealmDoubleArray>>{

    @Override
    public RealmList<RealmDoubleArray> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RealmList<RealmDoubleArray> realmDoubleArrays = new RealmList<>();
        JsonArray outerArray = json.getAsJsonArray();
        for(int i = 0;i<outerArray.size();i++){
            JsonArray innerArray = outerArray.get(i).getAsJsonArray();
            RealmDoubleArray realmDoubleArray= new RealmDoubleArray();
            for(int j = 0;j<innerArray.size();j++){
                JsonElement innerValue = innerArray.get(j);
                RealmDouble realmDouble = new RealmDouble(innerValue.getAsDouble());
                if(realmDoubleArray.getVals() == null) realmDoubleArray.setVals(new RealmList<>());
                realmDoubleArray.getVals().add(realmDouble);
            }
            realmDoubleArrays.add(realmDoubleArray);
        }

        return realmDoubleArrays;
    }
}