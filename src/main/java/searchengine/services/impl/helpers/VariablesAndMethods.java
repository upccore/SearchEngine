package searchengine.services.impl.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;

@Getter
@Setter
public class VariablesAndMethods {

    private static final String PATH = "http://www.playback.ru/";
    private static String pathFile = "src/main/resources/testFile.txt";

    private int depthOfCoverage = 3;

    public String setAddress() { //метод получения ссылки на сайт
        return PATH;
    }

    public Set<String> parseAncestor(Node ancestor) {
        Set<String> urlSet = null;
        try {
            var document = Jsoup.connect(ancestor.getUrl())
                    .timeout(100000)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .get();
            Thread.sleep(300);
            var elements = document.select("a");
            urlSet = elements.stream().map(element -> element.absUrl("href")).
                    filter(
                            s -> s.startsWith(ancestor.getStartURL())).
                    filter(s -> !s.equals(ancestor.getUrl())).
                    filter(s -> !s.contains("#"))
                    .collect(Collectors.toSet());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return urlSet;
    }

    public void writeInFiles(String text) {
        try {
            Files.deleteIfExists(Paths.get(pathFile));
            Files.write(Paths.get(pathFile), text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}