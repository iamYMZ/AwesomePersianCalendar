package yma.ymz.AwesomePersianCalendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import yma.ymz.AwesomePersianCalendar.Model.CalendarBuilder;
import yma.ymz.AwesomePersianCalendar.Model.PickDayListener;
import yma.ymz.AwesomePersianCalendar.Model.PickedDay;
import yma.ymz.AwesomePersianCalendar.Model.SquareTextView;

public class Month extends Fragment implements View.OnClickListener {


    View root;
    LinearLayout firstLayout, lastLayout;
    PickDayListener listener;
    PickedDay pickedDay;
    CalendarBuilder builder;

    int CURRENT_MONTH;
    int CURRENT_YEAR;

    boolean hasSelected = false;
    int selectedId;
    View selectedView = null;

    Drawable selectedDay = null;


    public static Month newInstance(int firstDay, int totolDays, PickedDay pickedDay, int currentMonth, int currentYear) {
        Month fragment = new Month();
        Bundle args = new Bundle();
        args.putInt("FIRST", firstDay);
        args.putInt("TOTAL", totolDays);
        args.putInt("YEAR", currentYear);
        args.putInt("MONTH", currentMonth);
        args.putSerializable("PICKED", pickedDay);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.month, container, false);
        int first = getArguments().getInt("FIRST");
        int total = getArguments().getInt("TOTAL");
        CURRENT_MONTH = getArguments().getInt("MONTH");
        CURRENT_YEAR = getArguments().getInt("YEAR");
        Resources res = getResources();

//        selectedDay = getResources().getDrawable(R.drawable.j_selected_day);
        changePickedDayColor(builder.getDayPickedBg(), getActivity());
        pickedDay = ((PickedDay) getArguments().getSerializable("PICKED"));
        selectedId = pickedDay.getId();

        if (pickedDay.getMonth() == CURRENT_MONTH && pickedDay.getYear() == CURRENT_YEAR) {
            hasSelected = true;
        }

        firstLayout = root.findViewById(R.id.firstLayout);
        lastLayout = root.findViewById(R.id.lastLayout);

        first--;

        if (first == 6) {
            firstLayout.setVisibility(View.GONE);
        }
        if (first + total < 35) {
            lastLayout.setVisibility(View.GONE);
        }

        for (int i = 0; i < 42; i++) {
            try {
                int id = res.getIdentifier("d" + i, "id", getContext().getPackageName());
                SquareTextView temp = root.findViewById(id);

                if (i <= first) {
                    temp.setVisibility(View.INVISIBLE);
                } else if (i > first && i <= first + total) {
                    int t = i - first;
                    if (t == selectedId && hasSelected) {
                        temp.setBackground(selectedDay);
                        temp.setTextColor(Color.parseColor("#ffffff"));
                        selectedView = temp;
                    }
                    temp.setText(t + "");
                    temp.setTag(t);
                    temp.setOnClickListener(this);
                } else
                    temp.setVisibility(View.INVISIBLE);
            } catch (Exception e) {

            }
        }

        return root;
    }


    public void dateClickListener(View v) {

    }

    public void setBuilder(CalendarBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void onClick(View view) {
        if (selectedView != null) {
            ((SquareTextView) selectedView).setBackground(null);
            ((SquareTextView) selectedView).setTextColor(Color.parseColor("#000000"));
        }

        ((SquareTextView) view).setBackground(selectedDay);
        ((SquareTextView) view).setTextColor(Color.parseColor("#ffffff"));
        selectedView = view;
        listener.OnDayPicked(((Integer) view.getTag()));
    }

    public void setListener(PickDayListener listener) {
        this.listener = listener;
    }

    public Drawable setBackgroundColor(int color, int source, Context context) {
        GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(source);
        drawable.setColor(color);
        return drawable;
    }

    public void changePickedDayColor(int color, Context context) {
        selectedDay = setBackgroundColor(color, R.drawable.j_selected_day, context);
    }
}
