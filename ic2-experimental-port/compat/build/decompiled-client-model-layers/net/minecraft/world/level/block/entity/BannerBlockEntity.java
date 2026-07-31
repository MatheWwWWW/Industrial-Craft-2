/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BannerBlockEntity
extends BlockEntity
implements Nameable {
    public static final int f_155030_ = 6;
    public static final String f_155031_ = "Patterns";
    public static final String f_155032_ = "Pattern";
    public static final String f_155033_ = "Color";
    @Nullable
    private Component f_58473_;
    private DyeColor f_58474_;
    @Nullable
    private ListTag f_58475_;
    @Nullable
    private List<Pair<Holder<BannerPattern>, DyeColor>> f_58477_;

    public BannerBlockEntity(BlockPos p_155035_, BlockState p_155036_) {
        super(BlockEntityType.f_58935_, p_155035_, p_155036_);
        this.f_58474_ = ((AbstractBannerBlock)p_155036_.m_60734_()).m_48674_();
    }

    public BannerBlockEntity(BlockPos p_155038_, BlockState p_155039_, DyeColor p_155040_) {
        this(p_155038_, p_155039_);
        this.f_58474_ = p_155040_;
    }

    @Nullable
    public static ListTag m_58487_(ItemStack p_58488_) {
        ListTag $$1 = null;
        CompoundTag $$2 = BlockItem.m_186336_(p_58488_);
        if ($$2 != null && $$2.m_128425_(f_155031_, 9)) {
            $$1 = $$2.m_128437_(f_155031_, 10).m_6426_();
        }
        return $$1;
    }

    public void m_58489_(ItemStack p_58490_, DyeColor p_58491_) {
        this.f_58474_ = p_58491_;
        this.m_187453_(p_58490_);
    }

    public void m_187453_(ItemStack p_187454_) {
        this.f_58475_ = BannerBlockEntity.m_58487_(p_187454_);
        this.f_58477_ = null;
        this.f_58473_ = p_187454_.m_41788_() ? p_187454_.m_41786_() : null;
    }

    @Override
    public Component m_7755_() {
        if (this.f_58473_ != null) {
            return this.f_58473_;
        }
        return Component.m_237115_("block.minecraft.banner");
    }

    @Override
    @Nullable
    public Component m_7770_() {
        return this.f_58473_;
    }

    public void m_58501_(Component p_58502_) {
        this.f_58473_ = p_58502_;
    }

    @Override
    protected void m_183515_(CompoundTag p_187456_) {
        super.m_183515_(p_187456_);
        if (this.f_58475_ != null) {
            p_187456_.m_128365_(f_155031_, this.f_58475_);
        }
        if (this.f_58473_ != null) {
            p_187456_.m_128359_("CustomName", Component.Serializer.m_130703_(this.f_58473_));
        }
    }

    @Override
    public void m_142466_(CompoundTag p_155042_) {
        super.m_142466_(p_155042_);
        if (p_155042_.m_128425_("CustomName", 8)) {
            this.f_58473_ = Component.Serializer.m_130701_(p_155042_.m_128461_("CustomName"));
        }
        this.f_58475_ = p_155042_.m_128437_(f_155031_, 10);
        this.f_58477_ = null;
    }

    public ClientboundBlockEntityDataPacket m_58483_() {
        return ClientboundBlockEntityDataPacket.m_195640_(this);
    }

    @Override
    public CompoundTag m_5995_() {
        return this.m_187482_();
    }

    public static int m_58504_(ItemStack p_58505_) {
        CompoundTag $$1 = BlockItem.m_186336_(p_58505_);
        if ($$1 != null && $$1.m_128441_(f_155031_)) {
            return $$1.m_128437_(f_155031_, 10).size();
        }
        return 0;
    }

    public List<Pair<Holder<BannerPattern>, DyeColor>> m_58508_() {
        if (this.f_58477_ == null) {
            this.f_58477_ = BannerBlockEntity.m_58484_(this.f_58474_, this.f_58475_);
        }
        return this.f_58477_;
    }

    public static List<Pair<Holder<BannerPattern>, DyeColor>> m_58484_(DyeColor p_58485_, @Nullable ListTag p_58486_) {
        ArrayList $$2 = Lists.newArrayList();
        $$2.add(Pair.of(Registry.f_235736_.m_206081_(BannerPatterns.f_222726_), (Object)p_58485_));
        if (p_58486_ != null) {
            for (int $$3 = 0; $$3 < p_58486_.size(); ++$$3) {
                CompoundTag $$4 = p_58486_.m_128728_($$3);
                Holder<BannerPattern> $$5 = BannerPattern.m_222700_($$4.m_128461_(f_155032_));
                if ($$5 == null) continue;
                int $$6 = $$4.m_128451_(f_155033_);
                $$2.add(Pair.of($$5, (Object)DyeColor.m_41053_($$6)));
            }
        }
        return $$2;
    }

    public static void m_58509_(ItemStack p_58510_) {
        CompoundTag $$1 = BlockItem.m_186336_(p_58510_);
        if ($$1 == null || !$$1.m_128425_(f_155031_, 9)) {
            return;
        }
        ListTag $$2 = $$1.m_128437_(f_155031_, 10);
        if ($$2.isEmpty()) {
            return;
        }
        $$2.remove($$2.size() - 1);
        if ($$2.isEmpty()) {
            $$1.m_128473_(f_155031_);
        }
        BlockItem.m_186338_(p_58510_, BlockEntityType.f_58935_, $$1);
    }

    public ItemStack m_155043_() {
        ItemStack $$0 = new ItemStack(BannerBlock.m_49014_(this.f_58474_));
        if (this.f_58475_ != null && !this.f_58475_.isEmpty()) {
            CompoundTag $$1 = new CompoundTag();
            $$1.m_128365_(f_155031_, this.f_58475_.m_6426_());
            BlockItem.m_186338_($$0, this.m_58903_(), $$1);
        }
        if (this.f_58473_ != null) {
            $$0.m_41714_(this.f_58473_);
        }
        return $$0;
    }

    public DyeColor m_155044_() {
        return this.f_58474_;
    }

    public /* synthetic */ Packet m_58483_() {
        return this.m_58483_();
    }
}

