package com.needoriginalname.bitsandpieces.blocks;

import com.needoriginalname.bitsandpieces.reference.Name;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Al on 11/13/2016.
 *
 * Block Class for BnPLadder, a self-standing ladder.
 */

@SuppressWarnings("deprecation")
public class BnPLadder extends BlockLadder {

    protected static final AxisAlignedBB LADDER_NS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.40625D, 1.0D, 1.0D, 0.59375D);
    protected static final AxisAlignedBB LADDER_SW_AABB = new AxisAlignedBB(0.40625D, 0.0D, 0.0D, 0.59375D, 1.0D, 1.0D);

    protected static final AxisAlignedBB LADDER_EAST_AABB = LADDER_SW_AABB;
    protected static final AxisAlignedBB LADDER_WEST_AABB = LADDER_SW_AABB;
    protected static final AxisAlignedBB LADDER_SOUTH_AABB = LADDER_NS_AABB;
    protected static final AxisAlignedBB LADDER_NORTH_AABB = LADDER_NS_AABB;


    public BnPLadder() {
        super();
        this.setHardness(3.0f);
        this.setRegistryName(Name.BnPLadder);
        this.setUnlocalizedName(Name.BnPLadder);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING))
        {
            case NORTH:
                return LADDER_NORTH_AABB;
            case SOUTH:
                return LADDER_SOUTH_AABB;
            case WEST:
                return LADDER_WEST_AABB;
            case EAST:
            default:
                return LADDER_EAST_AABB;
        }
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        ItemStack stack = null;
        EnumHand hand = null;
        if (playerIn.getHeldItemMainhand() != null && playerIn.getHeldItemMainhand().getItem() == ModBlocks.bnpLadderIB ){
            stack = playerIn.getHeldItemMainhand();
            hand = EnumHand.MAIN_HAND;
        } else if(playerIn.getHeldItemOffhand() != null && playerIn.getHeldItemOffhand().getItem() == ModBlocks.bnpLadderIB) {
            stack = playerIn.getHeldItemOffhand();
            hand = EnumHand.OFF_HAND;
        }

        if (stack != null){
            BlockPos toppos = getTopBlockPos(worldIn, pos);
            if (toppos.up().getY() < worldIn.getActualHeight() && worldIn.isAirBlock(toppos.up())){

                --stack.stackSize;
                worldIn.setBlockState(toppos.up(), this.getDefaultState().withProperty(FACING, worldIn.getBlockState(toppos).getValue(FACING)));
                playerIn.setHeldItem(hand, null);
            }
        }

        super.onBlockClicked(worldIn, pos, playerIn);
    }

    private BlockPos getTopBlockPos(World worldIn, BlockPos pos) {
        BlockPos searchPos = pos;

        while(worldIn.getBlockState(searchPos.up()).getBlock() == this){
            searchPos = searchPos.up();
        }

        return searchPos;

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
    @Override
    protected boolean canBlockStay(World worldIn, BlockPos pos, EnumFacing facing)
    {
        return worldIn.isSideSolid(pos.down(), EnumFacing.UP) || worldIn.getBlockState(pos.down()).getBlock() == this;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        //is on a flat surfance or above another BnPLadder
        return worldIn.isSideSolid(pos.down(), EnumFacing.UP) || worldIn.getBlockState(pos.down()).getBlock() == this;
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
        // copys the facing properties of the ladders below it
        if (worldIn.getBlockState(pos.down()).getBlock() == this
                && worldIn.getBlockState(pos.down()).getPropertyNames().contains(FACING)){
            return this.getDefaultState().withProperty(FACING, worldIn.getBlockState(pos.down()).getValue(FACING));
        } else if (!facing.getAxis().isHorizontal()){

            return super.onBlockPlaced(worldIn, pos, placer.getHorizontalFacing(), hitX, hitY, hitZ, meta, placer);
        } else {
            return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(I18n.translateToLocal(Name.getFlavorTranslate(getUnlocalizedName())));
        }
        super.addInformation(stack, player, tooltip, advanced);
    }
}
