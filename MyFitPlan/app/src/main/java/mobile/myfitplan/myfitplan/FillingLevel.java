package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FillingLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_level);


    }

    public void clickToNext(View view) {
        RadioGroup radioGroup = findViewById(R.id.radio_lvl);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroup.findViewById(radioButtonID);
        if (radioButton.getText().toString().equals("Nhiều")){
            ((MyApplication)getApplication()).accUser.TrainingLevel = 2;
        }
        if (radioButton.getText().toString().equals("Ít")){
            ((MyApplication)getApplication()).accUser.TrainingLevel = 1;
        }
        if (radioButton.getText().toString().equals("Không có")){
            ((MyApplication)getApplication()).accUser.TrainingLevel = 0;
        }
        startActivity(new Intent(this, FillingInfo.class));
    }

    public void clickToBack(View view) {
        startActivity(new Intent(this, FillingGoal.class));
    }
}
