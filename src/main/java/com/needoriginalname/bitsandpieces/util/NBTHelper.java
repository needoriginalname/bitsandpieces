package com.needoriginalname.bitsandpieces.util;

import com.needoriginalname.bitsandpieces.reference.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Al on 5/3/2015.
 */
public class NBTHelper
{


    /**
     * Initializes the NBT Tag Compound for the given ItemStack if it is null
     *
     * @param itemStack
     *         The ItemStack for which its NBT Tag Compound is being checked for initialization
     */
    private static NBTTagCompound getModLevelTags(ItemStack itemStack)
    {
        if (itemStack.getTagCompound() == null)
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        if (!itemStack.getTagCompound().hasKey(Reference.MODID)){
            itemStack.getTagCompound().setTag(Reference.MODID, new NBTTagCompound());
        }

        return itemStack.getTagCompound().getCompoundTag(Reference.MODID);
    }

    private static NBTTagCompound getModLevelTags(Entity entity)
    {
        if (!entity.getEntityData().hasKey(Reference.MODID)){
            entity.getEntityData().setTag(Reference.MODID, new NBTTagCompound());
        }

        return entity.getEntityData().getCompoundTag(Reference.MODID);
    }


    // ItemStack
    // String
    public static String getString(ItemStack itemStack, String keyName){
        return getString(itemStack, keyName, "");
    }


    public static String getString(ItemStack itemStack, String keyName, String defaultValue)
    {
        if (!getModLevelTags(itemStack).hasKey(keyName))
        {
            setString(itemStack, keyName, defaultValue);
        }

        return getModLevelTags(itemStack).getString(keyName);
    }

    public static void setString(ItemStack itemStack, String keyName, String keyValue)
    {
        getModLevelTags(itemStack).setString(keyName, keyValue);
    }

    // boolean
    public static boolean getBoolean(ItemStack itemStack, String keyName)
    {
        return getBoolean(itemStack, keyName, false);
    }

    public static boolean getBoolean(ItemStack itemStack, String keyName, boolean defaultValue)
    {
        if (!getModLevelTags(itemStack).hasKey(keyName))
        {
            setBoolean(itemStack, keyName, defaultValue);
        }

        return getModLevelTags(itemStack).getBoolean(keyName);
    }

    public static void setBoolean(ItemStack itemStack, String keyName, boolean keyValue)
    {
        getModLevelTags(itemStack).setBoolean(keyName, keyValue);
    }

    // byte
    public static byte getByte(ItemStack itemStack, String keyName){
        return getByte(itemStack, keyName, (byte) 0);
    }

    public static byte getByte(ItemStack itemStack, String keyName, byte defaultValue)
    {
        if (!getModLevelTags(itemStack).hasKey(keyName))
        {
            setByte(itemStack, keyName, (byte) defaultValue);
        }

        return getModLevelTags(itemStack).getByte(keyName);
    }

    public static void setByte(ItemStack itemStack, String keyName, byte keyValue)
    {
        getModLevelTags(itemStack).setByte(keyName, keyValue);
    }

    // short
    public static short getShort(ItemStack itemStack, String keyName){
        return getShort(itemStack, keyName, (short) 0);
    }

    public static short getShort(ItemStack itemStack, String keyName, short defaultValue)
    {
        if (!getModLevelTags(itemStack).hasKey(keyName))
        {
            setShort(itemStack, keyName, (short) defaultValue);
        }

        return getModLevelTags(itemStack).getShort(keyName);
    }

    public static void setShort(ItemStack itemStack, String keyName, short keyValue)
    {
        getModLevelTags(itemStack).setShort(keyName, keyValue);
    }

    // int
    public static int getInt(ItemStack itemStack, String keyName){
        return getInt(itemStack, keyName, 0);
    }

    public static int getInt(ItemStack itemStack, String keyName, int defaultValue)
    {

        if (!getModLevelTags(itemStack).hasKey(keyName))
        {
            setInteger(itemStack, keyName, defaultValue);
        }

        return getModLevelTags(itemStack).getInteger(keyName);
    }

    public static void setInteger(ItemStack itemStack, String keyName, int keyValue)
    {
        getModLevelTags(itemStack).setInteger(keyName, keyValue);
    }




    // long
    public static long getLong(ItemStack itemStack, String keyName){
        return getLong(itemStack, keyName, 0);
    }

    public static long getLong(ItemStack itemStack, String keyName, long defaultValue)
    {
        if (!getModLevelTags(itemStack).hasKey(keyName))
        {
            setLong(itemStack, keyName, defaultValue);
        }

        return getModLevelTags(itemStack).getLong(keyName);
    }
    public static void setLong(ItemStack itemStack, String keyName, long keyValue)
    {
        getModLevelTags(itemStack).setLong(keyName, keyValue);
    }



    // float
    public static float getFloat(ItemStack itemStack, String keyName, float defaultValue)
    {
        if (!getModLevelTags(itemStack).hasKey(keyName))
        {
            setFloat(itemStack, keyName, defaultValue);
        }

        return getModLevelTags(itemStack).getFloat(keyName);
    }

    public static float getFloat(ItemStack itemStack, String keyName){
        return getFloat(itemStack, keyName, 0.0f);
    }

    public static void setFloat(ItemStack itemStack, String keyName, float keyValue)
    {
        getModLevelTags(itemStack).setFloat(keyName, keyValue);
    }
    // double
    public static double getDouble(ItemStack itemStack, String keyName, double defaultValue)
    {

        if (!getModLevelTags(itemStack).hasKey(keyName))
        {
            setDouble(itemStack, keyName, defaultValue);
        }

        return getModLevelTags(itemStack).getDouble(keyName);
    }

    public static double getDouble(ItemStack itemStack, String keyName)
    {
        return getDouble(itemStack, keyName, 0.0D);
    }

    public static void setDouble(ItemStack itemStack, String keyName, double keyValue)
    {
        getModLevelTags(itemStack).setDouble(keyName, keyValue);
    }


    //Entities
    //boolean
    public static boolean getBoolean(Entity entity, String keyName){
        return getBoolean(entity, keyName, false);
    }

    public static boolean getBoolean(Entity entity, String keyName, boolean defaultValue)
    {
        if (!getModLevelTags(entity).hasKey(keyName))
        {
            setBoolean(entity, keyName, defaultValue);
        }

        return getModLevelTags(entity).getBoolean(keyName);
    }

    public static void setBoolean(Entity entity, String keyName, boolean keyValue)
    {
        getModLevelTags(entity).setBoolean(keyName, keyValue);
    }

}