package com.kindleassistant.net;

import android.widget.Toast;

import com.kindleassistant.App;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                RestManager.getInstance().getRetrofit().responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }

    static void showError(Response response) {
        Toast.makeText(
                App.getContext(),
                parseError(response).getMessage(), Toast.LENGTH_SHORT).show();
    }
}
