package com.example.firstapp.Models;

import android.widget.Button;

public class AdminNotificationModel {
    int survey_id;
    String surveyName,sc_name,uname,email;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //    public AdminNotificationModel(String surveyName,  String sc_name, Button detailbtn, Button approvebtn, Button denybtn) {
//
//        this.surveyName = surveyName;
//
//        this.sc_name = sc_name;
//        this.detailbtn = detailbtn;
//        this.approvebtn = approvebtn;
//        this.denybtn = denybtn;
//    }


    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }



    public String getSc_name() {
        return sc_name;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }

}
