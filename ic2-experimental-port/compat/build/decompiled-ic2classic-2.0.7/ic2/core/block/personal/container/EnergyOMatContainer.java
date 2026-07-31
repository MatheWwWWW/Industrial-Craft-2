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
package ic2.core.block.personal.container;

import ic2.core.block.personal.components.EnergyOMatClosedComponent;
import ic2.core.block.personal.components.EnergyOMatOpenComponent;
import ic2.core.block.personal.tile.EnergyOMatTileEntity;
import ic2.core.block.personal.trade.EnergyTrade;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.GhostSlot;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.inventory.slot.UpgradeSlot;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnergyOMatContainer
extends ContainerComponent<EnergyOMatTileEntity> {
    public static final ResourceLocation OPEN_TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/personal/gui_energy_o_mat_open.png");
    public static final ResourceLocation CLOSE_TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/personal/gui_energy_o_mat_close.png");
    final boolean open;

    public EnergyOMatContainer(EnergyOMatTileEntity key, Player player, int id, boolean open) {
        super(key, player, id);
        this.open = open;
        EnergyTrade trade = (EnergyTrade)key.getTrade();
        if (open) {
            int i;
            this.getComponents().clear();
            for (i = 0; i < 4; ++i) {
                this.m_38897_(new GhostSlot(trade.neededItems, i, i * 18 + 8, 18, SpecialFilters.ANY_ITEM));
            }
            for (i = 0; i < 2; ++i) {
                this.m_38897_(new UpgradeSlot(key, key.upgrades, i, 152, i * 18 + 18));
            }
            this.addComponent(new FilterComponent(this.getInventoryOffset()));
            this.addComponent(new EnergyOMatOpenComponent(key));
            this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 21);
        } else {
            int i;
            this.getComponents().clear();
            this.disablePreviewer();
            for (i = 0; i < 4; ++i) {
                this.m_38897_(new LockedSlot(trade.neededItems, i, i * 18 + 7, 21));
            }
            for (i = 0; i < 4; ++i) {
                this.m_38897_(new SlotBase(key, i, i % 2 * 18 + 26, 45 + i / 2 * 18));
            }
            for (i = 0; i < 4; ++i) {
                this.m_38897_(FilterSlot.createOutputSlot(key, 4 + i, 134 + i % 2 * 18, i / 2 * 18 + 45));
            }
            this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 22);
            this.addComponent(new EnergyOMatClosedComponent(key));
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.modifySize(0, this.open ? 21 : 22);
    }

    @Override
    public int getInventorySize() {
        return this.open ? 6 : 8;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.open ? OPEN_TEXTURE : CLOSE_TEXTURE;
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return new Vec2i(1, -13);
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return new Vec2i(1, -13);
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (this.open && slotId >= 0 && slotId < 4) {
            ItemStack stack = this.m_142621_();
            EnergyTrade trade = (EnergyTrade)((EnergyOMatTileEntity)this.getHolder()).getTrade();
            trade.neededItems.setStackInSlot(slotId, this.getStack(stack, this.m_38853_(slotId).m_7993_(), dragType, clickTypeIn == ClickType.QUICK_MOVE));
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }

    protected ItemStack getStack(ItemStack input, ItemStack present, int action, boolean half) {
        switch (action) {
            case 0: {
                if (present.m_41619_() && !input.m_41619_()) {
                    return StackUtil.copyWithSize(input, half ? input.m_41741_() : input.m_41613_());
                }
                if (!present.m_41619_()) {
                    present.m_41774_(half && present.m_41613_() > 1 ? present.m_41613_() / 2 : 1);
                }
                return present;
            }
            case 1: {
                if (present.m_41619_() && !input.m_41619_()) {
                    return StackUtil.copyWithSize(input, half ? 1 : input.m_41613_());
                }
                if (!present.m_41619_()) {
                    present.m_41764_(Math.min(present.m_41741_(), present.m_41613_() + (half && present.m_41613_() > 1 ? present.m_41613_() / 2 : 1)));
                }
                return present;
            }
            case 2: {
                return ItemStack.f_41583_;
            }
        }
        return present;
    }
}

