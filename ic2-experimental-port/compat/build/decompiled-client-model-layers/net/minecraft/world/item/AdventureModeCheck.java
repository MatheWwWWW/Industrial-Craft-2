/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

public class AdventureModeCheck {
    private final String f_186321_;
    @Nullable
    private BlockInWorld f_186322_;
    private boolean f_186323_;
    private boolean f_186324_;

    public AdventureModeCheck(String p_186327_) {
        this.f_186321_ = p_186327_;
    }

    private static boolean m_186332_(BlockInWorld p_186333_, @Nullable BlockInWorld p_186334_, boolean p_186335_) {
        if (p_186334_ == null || p_186333_.m_61168_() != p_186334_.m_61168_()) {
            return false;
        }
        if (!p_186335_) {
            return true;
        }
        if (p_186333_.m_61174_() == null && p_186334_.m_61174_() == null) {
            return true;
        }
        if (p_186333_.m_61174_() == null || p_186334_.m_61174_() == null) {
            return false;
        }
        return Objects.equals(p_186333_.m_61174_().m_187481_(), p_186334_.m_61174_().m_187481_());
    }

    public boolean m_204085_(ItemStack p_204086_, Registry<Block> p_204087_, BlockInWorld p_204088_) {
        if (AdventureModeCheck.m_186332_(p_204088_, this.f_186322_, this.f_186324_)) {
            return this.f_186323_;
        }
        this.f_186322_ = p_204088_;
        this.f_186324_ = false;
        CompoundTag $$3 = p_204086_.m_41783_();
        if ($$3 != null && $$3.m_128425_(this.f_186321_, 9)) {
            ListTag $$4 = $$3.m_128437_(this.f_186321_, 8);
            for (int $$5 = 0; $$5 < $$4.size(); ++$$5) {
                String $$6 = $$4.m_128778_($$5);
                try {
                    BlockPredicateArgument.Result $$7 = BlockPredicateArgument.m_234633_(HolderLookup.m_235701_(p_204087_), new StringReader($$6));
                    this.f_186324_ |= $$7.m_183631_();
                    if ($$7.test(p_204088_)) {
                        this.f_186323_ = true;
                        return true;
                    }
                    continue;
                }
                catch (CommandSyntaxException commandSyntaxException) {
                    // empty catch block
                }
            }
        }
        this.f_186323_ = false;
        return false;
    }
}

