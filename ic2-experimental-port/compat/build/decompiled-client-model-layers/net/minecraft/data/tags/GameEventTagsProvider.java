/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 */
package net.minecraft.data.tags;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.GameEventTags;
import net.minecraft.world.level.gameevent.GameEvent;

public class GameEventTagsProvider
extends TagsProvider<GameEvent> {
    @VisibleForTesting
    static final GameEvent[] f_236425_ = new GameEvent[]{GameEvent.f_157791_, GameEvent.f_157792_, GameEvent.f_157793_, GameEvent.f_157794_, GameEvent.f_157795_, GameEvent.f_157796_, GameEvent.f_157797_, GameEvent.f_223702_, GameEvent.f_223703_, GameEvent.f_157802_, GameEvent.f_157803_, GameEvent.f_157804_, GameEvent.f_223704_, GameEvent.f_157806_, GameEvent.f_223705_, GameEvent.f_223706_, GameEvent.f_223707_, GameEvent.f_223708_, GameEvent.f_157810_, GameEvent.f_223709_, GameEvent.f_223710_, GameEvent.f_157811_, GameEvent.f_157812_, GameEvent.f_157816_, GameEvent.f_157769_, GameEvent.f_157770_, GameEvent.f_223696_, GameEvent.f_223697_, GameEvent.f_157772_, GameEvent.f_223699_, GameEvent.f_157774_, GameEvent.f_157775_, GameEvent.f_157776_, GameEvent.f_157777_, GameEvent.f_157778_, GameEvent.f_157781_, GameEvent.f_157784_, GameEvent.f_157785_, GameEvent.f_157786_, GameEvent.f_238175_};

    public GameEventTagsProvider(DataGenerator p_176826_) {
        super(p_176826_, Registry.f_175412_);
    }

    @Override
    protected void m_6577_() {
        this.m_206424_(GameEventTags.f_144302_).m_126584_((GameEvent[])f_236425_).m_126582_(GameEvent.f_157815_);
        this.m_206424_(GameEventTags.f_215854_).m_126582_(GameEvent.f_223700_);
        this.m_206424_(GameEventTags.f_215853_).m_126584_((GameEvent[])f_236425_).m_126582_(GameEvent.f_223701_).m_206428_(GameEventTags.f_215854_);
        this.m_206424_(GameEventTags.f_144303_).m_126584_((GameEvent[])new GameEvent[]{GameEvent.f_157770_, GameEvent.f_157778_, GameEvent.f_157785_, GameEvent.f_157786_, GameEvent.f_223698_, GameEvent.f_223697_});
        this.m_206424_(GameEventTags.f_215855_).m_126582_(GameEvent.f_223699_);
    }
}

