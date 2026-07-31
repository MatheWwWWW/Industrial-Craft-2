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
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.personal.container;

import ic2.core.block.personal.components.FluidOMatClosedComponent;
import ic2.core.block.personal.components.FluidOMatOpenComponent;
import ic2.core.block.personal.tile.FluidOMatTileEntity;
import ic2.core.block.personal.trade.FluidTrade;
import ic2.core.block.personal.trade.Trade;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.gui.components.simple.TankComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.FluidSlot;
import ic2.core.inventory.slot.GhostSlot;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidOMatContainer
extends ContainerComponent<FluidOMatTileEntity> {
    public static final ResourceLocation OPEN_TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/personal/gui_fluid_o_mat_open.png");
    public static final ResourceLocation CLOSE_TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/personal/gui_fluid_o_mat_close.png");
    public static final Box2i OFFER_BOX = new Box2i(40, 21, 16, 58);
    public static final Box2i PAYED_BOX = new Box2i(120, 21, 16, 58);
    final boolean open;

    public FluidOMatContainer(FluidOMatTileEntity key, Player player, int id, boolean open) {
        super(key, player, id);
        this.open = open;
        if (open) {
            this.getComponents().clear();
            Object trade = key.getTrade();
            for (int i = 0; i < 4; ++i) {
                this.m_38897_(new GhostSlot(((Trade)trade).neededItems, i, 98 + i % 2 * 18, 18 + i / 2 * 18, SpecialFilters.ANY_ITEM));
            }
            this.m_38897_(new FluidSlot(key.helper, 0, 107, 69));
            this.addComponent(new FilterComponent(this.getInventoryOffset()).setFluid());
            this.addComponent(new FluidOMatOpenComponent(key));
            this.addComponent(new TankComponent(new Box2i(143, 27, 16, 58), (IFluidTank)((FluidTrade)key.getTrade()).offered).setTankName("Offering Tank:"));
            this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 22);
        } else {
            int i;
            this.disablePreviewer();
            for (i = 0; i < 4; ++i) {
                this.m_38897_(new LockedSlot(((FluidTrade)key.getTrade()).neededItems, i, 8, 16 + i * 18));
            }
            for (i = 0; i < 4; ++i) {
                this.m_38897_(new SlotBase(key, i, 62, 16 + i * 18));
            }
            for (i = 0; i < 4; ++i) {
                this.m_38897_(FilterSlot.createOutputSlot(key, i + 4, 98, 16 + i * 18));
            }
            this.m_38897_(FilterSlot.createFluidFillSlot((IHasInventory)key, 8, 152, 21, (IFluidHandler)key.tank));
            this.m_38897_(FilterSlot.createFluidOutputSlot(key, 9, 152, 63));
            this.addComponent(new TankComponent(OFFER_BOX, (IFluidTank)((FluidTrade)key.getTrade()).offered).setTankName("Offering Tank:"));
            this.addComponent(new TankComponent(PAYED_BOX, (IFluidTank)key.tank).setTankName("Output Tank:"));
            this.addComponent(new FluidOMatClosedComponent(key));
            this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 26);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return this.open ? OPEN_TEXTURE : CLOSE_TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.modifySize(0, this.open ? 21 : 26);
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return new Vec2i(0, -1);
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return new Vec2i(0, -1);
    }

    @Override
    public int getInventorySize() {
        return this.open ? 8 : 14;
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (this.open) {
            if (slotId >= 0 && slotId < 4) {
                ItemStack stack = this.m_142621_();
                FluidTrade trade = (FluidTrade)((FluidOMatTileEntity)this.getHolder()).getTrade();
                trade.neededItems.setStackInSlot(slotId, this.getStack(stack, this.m_38853_(slotId).m_7993_(), dragType, clickTypeIn == ClickType.QUICK_MOVE));
                return;
            }
            if (slotId == 4) {
                ((FluidOMatTileEntity)this.getHolder()).helper.setFluidInSlot(slotId, FluidHelper.getDisplayFluid(this.m_142621_()));
                return;
            }
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

