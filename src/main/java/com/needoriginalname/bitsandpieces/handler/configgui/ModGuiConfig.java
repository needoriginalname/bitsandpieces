package com.needoriginalname.bitsandpieces.handler.configgui;

import com.needoriginalname.bitsandpieces.handler.ConfigurationHandler;
import com.needoriginalname.bitsandpieces.reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * Created by Owner on 11/15/2016.
 */
public class ModGuiConfig extends GuiConfig {

    public ModGuiConfig(GuiScreen guiScreen){



        super(guiScreen, new ConfigElement(ConfigurationHandler.getConfiguration().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                Reference.MODID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(ConfigurationHandler.getConfiguration().toString()));
    }
}
