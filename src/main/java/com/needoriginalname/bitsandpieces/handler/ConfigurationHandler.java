package com.needoriginalname.bitsandpieces.handler;

import com.needoriginalname.bitsandpieces.reference.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * Created by Owner on 11/15/2016.
 */
public class ConfigurationHandler {

    public final static String SUN_CAPTURER_CATEGORY = "SUN CAPTURER";
    private static Configuration configuration;
    public static boolean isSunCapturerEnabled;
    public static int  sunCapturerLevel1;
    public static int sunCapturerLevel2;
    public static int sunCapturerRange;
    public static int sunCapturerTick;
    public static int sunCapturerLevel3;
    public static int sunCapturerLevel4;

    public static void init(File configFile){
        if (configuration == null){
            configuration = new Configuration(configFile);
            loadConfiguration();
            MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
        }
    }

    private static void loadConfiguration() {
        isSunCapturerEnabled = configuration.getBoolean("isSunCapturerEnabled", SUN_CAPTURER_CATEGORY, false, "Is the SunCapturerEnabled, NOT SUGGESTED FOR PUBLIC SERVERS.");
        sunCapturerRange = configuration.getInt("sunCapturerRange", SUN_CAPTURER_CATEGORY, 50, 5, 5000, "how many blocks in the x z plane a fire can spawn");
        sunCapturerTick = configuration.getInt("sunCapturerTick", SUN_CAPTURER_CATEGORY, 100, 1, 60*60*20, "how many ticks before trying to spawn in a new fire block");
        sunCapturerLevel1 = configuration.getInt("sunCapturerLevel1", SUN_CAPTURER_CATEGORY, 20*20*60, 0, Integer.MAX_VALUE, "when to start randomly starting fires, when sun capturer is started.");
        sunCapturerLevel2 = configuration.getInt("sunCapturerLevel2", SUN_CAPTURER_CATEGORY, 20*20*60*2, 1, Integer.MAX_VALUE, "when to stop fire from going out. Prevents rain for occurering, must be larger then level 1");
        sunCapturerLevel3 = configuration.getInt("sunCapturerLevel3", SUN_CAPTURER_CATEGORY, 20*20*60*3, 2, Integer.MAX_VALUE, "when to start destroying water in the search zone. Must be larger then level 2.");
        sunCapturerLevel4 = configuration.getInt("sunCapturerLevel4", SUN_CAPTURER_CATEGORY, 20*20*60*4, 3, Integer.MAX_VALUE, "when to start burning entities that can see sky. Must be larger then level 3.");

        if (sunCapturerLevel1 < sunCapturerLevel2){
            sunCapturerLevel2 = sunCapturerLevel1;
        }
        if (sunCapturerLevel2 < sunCapturerLevel3){
            sunCapturerLevel3 = sunCapturerLevel4;
        }

        if (sunCapturerLevel3 < sunCapturerLevel4){
            sunCapturerLevel4 = sunCapturerLevel3;
        }

        if (configuration.hasChanged()){
            configuration.save();
        }


    }

    public static Configuration getConfiguration(){
        return configuration;
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {

        if (event.getModID().equalsIgnoreCase(Reference.MODID))
        {
            loadConfiguration();
        }
    }

}
