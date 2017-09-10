import com.google.gson.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ParkHaeSung on 2017-09-01.
 */
public class main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();
        FileReader fileReader;
        BufferedReader bf;
        try {
            bf = new BufferedReader(new FileReader(main.class.getClassLoader().getResource("txt/previous_data_0_98.txt").getPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("error in read file");
            return;
        }
        String inLine;
        String json = "";
        try {
            while ((inLine = bf.readLine()) != null) {
                json += inLine + " ";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        PreviousSurvey[] surveys = gson.fromJson(json, PreviousSurvey[].class);
        System.out.println("파일 읽기 성공 , 전체갯수 : " + surveys.length);
        int underMin= 0;
        int oneMin = 0;
        int overOneMin = 0;
        for (PreviousSurvey survey : surveys) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date startTime = simpleDateFormat.parse(survey.startTime);
                Date endTime = simpleDateFormat.parse(survey.endTime);
                if (endTime.getTime() - startTime.getTime() < 60*1000) underMin++;
                if (60*1000< endTime.getTime() - startTime.getTime() &&  endTime.getTime() - startTime.getTime()< 120*1000) oneMin++;
                if (120*1000< endTime.getTime() - startTime.getTime()) overOneMin++;

            } catch (ParseException e) {
                e.printStackTrace();
                dataLog("시간읽기 실패 in json");
            }
        }
        dataLog("결과","1분 미만:"+underMin);
        dataLog("결과","1분~2분 :"+oneMin);
        dataLog("결과","2분이상 :"+overOneMin);
    }
    private static void dataLog(String message){
        dataLog("LOG",message);
    }

    private static void dataLog(String tag, String message){
        System.out.println(tag+" : "+message);
    }
}
