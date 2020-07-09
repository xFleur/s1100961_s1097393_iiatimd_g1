package com.example.iiatimd_project_1920;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class UserActivity extends AppCompatActivity {

    private TextInputLayout layoutName, layoutEmail;
    private TextInputEditText txtName, txtEmail;
    private Button btnLogout;

    private Bitmap bitmap = null;
    private SharedPreferences userPref;
    private ProgressDialog dialog;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        init();

    }

    private void init() {
        //preferences = getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        layoutEmail = findViewById(R.id.txtLayoutEmailUserInfo);
        layoutName = findViewById(R.id.txtLayoutNameUserInfo);
        txtName = findViewById(R.id.txtNameUserInfo);
        txtEmail = findViewById(R.id.txtEmailInfo);
        btnLogout = findViewById(R.id.bt_logout);

        //getData();

        setData();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, AuthActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });


    }
    private void setData() {
        txtName.setText("testname");
        txtEmail.setText("testemail");
    }


    private void getData() {
        StringRequest request = new StringRequest(Request.Method.GET, "https://mmherokuapp.herokuapp.com/api/userinfo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HttpClient", "success! response: " + response.toString());
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getBoolean("success")){
                        JSONObject user = object.getJSONObject("user");
                        //make shared pref user
                        txtName.setText(user.getString("name"));
                        //Picasso.get().load(Constant.URL+"storage/profiles/"+user.getString("photo")).into(imgProfile);
                        //if success
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
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
            protected Map<String, String> getParams() throws AuthFailureError  {
                String token = preferences.getString("token","");
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Authorization","Bearer "+token);
                return map;
            }


        };
        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}


