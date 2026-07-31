/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.state.predicate;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockMaterialPredicate
implements Predicate<BlockState> {
    private static final BlockMaterialPredicate f_61252_ = new BlockMaterialPredicate(Material.f_76296_){

        @Override
        public boolean test(@Nullable BlockState p_61269_) {
            return p_61269_ != null && p_61269_.m_60795_();
        }

        @Override
        public /* synthetic */ boolean test(@Nullable Object object) {
            return this.test((BlockState)object);
        }
    };
    private final Material f_61253_;

    BlockMaterialPredicate(Material p_61256_) {
        this.f_61253_ = p_61256_;
    }

    public static BlockMaterialPredicate m_61262_(Material p_61263_) {
        return p_61263_ == Material.f_76296_ ? f_61252_ : new BlockMaterialPredicate(p_61263_);
    }

    @Override
    public boolean test(@Nullable BlockState p_61261_) {
        return p_61261_ != null && p_61261_.m_60767_() == this.f_61253_;
    }

    @Override
    public /* synthetic */ boolean test(@Nullable Object object) {
        return this.test((BlockState)object);
    }
}

