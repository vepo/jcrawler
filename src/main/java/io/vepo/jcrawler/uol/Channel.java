package io.vepo.jcrawler.uol;

import java.util.List;
import java.util.Objects;

public class Channel {
    private String title;
    private String link;
    private String description;
    private String language;
    private String category;
    private String copyright;
    private Image image;
    private List<Item> item;

    public Channel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.link, this.description, this.language, this.category, this.copyright,
                this.image, this.item);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Channel) {
            return Objects.equals(this.title, ((Channel) other).title) && Objects.equals(this.link, ((Channel) other).link)
                    && Objects.equals(this.description, ((Channel) other).description)
                    && Objects.equals(this.language, ((Channel) other).language)
                    && Objects.equals(this.category, ((Channel) other).category)
                    && Objects.equals(this.copyright, ((Channel) other).copyright)
                    && Objects.equals(this.image, ((Channel) other).image)
                    && Objects.equals(this.item, ((Channel) other).item);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Channel [title=%s, link=%s, description=%s, language=%s, category=%s, copyright=%s, item=%s]",
                this.title, this.link, this.description, this.language, this.category, this.copyright, this.item);
    }
}
