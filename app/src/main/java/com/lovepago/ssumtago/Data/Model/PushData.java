package com.lovepago.ssumtago.Data.Model;

import java.util.List;

import lombok.Data;
import lombok.libs.com.zwitserloot.cmdreader.Excludes;

/**
 * Created by haesung on 2017-09-09.
 */

@Data
public class PushData {
    private String Code;
    private Object body;
    private String header;

    public PushBody getPushBody(){
        return (PushBody)body;
    }

    public ResultBody getResultBody(){
        return (ResultBody)body;
    }

    @Data
    public class PushBody{

    }

    @Data
    public class ResultBody{
        List<ResultFormat> results;

        @Data
        public class ValueFormat{
            String label;
            double value;
        }

        @Data
        public class ResultFormat{
            int type;
            List<ValueFormat> results;
        }
    }
}
