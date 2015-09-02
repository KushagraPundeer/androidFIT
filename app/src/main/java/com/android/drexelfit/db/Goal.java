package com.android.drexelfit.db;

public class Goal {

    private int id; //updated by the db
    private String name; //name of the goal
    private long startTime; //when goal started
    private long endTime; //end Time of goal
    private long lastUpdateTime; //time last updated with Fitbit server
    private int progress; //the number of steps or hrs in bed
    private int caloriesGoal; //how many calories you are trying to burn
    private int steps;
    private int sleep;
    
    public Goal(){
        this.name = "";
        this.startTime = 0;
        this.caloriesGoal=0;
        this.endTime = 0;
        lastUpdateTime = startTime;
        id=-1;
        progress=0;
    }

    public Goal(String name, long startTime, int caloriesGoal, long endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.caloriesGoal = caloriesGoal;
        lastUpdateTime = startTime;
        id=-1;
        progress=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setProgress(int currentUnits) {
        this.progress = currentUnits;
    }

    public int getCaloriesGoal() {
        return caloriesGoal;
    }

    public void setCaloriesGoal(int caloriesGoal) {
        this.caloriesGoal = caloriesGoal;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getProgress() {
        return progress;
    }

    public void setSteps(int steps) {
    	this.steps = steps;
    }
    
    public int getSteps() {
    	return this.steps;
    }
    
    public void setSleepMinutes(int sleep) {
    	this.sleep = sleep;
    }
    
    public int getSleepMinutes() {
    	return this.sleep;
    }
    
    public String toString(){
        return " ID: "+ id +", "+name+", " + progress+", " + caloriesGoal+", " + startTime+", " + endTime+", " + lastUpdateTime;
    }

}
