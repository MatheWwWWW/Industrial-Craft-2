/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.reactor.tileentity;

import com.google.common.base.Supplier;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.reactor.IReactorChamber;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.Redstone;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TileEntityReactorChamberElectric
extends Ic2TileEntity
implements Container,
IReactorChamber,
IEnergyEmitter {
    public final Redstone redstone = this.addComponent(new Redstone(this));
    protected final Fluids fluids = this.addComponent(new Fluids(this));
    private TileEntityNuclearReactorElectric reactor;
    private long lastReactorUpdate;

    public TileEntityReactorChamberElectric(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.REACTOR_CHAMBER, blockPos, blockState);
        this.fluids.addUnmanagedTankHook((Supplier<? extends Collection<Fluids.InternalFluidTank>>)new Supplier<Collection<Fluids.InternalFluidTank>>(){

            public Collection<Fluids.InternalFluidTank> get() {
                TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = TileEntityReactorChamberElectric.this.getReactor();
                if (tileEntityNuclearReactorElectric == null) {
                    return Collections.emptySet();
                }
                return Arrays.asList(tileEntityNuclearReactorElectric.inputTank, tileEntityNuclearReactorElectric.outputTank);
            }
        });
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateRedstoneLink();
    }

    private void updateRedstoneLink() {
        if (this.m_58904_().f_46443_) {
            return;
        }
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        if (tileEntityNuclearReactorElectric != null) {
            this.redstone.linkTo(tileEntityNuclearReactorElectric.redstone);
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        if (tileEntityNuclearReactorElectric != null) {
            TileEntityNuclearReactorElectric.showHeatEffects(this.m_58904_(), this.f_58858_, tileEntityNuclearReactorElectric.getHeat());
        }
    }

    @Override
    protected InteractionResult onActivated(Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        if (tileEntityNuclearReactorElectric != null) {
            Level level = this.m_58904_();
            return tileEntityNuclearReactorElectric.getBlockType().m_6227_(tileEntityNuclearReactorElectric.m_58900_(), level, tileEntityNuclearReactorElectric.m_58899_(), player, interactionHand, new BlockHitResult(vec3, direction, tileEntityNuclearReactorElectric.m_58899_(), false));
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void onNeighborChange(Block block, BlockPos blockPos) {
        super.onNeighborChange(block, blockPos);
        this.lastReactorUpdate = 0L;
        if (this.getReactor() == null) {
            this.destoryChamber(true);
        }
    }

    public void destoryChamber(boolean bl) {
        Level level = this.m_58904_();
        level.m_7471_(this.f_58858_, false);
        for (ItemStack itemStack : this.getSelfDrops(0, bl)) {
            StackUtil.dropAsEntity(level, this.f_58858_, itemStack);
        }
    }

    public int m_6643_() {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_6643_() : 0;
    }

    public boolean m_7983_() {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_7983_() : true;
    }

    public ItemStack m_8020_(int n) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_8020_(n) : null;
    }

    public ItemStack m_7407_(int n, int n2) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_7407_(n, n2) : null;
    }

    public ItemStack m_8016_(int n) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_8016_(n) : null;
    }

    public void m_6836_(int n, ItemStack itemStack) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        if (tileEntityNuclearReactorElectric != null) {
            tileEntityNuclearReactorElectric.m_6836_(n, itemStack);
        }
    }

    public int m_6893_() {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_6893_() : 0;
    }

    public boolean m_6542_(Player player) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_6542_(player) : false;
    }

    public void m_5856_(Player player) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        if (tileEntityNuclearReactorElectric != null) {
            tileEntityNuclearReactorElectric.m_5856_(player);
        }
    }

    public void m_5785_(Player player) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        if (tileEntityNuclearReactorElectric != null) {
            tileEntityNuclearReactorElectric.m_5785_(player);
        }
    }

    public boolean m_7013_(int n, ItemStack itemStack) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_7013_(n, itemStack) : false;
    }

    public void m_6211_() {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactor();
        if (tileEntityNuclearReactorElectric != null) {
            tileEntityNuclearReactorElectric.m_6211_();
        }
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, Direction direction) {
        return true;
    }

    @Override
    public TileEntityNuclearReactorElectric getReactorInstance() {
        return this.reactor;
    }

    @Override
    public boolean isWall() {
        return false;
    }

    private TileEntityNuclearReactorElectric getReactor() {
        long l = this.m_58904_().m_46467_();
        if (l != this.lastReactorUpdate) {
            this.updateReactor();
            this.lastReactorUpdate = l;
        } else if (this.reactor != null && this.reactor.m_58901_()) {
            this.reactor = null;
        }
        return this.reactor;
    }

    private void updateReactor() {
        Level level = this.m_58904_();
        this.reactor = null;
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (!(blockEntity instanceof TileEntityNuclearReactorElectric)) continue;
            this.reactor = (TileEntityNuclearReactorElectric)blockEntity;
            break;
        }
    }
}

