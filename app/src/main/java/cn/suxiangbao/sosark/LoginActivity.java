package cn.suxiangbao.sosark;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.suxiangbao.sosark.config.RetCodeConfig;
import cn.suxiangbao.sosark.entity.RetMsgObj;
import cn.suxiangbao.sosark.pic.KevinApplication;
import cn.suxiangbao.sosark.util.JsonObjectRequest;

import static cn.suxiangbao.sosark.config.ServerUrl.URL_LOGIN;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final String TAG = LoginActivity.class.getCanonicalName();

    private static final int REQUEST_FOR_SIGN_UP = 1;
    // UI references.
    private AutoCompleteTextView mViewUsername;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        toolbarSetting();
        initView();
    }

    private void initView(){

        // Set up the login form.
        mViewUsername = (AutoCompleteTextView) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mEmailSignInButton.setText(R.string.txt_login);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_sign_up_or_login);
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        Log.d(TAG,"attemptLogin");

        // Reset errors.
        mViewUsername.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String username = mViewUsername.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mViewUsername.setError(getString(R.string.error_field_required));
            focusView = mViewUsername;
            cancel = true;
        } else if (!isUserNameValid(username)) {
            mViewUsername.setError(getString(R.string.error_invalid_username));
            focusView = mViewUsername;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            Map<String,String > params = new HashMap<>();
            params.put("username",username);
            params.put("password",password);
            JsonObjectRequest requests =    new JsonObjectRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i(TAG,response.toString());
                    RetMsgObj ret   = mGson.fromJson(response.toString(),new TypeToken<RetMsgObj>(){}.getType());
                    Log.i(TAG,"login ret = "+ret);
                    showProgress(false);
                    if (ret.getCode() == RetCodeConfig.FAILED || ret.getCode() == RetCodeConfig.USER_NOT_EXIST){
                        mViewUsername.setError(ret.getMsg());
                        setLogin(false);
                        return;
                    }
                    if (ret.getCode() == RetCodeConfig.SUCCESS){
                        setLogin(true);

//                        initUserInfo();
                        isLogin();
                        finish();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);
//                    Log.i(TAG,error.getMessage());
                    Toast.makeText(LoginActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    isLogin();
                }
            },params);
            requests.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getmQueue().add(requests);
        }
    }

    private boolean isUserNameValid(String username) {
        Boolean ret = true;
        if (StringUtils.isBlank(username)){
            ret = false;
        }
        if (StringUtils.isEmpty(username))
        {
            ret = false;
        }
        return ret;
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_sign_up:
                startActivityForResult(new Intent(LoginActivity.this,SignUpActivity.class),REQUEST_FOR_SIGN_UP);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            Log.i(TAG,"ON ACTIVITY RESULT");
            if (requestCode == REQUEST_FOR_SIGN_UP){
                Bundle bundle = data.getExtras();
                String username = bundle.getString("username");
                String password =  bundle.getString("password");
                if (!StringUtils.isBlank(username)&&!StringUtils.isBlank(password)){
                    mViewUsername.setText(username);
                    mPasswordView.setText(password);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

