package com.neprofinishedgood.raf.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

public interface IRAFView {
    void onFailure(String message);

    void onSuccess(ScanCountingResponse body);

    void onUpdateRAFFailure(String message);

    void onUpdateRAFSuccess(UniversalResponse body);
}
