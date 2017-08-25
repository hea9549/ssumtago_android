package com.lovepago.ssumtago;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lovepago.ssumtago.CustomClass.Double2DimArrayConverter;
import com.lovepago.ssumtago.CustomClass.StringRealmListConverter;
import com.lovepago.ssumtago.Data.Model.RealmDoubleArray;
import com.lovepago.ssumtago.Data.Model.RealmString;

import org.junit.Test;

import io.realm.RealmList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void gsonTest(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<RealmList<RealmDoubleArray>>(){}.getType(),new Double2DimArrayConverter())
                .create();
        testArray data = gson.fromJson("{\"datas\":[[3.1,3.2],[2.2,2.1]]}",testArray.class);

        assertEquals(data.datas.get(0).getVals().get(1).toString(),"3.2");
    }

    public class testArray{
        RealmList<RealmDoubleArray> datas;
    }
}