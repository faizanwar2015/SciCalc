package com.example.resalat.scicalc;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mCalculatorDisplay;
    private static final String DIGITS = "0123456789.";
    private Boolean userIsInTheMiddleOfTypingANumber = false;

    DecimalFormat df = new DecimalFormat("@###########");

    CalculatorBrain mCalculatorBrain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mCalculatorBrain = new CalculatorBrain();

        mCalculatorDisplay = (TextView) findViewById(R.id.textViewDisplay);

        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);

        findViewById(R.id.button0).setOnClickListener( this);
        findViewById(R.id.button1).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.button2).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.button3).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.button4).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.button5).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.button6).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.button7).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.button8).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.button9).setOnClickListener((View.OnClickListener)this);

        findViewById(R.id.buttonAdd).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonSubtract).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonMultiply).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonDivide).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonToggleSign).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonDecimalPoint).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonEquals).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonClear).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonClearMemory).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonAddToMemory).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonSubtractFromMemory).setOnClickListener((View.OnClickListener)this);
        findViewById(R.id.buttonRecallMemory).setOnClickListener((View.OnClickListener)this);

        // The following buttons only exist in layout-land (Landscape mode) and require extra attention.
        // The messier option is to place the buttons in the regular layout too and set android:visibility="invisible".
        if (findViewById(R.id.buttonSquareRoot) != null) {
            findViewById(R.id.buttonSquareRoot).setOnClickListener((View.OnClickListener)this);
        }
        if (findViewById(R.id.buttonSquared) != null) {
            findViewById(R.id.buttonSquared).setOnClickListener((View.OnClickListener)this);
        }
        if (findViewById(R.id.buttonInvert) != null) {
            findViewById(R.id.buttonInvert).setOnClickListener((View.OnClickListener)this);
        }
        if (findViewById(R.id.buttonSine) != null) {
            findViewById(R.id.buttonSine).setOnClickListener((View.OnClickListener)this);
        }
        if (findViewById(R.id.buttonCosine) != null) {
            findViewById(R.id.buttonCosine).setOnClickListener((View.OnClickListener)this);
        }
        if (findViewById(R.id.buttonTangent) != null) {
            findViewById(R.id.buttonTangent).setOnClickListener((View.OnClickListener)this);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }


    public void onClick(View v) {

        String buttonPressed = ((Button) v).getText().toString();
        // String digits = "0123456789.";

        if (DIGITS.contains(buttonPressed)) {

            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) {

//				Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();

                if (buttonPressed.equals(".") && mCalculatorDisplay.getText().toString().contains(".")) {
                    // ERROR PREVENTION
                    // Eliminate entering multiple decimals
//					Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                } else {
                    mCalculatorDisplay.append(buttonPressed);
//					Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                }

            } else {

                if (buttonPressed.equals(".")) {
                    // ERROR PREVENTION
                    // This will avoid error if only the decimal is hit before an operator, by placing a leading zero before the decimal
                    mCalculatorDisplay.setText(0 + buttonPressed);
                } else {
                    mCalculatorDisplay.setText(buttonPressed);
                }

                userIsInTheMiddleOfTypingANumber = true;
            }

        } else {
            // operation was pressed
            if (userIsInTheMiddleOfTypingANumber) {

                mCalculatorBrain.setOperand(Double.parseDouble(mCalculatorDisplay.getText().toString()));
                userIsInTheMiddleOfTypingANumber = false;
            }

            mCalculatorBrain.performOperation(buttonPressed);
            mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables on screen orientation change
        outState.putDouble("OPERAND", mCalculatorBrain.getResult());
        outState.putDouble("MEMORY", mCalculatorBrain.getMemory());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        mCalculatorBrain.setOperand(savedInstanceState.getDouble("OPERAND"));
        mCalculatorBrain.setMemory(savedInstanceState.getDouble("MEMORY"));
        mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
    }


}
