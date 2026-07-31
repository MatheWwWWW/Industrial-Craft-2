/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

public abstract class BlockEntity {
    private static final Logger f_58854_ = LogUtils.getLogger();
    private final BlockEntityType<?> f_58855_;
    @Nullable
    protected Level f_58857_;
    protected final BlockPos f_58858_;
    protected boolean f_58859_;
    private BlockState f_58856_;

    public BlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        this.f_58855_ = p_155228_;
        this.f_58858_ = p_155229_.m_7949_();
        this.f_58856_ = p_155230_;
    }

    public static BlockPos m_187472_(CompoundTag p_187473_) {
        return new BlockPos(p_187473_.m_128451_("x"), p_187473_.m_128451_("y"), p_187473_.m_128451_("z"));
    }

    @Nullable
    public Level m_58904_() {
        return this.f_58857_;
    }

    public void m_142339_(Level p_155231_) {
        this.f_58857_ = p_155231_;
    }

    public boolean m_58898_() {
        return this.f_58857_ != null;
    }

    public void m_142466_(CompoundTag p_155245_) {
    }

    protected void m_183515_(CompoundTag p_187471_) {
    }

    public final CompoundTag m_187480_() {
        CompoundTag $$0 = this.m_187482_();
        this.m_187478_($$0);
        return $$0;
    }

    public final CompoundTag m_187481_() {
        CompoundTag $$0 = this.m_187482_();
        this.m_187474_($$0);
        return $$0;
    }

    public final CompoundTag m_187482_() {
        CompoundTag $$0 = new CompoundTag();
        this.m_183515_($$0);
        return $$0;
    }

    private void m_187474_(CompoundTag p_187475_) {
        ResourceLocation $$1 = BlockEntityType.m_58954_(this.m_58903_());
        if ($$1 == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        p_187475_.m_128359_("id", $$1.toString());
    }

    public static void m_187468_(CompoundTag p_187469_, BlockEntityType<?> p_187470_) {
        p_187469_.m_128359_("id", BlockEntityType.m_58954_(p_187470_).toString());
    }

    public void m_187476_(ItemStack p_187477_) {
        BlockItem.m_186338_(p_187477_, this.m_58903_(), this.m_187482_());
    }

    private void m_187478_(CompoundTag p_187479_) {
        this.m_187474_(p_187479_);
        p_187479_.m_128405_("x", this.f_58858_.m_123341_());
        p_187479_.m_128405_("y", this.f_58858_.m_123342_());
        p_187479_.m_128405_("z", this.f_58858_.m_123343_());
    }

    @Nullable
    public static BlockEntity m_155241_(BlockPos p_155242_, BlockState p_155243_, CompoundTag p_155244_) {
        String $$3 = p_155244_.m_128461_("id");
        ResourceLocation $$4 = ResourceLocation.m_135820_($$3);
        if ($$4 == null) {
            f_58854_.error("Block entity has invalid type: {}", (Object)$$3);
            return null;
        }
        return Registry.f_122830_.m_6612_($$4).map(p_155240_ -> {
            try {
                return p_155240_.m_155264_(p_155242_, p_155243_);
            }
            catch (Throwable $$4) {
                f_58854_.error("Failed to create block entity {}", (Object)$$3, (Object)$$4);
                return null;
            }
        }).map(p_155249_ -> {
            try {
                p_155249_.m_142466_(p_155244_);
                return p_155249_;
            }
            catch (Throwable $$3) {
                f_58854_.error("Failed to load data for block entity {}", (Object)$$3, (Object)$$3);
                return null;
            }
        }).orElseGet(() -> {
            f_58854_.warn("Skipping BlockEntity with id {}", (Object)$$3);
            return null;
        });
    }

    public void m_6596_() {
        if (this.f_58857_ != null) {
            BlockEntity.m_155232_(this.f_58857_, this.f_58858_, this.f_58856_);
        }
    }

    protected static void m_155232_(Level p_155233_, BlockPos p_155234_, BlockState p_155235_) {
        p_155233_.m_151543_(p_155234_);
        if (!p_155235_.m_60795_()) {
            p_155233_.m_46717_(p_155234_, p_155235_.m_60734_());
        }
    }

    public BlockPos m_58899_() {
        return this.f_58858_;
    }

    public BlockState m_58900_() {
        return this.f_58856_;
    }

    @Nullable
    public Packet<ClientGamePacketListener> m_58483_() {
        return null;
    }

    public CompoundTag m_5995_() {
        return new CompoundTag();
    }

    public boolean m_58901_() {
        return this.f_58859_;
    }

    public void m_7651_() {
        this.f_58859_ = true;
    }

    public void m_6339_() {
        this.f_58859_ = false;
    }

    public boolean m_7531_(int p_58889_, int p_58890_) {
        return false;
    }

    public void m_58886_(CrashReportCategory p_58887_) {
        p_58887_.m_128165_("Name", () -> Registry.f_122830_.m_7981_(this.m_58903_()) + " // " + this.getClass().getCanonicalName());
        if (this.f_58857_ == null) {
            return;
        }
        CrashReportCategory.m_178950_(p_58887_, this.f_58857_, this.f_58858_, this.m_58900_());
        CrashReportCategory.m_178950_(p_58887_, this.f_58857_, this.f_58858_, this.f_58857_.m_8055_(this.f_58858_));
    }

    public boolean m_6326_() {
        return false;
    }

    public BlockEntityType<?> m_58903_() {
        return this.f_58855_;
    }

    @Deprecated
    public void m_155250_(BlockState p_155251_) {
        this.f_58856_ = p_155251_;
    }
}

