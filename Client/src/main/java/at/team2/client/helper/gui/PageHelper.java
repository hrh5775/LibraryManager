package at.team2.client.helper.gui;

import at.team2.client.pages.BasePage;

public class PageHelper {
    public static boolean load(BasePage currentPage, BasePage nextPage) {
        if(nextPage != null) {
            if(currentPage == null || exit(currentPage)) {
                nextPage.load();
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