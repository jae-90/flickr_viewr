package com.jw.flickrviewr.photo;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Factory for Photo viewmodel.
 */
public class PhotoViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private Application app;

    private String url;

    private String id;

    public PhotoViewModelFactory(@NonNull Application app, String url, String id) {
        super(app);
        this.app = app;
        this.url = url;
        this.id = id;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotoViewModel(app, url, id);
    }

}