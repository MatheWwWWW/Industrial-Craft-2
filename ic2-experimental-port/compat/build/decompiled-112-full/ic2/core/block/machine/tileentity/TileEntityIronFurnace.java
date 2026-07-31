/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumFacing$Axis
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.MachineRecipeResult;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotConsumableFuel;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessableSmelting;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GuiSynced;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityIronFurnace
extends TileEntityInventory
implements IHasGui,
IGuiValueProvider,
INetworkClientTileEntityEventListener {
    public final InvSlotProcessableSmelting inputSlot = new InvSlotProcessableSmelting(this, "input", 1);
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);
    public final InvSlotConsumableFuel fuelSlot = new InvSlotConsumableFuel(this, "fuel", 1, true);
    protected AudioSource audioSource;
    @GuiSynced
    public int fuel = 0;
    @GuiSynced
    public int totalFuel = 0;
    @GuiSynced
    public short progress = 0;
    protected double xp = 0.0;
    public static final short operationLength = 160;

    @Override
    protected void onUnloaded() {
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
        super.onUnloaded();
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.fuel = nbt.func_74762_e("fuel");
        this.totalFuel = nbt.func_74762_e("totalFuel");
        this.progress = nbt.func_74765_d("progress");
        this.xp = nbt.func_74769_h("xp");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("fuel", this.fuel);
        nbt.func_74768_a("totalFuel", this.totalFuel);
        nbt.func_74777_a("progress", this.progress);
        nbt.func_74780_a("xp", this.xp);
        return nbt;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        if (this.fuel <= 0 && this.canOperate()) {
            this.fuel = this.totalFuel = this.fuelSlot.consumeFuel();
            if (this.fuel > 0) {
                needsInvUpdate = true;
            }
        }
        if (this.fuel > 0 && this.canOperate()) {
            this.progress = (short)(this.progress + 1);
            if (this.progress >= 160) {
                this.progress = 0;
                this.operate();
                needsInvUpdate = true;
            }
        } else {
            this.progress = 0;
        }
        if (this.fuel > 0) {
            --this.fuel;
            this.setActive(true);
        } else {
            this.setActive(false);
        }
        if (needsInvUpdate) {
            this.func_70296_d();
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        if (this.getActive()) {
            World world = this.func_145831_w();
            TileEntityIronFurnace.showFlames(world, this.field_174879_c, this.getFacing());
            if (world.field_73012_v.nextDouble() < 0.1) {
                world.func_184134_a((double)this.field_174879_c.func_177958_n() + 0.5, (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p() + 0.5, SoundEvents.field_187652_bv, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            }
        }
    }

    public static void showFlames(World world, BlockPos pos, EnumFacing facing) {
        if (world.field_73012_v.nextInt(8) != 0) {
            return;
        }
        double width = 0.625;
        double height = 0.375;
        double depthOffset = 0.02;
        double x = (double)pos.func_177958_n() + ((double)facing.func_82601_c() * 1.04 + 1.0) / 2.0;
        double y = (double)pos.func_177956_o() + (double)world.field_73012_v.nextFloat() * 0.375;
        double z = (double)pos.func_177952_p() + ((double)facing.func_82599_e() * 1.04 + 1.0) / 2.0;
        if (facing.func_176740_k() == EnumFacing.Axis.X) {
            z += (double)world.field_73012_v.nextFloat() * 0.625 - 0.3125;
        } else {
            x += (double)world.field_73012_v.nextFloat() * 0.625 - 0.3125;
        }
        world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0, 0.0, 0.0, new int[0]);
        world.func_175688_a(EnumParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0, new int[0]);
    }

    public static double spawnXP(EntityPlayer player, double xp) {
        int amount;
        World world = player.func_130014_f_();
        for (long balls = (long)Math.floor(xp); balls > 0L; balls -= (long)amount) {
            amount = balls < 2477L ? EntityXPOrb.func_70527_a((int)((int)balls)) : 2477;
            world.func_72838_d((Entity)new EntityXPOrb(world, player.field_70165_t, player.field_70163_u + 0.5, player.field_70161_v + 0.5, amount));
        }
        return xp - Math.floor(xp);
    }

    private void operate() {
        MachineRecipeResult result = this.inputSlot.process();
        ItemStack output = (ItemStack)result.getOutput();
        this.outputSlot.add(output);
        this.inputSlot.consume(result);
        this.xp += (double)result.getRecipe().getMetaData().func_74760_g("experience");
    }

    private boolean canOperate() {
        MachineRecipeResult result = this.inputSlot.process();
        if (result == null) {
            return false;
        }
        return this.outputSlot.canAdd((ItemStack)result.getOutput());
    }

    public double getProgress() {
        return (double)this.progress / 160.0;
    }

    public double getFuelRatio() {
        if (this.fuel <= 0) {
            return 0.0;
        }
        return (double)this.fuel / (double)this.totalFuel;
    }

    public ContainerBase<TileEntityIronFurnace> getGuiContainer(EntityPlayer player) {
        return DynamicContainer.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return DynamicGui.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public double getGuiValue(String name) {
        if (name.equals("fuel")) {
            return this.fuel == 0 ? 0.0 : (double)this.fuel / (double)this.totalFuel;
        }
        if (name.equals("progress")) {
            return this.progress == 0 ? 0.0 : (double)this.progress / 160.0;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        if (event == 0) {
            assert (!this.func_145831_w().field_72995_K);
            this.xp = TileEntityIronFurnace.spawnXP(player, this.xp);
        }
    }

    @Override
    public void onNetworkUpdate(String field) {
        if (field.equals("active")) {
            if (this.audioSource == null) {
                this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/IronFurnaceOp.ogg", true, false, IC2.audioManager.getDefaultVolume());
            }
            if (this.getActive()) {
                if (this.audioSource != null) {
                    this.audioSource.play();
                }
            } else if (this.audioSource != null) {
                this.audioSource.stop();
            }
        }
        super.onNetworkUpdate(field);
    }
}

