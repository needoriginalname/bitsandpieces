package com.needoriginalname.bitsandpieces;

import com.needoriginalname.bitsandpieces.blocks.ModBlocks;
import com.needoriginalname.bitsandpieces.handler.ConfigurationHandler;
import com.needoriginalname.bitsandpieces.handler.IceSkatingHandler;
import com.needoriginalname.bitsandpieces.handler.SunCaptuerHandler;
import com.needoriginalname.bitsandpieces.items.ModItems;
import com.needoriginalname.bitsandpieces.reference.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Owner on 11/13/2016.
 */


@Mod(modid = Reference.MODID, version = "0.0.1", name = Reference.MOD_NAME, guiFactory = Reference.GUI_FACTORY)
public class BitsAndPiecesMod {

    @Mod.Instance(Reference.MODID)
    public static BitsAndPiecesMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        ModBlocks.preInit(event);
        ModItems.preInit(event);
        MinecraftForge.EVENT_BUS.register(new SunCaptuerHandler());
        MinecraftForge.EVENT_BUS.register(new IceSkatingHandler());
    }

    public void init(FMLInitializationEvent event){
        ModBlocks.init(event);
    }
}
