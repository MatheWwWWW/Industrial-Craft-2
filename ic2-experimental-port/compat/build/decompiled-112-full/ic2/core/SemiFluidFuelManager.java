/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 */
package ic2.core;

import ic2.api.recipe.ISemiFluidFuelManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class SemiFluidFuelManager
implements ISemiFluidFuelManager {
    private final Map<String, ISemiFluidFuelManager.FuelProperty> fuelProperties = new HashMap<String, ISemiFluidFuelManager.FuelProperty>();

    @Override
    public void addFluid(String fluidName, long energyPerMb, long energyPerTick) {
        if (this.fuelProperties.containsKey(fluidName)) {
            throw new RuntimeException("The fluid " + fluidName + " does already have a fuel property assigned.");
        }
        this.fuelProperties.put(fluidName, new ISemiFluidFuelManager.FuelProperty(energyPerMb, energyPerTick));
    }

    @Override
    public void removeFluid(String fluidName) {
        this.fuelProperties.remove(fluidName);
    }

    @Override
    public ISemiFluidFuelManager.FuelProperty getFuelProperty(Fluid fluid) {
        if (fluid == null) {
            return null;
        }
        return this.fuelProperties.get(fluid.getName());
    }

    @Override
    public boolean acceptsFluid(Fluid fluid) {
        return fluid != null && this.fuelProperties.containsKey(fluid.getName());
    }

    @Override
    public Set<Fluid> getAcceptedFluids() {
        HashSet<Fluid> ret = new HashSet<Fluid>(this.fuelProperties.size() * 2, 0.5f);
        for (String fluidName : this.fuelProperties.keySet()) {
            Fluid fluid = FluidRegistry.getFluid((String)fluidName);
            if (fluid == null) continue;
            ret.add(fluid);
        }
        return ret;
    }

    @Override
    public Map<String, ISemiFluidFuelManager.FuelProperty> getFuelProperties() {
        return Collections.unmodifiableMap(this.fuelProperties);
    }
}

