/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.registries.IForgeRegistry
 */
package ic2.api.network.buffer;

import ic2.api.network.buffer.NetworkInfo;
import java.util.UUID;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistry;

public interface IInputBuffer {
    public boolean readBoolean();

    public byte readByte();

    public short readShort();

    public int readMedium();

    public int readInt();

    public int readVarInt();

    public float readFloat();

    public double readDouble();

    public long readLong();

    public long readData(NetworkInfo.BitLevel var1);

    public char readChar();

    public <T extends Enum<T>> T readEnum(Class<T> var1);

    public byte[] readBytes();

    public String readString();

    public CompoundTag readNBTData();

    public <T> T readForgeRegistryEntry(IForgeRegistry<T> var1);

    public ItemStack readItemStack();

    public FluidStack readFluidStack();

    public UUID readUUID();

    public <T> ResourceKey<T> readRegistryKey(ResourceKey<Registry<T>> var1);
}

