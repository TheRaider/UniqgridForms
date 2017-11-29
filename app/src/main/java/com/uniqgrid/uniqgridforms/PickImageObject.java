package com.uniqgrid.uniqgridforms;

import android.net.Uri;

/**
 * Created by vinee_000 on 28-11-2017.
 */

public class PickImageObject {
    int imgResult;
    Uri outputFileUri;
    Uri selectedImageUri;

    public PickImageObject() {
    }

    public PickImageObject(int imgResult) {
        this.imgResult = imgResult;
    }

    public int getImgResult() {
        return imgResult;
    }

    public void setImgResult(int imgResult) {
        this.imgResult = imgResult;
    }

    public Uri getOutputFileUri() {
        return outputFileUri;
    }

    public void setOutputFileUri(Uri outputFileUri) {
        this.outputFileUri = outputFileUri;
    }

    public Uri getSelectedImageUri() {
        return selectedImageUri;
    }

    public void setSelectedImageUri(Uri selectedImageUri) {
        this.selectedImageUri = selectedImageUri;
    }
}
