package com.cs251.tasktree.ui.tools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToolsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ToolsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Mail issues @ gsaiabhishek5@gmail.com");
    }

    public LiveData<String> getText() {
        return mText;
    }
}