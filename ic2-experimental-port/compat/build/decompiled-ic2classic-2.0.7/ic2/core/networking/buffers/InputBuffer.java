/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 */
package ic2.core.networking.buffers;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.UUID;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class InputBuffer
implements IInputBuffer {
    FriendlyByteBuf buffer;

    public InputBuffer(byte[] data) {
        this(new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])data)));
    }

    public InputBuffer(ByteBuf buf) {
        this(new FriendlyByteBuf(buf));
    }

    public InputBuffer(FriendlyByteBuf buffer) {
        this.buffer = buffer;
    }

    @Override
    public boolean readBoolean() {
        return this.buffer.readBoolean();
    }

    @Override
    public byte readByte() {
        return this.buffer.readByte();
    }

    @Override
    public short readShort() {
        return this.buffer.readShort();
    }

    @Override
    public int readMedium() {
        return this.buffer.readMedium();
    }

    @Override
    public int readInt() {
        return this.buffer.readInt();
    }

    @Override
    public int readVarInt() {
        return this.buffer.m_130242_();
    }

    @Override
    public float readFloat() {
        return this.buffer.readFloat();
    }

    @Override
    public double readDouble() {
        return this.buffer.readDouble();
    }

    @Override
    public long readLong() {
        return this.buffer.readLong();
    }

    @Override
    public long readData(NetworkInfo.BitLevel length) {
        if (length.isSkip()) {
            return 0L;
        }
        switch (length) {
            case BIT_0: {
                return 0L;
            }
            case BIT_8: {
                return this.buffer.readByte();
            }
            case BIT_16: {
                return this.buffer.readShort();
            }
            case BIT_24: {
                return this.buffer.readMedium();
            }
            case BIT_32: {
                return this.buffer.readInt();
            }
            case BIT_40: {
                return ((long)this.buffer.readByte() & 0xFFL) << 32 | ((long)this.buffer.readByte() & 0xFFL) << 24 | ((long)this.buffer.readByte() & 0xFFL) << 16 | ((long)this.buffer.readByte() & 0xFFL) << 8 | (long)this.buffer.readByte() & 0xFFL;
            }
            case BIT_48: {
                return ((long)this.buffer.readByte() & 0xFFL) << 40 | ((long)this.buffer.readByte() & 0xFFL) << 32 | ((long)this.buffer.readByte() & 0xFFL) << 24 | ((long)this.buffer.readByte() & 0xFFL) << 16 | ((long)this.buffer.readByte() & 0xFFL) << 8 | (long)this.buffer.readByte() & 0xFFL;
            }
            case BIT_56: {
                return ((long)this.buffer.readByte() & 0xFFL) << 48 | ((long)this.buffer.readByte() & 0xFFL) << 40 | ((long)this.buffer.readByte() & 0xFFL) << 32 | ((long)this.buffer.readByte() & 0xFFL) << 24 | ((long)this.buffer.readByte() & 0xFFL) << 16 | ((long)this.buffer.readByte() & 0xFFL) << 8 | (long)this.buffer.readByte() & 0xFFL;
            }
            case BIT_64: {
                return this.buffer.readLong();
            }
        }
        return 0L;
    }

    @Override
    public char readChar() {
        return this.buffer.readChar();
    }

    @Override
    public <T extends Enum<T>> T readEnum(Class<T> clz) {
        byte value = this.readByte();
        return (T)(value == 0 ? null : ((Enum[])clz.getEnumConstants())[value - 1]);
    }

    @Override
    public byte[] readBytes() {
        return this.buffer.m_130052_();
    }

    @Override
    public String readString() {
        return this.buffer.m_130136_(262144);
    }

    @Override
    public CompoundTag readNBTData() {
        return this.buffer.m_130260_();
    }

    @Override
    public <T> T readForgeRegistryEntry(IForgeRegistry<T> registry) {
        return (T)registry.getValue(this.buffer.m_130281_());
    }

    @Override
    public ItemStack readItemStack() {
        return this.buffer.m_130267_();
    }

    @Override
    public FluidStack readFluidStack() {
        if (!this.buffer.readBoolean()) {
            return FluidStack.EMPTY;
        }
        Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(this.buffer.m_130281_());
        FluidStack stack = new FluidStack(fluid, this.buffer.m_130242_());
        stack.setTag(this.buffer.m_130260_());
        return stack;
    }

    @Override
    public UUID readUUID() {
        return this.buffer.m_130259_();
    }

    @Override
    public <T> ResourceKey<T> readRegistryKey(ResourceKey<Registry<T>> key) {
        return ResourceKey.m_135785_(key, (ResourceLocation)this.buffer.m_130281_());
    }
}

