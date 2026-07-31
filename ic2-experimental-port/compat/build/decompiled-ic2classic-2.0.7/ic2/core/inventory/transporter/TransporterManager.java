/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.Container
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraftforge.common.capabilities.CapabilityProvider
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.inventory.transporter;

import ic2.api.tiles.tubes.ITube;
import ic2.api.util.DirectionList;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.transporters.CapabilityTransporter;
import ic2.core.inventory.transporter.transporters.IHasInventoryTransporter;
import ic2.core.inventory.transporter.transporters.ItemHandlerTransporter;
import ic2.core.inventory.transporter.transporters.SidedTransporter;
import ic2.core.inventory.transporter.transporters.SimpleTransporter;
import ic2.core.inventory.transporter.transporters.TubeTransporter;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

public class TransporterManager {
    public static IItemTransporter getTransporter(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IHasInventory && !(obj instanceof CapabilityProvider)) {
            return new IHasInventoryTransporter((IHasInventory)obj);
        }
        if (obj instanceof IItemHandler) {
            return new ItemHandlerTransporter((IItemHandler)obj);
        }
        if (obj instanceof ITube) {
            return new TubeTransporter((ITube)obj);
        }
        if (obj instanceof CapabilityProvider && TransporterManager.hasItemHandler((ICapabilityProvider)obj)) {
            return new CapabilityTransporter((ICapabilityProvider)obj);
        }
        if (obj instanceof WorldlyContainer) {
            return new SidedTransporter((WorldlyContainer)obj);
        }
        if (obj instanceof Container) {
            return new SimpleTransporter((Container)obj);
        }
        return null;
    }

    private static boolean hasItemHandler(ICapabilityProvider handler) {
        for (Direction side : DirectionList.ALL) {
            if (!handler.getCapability(ForgeCapabilities.ITEM_HANDLER, side).isPresent()) continue;
            return true;
        }
        return false;
    }
}

