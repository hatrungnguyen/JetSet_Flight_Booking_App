package edu.birzeit.jetset.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import edu.birzeit.jetset.R;
import edu.birzeit.jetset.database.DataBaseHelper;
import edu.birzeit.jetset.database.SharedPrefManager;
import edu.birzeit.jetset.model.Flight;

public class HomeActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    SharedPrefManager sharedPrefManager;
    List<Flight> flightsAdded = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataBaseHelper = new DataBaseHelper(HomeActivity.this);
        sharedPrefManager = SharedPrefManager.getInstance(HomeActivity.this);

        fillFlights(dataBaseHelper);

    }

//    public void addFlights(List<Flight> flights) {
//        for (Flight flight : flights) {
//            flight.setFlightId(dataBaseHelper.insertFlight(flight));
//            flightsAdded.add(flight);
//        }
//    }

    public void fillFlights(DataBaseHelper dataBaseHelper) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);
        linearLayout.removeAllViews();

        Cursor cursor = dataBaseHelper.getClosestFiveFlights();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String flightId = cursor.getString(cursor.getColumnIndexOrThrow("FLIGHT_ID"));
                String flightNumber = cursor.getString(cursor.getColumnIndexOrThrow("FLIGHT_NUMBER"));
                String departureCity = cursor.getString(cursor.getColumnIndexOrThrow("DEPARTURE_CITY"));
                String destinationCity = cursor.getString(cursor.getColumnIndexOrThrow("DESTINATION_CITY"));
                String departureDateTime = cursor.getString(cursor.getColumnIndexOrThrow("DEPARTURE_DATETIME"));
                String arrivalDateTime = cursor.getString(cursor.getColumnIndexOrThrow("ARRIVAL_DATETIME"));
                // You can retrieve other columns if needed

                // Format the flight information into a readable string
                String flightInfo = "Flight ID: "+ flightId +"\tFlight Number: " + flightNumber + "\n" +
                        "Departure: " + departureCity + " (" + departureDateTime + ")\n" +
                        "Destination: " + destinationCity + " (" + arrivalDateTime + ")\n";

                // Create a TextView for each flight and add it to the layout
                TextView textView = new TextView(this);
                textView.setText(flightInfo);
                linearLayout.addView(textView);

                Log.d("Home", "Flight: " + flightInfo);

            } while (cursor.moveToNext());

            cursor.close();
        } else {
            // If no flights are found, you can add a message indicating that
            TextView textView = new TextView(this);
            textView.setText("No upcoming flights found.");
            linearLayout.addView(textView);
        }
    }

}