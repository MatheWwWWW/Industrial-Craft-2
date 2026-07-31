/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.item;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

public class KnowledgeBookItem
extends Item {
    private static final String f_151103_ = "Recipes";
    private static final Logger f_42819_ = LogUtils.getLogger();

    public KnowledgeBookItem(Item.Properties p_42822_) {
        super(p_42822_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_42824_, Player p_42825_, InteractionHand p_42826_) {
        ItemStack $$3 = p_42825_.m_21120_(p_42826_);
        CompoundTag $$4 = $$3.m_41783_();
        if (!p_42825_.m_150110_().f_35937_) {
            p_42825_.m_21008_(p_42826_, ItemStack.f_41583_);
        }
        if ($$4 == null || !$$4.m_128425_(f_151103_, 9)) {
            f_42819_.error("Tag not valid: {}", (Object)$$4);
            return InteractionResultHolder.m_19100_($$3);
        }
        if (!p_42824_.f_46443_) {
            ListTag $$5 = $$4.m_128437_(f_151103_, 8);
            ArrayList $$6 = Lists.newArrayList();
            RecipeManager $$7 = p_42824_.m_7654_().m_129894_();
            for (int $$8 = 0; $$8 < $$5.size(); ++$$8) {
                String $$9 = $$5.m_128778_($$8);
                Optional<Recipe<?>> $$10 = $$7.m_44043_(new ResourceLocation($$9));
                if (!$$10.isPresent()) {
                    f_42819_.error("Invalid recipe: {}", (Object)$$9);
                    return InteractionResultHolder.m_19100_($$3);
                }
                $$6.add($$10.get());
            }
            p_42825_.m_7281_($$6);
            p_42825_.m_36246_(Stats.f_12982_.m_12902_(this));
        }
        return InteractionResultHolder.m_19092_($$3, p_42824_.m_5776_());
    }
}

