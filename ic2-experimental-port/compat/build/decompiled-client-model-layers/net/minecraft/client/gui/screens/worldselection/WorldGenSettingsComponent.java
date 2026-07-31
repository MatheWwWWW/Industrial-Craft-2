/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParser
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DataResult$PartialResult
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.Lifecycle
 *  org.lwjgl.util.tinyfd.TinyFileDialogs
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.worldselection;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import java.io.BufferedReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.PresetEditor;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.WorldPresetTags;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.slf4j.Logger;

public class WorldGenSettingsComponent
implements Widget {
    private static final Logger f_101381_ = LogUtils.getLogger();
    private static final Component f_101382_ = Component.m_237115_("generator.custom");
    private static final Component f_101383_ = Component.m_237115_("generator.minecraft.amplified.info");
    private static final Component f_101384_ = Component.m_237115_("selectWorld.mapFeatures.info");
    private static final Component f_170243_ = Component.m_237115_("selectWorld.import_worldgen_settings.select_file");
    private MultiLineLabel f_101385_ = MultiLineLabel.f_94331_;
    private Font f_101386_;
    private int f_101387_;
    private EditBox f_101388_;
    private CycleButton<Boolean> f_101389_;
    private CycleButton<Boolean> f_101380_;
    private CycleButton<Holder<WorldPreset>> f_101390_;
    private Button f_170244_;
    private Button f_101391_;
    private Button f_101392_;
    private WorldCreationContext f_101394_;
    private Optional<Holder<WorldPreset>> f_101395_;
    private OptionalLong f_101396_;

    public WorldGenSettingsComponent(WorldCreationContext p_233011_, Optional<ResourceKey<WorldPreset>> p_233012_, OptionalLong p_233013_) {
        this.f_101394_ = p_233011_;
        this.f_101395_ = WorldGenSettingsComponent.m_233047_(p_233011_, p_233012_);
        this.f_101396_ = p_233013_;
    }

    private static Optional<Holder<WorldPreset>> m_233047_(WorldCreationContext p_233048_, Optional<ResourceKey<WorldPreset>> p_233049_) {
        return p_233049_.flatMap(p_233046_ -> p_233048_.f_232989_().m_175515_(Registry.f_235726_).m_203636_((ResourceKey<WorldPreset>)p_233046_));
    }

    public void m_101429_(CreateWorldScreen p_101430_, Minecraft p_101431_, Font p_101432_) {
        this.f_101386_ = p_101432_;
        this.f_101387_ = p_101430_.f_96543_;
        this.f_101388_ = new EditBox(this.f_101386_, this.f_101387_ / 2 - 100, 60, 200, 20, Component.m_237115_("selectWorld.enterSeed"));
        this.f_101388_.m_94144_(WorldGenSettingsComponent.m_101447_(this.f_101396_));
        this.f_101388_.m_94151_(p_233063_ -> {
            this.f_101396_ = WorldGenSettings.m_202192_(this.f_101388_.m_94155_());
        });
        p_101430_.m_7787_(this.f_101388_);
        int $$3 = this.f_101387_ / 2 - 155;
        int $$4 = this.f_101387_ / 2 + 5;
        this.f_101389_ = p_101430_.m_142416_(CycleButton.m_168916_(this.f_101394_.f_232987_().m_224677_()).m_168959_(p_233081_ -> CommonComponents.m_178398_(p_233081_.m_168904_(), Component.m_237115_("selectWorld.mapFeatures.info"))).m_168936_($$3, 100, 150, 20, Component.m_237115_("selectWorld.mapFeatures"), (p_233083_, p_233084_) -> this.m_233038_(WorldGenSettings::m_224678_)));
        this.f_101389_.f_93624_ = false;
        Registry<WorldPreset> $$5 = this.f_101394_.f_232989_().m_175515_(Registry.f_235726_);
        List $$6 = WorldGenSettingsComponent.m_233059_($$5, WorldPresetTags.f_216053_).orElseGet(() -> $$5.m_203611_().collect(Collectors.toUnmodifiableList()));
        List<Holder<WorldPreset>> $$7 = WorldGenSettingsComponent.m_233059_($$5, WorldPresetTags.f_216054_).orElse($$6);
        this.f_101390_ = p_101430_.m_142416_(CycleButton.m_168894_(WorldGenSettingsComponent::m_233085_).m_168952_($$6, $$7).m_168959_(p_233030_ -> {
            if (WorldGenSettingsComponent.m_233050_((Holder)p_233030_.m_168883_())) {
                return CommonComponents.m_178398_(p_233030_.m_168904_(), f_101383_);
            }
            return p_233030_.m_168904_();
        }).m_168936_($$4, 100, 150, 20, Component.m_237115_("selectWorld.mapType"), (p_233036_, p_233037_) -> {
            this.f_101395_ = Optional.of(p_233037_);
            this.m_233038_(p_233054_ -> ((WorldPreset)p_233037_.m_203334_()).m_226427_((WorldGenSettings)p_233054_));
            p_101430_.m_170204_();
        }));
        this.f_101395_.ifPresent(this.f_101390_::m_168892_);
        this.f_101390_.f_93624_ = false;
        this.f_170244_ = p_101430_.m_142416_(new Button($$4, 100, 150, 20, CommonComponents.m_178393_(Component.m_237115_("selectWorld.mapType"), f_101382_), p_233028_ -> {}));
        this.f_170244_.f_93623_ = false;
        this.f_170244_.f_93624_ = false;
        this.f_101391_ = p_101430_.m_142416_(new Button($$4, 120, 150, 20, Component.m_237115_("selectWorld.customizeType"), p_233079_ -> {
            PresetEditor $$3 = PresetEditor.f_232950_.get(this.f_101395_.flatMap(Holder::m_203543_));
            if ($$3 != null) {
                p_101431_.m_91152_($$3.m_232976_(p_101430_, this.f_101394_));
            }
        }));
        this.f_101391_.f_93624_ = false;
        this.f_101380_ = p_101430_.m_142416_(CycleButton.m_168916_(this.f_101394_.f_232987_().m_64660_() && !p_101430_.f_100845_).m_168936_($$3, 151, 150, 20, Component.m_237115_("selectWorld.bonusItems"), (p_233032_, p_233033_) -> this.m_233038_(WorldGenSettings::m_64673_)));
        this.f_101380_.f_93624_ = false;
        this.f_101392_ = p_101430_.m_142416_(new Button($$3, 185, 150, 20, Component.m_237115_("selectWorld.import_worldgen_settings"), p_233026_ -> {
            DataResult $$10;
            String $$3 = TinyFileDialogs.tinyfd_openFileDialog((CharSequence)f_170243_.getString(), null, null, null, (boolean)false);
            if ($$3 == null) {
                return;
            }
            RegistryOps $$4 = RegistryOps.m_206821_(JsonOps.INSTANCE, this.f_101394_.f_232989_());
            try (BufferedReader $$5 = Files.newBufferedReader(Paths.get($$3, new String[0]));){
                JsonElement $$6 = JsonParser.parseReader((Reader)$$5);
                DataResult $$7 = WorldGenSettings.f_64600_.parse($$4, (Object)$$6);
            }
            catch (Exception $$9) {
                $$10 = DataResult.error((String)("Failed to parse file: " + $$9.getMessage()));
            }
            if ($$10.error().isPresent()) {
                MutableComponent $$11 = Component.m_237115_("selectWorld.import_worldgen_settings.failure");
                String $$12 = ((DataResult.PartialResult)$$10.error().get()).message();
                f_101381_.error("Error parsing world settings: {}", (Object)$$12);
                MutableComponent $$13 = Component.m_237113_($$12);
                p_101431_.m_91300_().m_94922_(SystemToast.m_94847_(p_101431_, SystemToast.SystemToastIds.WORLD_GEN_SETTINGS_TRANSFER, $$11, $$13));
                return;
            }
            Lifecycle $$14 = $$10.lifecycle();
            $$10.resultOrPartial(arg_0 -> ((Logger)f_101381_).error(arg_0)).ifPresent(p_233022_ -> WorldOpenFlows.m_233126_(p_101431_, p_101430_, $$14, () -> this.m_233016_((WorldGenSettings)p_233022_)));
        }));
        this.f_101392_.f_93624_ = false;
        this.f_101385_ = MultiLineLabel.m_94341_(p_101432_, f_101383_, this.f_101390_.m_5711_());
    }

    private static Optional<List<Holder<WorldPreset>>> m_233059_(Registry<WorldPreset> p_233060_, TagKey<WorldPreset> p_233061_) {
        return p_233060_.m_203431_(p_233061_).map(p_233056_ -> p_233056_.m_203614_().toList()).filter(p_233065_ -> !p_233065_.isEmpty());
    }

    private static boolean m_233050_(Holder<WorldPreset> p_233051_) {
        return p_233051_.m_203543_().filter(p_233073_ -> p_233073_.equals(WorldPresets.f_226440_)).isPresent();
    }

    private static Component m_233085_(Holder<WorldPreset> p_233086_) {
        return p_233086_.m_203543_().map(p_233015_ -> Component.m_237115_(p_233015_.m_135782_().m_214296_("generator"))).orElse(f_101382_);
    }

    private void m_233016_(WorldGenSettings p_233017_) {
        this.f_101394_ = this.f_101394_.m_232997_(p_233017_);
        this.f_101395_ = WorldGenSettingsComponent.m_233047_(this.f_101394_, WorldPresets.m_226445_(p_233017_));
        this.m_170289_(true);
        this.f_101396_ = OptionalLong.of(p_233017_.m_64619_());
        this.f_101388_.m_94144_(WorldGenSettingsComponent.m_101447_(this.f_101396_));
    }

    public void m_101469_() {
        this.f_101388_.m_94120_();
    }

    @Override
    public void m_6305_(PoseStack p_101407_, int p_101408_, int p_101409_, float p_101410_) {
        if (this.f_101389_.f_93624_) {
            this.f_101386_.m_92763_(p_101407_, f_101384_, this.f_101387_ / 2 - 150, 122.0f, -6250336);
        }
        this.f_101388_.m_6305_(p_101407_, p_101408_, p_101409_, p_101410_);
        if (this.f_101395_.filter(WorldGenSettingsComponent::m_233050_).isPresent()) {
            this.f_101385_.m_6516_(p_101407_, this.f_101390_.f_93620_ + 2, this.f_101390_.f_93621_ + 22, this.f_101386_.f_92710_, 0xA0A0A0);
        }
    }

    void m_233038_(WorldCreationContext.SimpleUpdater p_233039_) {
        this.f_101394_ = this.f_101394_.m_232999_(p_233039_);
    }

    void m_233040_(WorldCreationContext.Updater p_233041_) {
        this.f_101394_ = this.f_101394_.m_233001_(p_233041_);
    }

    void m_233042_(WorldCreationContext p_233043_) {
        this.f_101394_ = p_233043_;
    }

    private static String m_101447_(OptionalLong p_101448_) {
        if (p_101448_.isPresent()) {
            return Long.toString(p_101448_.getAsLong());
        }
        return "";
    }

    public WorldCreationContext m_233066_(boolean p_233067_) {
        OptionalLong $$1 = WorldGenSettings.m_202192_(this.f_101388_.m_94155_());
        return this.f_101394_.m_232999_(p_233071_ -> p_233071_.m_64654_(p_233067_, $$1));
    }

    public boolean m_101403_() {
        return this.f_101394_.f_232987_().m_64668_();
    }

    public void m_170287_(boolean p_170288_) {
        this.m_170289_(p_170288_);
        if (this.m_101403_()) {
            this.f_101389_.f_93624_ = false;
            this.f_101380_.f_93624_ = false;
            this.f_101391_.f_93624_ = false;
            this.f_101392_.f_93624_ = false;
        } else {
            this.f_101389_.f_93624_ = p_170288_;
            this.f_101380_.f_93624_ = p_170288_;
            this.f_101391_.f_93624_ = p_170288_ && PresetEditor.f_232950_.containsKey(this.f_101395_.flatMap(Holder::m_203543_));
            this.f_101392_.f_93624_ = p_170288_;
        }
        this.f_101388_.m_94194_(p_170288_);
    }

    private void m_170289_(boolean p_170290_) {
        if (this.f_101395_.isPresent()) {
            this.f_101390_.f_93624_ = p_170290_;
            this.f_170244_.f_93624_ = false;
        } else {
            this.f_101390_.f_93624_ = false;
            this.f_170244_.f_93624_ = p_170290_;
        }
    }

    public WorldCreationContext m_233087_() {
        return this.f_101394_;
    }

    public RegistryAccess m_205473_() {
        return this.f_101394_.f_232989_();
    }

    public void m_170291_() {
        this.f_101380_.f_93623_ = false;
        this.f_101380_.m_168892_(false);
    }

    public void m_170292_() {
        this.f_101380_.f_93623_ = true;
        this.f_101380_.m_168892_(this.f_101394_.f_232987_().m_64660_());
    }
}

