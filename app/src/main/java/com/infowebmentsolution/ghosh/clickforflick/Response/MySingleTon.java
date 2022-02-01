package com.infowebmentsolution.ghosh.clickforflick.Response;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleTon {

    private static Context mctx;
    private static MySingleTon mySingleTon;
    private RequestQueue requestQueue = getRequestQueue();


    private MySingleTon(Context context) {
        mctx = context;
    }

    public RequestQueue getRequestQueue() {
        if (this.requestQueue == null) {
            this.requestQueue = Volley.newRequestQueue(mctx.getApplicationContext());
        }
        return this.requestQueue;
    }

    public static synchronized MySingleTon getInstance(Context context) {
        MySingleTon mySingleTon2;
        synchronized (MySingleTon.class) {
            if (mySingleTon == null) {
                mySingleTon = new MySingleTon(context);
            }
            mySingleTon2 = mySingleTon;
        }
        return mySingleTon2;
    }

    public <T> void addToRequestQue(Request<T> request) {
        this.requestQueue.add(request);
    }
}

