package at.team2.client.gui;

import at.team2.client.common.AccountManager;
import at.team2.client.controls.sidebar.MenuSection;
import at.team2.client.controls.sidebar.Sidebar;
import at.team2.client.helper.DialogHelper;
import at.team2.client.helper.RmiErrorHelper;
import at.team2.client.pages.PageAction;
import at.team2.client.singletons.*;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.client.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import javafx.application.Platform;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import at.team2.client.helper.gui.PageHelper;
import at.team2.client.configuration.Configuration;
import at.team2.client.pages.BasePage;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Navigation {
    private static Navigation _instance;
    private Sidebar _sidebar;
    private BorderPane _pagePane;
    private Configuration _configuration;
    private BasePage _currentPage;
    private List<BasePage> _initializedPageList = new LinkedList<>();
    private PageAction<Void, NullType> _loadPage;

    public static Navigation getInstance() {
        return _instance;
    }

    public static Navigation getInstance(BorderPane pagePane, Configuration configuration) {
        if(_instance == null) {
            _instance = new Navigation(pagePane, configuration);
        }

        return _instance;
    }

    private Navigation(BorderPane pagePane, Configuration configuration) {
        _configuration = configuration;
        _pagePane = pagePane;
        _sidebar = new Sidebar();

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

    public void loadStartPage() {
        loadPage(SearchMediumSingleton.getInstance());
    }

    private void initializeNavigationBar() {
        _sidebar.clear();
        AccountManager accountManager = AccountManager.getInstance();
        AccountDetailedDto account = accountManager.getAccount();

        //MenuSection menuSection;
        ArrayList<MenuSection> menuSectionArrayList = new ArrayList<>();
        /*MenuSectionItem menuSectionItem;
        ToggleGroup toggleGroup = new ToggleGroup();*/

        if(account != null) {
            MenuSection menuSectionLogout = new MenuSection("Logout: " + account.getUserName(),"/logout.jpg",null);
            menuSectionLogout.setTooltip(new Tooltip("Logout: " + account.getUserName()));
            menuSectionLogout.setAnimated(false);
            menuSectionLogout.setCollapsible(false);
            menuSectionLogout.setOnMouseClicked(event -> {
                DialogHelper.showYesNoDialog("Logout", "Do you want to logout?", "", _currentPage, () -> {
                            try {
                                MainRemoteObjectInf remoteObject = RmiHelper.getSession();
                                remoteObject.getAccountRemoteObject().logout(accountManager.getAccount());
                            } catch (Exception e) {
                                RmiErrorHelper.showRmiErrorMessage(e, _currentPage);
                            }

                            AccountManager.getInstance().setAccount(null);

                            Platform.runLater(() -> {
                                for(BasePage page : _initializedPageList) {
                                    page.reset();
                                }

                                _initializedPageList.clear();
                                loadPage(HomeScreenSingleton.getInstance());
                            });
                    },
                    null
                );
            });
            _sidebar.add(menuSectionLogout);
            menuSectionArrayList.add(menuSectionLogout);
        } else {
            MenuSection menuSectionHome = new MenuSection("Login", "/login.jpg", null);
            menuSectionHome.setTooltip(new Tooltip("Login"));
            menuSectionHome.setAnimated(false);
            menuSectionHome.setCollapsible(false);
            menuSectionHome.setOnMouseClicked(event -> loadPage(HomeScreenSingleton.getInstance()));
            _sidebar.add(menuSectionHome);
            menuSectionArrayList.add(menuSectionHome);

            menuSectionHome.setOnMouseClicked(event -> {
                for (MenuSection menuSection : menuSectionArrayList) {
                    menuSection.setExpanded(false);
                }

                loadPage(HomeScreenSingleton.getInstance());
            });
        }

        addSearchItem(menuSectionArrayList);

        // this can be used to conditionally add some menu items for the specified account role
        if(account != null) {
            switch (account.getAccountRole().getKey()) {
                case "BIBLIOTHEKAR":
                    addLoanItem(menuSectionArrayList);
                    addSearchCustomer(menuSectionArrayList);
                    addSendRemoteLoanItem(menuSectionArrayList);
                    break;
                case "ADMIN":
                    addLoanItem(menuSectionArrayList);
                    addSearchCustomer(menuSectionArrayList);
                    break;
                case "DATENPFLEGER":
                    break;
                case "AUSLEIHE":
                    addLoanItem(menuSectionArrayList);
                    addSearchCustomer(menuSectionArrayList);
                    break;
                case "BIBLIOTHEKSBENUTZER":
                    break;
                case "OPERATOR":
                    addReceiveRemoteLoanItem(menuSectionArrayList);
                    break;
            }
        }
    }

    private void addSearchCustomer(ArrayList<MenuSection> menuSectionArrayList) {
        MenuSection menuSectionSearchMedium = new MenuSection("Search Customer", "/customers.jpg", null);
        menuSectionSearchMedium.setTooltip(new Tooltip("Search Customer"));
        menuSectionSearchMedium.setAnimated(false);
        menuSectionSearchMedium.setCollapsible(false);
        menuSectionSearchMedium.setOnMouseClicked(event -> loadPage(SearchCustomerSingleton.getInstance()));
        _sidebar.add(menuSectionSearchMedium);
        menuSectionArrayList.add(menuSectionSearchMedium);
    }

    private void addSearchItem(ArrayList<MenuSection> menuSectionArrayList) {
        MenuSection menuSectionSearchMedium = new MenuSection("Search Media", "/search1600.png", null);
        menuSectionSearchMedium.setTooltip(new Tooltip("Search Media"));
        menuSectionSearchMedium.setAnimated(false);
        menuSectionSearchMedium.setCollapsible(false);
        menuSectionSearchMedium.setOnMouseClicked(event -> loadPage(SearchMediumSingleton.getInstance()));
        _sidebar.add(menuSectionSearchMedium);
        menuSectionArrayList.add(menuSectionSearchMedium);
    }

    private void addLoanItem(ArrayList<MenuSection> menuSectionArrayList) {
        MenuSection menuSectionLendMedium = new MenuSection("Lend Medium", "/Lend.png", null);
        menuSectionLendMedium.setTooltip(new Tooltip("Lend Medium"));
        menuSectionLendMedium.setAnimated(false);
        menuSectionLendMedium.setCollapsible(false);
        menuSectionLendMedium.setOnMouseClicked(event -> loadPage(LendMediumSingleton.getInstance()));
        _sidebar.add(menuSectionLendMedium);
        menuSectionArrayList.add(menuSectionLendMedium);
    }

    private void addSendRemoteLoanItem(ArrayList<MenuSection> menuSectionArrayList) {
        MenuSection menuSectionRemoteLoanItem = new MenuSection("Send Remote Loans","/ViewReadRemoteLoans.png",null);
        menuSectionRemoteLoanItem.setTooltip(new Tooltip("Send messages"));
        menuSectionRemoteLoanItem.setAnimated(false);
        menuSectionRemoteLoanItem.setCollapsible(false);
        menuSectionRemoteLoanItem.setOnMouseClicked(event -> loadPage(ReadRemoteLoanSingleton.getInstance()));
        _sidebar.add(menuSectionRemoteLoanItem);
        menuSectionArrayList.add(menuSectionRemoteLoanItem);
    }

    private void addReceiveRemoteLoanItem(ArrayList<MenuSection> menuSectionArrayList) {
        MenuSection menuSectionRemoteLoanItem = new MenuSection("Receive Remote Loans","/ViewReadRemoteLoans.png",null);
        menuSectionRemoteLoanItem.setTooltip(new Tooltip("Receive messages"));
        menuSectionRemoteLoanItem.setAnimated(false);
        menuSectionRemoteLoanItem.setCollapsible(false);
        menuSectionRemoteLoanItem.setOnMouseClicked(event -> loadPage(ShowRemoteLoanSingleton.getInstance()));
        _sidebar.add(menuSectionRemoteLoanItem);
        menuSectionArrayList.add(menuSectionRemoteLoanItem);
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
        if(PageHelper.load(_currentPage, page)) {
            initializeNavigationBar();
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