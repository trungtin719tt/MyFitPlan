package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

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

    public void clickToChangePassword(View view) {
        startActivity(new Intent(this, Personal.class));
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
}
