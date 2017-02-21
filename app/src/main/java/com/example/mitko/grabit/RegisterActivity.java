package com.example.mitko.grabit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    RadioGroup genderRadioGroup;
    Button cancelButton;
    Button registerButton;
    DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText =(EditText) findViewById(R.id.lastNameEditText);
        emailEditText =(EditText) findViewById(R.id.emailEditText);
        passwordEditText =(EditText) findViewById(R.id.passwordEditText);
        confirmPasswordEditText =(EditText) findViewById
                (R.id.confirmPasswordEditText);
        genderRadioGroup =(RadioGroup) findViewById(R.id.genderRadioGroup);
        cancelButton =(Button) findViewById(R.id.cancelButton);
        registerButton =(Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(onClick);
        cancelButton.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.cancelButton){
                Intent intent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }

            if(view.getId() == R.id.registerButton){
                if(passwordEditText.getText().toString().equals(
                        confirmPasswordEditText.getText().toString())){

                    String username = usernameEditText.getText().toString();
                    String fName = firstNameEditText.getText().toString();
                    String lName = lastNameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String email = emailEditText.getText().toString();

                    int checked = genderRadioGroup.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(checked);

                    String gender = rb.getText().toString();

                    User user = new User(fName, lName, username, email, gender
                            ,password,"no_image.jpg");

                    dbHelper.registerNewUser(user);

                    Intent intel = new Intent(RegisterActivity.this,
                            LoginActivity.class);

                    startActivity(intel);

                }else
                    Toast.makeText(RegisterActivity.this,
                            "Passwords doest not match",
                            Toast.LENGTH_LONG).show();
            }
        }
    };
}
