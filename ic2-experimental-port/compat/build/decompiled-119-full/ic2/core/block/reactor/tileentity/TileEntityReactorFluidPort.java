/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.reactor.tileentity;

import com.google.common.base.Supplier;
import ic2.api.reactor.IReactorChamber;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.FluidReactorLookup;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityReactorFluidPort
extends TileEntityInventory
implements IHasGui,
IUpgradableBlock,
IReactorChamber {
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 1);
    private final FluidReactorLookup lookup = this.addComponent(new FluidReactorLookup(this));
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityReactorFluidPort(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.REACTOR_FLUID_PORT, blockPos, blockState);
        this.fluids.addUnmanagedTankHook((Supplier<? extends Collection<Fluids.InternalFluidTank>>)new Supplier<Collection<Fluids.InternalFluidTank>>(){

            public Collection<Fluids.InternalFluidTank> get() {
                TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = TileEntityReactorFluidPort.this.getReactorInstance();
                if (tileEntityNuclearReactorElectric == null) {
                    return Collections.emptySet();
                }
                return Arrays.asList(tileEntityNuclearReactorElectric.inputTank, tileEntityNuclearReactorElectric.outputTank);
            }
        });
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.upgradeSlot.tick();
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
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing);
    }

    @Override
    public double getEnergy() {
        return 40.0;
    }

    @Override
    public boolean useEnergy(double d) {
        return true;
    }

    @Override
    public TileEntityNuclearReactorElectric getReactorInstance() {
        return this.lookup.getReactor();
    }

    @Override
    public boolean isWall() {
        return true;
    }
}

