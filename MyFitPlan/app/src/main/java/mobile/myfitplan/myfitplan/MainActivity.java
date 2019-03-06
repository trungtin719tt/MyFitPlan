package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_selecting_meal);
        startActivity(new Intent(this, SelectingMeal.class));
    }
    //click để hiện popup
//    public void clickToPopUp(View view) {
//        Intent intent = new Intent(this, SelectingMeal.class);
//        startActivity(intent);
//    }
}
