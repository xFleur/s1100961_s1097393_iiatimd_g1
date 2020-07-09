package com.example.iiatimd_project_1920.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iiatimd_project_1920.AuthActivity;
import com.example.iiatimd_project_1920.MainActivity;
import com.example.iiatimd_project_1920.R;
import com.example.iiatimd_project_1920.SplashActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {
    private View view;
    private TextInputLayout layoutName, layoutEmail,layoutPassword,layoutConfirm;
    private TextInputEditText txtName, txtEmail,txtPassword,txtConfirm;
    private TextView txtSignIn;
    private Button btnSignUp;
    private ProgressDialog dialog;



    public SignUpFragment () {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_sign_up,container,false);
        init();
        return view;
    }

    private void init(){
        layoutPassword = view.findViewById(R.id.txtLayoutPasswordSignUp);
        layoutName = view.findViewById(R.id.txtLayoutNameSignUp);
        layoutEmail = view.findViewById(R.id.txtLayoutEmailSignUp);
        layoutConfirm = view.findViewById(R.id.txtLayoutConfirmSignUp);
        txtPassword = view.findViewById(R.id.txtPasswordSignUp);
        txtConfirm = view.findViewById(R.id.txtConfirmSignUp);
        txtSignIn = view.findViewById(R.id.txtSignIn);
        txtName = view.findViewById(R.id.txtNameSignUp);
        txtEmail = view.findViewById(R.id.txtEmailSignUp);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignInFragment()).commit();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check velden eerst
                if (validate()) {
                    register();
                }
            }
        });

//        txtSignIn.setOnClickListener(v->{
//            //verander fragments
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignInFragment()).commit();
//        });
//
//        btnSignUp.setOnClickListener(v->{
//            //check velden eerst
//            if(validate()){
//                //
//            }
//        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!txtEmail.getText().toString().isEmpty()){
                    layoutEmail.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtPassword.getText().toString().length()>7){
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtConfirm.getText().toString().equals(txtPassword.getText().toString())){
                    layoutConfirm.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private boolean validate(){
        if(txtEmail.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("email is vereist");
            return false;
        }
        if(txtPassword.getText().toString().length()<8){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("password van minimaal 8 karakters");
            return false;
        }
        if(!txtConfirm.getText().toString().equals(txtPassword.getText().toString())){
            layoutConfirm.setErrorEnabled(true);
            layoutConfirm.setError("password komt niet overeen");
            return false;
        }
        return true;
    }

    private void register() {
        dialog.setMessage("Registering");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, "https://mmherokuapp.herokuapp.com/api/register", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.getBoolean("success")){
                                JSONObject user = object.getJSONObject("user");
                                //make shared pref user
                                SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor = userPref.edit();
                                editor.putString("token",object.getString("token"));
                                editor.putString("name",user.getString("name"));
                                editor.putString("lastname",user.getString("lastname"));
                                editor.apply();
                                //if success
//                                startActivity(new Intent(((AuthActivity.getApplicationContext()),MainActivity.class));
//                                ((AuthActivity))getContext()).finish();
                                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getContext(),"Register Success",Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                        error.printStackTrace();
                        dialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name",txtName.getText().toString());
                map.put("email",txtEmail.getText().toString().trim());
                map.put("password",txtPassword.getText().toString());
                return map;
            }

        };
        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }


    // Post Request For JSONObject
//    public void postData() {
//
//        dialog.setMessage("Please Wait...");
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//
//        JSONObject object = new JSONObject();
//        try {
//            //input your API parameters
//            if(object.getBoolean("success")){
//                JSONObject user = object.getJSONObject("user");
//                //make shared pref user
//                SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
//                SharedPreferences.Editor editor = userPref.edit();
//                editor.putString("token",object.getString("token"));
//                editor.putString("name",user.getString("name"));
//                editor.putString("lastname",user.getString("lastname"));
//                editor.apply();
//                //if success
//                Toast.makeText(getContext(),"Register Success",Toast.LENGTH_SHORT).show();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        // Enter the correct url for your api service site
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "10.0.2.2.:8000/api/register", object,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
////                        Toast.makeText(Login_screen.this,"String Response : "+ response.toString(),Toast.LENGTH_LONG).show();
//                        try {
//                            Log.d("JSON", String.valueOf(response));
//                            dialog.dismiss();
//                            String Error = response.getString("httpStatus");
//                            if (Error.equals("")||Error.equals(null)){
//
//                            }else if(Error.equals("OK")){
//                                JSONObject body = response.getJSONObject("body");
//
//                            }else {
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            dialog.dismiss();
//                        }
////                        resultTextView.setText("String Response : "+ response.toString());
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
//                VolleyLog.d("Error", "Error: " + error.getMessage());
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(jsonObjectRequest);
//    }
}
