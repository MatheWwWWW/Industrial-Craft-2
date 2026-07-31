/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.entity.block;

import ic2.api.entity.block.ExplosiveEntity;
import ic2.core.item.tool.ItemToolWrench;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Entities;
import ic2.core.util.StackUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NukeEntity
extends ExplosiveEntity {
    public NukeEntity(Level level, double d, double d2, double d3, float f, int n) {
        super(Ic2Entities.NUKE, level, d, d2, d3, 300, f, 0.05f, 1.5f, Ic2Blocks.NUKE.m_49966_(), n);
    }

    public NukeEntity(EntityType<? extends NukeEntity> entityType, Level level) {
        this(level, 0.0, 0.0, 0.0, 1.0f, 1);
    }

    public InteractionResult m_6096_(Player player, InteractionHand interactionHand) {
        ItemToolWrench itemToolWrench;
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() instanceof ItemToolWrench && (itemToolWrench = (ItemToolWrench)itemStack.m_41720_()).canTakeDamage(itemStack, 1)) {
            if (this.f_19853_.f_46443_) {
                return InteractionResult.PASS;
            }
            itemToolWrench.damage(itemStack, 1, player, interactionHand);
            this.m_146870_();
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}

