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
import android.widget.TextView;

import com.animasium.searchitcards.Anime.MainActivity;
import com.animasium.searchitcards.Movie.Movies_activity;
import com.animasium.searchitcards.R;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button newUserSU,forgetPass,SignIn;
    TextView User_Name,Pass_word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);


        newUserSU = findViewById(R.id.newUserSignUpButton);
//        forgetPass = findViewById(R.id.forget_pass);

//        textView = findViewById(R.id.textView);
//        text2 = findViewById(R.id.textView2);
//        text3 = findViewById(R.id.text3);
        User_Name = findViewById(R.id.userName);
        Pass_word = findViewById(R.id.password);


        if(ParseUser.getCurrentUser() != null){
            Intent i = new Intent(LoginActivity.this, Movies_activity.class);
            startActivity(i);
            FancyToast.makeText(this,"LogIn",FancyToast.LENGTH_LONG,FancyToast.INFO,false);
            finish();

        }

        User_Name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER &&  event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(SignIn);
                }

                return false;
            }
        });
        SignIn = findViewById(R.id.signIn);
        newUserSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NewUserLogin.class);
//                Pair[] pairs =  new Pair[7];
//
//                pairs[0] = new Pair<View,String>(textView,"titleAnimation");
//                pairs[1] = new Pair<View,String>(text2,"textAnimation");
//                pairs[2] = new Pair<View,String>(text3,"simpleAnimation");
//                pairs[3] = new Pair<View,String>(username,"userNameAnimation");
//                pairs[4] = new Pair<View,String>(password,"passwordAnimation");
//                pairs[5] = new Pair<View,String>(forgetPass,"buttonAnimation");
//                pairs[6] = new Pair<View,String>(SignIn,"signTransition");
//
//
//
//
//                ActivityOptions  activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
//                startActivity(intent,activityOptions.toBundle());
                v.getContext().startActivity(intent);
            }
        });


//        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
////        bottomNav.setSelectedItemId(R.id.nav_dashboard);
//
//        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.nav_movie:
//                        startActivity(new Intent(getApplicationContext()
//                                ,Movies_activity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.nav_home:
//                        startActivity(new Intent(getApplicationContext()
//                                , MainActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.nav_news:
//                        startActivity(new Intent(getApplicationContext()
//                                ,News_activity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.nav_tv:
//                        startActivity(new Intent(getApplicationContext()
//                                ,TVShows.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.nav_dashboard:
//
//                        return true;
//
//                }
//                return false;
//            }
//        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.



    }

    public void Login(View view){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login...");
        progressDialog.show();
        ParseUser.logInInBackground(User_Name.getText().toString(), Pass_word.getText().toString(), (parseUser, e) -> {

            progressDialog.dismiss();


            if (parseUser != null) {
                showAlert("Login Successful", "Welcome, " + User_Name.getText().toString() + "!", false);
            } else {
                ParseUser.logOut();
                showAlert("Login Fail", e.getMessage() + " Please try again", true);
            }
        });
    }



    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = new Intent(LoginActivity.this, Movies_activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
        FancyToast.makeText(this,"LogIn",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
//        finish();


    }

    ///Used to hide the keyboard from the window when taped in empty area

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