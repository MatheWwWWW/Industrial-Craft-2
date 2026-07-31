/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.mv;

import ic2.core.block.machines.components.mv.SimpleCropLibraryComponent;
import ic2.core.block.machines.tiles.mv.SimpleCropLibraryTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.utils.math.MathUtils;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SimpleCropLibraryContainer
extends ContainerComponent<SimpleCropLibraryTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_seed_library.png");
    public static final Vec2i OFFSET = new Vec2i(0, 101);
    public static final Vec2i COMPARATOR_OFFSET = new Vec2i(-11, 123);

    public SimpleCropLibraryContainer(SimpleCropLibraryTileEntity key, Player player, int id) {
        super(key, player, id);
        int x;
        int y;
        for (y = 0; y < 2; ++y) {
            for (x = 0; x < 2; ++x) {
                this.m_38897_(new FilterSlot(key, x + y * 2, 134 + x * 18, 101 + y * 18, SpecialFilters.CROP_FILTER_SCANNED));
            }
        }
        for (y = 0; y < 5; ++y) {
            for (x = 0; x < 5; ++x) {
                this.m_38897_(new LockedSlot(key.storage.getInventory(), x + y * 5, 87 + x * 17, 5 + y * 17));
            }
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 56);
        this.addComponent(new SimpleCropLibraryComponent(key));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setFlag(4);
        screen.setMaxSize(screen.getXSize(), 222);
        screen.setContainerOffset(-20, 85);
        screen.setPlayerInventoryOffset(10, 0);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return OFFSET;
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return COMPARATOR_OFFSET;
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId >= 0 && this.m_38853_(slotId) instanceof LockedSlot) {
            Slot slot = this.m_38853_(slotId);
            if (((SimpleCropLibraryTileEntity)this.getHolder()).isRendering() && slot.m_6657_()) {
                this.handleClick(slot.getSlotIndex());
            }
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void handleClick(int slot) {
        int amount;
        int n = amount = Screen.m_96638_() ? 64 : 1;
        if (Screen.m_96637_()) {
            amount = amount == 1 ? 8 : 24;
        }
        ((SimpleCropLibraryTileEntity)this.getHolder()).sendToServer(6, MathUtils.putInt(slot, amount));
    }
}

