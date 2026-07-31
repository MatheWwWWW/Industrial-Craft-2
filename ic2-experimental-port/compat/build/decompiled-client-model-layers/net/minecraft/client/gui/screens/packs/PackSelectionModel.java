/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.screens.packs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;

public class PackSelectionModel {
    private final PackRepository f_99902_;
    final List<Pack> f_99903_;
    final List<Pack> f_99904_;
    final Function<Pack, ResourceLocation> f_99905_;
    final Runnable f_99906_;
    private final Consumer<PackRepository> f_99907_;

    public PackSelectionModel(Runnable p_99909_, Function<Pack, ResourceLocation> p_99910_, PackRepository p_99911_, Consumer<PackRepository> p_99912_) {
        this.f_99906_ = p_99909_;
        this.f_99905_ = p_99910_;
        this.f_99902_ = p_99911_;
        this.f_99903_ = Lists.newArrayList(p_99911_.m_10524_());
        Collections.reverse(this.f_99903_);
        this.f_99904_ = Lists.newArrayList(p_99911_.m_10519_());
        this.f_99904_.removeAll(this.f_99903_);
        this.f_99907_ = p_99912_;
    }

    public Stream<Entry> m_99913_() {
        return this.f_99904_.stream().map(p_99920_ -> new UnselectedPackEntry((Pack)p_99920_));
    }

    public Stream<Entry> m_99918_() {
        return this.f_99903_.stream().map(p_99915_ -> new SelectedPackEntry((Pack)p_99915_));
    }

    public void m_99923_() {
        this.f_99902_.m_10509_((Collection)Lists.reverse(this.f_99903_).stream().map(Pack::m_10446_).collect(ImmutableList.toImmutableList()));
        this.f_99907_.accept(this.f_99902_);
    }

    public void m_99926_() {
        this.f_99902_.m_10506_();
        this.f_99903_.retainAll(this.f_99902_.m_10519_());
        this.f_99904_.clear();
        this.f_99904_.addAll(this.f_99902_.m_10519_());
        this.f_99904_.removeAll(this.f_99903_);
    }

    class SelectedPackEntry
    extends EntryBase {
        public SelectedPackEntry(Pack p_99954_) {
            super(p_99954_);
        }

        @Override
        protected List<Pack> m_6956_() {
            return PackSelectionModel.this.f_99903_;
        }

        @Override
        protected List<Pack> m_6958_() {
            return PackSelectionModel.this.f_99904_;
        }

        @Override
        public boolean m_7857_() {
            return true;
        }

        @Override
        public void m_7849_() {
        }

        @Override
        public void m_7850_() {
            this.m_99950_();
        }
    }

    class UnselectedPackEntry
    extends EntryBase {
        public UnselectedPackEntry(Pack p_99963_) {
            super(p_99963_);
        }

        @Override
        protected List<Pack> m_6956_() {
            return PackSelectionModel.this.f_99904_;
        }

        @Override
        protected List<Pack> m_6958_() {
            return PackSelectionModel.this.f_99903_;
        }

        @Override
        public boolean m_7857_() {
            return false;
        }

        @Override
        public void m_7849_() {
            this.m_99950_();
        }

        @Override
        public void m_7850_() {
        }
    }

    abstract class EntryBase
    implements Entry {
        private final Pack f_99933_;

        public EntryBase(Pack p_99936_) {
            this.f_99933_ = p_99936_;
        }

        protected abstract List<Pack> m_6956_();

        protected abstract List<Pack> m_6958_();

        @Override
        public ResourceLocation m_6876_() {
            return PackSelectionModel.this.f_99905_.apply(this.f_99933_);
        }

        @Override
        public PackCompatibility m_7709_() {
            return this.f_99933_.m_10443_();
        }

        @Override
        public Component m_7356_() {
            return this.f_99933_.m_10429_();
        }

        @Override
        public Component m_7359_() {
            return this.f_99933_.m_10442_();
        }

        @Override
        public PackSource m_7485_() {
            return this.f_99933_.m_10453_();
        }

        @Override
        public boolean m_7867_() {
            return this.f_99933_.m_10450_();
        }

        @Override
        public boolean m_7844_() {
            return this.f_99933_.m_10449_();
        }

        protected void m_99950_() {
            this.m_6956_().remove(this.f_99933_);
            this.f_99933_.m_10451_().m_10470_(this.m_6958_(), this.f_99933_, Function.identity(), true);
            PackSelectionModel.this.f_99906_.run();
        }

        protected void m_99938_(int p_99939_) {
            List<Pack> $$1 = this.m_6956_();
            int $$2 = $$1.indexOf(this.f_99933_);
            $$1.remove($$2);
            $$1.add($$2 + p_99939_, this.f_99933_);
            PackSelectionModel.this.f_99906_.run();
        }

        @Override
        public boolean m_7802_() {
            List<Pack> $$0 = this.m_6956_();
            int $$1 = $$0.indexOf(this.f_99933_);
            return $$1 > 0 && !$$0.get($$1 - 1).m_10450_();
        }

        @Override
        public void m_7852_() {
            this.m_99938_(-1);
        }

        @Override
        public boolean m_7803_() {
            List<Pack> $$0 = this.m_6956_();
            int $$1 = $$0.indexOf(this.f_99933_);
            return $$1 >= 0 && $$1 < $$0.size() - 1 && !$$0.get($$1 + 1).m_10450_();
        }

        @Override
        public void m_7845_() {
            this.m_99938_(1);
        }
    }

    public static interface Entry {
        public ResourceLocation m_6876_();

        public PackCompatibility m_7709_();

        public Component m_7356_();

        public Component m_7359_();

        public PackSource m_7485_();

        default public Component m_99929_() {
            return this.m_7485_().m_10540_(this.m_7359_());
        }

        public boolean m_7867_();

        public boolean m_7844_();

        public void m_7849_();

        public void m_7850_();

        public void m_7852_();

        public void m_7845_();

        public boolean m_7857_();

        default public boolean m_99930_() {
            return !this.m_7857_();
        }

        default public boolean m_99931_() {
            return this.m_7857_() && !this.m_7844_();
        }

        public boolean m_7802_();

        public boolean m_7803_();
    }
}

