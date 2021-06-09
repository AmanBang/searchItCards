package com.animasium.searchitcards.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.animasium.searchitcards.R;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class NewUserLogin extends AppCompatActivity implements View.OnClickListener {
    public EditText edtUserName, edtEmail, edtPassword;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_user_login);

        ParseInstallation.getCurrentInstallation().saveInBackground();
        edtUserName = findViewById(R.id.n_username);
        edtEmail = findViewById(R.id.n_email);
        edtPassword = findViewById(R.id.n_password);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(signUp);
                }

                return false;
            }
        });

        signUp = findViewById(R.id.n_SignIn);


    }

    public void newSingUp(View view){
        Toast.makeText(this,"Button Pressed",Toast.LENGTH_SHORT).show();

        if (edtUserName.getText().toString().equals("") || edtPassword.getText().toString().equals("")  ||edtEmail.getText().toString().equals("")){
            Toast.makeText(this,"Email, UserName, Password is required",Toast.LENGTH_SHORT).show();
        }else {


            ParseUser user = new ParseUser();
            user.setUsername(edtUserName.getText().toString());
            user.setPassword(edtPassword.getText().toString());
            user.setEmail(edtEmail.getText().toString());

            // Other fields can be set just like any other ParseObject,
            // using the "put" method, like this: user.put("attribute", "its value");
            // If this field does not exists, it will be automatically created

            user.signUpInBackground(e -> {
//        progressDialog.dismiss();
                if (e == null) {
                    ParseUser.logOut();
                    showAlert("Account Created Successfully!", "Please verify your email before Login", false);
                    Intent intent = new Intent(NewUserLogin.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    ParseUser.logOut();
                    showAlert("Error Account Creation failed", "Account could not be created" + " :" + e.getMessage(), true);
                }
            });
        }

    }
    private void showAlert(String title, String message, boolean error) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("signIn...");
        progressDialog.show();
        AlertDialog.Builder builder = new AlertDialog.Builder(NewUserLogin.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    progressDialog.dismiss();
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = new Intent(NewUserLogin.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    public  void rootLayout(View view){
        try {
            InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {

    }
}
