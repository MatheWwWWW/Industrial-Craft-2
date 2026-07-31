/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.level;

import com.mojang.logging.LogUtils;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class ServerPlayerGameMode {
    private static final Logger f_9246_ = LogUtils.getLogger();
    protected ServerLevel f_9244_;
    protected final ServerPlayer f_9245_;
    private GameType f_9247_ = GameType.f_151492_;
    @Nullable
    private GameType f_9248_;
    private boolean f_9249_;
    private int f_9250_;
    private BlockPos f_9251_ = BlockPos.f_121853_;
    private int f_9252_;
    private boolean f_9253_;
    private BlockPos f_9254_ = BlockPos.f_121853_;
    private int f_9255_;
    private int f_9256_ = -1;

    public ServerPlayerGameMode(ServerPlayer p_143472_) {
        this.f_9245_ = p_143472_;
        this.f_9244_ = p_143472_.m_9236_();
    }

    public boolean m_143473_(GameType p_143474_) {
        if (p_143474_ == this.f_9247_) {
            return false;
        }
        this.m_9273_(p_143474_, this.f_9247_);
        return true;
    }

    protected void m_9273_(GameType p_9274_, @Nullable GameType p_9275_) {
        this.f_9248_ = p_9275_;
        this.f_9247_ = p_9274_;
        p_9274_.m_46398_(this.f_9245_.m_150110_());
        this.f_9245_.m_6885_();
        this.f_9245_.f_8924_.m_6846_().m_11268_(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_GAME_MODE, this.f_9245_));
        this.f_9244_.m_8878_();
    }

    public GameType m_9290_() {
        return this.f_9247_;
    }

    @Nullable
    public GameType m_9293_() {
        return this.f_9248_;
    }

    public boolean m_9294_() {
        return this.f_9247_.m_46409_();
    }

    public boolean m_9295_() {
        return this.f_9247_.m_46408_();
    }

    public void m_7712_() {
        ++this.f_9252_;
        if (this.f_9253_) {
            BlockState $$0 = this.f_9244_.m_8055_(this.f_9254_);
            if ($$0.m_60795_()) {
                this.f_9253_ = false;
            } else {
                float $$1 = this.m_9276_($$0, this.f_9254_, this.f_9255_);
                if ($$1 >= 1.0f) {
                    this.f_9253_ = false;
                    this.m_9280_(this.f_9254_);
                }
            }
        } else if (this.f_9249_) {
            BlockState $$2 = this.f_9244_.m_8055_(this.f_9251_);
            if ($$2.m_60795_()) {
                this.f_9244_.m_6801_(this.f_9245_.m_19879_(), this.f_9251_, -1);
                this.f_9256_ = -1;
                this.f_9249_ = false;
            } else {
                this.m_9276_($$2, this.f_9251_, this.f_9250_);
            }
        }
    }

    private float m_9276_(BlockState p_9277_, BlockPos p_9278_, int p_9279_) {
        int $$3 = this.f_9252_ - p_9279_;
        float $$4 = p_9277_.m_60625_(this.f_9245_, this.f_9245_.f_19853_, p_9278_) * (float)($$3 + 1);
        int $$5 = (int)($$4 * 10.0f);
        if ($$5 != this.f_9256_) {
            this.f_9244_.m_6801_(this.f_9245_.m_19879_(), p_9278_, $$5);
            this.f_9256_ = $$5;
        }
        return $$4;
    }

    private void m_215125_(BlockPos p_215126_, boolean p_215127_, int p_215128_, String p_215129_) {
    }

    public void m_214168_(BlockPos p_215120_, ServerboundPlayerActionPacket.Action p_215121_, Direction p_215122_, int p_215123_, int p_215124_) {
        if (this.f_9245_.m_146892_().m_82557_(Vec3.m_82512_(p_215120_)) > ServerGamePacketListenerImpl.f_215198_) {
            this.m_215125_(p_215120_, false, p_215124_, "too far");
            return;
        }
        if (p_215120_.m_123342_() >= p_215123_) {
            this.f_9245_.f_8906_.m_9829_(new ClientboundBlockUpdatePacket(p_215120_, this.f_9244_.m_8055_(p_215120_)));
            this.m_215125_(p_215120_, false, p_215124_, "too high");
            return;
        }
        if (p_215121_ == ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK) {
            if (!this.f_9244_.m_7966_(this.f_9245_, p_215120_)) {
                this.f_9245_.f_8906_.m_9829_(new ClientboundBlockUpdatePacket(p_215120_, this.f_9244_.m_8055_(p_215120_)));
                this.m_215125_(p_215120_, false, p_215124_, "may not interact");
                return;
            }
            if (this.m_9295_()) {
                this.m_215116_(p_215120_, p_215124_, "creative destroy");
                return;
            }
            if (this.f_9245_.m_36187_(this.f_9244_, p_215120_, this.f_9247_)) {
                this.f_9245_.f_8906_.m_9829_(new ClientboundBlockUpdatePacket(p_215120_, this.f_9244_.m_8055_(p_215120_)));
                this.m_215125_(p_215120_, false, p_215124_, "block action restricted");
                return;
            }
            this.f_9250_ = this.f_9252_;
            float $$5 = 1.0f;
            BlockState $$6 = this.f_9244_.m_8055_(p_215120_);
            if (!$$6.m_60795_()) {
                $$6.m_60686_(this.f_9244_, p_215120_, this.f_9245_);
                $$5 = $$6.m_60625_(this.f_9245_, this.f_9245_.f_19853_, p_215120_);
            }
            if (!$$6.m_60795_() && $$5 >= 1.0f) {
                this.m_215116_(p_215120_, p_215124_, "insta mine");
            } else {
                if (this.f_9249_) {
                    this.f_9245_.f_8906_.m_9829_(new ClientboundBlockUpdatePacket(this.f_9251_, this.f_9244_.m_8055_(this.f_9251_)));
                    this.m_215125_(p_215120_, false, p_215124_, "abort destroying since another started (client insta mine, server disagreed)");
                }
                this.f_9249_ = true;
                this.f_9251_ = p_215120_.m_7949_();
                int $$7 = (int)($$5 * 10.0f);
                this.f_9244_.m_6801_(this.f_9245_.m_19879_(), p_215120_, $$7);
                this.m_215125_(p_215120_, true, p_215124_, "actual start of destroying");
                this.f_9256_ = $$7;
            }
        } else if (p_215121_ == ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK) {
            if (p_215120_.equals(this.f_9251_)) {
                int $$8 = this.f_9252_ - this.f_9250_;
                BlockState $$9 = this.f_9244_.m_8055_(p_215120_);
                if (!$$9.m_60795_()) {
                    float $$10 = $$9.m_60625_(this.f_9245_, this.f_9245_.f_19853_, p_215120_) * (float)($$8 + 1);
                    if ($$10 >= 0.7f) {
                        this.f_9249_ = false;
                        this.f_9244_.m_6801_(this.f_9245_.m_19879_(), p_215120_, -1);
                        this.m_215116_(p_215120_, p_215124_, "destroyed");
                        return;
                    }
                    if (!this.f_9253_) {
                        this.f_9249_ = false;
                        this.f_9253_ = true;
                        this.f_9254_ = p_215120_;
                        this.f_9255_ = this.f_9250_;
                    }
                }
            }
            this.m_215125_(p_215120_, true, p_215124_, "stopped destroying");
        } else if (p_215121_ == ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK) {
            this.f_9249_ = false;
            if (!Objects.equals(this.f_9251_, p_215120_)) {
                f_9246_.warn("Mismatch in destroy block pos: {} {}", (Object)this.f_9251_, (Object)p_215120_);
                this.f_9244_.m_6801_(this.f_9245_.m_19879_(), this.f_9251_, -1);
                this.m_215125_(p_215120_, true, p_215124_, "aborted mismatched destroying");
            }
            this.f_9244_.m_6801_(this.f_9245_.m_19879_(), p_215120_, -1);
            this.m_215125_(p_215120_, true, p_215124_, "aborted destroying");
        }
    }

    public void m_215116_(BlockPos p_215117_, int p_215118_, String p_215119_) {
        if (this.m_9280_(p_215117_)) {
            this.m_215125_(p_215117_, true, p_215118_, p_215119_);
        } else {
            this.f_9245_.f_8906_.m_9829_(new ClientboundBlockUpdatePacket(p_215117_, this.f_9244_.m_8055_(p_215117_)));
            this.m_215125_(p_215117_, false, p_215118_, p_215119_);
        }
    }

    public boolean m_9280_(BlockPos p_9281_) {
        BlockState $$1 = this.f_9244_.m_8055_(p_9281_);
        if (!this.f_9245_.m_21205_().m_41720_().m_6777_($$1, this.f_9244_, p_9281_, this.f_9245_)) {
            return false;
        }
        BlockEntity $$2 = this.f_9244_.m_7702_(p_9281_);
        Block $$3 = $$1.m_60734_();
        if ($$3 instanceof GameMasterBlock && !this.f_9245_.m_36337_()) {
            this.f_9244_.m_7260_(p_9281_, $$1, $$1, 3);
            return false;
        }
        if (this.f_9245_.m_36187_(this.f_9244_, p_9281_, this.f_9247_)) {
            return false;
        }
        $$3.m_5707_(this.f_9244_, p_9281_, $$1, this.f_9245_);
        boolean $$4 = this.f_9244_.m_7471_(p_9281_, false);
        if ($$4) {
            $$3.m_6786_(this.f_9244_, p_9281_, $$1);
        }
        if (this.m_9295_()) {
            return true;
        }
        ItemStack $$5 = this.f_9245_.m_21205_();
        ItemStack $$6 = $$5.m_41777_();
        boolean $$7 = this.f_9245_.m_36298_($$1);
        $$5.m_41686_(this.f_9244_, $$1, p_9281_, this.f_9245_);
        if ($$4 && $$7) {
            $$3.m_6240_(this.f_9244_, this.f_9245_, p_9281_, $$1, $$2, $$6);
        }
        return true;
    }

    public InteractionResult m_6261_(ServerPlayer p_9262_, Level p_9263_, ItemStack p_9264_, InteractionHand p_9265_) {
        if (this.f_9247_ == GameType.SPECTATOR) {
            return InteractionResult.PASS;
        }
        if (p_9262_.m_36335_().m_41519_(p_9264_.m_41720_())) {
            return InteractionResult.PASS;
        }
        int $$4 = p_9264_.m_41613_();
        int $$5 = p_9264_.m_41773_();
        InteractionResultHolder<ItemStack> $$6 = p_9264_.m_41682_(p_9263_, p_9262_, p_9265_);
        ItemStack $$7 = $$6.m_19095_();
        if ($$7 == p_9264_ && $$7.m_41613_() == $$4 && $$7.m_41779_() <= 0 && $$7.m_41773_() == $$5) {
            return $$6.m_19089_();
        }
        if ($$6.m_19089_() == InteractionResult.FAIL && $$7.m_41779_() > 0 && !p_9262_.m_6117_()) {
            return $$6.m_19089_();
        }
        if (p_9264_ != $$7) {
            p_9262_.m_21008_(p_9265_, $$7);
        }
        if (this.m_9295_()) {
            $$7.m_41764_($$4);
            if ($$7.m_41763_() && $$7.m_41773_() != $$5) {
                $$7.m_41721_($$5);
            }
        }
        if ($$7.m_41619_()) {
            p_9262_.m_21008_(p_9265_, ItemStack.f_41583_);
        }
        if (!p_9262_.m_6117_()) {
            p_9262_.f_36095_.m_150429_();
        }
        return $$6.m_19089_();
    }

    public InteractionResult m_7179_(ServerPlayer p_9266_, Level p_9267_, ItemStack p_9268_, InteractionHand p_9269_, BlockHitResult p_9270_) {
        InteractionResult $$15;
        InteractionResult $$11;
        BlockPos $$5 = p_9270_.m_82425_();
        BlockState $$6 = p_9267_.m_8055_($$5);
        if (this.f_9247_ == GameType.SPECTATOR) {
            MenuProvider $$7 = $$6.m_60750_(p_9267_, $$5);
            if ($$7 != null) {
                p_9266_.m_5893_($$7);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        boolean $$8 = !p_9266_.m_21205_().m_41619_() || !p_9266_.m_21206_().m_41619_();
        boolean $$9 = p_9266_.m_36341_() && $$8;
        ItemStack $$10 = p_9268_.m_41777_();
        if (!$$9 && ($$11 = $$6.m_60664_(p_9267_, p_9266_, p_9269_, p_9270_)).m_19077_()) {
            CriteriaTriggers.f_10562_.m_220040_(p_9266_, $$5, $$10);
            return $$11;
        }
        if (p_9268_.m_41619_() || p_9266_.m_36335_().m_41519_(p_9268_.m_41720_())) {
            return InteractionResult.PASS;
        }
        UseOnContext $$12 = new UseOnContext(p_9266_, p_9269_, p_9270_);
        if (this.m_9295_()) {
            int $$13 = p_9268_.m_41613_();
            InteractionResult $$14 = p_9268_.m_41661_($$12);
            p_9268_.m_41764_($$13);
        } else {
            $$15 = p_9268_.m_41661_($$12);
        }
        if ($$15.m_19077_()) {
            CriteriaTriggers.f_10562_.m_220040_(p_9266_, $$5, $$10);
        }
        return $$15;
    }

    public void m_9260_(ServerLevel p_9261_) {
        this.f_9244_ = p_9261_;
    }
}

