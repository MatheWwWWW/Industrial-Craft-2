/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class CommandBlockEntity
extends BlockEntity {
    private boolean f_59123_;
    private boolean f_59124_;
    private boolean f_59125_;
    private final BaseCommandBlock f_59127_ = new BaseCommandBlock(){

        @Override
        public void m_6590_(String p_59157_) {
            super.m_6590_(p_59157_);
            CommandBlockEntity.this.m_6596_();
        }

        @Override
        public ServerLevel m_5991_() {
            return (ServerLevel)CommandBlockEntity.this.f_58857_;
        }

        @Override
        public void m_7368_() {
            BlockState $$0 = CommandBlockEntity.this.f_58857_.m_8055_(CommandBlockEntity.this.f_58858_);
            this.m_5991_().m_7260_(CommandBlockEntity.this.f_58858_, $$0, $$0, 3);
        }

        @Override
        public Vec3 m_6607_() {
            return Vec3.m_82512_(CommandBlockEntity.this.f_58858_);
        }

        @Override
        public CommandSourceStack m_6712_() {
            return new CommandSourceStack(this, Vec3.m_82512_(CommandBlockEntity.this.f_58858_), Vec2.f_82462_, this.m_5991_(), 2, this.m_45439_().getString(), this.m_45439_(), this.m_5991_().m_7654_(), null);
        }
    };

    public CommandBlockEntity(BlockPos p_155380_, BlockState p_155381_) {
        super(BlockEntityType.f_58938_, p_155380_, p_155381_);
    }

    @Override
    protected void m_183515_(CompoundTag p_187491_) {
        super.m_183515_(p_187491_);
        this.f_59127_.m_45421_(p_187491_);
        p_187491_.m_128379_("powered", this.m_59142_());
        p_187491_.m_128379_("conditionMet", this.m_59145_());
        p_187491_.m_128379_("auto", this.m_59143_());
    }

    @Override
    public void m_142466_(CompoundTag p_155383_) {
        super.m_142466_(p_155383_);
        this.f_59127_.m_45431_(p_155383_);
        this.f_59123_ = p_155383_.m_128471_("powered");
        this.f_59125_ = p_155383_.m_128471_("conditionMet");
        this.m_59137_(p_155383_.m_128471_("auto"));
    }

    @Override
    public boolean m_6326_() {
        return true;
    }

    public BaseCommandBlock m_59141_() {
        return this.f_59127_;
    }

    public void m_59135_(boolean p_59136_) {
        this.f_59123_ = p_59136_;
    }

    public boolean m_59142_() {
        return this.f_59123_;
    }

    public boolean m_59143_() {
        return this.f_59124_;
    }

    public void m_59137_(boolean p_59138_) {
        boolean $$1 = this.f_59124_;
        this.f_59124_ = p_59138_;
        if (!$$1 && p_59138_ && !this.f_59123_ && this.f_58857_ != null && this.m_59148_() != Mode.SEQUENCE) {
            this.m_59152_();
        }
    }

    public void m_59144_() {
        Mode $$0 = this.m_59148_();
        if ($$0 == Mode.AUTO && (this.f_59123_ || this.f_59124_) && this.f_58857_ != null) {
            this.m_59152_();
        }
    }

    private void m_59152_() {
        Block $$0 = this.m_58900_().m_60734_();
        if ($$0 instanceof CommandBlock) {
            this.m_59146_();
            this.f_58857_.m_186460_(this.f_58858_, $$0, 1);
        }
    }

    public boolean m_59145_() {
        return this.f_59125_;
    }

    public boolean m_59146_() {
        this.f_59125_ = true;
        if (this.m_59151_()) {
            BlockEntity $$1;
            BlockPos $$0 = this.f_58858_.m_121945_(this.f_58857_.m_8055_(this.f_58858_).m_61143_(CommandBlock.f_51793_).m_122424_());
            this.f_59125_ = this.f_58857_.m_8055_($$0).m_60734_() instanceof CommandBlock ? ($$1 = this.f_58857_.m_7702_($$0)) instanceof CommandBlockEntity && ((CommandBlockEntity)$$1).m_59141_().m_45436_() > 0 : false;
        }
        return this.f_59125_;
    }

    public Mode m_59148_() {
        BlockState $$0 = this.m_58900_();
        if ($$0.m_60713_(Blocks.f_50272_)) {
            return Mode.REDSTONE;
        }
        if ($$0.m_60713_(Blocks.f_50447_)) {
            return Mode.AUTO;
        }
        if ($$0.m_60713_(Blocks.f_50448_)) {
            return Mode.SEQUENCE;
        }
        return Mode.REDSTONE;
    }

    public boolean m_59151_() {
        BlockState $$0 = this.f_58857_.m_8055_(this.m_58899_());
        if ($$0.m_60734_() instanceof CommandBlock) {
            return $$0.m_61143_(CommandBlock.f_51794_);
        }
        return false;
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode SEQUENCE = new Mode();
        public static final /* enum */ Mode AUTO = new Mode();
        public static final /* enum */ Mode REDSTONE = new Mode();
        private static final /* synthetic */ Mode[] $VALUES;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String p_59171_) {
            return Enum.valueOf(Mode.class, p_59171_);
        }

        private static /* synthetic */ Mode[] m_155384_() {
            return new Mode[]{SEQUENCE, AUTO, REDSTONE};
        }

        static {
            $VALUES = Mode.m_155384_();
        }
    }
}

