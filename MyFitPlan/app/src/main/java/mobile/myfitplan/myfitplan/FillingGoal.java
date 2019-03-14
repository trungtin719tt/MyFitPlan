package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

public class FillingGoal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_goal);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        //set title and color for toolbar
//        int textColor = getResources().getColor(R.color.textColorOnPrimary);
//        String сolorString = String.format("%X", textColor).substring(2);
//        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\"'>Chọn mục tiêu </font>", сolorString)));
    }

    public void clickToNext(View view) {
        startActivity(new Intent(this, FillingLevel.class));
    }
}
