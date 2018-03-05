package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;

import java.util.Calendar;

/**
 * Display a day of the week
 */
@Experimental
@SuppressLint("ViewConstructor")
class WeekDayView extends TextView {

    private WeekDayFormatter formatter = WeekDayFormatter.DEFAULT;
    private int dayOfWeek;

    public WeekDayView(Context context, int dayOfWeek) {
        super(context);

        setGravity(Gravity.CENTER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }

        setDayOfWeek(dayOfWeek);
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        this.formatter = formatter == null ? WeekDayFormatter.DEFAULT : formatter;
        setDayOfWeek(dayOfWeek);
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        setText(formatter.format(dayOfWeek));
    }

    public void setDayOfWeek(Calendar calendar) {
        setDayOfWeek(CalendarUtils.getDayOfWeek(calendar));
    }

    @Override
    public void setTextAppearance(int resId) {
        super.setTextAppearance(resId);
        applyCustomTypeface(resId);
    }

    @Override
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        applyCustomTypeface(resId);
    }

    private void applyCustomTypeface(int resId) {
        TypedArray appearance = getContext().obtainStyledAttributes(resId, R.styleable.MaterialCalendarView);
        if(appearance != null) {
            String fontPath = appearance.getString(R.styleable.MaterialCalendarView_mcv_textAppearanceFontPath);
            if(!TextUtils.isEmpty(fontPath)) {
                try {
                    setTypeface(Typeface.createFromAsset(getResources().getAssets(), fontPath));
                }
                catch (Exception ex) {
                    Log.e("WeekDayView", "cannot apply custom font path", ex);
                }
            }
        }
    }
}
