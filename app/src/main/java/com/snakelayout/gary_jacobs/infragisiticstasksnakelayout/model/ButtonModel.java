package com.snakelayout.gary_jacobs.infragisiticstasksnakelayout.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.snakelayout.gary_jacobs.infragisiticstasksnakelayout.BR;

import java.util.Observable;

/**
 * Created by Gary Jacobs on 3/9/16.
 */
public class ButtonModel extends BaseObservable {

    private String buttonLabel = "buttonLabel";

    @Bindable
    public String getButtonLabel() {
        return buttonLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
        notifyPropertyChanged(BR.buttonLabel);
    }
}
