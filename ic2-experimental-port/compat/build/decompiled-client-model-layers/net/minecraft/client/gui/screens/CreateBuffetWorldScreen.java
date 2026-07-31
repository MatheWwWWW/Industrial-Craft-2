/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ibm.icu.text.Collator
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens;

import com.ibm.icu.text.Collator;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class CreateBuffetWorldScreen
extends Screen {
    private static final Component f_95742_ = Component.m_237115_("createWorld.customize.buffet.biome");
    private final Screen f_95743_;
    private final Consumer<Holder<Biome>> f_95744_;
    final Registry<Biome> f_95745_;
    private BiomeList f_95746_;
    Holder<Biome> f_95747_;
    private Button f_95748_;

    public CreateBuffetWorldScreen(Screen p_232732_, WorldCreationContext p_232733_, Consumer<Holder<Biome>> p_232734_) {
        super(Component.m_237115_("createWorld.customize.buffet.title"));
        this.f_95743_ = p_232732_;
        this.f_95744_ = p_232734_;
        this.f_95745_ = p_232733_.f_232989_().m_175515_(Registry.f_122885_);
        Holder<Biome> $$3 = this.f_95745_.m_203636_(Biomes.f_48202_).or(() -> this.f_95745_.m_203611_().findAny()).orElseThrow();
        this.f_95747_ = p_232733_.f_232987_().m_64666_().m_62218_().m_207840_().stream().findFirst().orElse($$3);
    }

    @Override
    public void m_7379_() {
        this.f_96541_.m_91152_(this.f_95743_);
    }

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_95746_ = new BiomeList();
        this.m_7787_(this.f_95746_);
        this.f_95748_ = this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 28, 150, 20, CommonComponents.f_130655_, p_95761_ -> {
            this.f_95744_.accept(this.f_95747_);
            this.f_96541_.m_91152_(this.f_95743_);
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ - 28, 150, 20, CommonComponents.f_130656_, p_232736_ -> this.f_96541_.m_91152_(this.f_95743_)));
        this.f_95746_.m_6987_((BiomeList.Entry)this.f_95746_.m_6702_().stream().filter(p_232738_ -> Objects.equals(p_232738_.f_95792_, this.f_95747_)).findFirst().orElse(null));
    }

    void m_95775_() {
        this.f_95748_.f_93623_ = this.f_95746_.m_93511_() != null;
    }

    @Override
    public void m_6305_(PoseStack p_95756_, int p_95757_, int p_95758_, float p_95759_) {
        this.m_96626_(0);
        this.f_95746_.m_6305_(p_95756_, p_95757_, p_95758_, p_95759_);
        CreateBuffetWorldScreen.m_93215_(p_95756_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 0xFFFFFF);
        CreateBuffetWorldScreen.m_93215_(p_95756_, this.f_96547_, f_95742_, this.f_96543_ / 2, 28, 0xA0A0A0);
        super.m_6305_(p_95756_, p_95757_, p_95758_, p_95759_);
    }

    class BiomeList
    extends ObjectSelectionList<Entry> {
        BiomeList() {
            super(CreateBuffetWorldScreen.this.f_96541_, CreateBuffetWorldScreen.this.f_96543_, CreateBuffetWorldScreen.this.f_96544_, 40, CreateBuffetWorldScreen.this.f_96544_ - 37, 16);
            Collator $$0 = Collator.getInstance((Locale)Locale.getDefault());
            CreateBuffetWorldScreen.this.f_95745_.m_203611_().map(p_205389_ -> new Entry((Holder.Reference<Biome>)p_205389_)).sorted(Comparator.comparing(p_203142_ -> p_203142_.f_95793_.getString(), $$0)).forEach(p_203138_ -> this.m_7085_(p_203138_));
        }

        @Override
        protected boolean m_5694_() {
            return CreateBuffetWorldScreen.this.m_7222_() == this;
        }

        @Override
        public void m_6987_(@Nullable Entry p_95785_) {
            super.m_6987_(p_95785_);
            if (p_95785_ != null) {
                CreateBuffetWorldScreen.this.f_95747_ = p_95785_.f_95792_;
            }
            CreateBuffetWorldScreen.this.m_95775_();
        }

        class Entry
        extends ObjectSelectionList.Entry<Entry> {
            final Holder.Reference<Biome> f_95792_;
            final Component f_95793_;

            public Entry(Holder.Reference<Biome> p_205392_) {
                this.f_95792_ = p_205392_;
                ResourceLocation $$1 = p_205392_.m_205785_().m_135782_();
                String $$2 = $$1.m_214296_("biome");
                this.f_95793_ = Language.m_128107_().m_6722_($$2) ? Component.m_237115_($$2) : Component.m_237113_($$1.toString());
            }

            @Override
            public Component m_142172_() {
                return Component.m_237110_("narrator.select", this.f_95793_);
            }

            @Override
            public void m_6311_(PoseStack p_95802_, int p_95803_, int p_95804_, int p_95805_, int p_95806_, int p_95807_, int p_95808_, int p_95809_, boolean p_95810_, float p_95811_) {
                GuiComponent.m_93243_(p_95802_, CreateBuffetWorldScreen.this.f_96547_, this.f_95793_, p_95805_ + 5, p_95804_ + 2, 0xFFFFFF);
            }

            @Override
            public boolean m_6375_(double p_95798_, double p_95799_, int p_95800_) {
                if (p_95800_ == 0) {
                    BiomeList.this.m_6987_(this);
                    return true;
                }
                return false;
            }
        }
    }
}

