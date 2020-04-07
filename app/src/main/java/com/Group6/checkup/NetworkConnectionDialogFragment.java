package com.Group6.checkup;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.DialogFragment;

public class NetworkConnectionDialogFragment extends DialogFragment {

    Context context;

    public NetworkConnectionDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public NetworkConnectionDialogFragment(Context context) {
        this.context = context;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("No Network  Detected")
                .setMessage("You are not connected to a network.\n Please establish a connection to continue")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(context,PatientHomeActivity.class));
                    }
                });


        return builder.create();
    }

}

