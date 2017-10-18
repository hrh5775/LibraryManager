package team2.client.helper.gui;

import team2.client.pages.BasePage;

public class PageHelper {
    public static boolean load(BasePage page) {
        if(page != null) {
            if(exit(page)) {
                page.load();
                return true;
            }

            return false;
        }

        return false;
    }

    public static boolean exit(BasePage page) {
        if(page != null) {
            page.exit();
            return true;
        }

        return false;
    }
}