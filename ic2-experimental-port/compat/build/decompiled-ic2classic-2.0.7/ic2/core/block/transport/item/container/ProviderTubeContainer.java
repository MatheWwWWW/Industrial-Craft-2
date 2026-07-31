/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.transport.item.container;

import ic2.core.block.transport.item.components.ProviderTubeComponent;
import ic2.core.block.transport.item.tubes.ProviderTubeTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.slot.GhostSlot;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ProviderTubeContainer
extends ContainerComponent<ProviderTubeTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/tubes/gui_provider_tube.png");

    public ProviderTubeContainer(ProviderTubeTileEntity key, Player player, int id) {
        super(key, player, id);
        SimpleInventory inv = new SimpleInventory(18);
        this.m_38897_(new GhostSlot(key.filterInv, 0, 8, 78, SpecialFilters.NO_TAGS));
        for (int i = 0; i < 18; ++i) {
            int xPos = i % 9;
            int yPos = i / 9;
            this.m_38897_(new LockedSlot(inv, i, 8 + xPos * 18, 17 + yPos * 18));
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 16);
        this.addComponent(new ProviderTubeComponent(key, inv));
        this.addComponent(new FilterComponent(this.getInventoryOffset()));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(177, 182);
        screen.setFlag(4);
        screen.clearFlag(1);
    }

    @Override
    public int getInventorySize() {
        return 0;
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId == 0) {
            ItemStack stack = this.m_142621_();
            ((ProviderTubeTileEntity)this.getHolder()).filterInv.setStackInSlot(slotId, stack.m_41619_() ? ItemStack.f_41583_ : StackUtil.copyWithSize(stack, 1));
            return;
        }
        if (slotId >= 1 && slotId < 19) {
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }
}

