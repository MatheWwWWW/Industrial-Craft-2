/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SkullBlock
extends AbstractSkullBlock {
    public static final int f_154563_ = 15;
    private static final int f_154564_ = 16;
    public static final IntegerProperty f_56314_ = BlockStateProperties.f_61390_;
    protected static final VoxelShape f_56315_ = Block.m_49796_(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

    protected SkullBlock(Type p_56318_, BlockBehaviour.Properties p_56319_) {
        super(p_56318_, p_56319_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_56314_, 0));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_56331_, BlockGetter p_56332_, BlockPos p_56333_, CollisionContext p_56334_) {
        return f_56315_;
    }

    @Override
    public VoxelShape m_7952_(BlockState p_56336_, BlockGetter p_56337_, BlockPos p_56338_) {
        return Shapes.m_83040_();
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_56321_) {
        return (BlockState)this.m_49966_().m_61124_(f_56314_, Mth.m_14107_((double)(p_56321_.m_7074_() * 16.0f / 360.0f) + 0.5) & 0xF);
    }

    @Override
    public BlockState m_6843_(BlockState p_56326_, Rotation p_56327_) {
        return (BlockState)p_56326_.m_61124_(f_56314_, p_56327_.m_55949_(p_56326_.m_61143_(f_56314_), 16));
    }

    @Override
    public BlockState m_6943_(BlockState p_56323_, Mirror p_56324_) {
        return (BlockState)p_56323_.m_61124_(f_56314_, p_56324_.m_54843_(p_56323_.m_61143_(f_56314_), 16));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56329_) {
        p_56329_.m_61104_(f_56314_);
    }

    public static interface Type {
    }

    public static final class Types
    extends Enum<Types>
    implements Type {
        public static final /* enum */ Types SKELETON = new Types();
        public static final /* enum */ Types WITHER_SKELETON = new Types();
        public static final /* enum */ Types PLAYER = new Types();
        public static final /* enum */ Types ZOMBIE = new Types();
        public static final /* enum */ Types CREEPER = new Types();
        public static final /* enum */ Types DRAGON = new Types();
        private static final /* synthetic */ Types[] $VALUES;

        public static Types[] values() {
            return (Types[])$VALUES.clone();
        }

        public static Types valueOf(String p_56351_) {
            return Enum.valueOf(Types.class, p_56351_);
        }

        private static /* synthetic */ Types[] m_154565_() {
            return new Types[]{SKELETON, WITHER_SKELETON, PLAYER, ZOMBIE, CREEPER, DRAGON};
        }

        static {
            $VALUES = Types.m_154565_();
        }
    }
}

