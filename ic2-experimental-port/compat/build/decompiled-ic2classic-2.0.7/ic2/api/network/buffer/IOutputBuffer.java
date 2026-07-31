/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.registries.IForgeRegistry
 */
package ic2.api.network.buffer;

import ic2.api.network.buffer.NetworkInfo;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistry;

public interface IOutputBuffer {
    public void writeBoolean(boolean var1);

    public void writeByte(byte var1);

    public void writeShort(short var1);

    public void writeMedium(int var1);

    public void writeInt(int var1);

    public void writeVarInt(int var1);

    public void writeFloat(float var1);

    public void writeDouble(double var1);

    public void writeLong(long var1);

    public void writeData(long var1, NetworkInfo.BitLevel var3);

    public void writeChar(char var1);

    public void writeEnum(Enum<?> var1);

    public void writeString(String var1);

    public void writeBytes(byte[] var1);

    public void writeNBTData(CompoundTag var1);

    public <T> void writeForgeEntry(T var1, IForgeRegistry<T> var2);

    public void writeItemStack(ItemStack var1);

    public void writeFluidStack(FluidStack var1);

    public void writeUUID(UUID var1);

    public void writeRegistryKey(ResourceKey<?> var1);

    public static void writeItemStack(ItemStack stack, IOutputBuffer buffer) {
        buffer.writeItemStack(stack);
    }

    public static void writeEnum(Enum<?> value, IOutputBuffer buffer) {
        buffer.writeEnum(value);
    }
}

