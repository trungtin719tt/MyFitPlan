package mobile.myfitplan.myfitplan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class PopUpLibraryPlus extends AppCompatActivity {
    public Spinner spFoodName;
    public String selectedSpinner;
    public Map<String, String> category = new HashMap<>();
    String FoodID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_library_plus);

        Intent intent = getIntent();
        FoodID = intent.getStringExtra("FoodID");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(height * .4));

        spFoodName = (Spinner)findViewById(R.id.spFoodName);
        final List<String> dataSrc = new ArrayList<>();
//
//        dataSrc.add("Bữa sáng");
//        dataSrc.add("Bữa trưa");
//        dataSrc.add("Bữa tối");



        RequestParams rp = new RequestParams();
//        rp.add("accUserID", String.valueOf(((MyApplication)getActivity().getApplication()).accUser.ID));
//        rp.add("getType", "1");
        HttpUtils http = new HttpUtils();
        String authorization = ((MyApplication) getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        http.client.addHeader("Accept", "application/json");
        http.client.addHeader("Authorization", authorization);
        http.get("api/PersonalCategories", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i = 0; i < response.length(); i++){
                    try{
                        JSONObject obj = response.getJSONObject(i);
                        String name = obj.get("Name").toString();
                        String id = obj.get("ID").toString();
                        dataSrc.add(name);
                        category.put(name, id);


                    }catch (Exception ex){

                    }
                }
                setAdt(dataSrc);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Kiểm tra lại kết nối mạng",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

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

    private void setAdt(List<String> dataSrc){
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

    //nhấn hoàn tất popup
    public void clickToSubmit(View view) {
//        this.finish();
        String categoryName = ((Spinner)findViewById(R.id.spFoodName)).getSelectedItem().toString();
        String categoryID = category.get(categoryName);
        HttpUtils http = new HttpUtils();
        String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        http.client.addHeader("Accept", "application/json");
        http.client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        http.client.addHeader("Authorization", authorization);

        RequestParams rp = new RequestParams();
        rp.add("FoodID", FoodID);
        rp.add("PersonalCategoryID", categoryID);
        http.post("api/CategoryDetails", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Toast.makeText(getApplicationContext(),"Thêm món ăn vào thực đơn thành công",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PopUpLibraryPlus.this, Personal.class));
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Thêm món ăn vào thực đơn thất bại",Toast.LENGTH_LONG).show();
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
