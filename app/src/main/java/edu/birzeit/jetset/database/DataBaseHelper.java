package edu.birzeit.jetset.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import edu.birzeit.jetset.model.Admin;
import edu.birzeit.jetset.model.Flight;
import edu.birzeit.jetset.model.Passenger;
import edu.birzeit.jetset.model.Reservation;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jetset.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE USER(" +
                "EMAIL TEXT PRIMARY KEY, " +
                "PHONE TEXT, " +
                "FIRST_NAME TEXT, " +
                "LAST_NAME TEXT, " +
                "PASSWORD TEXT, " +
                "ROLE TEXT, " +  // Admin or Passenger
                "PASSPORT_NUMBER TEXT, " +
                "PASSPORT_ISSUE_DATE TEXT, " +
                "PASSPORT_ISSUE_PLACE TEXT, " +
                "PASSPORT_EXPIRATION_DATE TEXT, " +
                "FOOD_PREFERENCE TEXT, " +
                "DATE_OF_BIRTH TEXT, " +
                "NATIONALITY TEXT" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE FLIGHT(" +
                "FLIGHT_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FLIGHT_NUMBER TEXT," +
                "DEPARTURE_CITY TEXT," +
                "DESTINATION_CITY TEXT," +
                "DEPARTURE_DATE TEXT," +
                "DEPARTURE_TIME TEXT," +
                "ARRIVAL_DATE TEXT," +
                "ARRIVAL_TIME TEXT," +
                "DURATION TEXT," +
                "AIRCRAFT_MODEL TEXT," +
                "CURRENT_RESERVATIONS INTEGER," +
                "MAX_SEATS INTEGER," +
                "MISSED_FLIGHTS INTEGER," +
                "BOOKING_OPEN_DATE TEXT," +
                "PRICE_ECONOMY REAL," +
                "PRICE_BUSINESS REAL," +
                "PRICE_EXTRA_BAGGAGE REAL," +
                "IS_RECURRENT TEXT" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE RESERVATION(" +
                "RESERVATION_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FLIGHT_NUMBER TEXT, " +
                "PASSENGER_EMAIL TEXT, " +
                "SEAT_NUMBER TEXT, " +
                "RESERVATION_STATUS TEXT, " +
                "FOREIGN KEY(FLIGHT_NUMBER) REFERENCES FLIGHT(FLIGHT_NUMBER), " +
                "FOREIGN KEY(PASSENGER_EMAIL) REFERENCES USER(EMAIL)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FLIGHT);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RESERVATION);
//        onCreate(sqLiteDatabase);
    }

    public void insertAdmin(Admin admin) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", admin.getEmail());
        contentValues.put("PHONE", admin.getPhoneNumber());
        contentValues.put("FIRST_NAME", admin.getFirstName());
        contentValues.put("LAST_NAME", admin.getLastName());
        contentValues.put("PASSWORD", admin.getPassword());
        contentValues.put("ROLE", "Admin");

        sqLiteDatabase.insert("USER", null, contentValues);
    }

    public void insertPassenger(Passenger passenger) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", passenger.getEmail());
        contentValues.put("PHONE", passenger.getPhoneNumber());
        contentValues.put("FIRST_NAME", passenger.getFirstName());
        contentValues.put("LAST_NAME", passenger.getLastName());
        contentValues.put("PASSWORD", passenger.getPassword());
        contentValues.put("ROLE", "Passenger");
        contentValues.put("PASSPORT_NUMBER", passenger.getPassportNumber());
        contentValues.put("PASSPORT_ISSUE_DATE", passenger.getPassportIssueDate().toString()); // Convert to String or appropriate format
        contentValues.put("PASSPORT_ISSUE_PLACE", passenger.getPassportIssuePlace());
        contentValues.put("PASSPORT_EXPIRATION_DATE", passenger.getPassportExpirationDate().toString()); // Convert to String or appropriate format
        contentValues.put("FOOD_PREFERENCE", passenger.getFoodPreference());
        contentValues.put("DATE_OF_BIRTH", passenger.getDateOfBirth().toString()); // Convert to String or appropriate format
        contentValues.put("NATIONALITY", passenger.getNationality());

        sqLiteDatabase.insert("USER", null, contentValues);
    }

    public void insertFlight(Flight flight) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FLIGHT_NUMBER", flight.getFlightNumber());
        contentValues.put("DEPARTURE_CITY", flight.getDepartureCity());
        contentValues.put("DESTINATION_CITY", flight.getDestinationCity());
        contentValues.put("DEPARTURE_DATE", flight.getDepartureDate().toString());
        contentValues.put("DEPARTURE_TIME", flight.getDepartureTime().toString());
        contentValues.put("ARRIVAL_DATE", flight.getArrivalDate().toString());
        contentValues.put("ARRIVAL_TIME", flight.getArrivalTime().toString());
        contentValues.put("DURATION", flight.getDuration());
        contentValues.put("AIRCRAFT_MODEL", flight.getAircraftModel());
        contentValues.put("CURRENT_RESERVATIONS", flight.getCurrentReservations());
        contentValues.put("MAX_SEATS", flight.getMaxSeats());
        contentValues.put("MISSED_FLIGHTS", flight.getMissedFlights());
        contentValues.put("BOOKING_OPEN_DATE", flight.getBookingOpenDate().toString());
        contentValues.put("PRICE_ECONOMY", flight.getPriceEconomy());
        contentValues.put("PRICE_BUSINESS", flight.getPriceBusiness());
        contentValues.put("PRICE_EXTRA_BAGGAGE", flight.getPriceExtraBaggage());
        contentValues.put("IS_RECURRENT", flight.getIsRecurrent());

        sqLiteDatabase.insert("FLIGHT", null, contentValues);
    }

    public void insertReservation(Reservation reservation) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FLIGHT_NUMBER", reservation.getFlightNumber());
        contentValues.put("PASSENGER_EMAIL", reservation.getPassengerEmail());
        contentValues.put("SEAT_NUMBER", reservation.getSeatNumber());
        contentValues.put("RESERVATION_STATUS", reservation.getStatus());

        sqLiteDatabase.insert("RESERVATION", null, contentValues);
    }

    public Cursor getAllFlights() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM FLIGHT", null);
    }

    public Cursor getReservationsForPassenger(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM RESERVATION WHERE PASSENGER_EMAIL =?", new String[]{email});
    }

    public Cursor getAllUsers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER", null);
    }

    public Cursor getUsersByRole(String role) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE  ROLE =?", new String[]{role});
    }

    public void updateFlight(Flight flight) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FLIGHT_NUMBER", flight.getFlightNumber());
        contentValues.put("DEPARTURE_CITY", flight.getDepartureCity());
        contentValues.put("DESTINATION_CITY", flight.getDestinationCity());
        contentValues.put("DEPARTURE_DATE", flight.getDepartureDate().toString());
        contentValues.put("DEPARTURE_TIME", flight.getDepartureTime().toString());
        contentValues.put("ARRIVAL_DATE", flight.getArrivalDate().toString());
        contentValues.put("ARRIVAL_TIME", flight.getArrivalTime().toString());
        contentValues.put("DURATION", flight.getDuration());
        contentValues.put("AIRCRAFT_MODEL", flight.getAircraftModel());
        contentValues.put("CURRENT_RESERVATIONS", flight.getCurrentReservations());
        contentValues.put("MAX_SEATS", flight.getMaxSeats());
        contentValues.put("MISSED_FLIGHTS", flight.getMissedFlights());
        contentValues.put("BOOKING_OPEN_DATE", flight.getBookingOpenDate().toString());
        contentValues.put("PRICE_ECONOMY", flight.getPriceEconomy());
        contentValues.put("PRICE_BUSINESS", flight.getPriceBusiness());
        contentValues.put("PRICE_EXTRA_BAGGAGE", flight.getPriceExtraBaggage());
        contentValues.put("IS_RECURRENT", flight.getIsRecurrent());

        sqLiteDatabase.update("FLIGHT", contentValues, "FLIGHT_ID = ?", new String[]{String.valueOf(flight.getFlightId())});
    }

    public void deleteFlight(int flightId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("FLIGHT", "FLIGHT_ID = ?", new String[]{String.valueOf(flightId)});
        sqLiteDatabase.close();
    }

    public Cursor getFilteredFlights(String departureCity, String destinationCity, String departureDate, String arrivalDate) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM FLIGHT WHERE DEPARTURE_CITY = ? AND DESTINATION_CITY = ? AND DEPARTURE_DATE = ? AND ARRIVAL_DATE = ?";
        return sqLiteDatabase.rawQuery(query, new String[]{departureCity, destinationCity, departureDate, arrivalDate});
    }

    public Cursor getReservationsByFlight(String flightNumber) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM RESERVATION WHERE FLIGHT_NUMBER = ?";
        return sqLiteDatabase.rawQuery(query, new String[]{flightNumber});
    }

    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM USER WHERE EMAIL = ? AND PASSWORD = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email, password});

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
}

