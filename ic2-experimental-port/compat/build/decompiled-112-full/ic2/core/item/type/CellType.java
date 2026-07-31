/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumActionResult
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.FluidTankProperties
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandlerItem
 *  net.minecraftforge.fluids.capability.IFluidTankProperties
 *  net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper
 */
package ic2.core.item.type;

import ic2.core.block.state.IIdProvider;
import ic2.core.crop.TileEntityCrop;
import ic2.core.profile.NotExperimental;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

@NotExperimental
public final class CellType
extends Enum<CellType>
implements IIdProvider {
    public static final /* enum */ CellType empty = new CellType(0);
    public static final /* enum */ CellType water = new CellType(1, FluidRegistry.WATER);
    public static final /* enum */ CellType lava = new CellType(2, FluidRegistry.LAVA);
    public static final /* enum */ CellType air = new CellType(3, FluidName.air.getInstance());
    public static final /* enum */ CellType electrolyzed_water = new CellType(4);
    public static final /* enum */ CellType biofuel = new CellType(5);
    public static final /* enum */ CellType coalfuel = new CellType(6);
    public static final /* enum */ CellType bio = new CellType(7);
    public static final /* enum */ CellType hydrated_coal = new CellType(8);
    public static final /* enum */ CellType weed_ex = new CellType(9);
    public static final /* enum */ CellType hydration = new CellType(10);
    private final int id;
    final Fluid fluid;
    private static final /* synthetic */ CellType[] $VALUES;

    public static CellType[] values() {
        return (CellType[])$VALUES.clone();
    }

    public static CellType valueOf(String name) {
        return Enum.valueOf(CellType.class, name);
    }

    private CellType(int id) {
        this(id, null);
    }

    private CellType(int id, Fluid fluid) {
        this.id = id;
        this.fluid = fluid;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public int getId() {
        return this.id;
    }

    public int getStackSize() {
        return this == weed_ex || this == hydration ? 1 : 64;
    }

    public boolean isFluidContainer() {
        return this.fluid != null || this == empty;
    }

    public boolean hasCropAction() {
        return this == water || this == weed_ex || this == hydration;
    }

    public int getUsage(ItemStack stack) {
        switch (this) {
            case weed_ex: {
                return stack.func_77942_o() ? stack.func_77978_p().func_74762_e("weedEX") : 0;
            }
            case hydration: {
                return stack.func_77942_o() ? stack.func_77978_p().func_74762_e("hydration") : 0;
            }
        }
        return 0;
    }

    public int getMaximum(ItemStack stack) {
        switch (this) {
            case weed_ex: {
                return 64;
            }
            case hydration: {
                return 10000;
            }
        }
        return 0;
    }

    public EnumActionResult doCropAction(ItemStack stack, Consumer<ItemStack> result, TileEntityCrop crop, boolean manual) {
        assert (this.hasCropAction());
        switch (this) {
            case water: {
                if (crop.getStorageWater() < 10) {
                    crop.setStorageWater(10);
                    return EnumActionResult.SUCCESS;
                }
                return EnumActionResult.FAIL;
            }
            case weed_ex: {
                WeedExHandler handler = new WeedExHandler(stack);
                if (crop.applyWeedEx((IFluidHandler)handler, manual)) {
                    result.accept(handler.getContainer());
                    return EnumActionResult.SUCCESS;
                }
                return EnumActionResult.FAIL;
            }
            case hydration: {
                HydrationHandler handler = new HydrationHandler(stack, manual);
                if (crop.applyHydration((IFluidHandler)handler)) {
                    result.accept(handler.getContainer());
                    return EnumActionResult.SUCCESS;
                }
                return EnumActionResult.FAIL;
            }
        }
        throw new IllegalStateException("Type was " + this);
    }

    static {
        $VALUES = new CellType[]{empty, water, lava, air, electrolyzed_water, biofuel, coalfuel, bio, hydrated_coal, weed_ex, hydration};
    }

    private static class HydrationHandler
    implements IFluidHandlerItem {
        public static final String NBT = "hydration";
        public static final int CHARGES = 10000;
        protected ItemStack container;
        protected final boolean manual;

        public HydrationHandler(ItemStack stack, boolean manual) {
            this.container = stack;
            this.manual = manual;
        }

        public ItemStack getContainer() {
            return this.container;
        }

        public int fill(FluidStack resource, boolean doFill) {
            return 0;
        }

        public FluidStack drain(FluidStack resource, boolean doDrain) {
            if (resource == null || resource.getFluid() != FluidRegistry.WATER) {
                return null;
            }
            return this.drain(resource.amount, doDrain);
        }

        public FluidStack drain(int maxDrain, boolean doDrain) {
            int remaining = this.container.func_77942_o() ? 10000 - this.container.func_77978_p().func_74762_e(NBT) : 10000;
            int target = Math.min(maxDrain, remaining);
            if (!this.manual && target > 180) {
                target = 180;
            }
            if (doDrain) {
                NBTTagCompound nbt = StackUtil.getOrCreateNbtData(this.container);
                int amount = nbt.func_74762_e(NBT) + target;
                if (amount >= 10000) {
                    this.container = StackUtil.decSize(this.container);
                } else {
                    nbt.func_74768_a(NBT, amount);
                }
            }
            return new FluidStack(FluidRegistry.WATER, target);
        }

        public IFluidTankProperties[] getTankProperties() {
            return new IFluidTankProperties[]{new FluidTankProperties(new FluidStack(FluidRegistry.WATER, (10000 - this.container.func_77952_i()) / 200), 50, false, true)};
        }
    }

    private static class WeedExHandler
    implements IFluidHandlerItem {
        public static final String NBT = "weedEX";
        public static final int CHARGES = 64;
        private static final int DRAIN = 50;
        protected ItemStack container;

        public WeedExHandler(ItemStack stack) {
            this.container = stack;
        }

        public ItemStack getContainer() {
            return this.container;
        }

        public int fill(FluidStack resource, boolean doFill) {
            return 0;
        }

        public FluidStack drain(FluidStack resource, boolean doDrain) {
            if (resource == null || resource.getFluid() != FluidName.weed_ex.getInstance()) {
                return null;
            }
            return this.drain(resource.amount, doDrain);
        }

        public FluidStack drain(int maxDrain, boolean doDrain) {
            if (maxDrain < 50) {
                return null;
            }
            if (doDrain) {
                NBTTagCompound nbt = StackUtil.getOrCreateNbtData(this.container);
                int amount = nbt.func_74762_e(NBT) + 1;
                if (amount >= 64) {
                    this.container = StackUtil.decSize(this.container);
                } else {
                    nbt.func_74768_a(NBT, amount);
                }
            }
            return new FluidStack(FluidName.weed_ex.getInstance(), 50);
        }

        public IFluidTankProperties[] getTankProperties() {
            return new IFluidTankProperties[]{new FluidTankProperties(new FluidStack(FluidName.weed_ex.getInstance(), (64 - this.container.func_77952_i()) * 50), 3200, false, true)};
        }
    }

    public static class CellFluidHandler
    extends FluidBucketWrapper {
        private static final Map<Fluid, CellType> VALID_FLUIDS = new IdentityHashMap(Arrays.stream(CellType.values()).filter(type -> type.fluid != null).collect(Collectors.toMap(type -> type.fluid, Function.identity())));
        protected final Supplier<CellType> typeGetter = () -> (CellType)typeGetter.apply(this.container);

        public CellFluidHandler(ItemStack container, Function<ItemStack, CellType> typeGetter) {
            super(container);
        }

        public FluidStack getFluid() {
            CellType type = this.typeGetter.get();
            assert (type.isFluidContainer());
            return type != null && type.fluid != null ? new FluidStack(type.fluid, 1000) : null;
        }

        public boolean canFillFluidType(FluidStack fluid) {
            assert (fluid != null);
            assert (fluid.getFluid() != null);
            return this.typeGetter.get() == empty && VALID_FLUIDS.containsKey(fluid.getFluid());
        }

        protected void setFluid(FluidStack stack) {
            if (stack == null) {
                assert (this.typeGetter.get() != empty);
                this.container = ItemName.cell.getItemStack(empty);
            } else {
                assert (this.typeGetter.get() == empty);
                assert (VALID_FLUIDS.containsKey(stack.getFluid()));
                this.container = ItemName.cell.getItemStack((Enum)VALID_FLUIDS.get(stack.getFluid()));
            }
        }
    }
}

