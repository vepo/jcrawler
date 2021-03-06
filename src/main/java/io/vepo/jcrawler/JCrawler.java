package io.vepo.jcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.vepo.jcrawler.uol.Rss;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;

public class JCrawler {
    public static void main(String[] args) throws Exception {
        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpGet httpget = new HttpGet("https://www.uol.com.br/vueland/api/?loadComponent=XmlFeedRss");

            HttpResponse response = httpclient.execute(httpget);
            Rss rss = Rss.read(response.getEntity().getContent());
            rss.getChannel()
                    .getItem()
                    .stream()
                    .filter(item -> item.getPubDate() != null)
                    .limit(1L)
                    .forEach(item -> {
                        try {
                            Document doc = Jsoup.connect(item.getLink()).get();
                            System.out.println("Analisando artigo: " + item.getDescription() + " " + item.getLink());
                            Elements paragrafos = doc.select("article p");

                            // Split on white spaces in the line to get words
                            TokenizerFactory t = new DefaultTokenizerFactory();

                            List<String> content = new ArrayList<>();
                            for (Element paragrafo : paragrafos) {
                                content.add(paragrafo.text());
                                System.out.println("Adicionando linha: " + paragrafo.text());
                            }
                            CollectionSentenceIterator iter = new CollectionSentenceIterator(content);
                            t.setTokenPreProcessor(new CommonPreprocessor());
                            Word2Vec vec = new Word2Vec.Builder()
                                    .minWordFrequency(2)
                                    .iterations(10)
                                    .layerSize(100)
                                    .seed(42)
                                    .windowSize(5)
                                    .iterate(iter)
                                    .tokenizerFactory(t)
                                    .stopWords(Arrays.asList("de", "a", "o", "que", "e", "do", "da", "em", "um", "para",
                                            "??", "com", "n??o", "uma", "os", "no", "se", "na", "por", "mais", "as",
                                            "dos", "como", "mas", "foi", "ao", "ele", "das", "tem", "??", "seu", "sua",
                                            "ou", "ser", "quando", "muito", "h??", "nos", "j??", "est??", "eu", "tamb??m",
                                            "s??", "pelo", "pela", "at??", "isso", "ela", "entre", "era", "depois", "sem",
                                            "mesmo", "aos", "ter", "seus", "quem", "nas", "me", "esse", "eles", "est??o",
                                            "voc??", "tinha", "foram", "essa", "num", "nem", "suas", "meu", "??s",
                                            "minha", "t??m", "numa", "pelos", "elas", "havia", "seja", "qual", "ser??",
                                            "n??s", "tenho", "lhe", "deles", "essas", "esses", "pelas", "este", "fosse",
                                            "dele", "tu", "te", "voc??s", "vos", "lhes", "meus", "minha", "teu", "tu",
                                            "teu", "tua", "nosso", "noss", "nosso", "nossa", "dela", "delas", "esta",
                                            "estes", "estas", "aquele", "aquela", "aqueles", "aquelas", "isto",
                                            "aquilo", "esto", "est", "estamo", "est??", "estiv", "estev", "estivemo",
                                            "estivera", "estav", "est??vamo", "estava", "estiver", "estiv??ramo", "estej",
                                            "estejamo", "esteja", "estivess", "estiv??ssemo", "estivesse", "estive",
                                            "estivermo", "estivere", "he", "h", "havemo", "h??", "houv", "houvemo",
                                            "houvera", "houver", "houv??ramo", "haj", "hajamo", "haja", "houvess",
                                            "houv??ssemo", "houvesse", "houve", "houvermo", "houvere", "houvere",
                                            "houver", "houveremo", "houver??", "houveri", "houver??amo", "houveria", "so",
                                            "somo", "s??", "er", "??ramo", "era", "fu", "fo", "fomo", "fora", "for",
                                            "f??ramo", "sej", "sejamo", "seja", "foss", "f??ssemo", "fosse", "fo",
                                            "formo", "fore", "sere", "ser", "seremo", "ser??", "seri", "ser??amo",
                                            "seria", "tenh", "te", "temo", "t??", "tinh", "t??nhamo", "tinha", "tiv",
                                            "tev", "tivemo", "tivera", "tiver", "tiv??ramo", "tenh", "tenhamo", "tenha",
                                            "tivess", "tiv??ssemo", "tivesse", "tive", "tivermo", "tivere", "tere",
                                            "ter", "teremo", "ter??", "teri", "ter??amo", "teriam"))
                                    .build();
                            vec.fit();

                            WordVectorSerializer.writeWord2VecModel(vec, "pathToWriteto.txt");
                            Collection<String> lst = vec.wordsNearest("governo", 10);
                            System.out.println("10 palavras proximas a 'governo': " + lst);
                            System.out.println(vec.toJson());
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    });

        }
    }
}