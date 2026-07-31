/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.player;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ParseResults;
import com.mojang.logging.LogUtils;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.JigsawBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.MinecartCommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.BiomeAmbientSoundsHandler;
import net.minecraft.client.resources.sounds.BubbleColumnAmbientSoundHandler;
import net.minecraft.client.resources.sounds.ElytraOnPlayerSoundInstance;
import net.minecraft.client.resources.sounds.RidingMinecartSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundHandler;
import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundInstances;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ArgumentSignatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ChatMessageContent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MessageSigner;
import net.minecraft.network.chat.PreviewableCommand;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Signer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

public class LocalPlayer
extends AbstractClientPlayer {
    public static final Logger f_234121_ = LogUtils.getLogger();
    private static final int f_172525_ = 20;
    private static final int f_172526_ = 600;
    private static final int f_172527_ = 100;
    private static final float f_172528_ = 0.6f;
    private static final double f_172529_ = 0.35;
    private static final double f_197409_ = 0.13962633907794952;
    private static final float f_234120_ = 0.3f;
    public final ClientPacketListener f_108617_;
    private final StatsCounter f_108591_;
    private final ClientRecipeBook f_108592_;
    private final List<AmbientSoundHandler> f_108593_ = Lists.newArrayList();
    private int f_108594_ = 0;
    private double f_108595_;
    private double f_108596_;
    private double f_108597_;
    private float f_108598_;
    private float f_108599_;
    private boolean f_108600_;
    private boolean f_108601_;
    private boolean f_108602_;
    private boolean f_108603_;
    private int f_108604_;
    private boolean f_108605_;
    @Nullable
    private String f_108606_;
    public Input f_108618_;
    protected final Minecraft f_108619_;
    protected int f_108583_;
    public int f_108584_;
    public float f_108585_;
    public float f_108586_;
    public float f_108587_;
    public float f_108588_;
    private int f_108607_;
    private float f_108608_;
    public float f_108589_;
    public float f_108590_;
    private boolean f_108609_;
    @Nullable
    private InteractionHand f_108610_;
    private boolean f_108611_;
    private boolean f_108612_ = true;
    private int f_108613_;
    private boolean f_108614_;
    private int f_108615_;
    private boolean f_108616_ = true;

    public LocalPlayer(Minecraft p_108621_, ClientLevel p_108622_, ClientPacketListener p_108623_, StatsCounter p_108624_, ClientRecipeBook p_108625_, boolean p_108626_, boolean p_108627_) {
        super(p_108622_, p_108623_.m_105144_(), p_108621_.m_231465_().m_233786_().orElse(null));
        this.f_108619_ = p_108621_;
        this.f_108617_ = p_108623_;
        this.f_108591_ = p_108624_;
        this.f_108592_ = p_108625_;
        this.f_108602_ = p_108626_;
        this.f_108603_ = p_108627_;
        this.f_108593_.add(new UnderwaterAmbientSoundHandler(this, p_108621_.m_91106_()));
        this.f_108593_.add(new BubbleColumnAmbientSoundHandler(this));
        this.f_108593_.add(new BiomeAmbientSoundsHandler(this, p_108621_.m_91106_(), p_108622_.m_7062_()));
    }

    @Override
    public boolean m_6469_(DamageSource p_108662_, float p_108663_) {
        return false;
    }

    @Override
    public void m_5634_(float p_108708_) {
    }

    @Override
    public boolean m_7998_(Entity p_108667_, boolean p_108668_) {
        if (!super.m_7998_(p_108667_, p_108668_)) {
            return false;
        }
        if (p_108667_ instanceof AbstractMinecart) {
            this.f_108619_.m_91106_().m_120367_(new RidingMinecartSoundInstance(this, (AbstractMinecart)p_108667_, true));
            this.f_108619_.m_91106_().m_120367_(new RidingMinecartSoundInstance(this, (AbstractMinecart)p_108667_, false));
        }
        return true;
    }

    @Override
    public void m_6038_() {
        super.m_6038_();
        this.f_108611_ = false;
    }

    @Override
    public float m_5686_(float p_108742_) {
        return this.m_146909_();
    }

    @Override
    public float m_5675_(float p_108753_) {
        if (this.m_20159_()) {
            return super.m_5675_(p_108753_);
        }
        return this.m_146908_();
    }

    @Override
    public void m_8119_() {
        if (!this.f_19853_.m_151577_(this.m_146903_(), this.m_146907_())) {
            return;
        }
        super.m_8119_();
        if (this.m_20159_()) {
            this.f_108617_.m_104955_(new ServerboundMovePlayerPacket.Rot(this.m_146908_(), this.m_146909_(), this.f_19861_));
            this.f_108617_.m_104955_(new ServerboundPlayerInputPacket(this.f_20900_, this.f_20902_, this.f_108618_.f_108572_, this.f_108618_.f_108573_));
            Entity $$0 = this.m_20201_();
            if ($$0 != this && $$0.m_6109_()) {
                this.f_108617_.m_104955_(new ServerboundMoveVehiclePacket($$0));
            }
        } else {
            this.m_108640_();
        }
        for (AmbientSoundHandler $$1 : this.f_108593_) {
            $$1.m_7551_();
        }
    }

    public float m_108762_() {
        for (AmbientSoundHandler $$0 : this.f_108593_) {
            if (!($$0 instanceof BiomeAmbientSoundsHandler)) continue;
            return ((BiomeAmbientSoundsHandler)$$0).m_119654_();
        }
        return 0.0f;
    }

    private void m_108640_() {
        boolean $$2;
        boolean $$0 = this.m_20142_();
        if ($$0 != this.f_108603_) {
            ServerboundPlayerCommandPacket.Action $$1 = $$0 ? ServerboundPlayerCommandPacket.Action.START_SPRINTING : ServerboundPlayerCommandPacket.Action.STOP_SPRINTING;
            this.f_108617_.m_104955_(new ServerboundPlayerCommandPacket(this, $$1));
            this.f_108603_ = $$0;
        }
        if (($$2 = this.m_6144_()) != this.f_108602_) {
            ServerboundPlayerCommandPacket.Action $$3 = $$2 ? ServerboundPlayerCommandPacket.Action.PRESS_SHIFT_KEY : ServerboundPlayerCommandPacket.Action.RELEASE_SHIFT_KEY;
            this.f_108617_.m_104955_(new ServerboundPlayerCommandPacket(this, $$3));
            this.f_108602_ = $$2;
        }
        if (this.m_108636_()) {
            boolean $$10;
            double $$4 = this.m_20185_() - this.f_108595_;
            double $$5 = this.m_20186_() - this.f_108596_;
            double $$6 = this.m_20189_() - this.f_108597_;
            double $$7 = this.m_146908_() - this.f_108598_;
            double $$8 = this.m_146909_() - this.f_108599_;
            ++this.f_108604_;
            boolean $$9 = Mth.m_211592_($$4, $$5, $$6) > Mth.m_144952_(2.0E-4) || this.f_108604_ >= 20;
            boolean bl = $$10 = $$7 != 0.0 || $$8 != 0.0;
            if (this.m_20159_()) {
                Vec3 $$11 = this.m_20184_();
                this.f_108617_.m_104955_(new ServerboundMovePlayerPacket.PosRot($$11.f_82479_, -999.0, $$11.f_82481_, this.m_146908_(), this.m_146909_(), this.f_19861_));
                $$9 = false;
            } else if ($$9 && $$10) {
                this.f_108617_.m_104955_(new ServerboundMovePlayerPacket.PosRot(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_(), this.f_19861_));
            } else if ($$9) {
                this.f_108617_.m_104955_(new ServerboundMovePlayerPacket.Pos(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.f_19861_));
            } else if ($$10) {
                this.f_108617_.m_104955_(new ServerboundMovePlayerPacket.Rot(this.m_146908_(), this.m_146909_(), this.f_19861_));
            } else if (this.f_108600_ != this.f_19861_) {
                this.f_108617_.m_104955_(new ServerboundMovePlayerPacket.StatusOnly(this.f_19861_));
            }
            if ($$9) {
                this.f_108595_ = this.m_20185_();
                this.f_108596_ = this.m_20186_();
                this.f_108597_ = this.m_20189_();
                this.f_108604_ = 0;
            }
            if ($$10) {
                this.f_108598_ = this.m_146908_();
                this.f_108599_ = this.m_146909_();
            }
            this.f_108600_ = this.f_19861_;
            this.f_108612_ = this.f_108619_.f_91066_.m_231812_().m_231551_();
        }
    }

    public boolean m_108700_(boolean p_108701_) {
        ServerboundPlayerActionPacket.Action $$1 = p_108701_ ? ServerboundPlayerActionPacket.Action.DROP_ALL_ITEMS : ServerboundPlayerActionPacket.Action.DROP_ITEM;
        ItemStack $$2 = this.m_150109_().m_182403_(p_108701_);
        this.f_108617_.m_104955_(new ServerboundPlayerActionPacket($$1, BlockPos.f_121853_, Direction.DOWN));
        return !$$2.m_41619_();
    }

    public void m_240287_(String p_240288_, @Nullable Component p_240289_) {
        this.m_240966_(p_240288_, p_240289_);
    }

    public boolean m_241927_(String p_242398_) {
        ParseResults $$1 = this.f_108617_.m_105146_().parse(p_242398_, (Object)this.f_108617_.m_105137_());
        return ArgumentSignatures.m_242625_(PreviewableCommand.m_242644_($$1));
    }

    public boolean m_242614_(String p_242856_) {
        if (!this.m_241927_(p_242856_)) {
            LastSeenMessages.Update $$1 = this.f_108617_.m_241918_();
            this.f_108617_.m_104955_(new ServerboundChatCommandPacket(p_242856_, Instant.now(), 0L, ArgumentSignatures.f_240907_, false, $$1));
            return true;
        }
        return false;
    }

    public void m_234148_(String p_234149_, @Nullable Component p_234150_) {
        this.m_241110_(p_234149_, p_234150_);
    }

    private void m_240966_(String p_241300_, @Nullable Component p_241533_) {
        ChatMessageContent $$2 = this.m_243129_(p_241300_, p_241533_);
        MessageSigner $$3 = this.m_241001_();
        LastSeenMessages.Update $$4 = this.f_108617_.m_241918_();
        MessageSignature $$5 = this.m_241991_($$3, $$2, $$4.f_241678_());
        this.f_108617_.m_104955_(new ServerboundChatPacket($$2.f_241656_(), $$3.f_237170_(), $$3.f_237171_(), $$5, $$2.m_241978_(), $$4));
    }

    private MessageSignature m_241991_(MessageSigner p_242224_, ChatMessageContent p_242260_, LastSeenMessages p_242299_) {
        try {
            Signer $$3 = this.f_108619_.m_231465_().m_233775_();
            if ($$3 != null) {
                return this.f_108617_.m_241060_().m_240988_($$3, p_242224_, p_242260_, p_242299_).f_240871_();
            }
        }
        catch (Exception $$4) {
            f_234121_.error("Failed to sign chat message: '{}'", (Object)p_242260_.f_241656_(), (Object)$$4);
        }
        return MessageSignature.f_240860_;
    }

    private void m_241110_(String p_241331_, @Nullable Component p_241563_) {
        ParseResults $$2 = this.f_108617_.m_105146_().parse(p_241331_, (Object)this.f_108617_.m_105137_());
        MessageSigner $$3 = this.m_241001_();
        LastSeenMessages.Update $$4 = this.f_108617_.m_241918_();
        ArgumentSignatures $$5 = this.m_241780_($$3, (ParseResults<SharedSuggestionProvider>)$$2, p_241563_, $$4.f_241678_());
        this.f_108617_.m_104955_(new ServerboundChatCommandPacket(p_241331_, $$3.f_237170_(), $$3.f_237171_(), $$5, p_241563_ != null, $$4));
    }

    private ArgumentSignatures m_241780_(MessageSigner p_242336_, ParseResults<SharedSuggestionProvider> p_242251_, @Nullable Component p_242373_, LastSeenMessages p_242435_) {
        Signer $$4 = this.f_108619_.m_231465_().m_233775_();
        if ($$4 == null) {
            return ArgumentSignatures.f_240907_;
        }
        try {
            return ArgumentSignatures.m_242588_(PreviewableCommand.m_242644_(p_242251_), (p_243155_, p_243156_) -> {
                ChatMessageContent $$6 = this.m_243129_(p_243156_, p_242373_);
                return this.f_108617_.m_241060_().m_240988_($$4, p_242336_, $$6, p_242435_).f_240871_();
            });
        }
        catch (Exception $$5) {
            f_234121_.error("Failed to sign command arguments", (Throwable)$$5);
            return ArgumentSignatures.f_240907_;
        }
    }

    private ChatMessageContent m_243129_(String p_243326_, @Nullable Component p_243275_) {
        String $$2 = StringUtil.m_216469_(p_243326_);
        if (p_243275_ != null) {
            return new ChatMessageContent($$2, p_243275_);
        }
        return new ChatMessageContent($$2);
    }

    private MessageSigner m_241001_() {
        return MessageSigner.m_237183_(this.m_20148_());
    }

    @Override
    public void m_6674_(InteractionHand p_108660_) {
        super.m_6674_(p_108660_);
        this.f_108617_.m_104955_(new ServerboundSwingPacket(p_108660_));
    }

    @Override
    public void m_7583_() {
        this.f_108617_.m_104955_(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
    }

    @Override
    protected void m_6475_(DamageSource p_108729_, float p_108730_) {
        if (this.m_6673_(p_108729_)) {
            return;
        }
        this.m_21153_(this.m_21223_() - p_108730_);
    }

    @Override
    public void m_6915_() {
        this.f_108617_.m_104955_(new ServerboundContainerClosePacket(this.f_36096_.f_38840_));
        this.m_108763_();
    }

    public void m_108763_() {
        super.m_6915_();
        this.f_108619_.m_91152_(null);
    }

    public void m_108760_(float p_108761_) {
        if (this.f_108605_) {
            float $$1 = this.m_21223_() - p_108761_;
            if ($$1 <= 0.0f) {
                this.m_21153_(p_108761_);
                if ($$1 < 0.0f) {
                    this.f_19802_ = 10;
                }
            } else {
                this.f_20898_ = $$1;
                this.f_19802_ = 20;
                this.m_21153_(p_108761_);
                this.f_20916_ = this.f_20917_ = 10;
            }
        } else {
            this.m_21153_(p_108761_);
            this.f_108605_ = true;
        }
    }

    @Override
    public void m_6885_() {
        this.f_108617_.m_104955_(new ServerboundPlayerAbilitiesPacket(this.m_150110_()));
    }

    @Override
    public boolean m_7578_() {
        return true;
    }

    @Override
    public boolean m_5791_() {
        return !this.m_150110_().f_35935_ && super.m_5791_();
    }

    @Override
    public boolean m_5843_() {
        return !this.m_150110_().f_35935_ && super.m_5843_();
    }

    @Override
    public boolean m_6039_() {
        return !this.m_150110_().f_35935_ && super.m_6039_();
    }

    protected void m_108765_() {
        this.f_108617_.m_104955_(new ServerboundPlayerCommandPacket(this, ServerboundPlayerCommandPacket.Action.START_RIDING_JUMP, Mth.m_14143_(this.m_108634_() * 100.0f)));
    }

    public void m_108628_() {
        this.f_108617_.m_104955_(new ServerboundPlayerCommandPacket(this, ServerboundPlayerCommandPacket.Action.OPEN_INVENTORY));
    }

    public void m_108748_(@Nullable String p_108749_) {
        this.f_108606_ = p_108749_;
    }

    @Nullable
    public String m_108629_() {
        return this.f_108606_;
    }

    public StatsCounter m_108630_() {
        return this.f_108591_;
    }

    public ClientRecipeBook m_108631_() {
        return this.f_108592_;
    }

    public void m_108675_(Recipe<?> p_108676_) {
        if (this.f_108592_.m_12717_(p_108676_)) {
            this.f_108592_.m_12721_(p_108676_);
            this.f_108617_.m_104955_(new ServerboundRecipeBookSeenRecipePacket(p_108676_));
        }
    }

    @Override
    protected int m_8088_() {
        return this.f_108594_;
    }

    public void m_108648_(int p_108649_) {
        this.f_108594_ = p_108649_;
    }

    @Override
    public void m_5661_(Component p_108696_, boolean p_108697_) {
        this.f_108619_.m_240442_().m_240494_(p_108696_, p_108697_);
    }

    private void m_108704_(double p_108705_, double p_108706_) {
        Direction[] $$7;
        BlockPos $$2 = new BlockPos(p_108705_, this.m_20186_(), p_108706_);
        if (!this.m_108746_($$2)) {
            return;
        }
        double $$3 = p_108705_ - (double)$$2.m_123341_();
        double $$4 = p_108706_ - (double)$$2.m_123343_();
        Direction $$5 = null;
        double $$6 = Double.MAX_VALUE;
        for (Direction $$8 : $$7 = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}) {
            double $$10;
            double $$9 = $$8.m_122434_().m_6150_($$3, 0.0, $$4);
            double d = $$10 = $$8.m_122421_() == Direction.AxisDirection.POSITIVE ? 1.0 - $$9 : $$9;
            if (!($$10 < $$6) || this.m_108746_($$2.m_121945_($$8))) continue;
            $$6 = $$10;
            $$5 = $$8;
        }
        if ($$5 != null) {
            Vec3 $$11 = this.m_20184_();
            if ($$5.m_122434_() == Direction.Axis.X) {
                this.m_20334_(0.1 * (double)$$5.m_122429_(), $$11.f_82480_, $$11.f_82481_);
            } else {
                this.m_20334_($$11.f_82479_, $$11.f_82480_, 0.1 * (double)$$5.m_122431_());
            }
        }
    }

    private boolean m_108746_(BlockPos p_108747_) {
        AABB $$1 = this.m_20191_();
        AABB $$2 = new AABB(p_108747_.m_123341_(), $$1.f_82289_, p_108747_.m_123343_(), (double)p_108747_.m_123341_() + 1.0, $$1.f_82292_, (double)p_108747_.m_123343_() + 1.0).m_82406_(1.0E-7);
        return this.f_19853_.m_186437_(this, $$2);
    }

    @Override
    public void m_6858_(boolean p_108751_) {
        super.m_6858_(p_108751_);
        this.f_108584_ = 0;
    }

    public void m_108644_(float p_108645_, int p_108646_, int p_108647_) {
        this.f_36080_ = p_108645_;
        this.f_36079_ = p_108646_;
        this.f_36078_ = p_108647_;
    }

    @Override
    public void m_213846_(Component p_234129_) {
        this.f_108619_.f_91065_.m_93076_().m_93785_(p_234129_);
    }

    @Override
    public void m_7822_(byte p_108643_) {
        if (p_108643_ >= 24 && p_108643_ <= 28) {
            this.m_108648_(p_108643_ - 24);
        } else {
            super.m_7822_(p_108643_);
        }
    }

    public void m_108711_(boolean p_108712_) {
        this.f_108616_ = p_108712_;
    }

    public boolean m_108632_() {
        return this.f_108616_;
    }

    @Override
    public void m_5496_(SoundEvent p_108651_, float p_108652_, float p_108653_) {
        this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), p_108651_, this.m_5720_(), p_108652_, p_108653_, false);
    }

    @Override
    public void m_6330_(SoundEvent p_108655_, SoundSource p_108656_, float p_108657_, float p_108658_) {
        this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), p_108655_, p_108656_, p_108657_, p_108658_, false);
    }

    @Override
    public boolean m_6142_() {
        return true;
    }

    @Override
    public void m_6672_(InteractionHand p_108718_) {
        ItemStack $$1 = this.m_21120_(p_108718_);
        if ($$1.m_41619_() || this.m_6117_()) {
            return;
        }
        super.m_6672_(p_108718_);
        this.f_108609_ = true;
        this.f_108610_ = p_108718_;
    }

    @Override
    public boolean m_6117_() {
        return this.f_108609_;
    }

    @Override
    public void m_5810_() {
        super.m_5810_();
        this.f_108609_ = false;
    }

    @Override
    public InteractionHand m_7655_() {
        return Objects.requireNonNullElse(this.f_108610_, InteractionHand.MAIN_HAND);
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_108699_) {
        super.m_7350_(p_108699_);
        if (f_20909_.equals(p_108699_)) {
            InteractionHand $$2;
            boolean $$1 = ((Byte)this.f_19804_.m_135370_(f_20909_) & 1) > 0;
            InteractionHand interactionHand = $$2 = ((Byte)this.f_19804_.m_135370_(f_20909_) & 2) > 0 ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            if ($$1 && !this.f_108609_) {
                this.m_6672_($$2);
            } else if (!$$1 && this.f_108609_) {
                this.m_5810_();
            }
        }
        if (f_19805_.equals(p_108699_) && this.m_21255_() && !this.f_108614_) {
            this.f_108619_.m_91106_().m_120367_(new ElytraOnPlayerSoundInstance(this));
        }
    }

    public boolean m_108633_() {
        Entity $$0 = this.m_20202_();
        return this.m_20159_() && $$0 instanceof PlayerRideableJumping && ((PlayerRideableJumping)((Object)$$0)).m_7132_();
    }

    public float m_108634_() {
        return this.f_108608_;
    }

    @Override
    public void m_7739_(SignBlockEntity p_108684_) {
        this.f_108619_.m_91152_(new SignEditScreen(p_108684_, this.f_108619_.m_167974_()));
    }

    @Override
    public void m_7907_(BaseCommandBlock p_108678_) {
        this.f_108619_.m_91152_(new MinecartCommandBlockEditScreen(p_108678_));
    }

    @Override
    public void m_7698_(CommandBlockEntity p_108680_) {
        this.f_108619_.m_91152_(new CommandBlockEditScreen(p_108680_));
    }

    @Override
    public void m_5966_(StructureBlockEntity p_108686_) {
        this.f_108619_.m_91152_(new StructureBlockEditScreen(p_108686_));
    }

    @Override
    public void m_7569_(JigsawBlockEntity p_108682_) {
        this.f_108619_.m_91152_(new JigsawBlockEditScreen(p_108682_));
    }

    @Override
    public void m_6986_(ItemStack p_108673_, InteractionHand p_108674_) {
        if (p_108673_.m_150930_(Items.f_42614_)) {
            this.f_108619_.m_91152_(new BookEditScreen(this, p_108673_, p_108674_));
        }
    }

    @Override
    public void m_5704_(Entity p_108665_) {
        this.f_108619_.f_91061_.m_107329_(p_108665_, ParticleTypes.f_123797_);
    }

    @Override
    public void m_5700_(Entity p_108710_) {
        this.f_108619_.f_91061_.m_107329_(p_108710_, ParticleTypes.f_123808_);
    }

    @Override
    public boolean m_6144_() {
        return this.f_108618_ != null && this.f_108618_.f_108573_;
    }

    @Override
    public boolean m_6047_() {
        return this.f_108601_;
    }

    public boolean m_108635_() {
        return this.m_6047_() || this.m_20143_();
    }

    @Override
    public void m_6140_() {
        super.m_6140_();
        if (this.m_108636_()) {
            this.f_20900_ = this.f_108618_.f_108566_;
            this.f_20902_ = this.f_108618_.f_108567_;
            this.f_20899_ = this.f_108618_.f_108572_;
            this.f_108587_ = this.f_108585_;
            this.f_108588_ = this.f_108586_;
            this.f_108586_ += (this.m_146909_() - this.f_108586_) * 0.5f;
            this.f_108585_ += (this.m_146908_() - this.f_108585_) * 0.5f;
        }
    }

    protected boolean m_108636_() {
        return this.f_108619_.m_91288_() == this;
    }

    public void m_172530_() {
        this.m_20124_(Pose.STANDING);
        if (this.f_19853_ != null) {
            for (double $$0 = this.m_20186_(); $$0 > (double)this.f_19853_.m_141937_() && $$0 < (double)this.f_19853_.m_151558_(); $$0 += 1.0) {
                this.m_6034_(this.m_20185_(), $$0, this.m_20189_());
                if (this.f_19853_.m_45786_(this)) break;
            }
            this.m_20256_(Vec3.f_82478_);
            this.m_146926_(0.0f);
        }
        this.m_21153_(this.m_21233_());
        this.f_20919_ = 0;
    }

    @Override
    public void m_8107_() {
        ItemStack $$9;
        boolean $$5;
        ++this.f_108584_;
        if (this.f_108583_ > 0) {
            --this.f_108583_;
        }
        this.m_108641_();
        boolean $$0 = this.f_108618_.f_108572_;
        boolean $$1 = this.f_108618_.f_108573_;
        boolean $$2 = this.m_108733_();
        this.f_108601_ = !this.m_150110_().f_35935_ && !this.m_6069_() && this.m_20175_(Pose.CROUCHING) && (this.m_6144_() || !this.m_5803_() && !this.m_20175_(Pose.STANDING));
        float $$3 = Mth.m_14036_(0.3f + EnchantmentHelper.m_220302_(this), 0.0f, 1.0f);
        this.f_108618_.m_214106_(this.m_108635_(), $$3);
        this.f_108619_.m_91301_().m_120586_(this.f_108618_);
        if (this.m_6117_() && !this.m_20159_()) {
            this.f_108618_.f_108566_ *= 0.2f;
            this.f_108618_.f_108567_ *= 0.2f;
            this.f_108583_ = 0;
        }
        boolean $$4 = false;
        if (this.f_108613_ > 0) {
            --this.f_108613_;
            $$4 = true;
            this.f_108618_.f_108572_ = true;
        }
        if (!this.f_19794_) {
            this.m_108704_(this.m_20185_() - (double)this.m_20205_() * 0.35, this.m_20189_() + (double)this.m_20205_() * 0.35);
            this.m_108704_(this.m_20185_() - (double)this.m_20205_() * 0.35, this.m_20189_() - (double)this.m_20205_() * 0.35);
            this.m_108704_(this.m_20185_() + (double)this.m_20205_() * 0.35, this.m_20189_() - (double)this.m_20205_() * 0.35);
            this.m_108704_(this.m_20185_() + (double)this.m_20205_() * 0.35, this.m_20189_() + (double)this.m_20205_() * 0.35);
        }
        if ($$1) {
            this.f_108583_ = 0;
        }
        boolean bl = $$5 = (float)this.m_36324_().m_38702_() > 6.0f || this.m_150110_().f_35936_;
        if (!(!this.f_19861_ && !this.m_5842_() || $$1 || $$2 || !this.m_108733_() || this.m_20142_() || !$$5 || this.m_6117_() || this.m_21023_(MobEffects.f_19610_))) {
            if (this.f_108583_ > 0 || this.f_108619_.f_91066_.f_92091_.m_90857_()) {
                this.m_6858_(true);
            } else {
                this.f_108583_ = 7;
            }
        }
        if (!this.m_20142_() && (!this.m_20069_() || this.m_5842_()) && this.m_108733_() && $$5 && !this.m_6117_() && !this.m_21023_(MobEffects.f_19610_) && this.f_108619_.f_91066_.f_92091_.m_90857_()) {
            this.m_6858_(true);
        }
        if (this.m_20142_()) {
            boolean $$7;
            boolean $$6 = !this.f_108618_.m_108577_() || !$$5;
            boolean bl2 = $$7 = $$6 || this.f_19862_ && !this.f_185931_ || this.m_20069_() && !this.m_5842_();
            if (this.m_6069_()) {
                if (!this.f_19861_ && !this.f_108618_.f_108573_ && $$6 || !this.m_20069_()) {
                    this.m_6858_(false);
                }
            } else if ($$7) {
                this.m_6858_(false);
            }
        }
        boolean $$8 = false;
        if (this.m_150110_().f_35936_) {
            if (this.f_108619_.f_91072_.m_105293_()) {
                if (!this.m_150110_().f_35935_) {
                    this.m_150110_().f_35935_ = true;
                    $$8 = true;
                    this.m_6885_();
                }
            } else if (!$$0 && this.f_108618_.f_108572_ && !$$4) {
                if (this.f_36098_ == 0) {
                    this.f_36098_ = 7;
                } else if (!this.m_6069_()) {
                    this.m_150110_().f_35935_ = !this.m_150110_().f_35935_;
                    $$8 = true;
                    this.m_6885_();
                    this.f_36098_ = 0;
                }
            }
        }
        if (this.f_108618_.f_108572_ && !$$8 && !$$0 && !this.m_150110_().f_35935_ && !this.m_20159_() && !this.m_6147_() && ($$9 = this.m_6844_(EquipmentSlot.CHEST)).m_150930_(Items.f_42741_) && ElytraItem.m_41140_($$9) && this.m_36319_()) {
            this.f_108617_.m_104955_(new ServerboundPlayerCommandPacket(this, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
        }
        this.f_108614_ = this.m_21255_();
        if (this.m_20069_() && this.f_108618_.f_108573_ && this.m_6129_()) {
            this.m_21208_();
        }
        if (this.m_204029_(FluidTags.f_13131_)) {
            int $$10 = this.m_5833_() ? 10 : 1;
            this.f_108615_ = Mth.m_14045_(this.f_108615_ + $$10, 0, 600);
        } else if (this.f_108615_ > 0) {
            this.m_204029_(FluidTags.f_13131_);
            this.f_108615_ = Mth.m_14045_(this.f_108615_ - 10, 0, 600);
        }
        if (this.m_150110_().f_35935_ && this.m_108636_()) {
            int $$11 = 0;
            if (this.f_108618_.f_108573_) {
                --$$11;
            }
            if (this.f_108618_.f_108572_) {
                ++$$11;
            }
            if ($$11 != 0) {
                this.m_20256_(this.m_20184_().m_82520_(0.0, (float)$$11 * this.m_150110_().m_35942_() * 3.0f, 0.0));
            }
        }
        if (this.m_108633_()) {
            PlayerRideableJumping $$12 = (PlayerRideableJumping)((Object)this.m_20202_());
            if (this.f_108607_ < 0) {
                ++this.f_108607_;
                if (this.f_108607_ == 0) {
                    this.f_108608_ = 0.0f;
                }
            }
            if ($$0 && !this.f_108618_.f_108572_) {
                this.f_108607_ = -10;
                $$12.m_7888_(Mth.m_14143_(this.m_108634_() * 100.0f));
                this.m_108765_();
            } else if (!$$0 && this.f_108618_.f_108572_) {
                this.f_108607_ = 0;
                this.f_108608_ = 0.0f;
            } else if ($$0) {
                ++this.f_108607_;
                this.f_108608_ = this.f_108607_ < 10 ? (float)this.f_108607_ * 0.1f : 0.8f + 2.0f / (float)(this.f_108607_ - 9) * 0.1f;
            }
        } else {
            this.f_108608_ = 0.0f;
        }
        super.m_8107_();
        if (this.f_19861_ && this.m_150110_().f_35935_ && !this.f_108619_.f_91072_.m_105293_()) {
            this.m_150110_().f_35935_ = false;
            this.m_6885_();
        }
    }

    @Override
    protected void m_6153_() {
        ++this.f_20919_;
        if (this.f_20919_ == 20) {
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    private void m_108641_() {
        this.f_108590_ = this.f_108589_;
        if (this.f_19817_) {
            if (!(this.f_108619_.f_91080_ == null || this.f_108619_.f_91080_.m_7043_() || this.f_108619_.f_91080_ instanceof DeathScreen || this.f_108619_.f_91080_ instanceof ReceivingLevelScreen)) {
                if (this.f_108619_.f_91080_ instanceof AbstractContainerScreen) {
                    this.m_6915_();
                }
                this.f_108619_.m_91152_(null);
            }
            if (this.f_108589_ == 0.0f) {
                this.f_108619_.m_91106_().m_120367_(SimpleSoundInstance.m_119766_(SoundEvents.f_12288_, this.f_19796_.m_188501_() * 0.4f + 0.8f, 0.25f));
            }
            this.f_108589_ += 0.0125f;
            if (this.f_108589_ >= 1.0f) {
                this.f_108589_ = 1.0f;
            }
            this.f_19817_ = false;
        } else if (this.m_21023_(MobEffects.f_19604_) && this.m_21124_(MobEffects.f_19604_).m_19557_() > 60) {
            this.f_108589_ += 0.006666667f;
            if (this.f_108589_ > 1.0f) {
                this.f_108589_ = 1.0f;
            }
        } else {
            if (this.f_108589_ > 0.0f) {
                this.f_108589_ -= 0.05f;
            }
            if (this.f_108589_ < 0.0f) {
                this.f_108589_ = 0.0f;
            }
        }
        this.m_8021_();
    }

    @Override
    public void m_6083_() {
        super.m_6083_();
        this.f_108611_ = false;
        if (this.m_20202_() instanceof Boat) {
            Boat $$0 = (Boat)this.m_20202_();
            $$0.m_38342_(this.f_108618_.f_108570_, this.f_108618_.f_108571_, this.f_108618_.f_108568_, this.f_108618_.f_108569_);
            this.f_108611_ |= this.f_108618_.f_108570_ || this.f_108618_.f_108571_ || this.f_108618_.f_108568_ || this.f_108618_.f_108569_;
        }
    }

    public boolean m_108637_() {
        return this.f_108611_;
    }

    @Override
    @Nullable
    public MobEffectInstance m_6234_(@Nullable MobEffect p_108720_) {
        if (p_108720_ == MobEffects.f_19604_) {
            this.f_108590_ = 0.0f;
            this.f_108589_ = 0.0f;
        }
        return super.m_6234_(p_108720_);
    }

    @Override
    public void m_6478_(MoverType p_108670_, Vec3 p_108671_) {
        double $$2 = this.m_20185_();
        double $$3 = this.m_20189_();
        super.m_6478_(p_108670_, p_108671_);
        this.m_108743_((float)(this.m_20185_() - $$2), (float)(this.m_20189_() - $$3));
    }

    public boolean m_108638_() {
        return this.f_108612_;
    }

    protected void m_108743_(float p_108744_, float p_108745_) {
        if (!this.m_108731_()) {
            return;
        }
        Vec3 $$2 = this.m_20182_();
        Vec3 $$3 = $$2.m_82520_(p_108744_, 0.0, p_108745_);
        Vec3 $$4 = new Vec3(p_108744_, 0.0, p_108745_);
        float $$5 = this.m_6113_();
        float $$6 = (float)$$4.m_82556_();
        if ($$6 <= 0.001f) {
            Vec2 $$7 = this.f_108618_.m_108575_();
            float $$8 = $$5 * $$7.f_82470_;
            float $$9 = $$5 * $$7.f_82471_;
            float $$10 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180));
            float $$11 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180));
            $$4 = new Vec3($$8 * $$11 - $$9 * $$10, $$4.f_82480_, $$9 * $$11 + $$8 * $$10);
            $$6 = (float)$$4.m_82556_();
            if ($$6 <= 0.001f) {
                return;
            }
        }
        float $$12 = Mth.m_14195_($$6);
        Vec3 $$13 = $$4.m_82490_($$12);
        Vec3 $$14 = this.m_20156_();
        float $$15 = (float)($$14.f_82479_ * $$13.f_82479_ + $$14.f_82481_ * $$13.f_82481_);
        if ($$15 < -0.15f) {
            return;
        }
        CollisionContext $$16 = CollisionContext.m_82750_(this);
        BlockPos $$17 = new BlockPos(this.m_20185_(), this.m_20191_().f_82292_, this.m_20189_());
        BlockState $$18 = this.f_19853_.m_8055_($$17);
        if (!$$18.m_60742_(this.f_19853_, $$17, $$16).m_83281_()) {
            return;
        }
        BlockState $$19 = this.f_19853_.m_8055_($$17 = $$17.m_7494_());
        if (!$$19.m_60742_(this.f_19853_, $$17, $$16).m_83281_()) {
            return;
        }
        float $$20 = 7.0f;
        float $$21 = 1.2f;
        if (this.m_21023_(MobEffects.f_19603_)) {
            $$21 += (float)(this.m_21124_(MobEffects.f_19603_).m_19564_() + 1) * 0.75f;
        }
        float $$22 = Math.max($$5 * 7.0f, 1.0f / $$12);
        Vec3 $$23 = $$2;
        Vec3 $$24 = $$3.m_82549_($$13.m_82490_($$22));
        float $$25 = this.m_20205_();
        float $$26 = this.m_20206_();
        AABB $$27 = new AABB($$23, $$24.m_82520_(0.0, $$26, 0.0)).m_82377_($$25, 0.0, $$25);
        $$23 = $$23.m_82520_(0.0, 0.51f, 0.0);
        $$24 = $$24.m_82520_(0.0, 0.51f, 0.0);
        Vec3 $$28 = $$13.m_82537_(new Vec3(0.0, 1.0, 0.0));
        Vec3 $$29 = $$28.m_82490_($$25 * 0.5f);
        Vec3 $$30 = $$23.m_82546_($$29);
        Vec3 $$31 = $$24.m_82546_($$29);
        Vec3 $$32 = $$23.m_82549_($$29);
        Vec3 $$33 = $$24.m_82549_($$29);
        Iterable<VoxelShape> $$34 = this.f_19853_.m_186431_(this, $$27);
        Iterator $$35 = StreamSupport.stream($$34.spliterator(), false).flatMap(p_234124_ -> p_234124_.m_83299_().stream()).iterator();
        float $$36 = Float.MIN_VALUE;
        while ($$35.hasNext()) {
            AABB $$37 = (AABB)$$35.next();
            if (!$$37.m_82335_($$30, $$31) && !$$37.m_82335_($$32, $$33)) continue;
            $$36 = (float)$$37.f_82292_;
            Vec3 $$38 = $$37.m_82399_();
            BlockPos $$39 = new BlockPos($$38);
            int $$40 = 1;
            while ((float)$$40 < $$21) {
                BlockState $$44;
                BlockPos $$41 = $$39.m_6630_($$40);
                BlockState $$42 = this.f_19853_.m_8055_($$41);
                VoxelShape $$43 = $$42.m_60742_(this.f_19853_, $$41, $$16);
                if (!$$43.m_83281_() && (double)($$36 = (float)$$43.m_83297_(Direction.Axis.Y) + (float)$$41.m_123342_()) - this.m_20186_() > (double)$$21) {
                    return;
                }
                if ($$40 > 1 && !($$44 = this.f_19853_.m_8055_($$17 = $$17.m_7494_())).m_60742_(this.f_19853_, $$17, $$16).m_83281_()) {
                    return;
                }
                ++$$40;
            }
            break block0;
        }
        if ($$36 == Float.MIN_VALUE) {
            return;
        }
        float $$45 = (float)((double)$$36 - this.m_20186_());
        if ($$45 <= 0.5f || $$45 > $$21) {
            return;
        }
        this.f_108613_ = 1;
    }

    @Override
    protected boolean m_196406_(Vec3 p_197411_) {
        float $$1 = this.m_146908_() * ((float)Math.PI / 180);
        double $$2 = Mth.m_14031_($$1);
        double $$3 = Mth.m_14089_($$1);
        double $$4 = (double)this.f_20900_ * $$3 - (double)this.f_20902_ * $$2;
        double $$5 = (double)this.f_20902_ * $$3 + (double)this.f_20900_ * $$2;
        double $$6 = Mth.m_144952_($$4) + Mth.m_144952_($$5);
        double $$7 = Mth.m_144952_(p_197411_.f_82479_) + Mth.m_144952_(p_197411_.f_82481_);
        if ($$6 < (double)1.0E-5f || $$7 < (double)1.0E-5f) {
            return false;
        }
        double $$8 = $$4 * p_197411_.f_82479_ + $$5 * p_197411_.f_82481_;
        double $$9 = Math.acos($$8 / Math.sqrt($$6 * $$7));
        return $$9 < 0.13962633907794952;
    }

    private boolean m_108731_() {
        return this.m_108638_() && this.f_108613_ <= 0 && this.f_19861_ && !this.m_36343_() && !this.m_20159_() && this.m_108732_() && (double)this.m_20098_() >= 1.0;
    }

    private boolean m_108732_() {
        Vec2 $$0 = this.f_108618_.m_108575_();
        return $$0.f_82470_ != 0.0f || $$0.f_82471_ != 0.0f;
    }

    private boolean m_108733_() {
        double $$0 = 0.8;
        return this.m_5842_() ? this.f_108618_.m_108577_() : (double)this.f_108618_.f_108567_ >= 0.8;
    }

    public float m_108639_() {
        if (!this.m_204029_(FluidTags.f_13131_)) {
            return 0.0f;
        }
        float $$0 = 600.0f;
        float $$1 = 100.0f;
        if ((float)this.f_108615_ >= 600.0f) {
            return 1.0f;
        }
        float $$2 = Mth.m_14036_((float)this.f_108615_ / 100.0f, 0.0f, 1.0f);
        float $$3 = (float)this.f_108615_ < 100.0f ? 0.0f : Mth.m_14036_(((float)this.f_108615_ - 100.0f) / 500.0f, 0.0f, 1.0f);
        return $$2 * 0.6f + $$3 * 0.39999998f;
    }

    @Override
    public boolean m_5842_() {
        return this.f_36076_;
    }

    @Override
    protected boolean m_7602_() {
        boolean $$0 = this.f_36076_;
        boolean $$1 = super.m_7602_();
        if (this.m_5833_()) {
            return this.f_36076_;
        }
        if (!$$0 && $$1) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12537_, SoundSource.AMBIENT, 1.0f, 1.0f, false);
            this.f_108619_.m_91106_().m_120367_(new UnderwaterAmbientSoundInstances.UnderwaterAmbientSoundInstance(this));
        }
        if ($$0 && !$$1) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12590_, SoundSource.AMBIENT, 1.0f, 1.0f, false);
        }
        return this.f_36076_;
    }

    @Override
    public Vec3 m_7398_(float p_108758_) {
        if (this.f_108619_.f_91066_.m_92176_().m_90612_()) {
            float $$1 = Mth.m_14179_(p_108758_ * 0.5f, this.m_146908_(), this.f_19859_) * ((float)Math.PI / 180);
            float $$2 = Mth.m_14179_(p_108758_ * 0.5f, this.m_146909_(), this.f_19860_) * ((float)Math.PI / 180);
            double $$3 = this.m_5737_() == HumanoidArm.RIGHT ? -1.0 : 1.0;
            Vec3 $$4 = new Vec3(0.39 * $$3, -0.6, 0.3);
            return $$4.m_82496_(-$$2).m_82524_(-$$1).m_82549_(this.m_20299_(p_108758_));
        }
        return super.m_7398_(p_108758_);
    }

    @Override
    public void m_141945_(ItemStack p_172532_, ItemStack p_172533_, ClickAction p_172534_) {
        this.f_108619_.m_91301_().m_175024_(p_172532_, p_172533_, p_172534_);
    }

    @Override
    public float m_213816_() {
        return this.m_146908_();
    }
}

