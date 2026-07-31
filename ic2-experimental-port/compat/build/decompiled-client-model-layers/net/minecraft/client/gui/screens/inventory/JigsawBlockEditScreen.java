/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

public class JigsawBlockEditScreen
extends Screen {
    private static final int f_169762_ = 7;
    private static final Component f_98933_ = Component.m_237115_("jigsaw_block.joint_label");
    private static final Component f_98934_ = Component.m_237115_("jigsaw_block.pool");
    private static final Component f_98935_ = Component.m_237115_("jigsaw_block.name");
    private static final Component f_98936_ = Component.m_237115_("jigsaw_block.target");
    private static final Component f_98937_ = Component.m_237115_("jigsaw_block.final_state");
    private final JigsawBlockEntity f_98938_;
    private EditBox f_98939_;
    private EditBox f_98940_;
    private EditBox f_98941_;
    private EditBox f_98942_;
    int f_98943_;
    private boolean f_98944_ = true;
    private CycleButton<JigsawBlockEntity.JointType> f_98945_;
    private Button f_98946_;
    private Button f_169763_;
    private JigsawBlockEntity.JointType f_98932_;

    public JigsawBlockEditScreen(JigsawBlockEntity p_98949_) {
        super(GameNarrator.f_93310_);
        this.f_98938_ = p_98949_;
    }

    @Override
    public void m_86600_() {
        this.f_98939_.m_94120_();
        this.f_98940_.m_94120_();
        this.f_98941_.m_94120_();
        this.f_98942_.m_94120_();
    }

    private void m_98990_() {
        this.m_98992_();
        this.f_96541_.m_91152_(null);
    }

    private void m_98991_() {
        this.f_96541_.m_91152_(null);
    }

    private void m_98992_() {
        this.f_96541_.m_91403_().m_104955_(new ServerboundSetJigsawBlockPacket(this.f_98938_.m_58899_(), new ResourceLocation(this.f_98939_.m_94155_()), new ResourceLocation(this.f_98940_.m_94155_()), new ResourceLocation(this.f_98941_.m_94155_()), this.f_98942_.m_94155_(), this.f_98932_));
    }

    private void m_98993_() {
        this.f_96541_.m_91403_().m_104955_(new ServerboundJigsawGeneratePacket(this.f_98938_.m_58899_(), this.f_98943_, this.f_98944_));
    }

    @Override
    public void m_7379_() {
        this.m_98991_();
    }

    @Override
    protected void m_7856_() {
        boolean $$1;
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_98941_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 20, 300, 20, Component.m_237115_("jigsaw_block.pool"));
        this.f_98941_.m_94199_(128);
        this.f_98941_.m_94144_(this.f_98938_.m_222765_().m_135782_().toString());
        this.f_98941_.m_94151_(p_98986_ -> this.m_98994_());
        this.m_7787_(this.f_98941_);
        this.f_98939_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 55, 300, 20, Component.m_237115_("jigsaw_block.name"));
        this.f_98939_.m_94199_(128);
        this.f_98939_.m_94144_(this.f_98938_.m_59442_().toString());
        this.f_98939_.m_94151_(p_98981_ -> this.m_98994_());
        this.m_7787_(this.f_98939_);
        this.f_98940_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 90, 300, 20, Component.m_237115_("jigsaw_block.target"));
        this.f_98940_.m_94199_(128);
        this.f_98940_.m_94144_(this.f_98938_.m_59443_().toString());
        this.f_98940_.m_94151_(p_98977_ -> this.m_98994_());
        this.m_7787_(this.f_98940_);
        this.f_98942_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 125, 300, 20, Component.m_237115_("jigsaw_block.final_state"));
        this.f_98942_.m_94199_(256);
        this.f_98942_.m_94144_(this.f_98938_.m_59445_());
        this.m_7787_(this.f_98942_);
        this.f_98932_ = this.f_98938_.m_59446_();
        int $$0 = this.f_96547_.m_92852_(f_98933_) + 10;
        this.f_98945_ = this.m_142416_(CycleButton.m_168894_(JigsawBlockEntity.JointType::m_155610_).m_168961_((JigsawBlockEntity.JointType[])JigsawBlockEntity.JointType.values()).m_168948_(this.f_98932_).m_168929_().m_168936_(this.f_96543_ / 2 - 152 + $$0, 150, 300 - $$0, 20, f_98933_, (p_169765_, p_169766_) -> {
            this.f_98932_ = p_169766_;
        }));
        this.f_98945_.f_93623_ = $$1 = JigsawBlock.m_54250_(this.f_98938_.m_58900_()).m_122434_().m_122478_();
        this.f_98945_.f_93624_ = $$1;
        this.m_142416_(new AbstractSliderButton(this.f_96543_ / 2 - 154, 180, 100, 20, CommonComponents.f_237098_, 0.0){
            {
                this.m_5695_();
            }

            @Override
            protected void m_5695_() {
                this.m_93666_(Component.m_237110_("jigsaw_block.levels", JigsawBlockEditScreen.this.f_98943_));
            }

            @Override
            protected void m_5697_() {
                JigsawBlockEditScreen.this.f_98943_ = Mth.m_14107_(Mth.m_14085_(0.0, 7.0, this.f_93577_));
            }
        });
        this.m_142416_(CycleButton.m_168916_(this.f_98944_).m_168936_(this.f_96543_ / 2 - 50, 180, 100, 20, Component.m_237115_("jigsaw_block.keep_jigsaws"), (p_169768_, p_169769_) -> {
            this.f_98944_ = p_169769_;
        }));
        this.f_169763_ = this.m_142416_(new Button(this.f_96543_ / 2 + 54, 180, 100, 20, Component.m_237115_("jigsaw_block.generate"), p_98979_ -> {
            this.m_98990_();
            this.m_98993_();
        }));
        this.f_98946_ = this.m_142416_(new Button(this.f_96543_ / 2 - 4 - 150, 210, 150, 20, CommonComponents.f_130655_, p_98973_ -> this.m_98990_()));
        this.m_142416_(new Button(this.f_96543_ / 2 + 4, 210, 150, 20, CommonComponents.f_130656_, p_98964_ -> this.m_98991_()));
        this.m_94718_(this.f_98941_);
        this.m_98994_();
    }

    private void m_98994_() {
        boolean $$0;
        this.f_98946_.f_93623_ = $$0 = ResourceLocation.m_135830_(this.f_98939_.m_94155_()) && ResourceLocation.m_135830_(this.f_98940_.m_94155_()) && ResourceLocation.m_135830_(this.f_98941_.m_94155_());
        this.f_169763_.f_93623_ = $$0;
    }

    @Override
    public void m_6574_(Minecraft p_98960_, int p_98961_, int p_98962_) {
        String $$3 = this.f_98939_.m_94155_();
        String $$4 = this.f_98940_.m_94155_();
        String $$5 = this.f_98941_.m_94155_();
        String $$6 = this.f_98942_.m_94155_();
        int $$7 = this.f_98943_;
        JigsawBlockEntity.JointType $$8 = this.f_98932_;
        this.m_6575_(p_98960_, p_98961_, p_98962_);
        this.f_98939_.m_94144_($$3);
        this.f_98940_.m_94144_($$4);
        this.f_98941_.m_94144_($$5);
        this.f_98942_.m_94144_($$6);
        this.f_98943_ = $$7;
        this.f_98932_ = $$8;
        this.f_98945_.m_168892_($$8);
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_7933_(int p_98951_, int p_98952_, int p_98953_) {
        if (super.m_7933_(p_98951_, p_98952_, p_98953_)) {
            return true;
        }
        if (this.f_98946_.f_93623_ && (p_98951_ == 257 || p_98951_ == 335)) {
            this.m_98990_();
            return true;
        }
        return false;
    }

    @Override
    public void m_6305_(PoseStack p_98955_, int p_98956_, int p_98957_, float p_98958_) {
        this.m_7333_(p_98955_);
        JigsawBlockEditScreen.m_93243_(p_98955_, this.f_96547_, f_98934_, this.f_96543_ / 2 - 153, 10, 0xA0A0A0);
        this.f_98941_.m_6305_(p_98955_, p_98956_, p_98957_, p_98958_);
        JigsawBlockEditScreen.m_93243_(p_98955_, this.f_96547_, f_98935_, this.f_96543_ / 2 - 153, 45, 0xA0A0A0);
        this.f_98939_.m_6305_(p_98955_, p_98956_, p_98957_, p_98958_);
        JigsawBlockEditScreen.m_93243_(p_98955_, this.f_96547_, f_98936_, this.f_96543_ / 2 - 153, 80, 0xA0A0A0);
        this.f_98940_.m_6305_(p_98955_, p_98956_, p_98957_, p_98958_);
        JigsawBlockEditScreen.m_93243_(p_98955_, this.f_96547_, f_98937_, this.f_96543_ / 2 - 153, 115, 0xA0A0A0);
        this.f_98942_.m_6305_(p_98955_, p_98956_, p_98957_, p_98958_);
        if (JigsawBlock.m_54250_(this.f_98938_.m_58900_()).m_122434_().m_122478_()) {
            JigsawBlockEditScreen.m_93243_(p_98955_, this.f_96547_, f_98933_, this.f_96543_ / 2 - 153, 156, 0xFFFFFF);
        }
        super.m_6305_(p_98955_, p_98956_, p_98957_, p_98958_);
    }
}

