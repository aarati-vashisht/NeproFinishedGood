package com.neprofinishedgood.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.neprofinishedgood.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.textViewUserName)
    TextView textViewUserName;

    @BindView(R.id.textViewUserId)
    TextView textViewUserId;

    @BindView(R.id.textViewUserEmail)
    TextView textViewUserEmail;

    @BindView(R.id.textViewVersion)
    TextView textViewVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        setTitle(getString(R.string.user_info));
        isUserInfo = true;

        setData();
    }

    void setData(){
        textViewUserName.setText(userName);
        textViewUserId.setText(userId);
        textViewUserEmail.setText(userEmail);

        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
            textViewVersion.setText("App Version : "+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
