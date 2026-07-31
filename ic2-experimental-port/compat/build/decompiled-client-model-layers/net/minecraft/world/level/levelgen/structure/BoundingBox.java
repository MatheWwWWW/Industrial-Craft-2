/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure;

import com.google.common.base.MoreObjects;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import org.slf4j.Logger;

public class BoundingBox {
    private static final Logger f_162355_ = LogUtils.getLogger();
    public static final Codec<BoundingBox> f_162354_ = Codec.INT_STREAM.comapFlatMap(p_162383_ -> Util.m_137539_(p_162383_, 6).map(p_162385_ -> new BoundingBox(p_162385_[0], p_162385_[1], p_162385_[2], p_162385_[3], p_162385_[4], p_162385_[5])), p_162391_ -> IntStream.of(p_162391_.f_162356_, p_162391_.f_162357_, p_162391_.f_162358_, p_162391_.f_162359_, p_162391_.f_162360_, p_162391_.f_162361_)).stable();
    private int f_162356_;
    private int f_162357_;
    private int f_162358_;
    private int f_162359_;
    private int f_162360_;
    private int f_162361_;

    public BoundingBox(BlockPos p_162364_) {
        this(p_162364_.m_123341_(), p_162364_.m_123342_(), p_162364_.m_123343_(), p_162364_.m_123341_(), p_162364_.m_123342_(), p_162364_.m_123343_());
    }

    public BoundingBox(int p_71001_, int p_71002_, int p_71003_, int p_71004_, int p_71005_, int p_71006_) {
        this.f_162356_ = p_71001_;
        this.f_162357_ = p_71002_;
        this.f_162358_ = p_71003_;
        this.f_162359_ = p_71004_;
        this.f_162360_ = p_71005_;
        this.f_162361_ = p_71006_;
        if (p_71004_ < p_71001_ || p_71005_ < p_71002_ || p_71006_ < p_71003_) {
            String $$6 = "Invalid bounding box data, inverted bounds for: " + this;
            if (SharedConstants.f_136183_) {
                throw new IllegalStateException($$6);
            }
            f_162355_.error($$6);
            this.f_162356_ = Math.min(p_71001_, p_71004_);
            this.f_162357_ = Math.min(p_71002_, p_71005_);
            this.f_162358_ = Math.min(p_71003_, p_71006_);
            this.f_162359_ = Math.max(p_71001_, p_71004_);
            this.f_162360_ = Math.max(p_71002_, p_71005_);
            this.f_162361_ = Math.max(p_71003_, p_71006_);
        }
    }

    public static BoundingBox m_162375_(Vec3i p_162376_, Vec3i p_162377_) {
        return new BoundingBox(Math.min(p_162376_.m_123341_(), p_162377_.m_123341_()), Math.min(p_162376_.m_123342_(), p_162377_.m_123342_()), Math.min(p_162376_.m_123343_(), p_162377_.m_123343_()), Math.max(p_162376_.m_123341_(), p_162377_.m_123341_()), Math.max(p_162376_.m_123342_(), p_162377_.m_123342_()), Math.max(p_162376_.m_123343_(), p_162377_.m_123343_()));
    }

    public static BoundingBox m_71044_() {
        return new BoundingBox(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public static BoundingBox m_71031_(int p_71032_, int p_71033_, int p_71034_, int p_71035_, int p_71036_, int p_71037_, int p_71038_, int p_71039_, int p_71040_, Direction p_71041_) {
        switch (p_71041_) {
            default: {
                return new BoundingBox(p_71032_ + p_71035_, p_71033_ + p_71036_, p_71034_ + p_71037_, p_71032_ + p_71038_ - 1 + p_71035_, p_71033_ + p_71039_ - 1 + p_71036_, p_71034_ + p_71040_ - 1 + p_71037_);
            }
            case NORTH: {
                return new BoundingBox(p_71032_ + p_71035_, p_71033_ + p_71036_, p_71034_ - p_71040_ + 1 + p_71037_, p_71032_ + p_71038_ - 1 + p_71035_, p_71033_ + p_71039_ - 1 + p_71036_, p_71034_ + p_71037_);
            }
            case WEST: {
                return new BoundingBox(p_71032_ - p_71040_ + 1 + p_71037_, p_71033_ + p_71036_, p_71034_ + p_71035_, p_71032_ + p_71037_, p_71033_ + p_71039_ - 1 + p_71036_, p_71034_ + p_71038_ - 1 + p_71035_);
            }
            case EAST: 
        }
        return new BoundingBox(p_71032_ + p_71037_, p_71033_ + p_71036_, p_71034_ + p_71035_, p_71032_ + p_71040_ - 1 + p_71037_, p_71033_ + p_71039_ - 1 + p_71036_, p_71034_ + p_71038_ - 1 + p_71035_);
    }

    public boolean m_71049_(BoundingBox p_71050_) {
        return this.f_162359_ >= p_71050_.f_162356_ && this.f_162356_ <= p_71050_.f_162359_ && this.f_162361_ >= p_71050_.f_162358_ && this.f_162358_ <= p_71050_.f_162361_ && this.f_162360_ >= p_71050_.f_162357_ && this.f_162357_ <= p_71050_.f_162360_;
    }

    public boolean m_71019_(int p_71020_, int p_71021_, int p_71022_, int p_71023_) {
        return this.f_162359_ >= p_71020_ && this.f_162356_ <= p_71022_ && this.f_162361_ >= p_71021_ && this.f_162358_ <= p_71023_;
    }

    public static Optional<BoundingBox> m_162378_(Iterable<BlockPos> p_162379_) {
        Iterator<BlockPos> $$1 = p_162379_.iterator();
        if (!$$1.hasNext()) {
            return Optional.empty();
        }
        BoundingBox $$2 = new BoundingBox($$1.next());
        $$1.forEachRemaining($$2::m_162371_);
        return Optional.of($$2);
    }

    public static Optional<BoundingBox> m_162388_(Iterable<BoundingBox> p_162389_) {
        Iterator<BoundingBox> $$1 = p_162389_.iterator();
        if (!$$1.hasNext()) {
            return Optional.empty();
        }
        BoundingBox $$2 = $$1.next();
        BoundingBox $$3 = new BoundingBox($$2.f_162356_, $$2.f_162357_, $$2.f_162358_, $$2.f_162359_, $$2.f_162360_, $$2.f_162361_);
        $$1.forEachRemaining($$3::m_162386_);
        return Optional.of($$3);
    }

    @Deprecated
    public BoundingBox m_162386_(BoundingBox p_162387_) {
        this.f_162356_ = Math.min(this.f_162356_, p_162387_.f_162356_);
        this.f_162357_ = Math.min(this.f_162357_, p_162387_.f_162357_);
        this.f_162358_ = Math.min(this.f_162358_, p_162387_.f_162358_);
        this.f_162359_ = Math.max(this.f_162359_, p_162387_.f_162359_);
        this.f_162360_ = Math.max(this.f_162360_, p_162387_.f_162360_);
        this.f_162361_ = Math.max(this.f_162361_, p_162387_.f_162361_);
        return this;
    }

    @Deprecated
    public BoundingBox m_162371_(BlockPos p_162372_) {
        this.f_162356_ = Math.min(this.f_162356_, p_162372_.m_123341_());
        this.f_162357_ = Math.min(this.f_162357_, p_162372_.m_123342_());
        this.f_162358_ = Math.min(this.f_162358_, p_162372_.m_123343_());
        this.f_162359_ = Math.max(this.f_162359_, p_162372_.m_123341_());
        this.f_162360_ = Math.max(this.f_162360_, p_162372_.m_123342_());
        this.f_162361_ = Math.max(this.f_162361_, p_162372_.m_123343_());
        return this;
    }

    @Deprecated
    public BoundingBox m_162367_(int p_162368_, int p_162369_, int p_162370_) {
        this.f_162356_ += p_162368_;
        this.f_162357_ += p_162369_;
        this.f_162358_ += p_162370_;
        this.f_162359_ += p_162368_;
        this.f_162360_ += p_162369_;
        this.f_162361_ += p_162370_;
        return this;
    }

    @Deprecated
    public BoundingBox m_162373_(Vec3i p_162374_) {
        return this.m_162367_(p_162374_.m_123341_(), p_162374_.m_123342_(), p_162374_.m_123343_());
    }

    public BoundingBox m_71045_(int p_71046_, int p_71047_, int p_71048_) {
        return new BoundingBox(this.f_162356_ + p_71046_, this.f_162357_ + p_71047_, this.f_162358_ + p_71048_, this.f_162359_ + p_71046_, this.f_162360_ + p_71047_, this.f_162361_ + p_71048_);
    }

    public BoundingBox m_191961_(int p_191962_) {
        return new BoundingBox(this.m_162395_() - p_191962_, this.m_162396_() - p_191962_, this.m_162398_() - p_191962_, this.m_162399_() + p_191962_, this.m_162400_() + p_191962_, this.m_162401_() + p_191962_);
    }

    public boolean m_71051_(Vec3i p_71052_) {
        return p_71052_.m_123341_() >= this.f_162356_ && p_71052_.m_123341_() <= this.f_162359_ && p_71052_.m_123343_() >= this.f_162358_ && p_71052_.m_123343_() <= this.f_162361_ && p_71052_.m_123342_() >= this.f_162357_ && p_71052_.m_123342_() <= this.f_162360_;
    }

    public Vec3i m_71053_() {
        return new Vec3i(this.f_162359_ - this.f_162356_, this.f_162360_ - this.f_162357_, this.f_162361_ - this.f_162358_);
    }

    public int m_71056_() {
        return this.f_162359_ - this.f_162356_ + 1;
    }

    public int m_71057_() {
        return this.f_162360_ - this.f_162357_ + 1;
    }

    public int m_71058_() {
        return this.f_162361_ - this.f_162358_ + 1;
    }

    public BlockPos m_162394_() {
        return new BlockPos(this.f_162356_ + (this.f_162359_ - this.f_162356_ + 1) / 2, this.f_162357_ + (this.f_162360_ - this.f_162357_ + 1) / 2, this.f_162358_ + (this.f_162361_ - this.f_162358_ + 1) / 2);
    }

    public void m_162380_(Consumer<BlockPos> p_162381_) {
        BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
        p_162381_.accept($$1.m_122178_(this.f_162359_, this.f_162360_, this.f_162361_));
        p_162381_.accept($$1.m_122178_(this.f_162356_, this.f_162360_, this.f_162361_));
        p_162381_.accept($$1.m_122178_(this.f_162359_, this.f_162357_, this.f_162361_));
        p_162381_.accept($$1.m_122178_(this.f_162356_, this.f_162357_, this.f_162361_));
        p_162381_.accept($$1.m_122178_(this.f_162359_, this.f_162360_, this.f_162358_));
        p_162381_.accept($$1.m_122178_(this.f_162356_, this.f_162360_, this.f_162358_));
        p_162381_.accept($$1.m_122178_(this.f_162359_, this.f_162357_, this.f_162358_));
        p_162381_.accept($$1.m_122178_(this.f_162356_, this.f_162357_, this.f_162358_));
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("minX", this.f_162356_).add("minY", this.f_162357_).add("minZ", this.f_162358_).add("maxX", this.f_162359_).add("maxY", this.f_162360_).add("maxZ", this.f_162361_).toString();
    }

    public boolean equals(Object p_162393_) {
        if (this == p_162393_) {
            return true;
        }
        if (p_162393_ instanceof BoundingBox) {
            BoundingBox $$1 = (BoundingBox)p_162393_;
            return this.f_162356_ == $$1.f_162356_ && this.f_162357_ == $$1.f_162357_ && this.f_162358_ == $$1.f_162358_ && this.f_162359_ == $$1.f_162359_ && this.f_162360_ == $$1.f_162360_ && this.f_162361_ == $$1.f_162361_;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.f_162356_, this.f_162357_, this.f_162358_, this.f_162359_, this.f_162360_, this.f_162361_);
    }

    public int m_162395_() {
        return this.f_162356_;
    }

    public int m_162396_() {
        return this.f_162357_;
    }

    public int m_162398_() {
        return this.f_162358_;
    }

    public int m_162399_() {
        return this.f_162359_;
    }

    public int m_162400_() {
        return this.f_162360_;
    }

    public int m_162401_() {
        return this.f_162361_;
    }
}

