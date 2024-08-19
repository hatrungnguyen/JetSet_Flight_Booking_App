package edu.birzeit.jetset.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.birzeit.jetset.R;
import edu.birzeit.jetset.activities.AdminHomeActivity;
import edu.birzeit.jetset.database.DataBaseHelper;
import edu.birzeit.jetset.database.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FlightsArchiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlightsArchiveFragment extends Fragment {
    private DataBaseHelper dataBaseHelper;
    private LinearLayout cardContainer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FlightsArchiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FlightsArchiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FlightsArchiveFragment newInstance(String param1, String param2) {
        FlightsArchiveFragment fragment = new FlightsArchiveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flights_archive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBaseHelper = new DataBaseHelper(getContext());
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getContext());
        sharedPrefManager.writeToolbarTitle("Flights Archive");
        if (getActivity() instanceof AdminHomeActivity) {
            ((AdminHomeActivity) getActivity()).toolbarTitle.setText(sharedPrefManager.readToolbarTitle());
        }

        cardContainer = view.findViewById(R.id.cardContainer);
        fillFlights();


    }
    @SuppressLint("SetTextI18n")
    public void fillFlights() {
        Cursor cursor = dataBaseHelper.getFLightsArchive();

        if (cursor != null && cursor.moveToFirst()) {
            do {
//                String flightId = cursor.getString(cursor.getColumnIndexOrThrow("FLIGHT_ID"));
                String flightNumber = cursor.getString(cursor.getColumnIndexOrThrow("FLIGHT_NUMBER"));
                String departureCity = cursor.getString(cursor.getColumnIndexOrThrow("DEPARTURE_CITY"));
                String destinationCity = cursor.getString(cursor.getColumnIndexOrThrow("DESTINATION_CITY"));
                String departureDateTime = cursor.getString(cursor.getColumnIndexOrThrow("DEPARTURE_DATETIME"));
                String arrivalDateTime = cursor.getString(cursor.getColumnIndexOrThrow("ARRIVAL_DATETIME"));

                View cardView = getLayoutInflater().inflate(R.layout.flight_card, cardContainer, false);

                TextView textViewFlightNumber = cardView.findViewById(R.id.textViewFlightNumber);
                TextView textViewFrom = cardView.findViewById(R.id.textViewFrom);
                TextView textViewDeparture = cardView.findViewById(R.id.textViewDeparture);
                TextView textViewTo = cardView.findViewById(R.id.textViewTo);
                TextView textViewArrival = cardView.findViewById(R.id.textViewArrival);

                textViewFlightNumber.setText(flightNumber);
                textViewFrom.setText(departureCity);
                textViewDeparture.setText("Departure on " + departureDateTime);
                textViewTo.setText(destinationCity);
                textViewArrival.setText("Arrival on " + arrivalDateTime);

                cardContainer.addView(cardView);

            } while (cursor.moveToNext());

            cursor.close();
        }
//        else {
//            TextView textView = new TextView(this);
//            textView.setText("No upcoming flights found.");
//            linearLayout.addView(textView);
//        }
    }
}