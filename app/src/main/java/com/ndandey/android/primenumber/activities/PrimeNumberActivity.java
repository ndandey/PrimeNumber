

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
        final Context vContext = this;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prime_number, menu);
        // ....Set Search Control.
        MenuItem mSearchItem = menu.findItem(R.id.menu_search);
        setSearchtext(mSearchItem, vContext);

        return true;
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
                            PrimeNumberController vPrimeNumberController = new PrimeNumberController(pContext);
                            vPrimeNumberController.getPrimeNumber(query, new PrimeNumberHandler());
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
