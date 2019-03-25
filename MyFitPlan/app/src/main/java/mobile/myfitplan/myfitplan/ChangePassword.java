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

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //set title and color for toolbar
        int textColor = getResources().getColor(R.color.textColorOnPrimary);
        String сolorString = String.format("%X", textColor).substring(2);
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\"'>Đổi mật khẩu</font>", сolorString)));
    }

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

    public void clickToChangePassword(View view) {
        RequestParams rp = new RequestParams();
        rp.add("OldPassword", ((EditText)findViewById(R.id.edtOldPassword)).getText().toString());
        rp.add("NewPassword", ((EditText)findViewById(R.id.edtNewPassword)).getText().toString());
        rp.add("ConfirmPassword", ((EditText)findViewById(R.id.edtReenterPassword)).getText().toString());

        HttpUtils httpUtils = new HttpUtils();
        String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        httpUtils.client.addHeader("Accept", "application/json");
        httpUtils.client.addHeader("Authorization", authorization);
        httpUtils.post("api/Account/ChangePassword", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Toast.makeText(getApplicationContext(),"Đổi mật khẩu thành công",Toast.LENGTH_LONG).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Đổi mật khẩu thất bại",Toast.LENGTH_LONG).show();
            }
        });
        this.finish();
    }
}
