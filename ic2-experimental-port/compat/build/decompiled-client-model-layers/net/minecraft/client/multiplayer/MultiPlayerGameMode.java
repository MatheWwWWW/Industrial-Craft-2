/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableObject
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.multiplayer.prediction.PredictiveAction;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.StatsCounter;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

public class MultiPlayerGameMode {
    private static final Logger f_105188_ = LogUtils.getLogger();
    private final Minecraft f_105189_;
    private final ClientPacketListener f_105190_;
    private BlockPos f_105191_ = new BlockPos(-1, -1, -1);
    private ItemStack f_105192_ = ItemStack.f_41583_;
    private float f_105193_;
    private float f_105194_;
    private int f_105195_;
    private boolean f_105196_;
    private GameType f_105197_ = GameType.f_151492_;
    @Nullable
    private GameType f_105198_;
    private int f_105200_;

    public MultiPlayerGameMode(Minecraft p_105203_, ClientPacketListener p_105204_) {
        this.f_105189_ = p_105203_;
        this.f_105190_ = p_105204_;
    }

    public void m_105221_(Player p_105222_) {
        this.f_105197_.m_46398_(p_105222_.m_150110_());
    }

    public void m_171805_(GameType p_171806_, @Nullable GameType p_171807_) {
        this.f_105197_ = p_171806_;
        this.f_105198_ = p_171807_;
        this.f_105197_.m_46398_(this.f_105189_.f_91074_.m_150110_());
    }

    public void m_105279_(GameType p_105280_) {
        if (p_105280_ != this.f_105197_) {
            this.f_105198_ = this.f_105197_;
        }
        this.f_105197_ = p_105280_;
        this.f_105197_.m_46398_(this.f_105189_.f_91074_.m_150110_());
    }

    public boolean m_105205_() {
        return this.f_105197_.m_46409_();
    }

    public boolean m_105267_(BlockPos p_105268_) {
        if (this.f_105189_.f_91074_.m_36187_(this.f_105189_.f_91073_, p_105268_, this.f_105197_)) {
            return false;
        }
        ClientLevel $$1 = this.f_105189_.f_91073_;
        BlockState $$2 = $$1.m_8055_(p_105268_);
        if (!this.f_105189_.f_91074_.m_21205_().m_41720_().m_6777_($$2, $$1, p_105268_, this.f_105189_.f_91074_)) {
            return false;
        }
        Block $$3 = $$2.m_60734_();
        if ($$3 instanceof GameMasterBlock && !this.f_105189_.f_91074_.m_36337_()) {
            return false;
        }
        if ($$2.m_60795_()) {
            return false;
        }
        $$3.m_5707_($$1, p_105268_, $$2, this.f_105189_.f_91074_);
        FluidState $$4 = $$1.m_6425_(p_105268_);
        boolean $$5 = $$1.m_7731_(p_105268_, $$4.m_76188_(), 11);
        if ($$5) {
            $$3.m_6786_($$1, p_105268_, $$2);
        }
        return $$5;
    }

    public boolean m_105269_(BlockPos p_105270_, Direction p_105271_) {
        if (this.f_105189_.f_91074_.m_36187_(this.f_105189_.f_91073_, p_105270_, this.f_105197_)) {
            return false;
        }
        if (!this.f_105189_.f_91073_.m_6857_().m_61937_(p_105270_)) {
            return false;
        }
        if (this.f_105197_.m_46408_()) {
            BlockState $$2 = this.f_105189_.f_91073_.m_8055_(p_105270_);
            this.f_105189_.m_91301_().m_120581_(this.f_105189_.f_91073_, p_105270_, $$2, 1.0f);
            this.m_233729_(this.f_105189_.f_91073_, p_233757_ -> {
                this.m_105267_(p_105270_);
                return new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, p_105270_, p_105271_, p_233757_);
            });
            this.f_105195_ = 5;
        } else if (!this.f_105196_ || !this.m_105281_(p_105270_)) {
            if (this.f_105196_) {
                this.f_105190_.m_104955_(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, this.f_105191_, p_105271_));
            }
            BlockState $$3 = this.f_105189_.f_91073_.m_8055_(p_105270_);
            this.f_105189_.m_91301_().m_120581_(this.f_105189_.f_91073_, p_105270_, $$3, 0.0f);
            this.m_233729_(this.f_105189_.f_91073_, p_233728_ -> {
                boolean $$4;
                boolean bl = $$4 = !$$3.m_60795_();
                if ($$4 && this.f_105193_ == 0.0f) {
                    $$3.m_60686_(this.f_105189_.f_91073_, p_105270_, this.f_105189_.f_91074_);
                }
                if ($$4 && $$3.m_60625_(this.f_105189_.f_91074_, this.f_105189_.f_91074_.f_19853_, p_105270_) >= 1.0f) {
                    this.m_105267_(p_105270_);
                } else {
                    this.f_105196_ = true;
                    this.f_105191_ = p_105270_;
                    this.f_105192_ = this.f_105189_.f_91074_.m_21205_();
                    this.f_105193_ = 0.0f;
                    this.f_105194_ = 0.0f;
                    this.f_105189_.f_91073_.m_6801_(this.f_105189_.f_91074_.m_19879_(), this.f_105191_, (int)(this.f_105193_ * 10.0f) - 1);
                }
                return new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, p_105270_, p_105271_, p_233728_);
            });
        }
        return true;
    }

    public void m_105276_() {
        if (this.f_105196_) {
            BlockState $$0 = this.f_105189_.f_91073_.m_8055_(this.f_105191_);
            this.f_105189_.m_91301_().m_120581_(this.f_105189_.f_91073_, this.f_105191_, $$0, -1.0f);
            this.f_105190_.m_104955_(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, this.f_105191_, Direction.DOWN));
            this.f_105196_ = false;
            this.f_105193_ = 0.0f;
            this.f_105189_.f_91073_.m_6801_(this.f_105189_.f_91074_.m_19879_(), this.f_105191_, -1);
            this.f_105189_.f_91074_.m_36334_();
        }
    }

    public boolean m_105283_(BlockPos p_105284_, Direction p_105285_) {
        this.m_105297_();
        if (this.f_105195_ > 0) {
            --this.f_105195_;
            return true;
        }
        if (this.f_105197_.m_46408_() && this.f_105189_.f_91073_.m_6857_().m_61937_(p_105284_)) {
            this.f_105195_ = 5;
            BlockState $$2 = this.f_105189_.f_91073_.m_8055_(p_105284_);
            this.f_105189_.m_91301_().m_120581_(this.f_105189_.f_91073_, p_105284_, $$2, 1.0f);
            this.m_233729_(this.f_105189_.f_91073_, p_233753_ -> {
                this.m_105267_(p_105284_);
                return new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, p_105284_, p_105285_, p_233753_);
            });
            return true;
        }
        if (this.m_105281_(p_105284_)) {
            BlockState $$3 = this.f_105189_.f_91073_.m_8055_(p_105284_);
            if ($$3.m_60795_()) {
                this.f_105196_ = false;
                return false;
            }
            this.f_105193_ += $$3.m_60625_(this.f_105189_.f_91074_, this.f_105189_.f_91074_.f_19853_, p_105284_);
            if (this.f_105194_ % 4.0f == 0.0f) {
                SoundType $$4 = $$3.m_60827_();
                this.f_105189_.m_91106_().m_120367_(new SimpleSoundInstance($$4.m_56778_(), SoundSource.BLOCKS, ($$4.m_56773_() + 1.0f) / 8.0f, $$4.m_56774_() * 0.5f, SoundInstance.m_235150_(), p_105284_));
            }
            this.f_105194_ += 1.0f;
            this.f_105189_.m_91301_().m_120581_(this.f_105189_.f_91073_, p_105284_, $$3, Mth.m_14036_(this.f_105193_, 0.0f, 1.0f));
            if (this.f_105193_ >= 1.0f) {
                this.f_105196_ = false;
                this.m_233729_(this.f_105189_.f_91073_, p_233739_ -> {
                    this.m_105267_(p_105284_);
                    return new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK, p_105284_, p_105285_, p_233739_);
                });
                this.f_105193_ = 0.0f;
                this.f_105194_ = 0.0f;
                this.f_105195_ = 5;
            }
        } else {
            return this.m_105269_(p_105284_, p_105285_);
        }
        this.f_105189_.f_91073_.m_6801_(this.f_105189_.f_91074_.m_19879_(), this.f_105191_, (int)(this.f_105193_ * 10.0f) - 1);
        return true;
    }

    private void m_233729_(ClientLevel p_233730_, PredictiveAction p_233731_) {
        try (BlockStatePredictionHandler $$2 = p_233730_.m_233601_().m_233855_();){
            int $$3 = $$2.m_233871_();
            Packet<ServerGamePacketListener> $$4 = p_233731_.m_233885_($$3);
            this.f_105190_.m_104955_($$4);
        }
    }

    public float m_105286_() {
        if (this.f_105197_.m_46408_()) {
            return 5.0f;
        }
        return 4.5f;
    }

    public void m_105287_() {
        this.m_105297_();
        if (this.f_105190_.m_6198_().m_129536_()) {
            this.f_105190_.m_6198_().m_129483_();
        } else {
            this.f_105190_.m_6198_().m_129541_();
        }
    }

    private boolean m_105281_(BlockPos p_105282_) {
        boolean $$2;
        ItemStack $$1 = this.f_105189_.f_91074_.m_21205_();
        boolean bl = $$2 = this.f_105192_.m_41619_() && $$1.m_41619_();
        if (!this.f_105192_.m_41619_() && !$$1.m_41619_()) {
            $$2 = $$1.m_150930_(this.f_105192_.m_41720_()) && ItemStack.m_41658_($$1, this.f_105192_) && ($$1.m_41763_() || $$1.m_41773_() == this.f_105192_.m_41773_());
        }
        return p_105282_.equals(this.f_105191_) && $$2;
    }

    private void m_105297_() {
        int $$0 = this.f_105189_.f_91074_.m_150109_().f_35977_;
        if ($$0 != this.f_105200_) {
            this.f_105200_ = $$0;
            this.f_105190_.m_104955_(new ServerboundSetCarriedItemPacket(this.f_105200_));
        }
    }

    public InteractionResult m_233732_(LocalPlayer p_233733_, InteractionHand p_233734_, BlockHitResult p_233735_) {
        this.m_105297_();
        if (!this.f_105189_.f_91073_.m_6857_().m_61937_(p_233735_.m_82425_())) {
            return InteractionResult.FAIL;
        }
        MutableObject $$3 = new MutableObject();
        this.m_233729_(this.f_105189_.f_91073_, p_233745_ -> {
            $$3.setValue((Object)this.m_233746_(p_233733_, p_233734_, p_233735_));
            return new ServerboundUseItemOnPacket(p_233734_, p_233735_, p_233745_);
        });
        return (InteractionResult)((Object)$$3.getValue());
    }

    private InteractionResult m_233746_(LocalPlayer p_233747_, InteractionHand p_233748_, BlockHitResult p_233749_) {
        InteractionResult $$11;
        InteractionResult $$7;
        boolean $$6;
        BlockPos $$3 = p_233749_.m_82425_();
        ItemStack $$4 = p_233747_.m_21120_(p_233748_);
        if (this.f_105197_ == GameType.SPECTATOR) {
            return InteractionResult.SUCCESS;
        }
        boolean $$5 = !p_233747_.m_21205_().m_41619_() || !p_233747_.m_21206_().m_41619_();
        boolean bl = $$6 = p_233747_.m_36341_() && $$5;
        if (!$$6 && ($$7 = this.f_105189_.f_91073_.m_8055_($$3).m_60664_(this.f_105189_.f_91073_, p_233747_, p_233748_, p_233749_)).m_19077_()) {
            return $$7;
        }
        if ($$4.m_41619_() || p_233747_.m_36335_().m_41519_($$4.m_41720_())) {
            return InteractionResult.PASS;
        }
        UseOnContext $$8 = new UseOnContext(p_233747_, p_233748_, p_233749_);
        if (this.f_105197_.m_46408_()) {
            int $$9 = $$4.m_41613_();
            InteractionResult $$10 = $$4.m_41661_($$8);
            $$4.m_41764_($$9);
        } else {
            $$11 = $$4.m_41661_($$8);
        }
        return $$11;
    }

    public InteractionResult m_233721_(Player p_233722_, InteractionHand p_233723_) {
        if (this.f_105197_ == GameType.SPECTATOR) {
            return InteractionResult.PASS;
        }
        this.m_105297_();
        this.f_105190_.m_104955_(new ServerboundMovePlayerPacket.PosRot(p_233722_.m_20185_(), p_233722_.m_20186_(), p_233722_.m_20189_(), p_233722_.m_146908_(), p_233722_.m_146909_(), p_233722_.m_20096_()));
        MutableObject $$2 = new MutableObject();
        this.m_233729_(this.f_105189_.f_91073_, p_233720_ -> {
            ServerboundUseItemPacket $$4 = new ServerboundUseItemPacket(p_233723_, p_233720_);
            ItemStack $$5 = p_233722_.m_21120_(p_233723_);
            if (p_233722_.m_36335_().m_41519_($$5.m_41720_())) {
                $$2.setValue((Object)InteractionResult.PASS);
                return $$4;
            }
            InteractionResultHolder<ItemStack> $$6 = $$5.m_41682_(this.f_105189_.f_91073_, p_233722_, p_233723_);
            ItemStack $$7 = $$6.m_19095_();
            if ($$7 != $$5) {
                p_233722_.m_21008_(p_233723_, $$7);
            }
            $$2.setValue((Object)$$6.m_19089_());
            return $$4;
        });
        return (InteractionResult)((Object)$$2.getValue());
    }

    public LocalPlayer m_105246_(ClientLevel p_105247_, StatsCounter p_105248_, ClientRecipeBook p_105249_) {
        return this.m_105250_(p_105247_, p_105248_, p_105249_, false, false);
    }

    public LocalPlayer m_105250_(ClientLevel p_105251_, StatsCounter p_105252_, ClientRecipeBook p_105253_, boolean p_105254_, boolean p_105255_) {
        return new LocalPlayer(this.f_105189_, p_105251_, this.f_105190_, p_105252_, p_105253_, p_105254_, p_105255_);
    }

    public void m_105223_(Player p_105224_, Entity p_105225_) {
        this.m_105297_();
        this.f_105190_.m_104955_(ServerboundInteractPacket.m_179605_(p_105225_, p_105224_.m_6144_()));
        if (this.f_105197_ != GameType.SPECTATOR) {
            p_105224_.m_5706_(p_105225_);
            p_105224_.m_36334_();
        }
    }

    public InteractionResult m_105226_(Player p_105227_, Entity p_105228_, InteractionHand p_105229_) {
        this.m_105297_();
        this.f_105190_.m_104955_(ServerboundInteractPacket.m_179608_(p_105228_, p_105227_.m_6144_(), p_105229_));
        if (this.f_105197_ == GameType.SPECTATOR) {
            return InteractionResult.PASS;
        }
        return p_105227_.m_36157_(p_105228_, p_105229_);
    }

    public InteractionResult m_105230_(Player p_105231_, Entity p_105232_, EntityHitResult p_105233_, InteractionHand p_105234_) {
        this.m_105297_();
        Vec3 $$4 = p_105233_.m_82450_().m_82492_(p_105232_.m_20185_(), p_105232_.m_20186_(), p_105232_.m_20189_());
        this.f_105190_.m_104955_(ServerboundInteractPacket.m_179612_(p_105232_, p_105231_.m_6144_(), p_105234_, $$4));
        if (this.f_105197_ == GameType.SPECTATOR) {
            return InteractionResult.PASS;
        }
        return p_105232_.m_7111_(p_105231_, $$4, p_105234_);
    }

    public void m_171799_(int p_171800_, int p_171801_, int p_171802_, ClickType p_171803_, Player p_171804_) {
        AbstractContainerMenu $$5 = p_171804_.f_36096_;
        if (p_171800_ != $$5.f_38840_) {
            f_105188_.warn("Ignoring click in mismatching container. Click in {}, player has {}.", (Object)p_171800_, (Object)$$5.f_38840_);
            return;
        }
        NonNullList<Slot> $$6 = $$5.f_38839_;
        int $$7 = $$6.size();
        ArrayList $$8 = Lists.newArrayListWithCapacity((int)$$7);
        for (Slot $$9 : $$6) {
            $$8.add($$9.m_7993_().m_41777_());
        }
        $$5.m_150399_(p_171801_, p_171802_, p_171803_, p_171804_);
        Int2ObjectOpenHashMap $$10 = new Int2ObjectOpenHashMap();
        for (int $$11 = 0; $$11 < $$7; ++$$11) {
            ItemStack $$13;
            ItemStack $$12 = (ItemStack)$$8.get($$11);
            if (ItemStack.m_41728_($$12, $$13 = $$6.get($$11).m_7993_())) continue;
            $$10.put($$11, (Object)$$13.m_41777_());
        }
        this.f_105190_.m_104955_(new ServerboundContainerClickPacket(p_171800_, $$5.m_182424_(), p_171801_, p_171802_, p_171803_, $$5.m_142621_().m_41777_(), (Int2ObjectMap<ItemStack>)$$10));
    }

    public void m_105217_(int p_105218_, Recipe<?> p_105219_, boolean p_105220_) {
        this.f_105190_.m_104955_(new ServerboundPlaceRecipePacket(p_105218_, p_105219_, p_105220_));
    }

    public void m_105208_(int p_105209_, int p_105210_) {
        this.f_105190_.m_104955_(new ServerboundContainerButtonClickPacket(p_105209_, p_105210_));
    }

    public void m_105241_(ItemStack p_105242_, int p_105243_) {
        if (this.f_105197_.m_46408_()) {
            this.f_105190_.m_104955_(new ServerboundSetCreativeModeSlotPacket(p_105243_, p_105242_));
        }
    }

    public void m_105239_(ItemStack p_105240_) {
        if (this.f_105197_.m_46408_() && !p_105240_.m_41619_()) {
            this.f_105190_.m_104955_(new ServerboundSetCreativeModeSlotPacket(-1, p_105240_));
        }
    }

    public void m_105277_(Player p_105278_) {
        this.m_105297_();
        this.f_105190_.m_104955_(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.RELEASE_USE_ITEM, BlockPos.f_121853_, Direction.DOWN));
        p_105278_.m_21253_();
    }

    public boolean m_105288_() {
        return this.f_105197_.m_46409_();
    }

    public boolean m_105289_() {
        return !this.f_105197_.m_46408_();
    }

    public boolean m_105290_() {
        return this.f_105197_.m_46408_();
    }

    public boolean m_105291_() {
        return this.f_105197_.m_46408_();
    }

    public boolean m_105292_() {
        return this.f_105189_.f_91074_.m_20159_() && this.f_105189_.f_91074_.m_20202_() instanceof HasCustomInventoryScreen;
    }

    public boolean m_105293_() {
        return this.f_105197_ == GameType.SPECTATOR;
    }

    @Nullable
    public GameType m_105294_() {
        return this.f_105198_;
    }

    public GameType m_105295_() {
        return this.f_105197_;
    }

    public boolean m_105296_() {
        return this.f_105196_;
    }

    public void m_105206_(int p_105207_) {
        this.f_105190_.m_104955_(new ServerboundPickItemPacket(p_105207_));
    }
}

