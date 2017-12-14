package com.imed.utils;

import android.content.Context;

import com.imed.R;

import java.util.regex.Pattern;

/**
 * Created by vinhnguyen.it.vn on 2017, September 20
 */

public class ValidationUtils {
    private static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private static final Pattern PHONE = Pattern.compile("^\\+?1?(\\d{10,12}$)");

    private static final int NAME_MINIMUM_LENGTH = 3;
    private static final int PASSWORD_MINIMUM_LENGTH = 6;
    private final Context context;

    public ValidationUtils(Context context) {
        this.context = context;
    }

    public String validateName(String name) {
        if (name == null || name.trim().length() == 0) {
            return context.getString(R.string.error_name_empty);
        }
        if (name.length() < NAME_MINIMUM_LENGTH) {
            return context.getString(R.string.error_name_wrong_format, NAME_MINIMUM_LENGTH);
        }
        return null;
    }

    public String validateEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            return context.getString(R.string.error_email_empty);
        }
        if (!EMAIL_ADDRESS.matcher(email).matches()) {
            return context.getString(R.string.error_email_wrong_format);
        }
        return null;
    }

    public String validatePhone(String phone) {
        if (phone == null || phone.trim().length() == 0) {
            return context.getString(R.string.error_phone_empty);
        }
        if (!PHONE.matcher(phone).matches()) {
            return context.getString(R.string.error_phone_wrong_format);
        }
        return null;
    }

    public String validatePassword(String password) {
        if (password == null || password.length() == 0) {
            return context.getString(R.string.error_password_empty);
        }
        if (password.length() < PASSWORD_MINIMUM_LENGTH) {
            return context.getString(R.string.error_password_wrong_format, PASSWORD_MINIMUM_LENGTH);
        }
        return null;
    }

    public String validateCompany(String company) {
        if (company == null || company.trim().length() == 0) {
            return context.getString(R.string.error_company_empty);
        }
        return null;
    }

    public String validateEvent(String id) {
        if (id == null || id.trim().length() == 0) {
            return context.getString(R.string.error_event_empty);
        }
        return null;
    }

    public String validatePlan(String id) {
        if (id == null || id.trim().length() == 0) {
            return context.getString(R.string.error_plan_empty);
        }
        return null;
    }
}
