package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

public class FillingInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_info);


    }

    public void clickToNext(View view) {
        startActivity(new Intent(this, FillingWeeklyGoal.class));
    }

    public void clickToBack(View view) {
        startActivity(new Intent(this, FillingLevel.class));
    }
}
