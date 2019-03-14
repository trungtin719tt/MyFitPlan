package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

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
        startActivity(new Intent(this, MainActivity.class));
    }

    public void clickToSignUp(View view) {
        startActivity(new Intent(this, SignUp.class));
    }
}
