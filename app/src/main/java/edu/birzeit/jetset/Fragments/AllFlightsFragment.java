package edu.birzeit.jetset.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.birzeit.jetset.R;
import edu.birzeit.jetset.activities.AdminHomeActivity;
import edu.birzeit.jetset.database.DataBaseHelper;
import edu.birzeit.jetset.database.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFlightsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllFlightsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private DataBaseHelper dataBaseHelper;
    private LinearLayout cardContainer;
    private Button buttonFilter;
    private TextInputEditText editDepartureCity, editArrivalCity, editDepartureDate, editArrivalDate;
    private String departureCity, arrivalCity;
    private Calendar departureDate, arrivalDate;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllFlightsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllFlightsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllFlightsFragment newInstance(String param1, String param2) {
        AllFlightsFragment fragment = new AllFlightsFragment();
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
        return inflater.inflate(R.layout.fragment_all_flights, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBaseHelper = new DataBaseHelper(getContext());
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getContext());
        sharedPrefManager.writeToolbarTitle("All Flights");
        if (getActivity() instanceof AdminHomeActivity) {
            ((AdminHomeActivity) getActivity()).toolbarTitle.setText(sharedPrefManager.readToolbarTitle());
        }

        findAndSetupViews();
        fillFlights();

        buttonFilter.setOnClickListener(v -> {
            if (!getData()) {
                return;
            }
            fillFlights();
        });

        getParentFragmentManager().setFragmentResultListener("flightDataUpdated", this, (requestKey, result) -> {
            boolean isUpdated = result.getBoolean("isUpdated", false);
            if (isUpdated) {
                fillFlights();
            }
        });
    }

    private boolean isAllDataEmpty() {
        return departureCity.isEmpty() &&
                arrivalCity.isEmpty() &&
                editDepartureDate.getText().toString().isEmpty() &&
                editArrivalDate.getText().toString().isEmpty();
    }

    private boolean getData() {
        if (editDepartureCity.getText().toString().isEmpty())
            departureCity = "";
        else
            departureCity = editDepartureCity.getText().toString();
        if (editArrivalCity.getText().toString().isEmpty())
            arrivalCity = "";
        else
            arrivalCity = editArrivalCity.getText().toString();

        try {
            if (!editDepartureDate.getText().toString().isEmpty()) {
                if (departureDate == null) {
                    departureDate = Calendar.getInstance();
                }
                departureDate.setTime(dateFormat.parse(editDepartureDate.getText().toString()));
            } else {
                departureDate = null;
            }

            if (!editArrivalDate.getText().toString().isEmpty()) {
                if (arrivalDate == null) {
                    arrivalDate = Calendar.getInstance();
                }
                arrivalDate.setTime(dateFormat.parse(editArrivalDate.getText().toString()));
            } else {
                arrivalDate = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("Flight", "getData: " + e.getMessage());
        }

        return true;
    }

    private void findAndSetupViews() {
        editDepartureCity = getView().findViewById(R.id.editDepartureCity);
        editArrivalCity = getView().findViewById(R.id.editArrivalCity);
        editDepartureDate = getView().findViewById(R.id.editDepartureDate);
        editArrivalDate = getView().findViewById(R.id.editArrivalDate);
        cardContainer = getView().findViewById(R.id.cardContainer);
        buttonFilter = getView().findViewById(R.id.buttonFilter);

        departureCity = "";
        arrivalCity = "";
        departureDate = Calendar.getInstance();
        arrivalDate = Calendar.getInstance();

        setupDatePickers(editDepartureDate);
        setupDatePickers(editArrivalDate);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupDatePickers(TextInputEditText dateInput) {
        dateInput.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    dateInput.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime()));
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                v.performClick();
            }
            return false;

        });
    }

    @SuppressLint("SetTextI18n")
    public void fillFlights() {
        cardContainer.removeAllViews();
        Cursor cursor;

        if (!isAllDataEmpty()) {
            String departureDateString = (departureDate != null) ? dateFormat.format(departureDate.getTime()): "";
            String arrivalDateString = (arrivalDate != null) ? dateFormat.format(arrivalDate.getTime()) : "";

            cursor = dataBaseHelper.getFlightsByCityAndDate(departureCity, arrivalCity, departureDateString, arrivalDateString);
        } else {
            cursor = dataBaseHelper.getAllFlights();
        }


        if (cursor != null && cursor.moveToFirst()) {
            do {
                String flightId = cursor.getString(cursor.getColumnIndexOrThrow("FLIGHT_ID"));
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
                TextView textViewFlightID = cardView.findViewById(R.id.textViewFlightID);

                textViewFlightNumber.setText(flightNumber);
                textViewFrom.setText(departureCity);
                textViewDeparture.setText("Departure on " + departureDateTime);
                textViewTo.setText(destinationCity);
                textViewArrival.setText("Arrival on " + arrivalDateTime);
                textViewFlightID.setText(flightId);

                cardView.setTag(flightId);

                cardView.setOnClickListener(v -> openFlightDetailsFragment((String) v.getTag()));

                cardContainer.addView(cardView);

            } while (cursor.moveToNext());

            cursor.close();
        }
        dataBaseHelper.close();
//        else {
//            TextView textView = new TextView(this);
//            textView.setText("No upcoming flights found.");
//            linearLayout.addView(textView);
//        }
    }

    private void openFlightDetailsFragment(String flightId) {
        Bundle bundle = new Bundle();
        bundle.putString("FLIGHT_ID", flightId);

        FlightDetailsFragment detailsFragment = new FlightDetailsFragment();
        detailsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager(); // or requireActivity().getSupportFragmentManager()
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, detailsFragment); // Make sure to use the correct container ID
        fragmentTransaction.addToBackStack(null); // Optional, if you want to be able to go back
        fragmentTransaction.commit();
    }

}