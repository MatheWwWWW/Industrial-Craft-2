/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerCropHarvester;
import ic2.core.block.machine.gui.GuiCropHarvester;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.crop.TileEntityCrop;
import ic2.core.profile.NotClassic;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityCropHarvester
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock {
    public final InvSlot contentSlot = new InvSlot(this, "content", InvSlot.Access.IO, 15);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    public int scanX = -4;
    public int scanY = -1;
    public int scanZ = -4;

    public TileEntityCropHarvester() {
        super(10000, 1, false);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.upgradeSlot.tick();
        if (this.field_145850_b.func_82737_E() % 10L == 0L && this.energy.getEnergy() >= 21.0) {
            this.scan();
        }
    }

    public void scan() {
        TileEntityCrop crop;
        ++this.scanX;
        if (this.scanX > 4) {
            this.scanX = -4;
            ++this.scanZ;
            if (this.scanZ > 4) {
                this.scanZ = -4;
                ++this.scanY;
                if (this.scanY > 1) {
                    this.scanY = -1;
                }
            }
        }
        this.energy.useEnergy(1.0);
        World world = this.func_145831_w();
        TileEntity tileEntity = world.func_175625_s(this.field_174879_c.func_177982_a(this.scanX, this.scanY, this.scanZ));
        if (tileEntity instanceof TileEntityCrop && !this.isInvFull() && (crop = (TileEntityCrop)tileEntity).getCrop() != null) {
            List<ItemStack> drops = null;
            if (crop.getCurrentSize() == crop.getCrop().getOptimalHarvestSize(crop)) {
                drops = crop.performHarvest();
            } else if (crop.getCurrentSize() == crop.getCrop().getMaxSize()) {
                drops = crop.performHarvest();
            }
            if (drops != null) {
                drops.forEach(drop -> {
                    if (StackUtil.putInInventory((TileEntity)this, EnumFacing.WEST, drop, true) == 0) {
                        StackUtil.dropAsEntity(world, this.field_174879_c, drop);
                    } else {
                        StackUtil.putInInventory((TileEntity)this, EnumFacing.WEST, drop, false);
                    }
                    this.energy.useEnergy(20.0);
                });
            }
        }
    }

    private boolean isInvFull() {
        for (int i = 0; i < this.contentSlot.size(); ++i) {
            ItemStack stack = this.contentSlot.get(i);
            if (!StackUtil.isEmpty(stack) && StackUtil.getSize(stack) >= Math.min(stack.func_77976_d(), this.contentSlot.getStackSizeLimit())) continue;
            return false;
        }
        return true;
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double amount) {
        return this.energy.useEnergy(amount);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemProducing);
    }

    public ContainerBase<TileEntityCropHarvester> getGuiContainer(EntityPlayer player) {
        return new ContainerCropHarvester(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiCropHarvester(new ContainerCropHarvester(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }
}

