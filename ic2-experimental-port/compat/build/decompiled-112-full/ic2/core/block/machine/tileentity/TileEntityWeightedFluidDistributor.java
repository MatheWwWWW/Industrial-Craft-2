/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.ClientModifiable;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.machine.container.ContainerWeightedFluidDistributor;
import ic2.core.block.machine.gui.GuiWeightedFluidDistributor;
import ic2.core.block.machine.tileentity.IWeightedDistributor;
import ic2.core.block.machine.tileentity.TileEntityFluidDistributor;
import ic2.core.profile.NotClassic;
import ic2.core.util.LiquidUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityWeightedFluidDistributor
extends TileEntityFluidDistributor
implements IWeightedDistributor {
    @ClientModifiable
    protected List<EnumFacing> priority = new ArrayList<EnumFacing>(5);

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        if (!this.priority.isEmpty()) {
            int[] indexes = new int[this.priority.size()];
            for (int i = 0; i < indexes.length; ++i) {
                indexes[i] = this.priority.get(i).func_176745_a();
            }
            nbt.func_74783_a("priority", indexes);
        }
        return nbt;
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        int[] indexes = nbt.func_74759_k("priority");
        if (indexes.length > 0) {
            for (int index : indexes) {
                this.priority.add(EnumFacing.func_82600_a((int)index));
            }
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("priority");
        return ret;
    }

    @Override
    protected void updateConnectivity() {
        if (!this.func_145831_w().field_72995_K && !this.priority.isEmpty() && this.priority.remove(this.getFacing())) {
            this.updatePriority(true);
        }
        this.fluids.changeConnectivity(this.fluidTank, Collections.singleton(this.getFacing()), Collections.emptySet());
    }

    @Override
    protected void moveFluid() {
        if (!this.priority.isEmpty()) {
            int tankAmount = this.fluidTank.getFluidAmount();
            for (EnumFacing dir : this.priority) {
                int amount;
                EnumFacing side;
                assert (dir != this.getFacing());
                TileEntity target = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(dir));
                if (!LiquidUtil.isFluidTile(target, side = dir.func_176734_d()) || (amount = LiquidUtil.fillTile(target, side, this.fluidTank.getFluid(), false)) <= 0) continue;
                this.fluidTank.drainInternal(amount, true);
                if ((tankAmount -= amount) > 0) continue;
                break;
            }
        }
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer player) {
        return new ContainerWeightedFluidDistributor(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiWeightedFluidDistributor(new ContainerWeightedFluidDistributor(player, this));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public List<EnumFacing> getPriority() {
        return this.priority;
    }

    @Override
    public void updatePriority(boolean server) {
        IC2.network.get(server).updateTileEntityField(this, "priority");
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        int position = event / 10;
        EnumFacing facing = EnumFacing.func_82600_a((int)(event % 10 & 6));
        assert (position >= 0 && position <= this.priority.size()) : "Position was " + position;
        assert (facing != this.getFacing());
        if (position == this.priority.size()) {
            this.priority.add(facing);
        } else {
            this.priority.set(position, facing);
        }
    }
}

