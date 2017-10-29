package at.team2.client.singletons;

import at.team2.client.pages.lendMedium.LendMedium;

public class LendMediumSingleton
{
    private static LendMedium _lendMedium;

    private LendMediumSingleton()
    {

    }

    public static LendMedium getInstance()
    {
        if(_lendMedium == null)
        {
            _lendMedium = new LendMedium();
        }
        return _lendMedium;
    }
}
