package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

    }

    public void clickToForgotPassword(View view) {
        startActivity(new Intent(this, ForgotPassword.class));
    }

    public void clickToLogin(View view) {
//        startActivity(new Intent(this, MainActivity.class));
        String username = ((EditText) findViewById(R.id.edtAccount)).getText().toString();
        String password = ((EditText) findViewById(R.id.edtPassword)).getText().toString();

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



                    startActivity(new Intent(LoginPage.this, MainActivity.class));
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
    }

    public void clickToSignUp(View view) {
        startActivity(new Intent(this, SignUp.class));
    }
}
