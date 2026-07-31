/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.CreateFlatWorldScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FlatLevelGeneratorPresetTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.slf4j.Logger;

public class PresetFlatWorldScreen
extends Screen {
    private static final Logger f_96368_ = LogUtils.getLogger();
    private static final int f_169346_ = 128;
    private static final int f_169347_ = 18;
    private static final int f_169348_ = 20;
    private static final int f_169349_ = 1;
    private static final int f_169350_ = 1;
    private static final int f_169351_ = 2;
    private static final int f_169352_ = 2;
    private static final ResourceKey<Biome> f_169353_ = Biomes.f_48202_;
    public static final Component f_232751_ = Component.m_237115_("flat_world_preset.unknown");
    private final CreateFlatWorldScreen f_96370_;
    private Component f_96371_;
    private Component f_96372_;
    private PresetsList f_96373_;
    private Button f_96374_;
    EditBox f_96375_;
    FlatLevelGeneratorSettings f_96376_;

    public PresetFlatWorldScreen(CreateFlatWorldScreen p_96379_) {
        super(Component.m_237115_("createWorld.customize.presets.title"));
        this.f_96370_ = p_96379_;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    private static FlatLayerInfo m_96413_(String p_96414_, int p_96415_) {
        void $$11;
        int $$5;
        String[] $$2 = p_96414_.split("\\*", 2);
        if ($$2.length == 2) {
            try {
                int $$3 = Math.max(Integer.parseInt($$2[0]), 0);
            }
            catch (NumberFormatException $$4) {
                f_96368_.error("Error while parsing flat world string => {}", (Object)$$4.getMessage());
                return null;
            }
        } else {
            $$5 = 1;
        }
        int $$6 = Math.min(p_96415_ + $$5, DimensionType.f_156651_);
        int $$7 = $$6 - p_96415_;
        String $$8 = $$2[$$2.length - 1];
        try {
            Block $$9 = Registry.f_122824_.m_6612_(new ResourceLocation($$8)).orElse(null);
        }
        catch (Exception $$10) {
            f_96368_.error("Error while parsing flat world string => {}", (Object)$$10.getMessage());
            return null;
        }
        if ($$11 == null) {
            f_96368_.error("Error while parsing flat world string => Unknown block, {}", (Object)$$8);
            return null;
        }
        return new FlatLayerInfo($$7, (Block)$$11);
    }

    private static List<FlatLayerInfo> m_96445_(String p_96446_) {
        ArrayList $$1 = Lists.newArrayList();
        String[] $$2 = p_96446_.split(",");
        int $$3 = 0;
        for (String $$4 : $$2) {
            FlatLayerInfo $$5 = PresetFlatWorldScreen.m_96413_($$4, $$3);
            if ($$5 == null) {
                return Collections.emptyList();
            }
            $$1.add($$5);
            $$3 += $$5.m_70337_();
        }
        return $$1;
    }

    public static FlatLevelGeneratorSettings m_211771_(Registry<Biome> p_211772_, Registry<StructureSet> p_211773_, String p_211774_, FlatLevelGeneratorSettings p_211775_) {
        Iterator $$4 = Splitter.on((char)';').split((CharSequence)p_211774_).iterator();
        if (!$$4.hasNext()) {
            return FlatLevelGeneratorSettings.m_211734_(p_211772_, p_211773_);
        }
        List<FlatLayerInfo> $$5 = PresetFlatWorldScreen.m_96445_((String)$$4.next());
        if ($$5.isEmpty()) {
            return FlatLevelGeneratorSettings.m_211734_(p_211772_, p_211773_);
        }
        FlatLevelGeneratorSettings $$6 = p_211775_.m_209803_($$5, p_211775_.m_209810_());
        ResourceKey<Biome> $$7 = f_169353_;
        if ($$4.hasNext()) {
            try {
                ResourceLocation $$8 = new ResourceLocation((String)$$4.next());
                $$7 = ResourceKey.m_135785_(Registry.f_122885_, $$8);
                p_211772_.m_123009_($$7).orElseThrow(() -> new IllegalArgumentException("Invalid Biome: " + $$8));
            }
            catch (Exception $$9) {
                f_96368_.error("Error while parsing flat world string => {}", (Object)$$9.getMessage());
                $$7 = f_169353_;
            }
        }
        $$6.m_204918_(p_211772_.m_214121_($$7));
        return $$6;
    }

    static String m_205393_(FlatLevelGeneratorSettings p_205394_) {
        StringBuilder $$1 = new StringBuilder();
        for (int $$2 = 0; $$2 < p_205394_.m_70401_().size(); ++$$2) {
            if ($$2 > 0) {
                $$1.append(",");
            }
            $$1.append(p_205394_.m_70401_().get($$2));
        }
        $$1.append(";");
        $$1.append(p_205394_.m_204921_().m_203543_().map(ResourceKey::m_135782_).orElseThrow(() -> new IllegalStateException("Biome not registered")));
        return $$1.toString();
    }

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_96371_ = Component.m_237115_("createWorld.customize.presets.share");
        this.f_96372_ = Component.m_237115_("createWorld.customize.presets.list");
        this.f_96375_ = new EditBox(this.f_96547_, 50, 40, this.f_96543_ - 100, 20, this.f_96371_);
        this.f_96375_.m_94199_(1230);
        RegistryAccess $$0 = this.f_96370_.f_95814_.f_100847_.m_205473_();
        Registry<Biome> $$1 = $$0.m_175515_(Registry.f_122885_);
        Registry<StructureSet> $$2 = $$0.m_175515_(Registry.f_211073_);
        this.f_96375_.m_94144_(PresetFlatWorldScreen.m_205393_(this.f_96370_.m_95846_()));
        this.f_96376_ = this.f_96370_.m_95846_();
        this.m_7787_(this.f_96375_);
        this.f_96373_ = new PresetsList(this.f_96370_.f_95814_.f_100847_.m_205473_());
        this.m_7787_(this.f_96373_);
        this.f_96374_ = this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 28, 150, 20, Component.m_237115_("createWorld.customize.presets.select"), p_211770_ -> {
            FlatLevelGeneratorSettings $$3 = PresetFlatWorldScreen.m_211771_($$1, $$2, this.f_96375_.m_94155_(), this.f_96376_);
            this.f_96370_.m_95825_($$3);
            this.f_96541_.m_91152_(this.f_96370_);
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ - 28, 150, 20, CommonComponents.f_130656_, p_96394_ -> this.f_96541_.m_91152_(this.f_96370_)));
        this.m_96449_(this.f_96373_.m_93511_() != null);
    }

    @Override
    public boolean m_6050_(double p_96381_, double p_96382_, double p_96383_) {
        return this.f_96373_.m_6050_(p_96381_, p_96382_, p_96383_);
    }

    @Override
    public void m_6574_(Minecraft p_96390_, int p_96391_, int p_96392_) {
        String $$3 = this.f_96375_.m_94155_();
        this.m_6575_(p_96390_, p_96391_, p_96392_);
        this.f_96375_.m_94144_($$3);
    }

    @Override
    public void m_7379_() {
        this.f_96541_.m_91152_(this.f_96370_);
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public void m_6305_(PoseStack p_96385_, int p_96386_, int p_96387_, float p_96388_) {
        this.m_7333_(p_96385_);
        this.f_96373_.m_6305_(p_96385_, p_96386_, p_96387_, p_96388_);
        p_96385_.m_85836_();
        p_96385_.m_85837_(0.0, 0.0, 400.0);
        PresetFlatWorldScreen.m_93215_(p_96385_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 0xFFFFFF);
        PresetFlatWorldScreen.m_93243_(p_96385_, this.f_96547_, this.f_96371_, 50, 30, 0xA0A0A0);
        PresetFlatWorldScreen.m_93243_(p_96385_, this.f_96547_, this.f_96372_, 50, 70, 0xA0A0A0);
        p_96385_.m_85849_();
        this.f_96375_.m_6305_(p_96385_, p_96386_, p_96387_, p_96388_);
        super.m_6305_(p_96385_, p_96386_, p_96387_, p_96388_);
    }

    @Override
    public void m_86600_() {
        this.f_96375_.m_94120_();
        super.m_86600_();
    }

    public void m_96449_(boolean p_96450_) {
        this.f_96374_.f_93623_ = p_96450_ || this.f_96375_.m_94155_().length() > 1;
    }

    class PresetsList
    extends ObjectSelectionList<Entry> {
        public PresetsList(RegistryAccess p_232754_) {
            super(PresetFlatWorldScreen.this.f_96541_, PresetFlatWorldScreen.this.f_96543_, PresetFlatWorldScreen.this.f_96544_, 80, PresetFlatWorldScreen.this.f_96544_ - 37, 24);
            for (Holder<FlatLevelGeneratorPreset> $$1 : p_232754_.m_175515_(Registry.f_235727_).m_206058_(FlatLevelGeneratorPresetTags.f_215848_)) {
                this.m_7085_(new Entry($$1));
            }
        }

        @Override
        public void m_6987_(@Nullable Entry p_96472_) {
            super.m_6987_(p_96472_);
            PresetFlatWorldScreen.this.m_96449_(p_96472_ != null);
        }

        @Override
        protected boolean m_5694_() {
            return PresetFlatWorldScreen.this.m_7222_() == this;
        }

        @Override
        public boolean m_7933_(int p_96466_, int p_96467_, int p_96468_) {
            if (super.m_7933_(p_96466_, p_96467_, p_96468_)) {
                return true;
            }
            if ((p_96466_ == 257 || p_96466_ == 335) && this.m_93511_() != null) {
                ((Entry)this.m_93511_()).m_96479_();
            }
            return false;
        }

        public class Entry
        extends ObjectSelectionList.Entry<Entry> {
            private final FlatLevelGeneratorPreset f_169357_;
            private final Component f_232755_;

            public Entry(Holder<FlatLevelGeneratorPreset> p_232758_) {
                this.f_169357_ = p_232758_.m_203334_();
                this.f_232755_ = p_232758_.m_203543_().map(p_232760_ -> Component.m_237115_(p_232760_.m_135782_().m_214296_("flat_world_preset"))).orElse(f_232751_);
            }

            @Override
            public void m_6311_(PoseStack p_96489_, int p_96490_, int p_96491_, int p_96492_, int p_96493_, int p_96494_, int p_96495_, int p_96496_, boolean p_96497_, float p_96498_) {
                this.m_96499_(p_96489_, p_96492_, p_96491_, this.f_169357_.f_226245_().m_203334_());
                PresetFlatWorldScreen.this.f_96547_.m_92889_(p_96489_, this.f_232755_, p_96492_ + 18 + 5, p_96491_ + 6, 0xFFFFFF);
            }

            @Override
            public boolean m_6375_(double p_96481_, double p_96482_, int p_96483_) {
                if (p_96483_ == 0) {
                    this.m_96479_();
                }
                return false;
            }

            void m_96479_() {
                PresetsList.this.m_6987_(this);
                PresetFlatWorldScreen.this.f_96376_ = this.f_169357_.f_226246_();
                PresetFlatWorldScreen.this.f_96375_.m_94144_(PresetFlatWorldScreen.m_205393_(PresetFlatWorldScreen.this.f_96376_));
                PresetFlatWorldScreen.this.f_96375_.m_94198_();
            }

            private void m_96499_(PoseStack p_96500_, int p_96501_, int p_96502_, Item p_96503_) {
                this.m_96484_(p_96500_, p_96501_ + 1, p_96502_ + 1);
                PresetFlatWorldScreen.this.f_96542_.m_115123_(new ItemStack(p_96503_), p_96501_ + 2, p_96502_ + 2);
            }

            private void m_96484_(PoseStack p_96485_, int p_96486_, int p_96487_) {
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.m_157456_(0, GuiComponent.f_93097_);
                GuiComponent.m_93143_(p_96485_, p_96486_, p_96487_, PresetFlatWorldScreen.this.m_93252_(), 0.0f, 0.0f, 18, 18, 128, 128);
            }

            @Override
            public Component m_142172_() {
                return Component.m_237110_("narrator.select", this.f_232755_);
            }
        }
    }
}

