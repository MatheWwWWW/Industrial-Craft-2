/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.api.tiles;

import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IMachine;
import ic2.api.tiles.tubes.ITube;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public class FakePlayerMachine
implements IMachine {
    static final EnumSet<IUpgradeItem.UpgradeType> DEFAULT = EnumSet.allOf(IUpgradeItem.UpgradeType.class);
    EnumSet<IUpgradeItem.UpgradeType> type;
    Player player;

    public FakePlayerMachine(Player player) {
        this(player, DEFAULT);
    }

    public FakePlayerMachine(Player player, EnumSet<IUpgradeItem.UpgradeType> type) {
        this.player = player;
        this.type = type;
    }

    @Override
    public int getValidRoom(ItemStack stack) {
        return 0;
    }

    @Override
    public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes() {
        return this.type;
    }

    @Override
    public int getAvailableEnergy() {
        return 0;
    }

    @Override
    public boolean useEnergy(int toUse, boolean doUse) {
        return false;
    }

    @Override
    public boolean isMachineWorking() {
        return false;
    }

    @Override
    public boolean isRedstoneSensitive() {
        return false;
    }

    @Override
    public void setRedstoneSensitive(boolean flag) {
    }

    @Override
    public void onUpgradesChanged() {
    }

    @Override
    public IItemHandler getConnectedInventory(Direction dir) {
        return null;
    }

    @Override
    public ITube getConnectedTube(Direction dir) {
        return null;
    }

    @Override
    public Level getWorldObj() {
        return this.player.m_20193_();
    }

    @Override
    public BlockPos getPosition() {
        return this.player.m_20183_();
    }

    public static class FakeMachine
    implements IMachine {
        EnumSet<IUpgradeItem.UpgradeType> type;
        Level world;
        BlockPos pos;

        public FakeMachine(Level world, BlockPos pos) {
            this(DEFAULT, world, pos);
        }

        public FakeMachine(EnumSet<IUpgradeItem.UpgradeType> type, Level world, BlockPos pos) {
            this.type = type;
            this.world = world;
            this.pos = pos;
        }

        @Override
        public int getValidRoom(ItemStack stack) {
            return 0;
        }

        @Override
        public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes() {
            return this.type;
        }

        @Override
        public int getAvailableEnergy() {
            return 0;
        }

        @Override
        public boolean useEnergy(int toUse, boolean doUse) {
            return false;
        }

        @Override
        public boolean isMachineWorking() {
            return false;
        }

        @Override
        public boolean isRedstoneSensitive() {
            return false;
        }

        @Override
        public void setRedstoneSensitive(boolean flag) {
        }

        @Override
        public void onUpgradesChanged() {
        }

        @Override
        public IItemHandler getConnectedInventory(Direction dir) {
            return null;
        }

        @Override
        public ITube getConnectedTube(Direction dir) {
            return null;
        }

        @Override
        public Level getWorldObj() {
            return this.world;
        }

        @Override
        public BlockPos getPosition() {
            return this.pos;
        }
    }
}

