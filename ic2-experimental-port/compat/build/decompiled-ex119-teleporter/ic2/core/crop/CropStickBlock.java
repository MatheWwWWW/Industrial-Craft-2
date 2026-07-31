/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.crop;

import ic2.core.ref.Ic2Items;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CropStickBlock
extends Block {
    public static final BooleanProperty CROSSING_BASE = BooleanProperty.m_61465_((String)"crossing_base");

    public CropStickBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{CROSSING_BASE});
    }

    @Nullable
    public BlockState m_5573_(BlockPlaceContext blockPlaceContext) {
        return (BlockState)Objects.requireNonNull(super.m_5573_(blockPlaceContext)).m_61124_((Property)CROSSING_BASE, (Comparable)Boolean.valueOf(false));
    }

    public InteractionResult m_6227_(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.m_21120_(interactionHand);
        if (itemStack.m_150930_(Ic2Items.CROP_STICK)) {
            level.m_46597_(blockPos, (BlockState)blockState.m_61124_((Property)CROSSING_BASE, (Comparable)Boolean.valueOf(true)));
            return InteractionResult.SUCCESS;
        }
        return super.m_6227_(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}

