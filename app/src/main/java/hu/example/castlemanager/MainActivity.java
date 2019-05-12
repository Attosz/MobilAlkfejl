package hu.example.castlemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarm;
    BroadcastReceiver br;
    ArrayList<KastelySzarny> kastelySzarnyak;
    long choosenCastleWing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView infoText = findViewById(R.id.info_text);
        TextView resultText = findViewById(R.id.result_text);
        loadSharedPreferencies();
        resultText.setText(deleteWingOnCreate(getIntent().getIntExtra("deleteCastleWing",-1)));
        infoText.setText(getIntent().getStringExtra("attackResult"));
        getIntent().removeExtra("deleteCastleWing");
        getIntent().removeExtra("attackResult");
        makeList();
        this.alarm = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        this.br = new AttackReciver();
    }

    private String deleteWingOnCreate(int deleteCastleWing) {
        String result;
        if (deleteCastleWing >= 0) {
            result = kastelySzarnyak.get(deleteCastleWing).getName();
            kastelySzarnyak.remove(deleteCastleWing);
            saveSharedPreferencies();
            return result+" szárny-t lerombolták.";
        } else {
            return "Sikeres védelem";
        }
    }

    public void setAttackTime() {
        int numberOfLovag = 0;
        for (KastelySzarny k : kastelySzarnyak) {
            numberOfLovag += k.getNumberOfLovag();
        }
        int numberOfOpponent = (int) ((Math.random()+0.4)*numberOfLovag);
        Intent intent = new Intent(MainActivity.this,AttackReciver.class);
        intent.putExtra("numberOfLovag", numberOfLovag);
        intent.putExtra("numberOfOpponent", numberOfOpponent);
        intent.putExtra("numberOfkastelySzarnyak", kastelySzarnyak.size());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarm.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP,10*1000, 5*1000, pendingIntent);
    }



    //Kastely szarnyak kilistazasa
    public void makeList() {
        ListAdapter castleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kastelySzarnyak);
        ListView castleListView = findViewById(R.id.castles);
        castleListView.setAdapter(castleAdapter);
        castleListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        choosenCastleWing = id;
                        buyDialog();
                    }
                }
        );
    }

    //Mentes
    private void saveSharedPreferencies() {
        SharedPreferences preferences = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor prefeditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(kastelySzarnyak);
        prefeditor.putString("castle_save", json);
        prefeditor.apply();
    }

    //Mentes betoltese
    private void loadSharedPreferencies () {
        SharedPreferences preferences = getSharedPreferences("save", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("castle_save", null);
        Type type = new TypeToken<ArrayList<KastelySzarny>>() {}.getType();
        kastelySzarnyak = gson.fromJson(json, type);
        if (kastelySzarnyak == null) {
            kastelySzarnyak = new ArrayList<>();
        }
    }


    //Lovag, szoba vasarlas
    private void buyDialog() {

        BuyDialog buyDialog = new BuyDialog();
        buyDialog.show(getSupportFragmentManager(), "Vásarlas");

    }

    public void addLovagToCastleWing () {
        if (kastelySzarnyak.get((int)choosenCastleWing).addLovag()) {
            makeList();
        } else {
            Toast.makeText(getApplicationContext(),"Egy szobába csak 3 Lovag fér be",Toast.LENGTH_SHORT).show();
        }

    }

    public void addSzobaToCastleWing () {
        if (kastelySzarnyak.get((int)choosenCastleWing).addSzoba()) {
            makeList();
        } else {
            Toast.makeText(getApplicationContext(),"Egy Szárnyba Maximum 5 szoba építhető ki",Toast.LENGTH_LONG).show();
        }

    }

    public void addCastleWing(KastelySzarny kastelySzarny) {
        kastelySzarnyak.add(kastelySzarny);
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Kastély szárnak:"+kastelySzarnyak.size(), Toast.LENGTH_LONG);
        toast.show();
    }


    //Kastelyszarny vasarlasa2
    public void buyCastleWing(View view) {
        BuyCastleWing buyCastleWing = new BuyCastleWing();
        buyCastleWing.show(getSupportFragmentManager(), "kastelyszarny_vasarlas");
    }

    @Override
    public void onStop() {
        super.onStop();
        saveSharedPreferencies();
        setAttackTime();

    }



    @Override
    public void onRestart() {
        super.onRestart();
        loadSharedPreferencies();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
