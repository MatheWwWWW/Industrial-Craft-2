/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AnvilScreen
extends ItemCombinerScreen<AnvilMenu> {
    private static final ResourceLocation f_97869_ = new ResourceLocation("textures/gui/container/anvil.png");
    private static final Component f_97870_ = Component.m_237115_("container.repair.expensive");
    private EditBox f_97871_;
    private final Player f_169611_;

    public AnvilScreen(AnvilMenu p_97874_, Inventory p_97875_, Component p_97876_) {
        super(p_97874_, p_97875_, p_97876_, f_97869_);
        this.f_169611_ = p_97875_.f_35978_;
        this.f_97728_ = 60;
    }

    @Override
    public void m_181908_() {
        super.m_181908_();
        this.f_97871_.m_94120_();
    }

    @Override
    protected void m_5653_() {
        this.f_96541_.f_91068_.m_90926_(true);
        int $$0 = (this.f_96543_ - this.f_97726_) / 2;
        int $$1 = (this.f_96544_ - this.f_97727_) / 2;
        this.f_97871_ = new EditBox(this.f_96547_, $$0 + 62, $$1 + 24, 103, 12, Component.m_237115_("container.repair"));
        this.f_97871_.m_94190_(false);
        this.f_97871_.m_94202_(-1);
        this.f_97871_.m_94205_(-1);
        this.f_97871_.m_94182_(false);
        this.f_97871_.m_94199_(50);
        this.f_97871_.m_94151_(this::m_97898_);
        this.f_97871_.m_94144_("");
        this.m_7787_(this.f_97871_);
        this.m_94718_(this.f_97871_);
        this.f_97871_.m_94186_(false);
    }

    @Override
    public void m_6574_(Minecraft p_97886_, int p_97887_, int p_97888_) {
        String $$3 = this.f_97871_.m_94155_();
        this.m_6575_(p_97886_, p_97887_, p_97888_);
        this.f_97871_.m_94144_($$3);
    }

    @Override
    public void m_7861_() {
        super.m_7861_();
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_7933_(int p_97878_, int p_97879_, int p_97880_) {
        if (p_97878_ == 256) {
            this.f_96541_.f_91074_.m_6915_();
        }
        if (this.f_97871_.m_7933_(p_97878_, p_97879_, p_97880_) || this.f_97871_.m_94204_()) {
            return true;
        }
        return super.m_7933_(p_97878_, p_97879_, p_97880_);
    }

    private void m_97898_(String p_97899_) {
        if (p_97899_.isEmpty()) {
            return;
        }
        String $$1 = p_97899_;
        Slot $$2 = ((AnvilMenu)this.f_97732_).m_38853_(0);
        if ($$2 != null && $$2.m_6657_() && !$$2.m_7993_().m_41788_() && $$1.equals($$2.m_7993_().m_41786_().getString())) {
            $$1 = "";
        }
        ((AnvilMenu)this.f_97732_).m_39020_($$1);
        this.f_96541_.f_91074_.f_108617_.m_104955_(new ServerboundRenameItemPacket($$1));
    }

    @Override
    protected void m_7027_(PoseStack p_97890_, int p_97891_, int p_97892_) {
        RenderSystem.m_69461_();
        super.m_7027_(p_97890_, p_97891_, p_97892_);
        int $$3 = ((AnvilMenu)this.f_97732_).m_39028_();
        if ($$3 > 0) {
            MutableComponent $$7;
            int $$4 = 8453920;
            if ($$3 >= 40 && !this.f_96541_.f_91074_.m_150110_().f_35937_) {
                Component $$5 = f_97870_;
                $$4 = 0xFF6060;
            } else if (!((AnvilMenu)this.f_97732_).m_38853_(2).m_6657_()) {
                Object $$6 = null;
            } else {
                $$7 = Component.m_237110_("container.repair.cost", $$3);
                if (!((AnvilMenu)this.f_97732_).m_38853_(2).m_8010_(this.f_169611_)) {
                    $$4 = 0xFF6060;
                }
            }
            if ($$7 != null) {
                int $$8 = this.f_97726_ - 8 - this.f_96547_.m_92852_($$7) - 2;
                int $$9 = 69;
                AnvilScreen.m_93172_(p_97890_, $$8 - 2, 67, this.f_97726_ - 8, 79, 0x4F000000);
                this.f_96547_.m_92763_(p_97890_, $$7, $$8, 69.0f, $$4);
            }
        }
    }

    @Override
    public void m_6691_(PoseStack p_97894_, int p_97895_, int p_97896_, float p_97897_) {
        this.f_97871_.m_6305_(p_97894_, p_97895_, p_97896_, p_97897_);
    }

    @Override
    public void m_7934_(AbstractContainerMenu p_97882_, int p_97883_, ItemStack p_97884_) {
        if (p_97883_ == 0) {
            this.f_97871_.m_94144_(p_97884_.m_41619_() ? "" : p_97884_.m_41786_().getString());
            this.f_97871_.m_94186_(!p_97884_.m_41619_());
            this.m_7522_(this.f_97871_);
        }
    }
}

