package io.vepo.jcrawler.uol;

import java.util.Date;
import java.util.Objects;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Item {
    private String description;
    private String guid;
    private String link;
    private String pubDate;

    public Item() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description, this.guid, this.link, this.pubDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Item) {
            return Objects.equals(this.description, ((Item) other).description) && Objects.equals(this.guid, ((Item) other).guid)
                    && Objects.equals(this.link, ((Item) other).link)
                    && Objects.equals(this.pubDate, ((Item) other).pubDate);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("Item [description=%s, guid=%s, link=%s, pubDate=%s]", this.description, this.guid,
                this.link, this.pubDate);
    }
}
