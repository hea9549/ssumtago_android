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
public class Question extends RealmObject {
    private RealmList<Answer> answers = new RealmList<>();
    private RealmList<RealmString> name = new RealmList<>();
    private String desc;
    private String code;
    private String img;
    public DTO makeDTO(){
        return new DTO();
    }
    @Data
    class DTO{
        private String desc;
        private String code;
        private List<Answer.DTO> answers;
        private List<String> name;
        private String img;
        DTO(){
            desc = Question.this.desc;
            code = Question.this.code;
            name = new ArrayList<>();
            for(RealmString name : Question.this.name){
                this.name.add(name.getContent());
            }

            answers = new ArrayList<>();
            for(Answer answer : Question.this.answers){
                this.answers.add(answer.makeDTO());
            }
            img = Question.this.img;
        }
    }


}
