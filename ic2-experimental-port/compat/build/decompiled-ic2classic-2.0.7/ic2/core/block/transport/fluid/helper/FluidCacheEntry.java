/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.transport.fluid.helper;

import ic2.core.IC2;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.inventory.base.INBTSavable;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidCacheEntry
implements IntSupplier,
Predicate<Fluid>,
INBTSavable {
    final IFluidPipe pipe;
    final Direction dir;
    final ICache<IFluidHandler> cache;
    NonNullList<Fluid> fluids = NonNullList.m_122780_((int)9, (Object)Fluids.f_76191_);
    IFluidPipe.FluidOutput output;
    public Set<Fluid> filter = new ObjectOpenHashSet();
    public int priority = 0;
    public boolean whiteList = true;

    public FluidCacheEntry(IFluidPipe pipe, Direction dir, ICache<IFluidHandler> cache) {
        this.pipe = pipe;
        this.dir = dir;
        this.cache = cache;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        NBTUtils.putBoolean(nbt, "white", this.whiteList, true);
        NBTUtils.putInt(nbt, "priority", this.priority, 0);
        ListTag list = new ListTag();
        for (int i = 0; i < 9; ++i) {
            Fluid fluid = (Fluid)this.fluids.get(i);
            if (fluid == Fluids.f_76191_) continue;
            CompoundTag data = new CompoundTag();
            data.m_128359_("id", ForgeRegistries.FLUIDS.getKey((Object)fluid).toString());
            data.m_128344_("index", (byte)i);
            list.add((Object)data);
        }
        NBTUtils.put(nbt, "filters", list);
        return nbt;
    }

    public void clear() {
        this.whiteList = true;
        this.fluids.clear();
        this.filter.clear();
        this.priority = 0;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.whiteList = NBTUtils.getBoolean(nbt, "white", true);
        this.priority = nbt.m_128451_("priority");
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("filters", 10), CompoundTag.class)) {
            int slot = data.m_128451_("index");
            if (slot < 0 || slot >= 9) continue;
            Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(data.m_128461_("id")));
            this.fluids.set(slot, (Object)fluid);
            if (fluid == Fluids.f_76191_) continue;
            this.filter.add(fluid);
        }
    }

    public boolean updateOutput() {
        IFluidHandler handler = this.cache.getHandler(this.dir);
        if (handler == null || this.pipe.hasAnchor(this.dir)) {
            this.output = null;
            return false;
        }
        if (this.output == null || this.output.getHandler() != handler) {
            this.output = new IFluidPipe.FluidOutput(this.pipe, this.pipe.getPosition().m_121945_(this.dir), this.dir, handler, this, this);
            return true;
        }
        return true;
    }

    public IFluidPipe.FluidOutput getOutput() {
        return this.output;
    }

    public boolean hasOutput() {
        return this.output != null;
    }

    @Override
    public boolean test(Fluid t) {
        return this.filter.isEmpty() || this.filter.contains(t) == this.whiteList;
    }

    @Override
    public int getAsInt() {
        return this.priority;
    }

    public void setFluid(int slot, Fluid fluid) {
        this.fluids.set(slot, (Object)fluid);
        if (IC2.PLATFORM.isSimulating()) {
            this.filter.clear();
            for (int i = 0; i < 9; ++i) {
                fluid = (Fluid)this.fluids.get(i);
                if (fluid == Fluids.f_76191_) continue;
                this.filter.add(fluid);
            }
        }
    }

    public Fluid getFluid(int slot) {
        return (Fluid)this.fluids.get(slot);
    }

    public void addPriority(int value) {
        this.priority += value;
    }

    public void setWhitelist(boolean value) {
        this.whiteList = value;
    }
}

