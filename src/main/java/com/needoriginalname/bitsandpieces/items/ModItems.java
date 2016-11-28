package com.needoriginalname.bitsandpieces.items;

import com.needoriginalname.bitsandpieces.items.armor.ItemIceSkates;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Owner on 11/28/2016.
 */
public class ModItems {
    public static ItemIceSkates iceSkates;

    public static void preInit(FMLPreInitializationEvent event){
        iceSkates = new ItemIceSkates();

        GameRegistry.register(iceSkates);
    }
}
