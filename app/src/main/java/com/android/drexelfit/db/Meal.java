package com.android.drexelfit.db;


public enum Meal {
    BREAKFAST ("Breakfast"),
    LUNCH ("Lunch"),
    DINNER("Dinner");

    private final String meal;

    Meal(String s) {
         meal = s;
    }

    @Override
    public String toString(){
        return meal;
    }

    public static Meal getMeal(String s){
        if(s.equals("Breakfast"))
            return Meal.BREAKFAST;
        else if(s.equals("Lunch"))
            return Meal.LUNCH;
        else if(s.equals("Dinner"))
            return Meal.DINNER;
        else
            return null;
    }

}
