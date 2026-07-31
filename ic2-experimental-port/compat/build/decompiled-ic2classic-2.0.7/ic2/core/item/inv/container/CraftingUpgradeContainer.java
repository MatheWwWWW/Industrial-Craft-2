/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 */
package ic2.core.item.inv.container;

import ic2.core.inventory.container.ItemContainer;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.item.inv.components.CraftingUpgradeComponent;
import ic2.core.item.inv.inventory.CraftingUpgradeInventory;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;

public class CraftingUpgradeContainer
extends ItemContainer<CraftingUpgradeInventory> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");

    public CraftingUpgradeContainer(CraftingUpgradeInventory key, Player player, int id, int windowID) {
        super(key, player, id, windowID);
        this.m_38897_(new LockedSlot(key, 9, 124, 35));
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.m_38897_(new LockedSlot.DragableLockedSlot(key, x + y * 3, 30 + x * 18, 17 + y * 18));
            }
        }
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new CraftingUpgradeComponent(key));
        this.m_38884_(key);
    }

    @Override
    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId >= 0 && this.m_38853_(slotId) instanceof LockedSlot.DragableLockedSlot) {
            LockedSlot.DragableLockedSlot slot = (LockedSlot.DragableLockedSlot)this.m_38853_(slotId);
            slot.onClick(clickTypeIn == ClickType.QUICK_CRAFT ? 1 : dragType, clickTypeIn == ClickType.QUICK_MOVE, clickTypeIn == ClickType.QUICK_CRAFT ? StackUtil.copyWithSize(this.m_142621_(), 1) : this.m_142621_());
            ((CraftingUpgradeInventory)this.getHolder()).clearResult();
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

