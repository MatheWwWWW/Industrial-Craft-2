/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package ic2.core.block.misc;

import ic2.core.block.misc.WallBlock;
import ic2.core.ref.Ic2Blocks;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FoamBlock
extends Block {
    public static final EnumProperty<FoamType> typeProperty = EnumProperty.m_61587_((String)"type", FoamType.class);

    public FoamBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.m_49959_((BlockState)this.m_49966_().m_61124_(typeProperty, (Comparable)((Object)FoamType.normal)));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{typeProperty});
    }

    public VoxelShape m_5939_(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.m_83040_();
    }

    public void m_213898_(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        int n = serverLevel.m_46469_().m_46215_(GameRules.f_46143_);
        if (n <= 0) {
            throw new IllegalStateException("Foam was randomly ticked when world " + serverLevel + " is not ticking?");
        }
        FoamType foamType = (FoamType)((Object)blockState.m_61143_(typeProperty));
        float f = FoamBlock.getHardenChance((Level)serverLevel, blockPos, blockState, foamType) * 4096.0f / (float)n;
        if (randomSource.m_188501_() < f) {
            serverLevel.m_46597_(blockPos, ((FoamType)((Object)blockState.m_61143_(typeProperty))).getResult());
        }
    }

    public InteractionResult m_6227_(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (StackUtil.consume(player, interactionHand, StackUtil.sameItem((ItemLike)Blocks.f_49992_), 1)) {
            level.m_46597_(blockPos, ((FoamType)((Object)blockState.m_61143_(typeProperty))).getResult());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public static float getHardenChance(Level level, BlockPos blockPos, BlockState blockState, FoamType foamType) {
        int n = level.m_46803_(blockPos);
        if (blockState.m_60739_((BlockGetter)level, blockPos) == 0) {
            for (Direction direction : Util.ALL_DIRS) {
                n = Math.max(n, level.m_46803_(blockPos.m_121945_(direction)));
            }
        }
        int n2 = foamType.hardenTime * (16 - n);
        return 1.0f / (float)(n2 * 20);
    }

    public static enum FoamType implements StringRepresentable
    {
        normal(300),
        reinforced(600);

        public final int hardenTime;

        private FoamType(int n2) {
            this.hardenTime = n2;
        }

        public String m_7912_() {
            return this.name();
        }

        public List<ItemStack> getDrops() {
            switch (this) {
                case normal: {
                    return new ArrayList<ItemStack>();
                }
                case reinforced: {
                    ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
                    return arrayList;
                }
            }
            throw new UnsupportedOperationException();
        }

        public BlockState getResult() {
            switch (this) {
                case normal: {
                    return WallBlock.get(WallBlock.DEFAULT_COLOR).m_49966_();
                }
                case reinforced: {
                    return Ic2Blocks.REINFORCED_STONE.m_49966_();
                }
            }
            throw new UnsupportedOperationException();
        }
    }
}

