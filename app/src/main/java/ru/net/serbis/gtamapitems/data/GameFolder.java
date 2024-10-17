package ru.net.serbis.gtamapitems.data;
import java.util.*;

public class GameFolder
{
    private String name;
    private List<GameMap> games = new ArrayList<GameMap>();

    public GameFolder(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public String getFullName()
    {
        if (games.size() > 0)
        {
            return name + " (" + games.size() + ")";
        }
        return name;
    }

    public void add(GameMap game)
    {
        games.add(game);
    }

    public List<GameMap> getGames()
    {
        return games;
    }
}
