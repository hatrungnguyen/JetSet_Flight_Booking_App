package edu.birzeit.jetset.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.birzeit.jetset.R;
import edu.birzeit.jetset.activities.PassengerHomeActivity;
import edu.birzeit.jetset.database.DataBaseHelper;
import edu.birzeit.jetset.database.SharedPrefManager;

public class ConfirmSummaryFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DataBaseHelper dataBaseHelper;
    SharedPrefManager sharedPrefManager;
    LinearLayout summaryContainer;
    Button buttonConfirm;
    String flightId, foodPreference, flightClass, numOfExtraBags, totalPrice;

    private String mParam1;
    private String mParam2;

    public ConfirmSummaryFragment() {
    }

    public static ConfirmSummaryFragment newInstance(String param1, String param2) {
        ConfirmSummaryFragment fragment = new ConfirmSummaryFragment();
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
        return inflater.inflate(R.layout.fragment_confirm_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPrefManager = SharedPrefManager.getInstance(getContext());
        sharedPrefManager.writeToolbarTitle("Confirm Summary");
        if (getActivity() instanceof PassengerHomeActivity) {
            ((PassengerHomeActivity) getActivity()).toolbarTitle.setText(sharedPrefManager.readToolbarTitle());
        }
        if (getArguments() != null) {
            flightId = getArguments().getString("FLIGHT_ID");
            foodPreference = getArguments().getString("FOOD_PREFERENCE");
            flightClass = getArguments().getString("FLIGHT_CLASS");
            numOfExtraBags = getArguments().getString("NUM_OF_EXTRA_BAGS");
            totalPrice = getArguments().getString("TOTAL_PRICE");
        }

        dataBaseHelper = new DataBaseHelper(getContext());
        sharedPrefManager = SharedPrefManager.getInstance(getContext());
        summaryContainer = view.findViewById(R.id.summaryContainer);
//        fillSummary();


        buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(v -> {
            Bundle result = new Bundle();
            result.putBoolean("isConfirmed", true);
            getParentFragmentManager().setFragmentResult("reservationConfirmed", result);
            getActivity().onBackPressed();
        });
    }
}