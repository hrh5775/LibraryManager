package at.team2.client.singletons;

import at.team2.client.pages.showremoteloan.ShowRemoteLoan;

public class ShowRemoteLoanSingleton {
    private static ShowRemoteLoan _instance;

    private ShowRemoteLoanSingleton() {
    }

    public static ShowRemoteLoan getInstance() {
        if(_instance == null) {
            _instance = new ShowRemoteLoan();
        }

        return _instance;
    }
}