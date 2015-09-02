package com.android.drexelfit.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // user table name
    private static final String TABLE_USERS = "users";
    // user Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USER = "name";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_tCALORIES = "totalCalories";
    private static final String KEY_tSTEPS = "totalSteps";
    private static final String KEY_tSLEEP = "totalSleep";
    private static final String KEY_POINTS = "points";

    //food table name
    private static final String TABLE_FOODS = "foods";

    // user Table Columns names
    private static final String KEY_FOOD = "name";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_COST = "cost";
    private static final String KEY_REDEEMED = "redeemed";
    private static final String KEY_DAY = "weekday";
    private static final String KEY_MEAL = "meal";

    //goal table name
    private static final String TABLE_GOALS = "goals";

    // user Table Columns names
    private static final String KEY_GOAL = "name";
    private static final String KEY_cGOAL = "caloriesGoal";
    private static final String KEY_PROGRESS = "progress";
    private static final String KEY_sTIME = "startTime";
    private static final String KEY_eTIME = "endTime";
    private static final String KEY_uTIME = "lastUpdateTime";

    // Database Version
    private static final int DATABASE_VERSION = 8;
    // Database Name
    private static final String DATABASE_NAME = "FitbitDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        // SQL statement to create user table
//        deleteDatabases(db);
        String CREATE_USER_TABLE = "CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "height TEXT, " +
                "weight TEXT, "+
                "points TEXT, "+
                "totalCalories TEXT, "+
                "totalSteps TEXT, "+
                "totalSleep TEXT)";

        String CREATE_FOOD_TABLE = "CREATE TABLE foods ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "calories TEXT, " +
                "cost TEXT, "+
                "weekday TEXT, "+
                "meal TEXT, "+
                "redeemed TEXT)";

        String CREATE_GOAL_TABLE = "CREATE TABLE goals ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "caloriesGoal TEXT, " +
                "progress TEXT, "+
                "startTime TEXT, "+
                "endTime TEXT, "+
                "lastUpdateTime TEXT)";

        // create all tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);
        db.execSQL(CREATE_GOAL_TABLE);
    }
    public void deleteDatabases(SQLiteDatabase db){
        String DELETE_FOOD_TABLE = "DROP TABLE users";
        String DELETE_USER_TABLE = "DROP TABLE foods";
        String DELETE_GOAL_TABLE = "DROP TABLE goals";

        // DELETES tableS
        db.execSQL(DELETE_FOOD_TABLE);
        db.execSQL(DELETE_USER_TABLE);
        db.execSQL(DELETE_GOAL_TABLE);
        Log.d("deleteTables", "DELETING TABLES!!");
    }

    public void emptyDatabases()
    {
        SQLiteDatabase db = this.getWritableDatabase();

    	db.delete("users", null, null);
    	db.delete("foods", null, null);
    	db.delete("goals", null, null);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS foods");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS goals");
        // create fresh foods table
        this.onCreate(db);
    }

    //USER Functions
    public void addUser(User user){
        //for logging
        Log.d("addUser", user.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USER,user.getName());
        values.put(KEY_HEIGHT, user.getHeight());
        values.put(KEY_WEIGHT, user.getWeight());
        values.put(KEY_POINTS, user.getPoints());
        values.put(KEY_tCALORIES, user.getTotalCalories());
        values.put(KEY_tSLEEP, user.getTotalSleep());
        values.put(KEY_tSTEPS, user.getTotalSteps());
        // 3. insert
        db.insert(TABLE_USERS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<User>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USERS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build food and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setHeight(cursor.getInt(2));
                user.setWeight(cursor.getInt(3));
                user.setPoints(cursor.getInt(4));
                user.setTotalCalories(cursor.getInt(5));
                user.setTotalSteps(cursor.getInt(6));
                user.setTotalSleep(cursor.getInt(7));

                // Add food to foods
                users.add(user);
            } while (cursor.moveToNext());
        }

        Log.d("getAllUsers()", users.toString());

        // return foods
        return users;
    }

    public int updateUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, user.getId());
        values.put(KEY_USER, user.getName());
        values.put(KEY_HEIGHT, user.getHeight());
        values.put(KEY_WEIGHT, user.getWeight());
        values.put(KEY_POINTS, user.getPoints());
        values.put(KEY_tCALORIES, user.getTotalCalories());
        values.put(KEY_tSLEEP, user.getTotalSleep());
        values.put(KEY_tSTEPS, user.getTotalSteps());

        // 3. updating row
        int i = db.update(TABLE_USERS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(user.getId()) }); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_USERS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(user.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteUser", user.toString());

    }

    //----------------------------------------------------------------------
    public void addGoal(Goal goal){
        //for logging
        Log.d("addGoal", goal.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_GOAL,goal.getName());
        values.put(KEY_cGOAL, goal.getCaloriesGoal());
        values.put(KEY_PROGRESS, goal.getProgress());
        values.put(KEY_sTIME, goal.getStartTime());
        values.put(KEY_eTIME, goal.getEndTime());
        values.put(KEY_uTIME, goal.getLastUpdateTime());
        // 3. insert
        db.insert(TABLE_GOALS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public List<Goal> getAllGoals() {
        List<Goal> goals = new LinkedList<Goal>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_GOALS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build goal and add it to list
        Goal goal = null;
        if (cursor.moveToFirst()) {
            do {
                goal = new Goal();
                goal.setId(Integer.parseInt(cursor.getString(0)));
                goal.setName(cursor.getString(1));
                goal.setCaloriesGoal(Integer.parseInt(cursor.getString(2)));
                goal.setProgress(Integer.parseInt(cursor.getString(3)));
                goal.setStartTime(Long.parseLong(cursor.getString(4)));
                goal.setEndTime(Long.parseLong(cursor.getString(5)));
                goal.setLastUpdateTime(Long.parseLong(cursor.getString(6)));

                // Add goal to goals
                goals.add(goal);
            } while (cursor.moveToNext());
        }

        Log.d("getAllGoals()", goals.toString());

        // return goals
        return goals;
    }

    public int updateGoal(Goal goal) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_GOAL,goal.getName());
        values.put(KEY_cGOAL, goal.getCaloriesGoal());
        values.put(KEY_PROGRESS, goal.getProgress());
        values.put(KEY_sTIME, goal.getStartTime());
        values.put(KEY_eTIME, goal.getEndTime());
        values.put(KEY_uTIME, goal.getLastUpdateTime());

        // 3. updating row
        int i = db.update(TABLE_GOALS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(goal.getId()) }); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteGoal(Goal goal) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_GOALS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(goal.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteGoal", goal.toString());

    }

    //-----------------------------------------------------------------------------

    //code for the FOOD Database
    public void addFood(Reward food){

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_FOOD, food.getName());
        values.put(KEY_CALORIES, food.getCalories());
        values.put(KEY_COST, food.getCost());
        values.put(KEY_DAY, food.getDay());
        values.put(KEY_MEAL, food.getMeal());
        values.put(KEY_REDEEMED, food.getWasRedeemed());
        // 3. insert
        db.insert(TABLE_FOODS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();

        //for logging
        Log.d("addFood", food.toString());
    }

    public ArrayList<Reward> getAllFoods() {
        ArrayList<Reward> foods = new ArrayList<Reward>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_FOODS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build food and add it to list
        Reward food = null;
        if (cursor.moveToFirst()) {
            do {
                food = new Reward();
                food.setId(Integer.parseInt(cursor.getString(0)));
                food.setName(cursor.getString(1));
                food.setCalories(cursor.getString(2));
                food.setCost(cursor.getInt(3));
                food.setDay(cursor.getString(4));
                food.setMeal(cursor.getString(5));
                food.setWasRedeemed(Boolean.getBoolean(cursor.getString(6)));

                // Add food to foods
                foods.add(food);
            } while (cursor.moveToNext());
        }

        Log.d("getAllFoods()", foods.toString());

        // return foods
        return foods;
    }

    public int updateFood(Reward food) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, food.getId());
        values.put(KEY_FOOD, food.getName());
        values.put(KEY_CALORIES, food.getCalories());
        values.put(KEY_COST, food.getCost());
        values.put(KEY_DAY, food.getDay());
        values.put(KEY_MEAL, food.getMeal());
        values.put(KEY_REDEEMED, food.getWasRedeemed());

        // 3. updating row
        int i = db.update(TABLE_FOODS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(food.getId()) }); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteFood(Reward food) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_FOODS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(food.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteFood", food.toString());

    }
}