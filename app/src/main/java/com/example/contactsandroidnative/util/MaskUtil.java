package com.example.contactsandroidnative.util;

import android.widget.TextView;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class MaskUtil {
    
    public static void putMask(TextView textView, String mask){
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter(mask);
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(textView, simpleMaskFormatter);

        textView.addTextChangedListener(maskTextWatcher);
    }
}
