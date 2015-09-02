package com.android.drexelfit.db;

public class Reward {
    private String name;
    private WeekDay day;
    private Meal meal;
    //Below is example of how to set the Meal and WeekDay enums
    //Meal m=Meal.valueOf("Lunch");
    private String calories;
    private int cost;
    private boolean wasRedeemed;
    private int id;

    public Reward(){
        name=null;
        day=null;
        calories=null;
        cost=-1;
        wasRedeemed=false;
        id=-1;
    }

    public Reward(String _itemName,  String _calories, int _caloriePoints,String _day, String _meal){
        name=_itemName;
        day= WeekDay.getDay(_day);
        meal= Meal.getMeal(_meal);
        calories=_calories;
        cost=_caloriePoints;
        wasRedeemed=false;
        id=-1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeal() {
        return meal.toString();
    }

    public void setMeal(String m) {
        this.meal = Meal.getMeal(m);
    }

    public boolean getWasRedeemed() {
        return wasRedeemed;
    }

    public void setWasRedeemed(boolean wasRedeemed) {
        this.wasRedeemed = wasRedeemed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day.toString();
    }

    public void setDay(String d) {
        this.day = WeekDay.getDay(d);
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String toString(){
        return name+", " + day.toString()+", " + meal.toString()+", " + calories+", " + cost;
    }
}
