package mobile.myfitplan.myfitplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class NewFolder extends AppCompatActivity {
    private Spinner spFoodName;
    private String selectedSpinner;
    private NumberPicker np;
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_folder);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(height * .3));


//        spFoodName = (Spinner)findViewById(R.id.spFoodName);
//        List<String> dataSrc = new ArrayList<>();
//
//        dataSrc.add("Bữa sáng");
//        dataSrc.add("Bữa trưa");
//        dataSrc.add("Bữa tối");
//
//        //danh sách muốn hiện lên control phải có adapter
//        ArrayAdapter<String> dataAdp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataSrc);
//        dataAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spFoodName.setAdapter(dataAdp);
//        spFoodName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedSpinner = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }
//    //nhấn dấu -
//    public void clickToDecrease(View view) {
//        Button decrement = (Button) findViewById(R.id.decrement);
//        quantity = quantity - 1;
//        display(quantity);
//    }
//
//    //nhấn dấu +
//    public void clickToIncrease(View view) {
//        Button increment = (Button) findViewById(R.id.increment);
//        quantity = quantity + 1;
//        display(quantity);
//    }
//
//    //hiển thị số
//    private void display(int number) {
//        TextView numberDisplay = (TextView) findViewById(
//                R.id.numberDisplay);
//        numberDisplay.setText("" + number);
//    }
//
//
    public void clickToSubmit(View view) {

        String folderName = ((TextView)findViewById(R.id.txt_folder_name)).getText().toString();
        String accUserID = String.valueOf(((MyApplication)getApplication()).accUser.ID);
        HttpUtils http = new HttpUtils();
        String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        http.client.addHeader("Accept", "application/json");
        http.client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        http.client.addHeader("Authorization", authorization);

        RequestParams rp = new RequestParams();
        rp.add("Name", folderName);
        http.post("api/PersonalCategories", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Toast.makeText(getApplicationContext(),"Tạo mới thư mục thành công",Toast.LENGTH_LONG).show();
                    Refresh();
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Tạo mới thư mục thất bại",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public void Refresh(){
        startActivity(new Intent(this, Personal.class));

    }

}
