package at.team2.client.singletons;

import at.team2.client.pages.sendremoteloan.SendRemoteLoan;

public class ReadRemoteLoanSingleton {
    private static SendRemoteLoan _instance;

    private ReadRemoteLoanSingleton() {
    }

    public static SendRemoteLoan getInstance() {
        if(_instance == null) {
            _instance = new SendRemoteLoan();
        }

        return _instance;
    }
}