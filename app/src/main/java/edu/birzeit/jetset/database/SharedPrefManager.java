package edu.birzeit.jetset.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.birzeit.jetset.model.Flight;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "JetSetPrefs";
    private static final String FLIGHT_LIST_KEY = "FlightList";
    private static final int SHARED_PREF_PRIVATE = Context.MODE_PRIVATE;
    private static SharedPreferences sharedPreferences = null;
    private static SharedPrefManager ourInstance = null;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, SHARED_PREF_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPrefManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SharedPrefManager(context);
        }

        return ourInstance;
    }

    public boolean writeString(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public String readString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean writeBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public Boolean readBoolean(String key, Boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public void removeValue(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void apply(){
        editor.apply();
    }

    public void saveFlightList(List<Flight> flights) {
        String json = gson.toJson(flights);
        writeString(FLIGHT_LIST_KEY, json);
    }

    public List<Flight> getFlightList() {
        String json = readString(FLIGHT_LIST_KEY, null);
        Type type = new TypeToken<ArrayList<Flight>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
