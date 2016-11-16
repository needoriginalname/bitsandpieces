package com.needoriginalname.bitsandpieces.blocks;

import com.needoriginalname.bitsandpieces.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/**
 * Created by Owner on 11/13/2016.
 */
abstract class BlockBnP extends Block {

    public BlockBnP(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    public BlockBnP(Material materialIn) {
        super(materialIn);
    }
/*
    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Reference.MODID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }


    public String getTextureName(int meta){
        return String.format("%s%s", Reference.MODID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
*/
}
