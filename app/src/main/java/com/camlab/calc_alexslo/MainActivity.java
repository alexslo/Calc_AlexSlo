package com.camlab.calc_alexslo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button mDotButton, mMultiButton, mPlusButton, mMinusButton, mDivButton, mEqualButton;
    private Button mClearButton,mDelButton;
    private Button mDigit0Button, mDigit1Button, mDigit2Button, mDigit3Button, mDigit4Button, mDigit5Button, mDigit6Button, mDigit7Button, mDigit8Button, mDigit9Button;

    private TextView mResultText;

    public static final String APP_PREFERENCES = "session_save_data";
    public static final String APP_PREFERENCES_DATA = "calc_text";

    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intialiseVar
        mEqualButton = (Button) findViewById(R.id.equal);

        mMultiButton = (Button) findViewById(R.id.mul);
        mPlusButton = (Button) findViewById(R.id.plus);
        mMinusButton = (Button) findViewById(R.id.minus);
        mDivButton = (Button) findViewById(R.id.div);
        mDotButton = (Button) findViewById(R.id.dot);

        mClearButton = (Button) findViewById(R.id.clear);
        mDelButton = (Button) findViewById(R.id.delete);

        mDigit0Button = (Button) findViewById(R.id.digit0);
        mDigit1Button = (Button) findViewById(R.id.digit1);
        mDigit2Button = (Button) findViewById(R.id.digit2);
        mDigit3Button = (Button) findViewById(R.id.digit3);
        mDigit4Button = (Button) findViewById(R.id.digit4);
        mDigit5Button = (Button) findViewById(R.id.digit5);
        mDigit6Button = (Button) findViewById(R.id.digit6);
        mDigit7Button = (Button) findViewById(R.id.digit7);
        mDigit8Button = (Button) findViewById(R.id.digit8);
        mDigit9Button = (Button) findViewById(R.id.digit9);

        mResultText = (TextView) findViewById(R.id.result);

        //OnClickListeners:
        mMultiButton.setOnClickListener(this);
        mPlusButton.setOnClickListener(this);
        mMinusButton.setOnClickListener(this);
        mDivButton.setOnClickListener(this);
        mDotButton.setOnClickListener(this);
        mClearButton.setOnClickListener(this);
        mDelButton.setOnClickListener(this);

        mDigit0Button.setOnClickListener(this);
        mDigit1Button.setOnClickListener(this);
        mDigit2Button.setOnClickListener(this);
        mDigit3Button.setOnClickListener(this);
        mDigit4Button.setOnClickListener(this);
        mDigit5Button.setOnClickListener(this);
        mDigit6Button.setOnClickListener(this);
        mDigit7Button.setOnClickListener(this);
        mDigit8Button.setOnClickListener(this);
        mDigit9Button.setOnClickListener(this);

        mEqualButton.setOnClickListener(this);

        mClearButton.setOnClickListener(this);
        mDelButton.setOnClickListener(this);

        //save data:
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View arg0) {
        switch(arg0.getId()) {
            case R.id.digit0:
                updateTextOnView('0');
                break;
            case R.id.digit1:
                updateTextOnView('1');
                break;
            case R.id.digit2:
                updateTextOnView('2');
                break;
            case R.id.digit3:
                updateTextOnView('3');
                break;
            case R.id.digit4:
                updateTextOnView('4');
                break;
            case R.id.digit5:
                updateTextOnView('5');
                break;
            case R.id.digit6:
                updateTextOnView('6');
                break;
            case R.id.digit7:
                updateTextOnView('7');
                break;
            case R.id.digit8:
                updateTextOnView('8');
                break;
            case R.id.digit9:
                updateTextOnView('9');
                break;

            case R.id.mul:
                updateTextOnView('×');
                break;
            case R.id.plus:
                updateTextOnView('+');
                break;
            case R.id.minus:
                updateTextOnView('−');
                break;
            case R.id.div:
                updateTextOnView('÷');
                break;
            case R.id.dot:
                updateTextOnView('.');
                break;
            case R.id.clear:
                mResultText.setText("");
                break;
            case R.id.delete:
                String textBuffer = mResultText.getText().toString();
                if (textBuffer.length() < 1 ) break;
                mResultText.setText(textBuffer.substring(0, textBuffer.length() - 1));
                break;
            case R.id.equal:
                textBuffer = mResultText.getText().toString();
                if (textBuffer.length() < 1 ) break;
                mResultText.setText(String.valueOf(CalcStringParser.calc(textBuffer)));
                break;
        }
    }
    void updateTextOnView(char _additionText) {
        String textBuffer = mResultText.getText().toString();
        if (appendTextValidator(textBuffer, _additionText))
        {
            textBuffer += _additionText;
            mResultText.setText(textBuffer);
        }
    }
    // 5 + 5 = true; 5 * 5 = true; 5 + - 5 = false; 5 * + = false; ...
    boolean appendTextValidator (String _text, char _additionalChar) {
        if (_text.isEmpty()) return true;
        String SignsBuf = "×+−÷.";
        char lastValue = _text.charAt(_text.length() - 1);

        for(int i = 0; i <SignsBuf.length(); i++)
        {
            for(int j = 0; j <SignsBuf.length(); j++)
            {
                if (_additionalChar == SignsBuf.charAt(i) && lastValue == SignsBuf.charAt(j)) return false;
            }
        }
        return true;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        String textBuffer = mResultText.getText().toString();
        //Save App data:
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_DATA, textBuffer);
        editor.apply();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        // Get Saved App Data:
        if (mSettings.contains(APP_PREFERENCES_DATA)) {
            String textBuffer = mSettings.getString(APP_PREFERENCES_DATA, "");
            mResultText.setText(textBuffer);
        }
    }
}
