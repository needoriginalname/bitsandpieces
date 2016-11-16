package com.needoriginalname.bitsandpieces.blocks.itemblocks;

import com.needoriginalname.bitsandpieces.blocks.ModBlocks;
import com.needoriginalname.bitsandpieces.handler.SunCaptuerHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by Owner on 11/15/2016.
 */
public class IBSunCapturer extends ItemBlock {
    public IBSunCapturer() {
        super(ModBlocks.sunCapturer);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return Minecraft.getMinecraft().theWorld.getWorldTime() > 5500
                && Minecraft.getMinecraft().theWorld.getWorldTime() < 6500;
    }
}
