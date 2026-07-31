/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableId;
import ic2.core.block.machine.tileentity.TileEntityBridgeNuke;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityNuke
extends TileEntityBridgeNuke
implements IHasGui {
    public int RadiationRange;
    public final InvSlotConsumable outsideSlot;
    public final InvSlotConsumable insideSlot = new InvSlotConsumableId(this, "insideSlot", 1, Ic2Items.URANIUM_BLOCK, Ic2Items.URANIUM_238, Ic2Items.URANIUM_235, Ic2Items.SMALL_URANIUM_235, Ic2Items.PLUTONIUM, Ic2Items.SMALL_PLUTONIUM);

    public TileEntityNuke(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityBridgeNuke>)Ic2BlockEntities.NUKE, blockPos, blockState);
        this.outsideSlot = new InvSlotConsumableId(this, "outsideSlot", 1, Ic2Items.ITNT);
    }

    @Override
    public int getRadiationRange() {
        return this.RadiationRange;
    }

    public void setRadiationRange(int n) {
        if (n != this.RadiationRange) {
            this.RadiationRange = n;
        }
    }

    @Override
    public float getNukeExplosivePower() {
        if (this.outsideSlot.isEmpty()) {
            return -1.0f;
        }
        int n = StackUtil.getSize(this.outsideSlot.get());
        double d = 5.0 * Math.pow(n, 0.3333333333333333);
        if (this.insideSlot.isEmpty()) {
            this.setRadiationRange(0);
        } else {
            ItemStack itemStack = this.insideSlot.get();
            Item item = itemStack.m_41720_();
            int n2 = StackUtil.getSize(itemStack);
            if (item == Ic2Items.URANIUM_238) {
                this.setRadiationRange(n);
            } else if (item == Ic2Items.URANIUM_BLOCK) {
                this.setRadiationRange(n * 6);
            } else if (item == Ic2Items.SMALL_URANIUM_235) {
                this.setRadiationRange(n * 2);
                if (n >= 64) {
                    d += 0.05555555555555555 * Math.pow(n2, 1.6);
                }
            } else if (item == Ic2Items.URANIUM_235) {
                this.setRadiationRange(n * 2);
                if (n >= 32) {
                    d += 0.5 * Math.pow(n2, 1.4);
                }
            } else if (item == Ic2Items.SMALL_PLUTONIUM) {
                this.setRadiationRange(n * 3);
                if (n >= 32) {
                    d += 0.05555555555555555 * Math.pow(n2, 2.0);
                }
            } else if (item == Ic2Items.PLUTONIUM) {
                this.setRadiationRange(n * 4);
                if (n >= 16) {
                    d += 0.5 * Math.pow(n2, 1.8);
                }
            }
        }
        d = Math.min(d, (double)ConfigUtil.getFloat(MainConfig.get(), "protection/nukeExplosionPowerLimit"));
        return (float)d;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }

    @Override
    protected void onIgnite(LivingEntity livingEntity) {
        super.onIgnite(livingEntity);
        this.outsideSlot.clear();
        this.insideSlot.clear();
    }
}

