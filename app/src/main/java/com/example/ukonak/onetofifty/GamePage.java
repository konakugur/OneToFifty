package com.example.ukonak.onetofifty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePage extends AppCompatActivity {

    List<Integer> takenTags,randomNumbers;
    List<Button> numberButtons;
    android.support.v7.widget.GridLayout firstPlayerArea;
    int nextNumber;

    public void initiateVariables()
    {
        takenTags = new ArrayList<>();
        randomNumbers = new ArrayList<>();
        firstPlayerArea = (android.support.v7.widget.GridLayout) findViewById(R.id.firstPlayerArea);
        firstPlayerArea.setVisibility(View.VISIBLE);
        numberButtons = new ArrayList<>();
        for(int i=0; i<firstPlayerArea.getChildCount();i++)
        {
            numberButtons.add((Button) firstPlayerArea.getChildAt(i));
        }
        nextNumber = 1;

    }

    public void onNumberSelected(View view)
    {
        Log.i("MYLOGS",String.valueOf(view.getTag()));
        Log.i("NEWLOGS",String.valueOf(numberButtons.get(Integer.valueOf(String.valueOf(view.getTag()))).getText()));
        int numberTapped = Integer.valueOf(String.valueOf(numberButtons.get(Integer.valueOf(String.valueOf(view.getTag()))).getText()));

        if ( numberTapped == nextNumber)
        {
            Log.i("MYLOGS","equality achieved.");
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
            Log.i("COUNTING",String.valueOf(randomNumbers.size()));
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        initiateVariables();
        generateFirstNumbers();

    }
}
