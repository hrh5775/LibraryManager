package at.team2.client.gui;

import at.team2.client.controls.sidebar.MenuSection;
import at.team2.client.controls.sidebar.MenuSectionItem;
import at.team2.client.controls.sidebar.Sidebar;
import at.team2.client.pages.PageAction;
import at.team2.client.singletons.HomeScreenSingleton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import at.team2.client.helper.gui.PageHelper;
import at.team2.client.configuration.Configuration;
import at.team2.client.pages.BasePage;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NavigationBar {
    private Sidebar _sidebar;
    private BorderPane _pagePane;
    private Configuration _configuration;
    private BasePage _currentPage;
    private List<BasePage> _initializedPageList = new LinkedList<>();
    private PageAction<Void, NullType> _loadPage;

    public NavigationBar(BorderPane pagePane, Configuration configuration) {
        _configuration = configuration;
        _pagePane = pagePane;
        _sidebar = new Sidebar();

        //MenuSection menuSection;
        ArrayList<MenuSection> menuSectionArrayList = new ArrayList<>();
        MenuSectionItem menuSectionItem;
        ToggleGroup toggleGroup = new ToggleGroup();

        MenuSection menuSectionHome = new MenuSection("Home", "/homeM.png", null);
        menuSectionHome.setAnimated(false);
        menuSectionHome.setCollapsible(false);
        menuSectionHome.setOnMouseClicked(event -> loadPage(HomeScreenSingleton.getInstance()));
        _sidebar.add(menuSectionHome);
        menuSectionArrayList.add(menuSectionHome);

        MenuSection menuSectionUserScreen = new MenuSection("User Profile", "/userM.png", toggleGroup);
        /*menuSectionItem = new MenuSectionItem("Section Management");
        menuSectionItem.setOnAction(event -> {
        });
        menuSectionUserScreen.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musician Management");
        menuSectionItem.setOnAction(event -> {
        });*/
        //menuSectionUserScreen.add(menuSectionItem);
        /*_sidebar.add(menuSectionUserScreen);
        menuSectionArrayList.add(menuSectionUserScreen);*/

        menuSectionHome.setOnMouseClicked(event -> {
            for (MenuSection menuSection : menuSectionArrayList) {
                menuSection.setExpanded(false);
            }

            loadPage(HomeScreenSingleton.getInstance());
        });

        // load the default site
        loadPage(HomeScreenSingleton.getInstance());
    }

    public void setOnLoad(PageAction<Void, NullType> action) {
        _loadPage = action;
    }

    public Sidebar getNavigationBar() {
        return _sidebar;
    }

    public BasePage getCurrentPage() {
        return _currentPage;
    }

    private void setCurrentPage(BasePage page) {
        _currentPage = page;
        setPane(_currentPage);
    }

    private void addInitializedPageListItem(BasePage page) {
        if(!_initializedPageList.contains(page)) {
            //page.initializeView();
            _initializedPageList.add(page);
        }
    }

    private void setPane(BasePage page) {
        _pagePane.setCenter(page);
    }

    private boolean loadPage(BasePage page) {
        if(PageHelper.load(page)) {
            addInitializedPageListItem(page);
            setCurrentPage(page);

            if(_loadPage != null) {
                _loadPage.doAction(null);
            }

            return true;
        }

        return false;
    }

    private boolean exitPage(BasePage page) {
        if(PageHelper.exit(page)) {
            setCurrentPage(null);
            return true;
        }

        return false;
    }
}