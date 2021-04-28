package com.istinye.week11asynctask;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class DiceThread extends Thread {



    int rollCount = 0, ones = 0, twos= 0, threes = 0, fours = 0, fives = 0, sixes = 0, randomNumber;
    Random random = new Random();

    private TextView resultView;
    private Context context;

    private Handler myHandler;

    public DiceThread(Context context, int rollCount, TextView view){
        this.context = context;
        this.rollCount = rollCount;
        this.resultView = view;

        myHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {

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

        final String result = "Results: " +
                "\n1: " + ones +
                "\n2: " + twos +
                "\n3: " + threes +
                "\n4: " + fours +
                "\n5: " + fives +
                "\n6: " + sixes;



        myHandler.post(new Runnable() {
            @Override
            public void run() {
                resultView.setText(result);
                Toast.makeText(context, "Rolling dice is finished.", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
