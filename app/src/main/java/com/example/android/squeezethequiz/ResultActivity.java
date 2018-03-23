package com.example.android.squeezethequiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    String score;
    String totalQuestions;
    String totalCorrectAnswers;
    String totalIncorrectAnswers;
    String totalNotAttemptedAnswers;
    String levelNumber;
    Button levelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Get the variable values for score,totalQuestions,correctAnswers,IncorrectAnswers

        Bundle bundle = getIntent().getExtras();
        score = bundle.getString("score","No Value");
        totalQuestions = bundle.getString("totalQuestions");
        totalCorrectAnswers = bundle.getString("totalCorrectAnswers");
        totalIncorrectAnswers = bundle.getString("totalIncorrectAnswers");
        totalNotAttemptedAnswers = bundle.getString("totalNotAttemptedAnswers");
        levelNumber = bundle.getString("levelNumber");

        // Check if it is the last level of the quiz and change the title of Next level button to RESTART to start quiz again
        if (Integer.valueOf(levelNumber) == 2) {
            levelButton = findViewById(R.id.level_Button);

            levelButton.setText("RESTART");

            Toast endOfQuizToast = Toast.makeText(getApplicationContext(), "End of Quiz" , Toast.LENGTH_LONG);
            endOfQuizToast.show();

        }

        showResult();
    }

    /**
     * Function to show the result Summary
     */

    public void showResult() {

        // Variables to refer to the XML view
        TextView scoreTextView = findViewById(R.id.score_text_view);
        TextView totalQuestionsTextView = findViewById(R.id.total_questions_text_view);
        TextView totalCorrectAnswersTextView = findViewById(R.id.correct_answers_text_view);
        TextView totalIncorrectAnswersTextView = findViewById(R.id.incorrect_answers_text_view);
        TextView totalNotAttemptedTextView = findViewById(R.id.not_attempted_text_view);
        TextView levelNumberTextView = findViewById(R.id.level_text_view);

        ImageView star1 = findViewById(R.id.star1);
        ImageView star2 = findViewById(R.id.star2);
        ImageView star3 = findViewById(R.id.star3);
        ImageView star4 = findViewById(R.id.star4);
        ImageView star5 = findViewById(R.id.star5);


        CardView scoreMessageCardView = findViewById(R.id.card_view_score_message);
        TextView scoreMessageTextView = findViewById(R.id.score_message_text_view);

        // Set the Score summary in the respective fields
        scoreTextView.setText(score);
        totalQuestionsTextView.setText(totalQuestions);
        totalCorrectAnswersTextView.setText(totalCorrectAnswers);
        totalIncorrectAnswersTextView.setText(totalIncorrectAnswers);
        totalNotAttemptedTextView.setText(totalNotAttemptedAnswers);

        levelNumberTextView.setText("Level " + levelNumber);

        // Convert the score to integer
        int quizScore = Integer.valueOf(score);

        /**
        * Condition to check the score and show the message as per performance and
         * also make Message cardview visible which we had set invisible in XML.
         * If score is below 60 then
         * - user should be directed back to the previous level.
         * - the Button Title should be updated to Try Again!
         * - On pressing the button, user should be directed back to the quiz
         **/
        if (quizScore >= 90) {

            scoreMessageCardView.setVisibility(View.VISIBLE);
            scoreMessageTextView.setText("Excellent!!");

            star1.setImageResource(R.drawable.star_green);
            star2.setImageResource(R.drawable.star_green);
            star3.setImageResource(R.drawable.star_green);
            star4.setImageResource(R.drawable.star_green);
            star5.setImageResource(R.drawable.star_green);


        }else if (quizScore < 90 && quizScore >= 70) {

            star1.setImageResource(R.drawable.star_green);
            star2.setImageResource(R.drawable.star_green);
            star3.setImageResource(R.drawable.star_green);
            star4.setImageResource(R.drawable.star_green);

            scoreMessageCardView.setVisibility(View.VISIBLE);
            scoreMessageTextView.setText("Good!!");

        }else if (quizScore < 70 && quizScore >= 60) {

            star1.setImageResource(R.drawable.star_green);
            star2.setImageResource(R.drawable.star_green);
            star3.setImageResource(R.drawable.star_green);

            scoreMessageCardView.setVisibility(View.VISIBLE);
            scoreMessageTextView.setText("Average!!");

        }else if (quizScore < 60 && quizScore >= 40) {

        star1.setImageResource(R.drawable.star_green);
        star2.setImageResource(R.drawable.star_green);

        scoreMessageCardView.setVisibility(View.VISIBLE);
        scoreMessageTextView.setText("You can do Better!!");

        }else {

            star1.setImageResource(R.drawable.star_green);

            scoreTextView.setTextColor(Color.RED);
            scoreMessageCardView.setVisibility(View.VISIBLE);
            scoreMessageTextView.setText("Need To Try Again!!");

            levelButton = findViewById(R.id.level_Button);
            levelButton.setText("TRY AGAIN!!");
        }

    }

    public void levelButtonClicked(View view) {

        levelButton = findViewById(R.id.level_Button);

        if ((levelButton.getText() == "TRY AGAIN!!") || (levelButton.getText() == "RESTART")) {
            tryPreviousLevel();
        }else{
            nextLevel();
        }

    }


    public void tryPreviousLevel() {

        Intent resultIntent = new  Intent(ResultActivity.this, MainActivity.class);
        startActivity(resultIntent);
        finish();

    }

    public void nextLevel() {

        Intent resultIntent = new  Intent(ResultActivity.this, Level2Activity.class);
        startActivity(resultIntent);
        finish();

    }


}
