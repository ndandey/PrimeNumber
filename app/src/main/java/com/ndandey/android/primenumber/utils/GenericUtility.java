

package com.ndandey.android.primenumber.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;

/**
 * @author ndandey
 * 
 */
public class GenericUtility
{

    private static ProgressDialog mProgress;

    /**
     * Check if 2 strings are equal, eg. null and empty string are equal.
     *
     * @param s1
     *            .
     * @param s2
     *            .
     *
     */
    public static boolean isStringEqual(final String s1, final String s2)
    {
        return equal(s1, s2);
    }

    /**
     * Check if 2 strings are equal, eg. null and empty string are equal.
     *
     * @param s1
     *            .
     * @param s2
     *            .
     */
    public static boolean equal(final String s1, final String s2)
    {
        if (isStringBlank(s1) && isStringBlank(s2))
        {
            return true;
        }
        return s1 != null && s1.equals(s2);
    }

    /**
     * @param pString
     * @return
     */
    public static boolean isStringBlank(final String pString)
    {
        if (pString == null)
        {
            return true;
        }

        if (pString.equals(""))
        {
            return true;
        }

        if (pString.length() == 0)
        {
            return true;
        }

        return false;
    }

    public static void showAlertBox(Context pContext, String message)
    {
        String Ok = "OK";
        String Title = "Prime Number";

        showAlertDialog(pContext, message, Ok, Title);
    }

    public static void showAlertDialog(final Context c, final String message, final String label, final String Title)
    {
        final AlertDialog.Builder adb = new AlertDialog.Builder(c);
        showAlertDialog(adb, message, label, Title);
    }

    public static void showAlertDialog(final AlertDialog.Builder adb, final String message, final String label, final String Title)
    {

        final AlertDialog ad = adb.create();
        ad.setMessage(message);
        ad.setTitle(Title);
        ad.setIcon(android.R.drawable.ic_dialog_alert);
        ad.setButton(DialogInterface.BUTTON_POSITIVE, label, new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(final DialogInterface dialog, final int id)
            {
                dialog.cancel();

            }
        });

        ad.show();
    }

    public static int calculateNoOfColumns(Context context, int scalingFactor)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

    public static void showHideProgress(final boolean showDialog, Context pContext)
    {
        if (showDialog)
        {
            String vMessage = "Please wait...";
            if (mProgress == null)
                mProgress = ProgressDialog.show(pContext, "", vMessage, true);
            ;
        }
        else if (mProgress != null)
        {
            mProgress.dismiss();
            mProgress = null;
        }
    }
}
