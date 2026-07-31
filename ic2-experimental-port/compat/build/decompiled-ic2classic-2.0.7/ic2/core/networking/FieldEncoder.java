/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.templates.FluidTank
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.networking;

import com.mojang.authlib.GameProfile;
import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRegistry;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.buffers.OutputBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;

public class FieldEncoder {
    public static void encode(IOutputBuffer buffer, Object obj) {
        FieldEncoder.encode(buffer, obj, NetworkInfo.BitLevel.BIT_0);
    }

    public static void encode(IOutputBuffer buffer, Object obj, NetworkInfo.BitLevel level) {
        if (obj == null) {
            buffer.writeByte((byte)-1);
        } else if (obj instanceof Number) {
            if (obj instanceof Integer) {
                buffer.writeByte((byte)0);
                if (level.isValid(NetworkInfo.BitLevel.BIT_32)) {
                    buffer.writeData(((Integer)obj).intValue(), level);
                } else {
                    buffer.writeInt((Integer)obj);
                }
            } else if (obj instanceof Short) {
                buffer.writeByte((byte)1);
                if (level.isValid(NetworkInfo.BitLevel.BIT_16)) {
                    buffer.writeData(((Short)obj).shortValue(), level);
                } else {
                    buffer.writeShort((Short)obj);
                }
            } else if (obj instanceof Long) {
                buffer.writeByte((byte)2);
                if (level.isValid(NetworkInfo.BitLevel.BIT_64)) {
                    buffer.writeData((Long)obj, level);
                } else {
                    buffer.writeLong((Long)obj);
                }
            } else if (obj instanceof Float) {
                buffer.writeByte((byte)3);
                buffer.writeFloat(((Float)obj).floatValue());
            } else if (obj instanceof Double) {
                buffer.writeByte((byte)4);
                buffer.writeDouble((Double)obj);
            } else if (obj instanceof Byte) {
                buffer.writeByte((byte)5);
                buffer.writeByte((Byte)obj);
            }
        } else if (obj instanceof Boolean) {
            buffer.writeByte((byte)6);
            buffer.writeBoolean((Boolean)obj);
        } else if (obj instanceof String) {
            buffer.writeByte((byte)7);
            buffer.writeString((String)obj);
        } else if (obj.getClass().isArray()) {
            if (obj instanceof int[]) {
                buffer.writeByte((byte)8);
                int[] array = (int[])obj;
                if (level.isValid(NetworkInfo.BitLevel.BIT_32)) {
                    buffer.writeData(array.length, level);
                } else {
                    buffer.writeInt(array.length);
                }
                for (int i = 0; i < array.length; ++i) {
                    buffer.writeInt(array[i]);
                }
            } else if (obj instanceof short[]) {
                buffer.writeByte((byte)9);
                short[] array = (short[])obj;
                if (level.isValid(NetworkInfo.BitLevel.BIT_32)) {
                    buffer.writeData(array.length, level);
                } else {
                    buffer.writeInt(array.length);
                }
                for (int i = 0; i < array.length; ++i) {
                    buffer.writeShort(array[i]);
                }
            } else if (obj instanceof long[]) {
                buffer.writeByte((byte)10);
                long[] array = (long[])obj;
                if (level.isValid(NetworkInfo.BitLevel.BIT_32)) {
                    buffer.writeData(array.length, level);
                } else {
                    buffer.writeInt(array.length);
                }
                for (int i = 0; i < array.length; ++i) {
                    buffer.writeLong(array[i]);
                }
            } else if (obj instanceof float[]) {
                buffer.writeByte((byte)11);
                float[] array = (float[])obj;
                if (level.isValid(NetworkInfo.BitLevel.BIT_32)) {
                    buffer.writeData(array.length, level);
                } else {
                    buffer.writeInt(array.length);
                }
                for (int i = 0; i < array.length; ++i) {
                    buffer.writeFloat(array[i]);
                }
            } else if (obj instanceof double[]) {
                buffer.writeByte((byte)12);
                double[] array = (double[])obj;
                if (level.isValid(NetworkInfo.BitLevel.BIT_32)) {
                    buffer.writeData(array.length, level);
                } else {
                    buffer.writeInt(array.length);
                }
                for (int i = 0; i < array.length; ++i) {
                    buffer.writeDouble(array[i]);
                }
            } else if (obj instanceof byte[]) {
                buffer.writeByte((byte)13);
                buffer.writeBytes((byte[])obj);
            } else if (obj instanceof boolean[]) {
                buffer.writeByte((byte)14);
                boolean[] array = (boolean[])obj;
                if (level.isValid(NetworkInfo.BitLevel.BIT_32)) {
                    buffer.writeData(array.length, level);
                } else {
                    buffer.writeInt(array.length);
                }
                for (int i = 0; i < array.length; ++i) {
                    buffer.writeBoolean(array[i]);
                }
            } else if (obj instanceof String[]) {
                buffer.writeByte((byte)15);
                String[] array = (String[])obj;
                if (level.isValid(NetworkInfo.BitLevel.BIT_32)) {
                    buffer.writeData(array.length, level);
                } else {
                    buffer.writeInt(array.length);
                }
                for (int i = 0; i < array.length; ++i) {
                    buffer.writeString(array[i]);
                }
            }
        } else if (obj instanceof INetworkDataBuffer) {
            buffer.writeByte((byte)16);
            try {
                ByteBuf buf = Unpooled.buffer();
                ((INetworkDataBuffer)obj).write(new OutputBuffer(new FriendlyByteBuf(buf)));
                byte[] data = new byte[buf.writerIndex()];
                buf.readBytes(data);
                buffer.writeBytes(data);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else if (obj instanceof ItemStack) {
            buffer.writeByte((byte)17);
            buffer.writeItemStack((ItemStack)obj);
        } else if (obj instanceof Item) {
            buffer.writeByte((byte)18);
            buffer.writeForgeEntry((Item)obj, ForgeRegistries.ITEMS);
        } else if (obj instanceof Block) {
            buffer.writeByte((byte)19);
            buffer.writeForgeEntry((Block)obj, ForgeRegistries.BLOCKS);
        } else if (obj instanceof Enchantment) {
            buffer.writeByte((byte)20);
            buffer.writeForgeEntry((Enchantment)obj, ForgeRegistries.ENCHANTMENTS);
        } else if (obj instanceof MobEffect) {
            buffer.writeByte((byte)21);
            buffer.writeForgeEntry((MobEffect)obj, ForgeRegistries.MOB_EFFECTS);
        } else if (obj instanceof ICrop) {
            buffer.writeByte((byte)22);
            buffer.writeString(((ICrop)obj).id().toString());
        } else if (obj instanceof CompoundTag) {
            buffer.writeByte((byte)23);
            buffer.writeNBTData((CompoundTag)obj);
        } else if (obj instanceof BlockPos) {
            buffer.writeByte((byte)24);
            BlockPos pos = (BlockPos)obj;
            buffer.writeLong(pos.m_121878_());
        } else if (obj instanceof Vec3) {
            buffer.writeByte((byte)25);
            Vec3 pos = (Vec3)obj;
            buffer.writeDouble(pos.f_82479_);
            buffer.writeDouble(pos.f_82480_);
            buffer.writeDouble(pos.f_82481_);
        } else if (obj instanceof Vec3i) {
            buffer.writeByte((byte)26);
            Vec3i pos = (Vec3i)obj;
            buffer.writeInt(pos.m_123341_());
            buffer.writeInt(pos.m_123342_());
            buffer.writeInt(pos.m_123343_());
        } else if (obj instanceof BlockEntity) {
            buffer.writeByte((byte)27);
            BlockEntity tile = (BlockEntity)obj;
            buffer.writeRegistryKey(tile.m_58904_().m_46472_());
            buffer.writeLong(tile.m_58899_().m_121878_());
        } else if (obj instanceof Level) {
            buffer.writeByte((byte)28);
            buffer.writeRegistryKey(((Level)obj).m_46472_());
        } else if (obj instanceof FluidStack) {
            buffer.writeByte((byte)29);
            buffer.writeFluidStack((FluidStack)obj);
        } else if (obj instanceof FluidTank) {
            buffer.writeByte((byte)30);
            FluidTank tank = (FluidTank)obj;
            boolean empty = tank.getFluid().isEmpty();
            buffer.writeBoolean(empty);
            buffer.writeInt(tank.getCapacity());
            if (!empty) {
                buffer.writeFluidStack(tank.getFluid());
            }
        } else if (obj instanceof UUID) {
            buffer.writeByte((byte)31);
            buffer.writeUUID((UUID)obj);
        } else if (obj instanceof GameProfile) {
            buffer.writeByte((byte)32);
            GameProfile prof = (GameProfile)obj;
            buffer.writeString(prof.getName());
            buffer.writeUUID(prof.getId());
        } else if (obj instanceof Enum) {
            buffer.writeByte((byte)33);
            buffer.writeString(((Enum)obj).name());
        } else if (obj instanceof ResourceLocation) {
            buffer.writeByte((byte)34);
            buffer.writeString(((ResourceLocation)obj).toString());
        } else if (obj instanceof DirectionList) {
            buffer.writeByte((byte)35);
            buffer.writeByte((byte)((DirectionList)obj).getCode());
        } else {
            IC2.LOGGER.info("Unknown Object: " + obj);
        }
    }

    public static Object decode(IInputBuffer buffer) {
        return FieldEncoder.decode(buffer, NetworkInfo.BitLevel.BIT_0);
    }

    public static Object decode(IInputBuffer buffer, NetworkInfo.BitLevel level) {
        byte index = buffer.readByte();
        switch (index) {
            case -1: {
                return null;
            }
            case 0: {
                return level.isValid(NetworkInfo.BitLevel.BIT_32) ? (int)buffer.readData(level) : buffer.readInt();
            }
            case 1: {
                return level.isValid(NetworkInfo.BitLevel.BIT_16) ? (short)buffer.readData(level) : buffer.readShort();
            }
            case 2: {
                return level.isValid(NetworkInfo.BitLevel.BIT_64) ? buffer.readData(level) : buffer.readLong();
            }
            case 3: {
                return Float.valueOf(buffer.readFloat());
            }
            case 4: {
                return buffer.readDouble();
            }
            case 5: {
                return buffer.readByte();
            }
            case 6: {
                return buffer.readBoolean();
            }
            case 7: {
                return buffer.readString();
            }
            case 8: {
                int[] array = new int[level.isValid(NetworkInfo.BitLevel.BIT_32) ? (int)buffer.readData(level) : buffer.readInt()];
                for (int i = 0; i < array.length; ++i) {
                    array[i] = buffer.readInt();
                }
                return array;
            }
            case 9: {
                short[] array = new short[level.isValid(NetworkInfo.BitLevel.BIT_32) ? (int)buffer.readData(level) : buffer.readInt()];
                for (int i = 0; i < array.length; ++i) {
                    array[i] = buffer.readShort();
                }
                return array;
            }
            case 10: {
                long[] array = new long[level.isValid(NetworkInfo.BitLevel.BIT_32) ? (int)buffer.readData(level) : buffer.readInt()];
                for (int i = 0; i < array.length; ++i) {
                    array[i] = buffer.readLong();
                }
                return array;
            }
            case 11: {
                float[] array = new float[level.isValid(NetworkInfo.BitLevel.BIT_32) ? (int)buffer.readData(level) : buffer.readInt()];
                for (int i = 0; i < array.length; ++i) {
                    array[i] = buffer.readFloat();
                }
                return array;
            }
            case 12: {
                double[] array = new double[level.isValid(NetworkInfo.BitLevel.BIT_32) ? (int)buffer.readData(level) : buffer.readInt()];
                for (int i = 0; i < array.length; ++i) {
                    array[i] = buffer.readDouble();
                }
                return array;
            }
            case 13: {
                return buffer.readBytes();
            }
            case 14: {
                boolean[] array = new boolean[level.isValid(NetworkInfo.BitLevel.BIT_32) ? (int)buffer.readData(level) : buffer.readInt()];
                for (int i = 0; i < array.length; ++i) {
                    array[i] = buffer.readBoolean();
                }
                return array;
            }
            case 15: {
                String[] array = new String[level.isValid(NetworkInfo.BitLevel.BIT_32) ? (int)buffer.readData(level) : buffer.readInt()];
                for (int i = 0; i < array.length; ++i) {
                    array[i] = buffer.readString();
                }
                return array;
            }
            case 16: {
                return new InputBuffer(new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])buffer.readBytes())));
            }
            case 17: {
                return buffer.readItemStack();
            }
            case 18: {
                return buffer.readForgeRegistryEntry(ForgeRegistries.ITEMS);
            }
            case 19: {
                return buffer.readForgeRegistryEntry(ForgeRegistries.BLOCKS);
            }
            case 20: {
                return buffer.readForgeRegistryEntry(ForgeRegistries.ENCHANTMENTS);
            }
            case 21: {
                return buffer.readForgeRegistryEntry(ForgeRegistries.MOB_EFFECTS);
            }
            case 22: {
                return ICropRegistry.INSTANCE.getCrop(new ResourceLocation(buffer.readString()));
            }
            case 23: {
                return buffer.readNBTData();
            }
            case 24: {
                return BlockPos.m_122022_((long)buffer.readLong());
            }
            case 25: {
                double x = buffer.readDouble();
                double y = buffer.readDouble();
                double z = buffer.readDouble();
                return new Vec3i(x, y, z);
            }
            case 26: {
                int x = buffer.readInt();
                int y = buffer.readInt();
                int z = buffer.readInt();
                return new Vec3i(x, y, z);
            }
            case 27: {
                Level world = IC2.PLATFORM.getWorld(buffer.readRegistryKey(Registry.f_122819_));
                if (world == null) {
                    return null;
                }
                return world.m_7702_(BlockPos.m_122022_((long)buffer.readLong()));
            }
            case 28: {
                return IC2.PLATFORM.getWorld(buffer.readRegistryKey(Registry.f_122819_));
            }
            case 29: {
                return buffer.readFluidStack();
            }
            case 30: {
                boolean empty = buffer.readBoolean();
                if (empty) {
                    return new FluidTank(buffer.readInt());
                }
                FluidTank tank = new FluidTank(buffer.readInt());
                tank.setFluid(buffer.readFluidStack());
                return tank;
            }
            case 31: {
                return buffer.readUUID();
            }
            case 32: {
                String id = buffer.readString();
                return new GameProfile(buffer.readUUID(), id);
            }
            case 33: {
                return buffer.readString();
            }
            case 34: {
                return new ResourceLocation(buffer.readString());
            }
            case 35: {
                return DirectionList.ofNumber(buffer.readByte());
            }
        }
        return null;
    }
}

