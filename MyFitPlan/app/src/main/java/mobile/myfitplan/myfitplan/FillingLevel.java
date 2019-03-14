package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

public class FillingLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_level);


    }

    public void clickToNext(View view) {
        startActivity(new Intent(this, FillingInfo.class));
    }

    public void clickToBack(View view) {
        startActivity(new Intent(this, FillingGoal.class));
    }
}
