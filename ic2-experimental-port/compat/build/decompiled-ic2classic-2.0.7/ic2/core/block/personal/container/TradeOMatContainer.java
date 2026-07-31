/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.personal.container;

import ic2.core.block.personal.components.TradeOMatClosedComponent;
import ic2.core.block.personal.components.TradeOMatOpenComponent;
import ic2.core.block.personal.provider.IItemTradeProvider;
import ic2.core.block.personal.tile.TradeOMatTileEntity;
import ic2.core.block.personal.trade.ItemTrade;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.GhostSlot;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TradeOMatContainer
extends ContainerComponent<TradeOMatTileEntity> {
    public static final ResourceLocation OPEN_TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/personal/gui_trade_o_mat_open.png");
    public static final ResourceLocation CLOSE_TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/personal/gui_trade_o_mat_close.png");
    final boolean open;
    final boolean perPlayer;

    public TradeOMatContainer(TradeOMatTileEntity key, Player player, int id, boolean open) {
        super(key, player, id);
        this.open = open;
        this.perPlayer = ((ItemTrade)key.getTrade()).perPlayer;
        ItemTrade trade = (ItemTrade)key.getTrade();
        if (open) {
            int i;
            for (i = 0; i < 4; ++i) {
                this.m_38897_(new GhostSlot(trade.neededItems, i, i * 18 + 8, 18, SpecialFilters.ANY_ITEM));
            }
            for (i = 0; i < 4; ++i) {
                this.m_38897_(new GhostSlot(trade.offered, i, i * 18 + 98, 18, SpecialFilters.ANY_ITEM));
            }
            this.addComponent(new TradeOMatOpenComponent(key));
            this.addComponent(new FilterComponent(this.getInventoryOffset()));
            this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 7);
        } else {
            int i;
            TradeOMatTileEntity provider = key.isSimulating() && ((ItemTrade)key.getTrade()).perPlayer ? (IItemTradeProvider)key.getOrCreate(player) : key;
            this.disablePreviewer();
            for (i = 0; i < 4; ++i) {
                this.m_38897_(new LockedSlot(trade.neededItems, i, i * 18 + 7, 21));
            }
            for (i = 0; i < 4; ++i) {
                this.m_38897_(new LockedSlot(trade.offered, i, i * 18 + 99, 21));
            }
            for (i = 0; i < 4; ++i) {
                this.m_38897_(new SlotBase(provider.getTradeInput(), i, i % 2 * 18 + 26, 45 + i / 2 * 18));
            }
            for (i = 0; i < 8; ++i) {
                this.m_38897_(FilterSlot.createOutputSlot(provider.getTradeOutput(), i, i % 4 * 18 + 80, 45 + i / 4 * 18));
            }
            this.addComponent(new TradeOMatClosedComponent(key));
            this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 22);
        }
    }

    @Override
    public boolean m_6875_(Player playerIn) {
        return super.m_6875_(playerIn) && (this.open || this.perPlayer == ((ItemTrade)((TradeOMatTileEntity)this.getHolder()).getTrade()).perPlayer);
    }

    @Override
    public void m_6877_(Player playerIn) {
        if (!this.open && this.perPlayer) {
            IItemTradeProvider provider = (IItemTradeProvider)((TradeOMatTileEntity)this.getHolder()).getOrCreate(playerIn);
            this.clearContainer(playerIn, provider.getTradeInput());
            this.clearContainer(playerIn, provider.getTradeOutput());
        }
        super.m_6877_(playerIn);
    }

    protected void clearContainer(Player player, IHasInventory inventory) {
        if (!player.m_6084_() || player instanceof ServerPlayer && ((ServerPlayer)player).m_9232_()) {
            for (int j = 0; j < inventory.getSlotCount(); ++j) {
                player.m_36176_(inventory.getStackInSlot(j), false);
                inventory.setStackInSlot(j, ItemStack.f_41583_);
            }
        } else {
            for (int i = 0; i < inventory.getSlotCount(); ++i) {
                player.m_150109_().m_150079_(inventory.getStackInSlot(i));
                inventory.setStackInSlot(i, ItemStack.f_41583_);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return this.open ? OPEN_TEXTURE : CLOSE_TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.modifySize(0, this.open ? 7 : 22);
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return new Vec2i(1, -2);
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return new Vec2i(1, -2);
    }

    @Override
    public int getInventorySize() {
        return this.open ? 8 : 20;
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (this.open && slotId >= 0 && slotId < 8) {
            ItemStack stack = this.m_142621_();
            ItemTrade trade = (ItemTrade)((TradeOMatTileEntity)this.getHolder()).getTrade();
            if (slotId < 4) {
                trade.neededItems.setStackInSlot(slotId, this.getStack(stack, this.m_38853_(slotId).m_7993_(), dragType, clickTypeIn == ClickType.QUICK_MOVE));
            } else {
                trade.offered.setStackInSlot(slotId - 4, this.getStack(stack, this.m_38853_(slotId).m_7993_(), dragType, clickTypeIn == ClickType.QUICK_MOVE));
            }
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

