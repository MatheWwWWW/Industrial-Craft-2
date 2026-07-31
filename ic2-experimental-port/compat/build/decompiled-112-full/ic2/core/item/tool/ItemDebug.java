/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.BlockStateContainer
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.ITickable
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fml.common.FMLCommonHandler
 */
package ic2.core.item.tool;

import ic2.api.crops.CropCard;
import ic2.api.energy.EnergyNet;
import ic2.api.item.IBoxable;
import ic2.api.item.IDebuggable;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import ic2.api.reactor.IReactor;
import ic2.api.tile.IEnergyStorage;
import ic2.core.IC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Redstone;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.crop.TileEntityCrop;
import ic2.core.item.InfiniteElectricItemManager;
import ic2.core.item.ItemIC2;
import ic2.core.ref.ItemName;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ItemDebug
extends ItemIC2
implements ISpecialElectricItem,
IBoxable {
    private static IElectricItemManager manager = null;

    public ItemDebug() {
        super(ItemName.debug_item);
        this.func_77627_a(false);
        if (!Util.inDev()) {
            this.func_77637_a(null);
        }
    }

    /*
     * WARNING - void declaration
     */
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        block43: {
            ByteArrayOutputStream chatBuffer;
            ByteArrayOutputStream consoleBuffer;
            block42: {
                NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(StackUtil.get(player, hand));
                int modeIdx = nbtData.func_74762_e("mode");
                if (modeIdx < 0 || modeIdx >= Mode.modes.length) {
                    modeIdx = 0;
                }
                Mode mode = Mode.modes[modeIdx];
                if (IC2.keyboard.isModeSwitchKeyDown(player)) {
                    if (!world.field_72995_K) {
                        mode = Mode.modes[(mode.ordinal() + 1) % Mode.modes.length];
                        nbtData.func_74768_a("mode", mode.ordinal());
                        IC2.platform.messagePlayer(player, "Debug Item Mode: " + mode.getName(), new Object[0]);
                        return EnumActionResult.SUCCESS;
                    }
                    return EnumActionResult.PASS;
                }
                TileEntity tileentity = world.func_175625_s(pos);
                if (tileentity instanceof IDebuggable) {
                    if (world.field_72995_K) {
                        return EnumActionResult.PASS;
                    }
                    IDebuggable dbg = (IDebuggable)tileentity;
                    if (dbg.isDebuggable() && !world.field_72995_K) {
                        IC2.platform.messagePlayer(player, dbg.getDebugText(), new Object[0]);
                    }
                    return world.field_72995_K ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
                }
                consoleBuffer = new ByteArrayOutputStream();
                PrintStream console = new PrintStream(consoleBuffer);
                chatBuffer = new ByteArrayOutputStream();
                PrintStream chat = new PrintStream(chatBuffer);
                switch (mode) {
                    case InterfacesFields: 
                    case InterfacesFieldsRetrace: {
                        IBlockState state;
                        RayTraceResult position;
                        if (mode == Mode.InterfacesFields) {
                            position = new RayTraceResult(RayTraceResult.Type.BLOCK, new Vec3d((double)hitX, (double)hitY, (double)hitX), side, pos);
                        } else {
                            position = this.func_77621_a(world, player, true);
                            if (position == null) {
                                return EnumActionResult.PASS;
                            }
                            RayTraceResult entityPosition = Util.traceEntities(player, position.field_72307_f, true);
                            if (entityPosition != null) {
                                position = entityPosition;
                            }
                        }
                        String plat = FMLCommonHandler.instance().getSide().isClient() ? (!world.field_72995_K ? "sp server" : (player.func_184102_h() == null ? "mp client" : "sp client")) : "mp server";
                        if (position.field_72313_a == RayTraceResult.Type.BLOCK) {
                            pos = position.func_178782_a();
                            state = world.func_180495_p(pos);
                            Block block = state.func_177230_c();
                            TileEntity te = world.func_175625_s(pos);
                            String message = String.format("[%s] block state: %s%nname: %s%ncls: %s%nte: %s", plat, state.func_185899_b((IBlockAccess)world, pos), block.func_149739_a(), block.getClass().getName(), te);
                            chat.println(message);
                            console.println(message);
                            if (te != null) {
                                message = "[" + plat + "] interfaces:";
                                Class<?> c = te.getClass();
                                do {
                                    for (Class<?> i : c.getInterfaces()) {
                                        message = message + " " + i.getName();
                                    }
                                } while ((c = c.getSuperclass()) != null);
                                chat.println(message);
                                console.println(message);
                            }
                            console.println("block fields:");
                            ItemDebug.dumpObjectFields(console, block);
                            if (te == null) break;
                            console.println();
                            console.println("tile entity fields:");
                            ItemDebug.dumpObjectFields(console, te);
                            break;
                        }
                        if (position.field_72313_a == RayTraceResult.Type.ENTITY) {
                            Object message = "[" + plat + "] entity: " + position.field_72308_g;
                            chat.println((String)message);
                            console.println((String)message);
                            if (!(position.field_72308_g instanceof EntityItem)) break;
                            ItemStack itemStack = ((EntityItem)position.field_72308_g).func_92059_d();
                            String name = Util.getName(itemStack.func_77973_b()).toString();
                            message = "[" + plat + "] item id: " + name + " meta: " + itemStack.func_77952_i() + " size: " + StackUtil.getSize(itemStack) + " name: " + itemStack.func_77977_a();
                            chat.println((String)message);
                            console.println((String)message);
                            console.println("NBT: " + itemStack.func_77978_p());
                            break;
                        }
                        return EnumActionResult.PASS;
                    }
                    case TileData: {
                        Object te;
                        if (world.field_72995_K) {
                            return EnumActionResult.PASS;
                        }
                        TileEntity tileEntity = world.func_175625_s(pos);
                        if (tileEntity instanceof TileEntityBlock) {
                            te = (TileEntityBlock)tileEntity;
                            chat.println("Block: Active=" + ((TileEntityBlock)te).getActive() + " Facing=" + ((TileEntityBlock)te).getFacing());
                            for (TileEntityComponent tileEntityComponent : ((TileEntityBlock)te).getComponents()) {
                                if (tileEntityComponent instanceof Energy) {
                                    Energy energy = (Energy)tileEntityComponent;
                                    chat.printf("Energy: %.2f / %.2f%n", energy.getEnergy(), energy.getCapacity());
                                    continue;
                                }
                                if (!(tileEntityComponent instanceof Redstone)) continue;
                                Redstone redstone = (Redstone)tileEntityComponent;
                                chat.printf("Redstone: %d%n", redstone.getRedstoneInput());
                            }
                        }
                        if (tileEntity instanceof TileEntityBaseGenerator) {
                            te = (TileEntityBaseGenerator)tileEntity;
                            chat.println("BaseGen: Fuel=" + ((TileEntityBaseGenerator)te).fuel);
                        }
                        if (tileEntity instanceof IEnergyStorage) {
                            te = (IEnergyStorage)tileEntity;
                            chat.println("EnergyStorage: Stored=" + te.getStored());
                        }
                        if (tileEntity instanceof IReactor) {
                            te = (IReactor)tileEntity;
                            chat.println("Reactor: Heat=" + te.getHeat() + " MaxHeat=" + te.getMaxHeat() + " HEM=" + te.getHeatEffectModifier() + " Output=" + te.getReactorEnergyOutput());
                        }
                        if (tileEntity instanceof IPersonalBlock) {
                            te = (IPersonalBlock)tileEntity;
                            chat.println("PersonalBlock: CanAccess=" + te.permitsAccess(player.func_146103_bH()));
                        }
                        if (!(tileEntity instanceof TileEntityCrop)) break;
                        te = (TileEntityCrop)tileEntity;
                        CropCard crop = ((TileEntityCrop)te).getCrop();
                        String string = crop != null ? crop.getOwner() + ":" + crop.getId() : "none";
                        chat.printf("Crop: Crop=%s Size=%d Growth=%d Gain=%d Resistance=%d Nutrients=%d Water=%d GrowthPoints=%d%n Cross=%b", string, ((TileEntityCrop)te).getCurrentSize(), ((TileEntityCrop)te).getStatGrowth(), ((TileEntityCrop)te).getStatGain(), ((TileEntityCrop)te).getStatResistance(), ((TileEntityCrop)te).getStorageNutrients(), ((TileEntityCrop)te).getStorageWater(), ((TileEntityCrop)te).getGrowthPoints(), ((TileEntityCrop)te).isCrossingBase());
                        break;
                    }
                    case EnergyNet: {
                        if (world.field_72995_K) {
                            return EnumActionResult.PASS;
                        }
                        if (EnergyNet.instance.dumpDebugInfo(world, pos, console, chat)) break;
                        return EnumActionResult.PASS;
                    }
                    case Accelerate: 
                    case AccelerateX100: {
                        void var20_34;
                        int count;
                        IBlockState state;
                        if (world.field_72995_K) {
                            return EnumActionResult.PASS;
                        }
                        TileEntity te = world.func_175625_s(pos);
                        int n = count = mode == Mode.Accelerate ? 1000 : 100000;
                        if (te == null) {
                            void var20_32;
                            state = world.func_180495_p(pos);
                            if (!state.func_177230_c().func_149653_t()) break;
                            chat.println("Running" + count + " ticks on " + state.func_177230_c() + "(" + pos + ").");
                            boolean bl = false;
                            while (var20_32 < count && world.func_180495_p(pos) == state) {
                                state.func_177230_c().func_180645_a(world, pos, state, field_77697_d);
                                ++var20_32;
                            }
                            if (var20_32 == count) break;
                            chat.println("Ran " + (int)var20_32 + " ticks before a state change.");
                            break;
                        }
                        if (!(te instanceof ITickable)) break;
                        ITickable tickable = (ITickable)te;
                        chat.println("Running " + count + " ticks on " + te + ".");
                        boolean bl = false;
                        int interruptCount = -1;
                        for (int i = 0; i < count; ++i) {
                            if (te.func_145837_r()) {
                                ++var20_34;
                                te = world.func_175625_s(pos);
                                if (!(te instanceof ITickable) || te.func_145837_r()) {
                                    interruptCount = i;
                                    break;
                                }
                                tickable = (ITickable)te;
                            }
                            tickable.func_73660_a();
                        }
                        if (var20_34 <= 0) break;
                        if (interruptCount != -1) {
                            chat.println("The tile entity changed " + (int)var20_34 + " time(s), interrupted after " + interruptCount + " updates.");
                            break;
                        }
                        chat.println("The tile entity changed " + (int)var20_34 + " time(s).");
                        break;
                    }
                }
                console.flush();
                chat.flush();
                if (!world.field_72995_K) break block42;
                try {
                    consoleBuffer.writeTo(new FileOutputStream(FileDescriptor.out));
                }
                catch (IOException e) {
                    IC2.log.warn(LogCategory.Item, e, "Stdout write failed.");
                }
                for (String string : chatBuffer.toString().split("[\\r\\n]+")) {
                    IC2.platform.messagePlayer(player, string, new Object[0]);
                }
                break block43;
            }
            if (!(player instanceof EntityPlayerMP)) break block43;
            try {
                IC2.network.get(true).sendConsole((EntityPlayerMP)player, consoleBuffer.toString("UTF-8"));
                IC2.network.get(true).sendChat((EntityPlayerMP)player, chatBuffer.toString("UTF-8"));
            }
            catch (UnsupportedEncodingException e) {
                IC2.log.warn(LogCategory.Item, e, "String encoding failed.");
            }
        }
        return world.field_72995_K ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
    }

    private static void dumpObjectFields(PrintStream ps, Object o) {
        Class<?> fieldDeclaringClass = o.getClass();
        do {
            Field[] fields;
            for (Field field : fields = fieldDeclaringClass.getDeclaredFields()) {
                ArrayList<Object> value;
                if ((field.getModifiers() & 8) != 0 && (fieldDeclaringClass == Block.class || fieldDeclaringClass == TileEntity.class)) continue;
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                try {
                    value = field.get(o);
                }
                catch (IllegalAccessException e) {
                    value = "<can't access>";
                }
                ps.println(field.getName() + " class: " + fieldDeclaringClass.getName() + " type: " + field.getType());
                ps.printf("  identity hash: %x hash: %x modifiers: %x%n", System.identityHashCode(value), value == null ? 0 : ((Object)value).hashCode(), field.getModifiers());
                if (value != null && field.getType().isArray()) {
                    ArrayList<Object> array = new ArrayList<Object>();
                    for (int i = 0; i < Array.getLength(value); ++i) {
                        array.add(Array.get(value, i));
                    }
                    value = array;
                }
                if (value instanceof Iterable) {
                    ps.println("  values (" + (value instanceof Collection ? Integer.valueOf(((Collection)value).size()) : "?") + "):");
                    int i = 0;
                    for (Object o2 : (Iterable)value) {
                        ps.print("    [" + i++ + "] ");
                        ItemDebug.dumpValueString(o2, field, "      ", ps);
                    }
                } else if (value instanceof Map) {
                    ps.println("  values (" + ((Map)((Object)value)).size() + "):");
                    for (Map.Entry entry : ((Map)((Object)value)).entrySet()) {
                        ps.print("    " + entry.getKey() + ": ");
                        ItemDebug.dumpValueString(entry.getValue(), field, "      ", ps);
                    }
                } else {
                    ps.print("  value: ");
                    ItemDebug.dumpValueString(value, field, "    ", ps);
                }
                field.setAccessible(accessible);
            }
        } while ((fieldDeclaringClass = fieldDeclaringClass.getSuperclass()) != null);
    }

    private static void dumpValueString(Object o, Field parentField, String prefix, PrintStream out) {
        String ret;
        if (o == null) {
            out.println("<null>");
            return;
        }
        if (o.getClass().isArray()) {
            ret = "";
            for (int i = 0; i < Array.getLength(o); ++i) {
                String valStr;
                Object val = Array.get(o, i);
                if (val == null) {
                    valStr = "<null>";
                } else {
                    valStr = val.toString();
                    if (valStr.length() > 32) {
                        valStr = valStr.substring(0, 20) + "... (" + (valStr.length() - 20) + " more)";
                    }
                }
                ret = ret + " [" + i + "] " + valStr;
            }
        } else {
            ret = o.toString();
        }
        if (ret.length() > 100) {
            ret = ret.substring(0, 90) + "... (" + (ret.length() - 90) + " more)";
        }
        out.println(ret);
        if (o instanceof FluidTank && IC2.platform.isSimulating()) {
            System.out.println();
        }
        if (Modifier.isStatic(parentField.getModifiers()) || parentField.isSynthetic() || o.getClass().isArray() || o.getClass().isEnum() || o.getClass().isPrimitive() || o instanceof Iterable || o instanceof Class || o instanceof String) {
            return;
        }
        if (o instanceof World) {
            out.println(prefix + " dim: " + ((World)o).field_73011_w.getDimension());
        } else if (!(o instanceof BlockStateContainer || o instanceof Block || o instanceof TileEntity || o instanceof Item || o instanceof ItemStack || o instanceof Vec3i || o instanceof Vec3d || o instanceof NBTBase || o.getClass().getName().startsWith("java."))) {
            for (Class<?> fieldDeclaringClass = o.getClass(); fieldDeclaringClass != null && fieldDeclaringClass != Object.class; fieldDeclaringClass = fieldDeclaringClass.getSuperclass()) {
                for (Field field : fieldDeclaringClass.getDeclaredFields()) {
                    Object val;
                    if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) continue;
                    try {
                        field.setAccessible(true);
                        val = field.get(o);
                    }
                    catch (Exception e) {
                        val = "<can't access>";
                    }
                    String valStr = val == o ? "<parent>" : ItemDebug.toStringLimited(val, 100);
                    out.println(prefix + field.getName() + ": " + valStr);
                }
            }
        }
    }

    private static String toStringLimited(Object o, int limit) {
        if (o == null) {
            return "<null>";
        }
        int extra = 12;
        limit = Math.max(limit, 12);
        String ret = o.toString();
        if (ret.length() > limit) {
            int newLimit = limit - 12;
            return ret.substring(0, newLimit) + "... (" + (ret.length() - newLimit) + " more)";
        }
        return ret;
    }

    @Override
    public IElectricItemManager getManager(ItemStack stack) {
        if (manager == null) {
            manager = new InfiniteElectricItemManager();
        }
        return manager;
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemstack) {
        return true;
    }

    private static enum Mode {
        InterfacesFields("Interfaces and Fields"),
        InterfacesFieldsRetrace("Interfaces and Fields (liquid/entity)"),
        TileData("Tile Data"),
        EnergyNet("Energy Net"),
        Accelerate("Accelerate"),
        AccelerateX100("Accelerate x100");

        static final Mode[] modes;
        private final String name;

        private Mode(String name) {
            this.name = name;
        }

        String getName() {
            return this.name;
        }

        static {
            modes = Mode.values();
        }
    }
}

