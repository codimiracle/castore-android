package cn.com.sise.ca.castore.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import cn.com.sise.ca.castore.CAstoreApplication;
import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.SimpleServerActionCallback;
import cn.com.sise.ca.castore.server.UserAction;
import cn.com.sise.ca.castore.server.som.Message;

public class UserSessionActivity extends ServerActionActivity {
    public static final String SIGNIN_ACTION = CAstoreApplication.PACKAGE_NAME + ".User.SignIn";
    public static final String SIGNUP_ACTION = CAstoreApplication.PACKAGE_NAME + ".User.SignUp";
    public static final int SING_IN_REQUEST_CODE = 0x0001;
    public static final int SIGN_IN_SUCCESS_CODE = 0x2001;
    public static final int SIGN_IN_FAILURE_CODE = 0X2002;

    private static final String TAG = "User.Session";

    private UserAction.SignInAction signInAction;
    private UserAction.SignUpActionCallback signUpAction;
    private EditText signInUsername, signInPassword, signUpUsername, signUpPassword,signUpPasswordAgain, signUpNickname, signUpIntroduction;
    private RadioGroup signUpGender;
    private Message message;
    private ViewFlipper flipper;
    private Button signInButton;


    private View signUpPageButton;
    private View signInPageButton;
    private CheckBox signUpLicense;
    private CAstoreApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);
        flipper = findViewById(R.id.user_session_pager);
        signInUsername = findViewById(R.id.user_sign_in_username);
        signInPassword = findViewById(R.id.user_sign_in_password);
        signUpPasswordAgain = findViewById(R.id.user_sign_up_passwrod_again);
        signInButton = findViewById(R.id.user_sign_in_button);
        signInPageButton = findViewById(R.id.user_sign_in_page_button);
        signInPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.showNext();
            }
        });
        signUpPageButton = findViewById(R.id.user_sign_up_page_button);
        signUpPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.showPrevious();
            }
        });


        signUpUsername = findViewById(R.id.user_sign_up_username);
        signUpPassword = findViewById(R.id.user_sign_up_passwrod);
        signUpNickname = findViewById(R.id.user_sign_up_nickname);
        signUpIntroduction = findViewById(R.id.user_sign_up_introduction);
        signUpGender = findViewById(R.id.user_sign_up_gender);
        signUpNickname = findViewById(R.id.user_sign_up_nickname);
        signUpLicense = findViewById(R.id.user_sign_up_license);

        signInAction = new UserAction.SignInAction();


        application = (CAstoreApplication) getApplication();
    }


    public void onSignIn(final View view) {
        signInAction.setUsername(signInUsername.getText().toString());
        signInAction.setPassword(signInPassword.getText().toString());
        signInAction.setCallback(new SimpleServerActionCallback<Message>() {
            @Override
            public void onSuccess(Message message) {
                UserSessionActivity.this.message = message;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserSessionActivity.this);
                        builder.setTitle(R.string.app_user_sign_in);
                        builder.setMessage(UserSessionActivity.this.message.getMessage());
                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                if (!application.isLogged())
                                    signInButton.setEnabled(true);
                            }
                        });
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i(TAG, UserSessionActivity.this.message.getIcon());
                                Log.i(TAG, UserSessionActivity.this.message.getMessage());
                                if (!UserSessionActivity.this.message.getIcon().equals("error")) {
                                    application.setLogged(true);
                                    UserSessionActivity.this.setResult(SIGN_IN_SUCCESS_CODE);
                                    UserSessionActivity.this.finish();
                                } else {
                                    signInButton.setEnabled(true);
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @Override
            public void onFailure(Message message) {
                onSuccess(message);
            }
        });
        addServerAction(signInAction);
        view.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(SIGN_IN_FAILURE_CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onSignUp(final View view) {
        if (signUpPassword.getText().toString().equals(signUpPasswordAgain.getText().toString()))
        view.setEnabled(false);
        signUpAction = new UserAction.SignUpActionCallback(this);
        signUpAction.setUsername(signUpUsername.getText().toString());
        signUpAction.setPassword(signUpPassword.getText().toString());
        signUpAction.setNickname(signUpNickname.getText().toString());
        int gender;
        if (signUpGender.getCheckedRadioButtonId() == R.id.gender_secret) {
            gender = 0;
        } else if (signUpGender.getCheckedRadioButtonId() == R.id.gender_boy) {
            gender = 1;
        } else {
            gender = 2;
        }
        signUpAction.setGender(gender);
        signUpAction.setIntroduction(signUpIntroduction.getText().toString());
        signUpAction.setLicense(signUpLicense.isChecked());
        signUpAction.setCallback(new SimpleServerActionCallback<Message>() {
            @Override
            public void onSuccess(Message message) {
                Log.i(TAG, message.getIcon() + ":" + message.getMessage());
                if (message.getMessage().equals("注册成功")) {
                    signInUsername.setText(signUpUsername.getText());
                    signInPassword.setText(signUpPassword.getText());
                    onSignIn(signInButton);
                    } else {
                    Toast.makeText(UserSessionActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                    view.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Message message) {
                onSuccess(message);
            }
        });
        addServerAction(signUpAction);
    }
}
