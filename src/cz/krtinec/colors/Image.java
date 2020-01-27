package cz.krtinec.colors;


import java.awt.image.BufferedImage;
import java.util.List;

public class Image {
    private String url;
    private BufferedImage data;

    public Image(String url, BufferedImage data) {
        this.url = url;
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public BufferedImage getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return url.equals(image.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}

