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
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.wiring.tileentity;

import ic2.api.energy.EnergyNet;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.block.wiring.ContainerTransformer;
import ic2.core.init.Localization;
import ic2.core.network.GrowingBuffer;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityTransformer
extends TileEntityInventory
implements IHasGui,
INetworkClientTileEntityEventListener {
    private static final Mode defaultMode = Mode.redstone;
    private double inputFlow = 0.0;
    private double outputFlow = 0.0;
    private final int defaultTier;
    protected final Energy energy;
    private Mode configuredMode = defaultMode;
    private Mode transformMode = null;

    public TileEntityTransformer(BlockEntityType<? extends TileEntityTransformer> blockEntityType, BlockPos blockPos, BlockState blockState, int n) {
        super((BlockEntityType<? extends TileEntityInventory>)blockEntityType, blockPos, blockState);
        this.defaultTier = n;
        this.energy = this.addComponent(new Energy(this, EnergyNet.instance.getPowerFromTier(n) * 8.0, Collections.emptySet(), Collections.emptySet(), n, n, true).setMultiSource(true));
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        int n = compoundTag.m_128451_("mode");
        this.configuredMode = n >= 0 && n < Mode.VALUES.length ? Mode.VALUES[n] : defaultMode;
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("mode", this.configuredMode.ordinal());
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.m_58904_().f_46443_) {
            this.updateRedstone(true);
        }
    }

    public Mode getMode() {
        return this.configuredMode;
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        if (n >= 0 && n < Mode.VALUES.length) {
            this.configuredMode = Mode.VALUES[n];
            this.updateRedstone(false);
        } else if (n == 3) {
            // empty if block
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.updateRedstone(false);
    }

    private void updateRedstone(boolean bl) {
        assert (!this.m_58904_().f_46443_);
        Mode mode = switch (this.configuredMode) {
            case Mode.redstone -> this.m_58904_().m_46753_(this.f_58858_) ? Mode.stepup : Mode.stepdown;
            case Mode.stepdown, Mode.stepup -> this.configuredMode;
            default -> throw new RuntimeException("invalid mode: " + this.configuredMode);
        };
        if (mode != Mode.stepup && mode != Mode.stepdown) {
            throw new RuntimeException("invalid mode: " + mode);
        }
        this.energy.setEnabled(true);
        if (bl || this.transformMode != mode) {
            this.transformMode = mode;
            this.setActive(this.isStepUp());
            if (this.isStepUp()) {
                this.energy.setSourceTier(this.defaultTier + 1);
                this.energy.setSinkTier(this.defaultTier);
                this.energy.setPacketOutput(1);
                this.energy.setDirections(EnumSet.complementOf(EnumSet.of(this.getFacing())), EnumSet.of(this.getFacing()));
            } else {
                this.energy.setSourceTier(this.defaultTier);
                this.energy.setSinkTier(this.defaultTier + 1);
                this.energy.setPacketOutput(4);
                this.energy.setDirections(EnumSet.of(this.getFacing()), EnumSet.complementOf(EnumSet.of(this.getFacing())));
            }
            this.outputFlow = EnergyNet.instance.getPowerFromTier(this.energy.getSourceTier());
            this.inputFlow = EnergyNet.instance.getPowerFromTier(this.energy.getSinkTier());
        }
    }

    @Override
    public void setFacing(Level level, Direction direction) {
        super.setFacing(level, direction);
        if (!this.m_58904_().f_46443_) {
            this.updateRedstone(true);
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> list, TooltipFlag tooltipFlag) {
        super.addInformation(itemStack, list, tooltipFlag);
        list.add(String.format("%s %.0f %s %s %.0f %s", Localization.translate("ic2.item.tooltip.Low"), EnergyNet.instance.getPowerFromTier(this.energy.getSinkTier()), Localization.translate("ic2.generic.text.EUt"), Localization.translate("ic2.item.tooltip.High"), EnergyNet.instance.getPowerFromTier(this.energy.getSourceTier() + 1), Localization.translate("ic2.generic.text.EUt")));
    }

    public ContainerBase<TileEntityTransformer> createServerScreenHandler(int n, Player player) {
        return new ContainerTransformer(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerTransformer(n, inventory, this);
    }

    public double getinputflow() {
        if (!this.isStepUp()) {
            return this.inputFlow;
        }
        return this.outputFlow;
    }

    public double getoutputflow() {
        if (this.isStepUp()) {
            return this.inputFlow;
        }
        return this.outputFlow;
    }

    private boolean isStepUp() {
        return this.transformMode == Mode.stepup;
    }

    public static enum Mode {
        redstone,
        stepdown,
        stepup;

        static final Mode[] VALUES;

        static {
            VALUES = Mode.values();
        }
    }
}

