package com.lovepago.ssumtago.Data.Model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-05-24.
 */

@Data
public class Survey extends RealmObject {
    private RealmList<Question> questions = new RealmList<>();
    private String desc;
    private String name;
    private String version;
    private int id;
    @Data
    public class DTO{
        private List<Question.DTO> questions;
        private String desc;
        private String name;
        private String version;
        private int id;
        DTO(){
            questions = new ArrayList<>();
            for(Question q : Survey.this.questions){
                questions.add(q.makeDTO());
            }

            desc = Survey.this.desc;
            name = Survey.this.name;
            id = Survey.this.id;
        }
    }
}
