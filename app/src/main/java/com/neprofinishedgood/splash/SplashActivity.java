package com.neprofinishedgood.splash;

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
                if (loginResponse != null) {
                    if (Integer.parseInt(loginResponse.getUserLoginResponse().get(0).getUserId()) > 0) {
                        Intent intent = new Intent(SplashActivity.this, DashBoardAcivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
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


}
