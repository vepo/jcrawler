package io.vepo.jcrawler.uol;

import java.util.Date;
import java.util.Objects;

public class Item {
    private String description;
    private String guid;
    private String link;
    private Date pubDate;

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

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
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
        } else if (other instanceof Item otherItem) {
            return Objects.equals(this.description, otherItem.description) && Objects.equals(this.guid, otherItem.guid)
                    && Objects.equals(this.link, otherItem.link)
                    && Objects.equals(this.pubDate, otherItem.pubDate);
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
