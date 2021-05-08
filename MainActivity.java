package ece.utexas.edu.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final String url = "https://api.openweathermap.org/data/2.5/onecall";
    private final String appid = "278f4661e3672a6f3659c1bdd9608cf0";
    private String lat = "30.267153";
    private String lon = "-97.743057";
    DecimalFormat df = new DecimalFormat("#.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volleyRequest();
    }

    private void volleyRequest(){
        TextView timezone = (TextView) findViewById(R.id.timezone);
        TextView temperature = (TextView) findViewById(R.id.temperature);
        TextView humidity = (TextView) findViewById(R.id.humidity);
        TextView windspeed = (TextView) findViewById(R.id.windspeed);
        TextView pressure = (TextView) findViewById(R.id.pressure);
        TextView description = (TextView) findViewById(R.id.description);
        TextView hourly1 = (TextView) findViewById(R.id.hourly1);
        TextView hourly2 = (TextView) findViewById(R.id.hourly2);
        TextView hourly3 = (TextView) findViewById(R.id.hourly3);
        TextView hourly4 = (TextView) findViewById(R.id.hourly4);
        TextView hourly5 = (TextView) findViewById(R.id.hourly5);
        TextView hourly6 = (TextView) findViewById(R.id.hourly6);
        TextView hourly7 = (TextView) findViewById(R.id.hourly7);
        TextView hourly8 = (TextView) findViewById(R.id.hourly8);
        TextView hourly9 = (TextView) findViewById(R.id.hourly9);
        TextView daily0 = (TextView) findViewById(R.id.daily0);
        TextView daily1 = (TextView) findViewById(R.id.daily1);
        TextView daily2 = (TextView) findViewById(R.id.daily2);
        TextView daily3 = (TextView) findViewById(R.id.daily3);
        TextView daily4 = (TextView) findViewById(R.id.daily4);
        TextView daily5 = (TextView) findViewById(R.id.daily5);
        TextView daily6 = (TextView) findViewById(R.id.daily6);
        TextView daily7 = (TextView) findViewById(R.id.daily7);
        TextView max = (TextView) findViewById(R.id.max);
        TextView min = (TextView) findViewById(R.id.min);
        //api url
        String tempUrl = url+ "?lat="+lat+"&lon="+lon+"&units=imperial&exclude=minutely,alerts&appid="+appid;
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="https://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, tempUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            //timezone
                            timezone.setText(jsonObj.getString("timezone"));
                            //current conditions
                            JSONObject current = jsonObj.getJSONObject("current");
                            temperature.setText(String.valueOf(current.getInt("temp"))+"°F");
                            humidity.setText("Humidity\n"+String.valueOf(current.getInt("humidity"))+"%" );
                            windspeed.setText("Wind Speed\n"+String.valueOf(current.getInt("wind_speed")+"mph") );
                            pressure.setText("Pressure\n"+String.valueOf(current.getInt("pressure"))+"in Hg");
                            //description
                            JSONObject weatherObj = current.getJSONArray("weather").getJSONObject(0);
                            description.setText(weatherObj.getString("description"));
                            //hourly
                            JSONArray hourlyArray = jsonObj.getJSONArray("hourly");
                            for(int i = 1; i < 10; i++){
                                JSONObject hourlyObj = hourlyArray.getJSONObject(i);
                                Long hourTime = hourlyObj.getLong("dt");
                                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                cal.setTimeInMillis(hourTime * 1000L);
                                String time = DateFormat.format("hh:mm", cal).toString();
                                String hourTemp = String.valueOf(hourlyObj.getInt("temp"));
                                //if(i==1){hourly1.setText("Time: "+time+" Temp: "+hourTemp+"°F\n");}
                                if(i==1){hourly1.setText(time+"\n"+hourTemp+"°F");}
                                if(i==2){hourly2.setText(time+"\n"+hourTemp+"°F");}
                                if(i==3){hourly3.setText(time+"\n"+hourTemp+"°F");}
                                if(i==4){hourly4.setText(time+"\n"+hourTemp+"°F");}
                                if(i==5){hourly5.setText(time+"\n"+hourTemp+"°F");}
                                if(i==6){hourly6.setText(time+"\n"+hourTemp+"°F");}
                                if(i==7){hourly7.setText(time+"\n"+hourTemp+"°F");}
                                if(i==8){hourly8.setText(time+"\n"+hourTemp+"°F");}
                                if(i==9){hourly9.setText(time+"\n"+hourTemp+"°F");}
                            }
                            //daily
                            JSONArray dailyArray = jsonObj.getJSONArray("daily");
                            for(int i = 0; i < 8; i++){
                                JSONObject dailyObj = dailyArray.getJSONObject(i);
                                Long dayTime = dailyObj.getLong("dt");
                                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                cal.setTimeInMillis(dayTime * 1000L);
                                String time = DateFormat.format("MM/dd", cal).toString();
                                JSONObject tempDaily = dailyObj.getJSONObject("temp");
                                String dayTemp = String.valueOf(tempDaily.getInt("day"));
                                if(i==0){
                                    max.setText("max\n"+String.valueOf(tempDaily.getInt("max"))+"°F");
                                    min.setText("min\n"+String.valueOf(tempDaily.getInt("min"))+"°F");
                                    daily0.setText(time+"\n"+dayTemp+"°F");
                                }
                                if(i==1){daily1.setText(time+"\n"+dayTemp+"°F");}
                                if(i==2){daily2.setText(time+"\n"+dayTemp+"°F");}
                                if(i==3){daily3.setText(time+"\n"+dayTemp+"°F");}
                                if(i==4){daily4.setText(time+"\n"+dayTemp+"°F");}
                                if(i==5){daily5.setText(time+"\n"+dayTemp+"°F");}
                                if(i==6){daily6.setText(time+"\n"+dayTemp+"°F");}
                                if(i==7){daily7.setText(time+"\n"+dayTemp+"°F");}
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                description.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
