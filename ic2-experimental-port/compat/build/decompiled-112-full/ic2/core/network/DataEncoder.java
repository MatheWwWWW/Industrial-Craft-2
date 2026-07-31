/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.block.Block
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTSizeTracker
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.potion.Potion
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.ChunkPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
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
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public final class DataEncoder {
    private static final Map<Class<?>, EncodedType> classToTypeCache = Collections.synchronizedMap(new IdentityHashMap());
    private static final Map<Class<?>, INetworkCustomEncoder> classToAddonType = Collections.synchronizedMap(new IdentityHashMap());

    public static void encode(GrowingBuffer os, Object o) throws IOException {
        try {
            DataEncoder.encode(os, o, true);
        }
        catch (IllegalArgumentException e) {
            IC2.platform.displayError(e, "An unknown data type was attempted to be encoded for sending through\nmultiplayer.\nThis could happen due to a bug.", new Object[0]);
        }
    }

    public static void encode(IGrowingBuffer os, Object o, boolean withType) throws IOException {
        EncodedType type = DataEncoder.typeFromObject(o);
        if (withType) {
            os.writeByte(DataEncoder.idFromType(type));
        }
        switch (type) {
            case Addon: 
            case UnSafeAddon: {
                assert (o != null);
                INetworkCustomEncoder ince = classToAddonType.get(o.getClass());
                if (ince == null) {
                    throw new IllegalStateException("Cannot encode an object without an encoder! Type was " + o.getClass());
                }
                os.writeString(o.getClass().getName());
                ince.encode(os, o);
                break;
            }
            case Array: {
                Object value;
                int i;
                Class<?> componentClass = o.getClass().getComponentType();
                int len = Array.getLength(o);
                if (componentClass == Object.class && len > 0) {
                    boolean isEnum = false;
                    Class<Object> target = null;
                    block40: for (i = 0; i < len; ++i) {
                        value = Array.get(o, i);
                        if (target == null) {
                            if (value instanceof Enum) {
                                target = ((Enum)value).getDeclaringClass();
                                isEnum = true;
                                continue;
                            }
                            if (value == null) continue;
                            target = value.getClass();
                            assert (target != Object.class);
                            continue;
                        }
                        if (value != null) {
                            Class<?> valueClass = value.getClass();
                            if (valueClass != target && !target.isAssignableFrom(valueClass)) {
                                if (isEnum || value instanceof Enum) {
                                    throw new IllegalArgumentException("Array of mixed enum entries");
                                }
                                while ((target = target.getSuperclass()) != Object.class) {
                                    if (!target.isAssignableFrom(valueClass)) continue;
                                    continue block40;
                                }
                                ++i;
                                while (i < len) {
                                    if (Array.get(o, i) instanceof Enum) {
                                        throw new IllegalArgumentException("Array of mixed enum entries");
                                    }
                                    ++i;
                                }
                                target = Object.class;
                                break;
                            }
                            assert (isEnum == value instanceof Enum);
                            continue;
                        }
                        if (!isEnum) continue;
                        throw new IllegalArgumentException("Enum array with null entry");
                    }
                    componentClass = target;
                }
                EncodedType componentType = DataEncoder.typeFromClass(componentClass);
                os.writeByte(DataEncoder.idFromType(componentType));
                os.writeBoolean(componentClass.isPrimitive());
                if (componentType == EncodedType.Addon || componentType == EncodedType.UnSafeAddon || componentType == EncodedType.Enum) {
                    os.writeString(componentClass.getName());
                }
                os.writeVarInt(len);
                boolean anyTypeMismatch = false;
                for (i = 0; i < len; ++i) {
                    value = Array.get(o, i);
                    if (value != null && DataEncoder.typeFromClass(value.getClass()) == componentType) continue;
                    anyTypeMismatch = true;
                    break;
                }
                os.writeBoolean(anyTypeMismatch);
                for (i = 0; i < len; ++i) {
                    DataEncoder.encode(os, Array.get(o, i), anyTypeMismatch);
                }
                break;
            }
            case Block: {
                DataEncoder.encode(os, Util.getName((Block)o), false);
                break;
            }
            case BlockPos: {
                BlockPos pos = (BlockPos)o;
                os.writeInt(pos.func_177958_n());
                os.writeInt(pos.func_177956_o());
                os.writeInt(pos.func_177952_p());
                break;
            }
            case Boolean: {
                os.writeBoolean((Boolean)o);
                break;
            }
            case Byte: {
                os.writeByte(((Byte)o).byteValue());
                break;
            }
            case Character: {
                os.writeChar(((Character)o).charValue());
                break;
            }
            case ChunkPos: {
                ChunkPos pos = (ChunkPos)o;
                os.writeInt(pos.field_77276_a);
                os.writeInt(pos.field_77275_b);
                break;
            }
            case Collection: {
                DataEncoder.encode(os, ((Collection)o).toArray(), false);
                break;
            }
            case Component: {
                NBTTagCompound nbt = ((TileEntityComponent)o).writeToNbt();
                DataEncoder.encode(os, nbt == null ? new NBTTagCompound() : nbt, false);
                break;
            }
            case CropCard: {
                CropCard cropCard = (CropCard)o;
                os.writeString(cropCard.getOwner());
                os.writeString(cropCard.getId());
                break;
            }
            case Double: {
                os.writeDouble((Double)o);
                break;
            }
            case ElectrolyzerRecipe: {
                IElectrolyzerRecipeManager.ElectrolyzerRecipe recipe = (IElectrolyzerRecipeManager.ElectrolyzerRecipe)o;
                os.writeInt(recipe.inputAmount);
                os.writeInt(recipe.EUaTick);
                os.writeInt(recipe.ticksNeeded);
                IElectrolyzerRecipeManager.ElectrolyzerOutput[] outputs = recipe.outputs;
                os.writeByte(outputs.length);
                for (IElectrolyzerRecipeManager.ElectrolyzerOutput output : outputs) {
                    os.writeString(output.fluidName);
                    os.writeInt(output.fluidAmount);
                    os.writeByte(output.tankDirection.func_176745_a());
                }
                break;
            }
            case Enchantment: {
                DataEncoder.encode(os, Enchantment.field_185264_b.func_177774_c((Object)((Enchantment)o)), false);
                break;
            }
            case Enum: {
                os.writeVarInt(((Enum)o).ordinal());
                break;
            }
            case Float: {
                os.writeFloat(((Float)o).floatValue());
                break;
            }
            case Fluid: {
                os.writeString(((Fluid)o).getName());
                break;
            }
            case FluidStack: {
                FluidStack fs = (FluidStack)o;
                DataEncoder.encode(os, fs.getFluid(), false);
                os.writeInt(fs.amount);
                DataEncoder.encode(os, fs.tag, true);
                break;
            }
            case FluidTank: {
                FluidTank tank = (FluidTank)o;
                DataEncoder.encode(os, tank.getFluid(), true);
                os.writeInt(tank.getCapacity());
                break;
            }
            case GameProfile: {
                GameProfile gp = (GameProfile)o;
                DataEncoder.encode(os, gp.getId(), true);
                os.writeString(gp.getName());
                break;
            }
            case Integer: {
                os.writeInt((Integer)o);
                break;
            }
            case InvSlot: {
                InvSlot slot = (InvSlot)o;
                ItemStack[] contents = new ItemStack[slot.size()];
                for (int i = 0; i < slot.size(); ++i) {
                    contents[i] = slot.get(i);
                }
                DataEncoder.encode(os, contents, false);
                break;
            }
            case Item: {
                DataEncoder.encode(os, Util.getName((Item)o), false);
                break;
            }
            case ItemStack: {
                ItemStack stack = (ItemStack)o;
                if (StackUtil.isEmpty(stack)) {
                    os.writeByte(0);
                    break;
                }
                os.writeByte(StackUtil.getSize(stack));
                DataEncoder.encode(os, stack.func_77973_b(), false);
                os.writeShort(stack.func_77952_i());
                DataEncoder.encode(os, stack.func_77978_p(), true);
                break;
            }
            case Long: {
                os.writeLong((Long)o);
                break;
            }
            case NBTTagCompound: {
                CompressedStreamTools.func_74800_a((NBTTagCompound)((NBTTagCompound)o), (DataOutput)os);
                break;
            }
            case Null: {
                if (withType) break;
                throw new IllegalArgumentException("o has to be non-null without types");
            }
            case Object: {
                throw new IllegalArgumentException("unhandled class: " + o.getClass());
            }
            case Potion: {
                DataEncoder.encode(os, Potion.field_188414_b.func_177774_c((Object)((Potion)o)), false);
                break;
            }
            case ResourceLocation: {
                ResourceLocation loc = (ResourceLocation)o;
                os.writeString(loc.func_110624_b());
                os.writeString(loc.func_110623_a());
                break;
            }
            case Short: {
                os.writeShort(((Short)o).shortValue());
                break;
            }
            case String: {
                os.writeString((String)o);
                break;
            }
            case TileEntity: {
                TileEntity te = (TileEntity)o;
                DataEncoder.encode(os, te.func_145831_w(), false);
                DataEncoder.encode(os, te.func_174877_v(), false);
                break;
            }
            case TupleT2: {
                Tuple.T2 t = (Tuple.T2)o;
                DataEncoder.encode(os, t.a, true);
                DataEncoder.encode(os, t.b, true);
                break;
            }
            case TupleT3: {
                Tuple.T3 t = (Tuple.T3)o;
                DataEncoder.encode(os, t.a, true);
                DataEncoder.encode(os, t.b, true);
                DataEncoder.encode(os, t.c, true);
                break;
            }
            case UUID: {
                UUID uuid = (UUID)o;
                os.writeLong(uuid.getMostSignificantBits());
                os.writeLong(uuid.getLeastSignificantBits());
                break;
            }
            case Vec3: {
                Vec3d v = (Vec3d)o;
                os.writeDouble(v.field_72450_a);
                os.writeDouble(v.field_72448_b);
                os.writeDouble(v.field_72449_c);
                break;
            }
            case World: {
                os.writeInt(((World)o).field_73011_w.getDimension());
                break;
            }
            default: {
                throw new IllegalArgumentException("unhandled type: " + (Object)((Object)type));
            }
        }
    }

    public static Object decode(IGrowingBuffer is) throws IOException {
        try {
            return DataEncoder.decode(is, DataEncoder.typeFromId(is.readUnsignedByte()));
        }
        catch (IllegalArgumentException e) {
            String msg = "An unknown data type was received over multiplayer to be decoded.\nThis could happen due to corrupted data or a bug.";
            IC2.platform.displayError(e, msg, new Object[0]);
            return null;
        }
    }

    public static <T> T decode(IGrowingBuffer is, Class<T> clazz) throws IOException {
        EncodedType type = DataEncoder.typeFromClass(clazz);
        if (type.threadSafe) {
            return (T)DataEncoder.decode(is, type);
        }
        throw new IllegalArgumentException("requesting decode for non thread safe type");
    }

    public static <T extends Enum<T>> T decodeEnum(IGrowingBuffer is, Class<T> clazz) throws IOException {
        int ordinal = (Integer)DataEncoder.decode(is, EncodedType.Enum);
        Enum[] values = (Enum[])clazz.getEnumConstants();
        return (T)(ordinal >= 0 && ordinal < values.length ? values[ordinal] : null);
    }

    public static Object decodeDeferred(GrowingBuffer is, Class<?> clazz) throws IOException {
        EncodedType type = DataEncoder.typeFromClass(clazz);
        return DataEncoder.decode((IGrowingBuffer)is, type);
    }

    public static Object decode(final IGrowingBuffer is, EncodedType type) throws IOException {
        switch (type) {
            case Addon: 
            case UnSafeAddon: {
                String aimTypeName = is.readString();
                final INetworkCustomEncoder ince = classToAddonType.get(DataEncoder.getClass(aimTypeName));
                if (ince == null) {
                    throw new IllegalStateException("Cannot decode an object without a decoder! Type was " + aimTypeName);
                }
                if (ince.isThreadSafe()) {
                    return ince.decode(is);
                }
                return new IResolvableValue<Object>(){

                    @Override
                    public Object get() {
                        try {
                            return ince.decode(is);
                        }
                        catch (IOException e) {
                            throw new RuntimeException("Unexpected error", e);
                        }
                    }
                };
            }
            case Array: {
                Class<?> component;
                EncodedType componentType = DataEncoder.typeFromId(is.readUnsignedByte());
                boolean primitive = is.readBoolean();
                boolean isEnum = componentType == EncodedType.Enum;
                Class<?> clazz = component = primitive ? DataEncoder.unbox(componentType.cls) : componentType.cls;
                if (component == null || isEnum) {
                    assert (componentType == EncodedType.Addon || componentType == EncodedType.UnSafeAddon || isEnum);
                    component = DataEncoder.getClass(is.readString());
                }
                final Class<?> componentClass = component;
                final int len = is.readVarInt();
                boolean anyTypeMismatch = is.readBoolean();
                boolean needsResolving = !componentType.threadSafe;
                Object array = !needsResolving ? Array.newInstance(componentClass, len) : new Object[len];
                if (!anyTypeMismatch) {
                    if (isEnum) {
                        ?[] constants = componentClass.getEnumConstants();
                        assert (constants != null);
                        for (int i = 0; i < len; ++i) {
                            Array.set(array, i, constants[(Integer)DataEncoder.decode(is, componentType)]);
                        }
                    } else {
                        for (int i = 0; i < len; ++i) {
                            Array.set(array, i, DataEncoder.decode(is, componentType));
                        }
                    }
                } else {
                    for (int i = 0; i < len; ++i) {
                        EncodedType cType = DataEncoder.typeFromId(is.readUnsignedByte());
                        if (!cType.threadSafe && !needsResolving) {
                            needsResolving = true;
                            if (componentClass != Object.class) {
                                Object[] newArray = new Object[len];
                                System.arraycopy(array, 0, newArray, 0, i);
                                array = newArray;
                            }
                        }
                        Array.set(array, i, DataEncoder.decode(is, cType));
                    }
                }
                if (!needsResolving) {
                    return array;
                }
                final Object tmpArray = array;
                return new IResolvableValue<Object>(){

                    @Override
                    public Object get() {
                        Object ret = Array.newInstance(componentClass, len);
                        for (int i = 0; i < len; ++i) {
                            Array.set(ret, i, DataEncoder.getValue(Array.get(tmpArray, i)));
                        }
                        return ret;
                    }
                };
            }
            case Block: {
                return Util.getBlock((ResourceLocation)DataEncoder.decode(is, EncodedType.ResourceLocation));
            }
            case BlockPos: {
                return new BlockPos(is.readInt(), is.readInt(), is.readInt());
            }
            case Boolean: {
                return is.readBoolean();
            }
            case Byte: {
                return is.readByte();
            }
            case Character: {
                return Character.valueOf(is.readChar());
            }
            case ChunkPos: {
                return new ChunkPos(is.readInt(), is.readInt());
            }
            case Collection: {
                final Object ret = DataEncoder.decode(is, EncodedType.Array);
                if (ret instanceof IResolvableValue) {
                    return new IResolvableValue<List<Object>>(){

                        @Override
                        public List<Object> get() {
                            return Arrays.asList((Object[])((IResolvableValue)ret).get());
                        }
                    };
                }
                return Arrays.asList((Object[])ret);
            }
            case Component: {
                return DataEncoder.decode(is, EncodedType.NBTTagCompound);
            }
            case CropCard: {
                return Crops.instance.getCropCard(is.readString(), is.readString());
            }
            case Double: {
                return is.readDouble();
            }
            case ElectrolyzerRecipe: {
                int inputAmount = is.readInt();
                int EUaTick = is.readInt();
                int ticksNeeded = is.readInt();
                byte max = is.readByte();
                IElectrolyzerRecipeManager.ElectrolyzerOutput[] outputs = new IElectrolyzerRecipeManager.ElectrolyzerOutput[max];
                for (byte i = 0; i < max; i = (byte)(i + 1)) {
                    outputs[i] = new IElectrolyzerRecipeManager.ElectrolyzerOutput(is.readString(), is.readInt(), EnumFacing.func_82600_a((int)is.readByte()));
                }
                return new IElectrolyzerRecipeManager.ElectrolyzerRecipe(inputAmount, EUaTick, ticksNeeded, outputs);
            }
            case Enchantment: {
                return Enchantment.field_185264_b.func_82594_a((Object)((ResourceLocation)DataEncoder.decode(is, EncodedType.ResourceLocation)));
            }
            case Enum: {
                return is.readVarInt();
            }
            case Float: {
                return Float.valueOf(is.readFloat());
            }
            case Fluid: {
                return FluidRegistry.getFluid((String)is.readString());
            }
            case FluidStack: {
                FluidStack ret = new FluidStack((Fluid)DataEncoder.decode(is, EncodedType.Fluid), is.readInt());
                ret.tag = (NBTTagCompound)DataEncoder.decode(is);
                return ret;
            }
            case FluidTank: {
                return new FluidTank((FluidStack)DataEncoder.decode(is), is.readInt());
            }
            case GameProfile: {
                return new GameProfile((UUID)DataEncoder.decode(is), is.readString());
            }
            case Integer: {
                return is.readInt();
            }
            case InvSlot: {
                ItemStack[] contents = (ItemStack[])DataEncoder.decode(is, EncodedType.Array);
                InvSlot ret = new InvSlot(contents.length);
                for (int i = 0; i < contents.length; ++i) {
                    ret.put(i, contents[i]);
                }
                return ret;
            }
            case Item: {
                return Util.getItem((ResourceLocation)DataEncoder.decode(is, EncodedType.ResourceLocation));
            }
            case ItemStack: {
                byte size = is.readByte();
                if (size == 0) {
                    return StackUtil.emptyStack;
                }
                Item item = DataEncoder.decode(is, Item.class);
                short meta = is.readShort();
                NBTTagCompound nbt = (NBTTagCompound)DataEncoder.decode(is);
                ItemStack ret = new ItemStack(item, (int)size, (int)meta);
                ret.func_77982_d(nbt);
                return ret;
            }
            case Long: {
                return is.readLong();
            }
            case NBTTagCompound: {
                return CompressedStreamTools.func_152456_a((DataInput)is, (NBTSizeTracker)NBTSizeTracker.field_152451_a);
            }
            case Null: {
                return null;
            }
            case Object: {
                return new Object();
            }
            case Potion: {
                return Potion.field_188414_b.func_82594_a((Object)((ResourceLocation)DataEncoder.decode(is, EncodedType.ResourceLocation)));
            }
            case ResourceLocation: {
                return new ResourceLocation(is.readString(), is.readString());
            }
            case Short: {
                return is.readShort();
            }
            case String: {
                return is.readString();
            }
            case TileEntity: {
                final IResolvableValue deferredWorld = (IResolvableValue)DataEncoder.decode(is, EncodedType.World);
                final BlockPos pos = (BlockPos)DataEncoder.decode(is, EncodedType.BlockPos);
                return new IResolvableValue<TileEntity>(){

                    @Override
                    public TileEntity get() {
                        World world = (World)deferredWorld.get();
                        if (world == null) {
                            return null;
                        }
                        return world.func_175625_s(pos);
                    }
                };
            }
            case TupleT2: {
                return new Tuple.T2<Object, Object>(DataEncoder.decode(is), DataEncoder.decode(is));
            }
            case TupleT3: {
                return new Tuple.T3<Object, Object, Object>(DataEncoder.decode(is), DataEncoder.decode(is), DataEncoder.decode(is));
            }
            case UUID: {
                return new UUID(is.readLong(), is.readLong());
            }
            case Vec3: {
                return new Vec3d(is.readDouble(), is.readDouble(), is.readDouble());
            }
            case World: {
                final int dimensionId = is.readInt();
                return new IResolvableValue<World>(){

                    @Override
                    public World get() {
                        return IC2.platform.getWorld(dimensionId);
                    }
                };
            }
        }
        throw new IllegalArgumentException("unhandled type: " + (Object)((Object)type));
    }

    public static <T> T getValue(Object decoded) {
        if (decoded instanceof IResolvableValue) {
            return ((IResolvableValue)decoded).get();
        }
        return (T)decoded;
    }

    public static <T> boolean copyValue(T src, T dst) {
        if (src == null || dst == null) {
            return false;
        }
        if (dst instanceof ItemStack) {
            ItemStack srcT = (ItemStack)src;
            ItemStack dstT = (ItemStack)dst;
            if (srcT.func_77973_b() == dstT.func_77973_b()) {
                dstT.func_190920_e(srcT.func_190916_E());
                StackUtil.setRawMeta(dstT, StackUtil.getRawMeta(srcT));
                dstT.func_77982_d(srcT.func_77978_p());
                return true;
            }
            return false;
        }
        if (dst instanceof FluidTank) {
            FluidTank srcT = (FluidTank)src;
            FluidTank dstT = (FluidTank)dst;
            dstT.setFluid(srcT.getFluid());
            dstT.setCapacity(srcT.getCapacity());
        } else if (dst instanceof InvSlot) {
            InvSlot srcT = (InvSlot)src;
            InvSlot dstT = (InvSlot)dst;
            if (srcT.size() != dstT.size()) {
                throw new RuntimeException("Can't sync InvSlots with mismatched sizes.");
            }
            for (int i = 0; i < srcT.size(); ++i) {
                if (DataEncoder.copyValue(srcT.get(i), dstT.get(i))) continue;
                dstT.put(i, srcT.get(i));
            }
        } else if (dst instanceof TileEntityComponent) {
            NBTTagCompound nbt = (NBTTagCompound)src;
            ((TileEntityComponent)dst).readFromNbt(nbt);
        } else if (dst instanceof Collection) {
            Collection srcT = (Collection)src;
            Collection dstT = (Collection)dst;
            dstT.clear();
            dstT.addAll(srcT);
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

    private static Class<?> getClass(String type) {
        try {
            return Class.forName(type);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Missing type from the class path expected by network: " + type, e);
        }
    }

    private static int idFromType(EncodedType type) {
        return type.ordinal();
    }

    private static EncodedType typeFromId(int id) {
        if (id < 0 || id >= EncodedType.types.length) {
            throw new IllegalArgumentException("invalid type id: " + id);
        }
        return EncodedType.types[id];
    }

    private static EncodedType typeFromObject(Object o) {
        if (o == null) {
            return EncodedType.Null;
        }
        return DataEncoder.typeFromClass(o.getClass());
    }

    private static EncodedType typeFromClass(Class<?> cls) {
        EncodedType ret;
        if (cls == null) {
            return EncodedType.Null;
        }
        if (cls.isArray()) {
            return EncodedType.Array;
        }
        if (cls.isPrimitive()) {
            cls = DataEncoder.box(cls);
        }
        if ((ret = EncodedType.classToTypeMap.get(cls)) != null) {
            return ret;
        }
        ret = classToTypeCache.get(cls);
        if (ret != null) {
            return ret;
        }
        INetworkCustomEncoder ince = classToAddonType.get(cls);
        if (ince != null) {
            ret = ince.isThreadSafe() ? EncodedType.Addon : EncodedType.UnSafeAddon;
            classToTypeCache.put(cls, ret);
            return ret;
        }
        for (EncodedType type : EncodedType.types) {
            if (type.cls == null || !type.cls.isAssignableFrom(cls)) continue;
            classToTypeCache.put(cls, type);
            return type;
        }
        throw new IllegalStateException("unmatched " + cls);
    }

    public static void addNetworkEncoder(Class<?> typeBeingEncoded, INetworkCustomEncoder customEncoder) {
        assert (typeBeingEncoded != null && customEncoder != null);
        INetworkCustomEncoder previous = classToAddonType.put(typeBeingEncoded, customEncoder);
        if (previous != null) {
            throw new IllegalStateException("Duplicate mapping for class! " + previous.getClass().getName() + " and " + customEncoder.getClass().getName() + " both map for " + typeBeingEncoded.getName() + '.');
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
        TileEntity(TileEntity.class, false),
        ItemStack(ItemStack.class),
        World(World.class, false),
        NBTTagCompound(NBTTagCompound.class),
        ResourceLocation(ResourceLocation.class),
        GameProfile(GameProfile.class),
        Potion(Potion.class),
        Enchantment(Enchantment.class),
        BlockPos(BlockPos.class),
        ChunkPos(ChunkPos.class),
        Vec3(Vec3d.class),
        Fluid(Fluid.class),
        FluidStack(FluidStack.class),
        FluidTank(FluidTank.class),
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

        private EncodedType(Class<?> cls) {
            this(cls, true);
        }

        private EncodedType(Class<?> cls, boolean threadSafe) {
            this.cls = cls;
            this.threadSafe = threadSafe;
        }

        static {
            types = EncodedType.values();
            classToTypeMap = new IdentityHashMap(types.length - 2);
            for (EncodedType type : types) {
                if (type.cls == null) continue;
                classToTypeMap.put(type.cls, type);
            }
            if (types.length > 255) {
                throw new RuntimeException("too many types");
            }
        }
    }

    private static interface IResolvableValue<T> {
        public T get();
    }
}

