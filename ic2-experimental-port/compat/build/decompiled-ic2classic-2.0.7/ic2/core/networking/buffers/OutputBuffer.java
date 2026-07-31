/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 */
package ic2.core.networking.buffers;

import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class OutputBuffer
implements IOutputBuffer {
    FriendlyByteBuf buffer;

    public OutputBuffer(ByteBuf buf) {
        this(new FriendlyByteBuf(buf));
    }

    public OutputBuffer(FriendlyByteBuf buffer) {
        this.buffer = buffer;
    }

    @Override
    public void writeBoolean(boolean value) {
        this.buffer.writeBoolean(value);
    }

    @Override
    public void writeByte(byte value) {
        this.buffer.writeByte((int)value);
    }

    @Override
    public void writeShort(short value) {
        this.buffer.writeShort((int)value);
    }

    @Override
    public void writeMedium(int value) {
        this.buffer.writeMedium(value);
    }

    @Override
    public void writeInt(int value) {
        this.buffer.writeInt(value);
    }

    @Override
    public void writeVarInt(int value) {
        this.buffer.m_130130_(value);
    }

    @Override
    public void writeFloat(float value) {
        this.buffer.writeFloat(value);
    }

    @Override
    public void writeDouble(double value) {
        this.buffer.writeDouble(value);
    }

    @Override
    public void writeLong(long value) {
        this.buffer.writeLong(value);
    }

    @Override
    public void writeData(long value, NetworkInfo.BitLevel type) {
        if (type.isSkip()) {
            return;
        }
        switch (type) {
            case BIT_0: {
                break;
            }
            case BIT_8: {
                this.buffer.writeByte((int)value);
                break;
            }
            case BIT_16: {
                this.buffer.writeShort((int)value);
                break;
            }
            case BIT_24: {
                this.buffer.writeMedium((int)value);
                break;
            }
            case BIT_32: {
                this.buffer.writeInt((int)value);
                break;
            }
            case BIT_40: {
                this.buffer.writeByte((int)((byte)(value >>> 32)));
                this.buffer.writeByte((int)((byte)(value >>> 24)));
                this.buffer.writeByte((int)((byte)(value >>> 16)));
                this.buffer.writeByte((int)((byte)(value >>> 8)));
                this.buffer.writeByte((int)((byte)value));
                break;
            }
            case BIT_48: {
                this.buffer.writeByte((int)((byte)(value >>> 40)));
                this.buffer.writeByte((int)((byte)(value >>> 32)));
                this.buffer.writeByte((int)((byte)(value >>> 24)));
                this.buffer.writeByte((int)((byte)(value >>> 16)));
                this.buffer.writeByte((int)((byte)(value >>> 8)));
                this.buffer.writeByte((int)((byte)value));
                break;
            }
            case BIT_56: {
                this.buffer.writeByte((int)((byte)(value >>> 48)));
                this.buffer.writeByte((int)((byte)(value >>> 40)));
                this.buffer.writeByte((int)((byte)(value >>> 32)));
                this.buffer.writeByte((int)((byte)(value >>> 24)));
                this.buffer.writeByte((int)((byte)(value >>> 16)));
                this.buffer.writeByte((int)((byte)(value >>> 8)));
                this.buffer.writeByte((int)((byte)value));
                break;
            }
            case BIT_64: {
                this.buffer.writeLong(value);
            }
        }
    }

    @Override
    public void writeChar(char value) {
        this.buffer.writeChar((int)value);
    }

    @Override
    public void writeEnum(Enum<?> value) {
        this.buffer.writeByte(value == null ? 0 : value.ordinal() + 1);
    }

    @Override
    public void writeString(String value) {
        this.buffer.m_130072_(value, 262144);
    }

    @Override
    public void writeBytes(byte[] value) {
        this.buffer.m_130087_(value);
    }

    @Override
    public void writeNBTData(CompoundTag value) {
        this.buffer.m_130079_(value);
    }

    @Override
    public <T> void writeForgeEntry(T value, IForgeRegistry<T> registry) {
        this.buffer.m_130085_(registry.getKey(value));
    }

    @Override
    public void writeItemStack(ItemStack stack) {
        this.buffer.m_130055_(stack);
    }

    @Override
    public void writeFluidStack(FluidStack stack) {
        if (stack.isEmpty()) {
            this.buffer.writeBoolean(false);
            return;
        }
        this.buffer.writeBoolean(true);
        this.buffer.m_130085_(ForgeRegistries.FLUIDS.getKey((Object)stack.getFluid()));
        this.buffer.m_130130_(stack.getAmount());
        this.buffer.m_130079_(stack.getTag());
    }

    @Override
    public void writeUUID(UUID value) {
        this.buffer.m_130077_(value);
    }

    @Override
    public void writeRegistryKey(ResourceKey<?> key) {
        this.buffer.m_130085_(key.m_135782_());
    }
}

