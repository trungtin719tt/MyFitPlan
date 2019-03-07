package mobile.myfitplan.myfitplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        Intent  intent = new Intent(this, Personal.class);
        startActivity(intent);
    }
    //click để hiện popup
    public void clickToPopUpNewFolder(View view) {
        Intent intent = new Intent(this, NewFolder.class);
        startActivity(intent);
    }
}
