/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtAccounter
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ColumnPos
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.network;

import com.mojang.authlib.GameProfile;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.network.IGrowingBuffer;
import ic2.api.network.INetworkCustomEncoder;
import ic2.api.recipe.IElectrolyzerRecipeManager;
import ic2.core.IC2;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.invslot.InvSlot;
import ic2.core.fluid.FluidHandler;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.network.GrowingBuffer;
import ic2.core.util.StackUtil;
import ic2.core.util.Tuple;
import ic2.core.util.Util;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;

public final class DataEncoder {
    private static final Map<Class<?>, EncodedType> classToTypeCache = Collections.synchronizedMap(new IdentityHashMap());
    private static final Map<Class<?>, INetworkCustomEncoder> classToAddonType = Collections.synchronizedMap(new IdentityHashMap());

    public static void encode(GrowingBuffer growingBuffer, Object object) throws IOException {
        try {
            DataEncoder.encode(growingBuffer, object, true);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            IC2.sideProxy.displayError(illegalArgumentException, "An unknown data type was attempted to be encoded for sending through\nmultiplayer.\nThis could happen due to a bug.", new Object[0]);
        }
    }

    public static void encode(IGrowingBuffer iGrowingBuffer, Object object, boolean bl) throws IOException {
        EncodedType encodedType = DataEncoder.typeFromObject(object);
        if (bl) {
            iGrowingBuffer.writeByte(DataEncoder.idFromType(encodedType));
        }
        switch (encodedType) {
            case Addon: 
            case UnSafeAddon: {
                assert (object != null);
                INetworkCustomEncoder iNetworkCustomEncoder = classToAddonType.get(object.getClass());
                if (iNetworkCustomEncoder == null) {
                    throw new IllegalStateException("Cannot encode an object without an encoder! Type was " + object.getClass());
                }
                iGrowingBuffer.writeString(object.getClass().getName());
                iNetworkCustomEncoder.encode(iGrowingBuffer, object);
                break;
            }
            case Array: {
                Object object2;
                int n;
                Class<?> clazz = object.getClass().getComponentType();
                int n2 = Array.getLength(object);
                if (clazz == Object.class && n2 > 0) {
                    boolean bl2 = false;
                    Class<Object> clazz2 = null;
                    block40: for (n = 0; n < n2; ++n) {
                        object2 = Array.get(object, n);
                        if (clazz2 == null) {
                            if (object2 instanceof Enum) {
                                clazz2 = ((Enum)object2).getDeclaringClass();
                                bl2 = true;
                                continue;
                            }
                            if (object2 == null) continue;
                            clazz2 = object2.getClass();
                            assert (clazz2 != Object.class);
                            continue;
                        }
                        if (object2 != null) {
                            Class<?> clazz3 = object2.getClass();
                            if (clazz3 != clazz2 && !clazz2.isAssignableFrom(clazz3)) {
                                if (bl2 || object2 instanceof Enum) {
                                    throw new IllegalArgumentException("Array of mixed enum entries");
                                }
                                while ((clazz2 = clazz2.getSuperclass()) != Object.class) {
                                    if (!clazz2.isAssignableFrom(clazz3)) continue;
                                    continue block40;
                                }
                                ++n;
                                while (n < n2) {
                                    if (Array.get(object, n) instanceof Enum) {
                                        throw new IllegalArgumentException("Array of mixed enum entries");
                                    }
                                    ++n;
                                }
                                clazz2 = Object.class;
                                break;
                            }
                            assert (bl2 == object2 instanceof Enum);
                            continue;
                        }
                        if (!bl2) continue;
                        throw new IllegalArgumentException("Enum array with null entry");
                    }
                    clazz = clazz2;
                }
                EncodedType encodedType2 = DataEncoder.typeFromClass(clazz);
                iGrowingBuffer.writeByte(DataEncoder.idFromType(encodedType2));
                iGrowingBuffer.writeBoolean(clazz.isPrimitive());
                if (encodedType2 == EncodedType.Addon || encodedType2 == EncodedType.UnSafeAddon || encodedType2 == EncodedType.Enum) {
                    iGrowingBuffer.writeString(clazz.getName());
                }
                iGrowingBuffer.writeVarInt(n2);
                boolean bl3 = false;
                for (n = 0; n < n2; ++n) {
                    object2 = Array.get(object, n);
                    if (object2 != null && DataEncoder.typeFromClass(object2.getClass()) == encodedType2) continue;
                    bl3 = true;
                    break;
                }
                iGrowingBuffer.writeBoolean(bl3);
                for (n = 0; n < n2; ++n) {
                    DataEncoder.encode(iGrowingBuffer, Array.get(object, n), bl3);
                }
                break;
            }
            case Block: {
                DataEncoder.encode(iGrowingBuffer, Util.getName((Block)object), false);
                break;
            }
            case BlockPos: {
                BlockPos blockPos = (BlockPos)object;
                iGrowingBuffer.writeInt(blockPos.m_123341_());
                iGrowingBuffer.writeInt(blockPos.m_123342_());
                iGrowingBuffer.writeInt(blockPos.m_123343_());
                break;
            }
            case Boolean: {
                iGrowingBuffer.writeBoolean((Boolean)object);
                break;
            }
            case Byte: {
                iGrowingBuffer.writeByte(((Byte)object).byteValue());
                break;
            }
            case Character: {
                iGrowingBuffer.writeChar(((Character)object).charValue());
                break;
            }
            case ChunkPos: {
                ColumnPos columnPos = (ColumnPos)object;
                iGrowingBuffer.writeInt(columnPos.f_140723_());
                iGrowingBuffer.writeInt(columnPos.f_140724_());
                break;
            }
            case Collection: {
                DataEncoder.encode(iGrowingBuffer, ((Collection)object).toArray(), false);
                break;
            }
            case Component: {
                CompoundTag compoundTag = ((TileEntityComponent)object).writeToNbt();
                DataEncoder.encode(iGrowingBuffer, compoundTag == null ? new CompoundTag() : compoundTag, false);
                break;
            }
            case CropCard: {
                CropCard cropCard = (CropCard)object;
                iGrowingBuffer.writeString(cropCard.getOwner());
                iGrowingBuffer.writeString(cropCard.getId());
                break;
            }
            case Double: {
                iGrowingBuffer.writeDouble((Double)object);
                break;
            }
            case ElectrolyzerRecipe: {
                IElectrolyzerRecipeManager.ElectrolyzerRecipe electrolyzerRecipe = (IElectrolyzerRecipeManager.ElectrolyzerRecipe)object;
                iGrowingBuffer.writeInt(electrolyzerRecipe.inputAmount);
                iGrowingBuffer.writeInt(electrolyzerRecipe.EUaTick);
                iGrowingBuffer.writeInt(electrolyzerRecipe.ticksNeeded);
                IElectrolyzerRecipeManager.ElectrolyzerOutput[] electrolyzerOutputArray = electrolyzerRecipe.outputs;
                iGrowingBuffer.writeByte(electrolyzerOutputArray.length);
                for (IElectrolyzerRecipeManager.ElectrolyzerOutput electrolyzerOutput : electrolyzerOutputArray) {
                    DataEncoder.encode(iGrowingBuffer, electrolyzerOutput.fluid, false);
                    iGrowingBuffer.writeInt(electrolyzerOutput.fluidAmount);
                    iGrowingBuffer.writeByte(electrolyzerOutput.tankDirection.m_122411_());
                }
                break;
            }
            case Enchantment: {
                DataEncoder.encode(iGrowingBuffer, Registry.f_122825_.m_7981_((Object)((Enchantment)object)), false);
                break;
            }
            case Enum: {
                iGrowingBuffer.writeVarInt(((Enum)object).ordinal());
                break;
            }
            case Float: {
                iGrowingBuffer.writeFloat(((Float)object).floatValue());
                break;
            }
            case Fluid: {
                DataEncoder.encode(iGrowingBuffer, Registry.f_122822_.m_7981_((Object)((Fluid)object)), false);
                break;
            }
            case FluidStack: {
                Ic2FluidStack ic2FluidStack = (Ic2FluidStack)object;
                DataEncoder.encode(iGrowingBuffer, ic2FluidStack.getFluid(), false);
                iGrowingBuffer.writeInt(ic2FluidStack.getAmountMb());
                DataEncoder.encode(iGrowingBuffer, FluidHandler.getFluidStackNbt(ic2FluidStack), true);
                break;
            }
            case FluidTank: {
                Ic2FluidTank ic2FluidTank = (Ic2FluidTank)object;
                DataEncoder.encode(iGrowingBuffer, ic2FluidTank.getFluidStack(), true);
                iGrowingBuffer.writeInt(ic2FluidTank.getCapacity());
                break;
            }
            case GameProfile: {
                GameProfile gameProfile = (GameProfile)object;
                DataEncoder.encode(iGrowingBuffer, gameProfile.getId(), true);
                iGrowingBuffer.writeString(gameProfile.getName());
                break;
            }
            case Integer: {
                iGrowingBuffer.writeInt((Integer)object);
                break;
            }
            case InvSlot: {
                InvSlot invSlot = (InvSlot)object;
                ItemStack[] itemStackArray = new ItemStack[invSlot.size()];
                for (int i = 0; i < invSlot.size(); ++i) {
                    itemStackArray[i] = invSlot.get(i);
                }
                DataEncoder.encode(iGrowingBuffer, itemStackArray, false);
                break;
            }
            case Item: {
                DataEncoder.encode(iGrowingBuffer, Util.getName((Item)object), false);
                break;
            }
            case ItemStack: {
                ItemStack itemStack = (ItemStack)object;
                if (StackUtil.isEmpty(itemStack)) {
                    iGrowingBuffer.writeByte(0);
                    break;
                }
                iGrowingBuffer.writeByte(StackUtil.getSize(itemStack));
                DataEncoder.encode(iGrowingBuffer, itemStack.m_41720_(), false);
                DataEncoder.encode(iGrowingBuffer, itemStack.m_41783_(), true);
                break;
            }
            case Long: {
                iGrowingBuffer.writeLong((Long)object);
                break;
            }
            case NBTTagCompound: {
                NbtIo.m_128941_((CompoundTag)((CompoundTag)object), (DataOutput)iGrowingBuffer);
                break;
            }
            case Null: {
                if (bl) break;
                throw new IllegalArgumentException("o has to be non-null without types");
            }
            case Object: {
                throw new IllegalArgumentException("unhandled class: " + object.getClass());
            }
            case Potion: {
                DataEncoder.encode(iGrowingBuffer, Registry.f_122823_.m_7981_((Object)((MobEffect)object)), false);
                break;
            }
            case ResourceLocation: {
                ResourceLocation resourceLocation = (ResourceLocation)object;
                iGrowingBuffer.writeString(resourceLocation.m_135827_());
                iGrowingBuffer.writeString(resourceLocation.m_135815_());
                break;
            }
            case Short: {
                iGrowingBuffer.writeShort(((Short)object).shortValue());
                break;
            }
            case String: {
                iGrowingBuffer.writeString((String)object);
                break;
            }
            case TileEntity: {
                BlockEntity blockEntity = (BlockEntity)object;
                DataEncoder.encode(iGrowingBuffer, blockEntity.m_58904_(), false);
                DataEncoder.encode(iGrowingBuffer, blockEntity.m_58899_(), false);
                break;
            }
            case TupleT2: {
                Tuple.T2 t2 = (Tuple.T2)object;
                DataEncoder.encode(iGrowingBuffer, t2.a, true);
                DataEncoder.encode(iGrowingBuffer, t2.b, true);
                break;
            }
            case TupleT3: {
                Tuple.T3 t3 = (Tuple.T3)object;
                DataEncoder.encode(iGrowingBuffer, t3.a, true);
                DataEncoder.encode(iGrowingBuffer, t3.b, true);
                DataEncoder.encode(iGrowingBuffer, t3.c, true);
                break;
            }
            case UUID: {
                UUID uUID = (UUID)object;
                iGrowingBuffer.writeLong(uUID.getMostSignificantBits());
                iGrowingBuffer.writeLong(uUID.getLeastSignificantBits());
                break;
            }
            case Vec3: {
                Vec3 vec3 = (Vec3)object;
                iGrowingBuffer.writeDouble(vec3.f_82479_);
                iGrowingBuffer.writeDouble(vec3.f_82480_);
                iGrowingBuffer.writeDouble(vec3.f_82481_);
                break;
            }
            case World: {
                DataEncoder.encode(iGrowingBuffer, Util.getDimId((Level)object), false);
                break;
            }
            default: {
                throw new IllegalArgumentException("unhandled type: " + encodedType);
            }
        }
    }

    public static Object decode(IGrowingBuffer iGrowingBuffer) throws IOException {
        try {
            return DataEncoder.decode(iGrowingBuffer, DataEncoder.typeFromId(iGrowingBuffer.readUnsignedByte()));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            String string = "An unknown data type was received over multiplayer to be decoded.\nThis could happen due to corrupted data or a bug.";
            IC2.sideProxy.displayError(illegalArgumentException, string, new Object[0]);
            return null;
        }
    }

    public static <T> T decode(IGrowingBuffer iGrowingBuffer, Class<T> clazz) throws IOException {
        EncodedType encodedType = DataEncoder.typeFromClass(clazz);
        if (encodedType.threadSafe) {
            return (T)DataEncoder.decode(iGrowingBuffer, encodedType);
        }
        throw new IllegalArgumentException("requesting decode for non thread safe type");
    }

    public static <T extends Enum<T>> T decodeEnum(IGrowingBuffer iGrowingBuffer, Class<T> clazz) throws IOException {
        int n = (Integer)DataEncoder.decode(iGrowingBuffer, EncodedType.Enum);
        Enum[] enumArray = (Enum[])clazz.getEnumConstants();
        return (T)(n >= 0 && n < enumArray.length ? enumArray[n] : null);
    }

    public static Object decodeDeferred(GrowingBuffer growingBuffer, Class<?> clazz) throws IOException {
        EncodedType encodedType = DataEncoder.typeFromClass(clazz);
        return DataEncoder.decode((IGrowingBuffer)growingBuffer, encodedType);
    }

    public static Object decode(final IGrowingBuffer iGrowingBuffer, EncodedType encodedType) throws IOException {
        switch (encodedType) {
            case Addon: 
            case UnSafeAddon: {
                String string = iGrowingBuffer.readString();
                final INetworkCustomEncoder iNetworkCustomEncoder = classToAddonType.get(DataEncoder.getClass(string));
                if (iNetworkCustomEncoder == null) {
                    throw new IllegalStateException("Cannot decode an object without a decoder! Type was " + string);
                }
                if (iNetworkCustomEncoder.isThreadSafe()) {
                    return iNetworkCustomEncoder.decode(iGrowingBuffer);
                }
                return new IResolvableValue<Object>(){

                    @Override
                    public Object get(MinecraftServer minecraftServer) {
                        try {
                            return iNetworkCustomEncoder.decode(iGrowingBuffer);
                        }
                        catch (IOException iOException) {
                            throw new RuntimeException("Unexpected error", iOException);
                        }
                    }
                };
            }
            case Array: {
                Object object;
                Class<?> clazz;
                EncodedType encodedType2 = DataEncoder.typeFromId(iGrowingBuffer.readUnsignedByte());
                boolean bl = iGrowingBuffer.readBoolean();
                boolean bl2 = encodedType2 == EncodedType.Enum;
                Class<?> clazz2 = clazz = bl ? DataEncoder.unbox(encodedType2.cls) : encodedType2.cls;
                if (clazz == null || bl2) {
                    assert (encodedType2 == EncodedType.Addon || encodedType2 == EncodedType.UnSafeAddon || bl2);
                    clazz = DataEncoder.getClass(iGrowingBuffer.readString());
                }
                final Class<?> clazz3 = clazz;
                final int n = iGrowingBuffer.readVarInt();
                boolean bl3 = iGrowingBuffer.readBoolean();
                boolean bl4 = !encodedType2.threadSafe;
                Object object2 = !bl4 ? Array.newInstance(clazz3, n) : new Object[n];
                if (!bl3) {
                    if (bl2) {
                        object = clazz3.getEnumConstants();
                        assert (object != null);
                        for (int i = 0; i < n; ++i) {
                            Array.set(object2, i, object[(Integer)DataEncoder.decode(iGrowingBuffer, encodedType2)]);
                        }
                    } else {
                        for (int i = 0; i < n; ++i) {
                            Array.set(object2, i, DataEncoder.decode(iGrowingBuffer, encodedType2));
                        }
                    }
                } else {
                    for (int i = 0; i < n; ++i) {
                        EncodedType encodedType3 = DataEncoder.typeFromId(iGrowingBuffer.readUnsignedByte());
                        if (!encodedType3.threadSafe && !bl4) {
                            bl4 = true;
                            if (clazz3 != Object.class) {
                                Object[] objectArray = new Object[n];
                                System.arraycopy(object2, 0, objectArray, 0, i);
                                object2 = objectArray;
                            }
                        }
                        Array.set(object2, i, DataEncoder.decode(iGrowingBuffer, encodedType3));
                    }
                }
                if (!bl4) {
                    return object2;
                }
                object = object2;
                return new IResolvableValue<Object>(){

                    @Override
                    public Object get(MinecraftServer minecraftServer) {
                        Object object2 = Array.newInstance(clazz3, n);
                        for (int i = 0; i < n; ++i) {
                            Array.set(object2, i, DataEncoder.getValue(Array.get(object, i), minecraftServer));
                        }
                        return object2;
                    }
                };
            }
            case Block: {
                return Util.getBlock((ResourceLocation)DataEncoder.decode(iGrowingBuffer, EncodedType.ResourceLocation));
            }
            case BlockPos: {
                return new BlockPos(iGrowingBuffer.readInt(), iGrowingBuffer.readInt(), iGrowingBuffer.readInt());
            }
            case Boolean: {
                return iGrowingBuffer.readBoolean();
            }
            case Byte: {
                return iGrowingBuffer.readByte();
            }
            case Character: {
                return Character.valueOf(iGrowingBuffer.readChar());
            }
            case ChunkPos: {
                return new ColumnPos(iGrowingBuffer.readInt(), iGrowingBuffer.readInt());
            }
            case Collection: {
                final Object object = DataEncoder.decode(iGrowingBuffer, EncodedType.Array);
                if (object instanceof IResolvableValue) {
                    return new IResolvableValue<List<Object>>(){

                        @Override
                        public List<Object> get(MinecraftServer minecraftServer) {
                            return Arrays.asList((Object[])((IResolvableValue)object).get(minecraftServer));
                        }
                    };
                }
                return Arrays.asList((Object[])object);
            }
            case Component: {
                return DataEncoder.decode(iGrowingBuffer, EncodedType.NBTTagCompound);
            }
            case CropCard: {
                return Crops.instance.getCropCard(iGrowingBuffer.readString(), iGrowingBuffer.readString());
            }
            case Double: {
                return iGrowingBuffer.readDouble();
            }
            case ElectrolyzerRecipe: {
                int n = iGrowingBuffer.readInt();
                int n2 = iGrowingBuffer.readInt();
                int n3 = iGrowingBuffer.readInt();
                byte by = iGrowingBuffer.readByte();
                IElectrolyzerRecipeManager.ElectrolyzerOutput[] electrolyzerOutputArray = new IElectrolyzerRecipeManager.ElectrolyzerOutput[by];
                for (byte by2 = 0; by2 < by; by2 = (byte)(by2 + 1)) {
                    electrolyzerOutputArray[by2] = new IElectrolyzerRecipeManager.ElectrolyzerOutput((Fluid)DataEncoder.decode(iGrowingBuffer, EncodedType.Fluid), iGrowingBuffer.readInt(), Direction.m_122376_((int)iGrowingBuffer.readByte()));
                }
                return new IElectrolyzerRecipeManager.ElectrolyzerRecipe(n, n2, n3, electrolyzerOutputArray);
            }
            case Enchantment: {
                return Registry.f_122825_.m_7745_((ResourceLocation)DataEncoder.decode(iGrowingBuffer, EncodedType.ResourceLocation));
            }
            case Enum: {
                return iGrowingBuffer.readVarInt();
            }
            case Float: {
                return Float.valueOf(iGrowingBuffer.readFloat());
            }
            case Fluid: {
                return Registry.f_122822_.m_7745_((ResourceLocation)DataEncoder.decode(iGrowingBuffer, EncodedType.ResourceLocation));
            }
            case FluidStack: {
                Fluid fluid = (Fluid)DataEncoder.decode(iGrowingBuffer, EncodedType.Fluid);
                int n = iGrowingBuffer.readInt();
                CompoundTag compoundTag = (CompoundTag)DataEncoder.decode(iGrowingBuffer);
                return FluidHandler.createFluidStackMb(fluid, n, compoundTag);
            }
            case FluidTank: {
                Ic2FluidStack ic2FluidStack = (Ic2FluidStack)DataEncoder.decode(iGrowingBuffer);
                Ic2FluidTank ic2FluidTank = new Ic2FluidTank(iGrowingBuffer.readInt());
                ic2FluidTank.setFluidStack(ic2FluidStack);
                return ic2FluidTank;
            }
            case GameProfile: {
                return new GameProfile((UUID)DataEncoder.decode(iGrowingBuffer), iGrowingBuffer.readString());
            }
            case Integer: {
                return iGrowingBuffer.readInt();
            }
            case InvSlot: {
                ItemStack[] itemStackArray = (ItemStack[])DataEncoder.decode(iGrowingBuffer, EncodedType.Array);
                InvSlot invSlot = new InvSlot(itemStackArray.length);
                for (int i = 0; i < itemStackArray.length; ++i) {
                    invSlot.put(i, itemStackArray[i]);
                }
                return invSlot;
            }
            case Item: {
                return Util.getItem((ResourceLocation)DataEncoder.decode(iGrowingBuffer, EncodedType.ResourceLocation));
            }
            case ItemStack: {
                byte by = iGrowingBuffer.readByte();
                if (by == 0) {
                    return StackUtil.emptyStack;
                }
                Item item = DataEncoder.decode(iGrowingBuffer, Item.class);
                CompoundTag compoundTag = (CompoundTag)DataEncoder.decode(iGrowingBuffer);
                ItemStack itemStack = new ItemStack((ItemLike)item, (int)by);
                itemStack.m_41751_(compoundTag);
                return itemStack;
            }
            case Long: {
                return iGrowingBuffer.readLong();
            }
            case NBTTagCompound: {
                return NbtIo.m_128934_((DataInput)iGrowingBuffer, (NbtAccounter)NbtAccounter.f_128917_);
            }
            case Null: {
                return null;
            }
            case Object: {
                return new Object();
            }
            case Potion: {
                return Registry.f_122823_.m_7745_((ResourceLocation)DataEncoder.decode(iGrowingBuffer, EncodedType.ResourceLocation));
            }
            case ResourceLocation: {
                return new ResourceLocation(iGrowingBuffer.readString(), iGrowingBuffer.readString());
            }
            case Short: {
                return iGrowingBuffer.readShort();
            }
            case String: {
                return iGrowingBuffer.readString();
            }
            case TileEntity: {
                final IResolvableValue iResolvableValue = (IResolvableValue)DataEncoder.decode(iGrowingBuffer, EncodedType.World);
                final BlockPos blockPos = (BlockPos)DataEncoder.decode(iGrowingBuffer, EncodedType.BlockPos);
                return new IResolvableValue<BlockEntity>(){

                    @Override
                    public BlockEntity get(MinecraftServer minecraftServer) {
                        Level level = (Level)iResolvableValue.get(minecraftServer);
                        if (level == null) {
                            return null;
                        }
                        return level.m_7702_(blockPos);
                    }
                };
            }
            case TupleT2: {
                return new Tuple.T2<Object, Object>(DataEncoder.decode(iGrowingBuffer), DataEncoder.decode(iGrowingBuffer));
            }
            case TupleT3: {
                return new Tuple.T3<Object, Object, Object>(DataEncoder.decode(iGrowingBuffer), DataEncoder.decode(iGrowingBuffer), DataEncoder.decode(iGrowingBuffer));
            }
            case UUID: {
                return new UUID(iGrowingBuffer.readLong(), iGrowingBuffer.readLong());
            }
            case Vec3: {
                return new Vec3(iGrowingBuffer.readDouble(), iGrowingBuffer.readDouble(), iGrowingBuffer.readDouble());
            }
            case World: {
                final ResourceLocation resourceLocation = (ResourceLocation)DataEncoder.decode(iGrowingBuffer, EncodedType.ResourceLocation);
                return new IResolvableValue<Level>(){

                    @Override
                    public Level get(MinecraftServer minecraftServer) {
                        return IC2.sideProxy.getWorld(minecraftServer, resourceLocation);
                    }
                };
            }
        }
        throw new IllegalArgumentException("unhandled type: " + encodedType);
    }

    public static <T> T getValue(Object object, MinecraftServer minecraftServer) {
        if (object instanceof IResolvableValue) {
            return ((IResolvableValue)object).get(minecraftServer);
        }
        return (T)object;
    }

    public static <T> boolean copyValue(T t, T t2) {
        if (t == null || t2 == null) {
            return false;
        }
        if (t2 instanceof ItemStack) {
            ItemStack itemStack = (ItemStack)t;
            ItemStack itemStack2 = (ItemStack)t2;
            if (itemStack.m_41720_() == itemStack2.m_41720_()) {
                itemStack2.m_41764_(itemStack.m_41613_());
                itemStack2.m_41751_(itemStack.m_41783_());
                return true;
            }
            return false;
        }
        if (t2 instanceof Ic2FluidTank) {
            Ic2FluidTank ic2FluidTank = (Ic2FluidTank)t;
            Ic2FluidTank ic2FluidTank2 = (Ic2FluidTank)t2;
            ic2FluidTank2.setFluidStack(ic2FluidTank.getFluidStack());
            ic2FluidTank2.setCapacity(ic2FluidTank.getCapacity());
        } else if (t2 instanceof InvSlot) {
            InvSlot invSlot = (InvSlot)t;
            InvSlot invSlot2 = (InvSlot)t2;
            if (invSlot.size() != invSlot2.size()) {
                throw new RuntimeException("Can't sync InvSlots with mismatched sizes.");
            }
            for (int i = 0; i < invSlot.size(); ++i) {
                if (DataEncoder.copyValue(invSlot.get(i), invSlot2.get(i))) continue;
                invSlot2.put(i, invSlot.get(i));
            }
        } else if (t2 instanceof TileEntityComponent) {
            CompoundTag compoundTag = (CompoundTag)t;
            ((TileEntityComponent)t2).readFromNbt(compoundTag);
        } else if (t2 instanceof Collection) {
            Collection collection = (Collection)t;
            Collection collection2 = (Collection)t2;
            collection2.clear();
            collection2.addAll(collection);
        } else {
            return false;
        }
        return true;
    }

    private static Class<?> box(Class<?> clazz) {
        if (clazz == Byte.TYPE) {
            return Byte.class;
        }
        if (clazz == Short.TYPE) {
            return Short.class;
        }
        if (clazz == Integer.TYPE) {
            return Integer.class;
        }
        if (clazz == Long.TYPE) {
            return Long.class;
        }
        if (clazz == Float.TYPE) {
            return Float.class;
        }
        if (clazz == Double.TYPE) {
            return Double.class;
        }
        if (clazz == Boolean.TYPE) {
            return Boolean.class;
        }
        if (clazz == Character.TYPE) {
            return Character.class;
        }
        return clazz;
    }

    private static Class<?> unbox(Class<?> clazz) {
        if (clazz == Byte.class) {
            return Byte.TYPE;
        }
        if (clazz == Short.class) {
            return Short.TYPE;
        }
        if (clazz == Integer.class) {
            return Integer.TYPE;
        }
        if (clazz == Long.class) {
            return Long.TYPE;
        }
        if (clazz == Float.class) {
            return Float.TYPE;
        }
        if (clazz == Double.class) {
            return Double.TYPE;
        }
        if (clazz == Boolean.class) {
            return Boolean.TYPE;
        }
        if (clazz == Character.class) {
            return Character.TYPE;
        }
        return clazz;
    }

    private static Class<?> getClass(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException("Missing type from the class path expected by network: " + string, classNotFoundException);
        }
    }

    private static int idFromType(EncodedType encodedType) {
        return encodedType.ordinal();
    }

    private static EncodedType typeFromId(int n) {
        if (n < 0 || n >= EncodedType.types.length) {
            throw new IllegalArgumentException("invalid type id: " + n);
        }
        return EncodedType.types[n];
    }

    private static EncodedType typeFromObject(Object object) {
        if (object == null) {
            return EncodedType.Null;
        }
        return DataEncoder.typeFromClass(object.getClass());
    }

    private static EncodedType typeFromClass(Class<?> clazz) {
        EncodedType encodedType;
        if (clazz == null) {
            return EncodedType.Null;
        }
        if (clazz.isArray()) {
            return EncodedType.Array;
        }
        if (clazz.isPrimitive()) {
            clazz = DataEncoder.box(clazz);
        }
        if ((encodedType = EncodedType.classToTypeMap.get(clazz)) != null) {
            return encodedType;
        }
        encodedType = classToTypeCache.get(clazz);
        if (encodedType != null) {
            return encodedType;
        }
        INetworkCustomEncoder iNetworkCustomEncoder = classToAddonType.get(clazz);
        if (iNetworkCustomEncoder != null) {
            encodedType = iNetworkCustomEncoder.isThreadSafe() ? EncodedType.Addon : EncodedType.UnSafeAddon;
            classToTypeCache.put(clazz, encodedType);
            return encodedType;
        }
        for (EncodedType encodedType2 : EncodedType.types) {
            if (encodedType2.cls == null || !encodedType2.cls.isAssignableFrom(clazz)) continue;
            classToTypeCache.put(clazz, encodedType2);
            return encodedType2;
        }
        throw new IllegalStateException("unmatched " + clazz);
    }

    public static void addNetworkEncoder(Class<?> clazz, INetworkCustomEncoder iNetworkCustomEncoder) {
        assert (clazz != null && iNetworkCustomEncoder != null);
        INetworkCustomEncoder iNetworkCustomEncoder2 = classToAddonType.put(clazz, iNetworkCustomEncoder);
        if (iNetworkCustomEncoder2 != null) {
            throw new IllegalStateException("Duplicate mapping for class! " + iNetworkCustomEncoder2.getClass().getName() + " and " + iNetworkCustomEncoder.getClass().getName() + " both map for " + clazz.getName() + ".");
        }
    }

    public static enum EncodedType {
        Null(null),
        Array(null),
        Byte(Byte.class),
        Short(Short.class),
        Integer(Integer.class),
        Long(Long.class),
        Float(Float.class),
        Double(Double.class),
        Boolean(Boolean.class),
        Character(Character.class),
        String(String.class),
        Enum(Enum.class),
        UUID(UUID.class),
        Block(Block.class),
        Item(Item.class),
        TileEntity(BlockEntity.class, false),
        ItemStack(ItemStack.class),
        World(Level.class, false),
        NBTTagCompound(CompoundTag.class),
        ResourceLocation(ResourceLocation.class),
        GameProfile(GameProfile.class),
        Potion(MobEffect.class),
        Enchantment(Enchantment.class),
        BlockPos(BlockPos.class),
        ChunkPos(ColumnPos.class),
        Vec3(Vec3.class),
        Fluid(Fluid.class),
        FluidStack(Ic2FluidStack.class),
        FluidTank(Ic2FluidTank.class),
        InvSlot(InvSlot.class),
        Component(TileEntityComponent.class, false),
        CropCard(CropCard.class),
        ElectrolyzerRecipe(IElectrolyzerRecipeManager.ElectrolyzerRecipe.class),
        TupleT2(Tuple.T2.class),
        TupleT3(Tuple.T3.class),
        Addon(null),
        UnSafeAddon(null, false),
        Collection(Collection.class),
        Object(Object.class);

        final Class<?> cls;
        final boolean threadSafe;
        static final EncodedType[] types;
        static final Map<Class<?>, EncodedType> classToTypeMap;

        private EncodedType(Class<?> clazz) {
            this(clazz, true);
        }

        private EncodedType(Class<?> clazz, boolean bl) {
            this.cls = clazz;
            this.threadSafe = bl;
        }

        static {
            types = EncodedType.values();
            classToTypeMap = new IdentityHashMap(types.length - 2);
            for (EncodedType encodedType : types) {
                if (encodedType.cls == null) continue;
                classToTypeMap.put(encodedType.cls, encodedType);
            }
            if (types.length > 255) {
                throw new RuntimeException("too many types");
            }
        }
    }

    private static interface IResolvableValue<T> {
        public T get(MinecraftServer var1);
    }
}

