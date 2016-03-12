package com.snakelayout.gary_jacobs.infragisiticstasksnakelayout.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.snakelayout.gary_jacobs.infragisiticstasksnakelayout.BR;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Gary Jacobs on 3/10/16.
 */
public class ButtonModelView extends BaseObservable {

    private List<String> buttonLabelList = new ArrayList<>();
    private List<Integer> buttonWidthList = new ArrayList<>();

    @Bindable
    public List<String> getButtonLabelList() {
        return buttonLabelList;
    }

    @Bindable
    public List<Integer> getButtonWidthList() {
        return buttonWidthList;
    }

    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int padding) {
        view.setPadding(padding, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    @BindingAdapter("android:paddingRight")
    public static void setPaddingRight(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), padding, view.getPaddingBottom());
    }

    @BindingAdapter("android:paddingTop")
    public static void setPaddingTop(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), padding, view.getPaddingRight(), view.getPaddingBottom());

    }

    @BindingAdapter("android:paddingBottom")
    public static void setPaddingBottom(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), padding);

    }

    public void setButtonValues(int index, String value, int width) {
        if (index >= buttonLabelList.size()) {
            buttonLabelList.add(value);
            buttonWidthList.add(new Integer(width));
        } else {
            buttonLabelList.set(index, value);
            buttonWidthList.set(index, new Integer(width));
        }
        notifyPropertyChanged(BR.buttonLabelList);
        notifyPropertyChanged(BR.buttonWidthList);
    }


}
