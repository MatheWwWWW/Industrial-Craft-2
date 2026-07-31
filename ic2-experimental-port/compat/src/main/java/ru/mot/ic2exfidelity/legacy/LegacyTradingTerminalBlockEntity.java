package ru.mot.ic2exfidelity.legacy;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.network.GrowingBuffer;
import java.util.Collections;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Exact 2.8.222 experimental Trading Terminal (`te#trading_terminal`). */
public final class LegacyTradingTerminalBlockEntity extends TileEntityInventory
        implements IHasGui, IUpgradableBlock {
    protected int range;
    public final InvSlotUpgrade rangeUpgrade = new InvSlotUpgrade(this, "range", 1);

    public LegacyTradingTerminalBlockEntity(BlockPos position, BlockState state) {
        super(RestoredLegacyContent.TRADING_TERMINAL_BLOCK_ENTITY.get(), position, state);
        rangeUpgrade.setStackSizeLimit(16);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        range = rangeUpgrade.getRemoteRange(512);
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        Level level = m_58904_();
        if (level != null && !level.f_46443_) {
            range = rangeUpgrade.getRemoteRange(512);
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        rangeUpgrade.tick();
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return new LegacyContainerTradingTerminal(syncId, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer buffer) {
        return new LegacyContainerTradingTerminal(syncId, inventory, this);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return Collections.singleton(UpgradableProperty.RemotelyAccessible);
    }

    @Override
    public double getEnergy() {
        return 0.0D;
    }

    @Override
    public boolean useEnergy(double amount) {
        return false;
    }
}
