/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.BucketItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidType
 *  net.minecraftforge.fluids.FluidType$Properties
 *  net.minecraftforge.fluids.ForgeFlowingFluid$Flowing
 *  net.minecraftforge.fluids.ForgeFlowingFluid$Properties
 *  net.minecraftforge.fluids.ForgeFlowingFluid$Source
 *  net.minecraftforge.fluids.IFluidBlock
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.fluids.capability.IFluidHandlerItem
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 *  net.minecraftforge.registries.IForgeRegistry
 *  org.apache.commons.lang3.mutable.Mutable
 *  org.jetbrains.annotations.Nullable
 */
package ic2.forge;

import ic2.core.IC2;
import ic2.core.fluid.EnvFluidHandler;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.forge.Ic2FluidStackImpl;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.mutable.Mutable;
import org.jetbrains.annotations.Nullable;

class EnvFluidHandlerForge
implements EnvFluidHandler {
    static final DeferredRegister<Fluid> fluidRegistry = DeferredRegister.create((IForgeRegistry)ForgeRegistries.FLUIDS, (String)"ic2");
    static final DeferredRegister<FluidType> fluidTypeRegistry = DeferredRegister.create((ResourceKey)ForgeRegistries.Keys.FLUID_TYPES, (String)"ic2");

    EnvFluidHandlerForge() {
    }

    @Override
    public EnvFluidHandler.FluidRefs createFluid(ResourceLocation resourceLocation, Material material, int n, int n2, int n3, int n4, final ResourceLocation resourceLocation2, final ResourceLocation resourceLocation3, final int n5) {
        AtomicReference<EnvFluidHandler.FluidRefs> atomicReference = new AtomicReference<EnvFluidHandler.FluidRefs>();
        FluidType.Properties properties = FluidType.Properties.create().density(n).viscosity(n2).lightLevel(n3).temperature(n4);
        FluidType fluidType = new FluidType(properties){

            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions(){

                    public int getTintColor() {
                        return n5;
                    }

                    public ResourceLocation getStillTexture() {
                        return resourceLocation2;
                    }

                    public ResourceLocation getFlowingTexture() {
                        return resourceLocation3;
                    }
                });
            }
        };
        ForgeFlowingFluid.Properties properties2 = new ForgeFlowingFluid.Properties(() -> fluidType, () -> ((EnvFluidHandler.FluidRefs)atomicReference.get()).still, () -> ((EnvFluidHandler.FluidRefs)atomicReference.get()).flowing).bucket(() -> ((EnvFluidHandler.FluidRefs)atomicReference.get()).bucket);
        ForgeFlowingFluid.Source source = new ForgeFlowingFluid.Source(properties2);
        ForgeFlowingFluid.Flowing flowing = resourceLocation3 != null ? new ForgeFlowingFluid.Flowing(properties2) : null;
        BucketItem bucketItem = new BucketItem(() -> EnvFluidHandlerForge.lambda$createFluid$4((Fluid)source), new Item.Properties().m_41495_(Items.f_42446_).m_41487_(1).m_41491_(IC2.tabIC2));
        EnvFluidHandler.FluidRefs fluidRefs = new EnvFluidHandler.FluidRefs(null, (Fluid)source, (Fluid)flowing, bucketItem);
        atomicReference.set(fluidRefs);
        ((IForgeRegistry)ForgeRegistries.FLUID_TYPES.get()).register(resourceLocation, (Object)fluidType);
        ForgeRegistries.FLUIDS.register(resourceLocation, (Object)source);
        if (fluidRefs.flowing != null) {
            ForgeRegistries.FLUIDS.register(new ResourceLocation(resourceLocation.m_135827_(), "flowing_" + resourceLocation.m_135815_()), (Object)flowing);
        }
        IC2.envProxy.registerItem(new ResourceLocation(resourceLocation.m_135827_(), resourceLocation.m_135815_() + "_bucket"), (Item)bucketItem);
        return fluidRefs;
    }

    @Override
    public Collection<Fluid> getAllFluids() {
        return ForgeRegistries.FLUIDS.getValues();
    }

    @Override
    public int getDensity(Fluid fluid) {
        return fluid.getFluidType().getDensity();
    }

    @Override
    public int getTemperature(Fluid fluid) {
        return fluid.getFluidType().getTemperature();
    }

    @Override
    public boolean isGaseous(Fluid fluid) {
        return fluid.getFluidType().isLighterThanAir();
    }

    @Override
    public ResourceLocation getStillSpriteId(Fluid fluid) {
        throw new UnsupportedOperationException("client only");
    }

    @Override
    public ResourceLocation getFlowingSpriteId(Fluid fluid) {
        throw new UnsupportedOperationException("client only");
    }

    @Override
    public int getColor(Fluid fluid) {
        throw new UnsupportedOperationException("client only");
    }

    @Override
    public Ic2FluidStack createFluidStackMb(Fluid fluid, int n, CompoundTag compoundTag) {
        return new Ic2FluidStackImpl(new FluidStack(fluid, n, compoundTag));
    }

    @Override
    public Ic2FluidStack getFluidStack(ItemStack itemStack) {
        FluidStack fluidStack;
        IFluidHandlerItem iFluidHandlerItem;
        if (StackUtil.isEmpty(itemStack)) {
            return null;
        }
        if (itemStack.m_41613_() != 1) {
            itemStack = StackUtil.copyWithSize(itemStack, 1);
        }
        if ((iFluidHandlerItem = EnvFluidHandlerForge.getFluidHandler(itemStack)) == null) {
            return null;
        }
        if (iFluidHandlerItem.getTanks() <= 0 || (fluidStack = iFluidHandlerItem.getFluidInTank(0)) == null) {
            fluidStack = iFluidHandlerItem.drain(Integer.MAX_VALUE, EnvFluidHandlerForge.getAction(true));
        }
        return fluidStack != null && !fluidStack.isEmpty() ? new Ic2FluidStackImpl(fluidStack) : Ic2FluidStack.EMPTY;
    }

    @Override
    public Ic2FluidStack[] getFluidStacks(ItemStack itemStack) {
        FluidStack fluidStack;
        IFluidHandlerItem iFluidHandlerItem;
        if (StackUtil.isEmpty(itemStack)) {
            return null;
        }
        if (itemStack.m_41613_() != 1) {
            itemStack = StackUtil.copyWithSize(itemStack, 1);
        }
        if ((iFluidHandlerItem = EnvFluidHandlerForge.getFluidHandler(itemStack)) == null) {
            return null;
        }
        int n = iFluidHandlerItem.getTanks();
        Ic2FluidStack[] ic2FluidStackArray = null;
        if (n > 0) {
            ic2FluidStackArray = new Ic2FluidStack[n];
            int n2 = 0;
            boolean bl = false;
            for (int i = 0; i < n; ++i) {
                FluidStack fluidStack2 = iFluidHandlerItem.getFluidInTank(i);
                if (fluidStack2 == null) continue;
                bl = true;
                ic2FluidStackArray[n2++] = !fluidStack2.isEmpty() ? new Ic2FluidStackImpl(fluidStack2) : Ic2FluidStack.EMPTY;
            }
            if (bl) {
                if (n2 < ic2FluidStackArray.length) {
                    ic2FluidStackArray = Arrays.copyOf(ic2FluidStackArray, n2);
                }
                return ic2FluidStackArray;
            }
        }
        if (!((fluidStack = iFluidHandlerItem.drain(Integer.MAX_VALUE, EnvFluidHandlerForge.getAction(true))) == null && ic2FluidStackArray != null || ic2FluidStackArray != null && ic2FluidStackArray.length == 1)) {
            ic2FluidStackArray = new Ic2FluidStack[]{fluidStack != null && !fluidStack.isEmpty() ? new Ic2FluidStackImpl(fluidStack) : Ic2FluidStack.EMPTY};
        }
        return ic2FluidStackArray;
    }

    @Override
    public Ic2FluidStack readFluidStack(CompoundTag compoundTag) {
        Fluid fluid;
        if (compoundTag.m_128425_("Tag", 10)) {
            return new Ic2FluidStackImpl(FluidStack.loadFluidStackFromNBT((CompoundTag)compoundTag));
        }
        String string = compoundTag.m_128461_("FluidName");
        int n = compoundTag.m_128451_("Amount");
        if (string != null && (fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(string))) != null && n >= 0) {
            return Ic2FluidStack.create(fluid, n);
        }
        return null;
    }

    @Override
    public CompoundTag getFluidStackNbt(Ic2FluidStack ic2FluidStack) {
        return ic2FluidStack instanceof Ic2FluidStackImpl ? ((Ic2FluidStackImpl)ic2FluidStack).parent.getTag() : null;
    }

    @Override
    public Ic2FluidStack drainMb(ItemStack itemStack, int n, boolean bl, Mutable<ItemStack> mutable) {
        if (mutable != null) {
            mutable.setValue((Object)itemStack);
        }
        if (n < 0) {
            throw new IllegalArgumentException("negative amount");
        }
        if (n == 0) {
            return Ic2FluidStack.EMPTY;
        }
        if (itemStack.m_41613_() != 1) {
            throw new IllegalArgumentException("invalid stack size: " + itemStack.m_41613_());
        }
        IFluidHandlerItem iFluidHandlerItem = EnvFluidHandlerForge.getFluidHandler(itemStack);
        if (iFluidHandlerItem == null) {
            return null;
        }
        FluidStack fluidStack = iFluidHandlerItem.drain(n, EnvFluidHandlerForge.getAction(bl));
        if (fluidStack == null || fluidStack.isEmpty()) {
            return Ic2FluidStack.EMPTY;
        }
        EnvFluidHandlerForge.updateResultStack(mutable, iFluidHandlerItem);
        return new Ic2FluidStackImpl(fluidStack);
    }

    @Override
    public int drainMb(ItemStack itemStack, Ic2FluidStack ic2FluidStack, boolean bl, Mutable<ItemStack> mutable) {
        if (mutable != null) {
            mutable.setValue((Object)itemStack);
        }
        if (ic2FluidStack == null) {
            throw new IllegalArgumentException("invalid drain medium");
        }
        if (ic2FluidStack.isEmpty()) {
            return 0;
        }
        if (itemStack.m_41613_() != 1) {
            throw new IllegalArgumentException("invalid stack size: " + itemStack.m_41613_());
        }
        IFluidHandlerItem iFluidHandlerItem = EnvFluidHandlerForge.getFluidHandler(itemStack);
        if (iFluidHandlerItem == null) {
            return 0;
        }
        FluidStack fluidStack = iFluidHandlerItem.drain(EnvFluidHandlerForge.getForgeFs(ic2FluidStack), EnvFluidHandlerForge.getAction(bl));
        if (fluidStack == null || fluidStack.isEmpty()) {
            return 0;
        }
        EnvFluidHandlerForge.updateResultStack(mutable, iFluidHandlerItem);
        return fluidStack.getAmount();
    }

    @Override
    public int fillMb(ItemStack itemStack, Ic2FluidStack ic2FluidStack, boolean bl, Mutable<ItemStack> mutable) {
        if (mutable != null) {
            mutable.setValue((Object)itemStack);
        }
        if (ic2FluidStack == null) {
            throw new IllegalArgumentException("invalid fill medium");
        }
        if (ic2FluidStack.isEmpty()) {
            return 0;
        }
        if (itemStack.m_41613_() != 1) {
            throw new IllegalArgumentException("invalid stack size: " + itemStack.m_41613_());
        }
        IFluidHandlerItem iFluidHandlerItem = EnvFluidHandlerForge.getFluidHandler(itemStack);
        if (iFluidHandlerItem == null) {
            return 0;
        }
        FluidStack fluidStack = EnvFluidHandlerForge.getForgeFs(ic2FluidStack);
        int n = iFluidHandlerItem.fill(fluidStack, EnvFluidHandlerForge.getAction(bl));
        if (n <= 0) {
            return 0;
        }
        EnvFluidHandlerForge.updateResultStack(mutable, iFluidHandlerItem);
        return n;
    }

    @Nullable
    private static IFluidHandlerItem getFluidHandler(ItemStack itemStack) {
        return (IFluidHandlerItem)itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null);
    }

    private static void updateResultStack(Mutable<ItemStack> mutable, IFluidHandlerItem iFluidHandlerItem) {
        if (mutable == null) {
            return;
        }
        assert (mutable.getValue() != null);
        ItemStack itemStack = iFluidHandlerItem.getContainer();
        if (itemStack == null) {
            IC2.log.warn(LogCategory.Item, "Fluid handler %s for item %s yielded null container", iFluidHandlerItem.getClass().getName(), Registry.f_122827_.m_7981_((Object)((ItemStack)mutable.getValue()).m_41720_()));
        } else {
            mutable.setValue((Object)itemStack);
        }
    }

    @Override
    public boolean isFluidBlock(BlockState blockState, Level level, BlockPos blockPos, BlockEntity blockEntity, Direction direction) {
        return EnvFluidHandlerForge.getFluidHandler(blockState, level, blockPos, blockEntity, direction) != null;
    }

    @Override
    public Ic2FluidStack drainMb(BlockState blockState, Level level, BlockPos blockPos, BlockEntity blockEntity, Direction direction, int n, boolean bl) {
        if (n < 0) {
            throw new IllegalArgumentException("negative amount");
        }
        if (n == 0) {
            return Ic2FluidStack.EMPTY;
        }
        IFluidHandler iFluidHandler = EnvFluidHandlerForge.getFluidHandler(blockState, level, blockPos, blockEntity, direction);
        if (iFluidHandler == null) {
            return null;
        }
        FluidStack fluidStack = iFluidHandler.drain(n, EnvFluidHandlerForge.getAction(bl));
        if (fluidStack == null || fluidStack.isEmpty()) {
            return Ic2FluidStack.EMPTY;
        }
        return new Ic2FluidStackImpl(fluidStack);
    }

    @Override
    public int drainMb(BlockState blockState, Level level, BlockPos blockPos, BlockEntity blockEntity, Direction direction, Ic2FluidStack ic2FluidStack, boolean bl) {
        if (ic2FluidStack == null) {
            throw new IllegalArgumentException("invalid drain medium");
        }
        if (ic2FluidStack.isEmpty()) {
            return 0;
        }
        if (!blockState.m_155947_()) {
            return 0;
        }
        IFluidHandler iFluidHandler = EnvFluidHandlerForge.getFluidHandler(blockState, level, blockPos, blockEntity, direction);
        if (iFluidHandler == null) {
            return 0;
        }
        FluidStack fluidStack = iFluidHandler.drain(EnvFluidHandlerForge.getForgeFs(ic2FluidStack), EnvFluidHandlerForge.getAction(bl));
        if (fluidStack == null || fluidStack.isEmpty()) {
            return 0;
        }
        return fluidStack.getAmount();
    }

    @Override
    public int fillMb(BlockState blockState, Level level, BlockPos blockPos, BlockEntity blockEntity, Direction direction, Ic2FluidStack ic2FluidStack, boolean bl) {
        if (ic2FluidStack == null) {
            throw new IllegalArgumentException("invalid fill medium");
        }
        if (ic2FluidStack.isEmpty()) {
            return 0;
        }
        if (!blockState.m_155947_()) {
            return 0;
        }
        IFluidHandler iFluidHandler = EnvFluidHandlerForge.getFluidHandler(blockState, level, blockPos, blockEntity, direction);
        if (iFluidHandler == null) {
            return 0;
        }
        FluidStack fluidStack = EnvFluidHandlerForge.getForgeFs(ic2FluidStack);
        int n = iFluidHandler.fill(fluidStack, EnvFluidHandlerForge.getAction(bl));
        if (n <= 0) {
            return 0;
        }
        return n;
    }

    private static IFluidHandler getFluidHandler(BlockState blockState, Level level, BlockPos blockPos, BlockEntity blockEntity, Direction direction) {
        if (blockEntity == null) {
            if (blockState.m_155947_()) {
                blockEntity = level.m_7702_(blockPos);
            }
            if (blockEntity == null) {
                return null;
            }
        }
        return (IFluidHandler)blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null).orElse(null);
    }

    private static IFluidHandler.FluidAction getAction(boolean bl) {
        return bl ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE;
    }

    @Override
    public Fluid getWorldFluid(BlockState blockState, Level level, BlockPos blockPos) {
        Block block = blockState.m_60734_();
        if (!(block instanceof IFluidBlock)) {
            return null;
        }
        return ((IFluidBlock)block).getFluid();
    }

    @Override
    public int getWorldFluidLevel(BlockState blockState, Level level, BlockPos blockPos) {
        Block block = blockState.m_60734_();
        if (!(block instanceof IFluidBlock)) {
            return -1;
        }
        float f = Math.abs(((IFluidBlock)block).getFilledPercentage(level, blockPos));
        return 7 - Util.limit(Math.round(6.0f * f), 0, 6);
    }

    @Override
    public Ic2FluidStack drainWorldFluid(BlockState blockState, Level level, BlockPos blockPos, boolean bl) {
        Block block = blockState.m_60734_();
        if (!(block instanceof IFluidBlock)) {
            return null;
        }
        IFluidBlock iFluidBlock = (IFluidBlock)block;
        if (!iFluidBlock.canDrain(level, blockPos)) {
            return null;
        }
        FluidStack fluidStack = iFluidBlock.drain(level, blockPos, EnvFluidHandlerForge.getAction(bl));
        if (fluidStack == null || fluidStack.isEmpty()) {
            return Ic2FluidStack.EMPTY;
        }
        return new Ic2FluidStackImpl(fluidStack);
    }

    static FluidStack getForgeFs(Ic2FluidStack ic2FluidStack) {
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            return FluidStack.EMPTY;
        }
        if (ic2FluidStack instanceof Ic2FluidStackImpl) {
            return ((Ic2FluidStackImpl)ic2FluidStack).parent;
        }
        return new FluidStack(ic2FluidStack.getFluid(), ic2FluidStack.getAmountMb());
    }

    private static /* synthetic */ Fluid lambda$createFluid$4(Fluid fluid) {
        return fluid;
    }
}

