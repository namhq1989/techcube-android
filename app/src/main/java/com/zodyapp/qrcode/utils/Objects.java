package com.zodyapp.qrcode.utils;

/**
 * Created by vinhnguyen.it.vn on 2017, September 19
 */

public class Objects {

    public static boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o2 != null && o1.equals(o2);
    }

    public static boolean nonNull(Object o) {
        return o != null;
    }
}
