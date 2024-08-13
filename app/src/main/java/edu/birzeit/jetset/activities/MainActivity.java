package edu.birzeit.jetset.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
import edu.birzeit.jetset.tasks.ConnectionAsyncTask;
import edu.birzeit.jetset.tasks.FlightJsonParser;
import edu.birzeit.jetset.tasks.Hash;


public class MainActivity extends AppCompatActivity implements ConnectionAsyncTask.TaskCallback {

    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private static final String IS_FORCE_CLOSED = "IsForceClosed";
    private static final String SAVED_EMAIL = "SavedEmail";
    Button signUp;
    Button login;
    EditText email;
    EditText password;
    ;
    CheckBox rememberMe;
    DataBaseHelper dataBaseHelper;
    List<Flight> flightsAdded = new ArrayList<Flight>();
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        dataBaseHelper = new DataBaseHelper(MainActivity.this);
//        dataBaseHelper.clearFlightTable();
        sharedPrefManager = SharedPrefManager.getInstance(MainActivity.this);

        signUp = findViewById(R.id.buttonSignUp);
        login = findViewById(R.id.buttonLogin);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        rememberMe = findViewById(R.id.checkBox);


        boolean isLoggedIn = sharedPrefManager.readBoolean(IS_LOGGED_IN, false);
        boolean isForceClosed = sharedPrefManager.readBoolean(IS_FORCE_CLOSED, false);
        String savedEmail = sharedPrefManager.readString(SAVED_EMAIL, "");

        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
        connectionAsyncTask.execute("https://api.mocki.io/v2/pk3l0h7g");

        if (!savedEmail.isEmpty()) {
            email.setText(savedEmail);
        }

        if (isLoggedIn && !isForceClosed) {
            navigateToHome();
        }


        login.setOnClickListener(v -> {
            String emailText = email.getText().toString();
            String passwordText = Hash.hashPassword(password.getText().toString());

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                displayToast("Please enter both email and password");
                return;
            }

            boolean isValidLogin = dataBaseHelper.checkUserCredentials(emailText, passwordText);

            if (isValidLogin) {
                if (rememberMe.isChecked()) {
                    sharedPrefManager.writeString(SAVED_EMAIL, emailText);
                } else {
                    sharedPrefManager.removeValue(SAVED_EMAIL);
                }

                sharedPrefManager.writeBoolean(IS_LOGGED_IN, true);
                sharedPrefManager.writeBoolean(IS_FORCE_CLOSED, true);
                sharedPrefManager.apply();

                navigateToHome();
            } else {
                displayToast("Invalid email or password");
            }
        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChooseRoleActivity.class);
            startActivity(intent);
        });

    }

    private void navigateToHome() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();  // Optionally finish the login activity so the user can't go back
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        sharedPrefManager.writeBoolean(IS_FORCE_CLOSED, true);
        sharedPrefManager.apply();
    }


    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskSuccess(String result) {
        displayToast("Connected!");
        List<Flight> flights = FlightJsonParser.getObjectFromJson(result);
        Log.d("MainActivity", "onTaskSuccess: " + flights);
        if (flights == null) {
            return;
        } else {
            for (Flight flight : flights) {
            if (dataBaseHelper.doesFlightExist(flight.getFlightNumber())) {
                // Flight exists, update it
                dataBaseHelper.updateFlight(flight);
            } else {
                // Flight does not exist, insert it
                flight.setFlightId(dataBaseHelper.insertFlight(flight));
            }
            flightsAdded.add(flight);
        }
        sharedPrefManager.saveFlightList(flightsAdded);
//        navigateToHome();
    }
}

    @Override
    public void onTaskFailure() {
        Intent intent = new Intent(MainActivity.this, RetrievalFailedActivity.class);
        startActivity(intent);
    }


}
