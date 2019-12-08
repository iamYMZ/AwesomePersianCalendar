package yma.ymz.AwesomePersianCalendar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ymz.yma.awesomepersiancalendar.DatePicker;
import ymz.yma.awesomepersiancalendar.Model.CalendarBuilder;
import ymz.yma.awesomepersiancalendar.Model.DatePickedListener;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CalendarBuilder picker = DatePicker.Builder(this, new DatePickedListener() {
            @Override
            public void onDatePicked(int Year, int Month, int Day) {
                Toast.makeText(MainActivity.this, " Year : " + Year + " Month : " + Month + " Day : " + Day, Toast.LENGTH_SHORT).show();
            }
        })
                .setYearRange(1300, 1380)
                .setDefDay(22)
                .setDefMonth(1)
                .setDefYear(1372)
                .setDayPickedColor(Color.parseColor("#BCAAA4"))
                .setCancelable(false)
                .setCloseBackgroundColor(Color.parseColor("#DCE775"))
                .setActionButtons("بستن", "تایید")
                .setConfStrokeColor(Color.parseColor("#673AB7"))
                .setConfTextColor(Color.parseColor("#2196F3"))
                .Build();

        picker.show();

//        picker.show();


    }

}
