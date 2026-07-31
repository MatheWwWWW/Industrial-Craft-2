/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.level.storage;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class CommandStorage {
    private static final String f_164834_ = "command_storage_";
    private final Map<String, Container> f_78032_ = Maps.newHashMap();
    private final DimensionDataStorage f_78033_;

    public CommandStorage(DimensionDataStorage p_78035_) {
        this.f_78033_ = p_78035_;
    }

    private Container m_164835_(String p_164836_) {
        Container $$1 = new Container();
        this.f_78032_.put(p_164836_, $$1);
        return $$1;
    }

    public CompoundTag m_78044_(ResourceLocation p_78045_) {
        String $$1 = p_78045_.m_135827_();
        Container $$2 = this.f_78033_.m_164858_(p_164844_ -> this.m_164835_($$1).m_164849_((CompoundTag)p_164844_), CommandStorage.m_78037_($$1));
        return $$2 != null ? $$2.m_78058_(p_78045_.m_135815_()) : new CompoundTag();
    }

    public void m_78046_(ResourceLocation p_78047_, CompoundTag p_78048_) {
        String $$2 = p_78047_.m_135827_();
        this.f_78033_.m_164861_(p_164839_ -> this.m_164835_($$2).m_164849_((CompoundTag)p_164839_), () -> this.m_164835_($$2), CommandStorage.m_78037_($$2)).m_78063_(p_78047_.m_135815_(), p_78048_);
    }

    public Stream<ResourceLocation> m_78036_() {
        return this.f_78032_.entrySet().stream().flatMap(p_164841_ -> ((Container)p_164841_.getValue()).m_78072_((String)p_164841_.getKey()));
    }

    private static String m_78037_(String p_78038_) {
        return f_164834_ + p_78038_;
    }

    static class Container
    extends SavedData {
        private static final String f_164847_ = "contents";
        private final Map<String, CompoundTag> f_78055_ = Maps.newHashMap();

        Container() {
        }

        Container m_164849_(CompoundTag p_164850_) {
            CompoundTag $$1 = p_164850_.m_128469_(f_164847_);
            for (String $$2 : $$1.m_128431_()) {
                this.f_78055_.put($$2, $$1.m_128469_($$2));
            }
            return this;
        }

        @Override
        public CompoundTag m_7176_(CompoundTag p_78075_) {
            CompoundTag $$1 = new CompoundTag();
            this.f_78055_.forEach((p_78070_, p_78071_) -> $$1.m_128365_((String)p_78070_, p_78071_.m_6426_()));
            p_78075_.m_128365_(f_164847_, $$1);
            return p_78075_;
        }

        public CompoundTag m_78058_(String p_78059_) {
            CompoundTag $$1 = this.f_78055_.get(p_78059_);
            return $$1 != null ? $$1 : new CompoundTag();
        }

        public void m_78063_(String p_78064_, CompoundTag p_78065_) {
            if (p_78065_.m_128456_()) {
                this.f_78055_.remove(p_78064_);
            } else {
                this.f_78055_.put(p_78064_, p_78065_);
            }
            this.m_77762_();
        }

        public Stream<ResourceLocation> m_78072_(String p_78073_) {
            return this.f_78055_.keySet().stream().map(p_78062_ -> new ResourceLocation(p_78073_, (String)p_78062_));
        }
    }
}

