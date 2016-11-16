package com.needoriginalname.bitsandpieces.blocks;

import com.needoriginalname.bitsandpieces.blocks.tileentites.TESunCapturer;
import com.needoriginalname.bitsandpieces.handler.ConfigurationHandler;
import com.needoriginalname.bitsandpieces.handler.SunCaptuerHandler;
import com.needoriginalname.bitsandpieces.reference.Name;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

/**
 * Created by Owner on 11/15/2016.
 */
public class BlockSunCapturer extends BlockBnP {
    public BlockSunCapturer() {
        super(Material.IRON);
        this.setHardness(5.0f);
        this.setRegistryName(Name.SunCapturer);
        this.setUnlocalizedName(Name.SunCapturer);
        this.setCreativeTab(CreativeTabs.MISC);

    }



    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     *
     * @param worldIn
     * @param pos
     * @param state
     * @param placer
     * @param stack
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return ConfigurationHandler.isSunCapturerEnabled
                && !SunCaptuerHandler.isCaptureActive()
                && worldIn.getWorldTime() > 5500
                && worldIn.getWorldTime() < 6500
                && worldIn.canBlockSeeSky(pos);
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     *
     * @param worldIn
     * @param pos
     * @param facing
     * @param hitX
     * @param hitY
     * @param hitZ
     * @param meta
     * @param placer
     */
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        SunCaptuerHandler.startCapturing(worldIn, pos);
        return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

    /**
     * Called when a player destroys this Block
     *
     * @param worldIn
     * @param pos
     * @param state
     */
    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        SunCaptuerHandler.stopCapturing(worldIn, pos);
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
    }

    /**
     * Called when this Block is destroyed by an Explosion
     *
     * @param worldIn
     * @param pos
     * @param explosionIn
     */
    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        SunCaptuerHandler.stopCapturing(worldIn, pos);
        super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
    }
}