package com.needoriginalname.bitsandpieces.handler;

import com.needoriginalname.bitsandpieces.blocks.BlockBnpFire;
import com.needoriginalname.bitsandpieces.blocks.ModBlocks;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Random;

/**
 * Created by Owner on 11/15/2016.
 */
public class SunCaptuerHandler{


    public static boolean isCaptureActive(){
        return captureLocation != null;
    }
    public static BlockPos captureLocation;
    public static boolean isLevel1Active;
    public static boolean isLevel2Active;
    public static boolean isLevel3Active;
    public static boolean isLevel4Active;

    public static Random random = new Random();

    private static final int blockRange = 2;
    private BitsAndPiecesWorldSavedData savedWorldData;

    public SunCaptuerHandler(){
    }



    @SubscribeEvent
    public void onEntityLivingUpdate(LivingEvent.LivingUpdateEvent event){
        if (ConfigurationHandler.isSunCapturerEnabled && isLevel4Active){
            EntityLivingBase entity = event.getEntityLiving();
            if (entity.worldObj.provider.getDimension() == 0){
                if (entity.worldObj.canSeeSky(entity.getPosition())){
                    entity.setFire(6);
                }
            }



        }


    }


    @SubscribeEvent
    public void onPlayerPostTick(TickEvent.PlayerTickEvent event){
        if (ConfigurationHandler.isSunCapturerEnabled && event.player.worldObj.provider.getDimension() == 0){
            // finds a block to set on fire on take away water
            if (event.player.worldObj.getTotalWorldTime() % ConfigurationHandler.sunCapturerTick == 0 && isCaptureActive()){
                World w = event.player.worldObj;
                int range = random.nextInt(ConfigurationHandler.sunCapturerRange);
                float rad = (float) (random.nextFloat() * (2*Math.PI));
                double x = range * MathHelper.cos(rad);
                double z = range * MathHelper.sin(rad);

                BlockPos pos = w.getHeight(
                        new BlockPos(event.player.getPosition().getX() + x,
                        0,
                        event.player.getPosition().getZ() + z
                        ));
                // removes water or start fire
                if (isLevel1Active && w.canBlockBePlaced(ModBlocks.bnpfire, pos, false, EnumFacing.NORTH, null, null) && w.isSideSolid(pos.down(), EnumFacing.NORTH)){
                    w.setBlockState(pos, ModBlocks.bnpfire.getDefaultState());
                } else if (isLevel3Active && w.getBlockState(pos.down()).getBlock() == Blocks.WATER || w.getBlockState(pos.down()).getBlock() == Blocks.FLOWING_WATER){
                    BlockPos WaterBlockPos = pos.down();
                    for(int i = blockRange; i >= -blockRange; i--){
                        for(int j=blockRange; j>=-blockRange; j--){
                            for (int k = blockRange; k >=-blockRange; k--) {
                                BlockPos currentPos = WaterBlockPos.add(i, j, k);
                                if (w.canBlockSeeSky(currentPos) && w.getBlockState(currentPos).getBlock() == Blocks.WATER || w.getBlockState(currentPos).getBlock() == Blocks.FLOWING_WATER) {
                                    w.setBlockToAir(currentPos);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event){
        if (ConfigurationHandler.isSunCapturerEnabled && event.world.provider.getDimension() == 0 && event.side == Side.SERVER){
            // handles startup
            if (savedWorldData == null){
                savedWorldData = BitsAndPiecesWorldSavedData.get(event.world);
                if (savedWorldData.sunCapturerPos == null){
                    captureLocation = null;
                } else {
                    captureLocation = savedWorldData.sunCapturerPos;
                }
            }

            //increments the tick alive
            long ticksAlive;
            if (SunCaptuerHandler.isCaptureActive()) {
                ticksAlive = ++savedWorldData.sunCapturerTicksAlive;
            } else {
                ticksAlive = 0;
            }
            isLevel1Active = ticksAlive >= ConfigurationHandler.sunCapturerLevel1;
            isLevel2Active = ticksAlive >= ConfigurationHandler.sunCapturerLevel2;
            isLevel3Active = ticksAlive >= ConfigurationHandler.sunCapturerLevel3;
            isLevel4Active = ticksAlive >= ConfigurationHandler.sunCapturerLevel4;



            //check to see if block is still there
            if (isCaptureActive()){
                assert captureLocation != null;
                World w = event.world;
                IBlockState state = w.getBlockState(captureLocation);
                if (state.getBlock() != ModBlocks.sunCapturer){
                    stopCapturing(w, captureLocation);
                }
            }


            //puts data back to saved data

            savedWorldData.sunCapturerTicksAlive = ticksAlive;
            savedWorldData.sunCapturerPos = captureLocation;
            savedWorldData.markDirty();
        }
    }

    @SubscribeEvent
    public void onCreateFluidSourceEvent(BlockEvent.CreateFluidSourceEvent event){
        if(isLevel3Active && event.getWorld().provider.getDimension() == 0){
            if (event.getState().getBlock() == Blocks.WATER || event.getState().getBlock() == Blocks.FLOWING_WATER){
                event.setResult(Event.Result.DENY);
            } else if (event.getState().getBlock() == Blocks.LAVA|| event.getState().getBlock() == Blocks.FLOWING_LAVA){
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    public static void startCapturing(World world, BlockPos pos){
        captureLocation = pos;


    }

    public static void stopCapturing(World world, BlockPos pos){
        captureLocation = null;
    }


}
