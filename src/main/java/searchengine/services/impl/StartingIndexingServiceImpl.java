package searchengine.services.impl;
import org.springframework.stereotype.Service;
import searchengine.model.PageRepository;
import searchengine.model.SiteRepository;
import searchengine.services.StartIndexingService;

import java.util.HashMap;
import java.util.Map;

@Service
public class StartingIndexingServiceImpl implements StartIndexingService {

    private PageRepository pageRepository;
    private SiteRepository siteRepository;

    @Override
    public Map<String, Boolean> startIndexing() {



        Map<String, Boolean> result = new HashMap<>();
        result.put("result", true);
        return result;
    }
}
