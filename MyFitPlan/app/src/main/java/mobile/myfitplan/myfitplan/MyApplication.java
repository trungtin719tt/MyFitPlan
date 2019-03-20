package mobile.myfitplan.myfitplan;

import android.app.Application;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MyApplication extends Application {

    public final String Url_API = "35.185.29.57:12345";
    public Map<Integer,String> NutritionMap = new HashMap<Integer,String>();
    public String access_token;
    public String token_type;
    public int accUserID;

//    URL url;
//    HttpsURLConnection myConnection;

    public  MyApplication(){
        NutritionMap.put(1, "Protein");
        NutritionMap.put(2, "Fat");
        NutritionMap.put(3, "Carbs");
        NutritionMap.put(4, "Calories");

        accUserID = -1;
//
//        try{
//            // Create URL
//            url = new URL(Url_API);
//
//            // Create connection
//            myConnection = (HttpsURLConnection) url.openConnection();
//        }catch (Exception ex){

//        }

    }
}