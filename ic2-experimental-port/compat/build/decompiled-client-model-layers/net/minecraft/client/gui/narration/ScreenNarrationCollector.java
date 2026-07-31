/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.gui.narration;

import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarrationThunk;

public class ScreenNarrationCollector {
    int f_169181_;
    final Map<EntryKey, NarrationEntry> f_169182_ = Maps.newTreeMap(Comparator.comparing(p_169196_ -> p_169196_.f_169207_).thenComparing(p_169185_ -> p_169185_.f_169208_));

    public void m_169186_(Consumer<NarrationElementOutput> p_169187_) {
        ++this.f_169181_;
        p_169187_.accept(new Output(0));
    }

    public String m_169188_(boolean p_169189_) {
        final StringBuilder $$1 = new StringBuilder();
        Consumer<String> $$2 = new Consumer<String>(){
            private boolean f_169199_ = true;

            @Override
            public void accept(String p_169204_) {
                if (!this.f_169199_) {
                    $$1.append(". ");
                }
                this.f_169199_ = false;
                $$1.append(p_169204_);
            }

            @Override
            public /* synthetic */ void accept(Object object) {
                this.accept((String)object);
            }
        };
        this.f_169182_.forEach((p_169193_, p_169194_) -> {
            if (p_169194_.f_169213_ == this.f_169181_ && (p_169189_ || !p_169194_.f_169214_)) {
                p_169194_.f_169212_.m_169168_($$2);
                p_169194_.f_169214_ = true;
            }
        });
        return $$1.toString();
    }

    class Output
    implements NarrationElementOutput {
        private final int f_169220_;

        Output(int p_169223_) {
            this.f_169220_ = p_169223_;
        }

        @Override
        public void m_142549_(NarratedElementType p_169226_, NarrationThunk<?> p_169227_) {
            ScreenNarrationCollector.this.f_169182_.computeIfAbsent(new EntryKey(p_169226_, this.f_169220_), p_169229_ -> new NarrationEntry()).m_169216_(ScreenNarrationCollector.this.f_169181_, p_169227_);
        }

        @Override
        public NarrationElementOutput m_142047_() {
            return new Output(this.f_169220_ + 1);
        }
    }

    static class NarrationEntry {
        NarrationThunk<?> f_169212_ = NarrationThunk.f_169153_;
        int f_169213_ = -1;
        boolean f_169214_;

        NarrationEntry() {
        }

        public NarrationEntry m_169216_(int p_169217_, NarrationThunk<?> p_169218_) {
            if (!this.f_169212_.equals(p_169218_)) {
                this.f_169212_ = p_169218_;
                this.f_169214_ = false;
            } else if (this.f_169213_ + 1 != p_169217_) {
                this.f_169214_ = false;
            }
            this.f_169213_ = p_169217_;
            return this;
        }
    }

    static class EntryKey {
        final NarratedElementType f_169207_;
        final int f_169208_;

        EntryKey(NarratedElementType p_169210_, int p_169211_) {
            this.f_169207_ = p_169210_;
            this.f_169208_ = p_169211_;
        }
    }
}

