/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.block.machine.tileentity;

import ic2.api.energy.tile.IHeatSource;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2FluidTags;
import ic2.core.ref.Ic2Fluids;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

@NotClassic
public class TileEntitySteamRepressurizer
extends TileEntityInventory
implements IHasGui {
    private static Fluid detectedSteamFluid;
    protected int currentHeat;
    @GuiSynced
    protected final Ic2FluidTank output;
    @GuiSynced
    protected final Ic2FluidTank input;
    protected static final int CONSUMPTION = 10;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntitySteamRepressurizer(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.STEAM_REPRESSURIZER, blockPos, blockState);
        this.input = this.fluids.addTankInsert("input", 10000, Fluids.fluidPredicate(Ic2Fluids.STEAM.still, Ic2Fluids.SUPERHEATED_STEAM.still));
        this.output = this.fluids.addTankExtract("output", 10000);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.currentHeat = compoundTag.m_128451_("heat");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("heat", this.currentHeat);
    }

    public static boolean hasSteam() {
        return TileEntitySteamRepressurizer.getSteam() != null;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        Fluid fluid = TileEntitySteamRepressurizer.getSteam();
        if (fluid == null) {
            return;
        }
        if (this.input.getFluidAmount() >= 10) {
            if (this.currentHeat < this.input.getFluidAmount() / 10) {
                this.getHeat();
            }
            int n = this.getOutput();
            while (this.currentHeat > 0 && this.input.getFluidAmount() >= 10 && this.canOutput(n)) {
                --this.currentHeat;
                this.input.drainMbUnchecked(10, false);
                this.output.fillMbUnchecked(Ic2FluidStack.create(fluid, n), false);
            }
        }
    }

    protected void getHeat() {
        int n = this.input.getFluidAmount() / 10;
        if (n > 0) {
            IHeatSource iHeatSource;
            int n2;
            Direction direction;
            BlockEntity blockEntity;
            Level level = this.m_58904_();
            int n3 = n;
            Direction[] directionArray = Util.ALL_DIRS;
            int n4 = directionArray.length;
            for (int i = 0; !(i >= n4 || (blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction = directionArray[i]))) instanceof IHeatSource && (n2 = (iHeatSource = (IHeatSource)blockEntity).drawHeat(direction.m_122424_(), n3, true)) > 0 && (n3 -= iHeatSource.drawHeat(direction.m_122424_(), n2, false)) <= 0); ++i) {
            }
            this.currentHeat += n - n3;
        }
    }

    protected int getOutput() {
        assert (!this.input.isEmpty());
        Fluid fluid = this.input.getFluidStack().getFluid();
        if (fluid == Ic2Fluids.STEAM.still) {
            return ConfigUtil.getInt(MainConfig.get(), "balance/steamRepressurizer/steamPerSteam");
        }
        if (fluid == Ic2Fluids.SUPERHEATED_STEAM.still) {
            return ConfigUtil.getInt(MainConfig.get(), "balance/steamRepressurizer/steamPerSuperSteam");
        }
        throw new IllegalStateException("Unknown tank contents: " + fluid);
    }

    protected boolean canOutput(int n) {
        return this.output.fillMbUnchecked(Ic2FluidStack.create(TileEntitySteamRepressurizer.getSteam(), n), true) == n;
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
    public boolean getGuiState(String string) {
        if ("valid".equals(string)) {
            return TileEntitySteamRepressurizer.hasSteam();
        }
        return super.getGuiState(string);
    }

    private static Fluid getSteam() {
        Fluid fluid;
        block0: {
            Iterator iterator;
            fluid = detectedSteamFluid;
            if (fluid != null || !(iterator = Registry.f_122822_.m_206058_(Ic2FluidTags.STEAM).iterator()).hasNext()) break block0;
            Holder holder = (Holder)iterator.next();
            detectedSteamFluid = fluid = (Fluid)holder.m_203334_();
        }
        return fluid;
    }
}

