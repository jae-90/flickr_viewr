package com.jw.flickrviewr.photo;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.jw.flickrviewr.data.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

import static com.jw.flickrviewr.util.Const.JPG_EXT;

/**
 * ViewModel for the enlarged Photo screen.
 */
public class PhotoViewModel extends AndroidViewModel {

    private String url;

    private String id;

    PhotoViewModel(@NonNull Application app, String url, String id) {
        super(app);
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public Result saveImage(Bitmap bitmap, String id) {
        String imageFileName = id + JPG_EXT;
        boolean success = true;

        File storageDir = new File(
                Objects.requireNonNull(
                        getApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES)).toString());

        if (!storageDir.exists())
            success = storageDir.mkdirs();

        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            if (imageFile.exists())
                return new Result.Error(new FileAlreadyExistsException(imageFile.getAbsolutePath()));
            try {
                FileOutputStream stream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.close();
                return new Result.Success(true);
            } catch (Exception e){
                e.printStackTrace();
                return new Result.Error(e);
            }
        }
        return null;
    }

}