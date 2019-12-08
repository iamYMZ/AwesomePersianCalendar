package ymz.yma.awesomepersiancalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ymz.yma.awesomepersiancalendar.Model.CalendarBuilder;
import ymz.yma.awesomepersiancalendar.Model.DatePickedListener;
import ymz.yma.awesomepersiancalendar.Model.CoreCalendar;
import ymz.yma.awesomepersiancalendar.Model.PickDayListener;
import ymz.yma.awesomepersiancalendar.Model.PickedDay;

@SuppressLint("ValidFragment")
public class PersianCalendar extends DialogFragment implements PickDayListener {

    View root;
    FrameLayout pager;
    ImageView pre, next;

    int CURRENT_MONTH;
    int CURRENT_YEAR;
    int MONTH_LENGTH;
    int MONTH_FIRST_DAY;
    Fragment CURRENT_FRAGMENT = null;

    String MONTH_NAME;
    TextView month;
    EditText year;
    TextView conf, close;
    PickedDay pickedDay;
    DatePickedListener listener;
    Integer SelectedColor = null;

    boolean cancelable = false;
    TextWatcher watcher;

    CalendarBuilder builder ;




    public static CalendarBuilder Builder(FragmentActivity context, DatePickedListener listener) {
        return new CalendarBuilder(context,listener);
    }

    @SuppressLint("ValidFragment")
    public PersianCalendar(CalendarBuilder builder ){
        this.builder = builder;
        cancelable = builder.isCancelable() ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.j_date_picker, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
             setCancelable(cancelable);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRtl();
        pager = view.findViewById(R.id.pager);
        pre = view.findViewById(R.id.left);
        next = view.findViewById(R.id.right);
        month = view.findViewById(R.id.month);
        year = view.findViewById(R.id.year);
        conf = view.findViewById(R.id.conf);
        close = view.findViewById(R.id.close);

        month.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/yekan.ttf"));
        year.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/yekan.ttf"));
        close.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/yekan.ttf"));
        conf.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/yekan.ttf"));

        CoreCalendar today = new CoreCalendar();
        CURRENT_MONTH = builder.getDefMonth() == null ? today.getMonth() : builder.getDefMonth();
        CURRENT_YEAR = builder.getDefYear() == null ? today.getYear() : builder.getDefYear();

        int dd = builder.getDefDay() == null ? today.getDay() : builder.getDefDay();
        pickedDay = PickedDay.getInstance(CURRENT_YEAR, CURRENT_MONTH, dd);
        today.getMonthLength();
        getFirstDayOFMonth();

        setActiveFragment(configFragment());

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPreMonth();
                getFirstDayOFMonth();
                setActiveFragment(configFragment());

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextMonth();
                getFirstDayOFMonth();
                setActiveFragment(configFragment());
            }
        });

        setActionButtos(builder.getCancelText(),builder.getConfirmText());
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onDismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        configCloseButton(builder.getCloseBackgroundColor(),builder.getCloseStrokeColor(),builder.getCloseTextColor());
        configConfirmButton(builder.getConfBackgroundColor(),builder.getConfStrokeColor(),builder.getConfTextColor());

        year.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    year.setText("");
            }
        });

          watcher  =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 4){
                    year.removeTextChangedListener(this);
                    year.clearFocus();
                    try {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int tmp = Integer.parseInt(charSequence.toString());
                                if(tmp >= builder.getMinYear() && tmp <= builder.getMaxYear()) {
                                    CURRENT_YEAR = tmp;
                                    getFirstDayOFMonth();
                                    setActiveFragment(configFragment());
                                    year.addTextChangedListener(watcher);
                                }else {
                                    Toast.makeText(getActivity(), "سال را بین " + builder.getMinYear() +" و " + builder.getMaxYear() +" انتخاب کنید", Toast.LENGTH_SHORT).show();
                                    year.setText(CURRENT_YEAR+"");
                                    year.addTextChangedListener(watcher);
                                }
                            }
                        },100);

                    }catch (Exception e ){
                     /*   YoYo.with(Techniques.Tada)
                                .playOn(textView);*/
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        year.addTextChangedListener(watcher);

    }


    protected void setActiveFragment(Fragment fragment) {


        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.pager, fragment, "YMZ");
        ft.commit();
        manager.executePendingTransactions();
        year.setText(CURRENT_YEAR + "");
        month.setText(MONTH_NAME + "");
        //Toast.makeText(getActivity(), " month :  "+ CURRENT_MONTH+ " year : " + CURRENT_YEAR + " total : "+ MONTH_LENGTH + " first : "+MONTH_FIRST_DAY, Toast.LENGTH_LONG).show();
    }

    protected void setRtl() {
        //Locale locale = new Locale("fa");
        //Locale.setDefault(locale);
        //Configuration config = new Configuration();
        //config.locale = locale;
        //getApplicationContext().getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());
        Resources res = getResources();
        Configuration newConfig = new Configuration(res.getConfiguration());
        Locale locale = new Locale("fa");
        newConfig.locale = locale;
        newConfig.setLayoutDirection(locale);
        res.updateConfiguration(newConfig, null);
    }

    protected Fragment configFragment() {
        CURRENT_FRAGMENT = Month.newInstance(MONTH_FIRST_DAY, MONTH_LENGTH, pickedDay, CURRENT_MONTH, CURRENT_YEAR);
        ((Month) CURRENT_FRAGMENT).setListener(this);
        ((Month) CURRENT_FRAGMENT).setBuilder(builder);
        ((Month) CURRENT_FRAGMENT).changePickedDayColor(builder.getDayPickedBg(), getActivity());
        return CURRENT_FRAGMENT;
    }

    private void getFirstDayOFMonth() {
        CoreCalendar jalaliCalendar = new CoreCalendar(CURRENT_YEAR, CURRENT_MONTH, 1);
        MONTH_FIRST_DAY = jalaliCalendar.getDayOfWeek();
        MONTH_NAME = jalaliCalendar.getMonthString();
        MONTH_LENGTH = jalaliCalendar.getMonthLength();

    }

    private void setNextMonth() {
        if (CURRENT_MONTH == 12) {
            CURRENT_MONTH = 1;
            CURRENT_YEAR++;
        } else
            CURRENT_MONTH++;
    }

    private void setPreMonth() {
        if (CURRENT_MONTH == 1) {
            CURRENT_MONTH = 12;
            CURRENT_YEAR--;
        } else
            CURRENT_MONTH--;
    }

    protected void dateClickListener(View v) {
        ((Month) CURRENT_FRAGMENT).dateClickListener(v);
    }

    @Override
    public void OnDayPicked(int day) {
        pickedDay.setId(day);
        pickedDay.setMonth(CURRENT_MONTH);
        pickedDay.setYear(CURRENT_YEAR);
    }

    @Override
    public void onDismiss() {
        if(listener!= null)
        listener.onDatePicked(CURRENT_YEAR, CURRENT_MONTH, pickedDay.getId());
    }

/*    public void onDayPickerColorChange(int color) {
        if (CURRENT_FRAGMENT != null)
            ((Month) CURRENT_FRAGMENT).changePickedDayColor(color, getActivity());
        SelectedColor = color ;

    }*/

    public void setListener(DatePickedListener listener) {
        this.listener = listener;
    }

    public void configCloseButton(int Bgcolor, int StrokeColor, int TextColor) {
        close.setBackground(setBackgroundColor(Bgcolor, StrokeColor, R.drawable.j_rounded_button, getActivity()));
        close.setTextColor(TextColor);
    }

    public void configConfirmButton(int Bgcolor, int StrokeColor, int TextColor) {
        conf.setBackground(setBackgroundColor(Bgcolor, StrokeColor, R.drawable.j_rounded_button, getActivity()));
        conf.setTextColor(TextColor);
    }

    private Drawable setBackgroundColor(int bgcolor, int stColor, int source, Context context) {
        GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(source);
        drawable.setColor(bgcolor);
        drawable.setStroke(1, stColor);
        return drawable;
    }

    public void setActionButtos(String cancel, String con) {

        if(close != null) {
            if (cancel == null)
                close.setVisibility(View.GONE);
            else
                close.setText(cancel+"");
            if (con == null)
                conf.setVisibility(View.GONE);
            else
                conf.setText(con+"");
        }else{

        }
    }

}
