/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class StructureBlockEntity
extends BlockEntity {
    private static final int f_155777_ = 5;
    public static final int f_155774_ = 48;
    public static final int f_155775_ = 48;
    public static final String f_155776_ = "author";
    private ResourceLocation f_59812_;
    private String f_59813_ = "";
    private String f_59814_ = "";
    private BlockPos f_59815_ = new BlockPos(0, 1, 0);
    private Vec3i f_59816_ = Vec3i.f_123288_;
    private Mirror f_59817_ = Mirror.NONE;
    private Rotation f_59818_ = Rotation.NONE;
    private StructureMode f_59819_;
    private boolean f_59820_ = true;
    private boolean f_59821_;
    private boolean f_59822_;
    private boolean f_59823_ = true;
    private float f_59824_ = 1.0f;
    private long f_59825_;

    public StructureBlockEntity(BlockPos p_155779_, BlockState p_155780_) {
        super(BlockEntityType.f_58936_, p_155779_, p_155780_);
        this.f_59819_ = p_155780_.m_61143_(StructureBlock.f_57110_);
    }

    @Override
    protected void m_183515_(CompoundTag p_187524_) {
        super.m_183515_(p_187524_);
        p_187524_.m_128359_("name", this.m_59895_());
        p_187524_.m_128359_(f_155776_, this.f_59813_);
        p_187524_.m_128359_("metadata", this.f_59814_);
        p_187524_.m_128405_("posX", this.f_59815_.m_123341_());
        p_187524_.m_128405_("posY", this.f_59815_.m_123342_());
        p_187524_.m_128405_("posZ", this.f_59815_.m_123343_());
        p_187524_.m_128405_("sizeX", this.f_59816_.m_123341_());
        p_187524_.m_128405_("sizeY", this.f_59816_.m_123342_());
        p_187524_.m_128405_("sizeZ", this.f_59816_.m_123343_());
        p_187524_.m_128359_("rotation", this.f_59818_.toString());
        p_187524_.m_128359_("mirror", this.f_59817_.toString());
        p_187524_.m_128359_("mode", this.f_59819_.toString());
        p_187524_.m_128379_("ignoreEntities", this.f_59820_);
        p_187524_.m_128379_("powered", this.f_59821_);
        p_187524_.m_128379_("showair", this.f_59822_);
        p_187524_.m_128379_("showboundingbox", this.f_59823_);
        p_187524_.m_128350_("integrity", this.f_59824_);
        p_187524_.m_128356_("seed", this.f_59825_);
    }

    @Override
    public void m_142466_(CompoundTag p_155800_) {
        super.m_142466_(p_155800_);
        this.m_59868_(p_155800_.m_128461_("name"));
        this.f_59813_ = p_155800_.m_128461_(f_155776_);
        this.f_59814_ = p_155800_.m_128461_("metadata");
        int $$1 = Mth.m_14045_(p_155800_.m_128451_("posX"), -48, 48);
        int $$2 = Mth.m_14045_(p_155800_.m_128451_("posY"), -48, 48);
        int $$3 = Mth.m_14045_(p_155800_.m_128451_("posZ"), -48, 48);
        this.f_59815_ = new BlockPos($$1, $$2, $$3);
        int $$4 = Mth.m_14045_(p_155800_.m_128451_("sizeX"), 0, 48);
        int $$5 = Mth.m_14045_(p_155800_.m_128451_("sizeY"), 0, 48);
        int $$6 = Mth.m_14045_(p_155800_.m_128451_("sizeZ"), 0, 48);
        this.f_59816_ = new Vec3i($$4, $$5, $$6);
        try {
            this.f_59818_ = Rotation.valueOf(p_155800_.m_128461_("rotation"));
        }
        catch (IllegalArgumentException $$7) {
            this.f_59818_ = Rotation.NONE;
        }
        try {
            this.f_59817_ = Mirror.valueOf(p_155800_.m_128461_("mirror"));
        }
        catch (IllegalArgumentException $$8) {
            this.f_59817_ = Mirror.NONE;
        }
        try {
            this.f_59819_ = StructureMode.valueOf(p_155800_.m_128461_("mode"));
        }
        catch (IllegalArgumentException $$9) {
            this.f_59819_ = StructureMode.DATA;
        }
        this.f_59820_ = p_155800_.m_128471_("ignoreEntities");
        this.f_59821_ = p_155800_.m_128471_("powered");
        this.f_59822_ = p_155800_.m_128471_("showair");
        this.f_59823_ = p_155800_.m_128471_("showboundingbox");
        this.f_59824_ = p_155800_.m_128441_("integrity") ? p_155800_.m_128457_("integrity") : 1.0f;
        this.f_59825_ = p_155800_.m_128454_("seed");
        this.m_59836_();
    }

    private void m_59836_() {
        if (this.f_58857_ == null) {
            return;
        }
        BlockPos $$0 = this.m_58899_();
        BlockState $$1 = this.f_58857_.m_8055_($$0);
        if ($$1.m_60713_(Blocks.f_50677_)) {
            this.f_58857_.m_7731_($$0, (BlockState)$$1.m_61124_(StructureBlock.f_57110_, this.f_59819_), 2);
        }
    }

    public ClientboundBlockEntityDataPacket m_58483_() {
        return ClientboundBlockEntityDataPacket.m_195640_(this);
    }

    @Override
    public CompoundTag m_5995_() {
        return this.m_187482_();
    }

    public boolean m_59853_(Player p_59854_) {
        if (!p_59854_.m_36337_()) {
            return false;
        }
        if (p_59854_.m_20193_().f_46443_) {
            p_59854_.m_5966_(this);
        }
        return true;
    }

    public String m_59895_() {
        return this.f_59812_ == null ? "" : this.f_59812_.toString();
    }

    public String m_59900_() {
        return this.f_59812_ == null ? "" : this.f_59812_.m_135815_();
    }

    public boolean m_59901_() {
        return this.f_59812_ != null;
    }

    public void m_59868_(@Nullable String p_59869_) {
        this.m_59874_(StringUtil.m_14408_(p_59869_) ? null : ResourceLocation.m_135820_(p_59869_));
    }

    public void m_59874_(@Nullable ResourceLocation p_59875_) {
        this.f_59812_ = p_59875_;
    }

    public void m_59851_(LivingEntity p_59852_) {
        this.f_59813_ = p_59852_.m_7755_().getString();
    }

    public BlockPos m_59902_() {
        return this.f_59815_;
    }

    public void m_59885_(BlockPos p_59886_) {
        this.f_59815_ = p_59886_;
    }

    public Vec3i m_155805_() {
        return this.f_59816_;
    }

    public void m_155797_(Vec3i p_155798_) {
        this.f_59816_ = p_155798_;
    }

    public Mirror m_59905_() {
        return this.f_59817_;
    }

    public void m_59881_(Mirror p_59882_) {
        this.f_59817_ = p_59882_;
    }

    public Rotation m_59906_() {
        return this.f_59818_;
    }

    public void m_59883_(Rotation p_59884_) {
        this.f_59818_ = p_59884_;
    }

    public String m_59907_() {
        return this.f_59814_;
    }

    public void m_59887_(String p_59888_) {
        this.f_59814_ = p_59888_;
    }

    public StructureMode m_59908_() {
        return this.f_59819_;
    }

    public void m_59860_(StructureMode p_59861_) {
        this.f_59819_ = p_59861_;
        BlockState $$1 = this.f_58857_.m_8055_(this.m_58899_());
        if ($$1.m_60713_(Blocks.f_50677_)) {
            this.f_58857_.m_7731_(this.m_58899_(), (BlockState)$$1.m_61124_(StructureBlock.f_57110_, p_59861_), 2);
        }
    }

    public boolean m_59910_() {
        return this.f_59820_;
    }

    public void m_59876_(boolean p_59877_) {
        this.f_59820_ = p_59877_;
    }

    public float m_59827_() {
        return this.f_59824_;
    }

    public void m_59838_(float p_59839_) {
        this.f_59824_ = p_59839_;
    }

    public long m_59828_() {
        return this.f_59825_;
    }

    public void m_59840_(long p_59841_) {
        this.f_59825_ = p_59841_;
    }

    public boolean m_59829_() {
        if (this.f_59819_ != StructureMode.SAVE) {
            return false;
        }
        BlockPos $$0 = this.m_58899_();
        int $$1 = 80;
        BlockPos $$2 = new BlockPos($$0.m_123341_() - 80, this.f_58857_.m_141937_(), $$0.m_123343_() - 80);
        BlockPos $$3 = new BlockPos($$0.m_123341_() + 80, this.f_58857_.m_151558_() - 1, $$0.m_123343_() + 80);
        Stream<BlockPos> $$4 = this.m_155791_($$2, $$3);
        return StructureBlockEntity.m_155794_($$0, $$4).filter(p_155790_ -> {
            int $$2 = p_155790_.m_162399_() - p_155790_.m_162395_();
            int $$3 = p_155790_.m_162400_() - p_155790_.m_162396_();
            int $$4 = p_155790_.m_162401_() - p_155790_.m_162398_();
            if ($$2 > 1 && $$3 > 1 && $$4 > 1) {
                this.f_59815_ = new BlockPos(p_155790_.m_162395_() - $$0.m_123341_() + 1, p_155790_.m_162396_() - $$0.m_123342_() + 1, p_155790_.m_162398_() - $$0.m_123343_() + 1);
                this.f_59816_ = new Vec3i($$2 - 1, $$3 - 1, $$4 - 1);
                this.m_6596_();
                BlockState $$5 = this.f_58857_.m_8055_($$0);
                this.f_58857_.m_7260_($$0, $$5, $$5, 3);
                return true;
            }
            return false;
        }).isPresent();
    }

    private Stream<BlockPos> m_155791_(BlockPos p_155792_, BlockPos p_155793_) {
        return BlockPos.m_121990_(p_155792_, p_155793_).filter(p_155804_ -> this.f_58857_.m_8055_((BlockPos)p_155804_).m_60713_(Blocks.f_50677_)).map(this.f_58857_::m_7702_).filter(p_155802_ -> p_155802_ instanceof StructureBlockEntity).map(p_155785_ -> (StructureBlockEntity)p_155785_).filter(p_155787_ -> p_155787_.f_59819_ == StructureMode.CORNER && Objects.equals(this.f_59812_, p_155787_.f_59812_)).map(BlockEntity::m_58899_);
    }

    private static Optional<BoundingBox> m_155794_(BlockPos p_155795_, Stream<BlockPos> p_155796_) {
        Iterator $$2 = p_155796_.iterator();
        if (!$$2.hasNext()) {
            return Optional.empty();
        }
        BlockPos $$3 = (BlockPos)$$2.next();
        BoundingBox $$4 = new BoundingBox($$3);
        if ($$2.hasNext()) {
            $$2.forEachRemaining($$4::m_162371_);
        } else {
            $$4.m_162371_(p_155795_);
        }
        return Optional.of($$4);
    }

    public boolean m_59830_() {
        return this.m_59889_(true);
    }

    /*
     * WARNING - void declaration
     */
    public boolean m_59889_(boolean p_59890_) {
        void $$6;
        if (this.f_59819_ != StructureMode.SAVE || this.f_58857_.f_46443_ || this.f_59812_ == null) {
            return false;
        }
        BlockPos $$1 = this.m_58899_().m_121955_(this.f_59815_);
        ServerLevel $$2 = (ServerLevel)this.f_58857_;
        StructureTemplateManager $$3 = $$2.m_215082_();
        try {
            StructureTemplate $$4 = $$3.m_230359_(this.f_59812_);
        }
        catch (ResourceLocationException $$5) {
            return false;
        }
        $$6.m_163802_(this.f_58857_, $$1, this.f_59816_, !this.f_59820_, Blocks.f_50454_);
        $$6.m_74612_(this.f_59813_);
        if (p_59890_) {
            try {
                return $$3.m_230416_(this.f_59812_);
            }
            catch (ResourceLocationException $$7) {
                return false;
            }
        }
        return true;
    }

    public boolean m_59842_(ServerLevel p_59843_) {
        return this.m_59844_(p_59843_, true);
    }

    public static RandomSource m_222888_(long p_222889_) {
        if (p_222889_ == 0L) {
            return RandomSource.m_216335_(Util.m_137550_());
        }
        return RandomSource.m_216335_(p_222889_);
    }

    /*
     * WARNING - void declaration
     */
    public boolean m_59844_(ServerLevel p_59845_, boolean p_59846_) {
        void $$5;
        if (this.f_59819_ != StructureMode.LOAD || this.f_59812_ == null) {
            return false;
        }
        StructureTemplateManager $$2 = p_59845_.m_215082_();
        try {
            Optional<StructureTemplate> $$3 = $$2.m_230407_(this.f_59812_);
        }
        catch (ResourceLocationException $$4) {
            return false;
        }
        if (!$$5.isPresent()) {
            return false;
        }
        return this.m_59847_(p_59845_, p_59846_, (StructureTemplate)$$5.get());
    }

    public boolean m_59847_(ServerLevel p_59848_, boolean p_59849_, StructureTemplate p_59850_) {
        Vec3i $$4;
        boolean $$5;
        BlockPos $$3 = this.m_58899_();
        if (!StringUtil.m_14408_(p_59850_.m_74627_())) {
            this.f_59813_ = p_59850_.m_74627_();
        }
        if (!($$5 = this.f_59816_.equals($$4 = p_59850_.m_163801_()))) {
            this.f_59816_ = $$4;
            this.m_6596_();
            BlockState $$6 = p_59848_.m_8055_($$3);
            p_59848_.m_7260_($$3, $$6, $$6, 3);
        }
        if (!p_59849_ || $$5) {
            StructurePlaceSettings $$7 = new StructurePlaceSettings().m_74377_(this.f_59817_).m_74379_(this.f_59818_).m_74392_(this.f_59820_);
            if (this.f_59824_ < 1.0f) {
                $$7.m_74394_().m_74383_(new BlockRotProcessor(Mth.m_14036_(this.f_59824_, 0.0f, 1.0f))).m_230324_(StructureBlockEntity.m_222888_(this.f_59825_));
            }
            BlockPos $$8 = $$3.m_121955_(this.f_59815_);
            p_59850_.m_230328_(p_59848_, $$8, $$8, $$7, StructureBlockEntity.m_222888_(this.f_59825_), 2);
            return true;
        }
        return false;
    }

    public void m_59831_() {
        if (this.f_59812_ == null) {
            return;
        }
        ServerLevel $$0 = (ServerLevel)this.f_58857_;
        StructureTemplateManager $$1 = $$0.m_215082_();
        $$1.m_230421_(this.f_59812_);
    }

    public boolean m_59832_() {
        if (this.f_59819_ != StructureMode.LOAD || this.f_58857_.f_46443_ || this.f_59812_ == null) {
            return false;
        }
        ServerLevel $$0 = (ServerLevel)this.f_58857_;
        StructureTemplateManager $$1 = $$0.m_215082_();
        try {
            return $$1.m_230407_(this.f_59812_).isPresent();
        }
        catch (ResourceLocationException $$2) {
            return false;
        }
    }

    public boolean m_59833_() {
        return this.f_59821_;
    }

    public void m_59893_(boolean p_59894_) {
        this.f_59821_ = p_59894_;
    }

    public boolean m_59834_() {
        return this.f_59822_;
    }

    public void m_59896_(boolean p_59897_) {
        this.f_59822_ = p_59897_;
    }

    public boolean m_59835_() {
        return this.f_59823_;
    }

    public void m_59898_(boolean p_59899_) {
        this.f_59823_ = p_59899_;
    }

    public /* synthetic */ Packet m_58483_() {
        return this.m_58483_();
    }

    private static /* synthetic */ void m_155781_(ServerLevel p_155782_, BlockPos p_155783_) {
        p_155782_.m_7731_(p_155783_, Blocks.f_50454_.m_49966_(), 2);
    }

    public static final class UpdateType
    extends Enum<UpdateType> {
        public static final /* enum */ UpdateType UPDATE_DATA = new UpdateType();
        public static final /* enum */ UpdateType SAVE_AREA = new UpdateType();
        public static final /* enum */ UpdateType LOAD_AREA = new UpdateType();
        public static final /* enum */ UpdateType SCAN_AREA = new UpdateType();
        private static final /* synthetic */ UpdateType[] $VALUES;

        public static UpdateType[] values() {
            return (UpdateType[])$VALUES.clone();
        }

        public static UpdateType valueOf(String p_59923_) {
            return Enum.valueOf(UpdateType.class, p_59923_);
        }

        private static /* synthetic */ UpdateType[] m_155806_() {
            return new UpdateType[]{UPDATE_DATA, SAVE_AREA, LOAD_AREA, SCAN_AREA};
        }

        static {
            $VALUES = UpdateType.m_155806_();
        }
    }
}

