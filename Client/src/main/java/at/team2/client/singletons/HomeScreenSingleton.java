package at.team2.client.singletons;

import at.team2.client.pages.home.HomeScreen;

public class HomeScreenSingleton {
    private static HomeScreen _instance;

    private HomeScreenSingleton() {
    }

    public static HomeScreen getInstance() {
        if (_instance == null) {
            _instance = new HomeScreen();
        }

        return _instance;
    }
}