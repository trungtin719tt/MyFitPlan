package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FillingInfo extends AppCompatActivity {

    private double weight, height;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_info);


    }

    public void clickToNext(View view) {

        try{
            weight = Double.parseDouble (((EditText)findViewById(R.id.weight)).getText().toString());
        }catch (Exception ex){
            flag = false;
        }
        try{
            height = Double.parseDouble (((EditText)findViewById(R.id.height)).getText().toString());
        }catch (Exception ex){
            flag = false;
        }
        RadioGroup genderGroup = findViewById(R.id.radio_gender);
        int radioButtonID = genderGroup.getCheckedRadioButtonId();
        RadioButton genderBtn = genderGroup.findViewById(radioButtonID);
        if (genderBtn.getText().toString().equals("Nam")){
            ((MyApplication)getApplication()).accUser.Gender = "M";
        }else{
            ((MyApplication)getApplication()).accUser.Gender = "F";
        }
        String dob = ((EditText)findViewById(R.id.date_of_birth)).getText().toString();
        if (dob.equals("")){
            flag = false;
        }
        ((MyApplication)getApplication()).accUser.DateOfBirth = dob;
        if (flag == false){
            Toast.makeText(getApplicationContext(),"Vui lòng điền thông tin chính xác",Toast.LENGTH_LONG).show();
            return;
        }

        RequestParams rp = new RequestParams();
//        rp.add("Name", ((MyApplication)getApplication()).accUser.Name);
        rp.add("Gender", ((MyApplication)getApplication()).accUser.Gender);
        rp.add("Purpose", String.valueOf(((MyApplication)getApplication()).accUser.Purpose));
        rp.add("TrainingLevel", String.valueOf(((MyApplication)getApplication()).accUser.TrainingLevel));
        rp.add("DateOfBirth", String.valueOf(((MyApplication)getApplication()).accUser.DateOfBirth));
        rp.add("Email", String.valueOf(((MyApplication)getApplication()).username));
        rp.add("Weight", String.valueOf(weight));
        rp.add("Height", String.valueOf(height));




        HttpUtils httpUtils = new HttpUtils();
        String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        httpUtils.client.addHeader("Accept", "application/json");
        httpUtils.client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpUtils.client.addHeader("Authorization", authorization);
        httpUtils.post("api/AccUsers", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    startActivity(new Intent(FillingInfo.this, MainActivity.class));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Vui lòng điền thông tin chính xác",Toast.LENGTH_LONG).show();
            }

        });
    }

    public void clickToBack(View view) {
        startActivity(new Intent(this, FillingLevel.class));
    }
}
