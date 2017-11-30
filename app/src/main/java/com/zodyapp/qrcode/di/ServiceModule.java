package com.zodyapp.qrcode.di;

import android.app.Application;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.zodyapp.qrcode.R;
import com.zodyapp.qrcode.api.LiveDataCallAdapterFactory;
import com.zodyapp.qrcode.service.AppService;
import com.zodyapp.qrcode.utils.SPUtils;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vinhnguyen.it.vn on 2017, September 21
 */

@Module
public class ServiceModule {

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

                    DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

                    @Override
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        String s = json.getAsString();
                        try {
                            return formatter.parseDateTime(s).toDate();
                        } catch (IllegalArgumentException e) {
                            return null;
                        }
                    }
                })
                .create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Application app, SPUtils spUtils, Gson gson) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    String token = spUtils.getToken();
                    Request request = chain.request();
                    Request.Builder requestBuilder = request.newBuilder();
                    if (token != null) {
                        String authorization = String.format("Bearer %s", token);
                        requestBuilder.addHeader("Authorization", authorization)
                                .build();
                    }
                    return chain.proceed(requestBuilder.build());
                });

        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        String url = Uri.parse(app.getString(R.string.host))
                .buildUpon()
                .appendPath("")
                .build().toString();
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();
    }

    @Singleton
    @Provides
    AppService provideMerchantService(Retrofit retrofit) {
        return retrofit.create(AppService.class);
    }
}
