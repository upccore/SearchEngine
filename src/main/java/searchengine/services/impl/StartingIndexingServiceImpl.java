package searchengine.services.impl;

import org.springframework.stereotype.Service;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.StartIndexingService;
import searchengine.services.impl.helpers.Mapper;
import searchengine.services.impl.helpers.Node;
import searchengine.services.impl.helpers.VariablesAndMethods;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

@Service
public class StartingIndexingServiceImpl implements StartIndexingService {

    private static final VariablesAndMethods VAM = new VariablesAndMethods();

    private final PageRepository pageRepository;
    private final SiteRepository siteRepository;

    public StartingIndexingServiceImpl(PageRepository pageRepository, SiteRepository siteRepository) {
        this.pageRepository = pageRepository;
        this.siteRepository = siteRepository;
    }

    @Override
    public Map<String, Boolean> startIndexing() {

        String URL = VAM.setAddress();
        Node root = new Node(URL, VAM);
        Mapper mapper = new Mapper(root);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mapper);
        VAM.writeInFiles(root.getString());

        Map<String, Boolean> result = new HashMap<>();
        result.put("result", true);
        return result;
    }
}
