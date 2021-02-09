package com.ReddyGona.schedular20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
    String name_s, email_s, mobile_s, regno_s, department_s, year_s, batch_s, pass_s;
    //shared preferances
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor MyEdit;

    ProgressDialog progressDialog;


    String login_api="https://oakspro.com/projects/project40/janardhan/Schedular2/sign_in.php";
    String api_signup="https://oakspro.com/projects/project40/janardhan/Schedular2/sign_up.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);



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

                Button submit_btn;


                name_si=bottomSheetDialog.findViewById(R.id.sign_name);
                email_si=bottomSheetDialog.findViewById(R.id.sign_email);
                mobile_si=bottomSheetDialog.findViewById(R.id.sign_mob);
                dept_si=bottomSheetDialog.findViewById(R.id.sign_dept);
                reg_si=bottomSheetDialog.findViewById(R.id.sign_regno);
                year_si=bottomSheetDialog.findViewById(R.id.sign_year);
                batch_si=bottomSheetDialog.findViewById(R.id.sign_batch);
                password_si=bottomSheetDialog.findViewById(R.id.sign_passwd);
                cpassword_si=bottomSheetDialog.findViewById(R.id.sign_cpass);
                submit_btn=bottomSheetDialog.findViewById(R.id.sign_btn_btn);

                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                            String name = name_si.getText().toString();
                            String email = email_si.getText().toString();
                            String mob = mobile_si.getText().toString();
                            String dept = dept_si.getText().toString().toUpperCase();
                            String reg = reg_si.getText().toString().toUpperCase();
                            String year = year_si.getText().toString();
                            String batch = batch_si.getText().toString();
                            String password = password_si.getText().toString();
                            String c_pass=cpassword_si.getText().toString();



                        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(mob) && !TextUtils.isEmpty(dept) &&
                                !TextUtils.isEmpty(reg) && !TextUtils.isEmpty(year) && !TextUtils.isEmpty(batch) && !TextUtils.isEmpty(password) ){


                            if (Character.isAlphabetic(reg.charAt(0)) && Character.isAlphabetic(reg.charAt(1)) && Character.isAlphabetic(reg.charAt(2)) &&
                                    Character.isDigit(reg.charAt(3)) &&  Character.isDigit(reg.charAt(4)) && Character.isAlphabetic(reg.charAt(5)) && Character.isAlphabetic(reg.charAt(6))
                                    && Character.isDigit(reg.charAt(7)) && Character.isDigit(reg.charAt(8)) && Character.isDigit(reg.charAt(9)) ){

                                if (c_pass.equals(password)){

                                    signupfunction(name, email, mob, dept, reg, year, batch, password);
                                }else{
                                    Toast.makeText(MainActivity.this, "Password and conform password did not match", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                reg_si.setError("Invalid regno");
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Please Fill all the details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                        String s_passwwd=l_Pass.getText().toString();
                        
                        if (!TextUtils.isEmpty(s_uname) && !TextUtils.isEmpty(s_passwwd)){
                            loginprocess(s_uname, s_passwwd);
                        }else{
                            Toast.makeText(MainActivity.this, "Enter credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




            }
        });

    }

    private void signupfunction(String name_e, String email_e, String mob_e, String dept_e,  String reg_e, String year_e, String batch_e, String password_e) {

        progressDialog.show();
        
        StringRequest signupreq=new StringRequest(Request.Method.POST, api_signup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status =jsonObject.getString("status");
                    if (status.equals("1")){
                        Toast.makeText(MainActivity.this, "Registration Successful, Please Login", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> upload = new HashMap<>();
                upload.put("name", name_e);
                upload.put("email", email_e);
                upload.put("mobile", mob_e);
                upload.put("dept", dept_e);
                upload.put("regno", reg_e);
                upload.put("year", year_e);
                upload.put("batch", batch_e);
                upload.put("password", password_e);
                return upload;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(signupreq);
        
    }

    private void loginprocess(String uuname, String uupass) {
        StringRequest loginreq= new StringRequest(Request.Method.POST, login_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success= jsonObject.getString("success").toString();
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if (success.equals("1")){

                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            name_s=object.getString("name");
                            email_s=object.getString("email");
                            mobile_s=object.getString("mobile");
                            regno_s=object.getString("regno");
                            department_s=object.getString("dept");
                            year_s=object.getString("year");
                            batch_s=object.getString("batch");
                            pass_s=object.getString("password");
                        }
                       
                        

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        MyEdit.putString("p_name", name_s);
                        MyEdit.putString("p_email", email_s);
                        MyEdit.putString("p_mobile", mobile_s);
                        MyEdit.putString("p_regno", regno_s);
                        MyEdit.putString("p_dept", department_s);
                        MyEdit.putString("p_year", year_s);
                        MyEdit.putString("p_batch", batch_s);
                        MyEdit.putString("p_passwd", pass_s);
                        MyEdit.putBoolean("loginS", true);
                        MyEdit.commit();
                        startActivity(intent);
                        finish();


                    }else {

                        Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){

                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Json Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> upload = new HashMap<>();
                upload.put("user", uuname);
                upload.put("passwd", uupass);

                return upload;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(loginreq);

    }
}