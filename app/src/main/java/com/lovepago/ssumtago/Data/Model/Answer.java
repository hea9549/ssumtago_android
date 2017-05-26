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
public class Answer extends RealmObject{
    private String desc;
    private String img;
    private String code;
    private RealmList<RealmString> name;
    public DTO makeDTO(){
        return new DTO();
    }
    @Data
    class DTO{
        private String desc;
        private String img;
        private String code;
        private List<String> name;
        DTO(){
            desc = Answer.this.desc;
            img = Answer.this.img;
            code = Answer.this.code;
            name = new ArrayList<>();
            for(RealmString name : Answer.this.name){
                this.name.add(name.getContent());
            }
        }
    }
}
