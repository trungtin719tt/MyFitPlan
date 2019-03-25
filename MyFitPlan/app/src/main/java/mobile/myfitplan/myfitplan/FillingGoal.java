package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        RadioGroup radioGroup = findViewById(R.id.radio_purpose);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroup.findViewById(radioButtonID);
        if (radioButton.getText().toString().equals("Tăng cân")){
            ((MyApplication)getApplication()).accUser.Purpose = 1;
        }
        if (radioButton.getText().toString().equals("Giảm cân")){
            ((MyApplication)getApplication()).accUser.Purpose = -1;
        }
        if (radioButton.getText().toString().equals("Giữ dáng")){
            ((MyApplication)getApplication()).accUser.Purpose = 0;
        }
        startActivity(new Intent(this, FillingLevel.class));

    }
}
