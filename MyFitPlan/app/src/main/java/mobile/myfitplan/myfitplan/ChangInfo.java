package mobile.myfitplan.myfitplan;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChangInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Spinner spinnerSex;
    private String selectedSpinner;
    private TextView txtBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_info);

        txtBirthday = findViewById(R.id.txtBirthday);
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR) - 18;
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String date = day + "-" + month + "-" + year;
        txtBirthday.setText(date);

        spinnerSex = findViewById(R.id.spSex);
        List<String> dataSrc = new ArrayList<>(1);
        dataSrc.add("Male");
        dataSrc.add("Female");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataSrc);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(dataAdapter);
        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
        txtBirthday.setText(date);
    }

    public void clickToGetDate(View view) {
        DialogFragment dateFragment = new DayPickerFragment();
        dateFragment.show(getFragmentManager(), "DatePicker");
    }
}
