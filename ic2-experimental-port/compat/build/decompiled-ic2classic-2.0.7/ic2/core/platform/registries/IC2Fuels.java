/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.platform.registries;

import ic2.api.recipes.registries.IFluidFuelRegistry;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Items;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class IC2Fuels {
    public static final IC2Fuels INSTANCE = new IC2Fuels();

    @SubscribeEvent
    public void provideFuels(FurnaceFuelBurnTimeEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.m_150930_(IC2Items.CELL_LAVA)) {
            event.setBurnTime(20000);
        } else if (stack.m_150930_(IC2Items.SCRAP)) {
            event.setBurnTime(350);
        } else if (stack.m_150930_(IC2Blocks.CHARCOAL_BLOCK.m_5456_())) {
            event.setBurnTime(16000);
        } else if (stack.m_150930_(IC2Blocks.SCAFFOLD_WOOD.m_5456_()) || stack.m_150930_(IC2Blocks.RUBBERWOOD_FENCE.m_5456_()) || stack.m_150930_(IC2Blocks.RUBBERWOOD_FENCE_GATE.m_5456_())) {
            event.setBurnTime(300);
        }
    }

    public static void registerFuels(IFluidFuelRegistry registry) {
        registry.addFuel(IC2Fluids.WOOD_GAS, 250, 10);
        registry.addFuel(IC2Fluids.BIO_FUEL, 2000, 128);
        registry.addFuel(IC2Fluids.ALCOHOL, 24000, 50);
    }
}

