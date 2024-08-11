package edu.birzeit.jetset.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import edu.birzeit.jetset.model.Admin;

public class SignUpAdminActivity extends AppCompatActivity {

    EditText editFirstName;
    EditText editLastName;
    EditText editEmail;
    EditText editPhoneNumber;
    EditText editPassword;
    EditText editConfirmPassword;

    Button buttonContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_up_admin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUpAdminActivity.this);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editEmail = findViewById(R.id.editEmail);
        editPhoneNumber = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);

        buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(v -> {
            Admin admin = new Admin();
            boolean validName = validateName(editFirstName.getText().toString());
            boolean validLastName = validateName(editLastName.getText().toString());
            boolean validPassword = validatePassword(editPassword.getText().toString());
            boolean validEmail = validateEmail(editEmail.getText().toString());
            boolean validPhoneNumber = validatePhoneNumber(editPhoneNumber.getText().toString());

            if (!validName) {
                Toast.makeText(this, "Please enter a valid first name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!validLastName) {
                Toast.makeText(this, "Please enter a valid last name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!validEmail) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!validPhoneNumber) {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!validPassword) {
                Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!editPassword.getText().toString().equals(editConfirmPassword.getText().toString())) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            createAdmin(admin, editEmail.getText().toString(), editPhoneNumber.getText().toString(), editFirstName.getText().toString(),
                    editLastName.getText().toString(), editPassword.getText().toString());

            dataBaseHelper.insertAdmin(admin);

            Toast.makeText(this, "Admin Created Successfully", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 1500);
        });

    }

    private boolean validateName(String name) {
        //if name is less than 3 characters, return false
        if (name.length() < 3 || name.length() > 20) {
            editFirstName.setError("Name must be at least 3 characters and less than 20 characters");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,15}$";

        if (!password.matches(passwordPattern)) {
            if (password.length() < 8 || password.length() > 15) {
                editPassword.setError("Password must be at least 8 characters and less than 15 characters");
            } else if (!password.matches(".*[a-z].*")) {
                editPassword.setError("Password must contain at least one lowercase letter");
            } else if (!password.matches(".*[A-Z].*")) {
                editPassword.setError("Password must contain at least one uppercase letter");
            } else if (!password.matches(".*[0-9].*")) {
                editPassword.setError("Password must contain at least one number");
            } else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                editPassword.setError("Password must contain at least one special character");
            }
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Invalid email address");
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            editPhoneNumber.setError("Phone number is required");
            return false;
        } else if (!android.util.Patterns.PHONE.matcher(phoneNumber).matches()) {
            editPhoneNumber.setError("Invalid phone number");
            return false;
        } else {
            return true;
        }
    }


    private void createAdmin(Admin admin, String email, String phoneNumber, String firstName, String lastName, String password) {
        admin.setEmail(email);
        admin.setPhoneNumber(phoneNumber);
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPassword(password);
    }


}
