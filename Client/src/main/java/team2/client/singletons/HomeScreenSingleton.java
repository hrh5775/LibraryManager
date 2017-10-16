package team2.client.singletons;

import team2.client.pages.home.HomeScreen;

public class HomeScreenSingleton {
    private static HomeScreen _homesScreen;

    private HomeScreenSingleton() {
    }

    public static HomeScreen getInstance() {
        if (_homesScreen == null) {
            _homesScreen = new HomeScreen();
        }

        return _homesScreen;
    }
}


