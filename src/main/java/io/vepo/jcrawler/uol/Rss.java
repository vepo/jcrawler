package io.vepo.jcrawler.uol;

import java.io.InputStream;
import java.util.Objects;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rss {
    private Channel channel;

    public Rss() {
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.channel);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Rss otherRss) {
            return Objects.equals(this.channel, otherRss.channel);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("RSS [channel=%s]", this.channel);
    }

    public static Rss read(InputStream content) {
        try {
            JAXBContext context = JAXBContext.newInstance(Rss.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Rss) unmarshaller.unmarshal(content);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
