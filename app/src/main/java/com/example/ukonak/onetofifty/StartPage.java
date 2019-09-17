package com.example.ukonak.onetofifty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartPage extends AppCompatActivity {

    Intent goToGamePage;
    public void initiateVariables() {
        goToGamePage = new Intent(this,GamePage.class);
    }

    public void startGameFunction (View view){
        startActivity(goToGamePage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        initiateVariables();
    }
}
