package com.imed.utils;

import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

/**
 * Created by vinhnguyen.it.vn on 2017, September 25
 */

public class UIUtils {

    /**
     * Appends the character sequence {@code text} and spans {@code what} over the appended part.
     * See {@link Spanned} for an explanation of what the flags mean.
     */
    public static SpannableStringBuilder append(SpannableStringBuilder builder, @Nullable CharSequence text, Object... whats) {
        if (text == null || whats == null || whats.length == 0) return builder;
        int start = builder.length();
        builder.append(text);
        int end = builder.length();
        for (Object what : whats) {
            builder.setSpan(what, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }
}
