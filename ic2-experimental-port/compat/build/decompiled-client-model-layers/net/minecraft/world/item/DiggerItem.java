/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.ImmutableMultimap$Builder
 *  com.google.common.collect.Multimap
 */
package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DiggerItem
extends TieredItem
implements Vanishable {
    private final TagKey<Block> f_40979_;
    protected final float f_40980_;
    private final float f_40981_;
    private final Multimap<Attribute, AttributeModifier> f_40982_;

    protected DiggerItem(float p_204108_, float p_204109_, Tier p_204110_, TagKey<Block> p_204111_, Item.Properties p_204112_) {
        super(p_204110_, p_204112_);
        this.f_40979_ = p_204111_;
        this.f_40980_ = p_204110_.m_6624_();
        this.f_40981_ = p_204108_ + p_204110_.m_6631_();
        ImmutableMultimap.Builder $$5 = ImmutableMultimap.builder();
        $$5.put((Object)Attributes.f_22281_, (Object)new AttributeModifier(f_41374_, "Tool modifier", (double)this.f_40981_, AttributeModifier.Operation.ADDITION));
        $$5.put((Object)Attributes.f_22283_, (Object)new AttributeModifier(f_41375_, "Tool modifier", (double)p_204109_, AttributeModifier.Operation.ADDITION));
        this.f_40982_ = $$5.build();
    }

    @Override
    public float m_8102_(ItemStack p_41004_, BlockState p_41005_) {
        return p_41005_.m_204336_(this.f_40979_) ? this.f_40980_ : 1.0f;
    }

    @Override
    public boolean m_7579_(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.m_41622_(2, p_40996_, p_41007_ -> p_41007_.m_21166_(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean m_6813_(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.f_46443_ && p_41000_.m_60800_(p_40999_, p_41001_) != 0.0f) {
            p_40998_.m_41622_(1, p_41002_, p_40992_ -> p_40992_.m_21166_(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot p_40990_) {
        if (p_40990_ == EquipmentSlot.MAINHAND) {
            return this.f_40982_;
        }
        return super.m_7167_(p_40990_);
    }

    public float m_41008_() {
        return this.f_40981_;
    }

    @Override
    public boolean m_8096_(BlockState p_150816_) {
        int $$1 = this.m_43314_().m_6604_();
        if ($$1 < 3 && p_150816_.m_204336_(BlockTags.f_144284_)) {
            return false;
        }
        if ($$1 < 2 && p_150816_.m_204336_(BlockTags.f_144285_)) {
            return false;
        }
        if ($$1 < 1 && p_150816_.m_204336_(BlockTags.f_144286_)) {
            return false;
        }
        return p_150816_.m_204336_(this.f_40979_);
    }
}

