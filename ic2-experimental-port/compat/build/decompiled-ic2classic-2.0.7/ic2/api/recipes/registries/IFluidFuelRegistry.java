/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.registries.IListenableRegistry;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public interface IFluidFuelRegistry
extends IListenableRegistry<IFluidFuelRegistry> {
    public void addFuel(Fluid var1, int var2, int var3);

    public void removeFuel(Fluid var1);

    public List<FuelEntry> getFuels();

    public FuelEntry getFuel(Fluid var1);

    public static class FuelEntry {
        final Fluid fluid;
        final int ticksPerBucket;
        final int euPerTick;

        public FuelEntry(FriendlyByteBuf buffer) {
            this.fluid = (Fluid)buffer.readRegistryIdUnsafe(ForgeRegistries.FLUIDS);
            this.ticksPerBucket = buffer.m_130242_();
            this.euPerTick = buffer.m_130242_();
        }

        public FuelEntry(Fluid fluid, int ticksPerBucket, int euPerTick) {
            this.fluid = fluid;
            this.ticksPerBucket = ticksPerBucket;
            this.euPerTick = euPerTick;
        }

        public Fluid getFluid() {
            return this.fluid;
        }

        public int getTicksPerBucket() {
            return this.ticksPerBucket;
        }

        public int getEuPerTick() {
            return this.euPerTick;
        }

        public void write(FriendlyByteBuf buffer) {
            buffer.writeRegistryIdUnsafe(ForgeRegistries.FLUIDS, (Object)this.fluid);
            buffer.m_130130_(this.ticksPerBucket);
            buffer.m_130130_(this.euPerTick);
        }
    }
}

