/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.item.DyeItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.transport.item.container;

import ic2.core.block.transport.item.components.LimiterTubeComponent;
import ic2.core.block.transport.item.tubes.LimiterTubeTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.slot.GhostSlot;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LimiterTubeContainer
extends ContainerComponent<LimiterTubeTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/tubes/gui_limiter_tube.png");
    IHasInventory simple = new SimpleInventory(1);

    public LimiterTubeContainer(LimiterTubeTileEntity key, Player player, int id) {
        super(key, player, id);
        this.m_38897_(new GhostSlot(this.simple, 0, 152, 81, T -> T.m_41720_() instanceof DyeItem));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 18);
        this.addComponent(new LimiterTubeComponent(key, this.simple));
        this.addComponent(new FilterComponent(this.getPreviewOffset()));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setYSize(184);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public int getInventorySize() {
        return 1;
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId == 0) {
            ItemStack stack = this.m_142621_();
            this.simple.setStackInSlot(slotId, stack.m_41619_() ? ItemStack.f_41583_ : StackUtil.copyWithSize(stack, 1));
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }
}

