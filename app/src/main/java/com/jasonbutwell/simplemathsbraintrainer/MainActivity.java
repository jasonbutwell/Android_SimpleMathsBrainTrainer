package com.jasonbutwell.simplemathsbraintrainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView resultTextView;
    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    int score = 0;

    public void start( View view ) {
        // start the start button to invisible
        startButton.setVisibility(View.INVISIBLE);
    }

    public void chooseAnswer( View view ) {
        // compare the button tag to the correct answer
        if ( view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            //Log.i("correct","correct");
            score++;
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Incorrect!");
        }

        // output to log for testing.
        Log.i("Tag", (String)view.getTag());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);

        TextView sumTextView = (TextView) findViewById(R.id.sumTextView);

        Button []answerButtons = {  (Button)findViewById(R.id.button0),
                                    (Button)findViewById(R.id.button1),
                                    (Button)findViewById(R.id.button2),
                                    (Button)findViewById(R.id.button3)
        };

        resultTextView = (TextView)findViewById(R.id.resultTextView);

        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a)+ "+" + Integer.toString(b));

        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;

        // generate the answers

        for (int i= 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);
            }
            else {
                incorrectAnswer = rand.nextInt(41);

                while (incorrectAnswer == a+b) {
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        // loop through all the answer buttons and set their text accordingly.
        for (int i=0; i< 4; i++ )
            answerButtons[i].setText(Integer.toString(answers.get(i)));

    }
}
