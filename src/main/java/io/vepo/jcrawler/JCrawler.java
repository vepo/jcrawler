package io.vepo.jcrawler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import io.vepo.jcrawler.uol.Rss;

public class JCrawler {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create("https://www.uol.com.br/vueland/api/?loadComponent=XmlFeedRss"))
                                         .build();
        client.sendAsync(request, BodyHandlers.ofInputStream())
              .thenAccept(response -> {
                  Rss rss = Rss.read(response.body());
                  System.out.println(rss);
              })
              .join();

    }
}