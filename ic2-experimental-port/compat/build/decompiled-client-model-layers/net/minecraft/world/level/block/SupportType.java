/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public abstract class SupportType
extends Enum<SupportType> {
    public static final /* enum */ SupportType FULL = new SupportType(){

        @Override
        public boolean m_5588_(BlockState p_57220_, BlockGetter p_57221_, BlockPos p_57222_, Direction p_57223_) {
            return Block.m_49918_(p_57220_.m_60816_(p_57221_, p_57222_), p_57223_);
        }
    };
    public static final /* enum */ SupportType CENTER = new SupportType(){
        private final int f_57224_ = 1;
        private final VoxelShape f_57225_ = Block.m_49796_(7.0, 0.0, 7.0, 9.0, 10.0, 9.0);

        @Override
        public boolean m_5588_(BlockState p_57230_, BlockGetter p_57231_, BlockPos p_57232_, Direction p_57233_) {
            return !Shapes.m_83157_(p_57230_.m_60816_(p_57231_, p_57232_).m_83263_(p_57233_), this.f_57225_, BooleanOp.f_82683_);
        }
    };
    public static final /* enum */ SupportType RIGID = new SupportType(){
        private final int f_57234_ = 2;
        private final VoxelShape f_57235_ = Shapes.m_83113_(Shapes.m_83144_(), Block.m_49796_(2.0, 0.0, 2.0, 14.0, 16.0, 14.0), BooleanOp.f_82685_);

        @Override
        public boolean m_5588_(BlockState p_57240_, BlockGetter p_57241_, BlockPos p_57242_, Direction p_57243_) {
            return !Shapes.m_83157_(p_57240_.m_60816_(p_57241_, p_57242_).m_83263_(p_57243_), this.f_57235_, BooleanOp.f_82683_);
        }
    };
    private static final /* synthetic */ SupportType[] $VALUES;

    public static SupportType[] values() {
        return (SupportType[])$VALUES.clone();
    }

    public static SupportType valueOf(String p_57214_) {
        return Enum.valueOf(SupportType.class, p_57214_);
    }

    public abstract boolean m_5588_(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4);

    private static /* synthetic */ SupportType[] m_154736_() {
        return new SupportType[]{FULL, CENTER, RIGID};
    }

    static {
        $VALUES = SupportType.m_154736_();
    }
}

