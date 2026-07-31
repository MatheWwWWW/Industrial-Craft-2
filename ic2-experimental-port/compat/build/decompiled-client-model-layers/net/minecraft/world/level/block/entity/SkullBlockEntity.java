/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.authlib.properties.Property
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.Services;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SkullBlockEntity
extends BlockEntity {
    public static final String f_155729_ = "SkullOwner";
    @Nullable
    private static GameProfileCache f_59755_;
    @Nullable
    private static MinecraftSessionService f_59756_;
    @Nullable
    private static Executor f_182457_;
    @Nullable
    private GameProfile f_59757_;
    private int f_59758_;
    private boolean f_59759_;

    public SkullBlockEntity(BlockPos p_155731_, BlockState p_155732_) {
        super(BlockEntityType.f_58931_, p_155731_, p_155732_);
    }

    public static void m_222885_(Services p_222886_, Executor p_222887_) {
        f_59755_ = p_222886_.f_214336_();
        f_59756_ = p_222886_.f_214333_();
        f_182457_ = p_222887_;
    }

    public static void m_196704_() {
        f_59755_ = null;
        f_59756_ = null;
        f_182457_ = null;
    }

    @Override
    protected void m_183515_(CompoundTag p_187518_) {
        super.m_183515_(p_187518_);
        if (this.f_59757_ != null) {
            CompoundTag $$1 = new CompoundTag();
            NbtUtils.m_129230_($$1, this.f_59757_);
            p_187518_.m_128365_(f_155729_, $$1);
        }
    }

    @Override
    public void m_142466_(CompoundTag p_155745_) {
        String $$1;
        super.m_142466_(p_155745_);
        if (p_155745_.m_128425_(f_155729_, 10)) {
            this.m_59769_(NbtUtils.m_129228_(p_155745_.m_128469_(f_155729_)));
        } else if (p_155745_.m_128425_("ExtraType", 8) && !StringUtil.m_14408_($$1 = p_155745_.m_128461_("ExtraType"))) {
            this.m_59769_(new GameProfile(null, $$1));
        }
    }

    public static void m_155733_(Level p_155734_, BlockPos p_155735_, BlockState p_155736_, SkullBlockEntity p_155737_) {
        if (p_155734_.m_46753_(p_155735_)) {
            p_155737_.f_59759_ = true;
            ++p_155737_.f_59758_;
        } else {
            p_155737_.f_59759_ = false;
        }
    }

    public float m_59762_(float p_59763_) {
        if (this.f_59759_) {
            return (float)this.f_59758_ + p_59763_;
        }
        return this.f_59758_;
    }

    @Nullable
    public GameProfile m_59779_() {
        return this.f_59757_;
    }

    public ClientboundBlockEntityDataPacket m_58483_() {
        return ClientboundBlockEntityDataPacket.m_195640_(this);
    }

    @Override
    public CompoundTag m_5995_() {
        return this.m_187482_();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_59769_(@Nullable GameProfile p_59770_) {
        SkullBlockEntity skullBlockEntity = this;
        synchronized (skullBlockEntity) {
            this.f_59757_ = p_59770_;
        }
        this.m_59780_();
    }

    private void m_59780_() {
        SkullBlockEntity.m_155738_(this.f_59757_, p_155747_ -> {
            this.f_59757_ = p_155747_;
            this.m_6596_();
        });
    }

    public static void m_155738_(@Nullable GameProfile p_155739_, Consumer<GameProfile> p_155740_) {
        if (p_155739_ == null || StringUtil.m_14408_(p_155739_.getName()) || p_155739_.isComplete() && p_155739_.getProperties().containsKey((Object)"textures") || f_59755_ == null || f_59756_ == null) {
            p_155740_.accept(p_155739_);
            return;
        }
        f_59755_.m_143967_(p_155739_.getName(), p_182470_ -> Util.m_183991_().execute(() -> Util.m_137521_(p_182470_, p_182479_ -> {
            Property $$2 = (Property)Iterables.getFirst((Iterable)p_182479_.getProperties().get((Object)"textures"), null);
            if ($$2 == null) {
                p_182479_ = f_59756_.fillProfileProperties(p_182479_, true);
            }
            GameProfile $$3 = p_182479_;
            f_182457_.execute(() -> {
                f_59755_.m_10991_($$3);
                p_155740_.accept($$3);
            });
        }, () -> f_182457_.execute(() -> p_155740_.accept(p_155739_)))));
    }

    public /* synthetic */ Packet m_58483_() {
        return this.m_58483_();
    }
}

