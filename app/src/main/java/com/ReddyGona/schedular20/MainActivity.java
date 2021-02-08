package com.ReddyGona.schedular20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button login, signup;
    //for login bottomsheet
    EditText l_Uname, l_Pass;
    Button l_login;
    //for signup
    EditText name_si, email_si, mobile_si, dept_si, reg_si, year_si, batch_si, password_si, cpassword_si;
    //for storing data to shared preferances
    String name_s, email_s, mobile_s, regno_s, department_s, year_s, batch_s;
    //shared preferances
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor MyEdit;


    String login_api="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("Users",MODE_PRIVATE);
        MyEdit=sharedPreferences.edit();

        login=findViewById(R.id.login_btn);
        signup=findViewById(R.id.signup_btn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(MainActivity.this);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_signup);
                bottomSheetDialog.setCanceledOnTouchOutside(false);


                name_si=bottomSheetDialog.findViewById(R.id.sign_name);
                email_si=bottomSheetDialog.findViewById(R.id.sign_email);
                mobile_si=bottomSheetDialog.findViewById(R.id.sign_mob);
                dept_si=bottomSheetDialog.findViewById(R.id.sign_dept);
                reg_si=bottomSheetDialog.findViewById(R.id.sign_regno);
                year_si=bottomSheetDialog.findViewById(R.id.sign_year);
                batch_si=bottomSheetDialog.findViewById(R.id.sign_batch);
                password_si=bottomSheetDialog.findViewById(R.id.sign_passwd);
                cpassword_si=bottomSheetDialog.findViewById(R.id.sign_cpass);

                year_si.setText("3rd year");

                bottomSheetDialog.show();


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(MainActivity.this);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_login);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();

                l_Uname=bottomSheetDialog.findViewById(R.id.l_user);
                l_Pass=bottomSheetDialog.findViewById(R.id.l_pass);
                l_login=bottomSheetDialog.findViewById(R.id.l_btn);
                
                l_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s_uname=l_Uname.getText().toString();
                        String s_pass=l_Pass.getText().toString();
                        
                        if (!TextUtils.isEmpty(s_uname) && !TextUtils.isEmpty(s_pass)){
                            loginprocess(s_uname, s_pass);
                        }else{
                            Toast.makeText(MainActivity.this, "Enter credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




            }
        });

    }

    private void loginprocess(String s_uname, String s_pass) {

        StringRequest loginrequest= new StringRequest(Request.Method.POST, login_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jsonObject.getJSONArray("ldetails");
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object=jsonArray.getJSONObject(i);

                            name_s=object.getString("name");
                            email_s=object.getString("email");
                            mobile_s=object.getString("mobile");
                            regno_s=object.getString("regno");
                            department_s=object.getString("dept");
                            year_s=object.getString("year");
                            batch_s=object.getString("batch");

                        }

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        MyEdit.putString("s_name", name_s);
                        MyEdit.putString("s_email", email_s);
                        MyEdit.putString("s_mobile", mobile_s);
                        MyEdit.putString("s_regno", regno_s);
                        MyEdit.putString("s_dept", department_s);
                        MyEdit.putString("s_year", year_s);
                        MyEdit.putString("s_batch", batch_s);
                        MyEdit.putBoolean("loginS", true);
                        MyEdit.commit();
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> upload = new HashMap<>();
                upload.put("uname", s_uname);
                upload.put("upass", s_pass);
                return upload;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(loginrequest);

    }
}