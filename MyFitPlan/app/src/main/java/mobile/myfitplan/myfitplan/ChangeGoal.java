package mobile.myfitplan.myfitplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ChangeGoal extends AppCompatActivity {

    Spinner spinnerGoal;
    Spinner spinnerTrain;
    private String selectedSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_goal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        spinnerGoal = findViewById(R.id.spGoal);
        List<String> dataSrc = new ArrayList<>();
        dataSrc.add("Tăng 0,25kg 1 tuần");
        dataSrc.add("Tăng 0,5kg 1 tuần");

        spinnerTrain = findViewById(R.id.spTrain);
        List<String> dataTrain = new ArrayList<>();
        dataTrain.add("Nhiều");
        dataTrain.add("Ít");
        dataTrain.add("Không");

        ArrayAdapter<String> dataAdap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataTrain);
        dataAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrain.setAdapter(dataAdap);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataSrc);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoal.setAdapter(dataAdapter);

        spinnerTrain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinner = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinner = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
