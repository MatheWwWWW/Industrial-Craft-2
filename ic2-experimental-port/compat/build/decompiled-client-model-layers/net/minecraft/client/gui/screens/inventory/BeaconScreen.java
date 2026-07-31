/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;

public class BeaconScreen
extends AbstractContainerScreen<BeaconMenu> {
    static final ResourceLocation f_97903_ = new ResourceLocation("textures/gui/container/beacon.png");
    private static final Component f_97904_ = Component.m_237115_("block.minecraft.beacon.primary");
    private static final Component f_97905_ = Component.m_237115_("block.minecraft.beacon.secondary");
    private final List<BeaconButton> f_169612_ = Lists.newArrayList();
    @Nullable
    MobEffect f_97908_;
    @Nullable
    MobEffect f_97909_;

    public BeaconScreen(final BeaconMenu p_97912_, Inventory p_97913_, Component p_97914_) {
        super(p_97912_, p_97913_, p_97914_);
        this.f_97726_ = 230;
        this.f_97727_ = 219;
        p_97912_.m_38893_(new ContainerListener(){

            @Override
            public void m_7934_(AbstractContainerMenu p_97973_, int p_97974_, ItemStack p_97975_) {
            }

            @Override
            public void m_142153_(AbstractContainerMenu p_169628_, int p_169629_, int p_169630_) {
                BeaconScreen.this.f_97908_ = p_97912_.m_39057_();
                BeaconScreen.this.f_97909_ = p_97912_.m_39058_();
            }
        });
    }

    private <T extends AbstractWidget> void m_169616_(T p_169617_) {
        this.m_142416_(p_169617_);
        this.f_169612_.add((BeaconButton)((Object)p_169617_));
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_169612_.clear();
        this.m_169616_(new BeaconConfirmButton(this.f_97735_ + 164, this.f_97736_ + 107));
        this.m_169616_(new BeaconCancelButton(this.f_97735_ + 190, this.f_97736_ + 107));
        for (int $$0 = 0; $$0 <= 2; ++$$0) {
            int $$1 = BeaconBlockEntity.f_58646_[$$0].length;
            int $$2 = $$1 * 22 + ($$1 - 1) * 2;
            for (int $$3 = 0; $$3 < $$1; ++$$3) {
                MobEffect $$4 = BeaconBlockEntity.f_58646_[$$0][$$3];
                BeaconPowerButton $$5 = new BeaconPowerButton(this.f_97735_ + 76 + $$3 * 24 - $$2 / 2, this.f_97736_ + 22 + $$0 * 25, $$4, true, $$0);
                $$5.f_93623_ = false;
                this.m_169616_($$5);
            }
        }
        int $$6 = 3;
        int $$7 = BeaconBlockEntity.f_58646_[3].length + 1;
        int $$8 = $$7 * 22 + ($$7 - 1) * 2;
        for (int $$9 = 0; $$9 < $$7 - 1; ++$$9) {
            MobEffect $$10 = BeaconBlockEntity.f_58646_[3][$$9];
            BeaconPowerButton $$11 = new BeaconPowerButton(this.f_97735_ + 167 + $$9 * 24 - $$8 / 2, this.f_97736_ + 47, $$10, false, 3);
            $$11.f_93623_ = false;
            this.m_169616_($$11);
        }
        BeaconUpgradePowerButton $$12 = new BeaconUpgradePowerButton(this.f_97735_ + 167 + ($$7 - 1) * 24 - $$8 / 2, this.f_97736_ + 47, BeaconBlockEntity.f_58646_[0][0]);
        $$12.f_93624_ = false;
        this.m_169616_($$12);
    }

    @Override
    public void m_181908_() {
        super.m_181908_();
        this.m_169626_();
    }

    void m_169626_() {
        int $$0 = ((BeaconMenu)this.f_97732_).m_39056_();
        this.f_169612_.forEach(p_169615_ -> p_169615_.m_142400_($$0));
    }

    @Override
    protected void m_7027_(PoseStack p_97935_, int p_97936_, int p_97937_) {
        BeaconScreen.m_93215_(p_97935_, this.f_96547_, f_97904_, 62, 10, 0xE0E0E0);
        BeaconScreen.m_93215_(p_97935_, this.f_96547_, f_97905_, 169, 10, 0xE0E0E0);
        for (BeaconButton $$3 : this.f_169612_) {
            if (!$$3.m_142699_()) continue;
            $$3.m_7428_(p_97935_, p_97936_ - this.f_97735_, p_97937_ - this.f_97736_);
            break;
        }
    }

    @Override
    protected void m_7286_(PoseStack p_97916_, float p_97917_, int p_97918_, int p_97919_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_97903_);
        int $$4 = (this.f_96543_ - this.f_97726_) / 2;
        int $$5 = (this.f_96544_ - this.f_97727_) / 2;
        this.m_93228_(p_97916_, $$4, $$5, 0, 0, this.f_97726_, this.f_97727_);
        this.f_96542_.f_115093_ = 100.0f;
        this.f_96542_.m_115203_(new ItemStack(Items.f_42418_), $$4 + 20, $$5 + 109);
        this.f_96542_.m_115203_(new ItemStack(Items.f_42616_), $$4 + 41, $$5 + 109);
        this.f_96542_.m_115203_(new ItemStack(Items.f_42415_), $$4 + 41 + 22, $$5 + 109);
        this.f_96542_.m_115203_(new ItemStack(Items.f_42417_), $$4 + 42 + 44, $$5 + 109);
        this.f_96542_.m_115203_(new ItemStack(Items.f_42416_), $$4 + 42 + 66, $$5 + 109);
        this.f_96542_.f_115093_ = 0.0f;
    }

    @Override
    public void m_6305_(PoseStack p_97921_, int p_97922_, int p_97923_, float p_97924_) {
        this.m_7333_(p_97921_);
        super.m_6305_(p_97921_, p_97922_, p_97923_, p_97924_);
        this.m_7025_(p_97921_, p_97922_, p_97923_);
    }

    static interface BeaconButton {
        public boolean m_142699_();

        public void m_7428_(PoseStack var1, int var2, int var3);

        public void m_142400_(int var1);
    }

    class BeaconConfirmButton
    extends BeaconSpriteScreenButton {
        public BeaconConfirmButton(int p_97992_, int p_97993_) {
            super(p_97992_, p_97993_, 90, 220, CommonComponents.f_130655_);
        }

        @Override
        public void m_5691_() {
            BeaconScreen.this.f_96541_.m_91403_().m_104955_(new ServerboundSetBeaconPacket(Optional.ofNullable(BeaconScreen.this.f_97908_), Optional.ofNullable(BeaconScreen.this.f_97909_)));
            ((BeaconScreen)BeaconScreen.this).f_96541_.f_91074_.m_6915_();
        }

        @Override
        public void m_142400_(int p_169638_) {
            this.f_93623_ = ((BeaconMenu)BeaconScreen.this.f_97732_).m_39059_() && BeaconScreen.this.f_97908_ != null;
        }
    }

    class BeaconCancelButton
    extends BeaconSpriteScreenButton {
        public BeaconCancelButton(int p_97982_, int p_97983_) {
            super(p_97982_, p_97983_, 112, 220, CommonComponents.f_130656_);
        }

        @Override
        public void m_5691_() {
            ((BeaconScreen)BeaconScreen.this).f_96541_.f_91074_.m_6915_();
        }

        @Override
        public void m_142400_(int p_169636_) {
        }
    }

    class BeaconPowerButton
    extends BeaconScreenButton {
        private final boolean f_98002_;
        protected final int f_169639_;
        private MobEffect f_98000_;
        private TextureAtlasSprite f_98001_;
        private Component f_98003_;

        public BeaconPowerButton(int p_169642_, int p_169643_, MobEffect p_169644_, boolean p_169645_, int p_169646_) {
            super(p_169642_, p_169643_);
            this.f_98002_ = p_169645_;
            this.f_169639_ = p_169646_;
            this.m_169649_(p_169644_);
        }

        protected void m_169649_(MobEffect p_169650_) {
            this.f_98000_ = p_169650_;
            this.f_98001_ = Minecraft.m_91087_().m_91306_().m_118732_(p_169650_);
            this.f_98003_ = this.m_141934_(p_169650_);
        }

        protected MutableComponent m_141934_(MobEffect p_169652_) {
            return Component.m_237115_(p_169652_.m_19481_());
        }

        @Override
        public void m_5691_() {
            if (this.m_98024_()) {
                return;
            }
            if (this.f_98002_) {
                BeaconScreen.this.f_97908_ = this.f_98000_;
            } else {
                BeaconScreen.this.f_97909_ = this.f_98000_;
            }
            BeaconScreen.this.m_169626_();
        }

        @Override
        public void m_7428_(PoseStack p_98016_, int p_98017_, int p_98018_) {
            BeaconScreen.this.m_96602_(p_98016_, this.f_98003_, p_98017_, p_98018_);
        }

        @Override
        protected void m_6805_(PoseStack p_98014_) {
            RenderSystem.m_157456_(0, this.f_98001_.m_118414_().m_118330_());
            BeaconPowerButton.m_93200_(p_98014_, this.f_93620_ + 2, this.f_93621_ + 2, this.m_93252_(), 18, 18, this.f_98001_);
        }

        @Override
        public void m_142400_(int p_169648_) {
            this.f_93623_ = this.f_169639_ < p_169648_;
            this.m_98031_(this.f_98000_ == (this.f_98002_ ? BeaconScreen.this.f_97908_ : BeaconScreen.this.f_97909_));
        }

        @Override
        protected MutableComponent m_5646_() {
            return this.m_141934_(this.f_98000_);
        }
    }

    class BeaconUpgradePowerButton
    extends BeaconPowerButton {
        public BeaconUpgradePowerButton(int p_169675_, int p_169676_, MobEffect p_169677_) {
            super(p_169675_, p_169676_, p_169677_, false, 3);
        }

        @Override
        protected MutableComponent m_141934_(MobEffect p_169681_) {
            return Component.m_237115_(p_169681_.m_19481_()).m_130946_(" II");
        }

        @Override
        public void m_142400_(int p_169679_) {
            if (BeaconScreen.this.f_97908_ != null) {
                this.f_93624_ = true;
                this.m_169649_(BeaconScreen.this.f_97908_);
                super.m_142400_(p_169679_);
            } else {
                this.f_93624_ = false;
            }
        }
    }

    abstract class BeaconSpriteScreenButton
    extends BeaconScreenButton {
        private final int f_98033_;
        private final int f_98034_;

        protected BeaconSpriteScreenButton(int p_169663_, int p_169664_, int p_169665_, int p_169666_, Component p_169667_) {
            super(p_169663_, p_169664_, p_169667_);
            this.f_98033_ = p_169665_;
            this.f_98034_ = p_169666_;
        }

        @Override
        protected void m_6805_(PoseStack p_98041_) {
            this.m_93228_(p_98041_, this.f_93620_ + 2, this.f_93621_ + 2, this.f_98033_, this.f_98034_, 18, 18);
        }

        @Override
        public void m_7428_(PoseStack p_169669_, int p_169670_, int p_169671_) {
            BeaconScreen.this.m_96602_(p_169669_, BeaconScreen.this.f_96539_, p_169670_, p_169671_);
        }
    }

    static abstract class BeaconScreenButton
    extends AbstractButton
    implements BeaconButton {
        private boolean f_98020_;

        protected BeaconScreenButton(int p_98022_, int p_98023_) {
            super(p_98022_, p_98023_, 22, 22, CommonComponents.f_237098_);
        }

        protected BeaconScreenButton(int p_169654_, int p_169655_, Component p_169656_) {
            super(p_169654_, p_169655_, 22, 22, p_169656_);
        }

        @Override
        public void m_6303_(PoseStack p_98027_, int p_98028_, int p_98029_, float p_98030_) {
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157456_(0, f_97903_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            int $$4 = 219;
            int $$5 = 0;
            if (!this.f_93623_) {
                $$5 += this.f_93618_ * 2;
            } else if (this.f_98020_) {
                $$5 += this.f_93618_ * 1;
            } else if (this.m_198029_()) {
                $$5 += this.f_93618_ * 3;
            }
            this.m_93228_(p_98027_, this.f_93620_, this.f_93621_, $$5, 219, this.f_93618_, this.f_93619_);
            this.m_6805_(p_98027_);
        }

        protected abstract void m_6805_(PoseStack var1);

        public boolean m_98024_() {
            return this.f_98020_;
        }

        public void m_98031_(boolean p_98032_) {
            this.f_98020_ = p_98032_;
        }

        @Override
        public boolean m_142699_() {
            return this.f_93622_;
        }

        @Override
        public void m_142291_(NarrationElementOutput p_169659_) {
            this.m_168802_(p_169659_);
        }
    }
}

