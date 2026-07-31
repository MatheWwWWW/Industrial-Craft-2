/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.material.Material
 */
package ic2.core.ref;

import ic2.core.IC2;
import ic2.core.fluid.EnvFluidHandler;
import ic2.core.fluid.FluidHandler;
import ic2.core.ref.IC2Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Material;

public final class Ic2Fluids {
    public static final EnvFluidHandler.FluidRefs UU_MATTER = Ic2Fluids.create("uu_matter", Material.f_76305_, 3000, 3000, 0, 300, false, "uu_matter", "uu_matter", -12909261);
    public static final EnvFluidHandler.FluidRefs CONSTRUCTION_FOAM = Ic2Fluids.create("construction_foam", Material.f_76305_, 10000, 50000, 0, 300, false, "fluid_3", "hot_coolant", -199089630);
    public static final EnvFluidHandler.FluidRefs COOLANT = Ic2Fluids.create("coolant", Material.f_76305_, 1000, 3000, 0, 300, false, "coolant", "coolant", -15443350);
    public static final EnvFluidHandler.FluidRefs CREOSOTE = Ic2Fluids.create("creosote", Material.f_76305_, 10000, 50000, 0, 300, false, "fluid", "fluid", -331005940);
    public static final EnvFluidHandler.FluidRefs HOT_COOLANT = Ic2Fluids.create("hot_coolant", Material.f_76305_, 1000, 3000, 0, 1200, false, "hot_coolant", "hot_coolant", -4904908);
    public static final EnvFluidHandler.FluidRefs PAHOEHOE_LAVA = Ic2Fluids.create("pahoehoe_lava", Material.f_76305_, 50000, 250000, 10, 1200, false, "pahoehoe_lava", null, -8686484);
    public static final EnvFluidHandler.FluidRefs BIOMASS = Ic2Fluids.create("biomass", Material.f_76305_, 1000, 3000, 0, 300, false, "fluid", "fluid", -1237485016);
    public static final EnvFluidHandler.FluidRefs BIOGAS = Ic2Fluids.create("biogas", Material.f_76305_, 1000, 3000, 0, 300, true, "fluid_3", null, -188435879);
    public static final EnvFluidHandler.FluidRefs DISTILLED_WATER = Ic2Fluids.create("distilled_water", Material.f_76305_, 1000, 1000, 0, 300, false, "fluid_water", "fluid_water", -632331785);
    public static final EnvFluidHandler.FluidRefs SUPERHEATED_STEAM = Ic2Fluids.create("superheated_steam", IC2Material.STEAM, -3000, 100, 0, 600, true, "fluid_3", null, -185797131);
    public static final EnvFluidHandler.FluidRefs STEAM = Ic2Fluids.create("steam", IC2Material.STEAM, -800, 300, 0, 420, true, "fluid_3", null, -186852132);
    public static final EnvFluidHandler.FluidRefs HOT_WATER = Ic2Fluids.create("hot_water", Material.f_76305_, 1000, 1000, 0, 350, false, "fluid_water", "fluid_water", -632884747);
    public static final EnvFluidHandler.FluidRefs WEED_EX = Ic2Fluids.create("weed_ex", Material.f_76305_, 1000, 1000, 0, 300, false, "weed_ex", null, -16298220);
    public static final EnvFluidHandler.FluidRefs AIR = Ic2Fluids.create("air", IC2Material.STEAM, 0, 500, 0, 300, true, "fluid_2", null, 0x5FFDFDFD);
    public static final EnvFluidHandler.FluidRefs HYDROGEN = Ic2Fluids.create("hydrogen", IC2Material.STEAM, 0, 500, 0, 300, true, "fluid_2", null, -2034379563);
    public static final EnvFluidHandler.FluidRefs OXYGEN = Ic2Fluids.create("oxygen", IC2Material.STEAM, 0, 500, 0, 300, true, "fluid_2", null, -2034581547);
    public static final EnvFluidHandler.FluidRefs HEAVY_WATER = Ic2Fluids.create("heavy_water", Material.f_76305_, 1000, 1000, 0, 300, false, "fluid", "fluid", -45191196);

    public static void init() {
    }

    private static EnvFluidHandler.FluidRefs create(String string, Material material, int n, int n2, int n3, int n4, boolean bl, String string2, String string3, int n5) {
        assert (n5 >>> 24 >= 8);
        ResourceLocation resourceLocation = IC2.getIdentifier("blocks/fluid/" + string2 + "_still");
        ResourceLocation resourceLocation2 = string3 != null ? IC2.getIdentifier("blocks/fluid/" + string3 + "_flow") : null;
        return FluidHandler.createFluid(IC2.getIdentifier(string), material, n, n2, n3, n4, bl, resourceLocation, resourceLocation2, n5);
    }
}

