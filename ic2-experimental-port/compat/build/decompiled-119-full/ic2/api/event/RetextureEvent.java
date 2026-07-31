/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.event.level.LevelEvent
 *  net.minecraftforge.eventbus.api.Cancelable
 */
package ic2.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class RetextureEvent
extends LevelEvent {
    public final BlockPos pos;
    public final BlockState state;
    public final Direction side;
    public final Player player;
    public final BlockState refState;
    public final String refVariant;
    public final Direction refSide;
    public final int[] refColorMultipliers;
    public boolean applied = false;

    public RetextureEvent(Level level, BlockPos blockPos, BlockState blockState, Direction direction, Player player, BlockState blockState2, String string, Direction direction2, int[] nArray) {
        super((LevelAccessor)level);
        if (level == null) {
            throw new NullPointerException("null world");
        }
        if (level.f_46443_) {
            throw new IllegalStateException("remote world");
        }
        if (blockPos == null) {
            throw new NullPointerException("null pos");
        }
        if (blockState == null) {
            throw new NullPointerException("null state");
        }
        if (direction == null) {
            throw new NullPointerException("null side");
        }
        if (blockState2 == null) {
            throw new NullPointerException("null refState");
        }
        if (string == null) {
            throw new NullPointerException("null refVariant");
        }
        if (direction2 == null) {
            throw new NullPointerException("null refSide");
        }
        if (nArray == null) {
            throw new NullPointerException("null refColorMultipliers");
        }
        this.pos = blockPos;
        this.state = blockState;
        this.side = direction;
        this.player = player;
        this.refState = blockState2;
        this.refVariant = string;
        this.refSide = direction2;
        this.refColorMultipliers = nArray;
    }
}

