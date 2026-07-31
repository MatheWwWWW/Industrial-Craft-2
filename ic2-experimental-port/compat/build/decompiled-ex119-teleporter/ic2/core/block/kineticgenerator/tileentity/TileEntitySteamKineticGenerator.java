/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.kineticgenerator.tileentity;

import ic2.api.energy.tile.IKineticSource;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.Ic2Explosion;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.kineticgenerator.container.ContainerSteamKineticGenerator;
import ic2.core.block.machine.tileentity.TileEntityCondenser;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LiquidUtil;
import ic2.core.util.Util;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

@NotClassic
public class TileEntitySteamKineticGenerator
extends TileEntityInventory
implements IKineticSource,
IHasGui,
IUpgradableBlock {
    protected final Ic2FluidTank steamTank;
    protected final Ic2FluidTank distilledWaterTank;
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 1);
    public final InvSlotConsumable turbineSlot = new InvSlotConsumableItemStack(this, "Turbineslot", 1, new ItemStack((ItemLike)Ic2Items.STEAM_TURBINE));
    private static final float outputModifier = ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/steam");
    private int kUoutput;
    private boolean ventingSteam;
    private boolean throttled;
    private boolean isTurbineFilledWithWater = false;
    private int condensationProgress = 0;
    private int updateTicker = IC2.random.m_188503_(this.getTickRate());
    protected final Fluids fluids;

    public TileEntitySteamKineticGenerator(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.STEAM_KINETIC_GENERATOR, blockPos, blockState);
        this.turbineSlot.setStackSizeLimit(1);
        this.fluids = this.addComponent(new Fluids(this));
        this.steamTank = this.fluids.addTankInsert("steamTank", 21000, Fluids.fluidPredicate(Ic2Fluids.STEAM.still, Ic2Fluids.SUPERHEATED_STEAM.still));
        this.distilledWaterTank = this.fluids.addTank("distilledWaterTank", 1000, Fluids.fluidPredicate(new Fluid[]{Ic2Fluids.DISTILLED_WATER.still, net.minecraft.world.level.material.Fluids.f_76193_}));
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.distilledWaterTank.getCapacity() - this.distilledWaterTank.getFluidAmount() >= 1 && this.isTurbineFilledWithWater) {
            this.isTurbineFilledWithWater = false;
        }
        if (this.steamTank.getFluidAmount() > 0 && !this.isTurbineFilledWithWater && !this.turbineSlot.isEmpty()) {
            if (!this.getActive()) {
                this.setActive(true);
                bl = true;
            }
            boolean bl2 = this.steamTank.hasExactFluid(Ic2Fluids.SUPERHEATED_STEAM.still);
            boolean bl3 = this.turbineDoWork(bl2);
            if (this.updateTicker++ >= this.getTickRate()) {
                if (bl3) {
                    this.turbineSlot.damage(bl2 ? 1 : 2, false);
                }
                this.updateTicker = 0;
            }
        } else if (this.getActive()) {
            this.setActive(false);
            bl = true;
            this.kUoutput = 0;
        }
        if (bl |= this.upgradeSlot.tickNoMark()) {
            super.m_6596_();
        }
    }

    private int handleSteam(boolean bl) {
        int n = this.steamTank.getFluidAmount();
        assert (n > 0);
        this.steamTank.drainMbUnchecked(n, false);
        int n2 = n * 2 * (bl ? 2 : 1);
        if (bl) {
            this.outputSteam(n, true);
        } else {
            int n3 = n / 10;
            this.condensationProgress += n3;
            this.outputSteam(n - n3, false);
        }
        return n2;
    }

    private boolean turbineDoWork(boolean bl) {
        float f;
        int n = this.handleSteam(bl);
        int n2 = this.distilledWaterTank.getFluidAmount();
        if (n2 == 0) {
            this.throttled = false;
            f = 1.0f;
        } else {
            this.throttled = true;
            f = 1.0f - (float)n2 / (float)this.distilledWaterTank.getCapacity();
        }
        this.kUoutput = (int)((float)n * f * outputModifier);
        if (this.condensationProgress >= 100) {
            if (this.distilledWaterTank.fillMbUnchecked(Ic2FluidStack.create(Ic2Fluids.DISTILLED_WATER.still, 1), true) == 1) {
                this.condensationProgress -= 100;
                this.distilledWaterTank.fillMbUnchecked(Ic2FluidStack.create(Ic2Fluids.DISTILLED_WATER.still, 1), false);
            } else {
                this.isTurbineFilledWithWater = true;
            }
        }
        return this.kUoutput > 0;
    }

    private void outputSteam(int n, boolean bl) {
        int n2;
        Direction direction;
        BlockEntity blockEntity;
        Level level = this.m_58904_();
        Direction[] directionArray = Util.ALL_DIRS;
        int n3 = directionArray.length;
        for (int i = 0; !(i >= n3 || ((blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction = directionArray[i]))) instanceof TileEntityCondenser || bl && blockEntity instanceof TileEntitySteamKineticGenerator) && (n2 = LiquidUtil.fillTile(blockEntity, direction.m_122424_(), Ic2FluidStack.create(Ic2Fluids.STEAM.still, n), false)) > 0 && (n -= n2) <= 0); ++i) {
        }
        if (n > 0) {
            this.ventingSteam = true;
            if (level.f_46441_.m_188503_(10) == 0) {
                new Ic2Explosion(level, null, this.f_58858_, 1, 1.0f, Ic2Explosion.Type.Heat).doExplosion();
            }
        } else {
            this.ventingSteam = false;
        }
    }

    public int getKUoutput() {
        return this.kUoutput;
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.condensationProgress = compoundTag.m_128451_("condensationprogress");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("condensationprogress", this.condensationProgress);
    }

    public ContainerBase<TileEntitySteamKineticGenerator> createServerScreenHandler(int n, Player player) {
        return new ContainerSteamKineticGenerator(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerSteamKineticGenerator(n, inventory, this);
    }

    @Override
    public int maxrequestkineticenergyTick(Direction direction) {
        return this.getConnectionBandwidth(direction);
    }

    @Override
    public int getConnectionBandwidth(Direction direction) {
        return direction == this.getFacing() ? this.kUoutput : 0;
    }

    @Override
    public int requestkineticenergy(Direction direction, int n) {
        return this.drawKineticEnergy(direction, n, false);
    }

    @Override
    public int drawKineticEnergy(Direction direction, int n, boolean bl) {
        return direction == this.getFacing() ? this.kUoutput : 0;
    }

    public int gaugeLiquidScaled(int n, int n2) {
        if (n2 == 0 && this.distilledWaterTank.getFluidAmount() > 0) {
            return this.distilledWaterTank.getFluidAmount() * n / this.distilledWaterTank.getCapacity();
        }
        return 0;
    }

    @Override
    public double getEnergy() {
        return 0.0;
    }

    @Override
    public boolean useEnergy(double d) {
        return false;
    }

    public int getDistilledWaterTankFill() {
        return this.distilledWaterTank.getFluidAmount();
    }

    public Ic2FluidTank getDistilledWaterTank() {
        return this.distilledWaterTank;
    }

    public boolean hasTurbine() {
        return !this.turbineSlot.isEmpty();
    }

    public boolean isVentingSteam() {
        return this.ventingSteam;
    }

    public boolean isThrottled() {
        return this.throttled;
    }

    public boolean isTurbineBlockedByWater() {
        return this.isTurbineFilledWithWater;
    }

    public int getTickRate() {
        return 20;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing);
    }
}

