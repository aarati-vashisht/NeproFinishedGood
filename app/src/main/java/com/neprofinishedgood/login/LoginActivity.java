package com.neprofinishedgood.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.dashboard.DashBoardAcivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.pinViewLogin)
    PinView pinViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        overridePendingTransition(0, 0);

    }

    @OnClick(R.id.buttonLogin)
    public void onLoginButtonClick() {
        if (pinViewLogin.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.plz_enter_pin), Toast.LENGTH_SHORT).show();
        } else if (!pinViewLogin.getText().toString().equals("1234")) {
            Toast.makeText(this, getString(R.string.invalid_pin), Toast.LENGTH_SHORT).show();
        } else if (pinViewLogin.getText().toString().equals("1234")) {
            Toast.makeText(this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DashBoardAcivity.class));
            finishAffinity();
            overridePendingTransition(0, 0);
        }
    }

}
