package io.vepo.jcrawler.uol;

import java.util.Objects;

public class Image {
    private String title;
    private String url;
    private String link;

    public Image() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.url, this.link);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Image) {
            return Objects.equals(this.title, ((Image) other).title) && Objects.equals(this.url, ((Image) other).url)
                    && Objects.equals(this.link, ((Image) other).link);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("Image [title=%s, url=%s, link=%s]", this.title, this.url, this.link);
    }
}
