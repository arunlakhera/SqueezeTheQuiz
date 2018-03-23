package com.example.android.squeezethequiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Level2Activity extends AppCompatActivity {


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
    String[] questions = {"How many ports are allocated for new emulator?",
                        "Persist data can be stored in Android through",
                        "What are return types of startActivityForResult() in android?",
                        "What is log message in android?",
                        "What is the HTTP response error code status in android?",
            "What is Pending Intent in android?",
            "Can a class be immutable in android?",
            "How to pass the data between activities in Android?",
            "What is the time limit of broadcast receiver in android?",
            "What are the debugging techniques available in android?"
    };

    // String Array to store the Answers
    String[][] answers = {{"2", "0","10", "None of the above"},
            {"Shared Preferences","Internal/External storage","SQlite","All of Above"},
            {"RESULT_OK","RESULT_CANCEL","RESULT_CRASH","RESULT_OK & RESULT_CANCEL"},
            {"Log message is used to debug a program","Same as printf()","Same as Toast()","None of the Above"},
            {"status code < 100","status code > 100","status >&equals; 400","None of the Above"},

            {"It is a kind of an intent","It is used to pass the data between activities","It will fire at a future point of time.","None of the Above"},
            {"No, it can't","Yes, Class can be immutable","Can't make the class as final class","None of the above"},
            {"Intent","Content Provider","Broadcast receiver","None of the Above"},
            {"10 sec","15 sec","5 sec","1 hour"},
            {"DDMS","Breaking point","Memory profiling","All of the Above"}

    };

    // String Array to store the list of correct answers
    String[] correctAnswersList = {"answer1","answer4","answer4","answer1","answer4","answer3","answer2","answer1","answer1","answer4"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // When the Activity loads reset the score and also enable the Submit button

        TextView levelTextView = findViewById(R.id.level_text_view);
        levelTextView.setText("Level 2");
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
                Intent resultIntent = new  Intent(Level2Activity.this, ResultActivity.class);
                resultIntent.putExtra ("score",String.valueOf(score));
                resultIntent.putExtra("totalQuestions",String.valueOf(totalQuestions));
                resultIntent.putExtra("totalCorrectAnswers",String.valueOf(totalCorrectAnswers));
                resultIntent.putExtra("totalIncorrectAnswers",String.valueOf(totalIncorrectAnswers));
                resultIntent.putExtra("totalNotAttemptedAnswers",String.valueOf(totalNotAttemptedAnswers));
                resultIntent.putExtra("levelNumber",String.valueOf(2));
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
