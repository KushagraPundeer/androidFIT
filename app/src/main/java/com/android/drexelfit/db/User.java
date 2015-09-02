package com.android.drexelfit.db;

/**
 * Created by Troy on 3/13/2015.
 */
public class User {

    String name;
    private int totalCalories;
    private int points;
    private int height;
    private int weight;
    private int id;
    private int totalSleep;
    private int totalSteps;
    private double strideLengthWalking;
    
    public User(String name, int totalCalories, int points, int height, int weight, int totalSleep, int totalSteps) {
        this.name = name;
        this.totalCalories = totalCalories;
        this.points = points;
        this.height = height;
        this.weight = weight;
        this.totalSleep = totalSleep;
        this.totalSteps = totalSteps;
        id=-1;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(){
        this.name = null;
        this.totalCalories = -1;
        this.points = 0;
        this.height = -1;
        this.weight = -1;

        this.totalSleep = -1;
        this.totalSteps = -1;
        id=-1;
    }

    public String toString(){
        return id+", "+name+", "+height+", "+weight+", "+points+", "+totalCalories+", "+totalSteps+", "+totalSleep;
    }

    public int getTotalSleep() {
        return totalSleep;
    }

    public void setTotalSleep(int totalSleep) {
        this.totalSleep = totalSleep;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public double getStrideLengthWalking() {
        return strideLengthWalking;
    }

    public void setStrideLengthWalking(double strideLengthWalking) {
        this.strideLengthWalking = strideLengthWalking;
    }
}
