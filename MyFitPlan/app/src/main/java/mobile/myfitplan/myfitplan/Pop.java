package mobile.myfitplan.myfitplan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Pop extends AppCompatActivity {
    private Spinner spFoodName;
    private String selectedSpinner;
    private NumberPicker np;
    int quantity = 1;
    Date lunch, breakfast, dinner;
    String FoodID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        Intent intent = getIntent();
        FoodID = intent.getStringExtra("FoodID");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(height * .4));



        spFoodName = (Spinner)findViewById(R.id.spFoodName);
        List<String> dataSrc = new ArrayList<>();

        dataSrc.add("Bữa sáng");
        dataSrc.add("Bữa trưa");
        dataSrc.add("Bữa tối");

        //danh sách muốn hiện lên control phải có adapter
        ArrayAdapter<String> dataAdp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataSrc);
        dataAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFoodName.setAdapter(dataAdp);
        spFoodName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinner = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    //nhấn dấu -
    public void clickToDecrease(View view) {
        Button decrement = (Button) findViewById(R.id.decrement);
        quantity = quantity - 1;
        display(quantity);
    }

    //nhấn dấu +
    public void clickToIncrease(View view) {
        Button increment = (Button) findViewById(R.id.increment);
        quantity = quantity + 1;
        display(quantity);
    }

    //hiển thị số
    private void display(int number) {
        TextView numberDisplay = (TextView) findViewById(
                R.id.numberDisplay);
        numberDisplay.setText("" + number);
    }

    //nhấn hoàn tất popup
    public void clickToSubmit(View view) {
        final Calendar calendar = Calendar.getInstance();
        Date t = new Date();
        int day          = Integer.parseInt(DateFormat.format("dd", t).toString()); // 20
        int monthNumber  = Integer.parseInt(DateFormat.format("MM", t).toString()); // 06
        int year         = Integer.parseInt(DateFormat.format("yyyy", t).toString());  // 2013
//        calendar.setTime(new Date(year, monthNumber, day));
        calendar.set(year, monthNumber - 1, day, 0, 0);
        calendar.add(Calendar.HOUR, 10);
        breakfast = calendar.getTime();
        calendar.add(Calendar.HOUR, 2);
        lunch = calendar.getTime();
        calendar.add(Calendar.HOUR, 6);
        dinner = calendar.getTime();
        String Quantity = ((TextView)findViewById(R.id.numberDisplay)).getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentDate = sdf.format(new Date());
        String time;
        if (((Spinner)findViewById(R.id.spFoodName)).getSelectedItem().toString().equals("Bữa sáng")){
            time = sdf2.format(breakfast);
        }else if (((Spinner)findViewById(R.id.spFoodName)).getSelectedItem().toString().equals("Bữa trưa")){
            time = sdf2.format(lunch);
        }else {
            time = sdf2.format(dinner);
        }

        HttpUtils http = new HttpUtils();
        String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        http.client.addHeader("Accept", "application/json");
        http.client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        http.client.addHeader("Authorization", authorization);

        RequestParams rp = new RequestParams();
        rp.add("FoodID", FoodID);
        rp.add("AccUserID", String.valueOf(((MyApplication)getApplication()).accUser.ID));
        rp.add("Quantity", Quantity);
        rp.add("Time", time);
        rp.add("Date", currentDate);
        http.post("api/Diaries", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Toast.makeText(getApplicationContext(),"Thêm món ăn vào nhật ký thành công",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Pop.this, DiaryActivity.class));
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Thêm món ăn vào nhật ký thất bại",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }
        });

    }
}
