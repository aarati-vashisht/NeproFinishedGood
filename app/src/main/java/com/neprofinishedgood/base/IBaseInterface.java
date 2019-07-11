package com.neprofinishedgood.base;

import android.app.Activity;

public interface IBaseInterface {
    void setTitle(String title);

    void showProgress(Activity activity);

    void hideProgress();
}
