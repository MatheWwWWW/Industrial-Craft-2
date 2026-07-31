/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.world.level.block;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;

public class PlayerHeadBlock
extends SkullBlock {
    protected PlayerHeadBlock(BlockBehaviour.Properties p_55177_) {
        super(SkullBlock.Types.PLAYER, p_55177_);
    }

    @Override
    public void m_6402_(Level p_55179_, BlockPos p_55180_, BlockState p_55181_, @Nullable LivingEntity p_55182_, ItemStack p_55183_) {
        super.m_6402_(p_55179_, p_55180_, p_55181_, p_55182_, p_55183_);
        BlockEntity $$5 = p_55179_.m_7702_(p_55180_);
        if ($$5 instanceof SkullBlockEntity) {
            SkullBlockEntity $$6 = (SkullBlockEntity)$$5;
            GameProfile $$7 = null;
            if (p_55183_.m_41782_()) {
                CompoundTag $$8 = p_55183_.m_41783_();
                if ($$8.m_128425_("SkullOwner", 10)) {
                    $$7 = NbtUtils.m_129228_($$8.m_128469_("SkullOwner"));
                } else if ($$8.m_128425_("SkullOwner", 8) && !StringUtils.isBlank((CharSequence)$$8.m_128461_("SkullOwner"))) {
                    $$7 = new GameProfile(null, $$8.m_128461_("SkullOwner"));
                }
            }
            $$6.m_59769_($$7);
        }
    }
}

