/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.gameevent;

import java.util.function.BiConsumer;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.Vec3;

public interface GameEventDispatcher {
    public static final GameEventDispatcher f_157829_ = new GameEventDispatcher(){

        @Override
        public boolean m_142086_() {
            return true;
        }

        @Override
        public void m_142501_(GameEventListener p_157843_) {
        }

        @Override
        public void m_142500_(GameEventListener p_157845_) {
        }

        @Override
        public boolean m_213682_(GameEvent p_223753_, Vec3 p_223754_, GameEvent.Context p_223755_, BiConsumer<GameEventListener, Vec3> p_223756_) {
            return false;
        }
    };

    public boolean m_142086_();

    public void m_142501_(GameEventListener var1);

    public void m_142500_(GameEventListener var1);

    public boolean m_213682_(GameEvent var1, Vec3 var2, GameEvent.Context var3, BiConsumer<GameEventListener, Vec3> var4);
}

