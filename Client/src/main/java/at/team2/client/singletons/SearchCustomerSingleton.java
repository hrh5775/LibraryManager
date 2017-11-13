package at.team2.client.singletons;

import at.team2.client.pages.searchcustomer.SearchCustomer;

public class SearchCustomerSingleton {
    private static SearchCustomer _instance;

    private SearchCustomerSingleton() {
    }

    public static SearchCustomer getInstance() {
        if (_instance == null) {
            _instance = new SearchCustomer();
        }

        return _instance;
    }
}