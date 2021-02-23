package com.pb.app.bookmarketingE.ui.content;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ContentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}