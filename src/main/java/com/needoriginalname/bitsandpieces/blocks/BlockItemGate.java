package com.needoriginalname.bitsandpieces.blocks;

import com.needoriginalname.bitsandpieces.reference.Name;
import net.minecraft.block.Block;
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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Owner on 11/24/2016.
 */

public class BlockItemGate extends BlockBnP{

    private static final PropertyDirection FACING = BlockDirectional.FACING;
    private static final PropertyBool OPENED = PropertyBool.create("opened");

    /** Ordering index for D-U-N-S-W-E */
    private static final AxisAlignedBB DOWN_BB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.1875, 1.0);
    private static final AxisAlignedBB UP_BB = new AxisAlignedBB(0.0, 0.8125, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB SOUTH_BB = new AxisAlignedBB(0.0, 0.0, 0.8125, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB NORTH_BB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.1875);
    private static final AxisAlignedBB WEST_BB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.1875, 1.0, 1.0);
    private static final AxisAlignedBB EAST_BB = new AxisAlignedBB(0.8125, 0.0, 0.0, 1.0, 1.0, 1.0);
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
     * Used in the renderer to apply ambient occlusion
     *
     * @param state
     */
    @Override
    public boolean isTranslucent(IBlockState state) {
        return true;
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
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     *
     * @param state
     * @param worldIn
     * @param pos
     * @param blockIn
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
        if (!worldIn.isRemote)
        {
            boolean isBeingPowered = worldIn.isBlockPowered(pos);
            boolean isCurrentlyPowered = !state.getValue(OPENED);


            if (isBeingPowered != isCurrentlyPowered){

                state = state.withProperty(OPENED, !isBeingPowered);
                SoundEvent sfx = state.getValue(OPENED) ? SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN : SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE;
                worldIn.playSound(null, pos.getX() + 0.5D ,pos.getY()+ 0.5D, pos.getZ() + 0.5D, sfx, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlockState(pos, state, 2);
            }




        }
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

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     *
     * @param state
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
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
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {

        if (!state.getValue(OPENED) || (entityIn != null && !(entityIn instanceof EntityItem))) {
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, null);
        } else if (entityIn == null){

            if (!worldIn.getEntitiesWithinAABB(EntityItem.class,
                    this.getCollisionBoundingBox(state,worldIn, pos).offset(pos)).isEmpty()){
                addCollisionBoxToList(pos, entityBox, collidingBoxes, NULL_AABB);
            } else {
                super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, null);
            }
        } else {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NULL_AABB);
        }
    }

    /**
     * Called When an Entity Collided with the Block
     *
     * @param worldIn
     * @param pos
     * @param state
     * @param entityIn
     */
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (entityIn instanceof EntityItem){
            //entityIn.setPositionAndUpdate(entityIn.posX, entityIn.posY-0.5f, entityIn.posZ);
        }
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
    }
}
