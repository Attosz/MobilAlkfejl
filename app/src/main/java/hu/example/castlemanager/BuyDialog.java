package hu.example.castlemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AlertDialogLayout;
import android.view.LayoutInflater;
import android.view.View;

public class BuyDialog extends DialogFragment {


    private CharSequence[] selectableItems = new CharSequence[] {"Lovag", "Szoba"};
    private int choosen = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //View view = inflater.inflate(R.layout.buy_dialog, null);
        //builder.setView(view);
        builder.setTitle("Kastélyszárny bővítés");
        builder.setSingleChoiceItems(selectableItems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choosen = which;
            }
        });
        builder.setPositiveButton("Rendben", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity callingActivity = (MainActivity) getActivity();
                switch (choosen) {
                    case 0:
                        System.out.println("lovag");
                        callingActivity.addLovagToCastleWing();
                        break;
                    case 1:
                        System.out.println("Szoba");
                        callingActivity.addSzobaToCastleWing();
                        break;
                }
            }
        });
        builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
