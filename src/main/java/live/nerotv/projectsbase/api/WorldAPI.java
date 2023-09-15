package live.nerotv.projectsbase.api;

import org.bukkit.*;
import org.bukkit.World.*;
import live.nerotv.projectsbase.generator.*;
import org.bukkit.block.Biome;

public class WorldAPI {

    public static void createWorld(String mapName, Environment environment, WorldType type, boolean structures) {
        if(Bukkit.getWorld(mapName)==null) {
            new WorldCreator(mapName).generateStructures(structures).environment(environment).type(type).createWorld();
        }
    }

    public static void createVoidWorld(String mapName, Environment environment) {
        if(Bukkit.getWorld(mapName)==null) {
            new WorldCreator(mapName).generateStructures(false).environment(environment).type(WorldType.FLAT).generator(new VoidGenerator()).createWorld();
        }
    }

    public static void addTime(long time, World world) {
        long t = world.getTime()+time;
        world.setTime(t);
    }

    public static void removeTime(long time, World world) {
        long t = world.getTime()-time;
        world.setTime(t);
    }

    public static void setDifficulty(World world, String difficultyname) {
        Difficulty diff = resolveDifficulty(difficultyname);
        if(diff!=null) {
            world.setDifficulty(diff);
        }
    }

    public static void setDifficulty(String worldname, String difficultyname) {
        Difficulty diff = resolveDifficulty(difficultyname);
        World world = Bukkit.getServer().getWorld(worldname);
        if(world != null)  {
            if(diff!=null) {
                world.setDifficulty(diff);
            }
        }
    }

    public static Difficulty resolveDifficulty(String difficulty) {
        if(difficulty.equalsIgnoreCase("peaceful")) {
            return Difficulty.PEACEFUL;
        } else if(difficulty.equalsIgnoreCase("peacefull")) {
            return Difficulty.PEACEFUL;
        } else if(difficulty.equalsIgnoreCase("0")) {
            return Difficulty.PEACEFUL;
        } else if(difficulty.equalsIgnoreCase("easy")) {
            return Difficulty.EASY;
        } else if(difficulty.equalsIgnoreCase("1")) {
            return Difficulty.EASY;
        } else if(difficulty.equalsIgnoreCase("normal")) {
            return Difficulty.NORMAL;
        } else if(difficulty.equalsIgnoreCase("2")) {
            return Difficulty.NORMAL;
        } else if(difficulty.equalsIgnoreCase("hard")) {
            return Difficulty.HARD;
        } else if(difficulty.equalsIgnoreCase("3")) {
            return Difficulty.HARD;
        } else {
            return null;
        }
    }
}