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
                                            "é", "com", "não", "uma", "os", "no", "se", "na", "por", "mais", "as",
                                            "dos", "como", "mas", "foi", "ao", "ele", "das", "tem", "à", "seu", "sua",
                                            "ou", "ser", "quando", "muito", "há", "nos", "já", "está", "eu", "também",
                                            "só", "pelo", "pela", "até", "isso", "ela", "entre", "era", "depois", "sem",
                                            "mesmo", "aos", "ter", "seus", "quem", "nas", "me", "esse", "eles", "estão",
                                            "você", "tinha", "foram", "essa", "num", "nem", "suas", "meu", "às",
                                            "minha", "têm", "numa", "pelos", "elas", "havia", "seja", "qual", "será",
                                            "nós", "tenho", "lhe", "deles", "essas", "esses", "pelas", "este", "fosse",
                                            "dele", "tu", "te", "vocês", "vos", "lhes", "meus", "minha", "teu", "tu",
                                            "teu", "tua", "nosso", "noss", "nosso", "nossa", "dela", "delas", "esta",
                                            "estes", "estas", "aquele", "aquela", "aqueles", "aquelas", "isto",
                                            "aquilo", "esto", "est", "estamo", "estã", "estiv", "estev", "estivemo",
                                            "estivera", "estav", "estávamo", "estava", "estiver", "estivéramo", "estej",
                                            "estejamo", "esteja", "estivess", "estivéssemo", "estivesse", "estive",
                                            "estivermo", "estivere", "he", "h", "havemo", "hã", "houv", "houvemo",
                                            "houvera", "houver", "houvéramo", "haj", "hajamo", "haja", "houvess",
                                            "houvéssemo", "houvesse", "houve", "houvermo", "houvere", "houvere",
                                            "houver", "houveremo", "houverã", "houveri", "houveríamo", "houveria", "so",
                                            "somo", "sã", "er", "éramo", "era", "fu", "fo", "fomo", "fora", "for",
                                            "fôramo", "sej", "sejamo", "seja", "foss", "fôssemo", "fosse", "fo",
                                            "formo", "fore", "sere", "ser", "seremo", "serã", "seri", "seríamo",
                                            "seria", "tenh", "te", "temo", "té", "tinh", "tínhamo", "tinha", "tiv",
                                            "tev", "tivemo", "tivera", "tiver", "tivéramo", "tenh", "tenhamo", "tenha",
                                            "tivess", "tivéssemo", "tivesse", "tive", "tivermo", "tivere", "tere",
                                            "ter", "teremo", "terã", "teri", "teríamo", "teriam"))
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