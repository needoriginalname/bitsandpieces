package com.needoriginalname.bitsandpieces.blocks;

import com.needoriginalname.bitsandpieces.reference.Name;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Owner on 11/24/2016.
 */

public class BlockItemGate extends BlockBnP{

    private static final PropertyDirection FACING = BlockDirectional.FACING;
    private static final PropertyBool OPENED = PropertyBool.create("opened");

    /** Ordering index for D-U-N-S-W-E */
    private static final AxisAlignedBB DOWN_BB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.1, 1.0);
    private static final AxisAlignedBB UP_BB = new AxisAlignedBB(0.0, 0.9, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB NORTH_BB = new AxisAlignedBB(0.0, 0.0, 0.9, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB SOUTH_BB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.1);
    private static final AxisAlignedBB WEST_BB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.1, 1.0, 1.0);
    private static final AxisAlignedBB EAST_BB = new AxisAlignedBB(0.9, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB[] BBs = new AxisAlignedBB[] {DOWN_BB, UP_BB, NORTH_BB, SOUTH_BB, WEST_BB, EAST_BB};


    /*
    Metadata flags, OFFF
    Flags: P: Polarity, O: Opened, F: facing, x:Unused
     */
    private static final byte FACING_FLAG = 7;
    private static final byte OPENED_FLAG = 8;

    public BlockItemGate() {
        super(Material.ROCK);
        this.setRegistryName(Name.ItemGate);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN).withProperty(OPENED, true));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, OPENED);
    }

    private void handleOpenProperty(IBlockState state, World world, BlockPos pos){
        boolean powered = world.isBlockPowered(pos);
        boolean opened = powered;
        state = state.withProperty(OPENED, opened);

    }


    /**
     * Indicate if a material is a normal solid opaque cube
     *
     * @param state
     */
    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    /**
     * Return true if the block is a normal, solid cube.  This
     * determines indirect power state, entity ejection from blocks, and a few
     * others.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @return True if the block is a full cube
     */
    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }




    /**
     * Whether this Block is solid on the given Side
     *
     * @param worldIn
     * @param pos
     * @param side
     */
    @Override
    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
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
        IBlockState state = this.getDefaultState();
        facing = facing.getOpposite();

        if (this.canPlaceBlockOnSide(worldIn, pos, facing)) {
            state = state.withProperty(FACING, facing);
        } else {
            state = state.withProperty(FACING, EnumFacing.DOWN);
        }
        handleOpenProperty(state, worldIn, pos);

        return state;

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BBs[state.getValue(FACING).getIndex()];
    }




    /**
     * Convert the BlockState into the correct metadata value
     *
     * @param state
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        boolean openedFlag = state.getValue(OPENED);
        int facingFlag = state.getValue(FACING).getIndex();

        int meta = facingFlag;
        if (openedFlag) meta |= 8;
        return meta;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     *
     * @param meta
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = getDefaultState();
        int facingFlag = meta & FACING_FLAG;
        boolean openedFlag = (meta & OPENED_FLAG) == OPENED_FLAG;
        state = state.withProperty(FACING, EnumFacing.values()[facingFlag]);
        state = state.withProperty(OPENED, openedFlag);

        return state;
    }


    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {

        if (entityIn == null || !state.getValue(OPENED) || !(entityIn instanceof EntityItem)) {
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn);
        } else {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NULL_AABB);
        }
    }
}
