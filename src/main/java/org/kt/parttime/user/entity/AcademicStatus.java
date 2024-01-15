package org.kt.parttime.user.entity;

import lombok.Getter;

@Getter
public enum AcademicStatus {
    ATTENDING("ATTENDING", "재학"),
    LEAVE("LEAVE", "휴학");

    private String name;
    private String desc;

    AcademicStatus(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static AcademicStatus detailValueOf(String desc){
        if(desc.equals("재학")) return ATTENDING;
        else if(desc.equals("휴학")) return LEAVE;
        return null;
    }
}
