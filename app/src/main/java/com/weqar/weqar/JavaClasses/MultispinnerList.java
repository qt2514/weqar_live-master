package com.weqar.weqar.JavaClasses;

/**
 * Created by safetop on 11/2/18.
 */

public class MultispinnerList {

    private String mulsubject;
    private  String mulsubjectid;


    public String getMulsubject() {
        return mulsubject;
    }

    public MultispinnerList(String mulsubject, String mulsubjectid) {
        this.mulsubjectid = mulsubjectid;
        this.mulsubject = mulsubject;
    }

    public void setMulsubject(String mulsubject) {
        this.mulsubject = mulsubject;
    }

    public String getMulsubjectid() {
        return mulsubjectid;
    }


}
