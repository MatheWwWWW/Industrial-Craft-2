/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.container;

import ic2.core.inventory.container.ItemContainer;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.slot.FluidSlot;
import ic2.core.item.inv.inventory.FluidScannerInventory;
import ic2.core.utils.helpers.FluidHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FluidScannerContainer
extends ItemContainer<FluidScannerInventory> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/gui_fluid_filter_scanner.png");

    public FluidScannerContainer(FluidScannerInventory key, Player player, int id, int windowID) {
        super(key, player, id, windowID);
        for (int i = 0; i < 9; ++i) {
            this.m_38897_(new FluidSlot(key, i, 8 + 18 * i, 24));
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, -17);
        this.addComponent(new FilterComponent(this.getPreviewOffset()).setFluid());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.modifySize(0, -17);
    }

    @Override
    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId >= 0 && slotId < this.getInventorySize()) {
            ((FluidScannerInventory)this.getHolder()).setFluidInSlot(slotId, FluidHelper.getDisplayFluid(this.m_142621_()));
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

