package com.neprofinishedgood.splash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.login.LoginActivity;
import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.utils.SharedPref;
import com.scottyab.rootbeer.RootBeer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.imageViewAppLogo)
    ImageView imageViewAppLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        setLogoWork();
    }

    private void setLogoWork() {
        Animation hold = AnimationUtils.loadAnimation(this, R.anim.hold);
        final Animation translateScale = AnimationUtils.loadAnimation(this, R.anim.translate_scale);
        translateScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(SharedPref.getLoginUser(), LoginResponse.class);

                imageViewAppLogo.clearAnimation();
                if (LoginActivity.getInstance() != null) {
                    LoginActivity.getInstance().finish();
                    LoginActivity.instance = null;
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }

//                Gson gson = new Gson();
//                LoginResponse loginResponse = gson.fromJson(SharedPref.getLoginUser(), LoginResponse.class);
//                imageViewAppLogo.clearAnimation();
//                if (loginResponse != null) {
//                    if (Integer.parseInt(loginResponse.getUserLoginResponse().get(0).getUserId()) > 0) {
//                        if (DashBoardAcivity.getInstance() != null) {
//                            DashBoardAcivity.getInstance().finish();
//                            DashBoardAcivity.instance = null;
//                        } else {
//                            Intent intent = new Intent(SplashActivity.this, DashBoardAcivity.class);
//                            startActivity(intent);
//                            finishAffinity();
//                        }
//                    } else {
//                        if (LoginActivity.getInstance() != null) {
//                            LoginActivity.getInstance().finish();
//                            LoginActivity.instance = null;
//                        } else {
//                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                            finishAffinity();
//                        }
//                    }
//                } else {
//                    if (LoginActivity.getInstance() != null) {
//                        LoginActivity.getInstance().finish();
//                        LoginActivity.instance = null;
//                    } else {
//                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        finishAffinity();
//                    }
//                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hold.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageViewAppLogo.clearAnimation();
                imageViewAppLogo.startAnimation(translateScale);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageViewAppLogo.startAnimation(hold);


    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(new RootBeer(SplashActivity.this).isRooted()){
//            showAlertDialogAndExitApp("This device is rooted. You can't use this app.");
//        }
//        else {
//            setLogoWork();
//        }
    }

    public void showAlertDialogAndExitApp(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finishAffinity();
                    }
                });

        alertDialog.show();
    }
}
