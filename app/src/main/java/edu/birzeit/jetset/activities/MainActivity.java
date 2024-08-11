package edu.birzeit.jetset.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.birzeit.jetset.R;
import edu.birzeit.jetset.database.DataBaseHelper;

public class MainActivity extends AppCompatActivity {

    Button signUp;
    Button login;
    EditText email;
    EditText password;
    DataBaseHelper dataBaseHelper;

    private static final String PREFS_NAME = "JetSetPrefs";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private static final String IS_FORCE_CLOSED = "IsForceClosed";


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

        signUp = findViewById(R.id.buttonSignUp);
        login = findViewById(R.id.buttonLogin);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean isLoggedIn = sharedPreferences.getBoolean(IS_LOGGED_IN, false);
        boolean isForceClosed = sharedPreferences.getBoolean(IS_FORCE_CLOSED, false);

        if (isLoggedIn && !isForceClosed) {
            // If logged in, directly go to HomeActivity
            navigateToHome();
        }

        login.setOnClickListener(v -> {
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isValidLogin = dataBaseHelper.checkUserCredentials(emailText, passwordText);

            if (isValidLogin) {
                // If the login is successful, navigate to the home screen
                editor.putBoolean(IS_LOGGED_IN, true);
                editor.putBoolean(IS_FORCE_CLOSED, false);
                editor.apply();

                navigateToHome();
            } else {
                Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
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

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FORCE_CLOSED, true);
        editor.apply();
    }


}
