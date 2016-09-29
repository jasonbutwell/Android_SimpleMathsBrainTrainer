package com.jasonbutwell.simplemathsbraintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button[] answerButtons;

    // view elements
    Button startButton;
    TextView resultTextView;
    TextView pointsTextView;
    TextView sumTextView;
    TextView timerTextView;
    Button playAgainButton;

    ArrayList<Integer> answers;

    int locationOfCorrectAnswer;

    // keeping score
    int score = 0;
    int numberOfQuestions = 0;

    // added as a game state variable
    boolean inPlay = false;

    // implemented to show and hide certain UI elements based on game state
    public void showDisplay( boolean hideState ){
        // show / hide the answer buttons
        for (int i=0; i < 4; i++ ) {
            if ( hideState == false)
                answerButtons[i].setVisibility(View.INVISIBLE);
            else
                answerButtons[i].setVisibility(View.VISIBLE);
        }

        // show and hide sumtextview, timertextview and pointstextview
        if ( hideState == false ) {
            sumTextView.setVisibility(View.INVISIBLE);
            timerTextView.setVisibility(View.INVISIBLE);
            pointsTextView.setVisibility(View.INVISIBLE);
        }
        else {
            sumTextView.setVisibility(View.VISIBLE);
            timerTextView.setVisibility(View.VISIBLE);
            pointsTextView.setVisibility(View.VISIBLE);
        }

    }

    // play the game again, reset everything
    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;

        // set the game state to inplay = true
        inPlay = true;

        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");

        playAgainButton.setVisibility(View.INVISIBLE);

        // show UI elements again
        showDisplay( true );

        // create a new question / puzzle
        generateQuestion();

        // set the count down timer to 30 seconds
        new CountDownTimer((30 * 1000) + 100, 1000) {

            // called for every tick = 1000 millis
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            // called when the timer completes
            @Override
            public void onFinish() {
                // reset the timer text view and show the show to the user

                // game no longer in play as it has come to an end
                inPlay = false;
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("You scored: "+Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
            }
        }.start();
    }

    // starts the game
    public void start( View view ) {
        // start the start button to invisible
        startButton.setVisibility(View.INVISIBLE);
        showDisplay( true );
        playAgain(findViewById(R.id.playAgainButton));
    }

    // generate the question and possible answers
    public void generateQuestion() {

        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        int incorrectAnswer;

        sumTextView.setText(Integer.toString(a)+ "+" + Integer.toString(b));

        // 4 is currently the number of possible answer locations
        locationOfCorrectAnswer = rand.nextInt(4);

        // clear previous answer entries if we have them
        if ( answers.size() > 0 )
            answers.clear();

        // generate the answers

        for (int i= 0; i < 4; i++) {
            // place the correct answer
            if (i == locationOfCorrectAnswer) {
                answers.add( a + b );
            }
            else {
                // setting up incorrect answers
                incorrectAnswer = rand.nextInt(41);

                // work around so we can't have the right answer twice
                while (incorrectAnswer == a+b) {
                    incorrectAnswer = rand.nextInt(41);
                }
                // add the incorrect answer to our answers list
                answers.add(incorrectAnswer);
            }
        }

        // loop through all the answer buttons and set their text accordingly.
        for (int i=0; i < 4; i++ )
            answerButtons[i].setText(Integer.toString(answers.get(i)));
            //Log.i("answers",Integer.toString(answers.get(i)));
    }

    // use the tag to calculate and compare with the answer index to see if correct
    public void chooseAnswer( View view ) {
        if ( inPlay == true ) {
            // compare the button tag to the correct answer
            if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
                //Log.i("correct","correct");
                score++;
                resultTextView.setText("Correct!");
            } else {
                // the selected answer was wrong
                resultTextView.setText("Incorrect!");
            }

            numberOfQuestions++;
            pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));

            generateQuestion();

            // output to log for testing.
            //Log.i("Tag", (String)view.getTag());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);

        // set references to the answer buttons
        answerButtons = new Button[4];
        answerButtons[0] = (Button) findViewById(R.id.button0);
        answerButtons[1] = (Button) findViewById(R.id.button1);
        answerButtons[2] = (Button) findViewById(R.id.button2);
        answerButtons[3] = (Button) findViewById(R.id.button3);

        // obtain textview references
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        // create and init new list for answers
        answers = new ArrayList<>();

        // we are on the start screen, so not in play
        inPlay = false;

        // set display the UI elements on starting to invisible
        showDisplay( false );
        startButton.setVisibility(View.VISIBLE);
    }
}
