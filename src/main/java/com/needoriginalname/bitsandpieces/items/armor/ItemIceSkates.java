package com.needoriginalname.bitsandpieces.items.armor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Created by Owner on 11/28/2016.
 */
public class ItemIceSkates extends ItemArmor{


    public ItemIceSkates() {
        super(ArmorMaterial.LEATHER, 0 , EntityEquipmentSlot.FEET);
        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
        this.setRegistryName("iceSkates");
        this.setUnlocalizedName("iceSkates");

    }

    /**
     * Return whether this item is repairable in an anvil.
     *
     * @param toRepair
     * @param repair
     */
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.IRON_INGOT;
    }

    /**
     * Called to tick armor in the armor slot. Override to do something
     *
     * @param world
     * @param player
     * @param itemStack
     */
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player.onGround) {
            IBlockState blockBelowPlayer = world.getBlockState(player.getPosition().down());
            if (blockBelowPlayer.getBlock() == Blocks.ICE || blockBelowPlayer.getBlock() == Blocks.FROSTED_ICE || blockBelowPlayer.getBlock() == Blocks.PACKED_ICE) {
                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("speed"), 4, 1, false, false));
                //TODO: add skating effects


            } else if (blockBelowPlayer.getBlock() != Blocks.AIR){
                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("slowness"), 4, 2, false, false));
            }
        }
    }
}
