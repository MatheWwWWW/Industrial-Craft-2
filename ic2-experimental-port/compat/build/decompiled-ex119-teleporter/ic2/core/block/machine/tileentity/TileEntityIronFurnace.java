/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.MachineRecipeResult;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.invslot.InvSlotConsumableFuel;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessableSmelting;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.ParticleUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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

    public TileEntityIronFurnace(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.IRON_FURNACE, blockPos, blockState);
    }

    @Override
    protected void onUnloaded() {
        if (IC2.sideProxy.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
        super.onUnloaded();
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.fuel = compoundTag.m_128451_("fuel");
        this.totalFuel = compoundTag.m_128451_("totalFuel");
        this.progress = compoundTag.m_128448_("progress");
        this.xp = compoundTag.m_128459_("xp");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("fuel", this.fuel);
        compoundTag.m_128405_("totalFuel", this.totalFuel);
        compoundTag.m_128376_("progress", this.progress);
        compoundTag.m_128347_("xp", this.xp);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.fuel <= 0 && this.canOperate()) {
            this.fuel = this.totalFuel = this.fuelSlot.consumeFuel();
            if (this.fuel > 0) {
                bl = true;
            }
        }
        if (this.fuel > 0 && this.canOperate()) {
            this.progress = (short)(this.progress + 1);
            if (this.progress >= 160) {
                this.progress = 0;
                this.operate();
                bl = true;
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
        if (bl) {
            this.m_6596_();
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        if (this.getActive()) {
            Level level = this.m_58904_();
            ParticleUtil.showFurnaceFlames(level, this.f_58858_, this.getFacing());
            if (level.f_46441_.m_188500_() < 0.1) {
                level.m_7785_((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_() + 0.5, SoundEvents.f_11907_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
            }
        }
    }

    public static double spawnXP(Player player, double d) {
        int n;
        Level level = player.m_20193_();
        for (long i = (long)Math.floor(d); i > 0L; i -= (long)n) {
            n = i < 2477L ? ExperienceOrb.m_20782_((int)((int)i)) : 2477;
            level.m_7967_((Entity)new ExperienceOrb(level, player.m_20185_(), player.m_20186_() + 0.5, player.m_20189_() + 0.5, n));
        }
        return d - Math.floor(d);
    }

    private void operate() {
        MachineRecipeResult machineRecipeResult = this.inputSlot.process();
        ItemStack itemStack = (ItemStack)machineRecipeResult.getOutput();
        this.outputSlot.add(itemStack);
        this.inputSlot.consume(machineRecipeResult);
        this.xp += (double)machineRecipeResult.getRecipe().getMetaData().m_128457_("experience");
    }

    private boolean canOperate() {
        MachineRecipeResult machineRecipeResult = this.inputSlot.process();
        if (machineRecipeResult == null) {
            return false;
        }
        return this.outputSlot.canAdd((ItemStack)machineRecipeResult.getOutput());
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

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }

    @Override
    public double getGuiValue(String string) {
        if (string.equals("fuel")) {
            return this.fuel == 0 ? 0.0 : (double)this.fuel / (double)this.totalFuel;
        }
        if (string.equals("progress")) {
            return this.progress == 0 ? 0.0 : (double)this.progress / 160.0;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        if (n == 0) {
            assert (!this.m_58904_().f_46443_);
            this.xp = TileEntityIronFurnace.spawnXP(player, this.xp);
        }
    }

    @Override
    public void onNetworkUpdate(String string) {
        if (string.equals("active")) {
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
        super.onNetworkUpdate(string);
    }
}

