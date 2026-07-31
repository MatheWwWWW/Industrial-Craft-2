/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core;

import ic2.api.recipe.ILiquidAcceptManager;
import ic2.api.recipe.ILiquidHeatExchangerManager;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.fluid.FluidHandler;
import ic2.core.init.MainConfig;
import ic2.core.util.LogCategory;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.level.material.Fluid;

public class LiquidHeatExchangerManager
implements ILiquidHeatExchangerManager {
    private final boolean heatup;
    private final SingleDirectionManager singleDirectionManager;
    private final Map<Fluid, ILiquidHeatExchangerManager.HeatExchangeProperty> map = new IdentityHashMap<Fluid, ILiquidHeatExchangerManager.HeatExchangeProperty>();

    public LiquidHeatExchangerManager(boolean bl) {
        this.heatup = bl;
        this.singleDirectionManager = new SingleDirectionManager();
    }

    @Override
    public boolean acceptsFluid(Fluid fluid) {
        return this.map.containsKey(fluid);
    }

    @Override
    public Set<Fluid> getAcceptedFluids() {
        return Collections.unmodifiableSet(this.map.keySet());
    }

    @Override
    public void addFluid(Fluid fluid, Fluid fluid2, int n) {
        if (this.map.containsKey(fluid)) {
            this.displayError("The fluid " + fluid + " does already have a HeatExchangerProperty assigned.");
            return;
        }
        if (n == 0) {
            this.displayError("A mod tried to register a Fluid for the HeatExchanging recipe, without having an Energy value. Ignoring...");
            return;
        }
        if (this.heatup) {
            if (FluidHandler.getTemperature(fluid) >= FluidHandler.getTemperature(fluid2)) {
                this.displayError("Cannot heat up a warm liquid into a cold one. " + fluid + " -> " + fluid2);
            }
        } else if (FluidHandler.getTemperature(fluid) <= FluidHandler.getTemperature(fluid2)) {
            this.displayError("Cannot cool down a cold liquid into a warm one. " + fluid + " -> " + fluid2);
        }
        this.map.put(fluid, new ILiquidHeatExchangerManager.HeatExchangeProperty(fluid2, Math.abs(n)));
    }

    @Override
    public ILiquidHeatExchangerManager.HeatExchangeProperty getHeatExchangeProperty(Fluid fluid) {
        return this.map.get(fluid);
    }

    @Override
    public Map<Fluid, ILiquidHeatExchangerManager.HeatExchangeProperty> getHeatExchangeProperties() {
        return this.map;
    }

    private void displayError(String string) {
        if (!MainConfig.ignoreInvalidRecipes) {
            throw new RuntimeException(string);
        }
        IC2.log.warn(LogCategory.Recipe, string);
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
            ILiquidHeatExchangerManager.HeatExchangeProperty heatExchangeProperty = LiquidHeatExchangerManager.this.getHeatExchangeProperty(fluid);
            return !LiquidHeatExchangerManager.this.getOpposite().acceptsFluid(heatExchangeProperty.outputFluid);
        }

        @Override
        public Set<Fluid> getAcceptedFluids() {
            HashSet<Fluid> hashSet = new HashSet<Fluid>();
            ILiquidHeatExchangerManager iLiquidHeatExchangerManager = LiquidHeatExchangerManager.this.getOpposite();
            for (Map.Entry<Fluid, ILiquidHeatExchangerManager.HeatExchangeProperty> entry : LiquidHeatExchangerManager.this.map.entrySet()) {
                if (iLiquidHeatExchangerManager.acceptsFluid(entry.getValue().outputFluid)) continue;
                hashSet.add(entry.getKey());
            }
            return hashSet;
        }
    }
}

