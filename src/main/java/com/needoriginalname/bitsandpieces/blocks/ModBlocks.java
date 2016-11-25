package com.needoriginalname.bitsandpieces.blocks;

import com.needoriginalname.bitsandpieces.blocks.itemblocks.IBSunCapturer;
import com.needoriginalname.bitsandpieces.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Owner on 11/13/2016.
 */
public class ModBlocks {

    public static BnPLadder bnpladder;
    public static ItemBlock bnpLadderIB;
    public static BlockBnpFire bnpfire;
    public static BlockSunCapturer sunCapturer;
    public static IBSunCapturer ibSunCapturer;
    public static BlockItemGate blockItemGate;
    public static ItemBlock ibBlockItemGate;

    public static void preInit(FMLPreInitializationEvent event){
        bnpladder = new BnPLadder();
        bnpfire = new BlockBnpFire();
        bnpLadderIB = registerBlockAndItemBlock(event, bnpladder);
        sunCapturer = new BlockSunCapturer();
        ibSunCapturer = new IBSunCapturer();
        blockItemGate = new BlockItemGate();
        ibBlockItemGate = registerBlockAndItemBlock(event, blockItemGate);
        registerBlockAndItemBlock(event, sunCapturer, ibSunCapturer);
        GameRegistry.register(bnpfire);
    }


    private static ItemBlock registerBlockAndItemBlock(FMLPreInitializationEvent event, Block block){
        ItemBlock ib = new ItemBlock(block);
        return registerBlockAndItemBlock(event, block, ib);
    }

    private static ItemBlock registerBlockAndItemBlock(FMLPreInitializationEvent event, Block block, ItemBlock ib){

        ib.setRegistryName(block.getRegistryName());
        GameRegistry.register(block);
        GameRegistry.register(ib);

        if (event.getSide() == Side.CLIENT) {
            ModelResourceLocation modelLocation = new ModelResourceLocation(ib.getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(ib, 0, modelLocation);
        }

        return ib;
    }

    public static void init(FMLInitializationEvent event) {

    }
}
