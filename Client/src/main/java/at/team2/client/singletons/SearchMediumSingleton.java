package at.team2.client.singletons;

import at.team2.client.pages.searchMedium.SearchMedium;

public class SearchMediumSingleton {
    private static SearchMedium _instance;

    private SearchMediumSingleton() {
    }

    public static SearchMedium getInstance() {
        if (_instance == null) {
            _instance = new SearchMedium();
        }

        return _instance;
    }
}