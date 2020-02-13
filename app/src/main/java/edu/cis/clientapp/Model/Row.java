package edu.cis.clientapp.Model;

import android.graphics.Bitmap;

public class Row {
    private String name;
    private String status;
    private Bitmap image;

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
