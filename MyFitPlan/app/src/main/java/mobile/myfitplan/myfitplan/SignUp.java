package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //set title and color for toolbar
        int textColor = getResources().getColor(R.color.textColorOnPrimary);
        String сolorString = String.format("%X", textColor).substring(2);
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\"'>Đăng ký </font>", сolorString)));
    }

    public void clickToConfirm(View view) {
//        startActivity(new Intent(this, Confirm.class));
        String username = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.confirm_password)).getText().toString();

        RequestParams rp = new RequestParams();
        rp.add("Email", username);
        rp.add("Password", password);
        rp.add("ConfirmPassword", confirmPassword);

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.post("api/Account/Register", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    String username = serverResp.getString("Email");
                    String password = serverResp.getString("Password");
                    RequestParams rp = new RequestParams();
                    rp.add("username", username);
                    rp.add("password", password);
                    rp.add("grant_type", "password");

                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.post("Token", rp, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                JSONObject serverResp = new JSONObject(response.toString());
                                ((MyApplication) getApplication()).access_token =  serverResp.get("access_token").toString();
                                ((MyApplication) getApplication()).token_type =  serverResp.get("token_type").toString();
                                ((MyApplication) getApplication()).username =  serverResp.get("userName").toString();

//                    String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
//                    HttpUtils.client.addHeader("Accept", "application/json");
//                    HttpUtils.client.addHeader("Content-Type", "application/json");
//                    HttpUtils.client.addHeader("Authorization", authorization);



                                startActivity(new Intent(SignUp.this, FillingGoal.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không đúng!",Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Tạo tài khoản thất bại",Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void clickToBack(View view) {
//        startActivity(new Intent(this, LoginPage.class));
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
