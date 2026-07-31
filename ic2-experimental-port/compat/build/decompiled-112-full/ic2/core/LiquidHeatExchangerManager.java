/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 */
package ic2.core;

import ic2.api.recipe.ILiquidAcceptManager;
import ic2.api.recipe.ILiquidHeatExchangerManager;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.util.LogCategory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class LiquidHeatExchangerManager
implements ILiquidHeatExchangerManager {
    private final boolean heatup;
    private final SingleDirectionManager singleDirectionManager;
    private Map<String, ILiquidHeatExchangerManager.HeatExchangeProperty> map = new HashMap<String, ILiquidHeatExchangerManager.HeatExchangeProperty>();

    public LiquidHeatExchangerManager(boolean heatup) {
        this.heatup = heatup;
        this.singleDirectionManager = new SingleDirectionManager();
    }

    @Override
    public boolean acceptsFluid(Fluid fluid) {
        return this.map.containsKey(fluid.getName());
    }

    @Override
    public Set<Fluid> getAcceptedFluids() {
        HashSet<Fluid> fluidSet = new HashSet<Fluid>();
        for (String fluidName : this.map.keySet()) {
            fluidSet.add(FluidRegistry.getFluid((String)fluidName));
        }
        return fluidSet;
    }

    @Override
    public void addFluid(String fluidName, String fluidOutput, int huPerMB) {
        if (this.map.containsKey(fluidName)) {
            this.displayError("The fluid " + fluidName + " does already have a HeatExchangerProperty assigned.");
            return;
        }
        if (huPerMB == 0) {
            this.displayError("A mod tried to register a Fluid for the HeatExchanging recipe, without having an Energy value. Ignoring...");
            return;
        }
        Fluid liquid1 = FluidRegistry.getFluid((String)fluidName);
        Fluid liquid2 = FluidRegistry.getFluid((String)fluidOutput);
        if (liquid1 == null || liquid2 == null) {
            this.displayError("Could not get both fluids for " + fluidName + " and " + fluidOutput + ".");
            return;
        }
        if (this.heatup) {
            if (liquid1.getTemperature() >= liquid2.getTemperature()) {
                this.displayError("Cannot heat up a warm liquid into a cold one. " + fluidName + " -> " + fluidOutput);
            }
        } else if (liquid1.getTemperature() <= liquid2.getTemperature()) {
            this.displayError("Cannot cool down a cold liquid into a warm one. " + fluidName + " -> " + fluidOutput);
        }
        this.map.put(fluidName, new ILiquidHeatExchangerManager.HeatExchangeProperty(FluidRegistry.getFluid((String)fluidOutput), Math.abs(huPerMB)));
    }

    @Override
    public ILiquidHeatExchangerManager.HeatExchangeProperty getHeatExchangeProperty(Fluid fluid) {
        if (this.map.containsKey(fluid.getName())) {
            return this.map.get(fluid.getName());
        }
        return null;
    }

    @Override
    public Map<String, ILiquidHeatExchangerManager.HeatExchangeProperty> getHeatExchangeProperties() {
        return this.map;
    }

    private void displayError(String msg) {
        if (!MainConfig.ignoreInvalidRecipes) {
            throw new RuntimeException(msg);
        }
        IC2.log.warn(LogCategory.Recipe, msg);
    }

    @Override
    public ILiquidAcceptManager getSingleDirectionLiquidManager() {
        return this.singleDirectionManager;
    }

    public ILiquidHeatExchangerManager getOpposite() {
        return this.heatup ? Recipes.liquidCooldownManager : Recipes.liquidHeatupManager;
    }

    public class SingleDirectionManager
    implements ILiquidAcceptManager {
        @Override
        public boolean acceptsFluid(Fluid fluid) {
            if (!LiquidHeatExchangerManager.this.acceptsFluid(fluid)) {
                return false;
            }
            ILiquidHeatExchangerManager.HeatExchangeProperty property = LiquidHeatExchangerManager.this.getHeatExchangeProperty(fluid);
            return !LiquidHeatExchangerManager.this.getOpposite().acceptsFluid(property.outputFluid);
        }

        @Override
        public Set<Fluid> getAcceptedFluids() {
            HashSet<Fluid> ret = new HashSet<Fluid>();
            ILiquidHeatExchangerManager opposite = LiquidHeatExchangerManager.this.getOpposite();
            for (Map.Entry e : LiquidHeatExchangerManager.this.map.entrySet()) {
                if (opposite.acceptsFluid(((ILiquidHeatExchangerManager.HeatExchangeProperty)e.getValue()).outputFluid)) continue;
                ret.add(FluidRegistry.getFluid((String)((String)e.getKey())));
            }
            return ret;
        }
    }
}

