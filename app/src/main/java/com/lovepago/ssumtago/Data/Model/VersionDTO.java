package com.lovepago.ssumtago.Data.Model;

import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-08-25.
 */

@Data
public class VersionDTO {
    String androidVersion;
    String iosVersion;
    String serverVersion;
    String dbVersion;
    
    public int getAppMajorVersion(){
        if (androidVersion == null)return -1;
        return androidVersion.split("\\.").length >0 ? Integer.valueOf(androidVersion.split("\\.")[0]) : -1;
    }
    public int getAppSubVersion(){
        if (androidVersion == null)return -1;
        return androidVersion.split("\\.").length >1 ? Integer.valueOf(androidVersion.split("\\.")[1]) : -1;
    }
    public int getAppMinorVersion(){
        if (androidVersion == null)return -1;
        return androidVersion.split("\\.").length >2 ? Integer.valueOf(androidVersion.split("\\.")[2]) : -1;
    }

    public int getDbMajorVersion(){
        if (dbVersion == null)return -1;
        return dbVersion.split("\\.").length >0 ? Integer.valueOf(dbVersion.split("\\.")[0]) : -1;
    }
    public int getDbSubVersion(){
        if (dbVersion == null)return -1;
        return dbVersion.split("\\.").length >1 ? Integer.valueOf(dbVersion.split("\\.")[1]) : -1;
    }
    public int getDbMinorVersion(){
        if (dbVersion == null)return -1;
        return dbVersion.split("\\.").length >2 ? Integer.valueOf(dbVersion.split("\\.")[2]) : -1;
    }
}
