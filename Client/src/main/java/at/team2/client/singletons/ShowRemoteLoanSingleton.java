package at.team2.client.singletons;

import at.team2.client.pages.viewreadremoteloan.ViewReadRemoteLoan;

public class ShowRemoteLoanSingleton {
    private static ViewReadRemoteLoan _instance;

    private ShowRemoteLoanSingleton() {
    }

    public static ViewReadRemoteLoan getInstance() {
        if(_instance == null) {
            _instance = new ViewReadRemoteLoan();
        }

        return _instance;
    }
}