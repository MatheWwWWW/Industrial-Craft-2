/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.commands;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public final class CommandBuildContext {
    private final RegistryAccess f_227129_;
    MissingTagAccessPolicy f_227130_ = MissingTagAccessPolicy.FAIL;

    public CommandBuildContext(RegistryAccess p_227132_) {
        this.f_227129_ = p_227132_;
    }

    public void m_227135_(MissingTagAccessPolicy p_227136_) {
        this.f_227130_ = p_227136_;
    }

    public <T> HolderLookup<T> m_227133_(ResourceKey<? extends Registry<T>> p_227134_) {
        return new HolderLookup.RegistryLookup<T>(this.f_227129_.m_175515_(p_227134_)){

            @Override
            public Optional<? extends HolderSet<T>> m_213810_(TagKey<T> p_227142_) {
                return switch (CommandBuildContext.this.f_227130_) {
                    default -> throw new IncompatibleClassChangeError();
                    case MissingTagAccessPolicy.FAIL -> this.f_235703_.m_203431_(p_227142_);
                    case MissingTagAccessPolicy.CREATE_NEW -> Optional.of(this.f_235703_.m_203561_(p_227142_));
                    case MissingTagAccessPolicy.RETURN_EMPTY -> {
                        Optional $$1 = this.f_235703_.m_203431_(p_227142_);
                        yield Optional.of($$1.isPresent() ? (HolderSet.Direct)((Object)$$1.get()) : HolderSet.m_205809_(new Holder[0]));
                    }
                };
            }
        };
    }

    public static final class MissingTagAccessPolicy
    extends Enum<MissingTagAccessPolicy> {
        public static final /* enum */ MissingTagAccessPolicy CREATE_NEW = new MissingTagAccessPolicy();
        public static final /* enum */ MissingTagAccessPolicy RETURN_EMPTY = new MissingTagAccessPolicy();
        public static final /* enum */ MissingTagAccessPolicy FAIL = new MissingTagAccessPolicy();
        private static final /* synthetic */ MissingTagAccessPolicy[] $VALUES;

        public static MissingTagAccessPolicy[] values() {
            return (MissingTagAccessPolicy[])$VALUES.clone();
        }

        public static MissingTagAccessPolicy valueOf(String p_227155_) {
            return Enum.valueOf(MissingTagAccessPolicy.class, p_227155_);
        }

        private static /* synthetic */ MissingTagAccessPolicy[] m_227153_() {
            return new MissingTagAccessPolicy[]{CREATE_NEW, RETURN_EMPTY, FAIL};
        }

        static {
            $VALUES = MissingTagAccessPolicy.m_227153_();
        }
    }
}

