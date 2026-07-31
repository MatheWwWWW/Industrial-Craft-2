/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;

public class PressurePlateBlock
extends BasePressurePlateBlock {
    public static final BooleanProperty f_55249_ = BlockStateProperties.f_61448_;
    private final Sensitivity f_55250_;

    protected PressurePlateBlock(Sensitivity p_55253_, BlockBehaviour.Properties p_55254_) {
        super(p_55254_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55249_, false));
        this.f_55250_ = p_55253_;
    }

    @Override
    protected int m_6016_(BlockState p_55270_) {
        return p_55270_.m_61143_(f_55249_) != false ? 15 : 0;
    }

    @Override
    protected BlockState m_7422_(BlockState p_55259_, int p_55260_) {
        return (BlockState)p_55259_.m_61124_(f_55249_, p_55260_ > 0);
    }

    @Override
    protected void m_5494_(LevelAccessor p_55256_, BlockPos p_55257_) {
        if (this.f_60442_ == Material.f_76320_ || this.f_60442_ == Material.f_76321_) {
            p_55256_.m_5594_(null, p_55257_, SoundEvents.f_12637_, SoundSource.BLOCKS, 0.3f, 0.8f);
        } else {
            p_55256_.m_5594_(null, p_55257_, SoundEvents.f_12449_, SoundSource.BLOCKS, 0.3f, 0.6f);
        }
    }

    @Override
    protected void m_5493_(LevelAccessor p_55267_, BlockPos p_55268_) {
        if (this.f_60442_ == Material.f_76320_ || this.f_60442_ == Material.f_76321_) {
            p_55267_.m_5594_(null, p_55268_, SoundEvents.f_12636_, SoundSource.BLOCKS, 0.3f, 0.7f);
        } else {
            p_55267_.m_5594_(null, p_55268_, SoundEvents.f_12448_, SoundSource.BLOCKS, 0.3f, 0.5f);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    protected int m_6693_(Level p_55264_, BlockPos p_55265_) {
        void $$5;
        AABB $$2 = f_49287_.m_82338_(p_55265_);
        switch (this.f_55250_) {
            case EVERYTHING: {
                List<Entity> $$3 = p_55264_.m_45933_(null, $$2);
                break;
            }
            case MOBS: {
                List<LivingEntity> $$4 = p_55264_.m_45976_(LivingEntity.class, $$2);
                break;
            }
            default: {
                return 0;
            }
        }
        if (!$$5.isEmpty()) {
            for (Entity $$6 : $$5) {
                if ($$6.m_6090_()) continue;
                return 15;
            }
        }
        return 0;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55262_) {
        p_55262_.m_61104_(f_55249_);
    }

    public static final class Sensitivity
    extends Enum<Sensitivity> {
        public static final /* enum */ Sensitivity EVERYTHING = new Sensitivity();
        public static final /* enum */ Sensitivity MOBS = new Sensitivity();
        private static final /* synthetic */ Sensitivity[] $VALUES;

        public static Sensitivity[] values() {
            return (Sensitivity[])$VALUES.clone();
        }

        public static Sensitivity valueOf(String p_55281_) {
            return Enum.valueOf(Sensitivity.class, p_55281_);
        }

        private static /* synthetic */ Sensitivity[] m_154297_() {
            return new Sensitivity[]{EVERYTHING, MOBS};
        }

        static {
            $VALUES = Sensitivity.m_154297_();
        }
    }
}

