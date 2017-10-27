package at.team2.client.singletons;

import at.team2.client.pages.searchMedium.SearchMedium;

public class SearchMediumSingleton {
    private static SearchMedium _searchMedium;

    private SearchMediumSingleton() {
    }

    public static SearchMedium getInstance() {
        if (_searchMedium == null) {
            _searchMedium = new SearchMedium();
        }

        return _searchMedium;
    }
}
