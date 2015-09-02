package www.luneyco.com.proxertestapp.view.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import www.luneyco.com.proxertestapp.config.Preferences;

/**
 * A numberpicker to choose a number for settings.
 * Created by TS on 30.08.2015.
 */
public class NumberPickerPreference extends DialogPreference {

    // at max 1 day/1440 minutes
    public static final int MAX_VALUE = Preferences.MAX_UPDATE_RATE;
    // at min 5 minutes.
    public static final int MIN_VALUE = Preferences.MIN_UPDATE_RATE;
    private NumberPicker m_NumberPicker;
    private int          m_Value;

    public NumberPickerPreference(Context _Context, AttributeSet _Attrs) {
        super(_Context, _Attrs);
    }

    public NumberPickerPreference(Context _Context, AttributeSet _Attrs, int _DefStyleAttr) {
        super(_Context, _Attrs, _DefStyleAttr);
    }


    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = Gravity.CENTER;

        m_NumberPicker = new NumberPicker(getContext());
        m_NumberPicker.setLayoutParams(layoutParams);

        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(m_NumberPicker);
        return dialogView;
    }

    @Override
    protected void onBindDialogView(View _View) {
        super.onBindDialogView(_View);
        m_NumberPicker.setMinValue(MIN_VALUE);
        m_NumberPicker.setMaxValue(MAX_VALUE);
        m_NumberPicker.setValue(this.m_Value);
    }

    @Override
    protected void onDialogClosed(boolean _PositiveResult) {
        if (_PositiveResult) {
            int newValue = m_NumberPicker.getValue();
            if (callChangeListener(newValue)) {
                setValue(newValue);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray _A, int _Index) {
        return _A.getInt(_Index, MIN_VALUE);
    }

    @Override
    protected void onSetInitialValue(boolean _RestorePersistedValue, Object _DefaultValue) {
        setValue(_RestorePersistedValue ? getPersistedInt(MIN_VALUE) : (Integer) _DefaultValue);
    }

    public void setValue(int _Value) {
        this.m_Value = _Value;
        persistInt(this.m_Value);
    }

    public int getValue() {
        return this.m_Value;
    }
}
