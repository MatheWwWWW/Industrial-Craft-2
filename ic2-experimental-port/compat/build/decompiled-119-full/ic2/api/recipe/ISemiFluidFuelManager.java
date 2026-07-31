/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.api.recipe;

import ic2.api.recipe.ILiquidAcceptManager;
import java.util.Map;
import net.minecraft.world.level.material.Fluid;

public interface ISemiFluidFuelManager
extends ILiquidAcceptManager {
    public void addFluid(Fluid var1, int var2, double var3);

    public BurnProperty getBurnProperty(Fluid var1);

    public Map<Fluid, BurnProperty> getBurnProperties();

    public static final class BurnProperty {
        public final int amount;
        public final double power;

        public BurnProperty(int n, double d) {
            this.amount = n;
            this.power = d;
        }
    }
}

