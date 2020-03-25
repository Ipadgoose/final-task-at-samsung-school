package com.example.edurado.MyFilms;

public class Film {

    String name;
    String year;
    String info;
    String ratio;
    String place;
    String url;
    String userRatio;
    String actorBall;
    String plotBall;
    String installationBall;
    String id;
    String viewed;

    Film(String name,String year, String info, String ratio, String place, String url){
        this.info = info;
        this.name = name;
        this.year = year;
        this.ratio = ratio;
        this.place = place;
        this.url = url;
    }
    Film(String id,String name,String year,String ratio, String userRatio,  String actorBall, String plotBall,String info, String installationBall,  String url, String viewed){
        this.id = id;
        this.name = name;
        this.userRatio = userRatio;
        this.year = year;
        this.ratio = ratio;
        this.actorBall = actorBall;
        this.plotBall = plotBall;
        this.installationBall = installationBall;
        this.info = info;
        this.url = url;
    }

    Film(String id,String name,String year, String ratio, String url){
        this.id = id;
        this.name = name;
        this.year = year;
        this.ratio = ratio;
        this.url = url;
    }

    Film(String name){
        this.name = name;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getPlace() { return place; }

    public void setPlace(String place) { this.place = place; }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) { this.ratio = ratio; }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUserRatio() { return userRatio; }

    public String getPlotBall() { return plotBall; }

    public String getActorBall() { return actorBall; }

    public String getId() { return id; }

    public String getInstallationBall() { return installationBall; }

    public void setActorBall(String actorBall) { this.actorBall = actorBall; }

    public void setId(String id) { this.id = id; }

    public void setInstallationBall(String installationBall) { this.installationBall = installationBall; }

    public void setPlotBall(String plotBall) { this.plotBall = plotBall; }

    public void setUserRatio(String userRatio) { this.userRatio = userRatio; }

    public String getViewed() { return viewed; }

    public void setViewed(String viewed) { this.viewed = viewed; }

    public String toString(){
        return name;
    }
}


