package at.team2.client.singletons;

import at.team2.client.pages.searchmedium.SearchMedia;

public class SearchMediumSingleton {
    private static SearchMedia _instance;

    private SearchMediumSingleton() {
    }

    public static SearchMedia getInstance() {
        if (_instance == null) {
            _instance = new SearchMedia();
        }

        return _instance;
    }
}