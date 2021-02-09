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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText conpass, newpass, oldpass;
    Button submit;

    String api_pswdck="https://oakspro.com/projects/project40/janardhan/Schedular2/chk_passwd.php";
    String api_changepswd="https://oakspro.com/projects/project40/janardhan/Schedular2/update_passwd.php";

    SharedPreferences sharedPreferences;
    String mobile_p;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sharedPreferences=getSharedPreferences("Users", MODE_PRIVATE);
        mobile_p=sharedPreferences.getString("p_mobile", "");


        newpass=findViewById(R.id.pr_new_pass);
        conpass=findViewById(R.id.pr_con_password);
        oldpass=findViewById(R.id.pr_passwd);

        submit=findViewById(R.id.chang_btn);
        newpass.setVisibility(View.INVISIBLE);
        conpass.setVisibility(View.INVISIBLE);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpasswd=oldpass.getText().toString();

                if (submit.getText().equals("submit") && !TextUtils.isEmpty(oldpasswd)){

                        checkpasswd(oldpasswd);


                }else if (submit.getText().equals("ChangePassword")){
                    newpass.setVisibility(View.VISIBLE);
                    conpass.setVisibility(View.VISIBLE);
                    String new_pass=newpass.getText().toString();
                    String con_pass=conpass.getText().toString();
                    if (new_pass.equals(con_pass)) {
                        changepasswd(new_pass, con_pass);
                    }else{
                        Toast.makeText(ChangePasswordActivity.this, "Enter Valid Password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    private void changepasswd(String new_pass, String con_pass) {
        progressDialog.show();
        StringRequest changepaswd=new StringRequest(Request.Method.POST, api_changepswd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.equals("1")){
                            Toast.makeText(ChangePasswordActivity.this, "Password Updated successfully", Toast.LENGTH_SHORT).show();
                          SharedPreferences login = getSharedPreferences("Users", MODE_PRIVATE);
                          SharedPreferences.Editor editor =login.edit();
                          editor.putBoolean("loginS", false);
                          editor.clear();
                          editor.commit();

                            Intent intent=new Intent(ChangePasswordActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                        }

                    else {
                            Toast.makeText(ChangePasswordActivity.this, "Please enter valid password and conform password", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ChangePasswordActivity.this, "error :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangePasswordActivity.this, "Check Network Connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> upload=new HashMap<>();
                upload.put("passwd", new_pass);
                upload.put("mobile", mobile_p);
                return upload;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(changepaswd);
    }

    private void checkpasswd(String oldpasswd) {
        progressDialog.show();
        StringRequest passwdck=new StringRequest(Request.Method.POST, api_pswdck, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status=jsonObject.getString("status");

                    if (status.equals("1")){
                        oldpass.setVisibility(View.INVISIBLE);
                        newpass.setVisibility(View.VISIBLE);
                        conpass.setVisibility(View.VISIBLE);
                        submit.setText("ChangePassword");
                        progressDialog.dismiss();
                    }else{
                        Toast.makeText(ChangePasswordActivity.this, "Enter Valid Password", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ChangePasswordActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String , String> upload = new HashMap<>();
               upload.put("mobile", mobile_p);
               upload.put("passwd", oldpasswd);
               return upload;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(passwdck);
    }
}