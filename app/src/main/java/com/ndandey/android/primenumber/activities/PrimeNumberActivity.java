

package com.ndandey.android.primenumber.activities;

import com.ndandey.android.primenumber.R;
import com.ndandey.android.primenumber.controllers.PrimeNumberController;
import com.ndandey.android.primenumber.utils.Constants;
import com.ndandey.android.primenumber.utils.GenericUtility;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PrimeNumberActivity extends AppCompatActivity
{

    private MenuItem mSearchItem;
    private String mSearchTxt = null;
    private Boolean bSearchItemCollapsed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prime_number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prime_number, menu);
        // ....Set Search Control.
        mSearchItem = menu.findItem(R.id.menu_search);
        setSearchtext(mSearchItem, this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        // return true;
        // }

        return super.onOptionsItemSelected(item);
    }

    private void setSearchtext(MenuItem searchItem, final Context pContext)
    {
        // ....Get EditText.
        final LinearLayout vLlayout = (LinearLayout) searchItem.getActionView();
        final EditText vEditText = (EditText) vLlayout.findViewById(R.id.etSearch);

        vEditText.setOnKeyListener(new View.OnKeyListener()
        {

            @Override
            public boolean onKey(View v, int pKeyCode, KeyEvent pEvent)
            {
                if (pEvent.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (pKeyCode)
                    {
                    case KeyEvent.KEYCODE_ENTER:
                        // ======================
                        // ....Get query string.
                        String query = null;
                        if (vEditText.getText() != null)
                            query = vEditText.getText().toString();

                        if (!GenericUtility.isStringBlank(query))
                        {
                            GenericUtility.showHideProgress(Boolean.TRUE, pContext);
                            mSearchTxt = query; // ....Set search Text.
                            PrimeNumberController vPrimeNumberController = new PrimeNumberController(pContext);
                            vPrimeNumberController.getPrimeNumber(query, new PrimeNumberHandler());
                        }
                        else if (GenericUtility.isStringBlank(query))
                        {
                            mSearchTxt = null; // ....set search text as null.
                            bSearchItemCollapsed = true; // ....Set Collapsed
                                                         // flag = true;
                        }
                        vEditText.clearFocus();
                        // ....Hide SoftKey Input.
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(vEditText.getWindowToken(), 0);
                        return true;

                    default:
                        // ====
                        break;
                    }
                }

                return false;
            }
        });
    }

    private void hideKeyboard()
    {
        if (mSearchItem != null)
        {
            LinearLayout vLlayout = (LinearLayout) mSearchItem.getActionView();
            EditText vEditText = (EditText) vLlayout.findViewById(R.id.etSearch);

            vEditText.clearFocus();

            // ....Hide soft input.
            InputMethodManager vImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            vImm.hideSoftInputFromWindow(vEditText.getWindowToken(), 0);
        }
    }

    private class PrimeNumberHandler extends Handler
    {

        @Override
        public void handleMessage(Message message)
        {
            GenericUtility.showHideProgress(Boolean.FALSE, PrimeNumberActivity.this);
            Bundle vBundle = message.getData();
            String vPrimeIndex = (String) vBundle.get(Constants.PRIME_INDEX);
            String vSufix = vPrimeIndex.endsWith("1") ? "st" : (vPrimeIndex.endsWith("2") ? "nd" : vPrimeIndex.endsWith("3") ? "rd" : "th");
            String vMessage = String.format("The %s%s Prime Number is : %s", vPrimeIndex, vSufix, vBundle.get(Constants.PRIME_NUMBER));
            GenericUtility.showAlertBox(PrimeNumberActivity.this, vMessage);
        }
    }

}
