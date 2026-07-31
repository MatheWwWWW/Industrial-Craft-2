/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;

public class InventoryScreen
extends EffectRenderingInventoryScreen<InventoryMenu>
implements RecipeUpdateListener {
    private static final ResourceLocation f_98830_ = new ResourceLocation("textures/gui/recipe_button.png");
    private float f_98831_;
    private float f_98832_;
    private final RecipeBookComponent f_98833_ = new RecipeBookComponent();
    private boolean f_98834_;
    private boolean f_98835_;
    private boolean f_98836_;

    public InventoryScreen(Player p_98839_) {
        super(p_98839_.f_36095_, p_98839_.m_150109_(), Component.m_237115_("container.crafting"));
        this.f_96546_ = true;
        this.f_97728_ = 97;
    }

    @Override
    public void m_181908_() {
        if (this.f_96541_.f_91072_.m_105290_()) {
            this.f_96541_.m_91152_(new CreativeModeInventoryScreen(this.f_96541_.f_91074_));
            return;
        }
        this.f_98833_.m_100386_();
    }

    @Override
    protected void m_7856_() {
        if (this.f_96541_.f_91072_.m_105290_()) {
            this.f_96541_.m_91152_(new CreativeModeInventoryScreen(this.f_96541_.f_91074_));
            return;
        }
        super.m_7856_();
        this.f_98835_ = this.f_96543_ < 379;
        this.f_98833_.m_100309_(this.f_96543_, this.f_96544_, this.f_96541_, this.f_98835_, (RecipeBookMenu)this.f_97732_);
        this.f_98834_ = true;
        this.f_97735_ = this.f_98833_.m_181401_(this.f_96543_, this.f_97726_);
        this.m_142416_(new ImageButton(this.f_97735_ + 104, this.f_96544_ / 2 - 22, 20, 18, 0, 0, 19, f_98830_, p_98880_ -> {
            this.f_98833_.m_100384_();
            this.f_97735_ = this.f_98833_.m_181401_(this.f_96543_, this.f_97726_);
            ((ImageButton)p_98880_).m_94278_(this.f_97735_ + 104, this.f_96544_ / 2 - 22);
            this.f_98836_ = true;
        }));
        this.m_7787_(this.f_98833_);
        this.m_94718_(this.f_98833_);
    }

    @Override
    protected void m_7027_(PoseStack p_98889_, int p_98890_, int p_98891_) {
        this.f_96547_.m_92889_(p_98889_, this.f_96539_, this.f_97728_, this.f_97729_, 0x404040);
    }

    @Override
    public void m_6305_(PoseStack p_98875_, int p_98876_, int p_98877_, float p_98878_) {
        this.m_7333_(p_98875_);
        if (this.f_98833_.m_100385_() && this.f_98835_) {
            this.m_7286_(p_98875_, p_98878_, p_98876_, p_98877_);
            this.f_98833_.m_6305_(p_98875_, p_98876_, p_98877_, p_98878_);
        } else {
            this.f_98833_.m_6305_(p_98875_, p_98876_, p_98877_, p_98878_);
            super.m_6305_(p_98875_, p_98876_, p_98877_, p_98878_);
            this.f_98833_.m_6545_(p_98875_, this.f_97735_, this.f_97736_, false, p_98878_);
        }
        this.m_7025_(p_98875_, p_98876_, p_98877_);
        this.f_98833_.m_100361_(p_98875_, this.f_97735_, this.f_97736_, p_98876_, p_98877_);
        this.f_98831_ = p_98876_;
        this.f_98832_ = p_98877_;
    }

    @Override
    protected void m_7286_(PoseStack p_98870_, float p_98871_, int p_98872_, int p_98873_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_97725_);
        int $$4 = this.f_97735_;
        int $$5 = this.f_97736_;
        this.m_93228_(p_98870_, $$4, $$5, 0, 0, this.f_97726_, this.f_97727_);
        InventoryScreen.m_98850_($$4 + 51, $$5 + 75, 30, (float)($$4 + 51) - this.f_98831_, (float)($$5 + 75 - 50) - this.f_98832_, this.f_96541_.f_91074_);
    }

    public static void m_98850_(int p_98851_, int p_98852_, int p_98853_, float p_98854_, float p_98855_, LivingEntity p_98856_) {
        float $$6 = (float)Math.atan(p_98854_ / 40.0f);
        float $$7 = (float)Math.atan(p_98855_ / 40.0f);
        PoseStack $$8 = RenderSystem.m_157191_();
        $$8.m_85836_();
        $$8.m_85837_(p_98851_, p_98852_, 1050.0);
        $$8.m_85841_(1.0f, 1.0f, -1.0f);
        RenderSystem.m_157182_();
        PoseStack $$9 = new PoseStack();
        $$9.m_85837_(0.0, 0.0, 1000.0);
        $$9.m_85841_(p_98853_, p_98853_, p_98853_);
        Quaternion $$10 = Vector3f.f_122227_.m_122240_(180.0f);
        Quaternion $$11 = Vector3f.f_122223_.m_122240_($$7 * 20.0f);
        $$10.m_80148_($$11);
        $$9.m_85845_($$10);
        float $$12 = p_98856_.f_20883_;
        float $$13 = p_98856_.m_146908_();
        float $$14 = p_98856_.m_146909_();
        float $$15 = p_98856_.f_20886_;
        float $$16 = p_98856_.f_20885_;
        p_98856_.f_20883_ = 180.0f + $$6 * 20.0f;
        p_98856_.m_146922_(180.0f + $$6 * 40.0f);
        p_98856_.m_146926_(-$$7 * 20.0f);
        p_98856_.f_20885_ = p_98856_.m_146908_();
        p_98856_.f_20886_ = p_98856_.m_146908_();
        Lighting.m_166384_();
        EntityRenderDispatcher $$17 = Minecraft.m_91087_().m_91290_();
        $$11.m_80157_();
        $$17.m_114412_($$11);
        $$17.m_114468_(false);
        MultiBufferSource.BufferSource $$18 = Minecraft.m_91087_().m_91269_().m_110104_();
        RenderSystem.m_69890_(() -> $$17.m_114384_(p_98856_, 0.0, 0.0, 0.0, 0.0f, 1.0f, $$9, $$18, 0xF000F0));
        $$18.m_109911_();
        $$17.m_114468_(true);
        p_98856_.f_20883_ = $$12;
        p_98856_.m_146922_($$13);
        p_98856_.m_146926_($$14);
        p_98856_.f_20886_ = $$15;
        p_98856_.f_20885_ = $$16;
        $$8.m_85849_();
        RenderSystem.m_157182_();
        Lighting.m_84931_();
    }

    @Override
    protected boolean m_6774_(int p_98858_, int p_98859_, int p_98860_, int p_98861_, double p_98862_, double p_98863_) {
        return (!this.f_98835_ || !this.f_98833_.m_100385_()) && super.m_6774_(p_98858_, p_98859_, p_98860_, p_98861_, p_98862_, p_98863_);
    }

    @Override
    public boolean m_6375_(double p_98841_, double p_98842_, int p_98843_) {
        if (this.f_98833_.m_6375_(p_98841_, p_98842_, p_98843_)) {
            this.m_7522_(this.f_98833_);
            return true;
        }
        if (this.f_98835_ && this.f_98833_.m_100385_()) {
            return false;
        }
        return super.m_6375_(p_98841_, p_98842_, p_98843_);
    }

    @Override
    public boolean m_6348_(double p_98893_, double p_98894_, int p_98895_) {
        if (this.f_98836_) {
            this.f_98836_ = false;
            return true;
        }
        return super.m_6348_(p_98893_, p_98894_, p_98895_);
    }

    @Override
    protected boolean m_7467_(double p_98845_, double p_98846_, int p_98847_, int p_98848_, int p_98849_) {
        boolean $$5 = p_98845_ < (double)p_98847_ || p_98846_ < (double)p_98848_ || p_98845_ >= (double)(p_98847_ + this.f_97726_) || p_98846_ >= (double)(p_98848_ + this.f_97727_);
        return this.f_98833_.m_100297_(p_98845_, p_98846_, this.f_97735_, this.f_97736_, this.f_97726_, this.f_97727_, p_98849_) && $$5;
    }

    @Override
    protected void m_6597_(Slot p_98865_, int p_98866_, int p_98867_, ClickType p_98868_) {
        super.m_6597_(p_98865_, p_98866_, p_98867_, p_98868_);
        this.f_98833_.m_6904_(p_98865_);
    }

    @Override
    public void m_6916_() {
        this.f_98833_.m_100387_();
    }

    @Override
    public void m_7861_() {
        if (this.f_98834_) {
            this.f_98833_.m_100373_();
        }
        super.m_7861_();
    }

    @Override
    public RecipeBookComponent m_5564_() {
        return this.f_98833_;
    }
}

