package at.team2.client.singletons;

import at.team2.client.pages.lendmedium.LendMedium;

public class LendMediumSingleton {
    private static LendMedium _instance;

    private LendMediumSingleton() {
    }

    public static LendMedium getInstance() {
        if(_instance == null) {
            _instance = new LendMedium();
        }

        return _instance;
    }
}