package com.example.ukonak.onetofifty;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePage extends AppCompatActivity {

    List<Integer> takenTags,randomNumbers;
    Intent gamePageIntent;
    List<Button> numberButtons;
    TextView textTimer,endGame;
    Button restartButton;
    android.support.v7.widget.GridLayout firstPlayerArea;
    String chronometerTime;
    int nextNumber;
    long startTime = 0L, timeInMs = 0L, updateTime= 0L,timeSwapBuff=0L;
    Handler timeHandler = new Handler();
    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMs = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMs;
            Log.i("Chronometer",((timeInMs)+" - " +(updateTime)));
            int secs = (int) updateTime/1000;
            int minutes =  secs / 60;
            secs %= 60;
            int milliseconds = (int)(updateTime%1000);
            chronometerTime = ""+minutes+":"+String.format("%02d",secs)+":"+String.format("%03d",milliseconds);
            textTimer.setText(chronometerTime);
            timeHandler.postDelayed(this,0);
        }
    };

    public void initiateVariables()
    {
        gamePageIntent = new Intent(this,GamePage.class);
        restartButton = findViewById(R.id.restartButton);
        restartButton.setVisibility(View.INVISIBLE);
        endGame = findViewById(R.id.endGame);
        endGame.setVisibility(View.INVISIBLE);
        textTimer = findViewById(R.id.myChronometer);
        takenTags = new ArrayList<>();
        randomNumbers = new ArrayList<>();
        firstPlayerArea =findViewById(R.id.firstPlayerArea);
        firstPlayerArea.setVisibility(View.VISIBLE);
        textTimer.setVisibility(View.VISIBLE);
        numberButtons = new ArrayList<>();
        for(int i=0; i<firstPlayerArea.getChildCount();i++)
        {
            numberButtons.add((Button) firstPlayerArea.getChildAt(i));
        }
        nextNumber = 1;

    }

    public void onNumberSelected(View view)
    {
        int numberTapped = Integer.valueOf(String.valueOf(numberButtons.get(Integer.valueOf(String.valueOf(view.getTag()))).getText()));

        if ( numberTapped == nextNumber)
        {
            if (numberTapped<=16)
            {
                int randomNumber;
                do {
                    Random random = new Random();
                    randomNumber = random.nextInt(16) + 17;
                }while ( isGeneratedBefore(randomNumber) );
                randomNumbers.add(randomNumber);
                numberButtons.get(Integer.valueOf(String.valueOf(view.getTag()))).setText(String.valueOf(randomNumber));
            }
            else if ( numberTapped<=32 )
            {
                int randomNumber;
                do {
                    Random random = new Random();
                    randomNumber = random.nextInt(16) + 33;
                }while ( isGeneratedBefore(randomNumber) );
                randomNumbers.add(randomNumber);
                numberButtons.get(Integer.valueOf(String.valueOf(view.getTag()))).setText(String.valueOf(randomNumber));
            }
            else if (numberTapped<=34)
            {
                int randomNumber;
                do{
                    Random random = new Random();
                    randomNumber = random.nextInt(2) + 49;
                }while (isGeneratedBefore(randomNumber));
                randomNumbers.add(randomNumber);
                numberButtons.get(Integer.valueOf(String.valueOf(view.getTag()))).setText(String.valueOf(randomNumber));
            }
            else
            numberButtons.get(Integer.valueOf(String.valueOf(view.getTag()))).setVisibility(View.INVISIBLE);
            nextNumber++;
            if(numberTapped == 50){
                timeSwapBuff += timeInMs;
                timeHandler.removeCallbacks(updateTimerThread);
                firstPlayerArea.setVisibility(View.INVISIBLE);
                textTimer.setVisibility(View.INVISIBLE);
                endGame.setText("Your time is: \n" + chronometerTime);
                endGame.setVisibility(View.VISIBLE);
                restartButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public boolean isGeneratedBefore(int randomNumber)
    {
        for(int i=0; i<randomNumbers.size(); i++)
        {
            if(randomNumbers.get(i)==randomNumber)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isTagTaken(int indexCounter)
    {
        for(int i=0; i<takenTags.size(); i++)
        {
            if(takenTags.get(i)==indexCounter)
            {
                return true;
            }
        }
        return false;
    }

    public void generateFirstNumbers()
    {
        int randomNumber,indexCounter;
        indexCounter = 0;
        Random random = new Random();
        randomNumber = random.nextInt(16 );
        takenTags.add(randomNumber);
        randomNumbers.add(1);
        numberButtons.get(randomNumber).setText("1");
        while (randomNumbers.size() < 16)
        {
            randomNumber = random.nextInt(15 ) + 2;
            if(isTagTaken(indexCounter))
            {
                indexCounter++;
            }
            else
            {
                if( (!isGeneratedBefore(randomNumber))  )
                {
                    takenTags.add(indexCounter);
                    randomNumbers.add(randomNumber);
                    numberButtons.get(indexCounter).setText(String.valueOf(randomNumber));
                    indexCounter++;
                }
            }
        }
    }

    public void startChronometer(){
        startTime = SystemClock.uptimeMillis();
        timeHandler.postDelayed(updateTimerThread,0);
    }

    public void restartFunction(View view){
        startActivity(gamePageIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        initiateVariables();
        generateFirstNumbers();
        startChronometer();

    }
}
