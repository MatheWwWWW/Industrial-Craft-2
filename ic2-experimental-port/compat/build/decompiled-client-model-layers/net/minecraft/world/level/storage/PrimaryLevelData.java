/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelVersion;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.timers.TimerCallbacks;
import net.minecraft.world.level.timers.TimerQueue;
import org.slf4j.Logger;

public class PrimaryLevelData
implements ServerLevelData,
WorldData {
    private static final Logger f_78442_ = LogUtils.getLogger();
    protected static final String f_202314_ = "Player";
    protected static final String f_164940_ = "WorldGenSettings";
    private LevelSettings f_78443_;
    private final WorldGenSettings f_78444_;
    private final Lifecycle f_78445_;
    private int f_78446_;
    private int f_78447_;
    private int f_78448_;
    private float f_78449_;
    private long f_78450_;
    private long f_78451_;
    @Nullable
    private final DataFixer f_78452_;
    private final int f_78453_;
    private boolean f_78454_;
    @Nullable
    private CompoundTag f_78455_;
    private final int f_78456_;
    private int f_78457_;
    private boolean f_78458_;
    private int f_78459_;
    private boolean f_78460_;
    private int f_78461_;
    private boolean f_78462_;
    private boolean f_78463_;
    private WorldBorder.Settings f_78464_;
    private CompoundTag f_78465_;
    @Nullable
    private CompoundTag f_78466_;
    private int f_78467_;
    private int f_78437_;
    @Nullable
    private UUID f_78438_;
    private final Set<String> f_78439_;
    private boolean f_78440_;
    private final TimerQueue<MinecraftServer> f_78441_;

    private PrimaryLevelData(@Nullable DataFixer p_164942_, int p_164943_, @Nullable CompoundTag p_164944_, boolean p_164945_, int p_164946_, int p_164947_, int p_164948_, float p_164949_, long p_164950_, long p_164951_, int p_164952_, int p_164953_, int p_164954_, boolean p_164955_, int p_164956_, boolean p_164957_, boolean p_164958_, boolean p_164959_, WorldBorder.Settings p_164960_, int p_164961_, int p_164962_, @Nullable UUID p_164963_, Set<String> p_164964_, TimerQueue<MinecraftServer> p_164965_, @Nullable CompoundTag p_164966_, CompoundTag p_164967_, LevelSettings p_164968_, WorldGenSettings p_164969_, Lifecycle p_164970_) {
        if (!p_164969_.m_204655_().m_142003_(LevelStem.f_63971_)) {
            throw new IllegalStateException("Missing Overworld dimension data");
        }
        this.f_78452_ = p_164942_;
        this.f_78440_ = p_164945_;
        this.f_78446_ = p_164946_;
        this.f_78447_ = p_164947_;
        this.f_78448_ = p_164948_;
        this.f_78449_ = p_164949_;
        this.f_78450_ = p_164950_;
        this.f_78451_ = p_164951_;
        this.f_78456_ = p_164952_;
        this.f_78457_ = p_164953_;
        this.f_78459_ = p_164954_;
        this.f_78458_ = p_164955_;
        this.f_78461_ = p_164956_;
        this.f_78460_ = p_164957_;
        this.f_78462_ = p_164958_;
        this.f_78463_ = p_164959_;
        this.f_78464_ = p_164960_;
        this.f_78467_ = p_164961_;
        this.f_78437_ = p_164962_;
        this.f_78438_ = p_164963_;
        this.f_78439_ = p_164964_;
        this.f_78455_ = p_164944_;
        this.f_78453_ = p_164943_;
        this.f_78441_ = p_164965_;
        this.f_78466_ = p_164966_;
        this.f_78465_ = p_164967_;
        this.f_78443_ = p_164968_;
        this.f_78444_ = p_164969_;
        this.f_78445_ = p_164970_;
    }

    public PrimaryLevelData(LevelSettings p_78470_, WorldGenSettings p_78471_, Lifecycle p_78472_) {
        this(null, SharedConstants.m_183709_().getWorldVersion(), null, false, 0, 0, 0, 0.0f, 0L, 0L, 19133, 0, 0, false, 0, false, false, false, WorldBorder.f_61907_, 0, 0, null, Sets.newLinkedHashSet(), new TimerQueue<MinecraftServer>(TimerCallbacks.f_82226_), null, new CompoundTag(), p_78470_.m_46935_(), p_78471_, p_78472_);
    }

    public static PrimaryLevelData m_78530_(Dynamic<Tag> p_78531_, DataFixer p_78532_, int p_78533_, @Nullable CompoundTag p_78534_, LevelSettings p_78535_, LevelVersion p_78536_, WorldGenSettings p_78537_, Lifecycle p_78538_) {
        long $$8 = p_78531_.get("Time").asLong(0L);
        CompoundTag $$9 = (CompoundTag)p_78531_.get("DragonFight").result().map(Dynamic::getValue).orElseGet(() -> (Tag)p_78531_.get("DimensionData").get("1").get("DragonFight").orElseEmptyMap().getValue());
        return new PrimaryLevelData(p_78532_, p_78533_, p_78534_, p_78531_.get("WasModded").asBoolean(false), p_78531_.get("SpawnX").asInt(0), p_78531_.get("SpawnY").asInt(0), p_78531_.get("SpawnZ").asInt(0), p_78531_.get("SpawnAngle").asFloat(0.0f), $$8, p_78531_.get("DayTime").asLong($$8), p_78536_.m_78389_(), p_78531_.get("clearWeatherTime").asInt(0), p_78531_.get("rainTime").asInt(0), p_78531_.get("raining").asBoolean(false), p_78531_.get("thunderTime").asInt(0), p_78531_.get("thundering").asBoolean(false), p_78531_.get("initialized").asBoolean(true), p_78531_.get("DifficultyLocked").asBoolean(false), WorldBorder.Settings.m_62037_(p_78531_, WorldBorder.f_61907_), p_78531_.get("WanderingTraderSpawnDelay").asInt(0), p_78531_.get("WanderingTraderSpawnChance").asInt(0), p_78531_.get("WanderingTraderId").read(UUIDUtil.f_235867_).result().orElse(null), p_78531_.get("ServerBrands").asStream().flatMap(p_78529_ -> p_78529_.asString().result().stream()).collect(Collectors.toCollection(Sets::newLinkedHashSet)), new TimerQueue<MinecraftServer>(TimerCallbacks.f_82226_, p_78531_.get("ScheduledEvents").asStream()), (CompoundTag)p_78531_.get("CustomBossEvents").orElseEmptyMap().getValue(), $$9, p_78535_, p_78537_, p_78538_);
    }

    @Override
    public CompoundTag m_6626_(RegistryAccess p_78543_, @Nullable CompoundTag p_78544_) {
        this.m_78512_();
        if (p_78544_ == null) {
            p_78544_ = this.f_78455_;
        }
        CompoundTag $$2 = new CompoundTag();
        this.m_78545_(p_78543_, $$2, p_78544_);
        return $$2;
    }

    private void m_78545_(RegistryAccess p_78546_, CompoundTag p_78547_, @Nullable CompoundTag p_78548_) {
        ListTag $$3 = new ListTag();
        this.f_78439_.stream().map(StringTag::m_129297_).forEach($$3::add);
        p_78547_.m_128365_("ServerBrands", $$3);
        p_78547_.m_128379_("WasModded", this.f_78440_);
        CompoundTag $$4 = new CompoundTag();
        $$4.m_128359_("Name", SharedConstants.m_183709_().getName());
        $$4.m_128405_("Id", SharedConstants.m_183709_().m_183476_().m_193006_());
        $$4.m_128379_("Snapshot", !SharedConstants.m_183709_().isStable());
        $$4.m_128359_("Series", SharedConstants.m_183709_().m_183476_().m_193005_());
        p_78547_.m_128365_("Version", $$4);
        p_78547_.m_128405_("DataVersion", SharedConstants.m_183709_().getWorldVersion());
        RegistryOps<Tag> $$5 = RegistryOps.m_206821_(NbtOps.f_128958_, p_78546_);
        WorldGenSettings.f_64600_.encodeStart($$5, (Object)this.f_78444_).resultOrPartial(Util.m_137489_("WorldGenSettings: ", arg_0 -> ((Logger)f_78442_).error(arg_0))).ifPresent(p_78574_ -> p_78547_.m_128365_(f_164940_, (Tag)p_78574_));
        p_78547_.m_128405_("GameType", this.f_78443_.m_46929_().m_46392_());
        p_78547_.m_128405_("SpawnX", this.f_78446_);
        p_78547_.m_128405_("SpawnY", this.f_78447_);
        p_78547_.m_128405_("SpawnZ", this.f_78448_);
        p_78547_.m_128350_("SpawnAngle", this.f_78449_);
        p_78547_.m_128356_("Time", this.f_78450_);
        p_78547_.m_128356_("DayTime", this.f_78451_);
        p_78547_.m_128356_("LastPlayed", Util.m_137574_());
        p_78547_.m_128359_("LevelName", this.f_78443_.m_46917_());
        p_78547_.m_128405_("version", 19133);
        p_78547_.m_128405_("clearWeatherTime", this.f_78457_);
        p_78547_.m_128405_("rainTime", this.f_78459_);
        p_78547_.m_128379_("raining", this.f_78458_);
        p_78547_.m_128405_("thunderTime", this.f_78461_);
        p_78547_.m_128379_("thundering", this.f_78460_);
        p_78547_.m_128379_("hardcore", this.f_78443_.m_46930_());
        p_78547_.m_128379_("allowCommands", this.f_78443_.m_46932_());
        p_78547_.m_128379_("initialized", this.f_78462_);
        this.f_78464_.m_62040_(p_78547_);
        p_78547_.m_128344_("Difficulty", (byte)this.f_78443_.m_46931_().m_19028_());
        p_78547_.m_128379_("DifficultyLocked", this.f_78463_);
        p_78547_.m_128365_("GameRules", this.f_78443_.m_46933_().m_46163_());
        p_78547_.m_128365_("DragonFight", this.f_78465_);
        if (p_78548_ != null) {
            p_78547_.m_128365_(f_202314_, p_78548_);
        }
        DataPackConfig.f_45843_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_78443_.m_46934_()).result().ifPresent(p_78560_ -> p_78547_.m_128365_("DataPacks", (Tag)p_78560_));
        if (this.f_78466_ != null) {
            p_78547_.m_128365_("CustomBossEvents", this.f_78466_);
        }
        p_78547_.m_128365_("ScheduledEvents", this.f_78441_.m_82267_());
        p_78547_.m_128405_("WanderingTraderSpawnDelay", this.f_78467_);
        p_78547_.m_128405_("WanderingTraderSpawnChance", this.f_78437_);
        if (this.f_78438_ != null) {
            p_78547_.m_128362_("WanderingTraderId", this.f_78438_);
        }
    }

    @Override
    public int m_6789_() {
        return this.f_78446_;
    }

    @Override
    public int m_6527_() {
        return this.f_78447_;
    }

    @Override
    public int m_6526_() {
        return this.f_78448_;
    }

    @Override
    public float m_6790_() {
        return this.f_78449_;
    }

    @Override
    public long m_6793_() {
        return this.f_78450_;
    }

    @Override
    public long m_6792_() {
        return this.f_78451_;
    }

    private void m_78512_() {
        if (this.f_78454_ || this.f_78455_ == null) {
            return;
        }
        if (this.f_78453_ < SharedConstants.m_183709_().getWorldVersion()) {
            if (this.f_78452_ == null) {
                throw Util.m_137570_(new NullPointerException("Fixer Upper not set inside LevelData, and the player tag is not upgraded."));
            }
            this.f_78455_ = NbtUtils.m_129213_(this.f_78452_, DataFixTypes.PLAYER, this.f_78455_, this.f_78453_);
        }
        this.f_78454_ = true;
    }

    @Override
    public CompoundTag m_6614_() {
        this.m_78512_();
        return this.f_78455_;
    }

    @Override
    public void m_6395_(int p_78565_) {
        this.f_78446_ = p_78565_;
    }

    @Override
    public void m_6397_(int p_78579_) {
        this.f_78447_ = p_78579_;
    }

    @Override
    public void m_6400_(int p_78584_) {
        this.f_78448_ = p_78584_;
    }

    @Override
    public void m_7113_(float p_78515_) {
        this.f_78449_ = p_78515_;
    }

    @Override
    public void m_6253_(long p_78519_) {
        this.f_78450_ = p_78519_;
    }

    @Override
    public void m_6247_(long p_78567_) {
        this.f_78451_ = p_78567_;
    }

    @Override
    public void m_7250_(BlockPos p_78540_, float p_78541_) {
        this.f_78446_ = p_78540_.m_123341_();
        this.f_78447_ = p_78540_.m_123342_();
        this.f_78448_ = p_78540_.m_123343_();
        this.f_78449_ = p_78541_;
    }

    @Override
    public String m_5462_() {
        return this.f_78443_.m_46917_();
    }

    @Override
    public int m_6517_() {
        return this.f_78456_;
    }

    @Override
    public int m_6537_() {
        return this.f_78457_;
    }

    @Override
    public void m_6393_(int p_78517_) {
        this.f_78457_ = p_78517_;
    }

    @Override
    public boolean m_6534_() {
        return this.f_78460_;
    }

    @Override
    public void m_5557_(boolean p_78562_) {
        this.f_78460_ = p_78562_;
    }

    @Override
    public int m_6558_() {
        return this.f_78461_;
    }

    @Override
    public void m_6398_(int p_78589_) {
        this.f_78461_ = p_78589_;
    }

    @Override
    public boolean m_6533_() {
        return this.f_78458_;
    }

    @Override
    public void m_5565_(boolean p_78576_) {
        this.f_78458_ = p_78576_;
    }

    @Override
    public int m_6531_() {
        return this.f_78459_;
    }

    @Override
    public void m_6399_(int p_78592_) {
        this.f_78459_ = p_78592_;
    }

    @Override
    public GameType m_5464_() {
        return this.f_78443_.m_46929_();
    }

    @Override
    public void m_5458_(GameType p_78525_) {
        this.f_78443_ = this.f_78443_.m_46922_(p_78525_);
    }

    @Override
    public boolean m_5466_() {
        return this.f_78443_.m_46930_();
    }

    @Override
    public boolean m_5468_() {
        return this.f_78443_.m_46932_();
    }

    @Override
    public boolean m_6535_() {
        return this.f_78462_;
    }

    @Override
    public void m_5555_(boolean p_78581_) {
        this.f_78462_ = p_78581_;
    }

    @Override
    public GameRules m_5470_() {
        return this.f_78443_.m_46933_();
    }

    @Override
    public WorldBorder.Settings m_5813_() {
        return this.f_78464_;
    }

    @Override
    public void m_7831_(WorldBorder.Settings p_78527_) {
        this.f_78464_ = p_78527_;
    }

    @Override
    public Difficulty m_5472_() {
        return this.f_78443_.m_46931_();
    }

    @Override
    public void m_6166_(Difficulty p_78521_) {
        this.f_78443_ = this.f_78443_.m_46918_(p_78521_);
    }

    @Override
    public boolean m_5474_() {
        return this.f_78463_;
    }

    @Override
    public void m_5560_(boolean p_78586_) {
        this.f_78463_ = p_78586_;
    }

    @Override
    public TimerQueue<MinecraftServer> m_7540_() {
        return this.f_78441_;
    }

    @Override
    public void m_142471_(CrashReportCategory p_164972_, LevelHeightAccessor p_164973_) {
        ServerLevelData.super.m_142471_(p_164972_, p_164973_);
        WorldData.super.m_5461_(p_164972_);
    }

    @Override
    public WorldGenSettings m_5961_() {
        return this.f_78444_;
    }

    @Override
    public Lifecycle m_5754_() {
        return this.f_78445_;
    }

    @Override
    public CompoundTag m_6564_() {
        return this.f_78465_;
    }

    @Override
    public void m_5915_(CompoundTag p_78557_) {
        this.f_78465_ = p_78557_;
    }

    @Override
    public DataPackConfig m_7513_() {
        return this.f_78443_.m_46934_();
    }

    @Override
    public void m_6645_(DataPackConfig p_78523_) {
        this.f_78443_ = this.f_78443_.m_46920_(p_78523_);
    }

    @Override
    @Nullable
    public CompoundTag m_6587_() {
        return this.f_78466_;
    }

    @Override
    public void m_5917_(@Nullable CompoundTag p_78571_) {
        this.f_78466_ = p_78571_;
    }

    @Override
    public int m_6530_() {
        return this.f_78467_;
    }

    @Override
    public void m_6391_(int p_78595_) {
        this.f_78467_ = p_78595_;
    }

    @Override
    public int m_6528_() {
        return this.f_78437_;
    }

    @Override
    public void m_6387_(int p_78598_) {
        this.f_78437_ = p_78598_;
    }

    @Override
    @Nullable
    public UUID m_142403_() {
        return this.f_78438_;
    }

    @Override
    public void m_8115_(UUID p_78553_) {
        this.f_78438_ = p_78553_;
    }

    @Override
    public void m_7955_(String p_78550_, boolean p_78551_) {
        this.f_78439_.add(p_78550_);
        this.f_78440_ |= p_78551_;
    }

    @Override
    public boolean m_6565_() {
        return this.f_78440_;
    }

    @Override
    public Set<String> m_6161_() {
        return ImmutableSet.copyOf(this.f_78439_);
    }

    @Override
    public ServerLevelData m_5996_() {
        return this;
    }

    @Override
    public LevelSettings m_5926_() {
        return this.f_78443_.m_46935_();
    }
}

