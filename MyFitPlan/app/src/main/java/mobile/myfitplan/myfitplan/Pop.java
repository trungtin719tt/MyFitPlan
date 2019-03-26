package mobile.myfitplan.myfitplan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Pop extends AppCompatActivity {
    private Spinner spFoodName;
    private String selectedSpinner;
    private NumberPicker np;
    int quantity = 1;
    private EditText edtKcal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(height * .4));

        //số kcal
        Intent intent = getIntent();
        edtKcal = findViewById(R.id.edtKcal);
        edtKcal.setText(intent.getStringExtra("KCAL"));

    }


    //nhấn dấu -
    public void clickToDecrease(View view) {
        quantity = quantity - 1;

        //số kcal
        Intent intent = getIntent();
        edtKcal = findViewById(R.id.edtKcal);
        int calo = Integer.parseInt(intent.getStringExtra("KCAL"));
        int total = quantity * calo;

        if (quantity < 0) {
            Toast.makeText(this, "Nhập lại", Toast.LENGTH_SHORT).show();
            quantity = 0;
        } else {
            display(quantity, total);
        }

    }

    //nhấn dấu +
    public void clickToIncrease(View view) {
        quantity = quantity + 1;

        //số kcal
        Intent intent = getIntent();
        edtKcal = findViewById(R.id.edtKcal);
        int cal = Integer.parseInt(intent.getStringExtra("KCAL"));
        int total = quantity * cal;
        display(quantity, total);
    }

    //hiển thị số
    private void display(int number, int cal) {
        edtKcal = findViewById(R.id.edtKcal);
        edtKcal.setText("" + cal);

        TextView numberDisplay = (TextView) findViewById(
                R.id.numberDisplay);
        numberDisplay.setText("" + number);
    }

    //nhấn hoàn tất popup
    public void clickToSubmit(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
