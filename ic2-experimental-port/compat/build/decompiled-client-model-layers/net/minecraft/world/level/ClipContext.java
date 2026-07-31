/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ClipContext {
    private final Vec3 f_45682_;
    private final Vec3 f_45683_;
    private final Block f_45684_;
    private final Fluid f_45685_;
    private final CollisionContext f_45686_;

    public ClipContext(Vec3 p_45688_, Vec3 p_45689_, Block p_45690_, Fluid p_45691_, Entity p_45692_) {
        this.f_45682_ = p_45688_;
        this.f_45683_ = p_45689_;
        this.f_45684_ = p_45690_;
        this.f_45685_ = p_45691_;
        this.f_45686_ = CollisionContext.m_82750_(p_45692_);
    }

    public Vec3 m_45693_() {
        return this.f_45683_;
    }

    public Vec3 m_45702_() {
        return this.f_45682_;
    }

    public VoxelShape m_45694_(BlockState p_45695_, BlockGetter p_45696_, BlockPos p_45697_) {
        return this.f_45684_.m_7544_(p_45695_, p_45696_, p_45697_, this.f_45686_);
    }

    public VoxelShape m_45698_(FluidState p_45699_, BlockGetter p_45700_, BlockPos p_45701_) {
        return this.f_45685_.m_45731_(p_45699_) ? p_45699_.m_76183_(p_45700_, p_45701_) : Shapes.m_83040_();
    }

    public static final class Block
    extends Enum<Block>
    implements ShapeGetter {
        public static final /* enum */ Block COLLIDER = new Block(BlockBehaviour.BlockStateBase::m_60742_);
        public static final /* enum */ Block OUTLINE = new Block(BlockBehaviour.BlockStateBase::m_60651_);
        public static final /* enum */ Block VISUAL = new Block(BlockBehaviour.BlockStateBase::m_60771_);
        public static final /* enum */ Block FALLDAMAGE_RESETTING = new Block((p_201982_, p_201983_, p_201984_, p_201985_) -> {
            if (p_201982_.m_204336_(BlockTags.f_201924_)) {
                return Shapes.m_83144_();
            }
            return Shapes.m_83040_();
        });
        private final ShapeGetter f_45706_;
        private static final /* synthetic */ Block[] $VALUES;

        public static Block[] values() {
            return (Block[])$VALUES.clone();
        }

        public static Block valueOf(String p_45719_) {
            return Enum.valueOf(Block.class, p_45719_);
        }

        private Block(ShapeGetter p_45712_) {
            this.f_45706_ = p_45712_;
        }

        @Override
        public VoxelShape m_7544_(BlockState p_45714_, BlockGetter p_45715_, BlockPos p_45716_, CollisionContext p_45717_) {
            return this.f_45706_.m_7544_(p_45714_, p_45715_, p_45716_, p_45717_);
        }

        private static /* synthetic */ Block[] m_151407_() {
            return new Block[]{COLLIDER, OUTLINE, VISUAL, FALLDAMAGE_RESETTING};
        }

        static {
            $VALUES = Block.m_151407_();
        }
    }

    public static final class Fluid
    extends Enum<Fluid> {
        public static final /* enum */ Fluid NONE = new Fluid(p_45736_ -> false);
        public static final /* enum */ Fluid SOURCE_ONLY = new Fluid(FluidState::m_76170_);
        public static final /* enum */ Fluid ANY = new Fluid(p_45734_ -> !p_45734_.m_76178_());
        public static final /* enum */ Fluid WATER = new Fluid(p_201988_ -> p_201988_.m_205070_(FluidTags.f_13131_));
        private final Predicate<FluidState> f_45724_;
        private static final /* synthetic */ Fluid[] $VALUES;

        public static Fluid[] values() {
            return (Fluid[])$VALUES.clone();
        }

        public static Fluid valueOf(String p_45738_) {
            return Enum.valueOf(Fluid.class, p_45738_);
        }

        private Fluid(Predicate<FluidState> p_45730_) {
            this.f_45724_ = p_45730_;
        }

        public boolean m_45731_(FluidState p_45732_) {
            return this.f_45724_.test(p_45732_);
        }

        private static /* synthetic */ Fluid[] m_151408_() {
            return new Fluid[]{NONE, SOURCE_ONLY, ANY, WATER};
        }

        static {
            $VALUES = Fluid.m_151408_();
        }
    }

    public static interface ShapeGetter {
        public VoxelShape m_7544_(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4);
    }
}

