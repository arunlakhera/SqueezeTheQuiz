package com.example.android.squeezethequiz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.function.Function;

public class MainActivity extends AppCompatActivity {


    // Flags to keep track of which answer button was selected
    boolean answer1Clicked = false;
    boolean answer2Clicked = false;
    boolean answer3Clicked = false;
    boolean answer4Clicked = false;

    // Flag to keep track if the quiz is completed i.e the last question/answer has been submitted
    boolean quizComplete = false;

    // Declaration of Answer button
    Button answer1Button;
    Button answer2Button;
    Button answer3Button;
    Button answer4Button;

    // Variable to keep track of current question number on screen
    int currentQuestionNumber = 0;

    // Variables to keep the number of correct answers, incorrect answers , or not attempted questions
    int totalCorrectAnswers = 0;
    int totalIncorrectAnswers = 0;
    int totalNotAttemptedAnswers = 0;

    int score = 0;


    // String Array to store the Questions
    String[] questions = {"What is an activity in Android?",
            "Which of the following is/are are the subclasses in Android?",
            "On which thread broadcast receivers will work in android?",
            "What is sleep mode in android?",
            "How to get current location in android?",

            "What is a base adapter in android?",
            "How to fix crash using log cat in android?",
            "What are the JSON elements in android?",
            "What is transient data in android?",
            "Can a class be immutable in android?"
    };

    // String Array to store the Answers
    String[][] answers = {{"Activity performs the actions on the screen", "Manage the Application content", "Screen UI", " None of the above"},
            {"Action Bar Activity", "Launcher Activity", "Preference Activity", " All of above"},
            {"Worker Thread", "Main Thread", "Activity Thread", "None of the Above"},
            {"Only Radio interface layer and alarm are in active mode", "Switched off", "Air plane mode", "None of the Above"},
            {"Using with Camera", "Using SMS", "Using location provider", "SQlite"},

            {"Common class for any adapter, can be used for both ListView and spinner", "A kind of adapter", "Data storage space", "None of the above."},
            {"Gmail", "log cat contains the exception name along with the line number", "Google Search", "None of the Above"},
            {"integer, boolean", "boolean", "null", "Number, string, boolean, null, array, and object"},
            {"Permanent data", "Secure Data", "Temporary Data", "Logical Data"},
            {"No, it can't", "Yes, it can", "Can't make class as final class", "None of the Above"}

    };

    // String Array to store the list of correct answers
    String[] correctAnswersList = {"answer1", "answer4", "answer2", "answer1", "answer3", "answer1", "answer2", "answer4", "answer4", "answer2"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // When the Activity loads reset the score and also enable the Submit button

        // Enable the Submit button once the quiz is finished
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setEnabled(true);
        currentQuestionNumber = 0;

        // Call the Show question function
        showQuestion();
    }

    // Function to show the question on screen
    public void showQuestion() {

        // Variable to store the total questions in out question Array
        int totalQuestions = questions.length;

        // Variables to refer to textview in XML to show total number of questions
        TextView questionTotalTextView = findViewById(R.id.question_number_text_view);

        // Variables to refer to textview in XML to show question
        TextView questionTextView = findViewById(R.id.question_text_view);

        //Variable to refer to textview in XML to show score
        TextView scoreTextView = findViewById(R.id.score_text_view);

        // Initialize buttons to refer to answer button in XML
        answer1Button = (Button) findViewById(R.id.answer1_button);
        answer2Button = (Button) findViewById(R.id.answer2_button);
        answer3Button = (Button) findViewById(R.id.answer3_button);
        answer4Button = (Button) findViewById(R.id.answer4_button);

        // Condtion to check if the current question number on screen is less then total number of questions
        if (currentQuestionNumber < (totalQuestions)) {

            // Set the current question number and total number of questions on screen
            questionTotalTextView.setText(String.valueOf(currentQuestionNumber + 1) + "/" + String.valueOf(totalQuestions));

            // Set the current question on question textview in XML
            questionTextView.setText(questions[currentQuestionNumber]);

            // Set the score in score textview in XML

            scoreTextView.setText(String.valueOf("Score:" + score));

            // Set the answers for the current question to answer buttons in XML
            answer1Button.setText(answers[currentQuestionNumber][0]);
            answer2Button.setText(answers[currentQuestionNumber][1]);
            answer3Button.setText(answers[currentQuestionNumber][2]);
            answer4Button.setText(answers[currentQuestionNumber][3]);


        } else {
            // If the last uestion has been answered i.e current question is equal to total questions then mark the quiz complete
            quizComplete = true;
        }

        // Check if the quiz is completed and show the summary to the user
        if (quizComplete) {

            totalIncorrectAnswers = totalQuestions - (totalCorrectAnswers + totalNotAttemptedAnswers);
            callAlertDialog(totalQuestions);

        }

    }


    public void callAlertDialog(final int totalQuestions){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Quiz Completed");
        alertDialogBuilder.setMessage("Thank you for participating. You can now view the result.");
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent resultIntent = new  Intent(MainActivity.this, ResultActivity.class);
                resultIntent.putExtra ("score",String.valueOf(score));
                resultIntent.putExtra("totalQuestions",String.valueOf(totalQuestions));
                resultIntent.putExtra("totalCorrectAnswers",String.valueOf(totalCorrectAnswers));
                resultIntent.putExtra("totalIncorrectAnswers",String.valueOf(totalIncorrectAnswers));
                resultIntent.putExtra("totalNotAttemptedAnswers",String.valueOf(totalNotAttemptedAnswers));
                resultIntent.putExtra("levelNumber",String.valueOf(1));
                startActivity(resultIntent);

                finish();
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
        Function to change the color of the answer button that user selects and if other option is selected then previously selected button is
        changed back to default color

     */
    public void changeButtonColor() {

        answer1Button = (Button) findViewById(R.id.answer1_button);
        answer2Button = (Button) findViewById(R.id.answer2_button);
        answer3Button = (Button) findViewById(R.id.answer3_button);
        answer4Button = (Button) findViewById(R.id.answer4_button);

        // If answer 1 is selected, change its color and the text color else change its color back to default.

        if (answer1Clicked) {
            answer1Button.setBackgroundColor(Color.parseColor("#C51162"));
            answer1Button.setTextColor(Color.parseColor("#FFFFFF"));


        } else {
            answer1Button.setBackgroundColor(Color.parseColor("#BDBDBD"));
            answer1Button.setTextColor(Color.parseColor("#000000"));

        }

        // If answer 2 is selected, change its color and the text color else change its color back to default.

        if (answer2Clicked) {
            answer2Button.setBackgroundColor(Color.parseColor("#C51162"));
            answer2Button.setTextColor(Color.parseColor("#FFFFFF"));

        } else {
            answer2Button.setBackgroundColor(Color.parseColor("#BDBDBD"));
            answer2Button.setTextColor(Color.parseColor("#000000"));

        }

        // If answer 3 is selected, change its color and the text color else change its color back to default.

        if (answer3Clicked) {
            answer3Button.setBackgroundColor(Color.parseColor("#C51162"));
            answer3Button.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            answer3Button.setBackgroundColor(Color.parseColor("#BDBDBD"));
            answer3Button.setTextColor(Color.parseColor("#000000"));

        }

        // If answer 4 is selected, change its color and the text color else change its color back to default.

        if (answer4Clicked) {
            answer4Button.setBackgroundColor(Color.parseColor("#C51162"));
            answer4Button.setTextColor(Color.parseColor("#FFFFFF"));

        } else {
            answer4Button.setBackgroundColor(Color.parseColor("#BDBDBD"));
            answer4Button.setTextColor(Color.parseColor("#000000"));

        }


    }

    /** Function to check which answer button was clicked and change its flag to true and
     * other buttons flag to false and then call changecolor function to change the color of the selected button
     * and set other buttons color to default.
    */


    public void answer1Clicked(View view) {

        answer1Clicked = true;

        answer2Clicked = false;
        answer3Clicked = false;
        answer4Clicked = false;

        changeButtonColor();
    }

    public void answer2Clicked(View view) {

        answer2Clicked = true;

        answer1Clicked = false;
        answer3Clicked = false;
        answer4Clicked = false;

        changeButtonColor();

    }


    public void answer3Clicked(View view) {

        answer3Clicked = true;

        answer1Clicked = false;
        answer2Clicked = false;
        answer4Clicked = false;

        changeButtonColor();
    }


    public void answer4Clicked(View view) {

        answer4Clicked = true;

        answer1Clicked = false;
        answer2Clicked = false;
        answer3Clicked = false;

        changeButtonColor();
    }

    /** Function to execute when submit button is clicked to submit the answer.
    * */
    public void submitClicked(View view) {

        // Change the buttons color to default once the user has answered
        answer1Button.setBackgroundColor(Color.parseColor("#BDBDBD"));
        answer1Button.setTextColor(Color.parseColor("#000000"));
        answer2Button.setBackgroundColor(Color.parseColor("#BDBDBD"));
        answer2Button.setTextColor(Color.parseColor("#000000"));
        answer3Button.setBackgroundColor(Color.parseColor("#BDBDBD"));
        answer3Button.setTextColor(Color.parseColor("#000000"));
        answer4Button.setBackgroundColor(Color.parseColor("#BDBDBD"));
        answer4Button.setTextColor(Color.parseColor("#000000"));

        // Check if the quiz is completed.
        if (!quizComplete) {

            /** If quiz not completed. check the answer button that was clicked and
             * check it with answerlist array and increment the total correct answer variable by 1
            */
            /*
            if (((answer1Clicked) && (correctAnswersList[currentQuestionNumber] == "answer1")) ||
                    ((answer2Clicked) && (correctAnswersList[currentQuestionNumber] == "answer2")) ||
                    ((answer3Clicked) && (correctAnswersList[currentQuestionNumber] == "answer3")) ||
                    ((answer4Clicked) && (correctAnswersList[currentQuestionNumber] == "answer4"))
                    ) {
                totalCorrectAnswers++;
                score = totalCorrectAnswers * 10;
            } else if (!answer1Clicked && !answer2Clicked && !answer3Clicked && !answer4Clicked) {
                totalNotAttemptedAnswers++;
            }
*/

            if ((answer1Clicked) && (correctAnswersList[currentQuestionNumber] == "answer1")) {
                totalCorrectAnswers++;
            }else if ((answer2Clicked) && (correctAnswersList[currentQuestionNumber] == "answer2")) {
                totalCorrectAnswers++;
            }else if ((answer3Clicked) && (correctAnswersList[currentQuestionNumber] == "answer3")) {
                totalCorrectAnswers++;
            }else if ((answer4Clicked) && (correctAnswersList[currentQuestionNumber] == "answer4")) {
                totalCorrectAnswers++;
            }

            if (!answer1Clicked && !answer2Clicked && !answer3Clicked && !answer4Clicked) {
                totalNotAttemptedAnswers++;
            }

            score = totalCorrectAnswers * 10;

            // Increment the current question number if the current question number is not more then total questions
            if (currentQuestionNumber < (questions.length)) {
                // Increment question Number
                currentQuestionNumber++;

            }


            answer1Clicked = false;
            answer2Clicked = false;
            answer3Clicked = false;
            answer4Clicked = false;

            // Show the next Question

            showQuestion();


        } else {
            // Disbale the Submit button once the quiz is finished
            Button submitButton = findViewById(R.id.submit_button);
            submitButton.setEnabled(false);
            currentQuestionNumber = 0;
        }

    }

}
