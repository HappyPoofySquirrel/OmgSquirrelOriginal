package com.example.sleazypmartini.omgsquirrel2.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;

import com.example.sleazypmartini.omgsquirrel2.R;


public class DialogFactory {



    public static ProgressDialog newDialog(Context mContext, String msg) {
        return newDialog(mContext, "Please Wait", msg);
    }


    public static ProgressDialog newDialog(Context mContext, String title, String msg) {
//        if (act == null || act.isFinishing()) {
//            return null;
//        }
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return dialog;
    }

    public static AlertDialog newOneBtnAlert(Context mContext, String msg) { // String title,
//        if (act == null || act.isFinishing()) {
//            return null;
//        }
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setMessage(msg)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        dialog.show();
        return dialog;
    }





    public static ProgressDialog newGenericDialog(Context mContext){

        ProgressDialog progressDialog = new ProgressDialog(mContext, R.style.MyProgressDialogTheme);
        progressDialog.setProgressStyle(R.style.CustomAlertDialogStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        return progressDialog;

    }

    public static void dismiss(Dialog dialog) {
        if ((dialog != null) && dialog.isShowing()) {
            Context context = ((ContextWrapper) dialog.getContext()).getBaseContext();
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing())
                    try {
                        dialog.dismiss();
                    } catch (final IllegalArgumentException e) {
                    }

            } else {
                dialog.dismiss();
            }

        }
    }
}
