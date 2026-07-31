/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.api.tile;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface IWrenchable {
    public Direction getFacing(Level var1, BlockPos var2);

    default public boolean canSetFacing(Level level, BlockPos blockPos, Direction direction, Player player) {
        return true;
    }

    public boolean setFacing(Level var1, BlockPos var2, Direction var3, Player var4);

    public boolean wrenchCanRemove(Level var1, BlockPos var2, Player var3);

    public List<ItemStack> getWrenchDrops(Level var1, BlockPos var2, BlockState var3, BlockEntity var4, Player var5, int var6);
}

