/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.container;

import ic2.core.inventory.container.ItemContainer;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.item.inv.inventory.ToolBoxInventory;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ToolBoxContainer
extends ItemContainer<ToolBoxInventory> {
    public static final Vec2i OFFSET = new Vec2i(0, -11);

    public ToolBoxContainer(ToolBoxInventory key, Player player, int id, int windowID) {
        super(key, player, id, windowID);
        int columns = key.getColumns();
        int slotX = key.getStartX();
        for (int i = 0; i < key.getSlotCount(); ++i) {
            int x = i % columns;
            int y = i / columns;
            this.m_38897_(new FilterSlot(key, i, slotX + 18 * x, 18 + 18 * y, key.getFilter()));
        }
        Vec2i offset = key.getOffset();
        this.addPlayerInventoryWithOffset(player.m_150109_(), offset.getX(), offset.getY());
    }

    @Override
    public ResourceLocation getTexture() {
        return ((ToolBoxInventory)this.getHolder()).getTexture();
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return OFFSET;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        Vec2i offset = ((ToolBoxInventory)this.getHolder()).getOffset();
        screen.modifySize(offset.getX(), offset.getY());
    }
}

