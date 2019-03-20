package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.net.Uri;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

public class AddingMeal extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView getPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_meal);

        getPhoto = findViewById(R.id.get_photo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //set title and color for toolbar
        int textColor = getResources().getColor(R.color.textColorOnPrimary);
        String сolorString = String.format("%X", textColor).substring(2);
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\"'>Thêm món</font>", сolorString)));
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

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                getPhoto.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void clickToGoToLibrary(View view) {
        startActivity(new Intent(AddingMeal.this, LibraryActivity.class));
    }

    public void clickToAddMeal(View view) {
//        this.finish();
//        startActivity(new Intent(AddingMeal.this, LibraryActivity.class));

        String foodName = ((EditText)findViewById(R.id.edtFoodName)).getText().toString();
        String unit = ((EditText)findViewById(R.id.edtUnit)).getText().toString();
        String calories = ((EditText)findViewById(R.id.edtCalories)).getText().toString();
        String protein = ((EditText)findViewById(R.id.edtProtein)).getText().toString();
        String carbs = ((EditText)findViewById(R.id.edtCarbs)).getText().toString();
        String fat = ((EditText)findViewById(R.id.edtFat)).getText().toString();

        if(foodName.trim().equals("") || calories.trim().equals("") || protein.trim().equals("") || carbs.trim().equals("") || fat.trim().equals("") ){
            Toast.makeText(getApplicationContext(),"Vui lòng điền thông tin đầy đủ!",Toast.LENGTH_LONG).show();
            return;
        }
//        String image = ((EditText)findViewById(R.id.edtFoodName)).getText().toString(); //thêm image sau
        HttpUtils.client.removeHeader("Content-Type");

        HttpUtils.client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        RequestParams rp = new RequestParams();
        rp.add("NameVN", foodName);
        rp.add("Unit", unit);
        rp.add("Protein", protein);
        rp.add("Fat", fat);
        rp.add("Carbs", carbs);
        rp.add("Calories", calories);
        rp.add("FollowedBy", "1");
        HttpUtils.post("api/Foods", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Toast.makeText(getApplicationContext(),"Tạo món ăn mới thành công",Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Tạo món ăn mới thất bại",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clickToChoosePicture(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
    }
}
