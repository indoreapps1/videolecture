package com.example.videolecture.framework;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by lalit on 7/25/2017.
 */
public class ServiceCaller {
    Context context;

    public ServiceCaller(Context context) {
        this.context = context;
    }

//    call category data
    public void callCategoryData(final IAsyncWorkCompletedCallback asyncWorkCompletedCallback){
        final String URL="https://loopfusion.in/videolecture/getCategory.php";
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                asyncWorkCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                asyncWorkCompletedCallback.onDone(error.getMessage(), false);
            }
        });
        requestQueue.add(stringRequest);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }
//    //    call All login data
//    public void callLoginService(final String phone,final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = "http://dnexusapi.veteransoftwares.com/api/Client?mob="+phone;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
////                params.put("email", phone);
////                params.put("password", password);
//                return params;
//            }
//        };
//
////        RequestQueue requestQueue = Volley.newRequestQueue(context);
////        requestQueue.add(stringRequest);
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
////    call otp data
//    public void callOtpService(final String phone, final String otp, final IAsyncWorkCompletedCallback workCompletedCallback){
//        final String url= "http://dnexusapi.veteransoftwares.com/api/VerifyOTP?mob=" +phone+"&otp="+otp;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
////                params.put("email", phone);
////                params.put("password", password);
//                return params;
//            }
//        };
//
////        RequestQueue requestQueue = Volley.newRequestQueue(context);
////        requestQueue.add(stringRequest);
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
//
//    //    call Plan Service
//    public void callPlanService(final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String Url = Contants.SERVICE_BASE_URL + Contants.plans;
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        });
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);
//    }
//
//
//    //    call show all tv
//    public void callShowAllTv(final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = "http://dnexusapi.veteransoftwares.com/api/alldata";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        });
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);
//    }

}
