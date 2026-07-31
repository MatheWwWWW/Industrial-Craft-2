/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.InventoryMenu
 */
package ic2.core.block.machines.containers.nv;

import ic2.core.block.machines.tiles.nv.MemoryExpansionTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.item.misc.MemoryStickItem;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;

public class CraftingExpansionContainer
extends ContainerComponent<MemoryExpansionTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_crafting_expansion.png");

    public CraftingExpansionContainer(MemoryExpansionTileEntity key, Player player, int id) {
        super(key, player, id);
        LockedSlot slot;
        int x;
        int y;
        this.m_38897_(new FilterSlot(key.inventory, 0, 80, 18, new ClassFilter(MemoryStickItem.class)));
        this.m_38897_(new FilterSlot(key.inventory, 1, 80, 54, new ClassFilter(MemoryStickItem.class)));
        for (y = 0; y < 3; ++y) {
            for (x = 0; x < 3; ++x) {
                slot = new LockedSlot(key.crafting, x + y * 3, 8 + 18 * x, 18 + 18 * y);
                slot.setBackground(InventoryMenu.f_39692_, new ResourceLocation("ic2:misc/gui/memory_stick"));
                this.m_38897_(slot);
            }
        }
        for (y = 0; y < 3; ++y) {
            for (x = 0; x < 3; ++x) {
                slot = new LockedSlot(key.crafting, 9 + x + y * 3, 116 + 18 * x, 18 + 18 * y);
                slot.setBackground(InventoryMenu.f_39692_, new ResourceLocation("ic2:misc/gui/memory_stick"));
                this.m_38897_(slot);
            }
        }
        this.addPlayerInventory(player.m_150109_());
    }

    @Override
    public int getInventorySize() {
        return 20;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return new Vec2i(0, -10);
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        super.m_150399_(slotId, dragType, clickTypeIn, player);
        this.m_38946_();
    }
}

