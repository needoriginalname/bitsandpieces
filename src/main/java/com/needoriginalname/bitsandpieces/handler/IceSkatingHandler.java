package com.needoriginalname.bitsandpieces.handler;

import com.needoriginalname.bitsandpieces.items.ModItems;
import com.needoriginalname.bitsandpieces.util.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Owner on 11/28/2016.
 */
public class IceSkatingHandler {
    private static final double speedMultiMax = 0.35D;
    private static final double speedMultiMin = 0.07D;

    private static final String motionXName = "motionX";
    private static final String motionZName = "motionZ";
    private static final String speedMultiplierName = "speedMultiplier";

    @SubscribeEvent
    public void onPlayerPostTick(TickEvent.PlayerTickEvent event){

        if (event.phase == TickEvent.Phase.END && event.side == Side.CLIENT){

            EntityPlayerSP player = (EntityPlayerSP) event.player;
            if (player.inventory.armorItemInSlot(0) != null
                    && player.inventory.armorItemInSlot(0).getItem() == ModItems.iceSkates){
                World world = ((EntityPlayerSP) event.player).worldObj;
                IBlockState blockBelowPlayer = world.getBlockState(player.getPosition().down());
                ItemStack iceSkatesStack = player.inventory.armorItemInSlot(0);
                if (blockBelowPlayer.getBlock() == Blocks.ICE
                        || blockBelowPlayer.getBlock() == Blocks.FROSTED_ICE
                        || blockBelowPlayer.getBlock() == Blocks.PACKED_ICE) {


                    float motionX = NBTHelper.getFloat(iceSkatesStack, motionXName);
                    float motionZ = NBTHelper.getFloat(iceSkatesStack, motionZName);
                    Double speedMultiplier = NBTHelper.getDouble(iceSkatesStack, speedMultiplierName, speedMultiMin);

                    double prevSpeed = Math.sqrt(motionX * motionX + motionZ *motionZ);
                    float f = player.rotationYaw + -player.moveStrafing * 90.0F;
                    motionX += -Math.sin((double)(f * (float)Math.PI / 180.0F)) * speedMultiplier * (double)player.moveForward * 0.05000000074505806D;
                    motionZ += Math.cos((double)(f * (float)Math.PI / 180.0F)) * speedMultiplier * (double)player.moveForward * 0.05000000074505806D;
                    double speed = Math.sqrt(motionX * motionX + motionZ * motionZ);

                    if (speed > speedMultiMax)
                    {
                        double d14 = speedMultiMax / prevSpeed;
                        motionX *= d14;
                        motionZ *= d14;
                        speed = speedMultiMax;
                    }

                    if (speed > prevSpeed && speedMultiplier < speedMultiMax)
                    {
                        speedMultiplier += (speedMultiMax - speedMultiplier) / speedMultiMax;

                        if (speedMultiplier > speedMultiMax)
                        {
                            speedMultiplier = speedMultiMax;
                        }
                    }
                    else
                    {
                        speedMultiplier -= (speedMultiplier - speedMultiMin) / speedMultiMax;

                        if (speedMultiplier < speedMultiMin)
                        {
                            speedMultiplier = speedMultiMin;
                        }
                    }


                    NBTHelper.setFloat(iceSkatesStack, motionXName, motionX);
                    NBTHelper.setFloat(iceSkatesStack, motionZName, motionZ);
                    NBTHelper.setDouble(iceSkatesStack, speedMultiplierName, speedMultiplier);

                    player.motionX = motionX;
                    player.motionZ = motionZ;
                } else {

                    NBTHelper.setFloat(iceSkatesStack, motionXName, 0);
                    NBTHelper.setFloat(iceSkatesStack, motionZName, 0);
                    NBTHelper.setDouble(iceSkatesStack, speedMultiplierName, speedMultiMin);
                }
            }


        }
    }
}
