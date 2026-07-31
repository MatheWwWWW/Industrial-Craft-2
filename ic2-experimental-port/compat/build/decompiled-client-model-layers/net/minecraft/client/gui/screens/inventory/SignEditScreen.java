/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.util.stream.IntStream;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SignEditScreen
extends Screen {
    private final SignBlockEntity f_99254_;
    private int f_99255_;
    private int f_99256_;
    private TextFieldHelper f_99257_;
    private WoodType f_169809_;
    private SignRenderer.SignModel f_99253_;
    private final String[] f_99258_ = (String[])IntStream.range(0, 4).mapToObj(p_169818_ -> p_169811_.m_155706_(p_169818_, p_169812_)).map(Component::getString).toArray(String[]::new);

    public SignEditScreen(SignBlockEntity p_169811_, boolean p_169812_) {
        super(Component.m_237115_("sign.edit"));
        this.f_99254_ = p_169811_;
    }

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120, 200, 20, CommonComponents.f_130655_, p_169820_ -> this.m_99285_()));
        this.f_99254_.m_59746_(false);
        this.f_99257_ = new TextFieldHelper(() -> this.f_99258_[this.f_99256_], p_169824_ -> {
            this.f_99258_[this.f_99256_] = p_169824_;
            this.f_99254_.m_59732_(this.f_99256_, Component.m_237113_(p_169824_));
        }, TextFieldHelper.m_95153_(this.f_96541_), TextFieldHelper.m_95182_(this.f_96541_), p_169822_ -> this.f_96541_.f_91062_.m_92895_((String)p_169822_) <= 90);
        BlockState $$0 = this.f_99254_.m_58900_();
        this.f_169809_ = SignRenderer.m_173637_($$0.m_60734_());
        this.f_99253_ = SignRenderer.m_173646_(this.f_96541_.m_167973_(), this.f_169809_);
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
        ClientPacketListener $$0 = this.f_96541_.m_91403_();
        if ($$0 != null) {
            $$0.m_104955_(new ServerboundSignUpdatePacket(this.f_99254_.m_58899_(), this.f_99258_[0], this.f_99258_[1], this.f_99258_[2], this.f_99258_[3]));
        }
        this.f_99254_.m_59746_(true);
    }

    @Override
    public void m_86600_() {
        ++this.f_99255_;
        if (!this.f_99254_.m_58903_().m_155262_(this.f_99254_.m_58900_())) {
            this.m_99285_();
        }
    }

    private void m_99285_() {
        this.f_99254_.m_6596_();
        this.f_96541_.m_91152_(null);
    }

    @Override
    public boolean m_5534_(char p_99262_, int p_99263_) {
        this.f_99257_.m_95143_(p_99262_);
        return true;
    }

    @Override
    public void m_7379_() {
        this.m_99285_();
    }

    @Override
    public boolean m_7933_(int p_99267_, int p_99268_, int p_99269_) {
        if (p_99267_ == 265) {
            this.f_99256_ = this.f_99256_ - 1 & 3;
            this.f_99257_.m_95193_();
            return true;
        }
        if (p_99267_ == 264 || p_99267_ == 257 || p_99267_ == 335) {
            this.f_99256_ = this.f_99256_ + 1 & 3;
            this.f_99257_.m_95193_();
            return true;
        }
        if (this.f_99257_.m_95145_(p_99267_)) {
            return true;
        }
        return super.m_7933_(p_99267_, p_99268_, p_99269_);
    }

    @Override
    public void m_6305_(PoseStack p_99271_, int p_99272_, int p_99273_, float p_99274_) {
        Lighting.m_84930_();
        this.m_7333_(p_99271_);
        SignEditScreen.m_93215_(p_99271_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 40, 0xFFFFFF);
        p_99271_.m_85836_();
        p_99271_.m_85837_(this.f_96543_ / 2, 0.0, 50.0);
        float $$4 = 93.75f;
        p_99271_.m_85841_(93.75f, -93.75f, 93.75f);
        p_99271_.m_85837_(0.0, -1.3125, 0.0);
        BlockState $$5 = this.f_99254_.m_58900_();
        boolean $$6 = $$5.m_60734_() instanceof StandingSignBlock;
        if (!$$6) {
            p_99271_.m_85837_(0.0, -0.3125, 0.0);
        }
        boolean $$7 = this.f_99255_ / 6 % 2 == 0;
        float $$8 = 0.6666667f;
        p_99271_.m_85836_();
        p_99271_.m_85841_(0.6666667f, -0.6666667f, -0.6666667f);
        MultiBufferSource.BufferSource $$9 = this.f_96541_.m_91269_().m_110104_();
        Material $$10 = Sheets.m_173381_(this.f_169809_);
        VertexConsumer $$11 = $$10.m_119194_($$9, this.f_99253_::m_103119_);
        this.f_99253_.f_112507_.f_104207_ = $$6;
        this.f_99253_.f_173655_.m_104301_(p_99271_, $$11, 0xF000F0, OverlayTexture.f_118083_);
        p_99271_.m_85849_();
        float $$12 = 0.010416667f;
        p_99271_.m_85837_(0.0, 0.3333333432674408, 0.046666666865348816);
        p_99271_.m_85841_(0.010416667f, -0.010416667f, 0.010416667f);
        int $$13 = this.f_99254_.m_59753_().m_41071_();
        int $$14 = this.f_99257_.m_95194_();
        int $$15 = this.f_99257_.m_95197_();
        int $$16 = this.f_99256_ * 10 - this.f_99258_.length * 5;
        Matrix4f $$17 = p_99271_.m_85850_().m_85861_();
        for (int $$18 = 0; $$18 < this.f_99258_.length; ++$$18) {
            String $$19 = this.f_99258_[$$18];
            if ($$19 == null) continue;
            if (this.f_96547_.m_92718_()) {
                $$19 = this.f_96547_.m_92801_($$19);
            }
            float $$20 = -this.f_96541_.f_91062_.m_92895_($$19) / 2;
            this.f_96541_.f_91062_.m_92822_($$19, $$20, $$18 * 10 - this.f_99258_.length * 5, $$13, false, $$17, $$9, false, 0, 0xF000F0, false);
            if ($$18 != this.f_99256_ || $$14 < 0 || !$$7) continue;
            int $$21 = this.f_96541_.f_91062_.m_92895_($$19.substring(0, Math.max(Math.min($$14, $$19.length()), 0)));
            int $$22 = $$21 - this.f_96541_.f_91062_.m_92895_($$19) / 2;
            if ($$14 < $$19.length()) continue;
            this.f_96541_.f_91062_.m_92822_("_", $$22, $$16, $$13, false, $$17, $$9, false, 0, 0xF000F0, false);
        }
        $$9.m_109911_();
        for (int $$23 = 0; $$23 < this.f_99258_.length; ++$$23) {
            String $$24 = this.f_99258_[$$23];
            if ($$24 == null || $$23 != this.f_99256_ || $$14 < 0) continue;
            int $$25 = this.f_96541_.f_91062_.m_92895_($$24.substring(0, Math.max(Math.min($$14, $$24.length()), 0)));
            int $$26 = $$25 - this.f_96541_.f_91062_.m_92895_($$24) / 2;
            if ($$7 && $$14 < $$24.length()) {
                SignEditScreen.m_93172_(p_99271_, $$26, $$16 - 1, $$26 + 1, $$16 + this.f_96541_.f_91062_.f_92710_, 0xFF000000 | $$13);
            }
            if ($$15 == $$14) continue;
            int $$27 = Math.min($$14, $$15);
            int $$28 = Math.max($$14, $$15);
            int $$29 = this.f_96541_.f_91062_.m_92895_($$24.substring(0, $$27)) - this.f_96541_.f_91062_.m_92895_($$24) / 2;
            int $$30 = this.f_96541_.f_91062_.m_92895_($$24.substring(0, $$28)) - this.f_96541_.f_91062_.m_92895_($$24) / 2;
            int $$31 = Math.min($$29, $$30);
            int $$32 = Math.max($$29, $$30);
            Tesselator $$33 = Tesselator.m_85913_();
            BufferBuilder $$34 = $$33.m_85915_();
            RenderSystem.m_157427_(GameRenderer::m_172811_);
            RenderSystem.m_69472_();
            RenderSystem.m_69479_();
            RenderSystem.m_69835_(GlStateManager.LogicOp.OR_REVERSE);
            $$34.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
            $$34.m_85982_($$17, $$31, $$16 + this.f_96541_.f_91062_.f_92710_, 0.0f).m_6122_(0, 0, 255, 255).m_5752_();
            $$34.m_85982_($$17, $$32, $$16 + this.f_96541_.f_91062_.f_92710_, 0.0f).m_6122_(0, 0, 255, 255).m_5752_();
            $$34.m_85982_($$17, $$32, $$16, 0.0f).m_6122_(0, 0, 255, 255).m_5752_();
            $$34.m_85982_($$17, $$31, $$16, 0.0f).m_6122_(0, 0, 255, 255).m_5752_();
            BufferUploader.m_231202_($$34.m_231175_());
            RenderSystem.m_69462_();
            RenderSystem.m_69493_();
        }
        p_99271_.m_85849_();
        Lighting.m_84931_();
        super.m_6305_(p_99271_, p_99272_, p_99273_, p_99274_);
    }
}

