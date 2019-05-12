package hu.example.castlemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class BuyCastleWing extends DialogFragment {

    private KastelySzarny kastelySzarny = null;
    private TextInputLayout nameInput;
    private TextInputEditText nameInputText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.buy_kastelyszarny, null);
        builder.setView(view);
        nameInputText = view.findViewById(R.id.name_castlewing_edittext);
        builder.setPositiveButton("Rendben", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                kastelySzarny = new KastelySzarny(nameInputText.getText().toString().trim());
                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.addCastleWing(kastelySzarny);
            }
        });
        builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();


        return builder.create();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy_kastelyszarny, container, false);
        nameInput =  view.findViewById(R.id.name_castlewing);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public KastelySzarny response () {
        return this.kastelySzarny;
    }
}
