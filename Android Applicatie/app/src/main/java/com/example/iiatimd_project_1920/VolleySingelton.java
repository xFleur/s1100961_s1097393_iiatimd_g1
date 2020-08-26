package com.example.iiatimd_project_1920;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingelton {


    private static VolleySingelton instance;
    private RequestQueue requestQueue;
    private static Context ctx;


    private VolleySingelton(Context context){
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingelton getInstance(Context context) {
        if (instance == null ){
            instance = new VolleySingelton(context);
        }
        return instance;
    }
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}


