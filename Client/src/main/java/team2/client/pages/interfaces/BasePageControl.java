package team2.client.pages.interfaces;

public interface BasePageControl {
    /**
     * initialize the instance with fundamental necessary data
     * only called once at the instance creation
     */
    public void initialize();

    /**
     * initialize the view
     * only called once at the instance creation
     */
    public void initializeView();

    /**
     * do something which could take a lot of time (it's not guaranteed that the view is accessible at this stage)
     * additionally this method can be called asynchronously
     */
    public void load();

    /**
     * reload the view and all necessary data to ensure a view as at the beginning
     */
    public void reload();

    /**
     * called when the page is going to exit
     * only called once
     */
    public void exit();

    /**
     * called to release resources which could lead to a memory leak
     * only called once
     */
    public void dispose();
}