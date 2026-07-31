/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.BlockHitResult
 */
package ic2.core.block.base.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

public interface IClickable {
    default public ClickAction getRequiredActions() {
        return ClickAction.RIGHT_CLICK;
    }

    public boolean onRightClick(Player var1, InteractionHand var2, Direction var3, BlockHitResult var4);

    default public boolean onLeftClick(Player player, BlockPos pos) {
        return false;
    }

    public static enum ClickAction {
        NONE,
        RIGHT_CLICK,
        LEFT_CLICK,
        BOTH;


        public boolean canDoLeftClick() {
            return this == LEFT_CLICK || this == BOTH;
        }

        public boolean canDoRightClick() {
            return this == RIGHT_CLICK || this == BOTH;
        }
    }
}

