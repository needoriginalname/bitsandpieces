package com.needoriginalname.bitsandpieces.handler;

import com.needoriginalname.bitsandpieces.reference.NbtTags;
import com.needoriginalname.bitsandpieces.reference.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

/**
 * Created by Owner on 11/15/2016.
 */
public class BitsAndPiecesWorldSavedData extends WorldSavedData {
    public BitsAndPiecesWorldSavedData () {
        super(Reference.MODID);
    }
    public BitsAndPiecesWorldSavedData (String s){
        super(s);
    }
    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     *
     * @param nbt
     */
    public BlockPos sunCapturerPos;
    public long sunCapturerTicksAlive;

    private NBTTagCompound initNBTTags(NBTTagCompound nbt){

        sunCapturerPos = new BlockPos.MutableBlockPos(0,0,0);

        if (!nbt.hasKey(NbtTags.SunCapturerTicksAlive)){
            nbt.setLong(NbtTags.SunCapturerTicksAlive, 0);
        }

        if (!nbt.hasKey(NbtTags.SunCapturerX)){
            nbt.setInteger(NbtTags.SunCapturerX, 0);
        }
        if (!nbt.hasKey(NbtTags.SunCapturerY)){
            nbt.setInteger(NbtTags.SunCapturerY, 0);
        }
        if (!nbt.hasKey(NbtTags.SunCapturerZ)){
            nbt.setInteger(NbtTags.SunCapturerZ, 0);
        }
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        nbt = initNBTTags(nbt);
        int x = nbt.getInteger(NbtTags.SunCapturerX);
        int y = nbt.getInteger(NbtTags.SunCapturerY);
        int z = nbt.getInteger(NbtTags.SunCapturerZ);
        if (sunCapturerPos.getX() != x || sunCapturerPos.getY() != y || sunCapturerPos.getZ() != z){
            sunCapturerPos = new BlockPos(x,y,z);
        }

        sunCapturerTicksAlive = nbt.getLong(NbtTags.SunCapturerTicksAlive);

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt = initNBTTags(nbt);

        nbt.setInteger(NbtTags.SunCapturerX, sunCapturerPos.getX());
        nbt.setInteger(NbtTags.SunCapturerY, sunCapturerPos.getY());
        nbt.setInteger(NbtTags.SunCapturerZ, sunCapturerPos.getZ());
        nbt.setLong(NbtTags.SunCapturerTicksAlive, sunCapturerTicksAlive);

        return nbt;

    }

    public static BitsAndPiecesWorldSavedData get(World world){
        MapStorage storage = world.getMapStorage();
        BitsAndPiecesWorldSavedData data = null;
        assert storage != null;
        data = (BitsAndPiecesWorldSavedData) storage.getOrLoadData(BitsAndPiecesWorldSavedData.class, Reference.MODID);

        if (data == null){
            data = new BitsAndPiecesWorldSavedData();


            storage.setData(Reference.MODID, data);

        }
        return data;
    }
}
