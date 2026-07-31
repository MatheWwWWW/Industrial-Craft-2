/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.fluids.IFluidTank
 */
package ic2.core.block.machines.containers.ev;

import ic2.core.block.base.tiles.impls.machine.multi.BaseColossalMachineTileEntity;
import ic2.core.block.machines.components.ev.ColossalMachineComponent;
import ic2.core.block.machines.tiles.ev.ColossalRecycler;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.TankComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.UpgradeSlot;
import ic2.core.inventory.slot.XPSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.IFluidTank;

public class ColossalMachineContainer
extends ContainerComponent<BaseColossalMachineTileEntity> {
    public static final Vec2i[][] SLOT_INPUTS = new Vec2i[][]{new Vec2i[0], new Vec2i[0], {new Vec2i(52, 23), new Vec2i(52, 55)}, new Vec2i[0], {new Vec2i(28, 30), new Vec2i(91, 30), new Vec2i(28, 49), new Vec2i(91, 49)}, new Vec2i[0], new Vec2i[0], new Vec2i[0], {new Vec2i(28, 19), new Vec2i(91, 19), new Vec2i(28, 38), new Vec2i(91, 38), new Vec2i(28, 57), new Vec2i(91, 57), new Vec2i(28, 76), new Vec2i(91, 76)}};
    public static final Vec2i[][] SLOT_OUTPUTS = new Vec2i[][]{new Vec2i[0], new Vec2i[0], {new Vec2i(109, 23), new Vec2i(109, 55)}, new Vec2i[0], {new Vec2i(70, 30), new Vec2i(133, 30), new Vec2i(70, 49), new Vec2i(133, 49)}, new Vec2i[0], new Vec2i[0], new Vec2i[0], {new Vec2i(70, 19), new Vec2i(133, 19), new Vec2i(70, 38), new Vec2i(133, 38), new Vec2i(70, 57), new Vec2i(133, 57), new Vec2i(70, 76), new Vec2i(133, 76)}};

    public ColossalMachineContainer(BaseColossalMachineTileEntity key, Player player, int id) {
        super(key, player, id);
        int size = key.getSlotsInUse();
        int[] inputs = key.getAllSlots(true);
        for (int i = 0; i < size; ++i) {
            int slot = inputs[i];
            Vec2i pos = SLOT_INPUTS[size][i];
            this.m_38897_(new FilterSlot(key, slot, pos.getX(), pos.getY(), T -> key.canInsertInSlot(slot, T)));
        }
        int[] outputs = key.getAllSlots(false);
        for (int i = 0; i < size; ++i) {
            int slot = outputs[i];
            Vec2i pos = SLOT_OUTPUTS[size][i];
            this.m_38897_(new XPSlot(key, slot, pos.getX(), pos.getY()));
        }
        int offset = size == 8 ? 15 : 0;
        for (int i = 0; i < key.upgradeSlots; ++i) {
            this.m_38897_(new UpgradeSlot(key, key.inventorySize - key.upgradeSlots + i, 152 + (i - key.upgradeSlots + 1) * 18, 82 + offset));
        }
        this.addComponent(new ColossalMachineComponent(key));
        this.addComponent(new ChargebarComponent(new Box2i(118, 82 + offset, 14, 14), key, new Vec2i(176, 0), true));
        this.addComponent(new TankComponent(new Box2i(9, 19 + offset, 16, 58), new Vec2i(176, 31), (IFluidTank)key.waterTank));
        this.addComponent(new TankComponent(new Box2i(152, 19 + offset, 16, 58), new Vec2i(176, 31), (IFluidTank)key.steamTank));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 18 + offset);
    }

    @Override
    public int getInventorySize() {
        return ((BaseColossalMachineTileEntity)this.getHolder()).upgradeSlots + ((BaseColossalMachineTileEntity)this.getHolder()).getSlotsInUse() * 2;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.modifySize(0, ((BaseColossalMachineTileEntity)this.getHolder()).getSlotsInUse() == 8 ? 33 : 18);
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return ((BaseColossalMachineTileEntity)this.getHolder()).getSlotsInUse() != 8 ? new Vec2i(22, -11) : super.getPreviewButtonOffset();
    }

    @Override
    public ResourceLocation getTexture() {
        return ((BaseColossalMachineTileEntity)this.getHolder()).getGuiTexture();
    }

    @Override
    protected boolean m_38903_(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        if (this.getHolder() instanceof ColossalRecycler) {
            return this.moveItemStackToPriorizeUpgradeSlots(stack, startIndex, endIndex, reverseDirection);
        }
        return super.m_38903_(stack, startIndex, endIndex, reverseDirection);
    }
}

