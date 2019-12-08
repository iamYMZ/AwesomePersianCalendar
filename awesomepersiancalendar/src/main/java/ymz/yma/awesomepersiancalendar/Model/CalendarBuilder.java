package ymz.yma.awesomepersiancalendar.Model;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import ymz.yma.awesomepersiancalendar.PersianCalendar;
import ymz.yma.awesomepersiancalendar.R;

public class CalendarBuilder {
    private FragmentActivity context;
    private PersianCalendar datePicker;
    private boolean isShowing = false;

    String cancelText = "بستن", confirmText = "تایید";
    int closeBackgroundColor = android.R.color.transparent,
            closeStrokeColor = Color.parseColor("#17C97B"),
            closeTextColor = Color.parseColor("#17C97B");
    int confBackgroundColor = android.R.color.transparent,
            confStrokeColor = Color.parseColor("#17C97B"),
            confTextColor = Color.parseColor("#17C97B");
    boolean cancelable = false;
    int dayPickedColor = R.drawable.j_selected_day;
    DatePickedListener listener;

    Integer defYear ;
    Integer defDay ;
    Integer defMonth ;
    int minYear = 1000 ;
    int maxYear = 9999;

    public CalendarBuilder(FragmentActivity context, DatePickedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public CalendarBuilder Build() {
        datePicker = new PersianCalendar(this);
        datePicker.setListener(listener);
        return this;
    }

    public CalendarBuilder setDayPickedColor(int color) {
        this.dayPickedColor = color;
        return this;
    }

    public CalendarBuilder setCancelable(boolean state) {
        this.cancelable = state;
        // datePicker.setCancelable(false);
        return this;
    }

  /*  public Builder configConfirmButton(int backgroundColor, int strokeColor, int textColor) {
        this.confBackgroundColor = backgroundColor;
        this.confStrokeColor = strokeColor;
        this.confTextColor = textColor;
        return this;
    }

    public Builder configCancelButton(int backgroundColor, int strokeColor, int textColor) {
        this.closeBackgroundColor = backgroundColor;
        this.closeStrokeColor = strokeColor;
        this.closeTextColor = textColor;
        return this;
    }*/

    public CalendarBuilder setActionButtons(String cancelText, String confirmText) {
        this.cancelText = cancelText;
        this.confirmText = confirmText;

        return this;
    }

    public String getCancelText() {
        return cancelText;
    }

    public String getConfirmText() {
        return confirmText;
    }

    public int getCloseBackgroundColor() {
        return closeBackgroundColor;
    }

    public int getCloseStrokeColor() {
        return closeStrokeColor;
    }

    public int getCloseTextColor() {
        return closeTextColor;
    }

    public int getConfBackgroundColor() {
        return confBackgroundColor;
    }

    public int getConfStrokeColor() {
        return confStrokeColor;
    }

    public int getConfTextColor() {
        return confTextColor;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public int getDayPickedBg() {
        return dayPickedColor;
    }

    public CalendarBuilder setCancelText(String cancelText) {
        this.cancelText = cancelText;
        return this;
    }

    public CalendarBuilder setConfirmText(String confirmText) {
        this.confirmText = confirmText;
        return this;
    }

    public CalendarBuilder setCloseBackgroundColor(int closeBackgroundColor) {
        this.closeBackgroundColor = closeBackgroundColor;
        return this;
    }

    public CalendarBuilder setCloseStrokeColor(int closeStrokeColor) {
        this.closeStrokeColor = closeStrokeColor;
        return this;
    }

    public CalendarBuilder setCloseTextColor(int closeTextColor) {
        this.closeTextColor = closeTextColor;
        return this;
    }

    public CalendarBuilder setConfBackgroundColor(int confBackgroundColor) {
        this.confBackgroundColor = confBackgroundColor;
        return this;
    }

    public CalendarBuilder setConfStrokeColor(int confStrokeColor) {
        this.confStrokeColor = confStrokeColor;
        return this;
    }

    public CalendarBuilder setConfTextColor(int confTextColor) {
        this.confTextColor = confTextColor;
        return this;
    }

    public CalendarBuilder setDefDay(Integer defDay) {
        this.defDay = defDay;
        return this;
    }

    public CalendarBuilder setDefYear(Integer defYear) {
        this.defYear = defYear;
        return this;
    }

    public CalendarBuilder setDefMonth(Integer defMonth) {
        this.defMonth = defMonth;
        return this;
    }

    public Integer getDefYear() {
        return defYear;
    }

    public Integer getDefDay() {
        return defDay;
    }

    public Integer getDefMonth() {
        return defMonth;
    }

    public void show() {
        FragmentManager fm = context.getSupportFragmentManager();
        datePicker.show(fm, "datePicker");
        isShowing = true ;
    }

    public void dismiss(){
        if(datePicker!=null && isShowing){
            datePicker.dismiss();
        }
    }

    public CalendarBuilder setYearRange(int min , int max){
        this.minYear = min ;
        this.maxYear = max ;
        return this;
    }

    public int getMinYear() {
        return minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

}
