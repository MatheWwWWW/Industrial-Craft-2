/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.phys.Vec3
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
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Redstone;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.crop.TileEntityCrop;
import ic2.core.energy.grid.EnergyNetGlobal;
import ic2.core.item.InfiniteElectricItemManager;
import ic2.core.item.PriorityUsableItem;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;

public class ItemDebug
extends Item
implements PriorityUsableItem,
ISpecialElectricItem,
IBoxable {
    private static IElectricItemManager manager = null;

    public ItemDebug(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext useOnContext) {
        Level level = useOnContext.m_43725_();
        BlockPos blockPos = useOnContext.m_8083_();
        Player player = useOnContext.m_43723_();
        Mode mode = ItemDebug.getMode(itemStack);
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            if (!level.f_46443_) {
                mode = Mode.modes[(mode.ordinal() + 1) % Mode.modes.length];
                ItemDebug.setMode(itemStack, mode);
                IC2.sideProxy.messagePlayer(player, "Debug Item Mode: " + mode.getName(), new Object[0]);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        BlockEntity blockEntity = level.m_7702_(blockPos);
        if (blockEntity instanceof IDebuggable) {
            if (level.f_46443_) {
                return InteractionResult.PASS;
            }
            IDebuggable iDebuggable = (IDebuggable)blockEntity;
            if (iDebuggable.isDebuggable() && !level.f_46443_) {
                IC2.sideProxy.messagePlayer(player, iDebuggable.getDebugText(), new Object[0]);
            }
            return level.f_46443_ ? InteractionResult.PASS : InteractionResult.SUCCESS;
        }
        Output output = new Output();
        switch (mode) {
            case InterfacesFields: 
            case InterfacesFieldsRetrace: {
                String string = ItemDebug.getPlatform(level);
                BlockState blockState = level.m_8055_(blockPos);
                Block block = blockState.m_60734_();
                BlockEntity blockEntity2 = level.m_7702_(blockPos);
                output.both("[%s] block state: %s%nname: %s%ncls: %s%nbe: %s", string, blockState, block.m_7705_(), block.getClass().getName(), blockEntity2);
                if (blockEntity2 != null) {
                    output.part("[%s] interfaces:", string);
                    Class<?> tileEntityComponent = blockEntity2.getClass();
                    do {
                        for (Class<?> clazz : tileEntityComponent.getInterfaces()) {
                            output.part(' ').part(clazz.getName());
                        }
                    } while ((tileEntityComponent = tileEntityComponent.getSuperclass()) != null);
                    output.partToConsole();
                }
                output.console("block fields:");
                ItemDebug.dumpObjectFields(block, output);
                if (blockEntity2 == null) break;
                output.console("");
                output.console("tile entity fields:");
                ItemDebug.dumpObjectFields(blockEntity2, output);
                break;
            }
            case TileData: {
                Object object2;
                if (level.f_46443_) {
                    return InteractionResult.PASS;
                }
                BlockEntity blockEntity3 = level.m_7702_(blockPos);
                if (blockEntity3 instanceof Ic2TileEntity) {
                    object2 = (Ic2TileEntity)blockEntity3;
                    output.chat("Block: Active=%b Facing=%s", ((Ic2TileEntity)object2).getActive(), ((Ic2TileEntity)object2).getFacing());
                    for (TileEntityComponent object3 : ((Ic2TileEntity)object2).getComponents()) {
                        TileEntityComponent tileEntityComponent;
                        if (object3 instanceof Energy) {
                            tileEntityComponent = (Energy)object3;
                            output.chat("Energy: %.2f / %.2f", ((Energy)tileEntityComponent).getEnergy(), ((Energy)tileEntityComponent).getCapacity());
                            continue;
                        }
                        if (!(object3 instanceof Redstone)) continue;
                        tileEntityComponent = (Redstone)object3;
                        output.chat("Redstone: %d", ((Redstone)tileEntityComponent).getRedstoneInput());
                    }
                }
                if (blockEntity3 instanceof TileEntityBaseGenerator) {
                    object2 = (TileEntityBaseGenerator)blockEntity3;
                    output.chat("BaseGen: Fuel=%d", ((TileEntityBaseGenerator)object2).fuel);
                }
                if (blockEntity3 instanceof IEnergyStorage) {
                    object2 = (IEnergyStorage)blockEntity3;
                    output.chat("EnergyStorage: Stored=%d", object2.getStored());
                }
                if (blockEntity3 instanceof IReactor) {
                    object2 = (IReactor)blockEntity3;
                    output.chat("Reactor: Heat=%d MaxHeat=%d HEM=%f Output=%f", object2.getHeat(), object2.getMaxHeat(), Float.valueOf(object2.getHeatEffectModifier()), Float.valueOf(object2.getReactorEnergyOutput()));
                }
                if (blockEntity3 instanceof IPersonalBlock) {
                    object2 = (IPersonalBlock)blockEntity3;
                    output.chat("PersonalBlock: CanAccess=%b", object2.permitsAccess(player.m_36316_()));
                }
                if (!(blockEntity3 instanceof TileEntityCrop)) break;
                object2 = (TileEntityCrop)blockEntity3;
                CropCard cropCard = ((TileEntityCrop)object2).getCrop();
                String printStream2 = cropCard != null ? cropCard.getOwner() + ":" + cropCard.getId() : "none";
                output.chat("Crop: Crop=%s Size=%d Growth=%d Gain=%d Resistance=%d Nutrients=%d Water=%d GrowthPoints=%d%n Cross=%b", printStream2, ((TileEntityCrop)object2).getCurrentAge(), ((TileEntityCrop)object2).getStatGrowth(), ((TileEntityCrop)object2).getStatGain(), ((TileEntityCrop)object2).getStatResistance(), ((TileEntityCrop)object2).getStorageNutrients(), ((TileEntityCrop)object2).getStorageWater(), ((TileEntityCrop)object2).getGrowthPoints(), ((TileEntityCrop)object2).isCrossingBase());
                break;
            }
            case EnergyNet: {
                if (level.f_46443_) {
                    return InteractionResult.PASS;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream((OutputStream)byteArrayOutputStream, false, StandardCharsets.UTF_8);
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                PrintStream printStream2 = new PrintStream((OutputStream)byteArrayOutputStream, false, StandardCharsets.UTF_8);
                if (!((EnergyNetGlobal)EnergyNet.instance).dumpDebugInfo(level, blockPos, printStream, printStream2)) {
                    return InteractionResult.PASS;
                }
                printStream2.flush();
                printStream.flush();
                if (byteArrayOutputStream.size() > 0) {
                    output.console(byteArrayOutputStream.toString(StandardCharsets.UTF_8).stripTrailing());
                }
                if (byteArrayOutputStream2.size() <= 0) break;
                output.chat(byteArrayOutputStream2.toString(StandardCharsets.UTF_8).stripTrailing());
                break;
            }
            case Accelerate: 
            case AccelerateX100: {
                if (level.f_46443_) {
                    return InteractionResult.PASS;
                }
                ItemDebug.accelerate(level, blockPos, mode == Mode.Accelerate ? 1000 : 100000, output);
            }
        }
        output.flush(player);
        return level.f_46443_ ? InteractionResult.PASS : InteractionResult.SUCCESS;
    }

    private static boolean accelerate(Level level, BlockPos blockPos, int n, Output output) {
        BlockEntity blockEntity;
        BlockState blockState = level.m_8055_(blockPos);
        if (!blockState.m_155947_() || (blockEntity = level.m_7702_(blockPos)) == null) {
            if (!blockState.m_60823_()) {
                return false;
            }
            output.chat("Running up to %d ticks on % (%s).", n, blockState.m_60734_(), blockPos);
            for (int i = 0; i < n && level.m_8055_(blockPos) == blockState; ++i) {
                blockState.m_222972_((ServerLevel)level, blockPos, IC2.random);
                if (level.m_8055_(blockPos) == blockState) continue;
                output.chat("Ran %d ticks before a state change.", i);
                break;
            }
            return true;
        }
        BlockEntityTicker blockEntityTicker = blockState.m_155944_(level, blockEntity.m_58903_());
        if (blockEntityTicker == null) {
            return false;
        }
        output.chat("Running %s ticks on %s.", n, blockEntity);
        int n2 = 0;
        int n3 = -1;
        for (int i = 0; i < n; ++i) {
            if (blockEntity.m_58901_()) {
                ++n2;
                blockState = level.m_8055_(blockPos);
                if (!blockState.m_155947_() || (blockEntity = level.m_7702_(blockPos)) == null || blockEntity.m_58901_() || (blockEntityTicker = blockState.m_155944_(level, blockEntity.m_58903_())) == null) {
                    n3 = i;
                    break;
                }
            }
            blockEntityTicker.m_155252_(level, blockPos, blockState, blockEntity);
        }
        if (n2 > 0) {
            if (n3 != -1) {
                output.chat("The tile entity changed %d time(s), interrupted after %d updates.", n2, n3);
            } else {
                output.chat("The tile entity changed %d time(s).", n2);
            }
        }
        return true;
    }

    public InteractionResult m_6880_(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        return ItemDebug.handleEntity(itemStack, player, (Entity)livingEntity);
    }

    private static InteractionResult handleEntity(ItemStack itemStack, Player player, Entity entity) {
        Mode mode = ItemDebug.getMode(itemStack);
        if (mode != Mode.InterfacesFieldsRetrace) {
            return InteractionResult.PASS;
        }
        Level level = player.m_9236_();
        Output output = new Output();
        String string = ItemDebug.getPlatform(level);
        output.both("[%s] entity: %s", output, entity);
        if (entity instanceof ItemEntity) {
            ItemStack itemStack2 = ((ItemEntity)entity).m_32055_();
            String string2 = Util.getName(itemStack2.m_41720_()).toString();
            output.both("[%s] item id: %s size: %s name: %s", string, string2, StackUtil.getSize(itemStack2), itemStack2.m_41778_());
            output.console("NBT: %s", itemStack2.m_41783_());
        }
        output.flush(player);
        return level.f_46443_ ? InteractionResult.PASS : InteractionResult.SUCCESS;
    }

    private static Mode getMode(ItemStack itemStack) {
        int n;
        CompoundTag compoundTag = itemStack.m_41783_();
        int n2 = n = compoundTag != null ? compoundTag.m_128451_("mode") : 0;
        if (n < 0 || n >= Mode.modes.length) {
            n = 0;
        }
        return Mode.modes[n];
    }

    private static void setMode(ItemStack itemStack, Mode mode) {
        itemStack.m_41784_().m_128405_("mode", mode.ordinal());
    }

    private static String getPlatform(Level level) {
        if (IC2.envProxy.isClientEnv()) {
            if (!level.f_46443_) {
                return "sp server";
            }
            if (level.m_7654_() == null) {
                return "mp client";
            }
            return "sp client";
        }
        return "mp server";
    }

    /*
     * Could not resolve type clashes
     */
    private static void dumpObjectFields(Object object, Output output) {
        ArrayList arrayList = new ArrayList();
        Class<?> clazz = object.getClass();
        do {
            arrayList.add(clazz);
        } while ((clazz = clazz.getSuperclass()) != null);
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            Class clazz2 = (Class)arrayList.get(i);
            Field[] fieldArray = clazz2.getDeclaredFields();
            boolean bl = false;
            for (Field field : fieldArray) {
                ArrayList<Object> arrayList2;
                int n;
                Class<?> clazz3 = field.getType();
                int n2 = field.getModifiers();
                if (Modifier.isStatic(n2) && (clazz2 == Block.class || clazz2 == BlockEntity.class || clazz2 == Ic2TileEntity.class || Modifier.isFinal(n2) && (clazz3.isPrimitive() || clazz3 == String.class || Property.class.isAssignableFrom(clazz3)))) continue;
                if (!bl) {
                    output.console(clazz2.getName());
                    bl = true;
                }
                try {
                    n = field.isAccessible();
                    field.setAccessible(true);
                    arrayList2 = field.get(object);
                    field.setAccessible(n != 0);
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                    arrayList2 = "<can't access>";
                }
                output.console("  %s type: %s", field.getName(), clazz3.getName());
                if (!ItemDebug.isSelfDescribingClass(clazz3)) {
                    output.part("    identity hash: %x hash: %x modifiers: %x", System.identityHashCode(arrayList2), arrayList2 == null ? 0 : ((Object)arrayList2).hashCode(), n2);
                    if (arrayList2 != null && arrayList2.getClass() != clazz3) {
                        output.part(" class: %s", arrayList2.getClass().getName());
                    }
                    output.partToConsole();
                }
                if (arrayList2 != null && field.getType().isArray()) {
                    ArrayList<Object> arrayList3 = new ArrayList<Object>();
                    for (int j = 0; j < Array.getLength(arrayList2); ++j) {
                        arrayList3.add(Array.get(arrayList2, j));
                    }
                    arrayList2 = arrayList3;
                }
                if (arrayList2 instanceof Iterable) {
                    output.part("    values (%s):", arrayList2 instanceof Collection ? Integer.valueOf(((Collection)arrayList2).size()) : "?");
                    n = 0;
                    for (Object t : (Iterable)arrayList2) {
                        output.part("      [%d] ", n++);
                        ItemDebug.dumpValueString(t, field, "        ", output);
                    }
                    continue;
                }
                if (arrayList2 instanceof Map) {
                    output.console("    values (%s):", ((Map)((Object)arrayList2)).size());
                    for (Map.Entry entry : ((Map)((Object)arrayList2)).entrySet()) {
                        output.part("      %s: ", entry.getKey());
                        ItemDebug.dumpValueString(entry.getValue(), field, "        ", output);
                    }
                    continue;
                }
                output.part("    value: ");
                ItemDebug.dumpValueString(arrayList2, field, "      ", output);
            }
        }
    }

    private static void dumpValueString(Object object, Field field, String string, Output output) {
        Object object2;
        if (object == null) {
            output.part("<null>");
            output.partToConsole();
            return;
        }
        if (object.getClass().isArray()) {
            object2 = "";
            for (int i = 0; i < Array.getLength(object); ++i) {
                Object object3;
                Field[] fieldArray = Array.get(object, i);
                if (fieldArray == null) {
                    object3 = "<null>";
                } else {
                    object3 = fieldArray.toString();
                    if (((String)object3).length() > 32) {
                        object3 = ((String)object3).substring(0, 20) + "... (" + (((String)object3).length() - 20) + " more)";
                    }
                }
                object2 = (String)object2 + " [" + i + "] " + (String)object3;
            }
        } else {
            object2 = object.toString();
        }
        if (((String)object2).length() > 100) {
            object2 = ((String)object2).substring(0, 90) + "... (" + (((String)object2).length() - 90) + " more)";
        }
        output.part((CharSequence)object2);
        output.partToConsole();
        if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic() || object.getClass().isArray() || object instanceof Iterable || ItemDebug.isSelfDescribingClass(object.getClass())) {
            return;
        }
        if (object instanceof Level) {
            output.console("%s dim: %s", string, Util.getDimId((Level)object));
        } else if (!(object instanceof StateDefinition || object instanceof BlockEntity || object instanceof ItemStack || object.getClass().getName().startsWith("java."))) {
            for (Class<?> clazz = object.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
                for (Field field2 : clazz.getDeclaredFields()) {
                    Object object4;
                    if (field2.isSynthetic() || Modifier.isStatic(field2.getModifiers())) continue;
                    try {
                        field2.setAccessible(true);
                        object4 = field2.get(object);
                    }
                    catch (Exception exception) {
                        object4 = "<can't access>";
                    }
                    String string2 = object4 == object ? "<parent>" : ItemDebug.toStringLimited(object4, 100);
                    output.console("%s%s: %s", string, field2.getName(), string2);
                }
            }
        }
    }

    private static boolean isSelfDescribingClass(Class<?> clazz) {
        return clazz.isPrimitive() || clazz.isEnum() || clazz == Class.class || clazz == String.class || clazz == BlockState.class || clazz == ResourceLocation.class || Tag.class.isAssignableFrom(clazz) || Vec3i.class.isAssignableFrom(clazz) || Vec3.class.isAssignableFrom(clazz) || Block.class.isAssignableFrom(clazz) || Item.class.isAssignableFrom(clazz) || Fluid.class.isAssignableFrom(clazz);
    }

    private static String toStringLimited(Object object, int n) {
        if (object == null) {
            return "<null>";
        }
        n = Math.max(n, 12);
        String string = object.toString();
        if (string.length() > n) {
            int n2 = n - 12;
            return string.substring(0, n2) + "... (" + (string.length() - n2) + " more)";
        }
        return string;
    }

    @Override
    public IElectricItemManager getManager(ItemStack itemStack) {
        if (manager == null) {
            manager = new InfiniteElectricItemManager();
        }
        return manager;
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemStack) {
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

        private Mode(String string2) {
            this.name = string2;
        }

        String getName() {
            return this.name;
        }

        static {
            modes = Mode.values();
        }
    }

    private static class Output {
        private final StringBuilder chatSb = new StringBuilder();
        private final StringBuilder consoleSb = new StringBuilder();
        private final StringBuilder partSb = new StringBuilder();

        private Output() {
        }

        public void chat(CharSequence charSequence) {
            if (!this.chatSb.isEmpty()) {
                this.chatSb.append('\n');
            }
            this.chatSb.append(charSequence);
        }

        public void chat(String string, Object ... objectArray) {
            this.chat(String.format(string, objectArray));
        }

        public void console(CharSequence charSequence) {
            if (!this.consoleSb.isEmpty()) {
                this.consoleSb.append('\n');
            }
            this.consoleSb.append(charSequence);
        }

        public void console(String string, Object ... objectArray) {
            this.console(String.format(string, objectArray));
        }

        public void both(CharSequence charSequence) {
            this.chat(charSequence);
            this.console(charSequence);
        }

        public void both(String string, Object ... objectArray) {
            this.both(String.format(string, objectArray));
        }

        public Output part(CharSequence charSequence) {
            this.partSb.append(charSequence);
            return this;
        }

        public Output part(char c) {
            this.partSb.append(c);
            return this;
        }

        public Output part(String string, Object ... objectArray) {
            return this.part(String.format(string, objectArray));
        }

        public void partToChat() {
            this.chat(this.partSb);
            this.partSb.setLength(0);
        }

        public void partToConsole() {
            this.console(this.partSb);
            this.partSb.setLength(0);
        }

        public void partToBoth() {
            this.both(this.partSb);
            this.partSb.setLength(0);
        }

        void flush(Player player) {
            if (player.m_9236_().f_46443_) {
                System.out.println(this.consoleSb);
                for (String string : this.chatSb.toString().split("[\\r\\n]+")) {
                    IC2.sideProxy.messagePlayer(player, string, new Object[0]);
                }
            } else if (player instanceof ServerPlayer) {
                IC2.network.get(true).sendConsole((ServerPlayer)player, this.consoleSb.toString());
                IC2.network.get(true).sendChat((ServerPlayer)player, this.chatSb.toString());
            }
            this.chatSb.setLength(0);
            this.consoleSb.setLength(0);
        }
    }
}

