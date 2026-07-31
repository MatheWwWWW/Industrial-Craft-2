/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Ordering
 */
package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class EffectRenderingInventoryScreen<T extends AbstractContainerMenu>
extends AbstractContainerScreen<T> {
    public EffectRenderingInventoryScreen(T p_98701_, Inventory p_98702_, Component p_98703_) {
        super(p_98701_, p_98702_, p_98703_);
    }

    @Override
    public void m_6305_(PoseStack p_98705_, int p_98706_, int p_98707_, float p_98708_) {
        super.m_6305_(p_98705_, p_98706_, p_98707_, p_98708_);
        this.m_194014_(p_98705_, p_98706_, p_98707_);
    }

    public boolean m_194018_() {
        int $$0 = this.f_97735_ + this.f_97726_ + 2;
        int $$1 = this.f_96543_ - $$0;
        return $$1 >= 32;
    }

    private void m_194014_(PoseStack p_194015_, int p_194016_, int p_194017_) {
        int $$3 = this.f_97735_ + this.f_97726_ + 2;
        int $$4 = this.f_96543_ - $$3;
        Collection<MobEffectInstance> $$5 = this.f_96541_.f_91074_.m_21220_();
        if ($$5.isEmpty() || $$4 < 32) {
            return;
        }
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        boolean $$6 = $$4 >= 120;
        int $$7 = 33;
        if ($$5.size() > 5) {
            $$7 = 132 / ($$5.size() - 1);
        }
        List $$8 = Ordering.natural().sortedCopy($$5);
        this.m_194002_(p_194015_, $$3, $$7, $$8, $$6);
        this.m_194008_(p_194015_, $$3, $$7, $$8, $$6);
        if ($$6) {
            this.m_98722_(p_194015_, $$3, $$7, $$8);
        } else if (p_194016_ >= $$3 && p_194016_ <= $$3 + 33) {
            int $$9 = this.f_97736_;
            MobEffectInstance $$10 = null;
            for (MobEffectInstance $$11 : $$8) {
                if (p_194017_ >= $$9 && p_194017_ <= $$9 + $$7) {
                    $$10 = $$11;
                }
                $$9 += $$7;
            }
            if ($$10 != null) {
                List<Component> $$12 = List.of(this.m_194000_($$10), Component.m_237113_(MobEffectUtil.m_19581_($$10, 1.0f)));
                this.m_169388_(p_194015_, $$12, Optional.empty(), p_194016_, p_194017_);
            }
        }
    }

    private void m_194002_(PoseStack p_194003_, int p_194004_, int p_194005_, Iterable<MobEffectInstance> p_194006_, boolean p_194007_) {
        RenderSystem.m_157456_(0, f_97725_);
        int $$5 = this.f_97736_;
        for (MobEffectInstance $$6 : p_194006_) {
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            if (p_194007_) {
                this.m_93228_(p_194003_, p_194004_, $$5, 0, 166, 120, 32);
            } else {
                this.m_93228_(p_194003_, p_194004_, $$5, 0, 198, 32, 32);
            }
            $$5 += p_194005_;
        }
    }

    private void m_194008_(PoseStack p_194009_, int p_194010_, int p_194011_, Iterable<MobEffectInstance> p_194012_, boolean p_194013_) {
        MobEffectTextureManager $$5 = this.f_96541_.m_91306_();
        int $$6 = this.f_97736_;
        for (MobEffectInstance $$7 : p_194012_) {
            MobEffect $$8 = $$7.m_19544_();
            TextureAtlasSprite $$9 = $$5.m_118732_($$8);
            RenderSystem.m_157456_(0, $$9.m_118414_().m_118330_());
            EffectRenderingInventoryScreen.m_93200_(p_194009_, p_194010_ + (p_194013_ ? 6 : 7), $$6 + 7, this.m_93252_(), 18, 18, $$9);
            $$6 += p_194011_;
        }
    }

    private void m_98722_(PoseStack p_98723_, int p_98724_, int p_98725_, Iterable<MobEffectInstance> p_98726_) {
        int $$4 = this.f_97736_;
        for (MobEffectInstance $$5 : p_98726_) {
            Component $$6 = this.m_194000_($$5);
            this.f_96547_.m_92763_(p_98723_, $$6, p_98724_ + 10 + 18, $$4 + 6, 0xFFFFFF);
            String $$7 = MobEffectUtil.m_19581_($$5, 1.0f);
            this.f_96547_.m_92750_(p_98723_, $$7, p_98724_ + 10 + 18, $$4 + 6 + 10, 0x7F7F7F);
            $$4 += p_98725_;
        }
    }

    private Component m_194000_(MobEffectInstance p_194001_) {
        MutableComponent $$1 = p_194001_.m_19544_().m_19482_().m_6881_();
        if (p_194001_.m_19564_() >= 1 && p_194001_.m_19564_() <= 9) {
            $$1.m_130946_(" ").m_7220_(Component.m_237115_("enchantment.level." + (p_194001_.m_19564_() + 1)));
        }
        return $$1;
    }
}

