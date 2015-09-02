package com.android.drexelfit.db;

/**
 * Created by Troy on 3/13/2015.
 */
public enum WeekDay {
    SUNDAY("Sunday"),
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday");

    private final String day;
    WeekDay(String s) {
        day=s;
    }

    @Override
    public String toString(){
        return this.day;
    }

    public static WeekDay getDay(String s){
        if(s.equals("Sunday"))
        	return WeekDay.SUNDAY;
        else if(s.equals("Monday"))
        	return WeekDay.MONDAY;
        else if(s.equals("Tuesday"))
        	return WeekDay.TUESDAY;
        else if(s.equals("Wednesday"))
        	return WeekDay.WEDNESDAY;
        else if(s.equals("Thursday"))
        	return WeekDay.THURSDAY;
        else if(s.equals("Friday"))
        	return WeekDay.FRIDAY;
        else if(s.equals("Saturday"))
        	return WeekDay.SATURDAY;
        else
               return null;
    }
    
}
