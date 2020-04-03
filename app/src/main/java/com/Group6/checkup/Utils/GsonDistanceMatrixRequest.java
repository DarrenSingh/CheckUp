package com.Group6.checkup.Utils;

import com.Group6.checkup.Entities.DistanceMatrixRequest;
import com.Group6.checkup.Entities.DistanceMatrixResponse;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.Group6.checkup.BuildConfig.API_KEY;

public class GsonDistanceMatrixRequest<T> extends Request<T> {

    private final DistanceMatrixRequest requestObj;
    private final Class responseClass = DistanceMatrixResponse.class;
    private final Gson gson = new Gson();
    private final Response.Listener<T> listener;

    public GsonDistanceMatrixRequest(DistanceMatrixRequest requestObj, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, "http://www.mapquestapi.com/directions/v2/routematrix?key="+API_KEY, errorListener);
        this.requestObj = requestObj;
        this.listener = listener;
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        Gson gson = new Gson();
        String json = gson.toJson(requestObj);
        return json.getBytes();
    }

    @Override
    protected void deliverResponse(T response) {
        this.listener.onResponse(response);

    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {

        try {

            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            return Response.success(
                    gson.fromJson(json, this.responseClass),
                    HttpHeaderParser.parseCacheHeaders(response));

            //Boilerplate Try Catch to handle response errors
                // https://developer.android.com/training/volley/request-custom
        } catch (UnsupportedEncodingException e) {

            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {

            return Response.error(new ParseError(e));
        }
    }
}
