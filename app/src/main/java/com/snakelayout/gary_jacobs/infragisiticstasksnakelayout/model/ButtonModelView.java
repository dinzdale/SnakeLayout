package com.snakelayout.gary_jacobs.infragisiticstasksnakelayout.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;

import com.snakelayout.gary_jacobs.infragisiticstasksnakelayout.BR;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Gary Jacobs on 3/10/16.
 *
 * This is the model view object which binds label and margin sizing information to Button Views defined in
 * the Main Activities layout definition.
 */
public class ButtonModelView extends BaseObservable {

    private List<String> buttonLabelList = new ArrayList<>();
    private List<Integer> buttonMarginList = new ArrayList<>();

    @Bindable
    public List<String> getButtonLabelList() {
        return buttonLabelList;
    }

    @Bindable
    public List<Integer> getButtonMarginList() {
        return buttonMarginList;
    }

    // Binding adapters to set view padding on left, right, top and bottom of view
    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int padding) {
        view.setPadding(padding, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        view.requestLayout();
    }

    @BindingAdapter("android:paddingRight")
    public static void setPaddingRight(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), padding, view.getPaddingBottom());
        view.requestLayout();
    }

    @BindingAdapter("android:paddingTop")
    public static void setPaddingTop(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), padding, view.getPaddingRight(), view.getPaddingBottom());
        view.requestLayout();
    }

    @BindingAdapter("android:paddingBottom")
    public static void setPaddingBottom(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), padding);
        view.requestLayout();
    }

    /**
     * Set button sizing and labels. UI will automatically update.
     *
     * @param index - corresponding button data to update.
     * @param value - corresponding button labels.
     * @param width - corresponding button margin widths.
     */
    public void setButtonValues(int index, String value, int width) {
        if (index >= buttonLabelList.size()) {
            buttonLabelList.add(value);
            buttonMarginList.add(new Integer(width));
        } else {
            buttonLabelList.set(index, value);
            buttonMarginList.set(index, new Integer(width));
        }
        notifyPropertyChanged(BR.buttonLabelList);
        notifyPropertyChanged(BR.buttonMarginList);
    }


}
