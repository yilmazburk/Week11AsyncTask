package com.istinye.week11asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText myEditTextView;
    private Button rollButton;
    private TextView resultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myEditTextView = (EditText) findViewById(R.id.myEditText);
        rollButton = (Button) findViewById(R.id.rollButton);
        resultsTextView = (TextView) findViewById(R.id.resultsTextView);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = myEditTextView.getText().toString();
                if (input.isEmpty()) return;


                int rollCount = 0;
                try {
                    rollCount = Integer.valueOf(input.trim());
                } catch (NumberFormatException ex) {
                    Toast.makeText(getApplicationContext(), "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                    return;
                }

                new DiceAsyncTask().execute(rollCount);

                /*DiceThread diceThread = new DiceThread(MainActivity.this, rollCount, resultsTextView);
                diceThread.start();*/

                /*int ones = 0, twos= 0, threes = 0, fours = 0, fives = 0, sixes = 0, randomNumber;
                Random random = new Random();

                for (int i = 0; i<rollCount; i++) {
                    randomNumber = random.nextInt(6) + 1;

                    switch (randomNumber) {
                        case 1:
                            ones++;
                            break;
                        case 2:
                            twos++;
                            break;
                        case 3:
                            threes++;
                            break;
                        case 4:
                            fours++;
                            break;
                        case 5:
                            fives++;
                            break;
                        default:
                            sixes++;
                    }
                }

                String result = "Results: " +
                        "\n1: " + ones +
                        "\n2: " + twos +
                        "\n3: " + threes +
                        "\n4: " + fours +
                        "\n5: " + fives +
                        "\n6: " + sixes;

                resultsTextView.setText(result);


                Toast.makeText(getApplicationContext(), "Rolling dice is finished.", Toast.LENGTH_SHORT).show();*/
            }
        });

    }

    public class DiceAsyncTask extends AsyncTask<Integer, Integer, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(Integer.valueOf(myEditTextView.getText().toString()));
            dialog.show();
        }

        @Override
        protected String doInBackground(Integer... params) {
            int ones = 0, twos= 0, threes = 0, fours = 0, fives = 0, sixes = 0, randomNumber;
            Random random = new Random();

            double currentProgress = 0;
            double previousProgress = 0;

            int rollCount = params[0];

            for (int i = 0; i<rollCount; i++) {
                randomNumber = random.nextInt(6) + 1;

                currentProgress = (double) i/rollCount;

                if (currentProgress - previousProgress >= 0.02) {
                    publishProgress(i);
                    previousProgress = currentProgress;
                }

                switch (randomNumber) {
                    case 1:
                        ones++;
                        break;
                    case 2:
                        twos++;
                        break;
                    case 3:
                        threes++;
                        break;
                    case 4:
                        fours++;
                        break;
                    case 5:
                        fives++;
                        break;
                    default:
                        sixes++;
                }
            }

            String result = "Results: " +
                    "\n1: " + ones +
                    "\n2: " + twos +
                    "\n3: " + threes +
                    "\n4: " + fours +
                    "\n5: " + fives +
                    "\n6: " + sixes;

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            resultsTextView.setText(result);
            Toast.makeText(getApplicationContext(), "Rolling dice is finished.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            dialog.setProgress(progress[0]);
        }
    }

}