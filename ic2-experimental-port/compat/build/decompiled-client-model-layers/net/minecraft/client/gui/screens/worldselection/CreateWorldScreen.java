/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableObject
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.client.gui.screens.worldselection.WorldGenSettingsComponent;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

public class CreateWorldScreen
extends Screen {
    private static final Logger f_100848_ = LogUtils.getLogger();
    private static final String f_170147_ = "mcworld-";
    private static final Component f_100849_ = Component.m_237115_("selectWorld.gameMode");
    private static final Component f_100850_ = Component.m_237115_("selectWorld.enterSeed");
    private static final Component f_100851_ = Component.m_237115_("selectWorld.seedInfo");
    private static final Component f_100852_ = Component.m_237115_("selectWorld.enterName");
    private static final Component f_100853_ = Component.m_237115_("selectWorld.resultFolder");
    private static final Component f_100854_ = Component.m_237115_("selectWorld.allowCommands.info");
    private static final Component f_232866_ = Component.m_237115_("createWorld.preparing");
    @Nullable
    private final Screen f_100855_;
    private EditBox f_100856_;
    String f_100857_;
    private SelectedGameMode f_100858_ = SelectedGameMode.SURVIVAL;
    @Nullable
    private SelectedGameMode f_100826_;
    private Difficulty f_170145_ = Difficulty.NORMAL;
    private boolean f_100829_;
    private boolean f_100830_;
    public boolean f_100845_;
    protected DataPackConfig f_100846_;
    @Nullable
    private Path f_100831_;
    @Nullable
    private PackRepository f_100832_;
    private boolean f_170146_;
    private Button f_100834_;
    private CycleButton<SelectedGameMode> f_100835_;
    private CycleButton<Difficulty> f_100836_;
    private Button f_100837_;
    private Button f_100838_;
    private Button f_100839_;
    private CycleButton<Boolean> f_100840_;
    private Component f_100841_;
    private Component f_100842_;
    private String f_100843_;
    private GameRules f_100844_ = new GameRules();
    public final WorldGenSettingsComponent f_100847_;

    public static void m_232896_(Minecraft p_232897_, @Nullable Screen p_232898_) {
        CreateWorldScreen.m_232899_(p_232897_, f_232866_);
        PackRepository $$2 = new PackRepository(PackType.SERVER_DATA, new ServerPacksSource());
        WorldLoader.InitConfig $$3 = CreateWorldScreen.m_232872_($$2, DataPackConfig.f_45842_);
        CompletableFuture<WorldCreationContext> $$4 = WorldLoader.m_214362_($$3, (p_232935_, p_232936_) -> {
            RegistryAccess.Frozen $$2 = RegistryAccess.m_206197_().m_203557_();
            WorldGenSettings $$3 = WorldPresets.m_226449_($$2);
            return Pair.of((Object)$$3, (Object)$$2);
        }, (p_232881_, p_232882_, p_232883_, p_232884_) -> {
            p_232881_.close();
            return new WorldCreationContext((WorldGenSettings)p_232884_, Lifecycle.stable(), p_232883_, p_232882_);
        }, Util.m_183991_(), p_232897_);
        p_232897_.m_18701_($$4::isDone);
        p_232897_.m_91152_(new CreateWorldScreen(p_232898_, DataPackConfig.f_45842_, new WorldGenSettingsComponent($$4.join(), Optional.of(WorldPresets.f_226437_), OptionalLong.empty())));
    }

    public static CreateWorldScreen m_205426_(@Nullable Screen p_205427_, WorldStem p_205428_, @Nullable Path p_205429_) {
        WorldData $$3 = p_205428_.f_206895_();
        LevelSettings $$4 = $$3.m_5926_();
        WorldGenSettings $$5 = $$3.m_5961_();
        RegistryAccess.Frozen $$6 = p_205428_.f_206894_();
        WorldCreationContext $$7 = new WorldCreationContext($$5, $$3.m_5754_(), $$6, p_205428_.f_206893_());
        DataPackConfig $$8 = $$4.m_46934_();
        CreateWorldScreen $$9 = new CreateWorldScreen(p_205427_, $$8, new WorldGenSettingsComponent($$7, WorldPresets.m_226445_($$5), OptionalLong.of($$5.m_64619_())));
        $$9.f_100843_ = $$4.m_46917_();
        $$9.f_100829_ = $$4.m_46932_();
        $$9.f_100830_ = true;
        $$9.f_170145_ = $$4.m_46931_();
        $$9.f_100844_.m_46176_($$4.m_46933_(), null);
        if ($$4.m_46930_()) {
            $$9.f_100858_ = SelectedGameMode.HARDCORE;
        } else if ($$4.m_46929_().m_46409_()) {
            $$9.f_100858_ = SelectedGameMode.SURVIVAL;
        } else if ($$4.m_46929_().m_46408_()) {
            $$9.f_100858_ = SelectedGameMode.CREATIVE;
        }
        $$9.f_100831_ = p_205429_;
        return $$9;
    }

    private CreateWorldScreen(@Nullable Screen p_100861_, DataPackConfig p_100862_, WorldGenSettingsComponent p_100863_) {
        super(Component.m_237115_("selectWorld.create"));
        this.f_100855_ = p_100861_;
        this.f_100843_ = I18n.m_118938_("selectWorld.newWorld", new Object[0]);
        this.f_100846_ = p_100862_;
        this.f_100847_ = p_100863_;
    }

    @Override
    public void m_86600_() {
        this.f_100856_.m_94120_();
        this.f_100847_.m_101469_();
    }

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_100856_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 100, 60, 200, 20, (Component)Component.m_237115_("selectWorld.enterName")){

            @Override
            protected MutableComponent m_5646_() {
                return CommonComponents.m_178398_(super.m_5646_(), Component.m_237115_("selectWorld.resultFolder")).m_130946_(" ").m_130946_(CreateWorldScreen.this.f_100857_);
            }
        };
        this.f_100856_.m_94144_(this.f_100843_);
        this.f_100856_.m_94151_(p_232916_ -> {
            this.f_100843_ = p_232916_;
            this.f_100834_.f_93623_ = !this.f_100856_.m_94155_().isEmpty();
            this.m_100971_();
        });
        this.m_7787_(this.f_100856_);
        int $$0 = this.f_96543_ / 2 - 155;
        int $$1 = this.f_96543_ / 2 + 5;
        this.f_100835_ = this.m_142416_((GuiEventListener & Widget)CycleButton.m_168894_(SelectedGameMode::m_170207_).m_168961_((SelectedGameMode[])new SelectedGameMode[]{SelectedGameMode.SURVIVAL, SelectedGameMode.HARDCORE, SelectedGameMode.CREATIVE}).m_168948_(this.f_100858_).m_168959_(p_232940_ -> AbstractWidget.m_168799_(p_232940_.m_6035_()).m_7220_(CommonComponents.f_178389_).m_7220_(this.f_100841_).m_130946_(" ").m_7220_(this.f_100842_)).m_168936_($$0, 100, 150, 20, f_100849_, (p_232910_, p_232911_) -> this.m_100900_((SelectedGameMode)((Object)p_232911_))));
        this.f_100836_ = this.m_142416_((GuiEventListener & Widget)CycleButton.m_168894_(Difficulty::m_19033_).m_168961_((Difficulty[])Difficulty.values()).m_168948_(this.m_170205_()).m_168936_($$1, 100, 150, 20, Component.m_237115_("options.difficulty"), (p_232907_, p_232908_) -> {
            this.f_170145_ = p_232908_;
        }));
        this.f_100840_ = this.m_142416_((GuiEventListener & Widget)CycleButton.m_168916_(this.f_100829_ && !this.f_100845_).m_168959_(p_232905_ -> CommonComponents.m_178398_(p_232905_.m_168904_(), Component.m_237115_("selectWorld.allowCommands.info"))).m_168936_($$0, 151, 150, 20, Component.m_237115_("selectWorld.allowCommands"), (p_232913_, p_232914_) -> {
            this.f_100830_ = true;
            this.f_100829_ = p_232914_;
        }));
        this.f_100839_ = this.m_142416_(new Button($$1, 151, 150, 20, Component.m_237115_("selectWorld.dataPacks"), p_232947_ -> this.m_100975_()));
        this.f_100838_ = this.m_142416_(new Button($$0, 185, 150, 20, Component.m_237115_("selectWorld.gameRules"), p_170188_ -> this.f_96541_.m_91152_(new EditGameRulesScreen(this.f_100844_.m_46202_(), p_232929_ -> {
            this.f_96541_.m_91152_(this);
            p_232929_.ifPresent(p_232892_ -> {
                this.f_100844_ = p_232892_;
            });
        }))));
        this.f_100847_.m_101429_(this, this.f_96541_, this.f_96547_);
        this.f_100837_ = this.m_142416_(new Button($$1, 185, 150, 20, Component.m_237115_("selectWorld.moreWorldOptions"), p_170158_ -> this.m_170148_()));
        this.f_100834_ = this.m_142416_(new Button($$0, this.f_96544_ - 28, 150, 20, Component.m_237115_("selectWorld.create"), p_232938_ -> this.m_100972_()));
        this.f_100834_.f_93623_ = !this.f_100843_.isEmpty();
        this.m_142416_(new Button($$1, this.f_96544_ - 28, 150, 20, CommonComponents.f_130656_, p_232903_ -> this.m_100967_()));
        this.m_170204_();
        this.m_94718_(this.f_100856_);
        this.m_100900_(this.f_100858_);
        this.m_100971_();
    }

    private Difficulty m_170205_() {
        return this.f_100858_ == SelectedGameMode.HARDCORE ? Difficulty.HARD : this.f_170145_;
    }

    private void m_100970_() {
        this.f_100841_ = Component.m_237115_("selectWorld.gameMode." + this.f_100858_.f_101028_ + ".line1");
        this.f_100842_ = Component.m_237115_("selectWorld.gameMode." + this.f_100858_.f_101028_ + ".line2");
    }

    private void m_100971_() {
        this.f_100857_ = this.f_100856_.m_94155_().trim();
        if (this.f_100857_.isEmpty()) {
            this.f_100857_ = "World";
        }
        try {
            this.f_100857_ = FileUtil.m_133730_(this.f_96541_.m_91392_().m_78257_(), this.f_100857_, "");
        }
        catch (Exception $$0) {
            this.f_100857_ = "World";
            try {
                this.f_100857_ = FileUtil.m_133730_(this.f_96541_.m_91392_().m_78257_(), this.f_100857_, "");
            }
            catch (Exception $$1) {
                throw new RuntimeException("Could not create save folder", $$1);
            }
        }
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    private static void m_232899_(Minecraft p_232900_, Component p_232901_) {
        p_232900_.m_91346_(new GenericDirtMessageScreen(p_232901_));
    }

    private void m_100972_() {
        WorldOpenFlows.m_233126_(this.f_96541_, this, this.f_100847_.m_233087_().f_232988_(), this::m_232867_);
    }

    private void m_232867_() {
        CreateWorldScreen.m_232899_(this.f_96541_, f_232866_);
        Optional<LevelStorageSource.LevelStorageAccess> $$0 = this.m_232868_();
        if ($$0.isEmpty()) {
            return;
        }
        this.m_100976_();
        WorldCreationContext $$1 = this.f_100847_.m_233066_(this.f_100845_);
        LevelSettings $$2 = this.m_205447_($$1.f_232987_().m_64668_());
        PrimaryLevelData $$3 = new PrimaryLevelData($$2, $$1.f_232987_(), $$1.f_232988_());
        this.f_96541_.m_231466_().m_233107_($$0.get(), $$1.f_232990_(), $$1.f_232989_(), $$3);
    }

    private LevelSettings m_205447_(boolean p_205448_) {
        String $$1 = this.f_100856_.m_94155_().trim();
        if (p_205448_) {
            GameRules $$2 = new GameRules();
            $$2.m_46170_(GameRules.f_46140_).m_46246_(false, null);
            return new LevelSettings($$1, GameType.SPECTATOR, false, Difficulty.PEACEFUL, true, $$2, DataPackConfig.f_45842_);
        }
        return new LevelSettings($$1, this.f_100858_.f_101029_, this.f_100845_, this.m_170205_(), this.f_100829_ && !this.f_100845_, this.f_100844_, this.f_100846_);
    }

    private void m_170148_() {
        this.m_170196_(!this.f_170146_);
    }

    private void m_100900_(SelectedGameMode p_100901_) {
        if (!this.f_100830_) {
            this.f_100829_ = p_100901_ == SelectedGameMode.CREATIVE;
            this.f_100840_.m_168892_(this.f_100829_);
        }
        if (p_100901_ == SelectedGameMode.HARDCORE) {
            this.f_100845_ = true;
            this.f_100840_.f_93623_ = false;
            this.f_100840_.m_168892_(false);
            this.f_100847_.m_170291_();
            this.f_100836_.m_168892_(Difficulty.HARD);
            this.f_100836_.f_93623_ = false;
        } else {
            this.f_100845_ = false;
            this.f_100840_.f_93623_ = true;
            this.f_100840_.m_168892_(this.f_100829_);
            this.f_100847_.m_170292_();
            this.f_100836_.m_168892_(this.f_170145_);
            this.f_100836_.f_93623_ = true;
        }
        this.f_100858_ = p_100901_;
        this.m_100970_();
    }

    public void m_170204_() {
        this.m_170196_(this.f_170146_);
    }

    private void m_170196_(boolean p_170197_) {
        this.f_170146_ = p_170197_;
        this.f_100835_.f_93624_ = !p_170197_;
        boolean bl = this.f_100836_.f_93624_ = !p_170197_;
        if (this.f_100847_.m_101403_()) {
            this.f_100839_.f_93624_ = false;
            this.f_100835_.f_93623_ = false;
            if (this.f_100826_ == null) {
                this.f_100826_ = this.f_100858_;
            }
            this.m_100900_(SelectedGameMode.DEBUG);
            this.f_100840_.f_93624_ = false;
        } else {
            this.f_100835_.f_93623_ = true;
            if (this.f_100826_ != null) {
                this.m_100900_(this.f_100826_);
            }
            this.f_100840_.f_93624_ = !p_170197_;
            this.f_100839_.f_93624_ = !p_170197_;
        }
        this.f_100847_.m_170287_(p_170197_);
        this.f_100856_.m_94194_(!p_170197_);
        if (p_170197_) {
            this.f_100837_.m_93666_(CommonComponents.f_130655_);
        } else {
            this.f_100837_.m_93666_(Component.m_237115_("selectWorld.moreWorldOptions"));
        }
        this.f_100838_.f_93624_ = !p_170197_;
    }

    @Override
    public boolean m_7933_(int p_100875_, int p_100876_, int p_100877_) {
        if (super.m_7933_(p_100875_, p_100876_, p_100877_)) {
            return true;
        }
        if (p_100875_ == 257 || p_100875_ == 335) {
            this.m_100972_();
            return true;
        }
        return false;
    }

    @Override
    public void m_7379_() {
        if (this.f_170146_) {
            this.m_170196_(false);
        } else {
            this.m_100967_();
        }
    }

    public void m_100967_() {
        this.f_96541_.m_91152_(this.f_100855_);
        this.m_100976_();
    }

    @Override
    public void m_6305_(PoseStack p_100890_, int p_100891_, int p_100892_, float p_100893_) {
        this.m_7333_(p_100890_);
        CreateWorldScreen.m_93215_(p_100890_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, -1);
        if (this.f_170146_) {
            CreateWorldScreen.m_93243_(p_100890_, this.f_96547_, f_100850_, this.f_96543_ / 2 - 100, 47, -6250336);
            CreateWorldScreen.m_93243_(p_100890_, this.f_96547_, f_100851_, this.f_96543_ / 2 - 100, 85, -6250336);
            this.f_100847_.m_6305_(p_100890_, p_100891_, p_100892_, p_100893_);
        } else {
            CreateWorldScreen.m_93243_(p_100890_, this.f_96547_, f_100852_, this.f_96543_ / 2 - 100, 47, -6250336);
            CreateWorldScreen.m_93243_(p_100890_, this.f_96547_, Component.m_237119_().m_7220_(f_100853_).m_130946_(" ").m_130946_(this.f_100857_), this.f_96543_ / 2 - 100, 85, -6250336);
            this.f_100856_.m_6305_(p_100890_, p_100891_, p_100892_, p_100893_);
            CreateWorldScreen.m_93243_(p_100890_, this.f_96547_, this.f_100841_, this.f_96543_ / 2 - 150, 122, -6250336);
            CreateWorldScreen.m_93243_(p_100890_, this.f_96547_, this.f_100842_, this.f_96543_ / 2 - 150, 134, -6250336);
            if (this.f_100840_.f_93624_) {
                CreateWorldScreen.m_93243_(p_100890_, this.f_96547_, f_100854_, this.f_96543_ / 2 - 150, 172, -6250336);
            }
        }
        super.m_6305_(p_100890_, p_100891_, p_100892_, p_100893_);
    }

    @Override
    protected <T extends GuiEventListener & NarratableEntry> T m_7787_(T p_100948_) {
        return super.m_7787_(p_100948_);
    }

    @Override
    protected <T extends GuiEventListener & Widget> T m_142416_(T p_170199_) {
        return super.m_142416_(p_170199_);
    }

    @Nullable
    private Path m_100968_() {
        if (this.f_100831_ == null) {
            try {
                this.f_100831_ = Files.createTempDirectory(f_170147_, new FileAttribute[0]);
            }
            catch (IOException $$0) {
                f_100848_.warn("Failed to create temporary dir", (Throwable)$$0);
                SystemToast.m_94875_(this.f_96541_, this.f_100857_);
                this.m_100967_();
            }
        }
        return this.f_100831_;
    }

    private void m_100975_() {
        Pair<File, PackRepository> $$0 = this.m_100871_();
        if ($$0 != null) {
            this.f_96541_.m_91152_(new PackSelectionScreen(this, (PackRepository)$$0.getSecond(), this::m_100878_, (File)$$0.getFirst(), Component.m_237115_("dataPack.title")));
        }
    }

    private void m_100878_(PackRepository p_100879_) {
        ImmutableList $$1 = ImmutableList.copyOf(p_100879_.m_10523_());
        List $$2 = (List)p_100879_.m_10514_().stream().filter(arg_0 -> CreateWorldScreen.m_232925_((List)$$1, arg_0)).collect(ImmutableList.toImmutableList());
        DataPackConfig $$3 = new DataPackConfig((List<String>)$$1, $$2);
        if ($$1.equals(this.f_100846_.m_45850_())) {
            this.f_100846_ = $$3;
            return;
        }
        this.f_96541_.m_6937_(() -> this.f_96541_.m_91152_(new GenericDirtMessageScreen(Component.m_237115_("dataPack.validation.working"))));
        WorldLoader.InitConfig $$4 = CreateWorldScreen.m_232872_(p_100879_, $$3);
        ((CompletableFuture)WorldLoader.m_214362_($$4, (p_232886_, p_232887_) -> {
            WorldCreationContext $$2 = this.f_100847_.m_233087_();
            RegistryAccess.Frozen $$3 = $$2.f_232989_();
            RegistryAccess.Writable $$4 = RegistryAccess.m_206197_();
            RegistryOps $$5 = RegistryOps.m_206821_(JsonOps.INSTANCE, $$3);
            RegistryOps $$6 = RegistryOps.m_206813_(JsonOps.INSTANCE, $$4, p_232886_);
            DataResult $$7 = WorldGenSettings.f_64600_.encodeStart($$5, (Object)$$2.f_232987_()).setLifecycle(Lifecycle.stable());
            DataResult $$8 = $$7.flatMap(p_232895_ -> WorldGenSettings.f_64600_.parse($$6, p_232895_));
            RegistryAccess.Frozen $$9 = $$4.m_203557_();
            Lifecycle $$10 = $$8.lifecycle().add($$9.m_211816_());
            WorldGenSettings $$11 = (WorldGenSettings)$$8.getOrThrow(false, Util.m_137489_("Error parsing worldgen settings after loading data packs: ", arg_0 -> ((Logger)f_100848_).error(arg_0)));
            if ($$9.m_175515_(Registry.f_235726_).m_13562_() == 0) {
                throw new IllegalStateException("Needs at least one world preset to continue");
            }
            if ($$9.m_175515_(Registry.f_122885_).m_13562_() == 0) {
                throw new IllegalStateException("Needs at least one biome continue");
            }
            return Pair.of((Object)Pair.of((Object)$$11, (Object)$$10), (Object)$$9);
        }, (p_232876_, p_232877_, p_232878_, p_232879_) -> {
            p_232876_.close();
            return new WorldCreationContext((WorldGenSettings)p_232879_.getFirst(), (Lifecycle)p_232879_.getSecond(), p_232878_, p_232877_);
        }, Util.m_183991_(), this.f_96541_).thenAcceptAsync(p_232890_ -> {
            this.f_100846_ = $$3;
            this.f_100847_.m_233042_((WorldCreationContext)p_232890_);
            this.m_232761_();
        }, (Executor)this.f_96541_)).handle((p_232918_, p_232919_) -> {
            if (p_232919_ != null) {
                f_100848_.warn("Failed to validate datapack", p_232919_);
                this.f_96541_.m_6937_(() -> this.f_96541_.m_91152_(new ConfirmScreen(p_232949_ -> {
                    if (p_232949_) {
                        this.m_100975_();
                    } else {
                        this.f_100846_ = DataPackConfig.f_45842_;
                        this.f_96541_.m_91152_(this);
                    }
                }, Component.m_237115_("dataPack.validation.failed"), CommonComponents.f_237098_, Component.m_237115_("dataPack.validation.back"), Component.m_237115_("dataPack.validation.reset"))));
            } else {
                this.f_96541_.m_6937_(() -> this.f_96541_.m_91152_(this));
            }
            return null;
        });
    }

    private static WorldLoader.InitConfig m_232872_(PackRepository p_232873_, DataPackConfig p_232874_) {
        WorldLoader.PackConfig $$2 = new WorldLoader.PackConfig(p_232873_, p_232874_, false);
        return new WorldLoader.InitConfig($$2, Commands.CommandSelection.INTEGRATED, 2);
    }

    private void m_100976_() {
        if (this.f_100831_ != null) {
            try (Stream<Path> $$0 = Files.walk(this.f_100831_, new FileVisitOption[0]);){
                $$0.sorted(Comparator.reverseOrder()).forEach(p_232942_ -> {
                    try {
                        Files.delete(p_232942_);
                    }
                    catch (IOException $$1) {
                        f_100848_.warn("Failed to remove temporary file {}", p_232942_, (Object)$$1);
                    }
                });
            }
            catch (IOException $$1) {
                f_100848_.warn("Failed to list temporary dir {}", (Object)this.f_100831_);
            }
            this.f_100831_ = null;
        }
    }

    private static void m_100912_(Path p_100913_, Path p_100914_, Path p_100915_) {
        try {
            Util.m_137563_(p_100913_, p_100914_, p_100915_);
        }
        catch (IOException $$3) {
            f_100848_.warn("Failed to copy datapack file from {} to {}", (Object)p_100915_, (Object)p_100914_);
            throw new UncheckedIOException($$3);
        }
    }

    private Optional<LevelStorageSource.LevelStorageAccess> m_232868_() {
        Optional<LevelStorageSource.LevelStorageAccess> optional;
        block12: {
            LevelStorageSource.LevelStorageAccess $$0;
            block11: {
                $$0 = this.f_96541_.m_91392_().m_78260_(this.f_100857_);
                if (this.f_100831_ != null) break block11;
                return Optional.of($$0);
            }
            Stream<Path> $$1 = Files.walk(this.f_100831_, new FileVisitOption[0]);
            try {
                Path $$2 = $$0.m_78283_(LevelResource.f_78180_);
                Files.createDirectories($$2, new FileAttribute[0]);
                $$1.filter(p_232921_ -> !p_232921_.equals(this.f_100831_)).forEach(p_232945_ -> CreateWorldScreen.m_100912_(this.f_100831_, $$2, p_232945_));
                optional = Optional.of($$0);
                if ($$1 == null) break block12;
            }
            catch (Throwable throwable) {
                try {
                    try {
                        if ($$1 != null) {
                            try {
                                $$1.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    catch (IOException | UncheckedIOException $$3) {
                        f_100848_.warn("Failed to copy datapacks to world {}", (Object)this.f_100857_, (Object)$$3);
                        $$0.close();
                    }
                }
                catch (IOException | UncheckedIOException $$4) {
                    f_100848_.warn("Failed to create access for {}", (Object)this.f_100857_, (Object)$$4);
                }
            }
            $$1.close();
        }
        return optional;
        SystemToast.m_94875_(this.f_96541_, this.f_100857_);
        this.m_100967_();
        return Optional.empty();
    }

    @Nullable
    public static Path m_100906_(Path p_100907_, Minecraft p_100908_) {
        MutableObject $$2 = new MutableObject();
        try (Stream<Path> $$3 = Files.walk(p_100907_, new FileVisitOption[0]);){
            $$3.filter(p_232924_ -> !p_232924_.equals(p_100907_)).forEach(p_232933_ -> {
                Path $$3 = (Path)$$2.getValue();
                if ($$3 == null) {
                    try {
                        $$3 = Files.createTempDirectory(f_170147_, new FileAttribute[0]);
                    }
                    catch (IOException $$4) {
                        f_100848_.warn("Failed to create temporary dir");
                        throw new UncheckedIOException($$4);
                    }
                    $$2.setValue((Object)$$3);
                }
                CreateWorldScreen.m_100912_(p_100907_, $$3, p_232933_);
            });
        }
        catch (IOException | UncheckedIOException $$4) {
            f_100848_.warn("Failed to copy datapacks from world {}", (Object)p_100907_, (Object)$$4);
            SystemToast.m_94875_(p_100908_, p_100907_.toString());
            return null;
        }
        return (Path)$$2.getValue();
    }

    @Nullable
    private Pair<File, PackRepository> m_100871_() {
        Path $$0 = this.m_100968_();
        if ($$0 != null) {
            File $$1 = $$0.toFile();
            if (this.f_100832_ == null) {
                this.f_100832_ = new PackRepository(PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource($$1, PackSource.f_10527_));
                this.f_100832_.m_10506_();
            }
            this.f_100832_.m_10509_(this.f_100846_.m_45850_());
            return Pair.of((Object)$$1, (Object)this.f_100832_);
        }
        return null;
    }

    private static /* synthetic */ boolean m_232925_(List p_232926_, String p_232927_) {
        return !p_232926_.contains(p_232927_);
    }

    static final class SelectedGameMode
    extends Enum<SelectedGameMode> {
        public static final /* enum */ SelectedGameMode SURVIVAL = new SelectedGameMode("survival", GameType.SURVIVAL);
        public static final /* enum */ SelectedGameMode HARDCORE = new SelectedGameMode("hardcore", GameType.SURVIVAL);
        public static final /* enum */ SelectedGameMode CREATIVE = new SelectedGameMode("creative", GameType.CREATIVE);
        public static final /* enum */ SelectedGameMode DEBUG = new SelectedGameMode("spectator", GameType.SPECTATOR);
        final String f_101028_;
        final GameType f_101029_;
        private final Component f_170206_;
        private static final /* synthetic */ SelectedGameMode[] $VALUES;

        public static SelectedGameMode[] values() {
            return (SelectedGameMode[])$VALUES.clone();
        }

        public static SelectedGameMode valueOf(String p_101042_) {
            return Enum.valueOf(SelectedGameMode.class, p_101042_);
        }

        private SelectedGameMode(String p_101035_, GameType p_101036_) {
            this.f_101028_ = p_101035_;
            this.f_101029_ = p_101036_;
            this.f_170206_ = Component.m_237115_("selectWorld.gameMode." + p_101035_);
        }

        public Component m_170207_() {
            return this.f_170206_;
        }

        private static /* synthetic */ SelectedGameMode[] m_170208_() {
            return new SelectedGameMode[]{SURVIVAL, HARDCORE, CREATIVE, DEBUG};
        }

        static {
            $VALUES = SelectedGameMode.m_170208_();
        }
    }
}

