/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 */
package ic2.core;

import ic2.api.recipe.IFluidHeatManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidHeatManager
implements IFluidHeatManager {
    private final Map<String, IFluidHeatManager.BurnProperty> burnProperties = new HashMap<String, IFluidHeatManager.BurnProperty>();

    @Override
    public void addFluid(String fluidName, int amount, int heat) {
        if (this.burnProperties.containsKey(fluidName)) {
            throw new RuntimeException("The fluid " + fluidName + " does already have a burn property assigned.");
        }
        this.burnProperties.put(fluidName, new IFluidHeatManager.BurnProperty(amount, heat));
    }

    @Override
    public IFluidHeatManager.BurnProperty getBurnProperty(Fluid fluid) {
        if (fluid == null) {
            return null;
        }
        return this.burnProperties.get(fluid.getName());
    }

    @Override
    public boolean acceptsFluid(Fluid fluid) {
        return this.burnProperties.containsKey(fluid.getName());
    }

    @Override
    public Set<Fluid> getAcceptedFluids() {
        HashSet<Fluid> ret = new HashSet<Fluid>();
        for (String fluidName : this.burnProperties.keySet()) {
            Fluid fluid = FluidRegistry.getFluid((String)fluidName);
            if (fluid == null) continue;
            ret.add(fluid);
        }
        return ret;
    }

    @Override
    public Map<String, IFluidHeatManager.BurnProperty> getBurnProperties() {
        return this.burnProperties;
    }
}

