/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.gameevent;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.Vec3;

public class GameEvent {
    public static final GameEvent f_223702_ = GameEvent.m_157822_("block_activate");
    public static final GameEvent f_157791_ = GameEvent.m_157822_("block_attach");
    public static final GameEvent f_157792_ = GameEvent.m_157822_("block_change");
    public static final GameEvent f_157793_ = GameEvent.m_157822_("block_close");
    public static final GameEvent f_223703_ = GameEvent.m_157822_("block_deactivate");
    public static final GameEvent f_157794_ = GameEvent.m_157822_("block_destroy");
    public static final GameEvent f_157795_ = GameEvent.m_157822_("block_detach");
    public static final GameEvent f_157796_ = GameEvent.m_157822_("block_open");
    public static final GameEvent f_157797_ = GameEvent.m_157822_("block_place");
    public static final GameEvent f_157802_ = GameEvent.m_157822_("container_close");
    public static final GameEvent f_157803_ = GameEvent.m_157822_("container_open");
    public static final GameEvent f_157804_ = GameEvent.m_157822_("dispense_fail");
    public static final GameEvent f_223704_ = GameEvent.m_157822_("drink");
    public static final GameEvent f_157806_ = GameEvent.m_157822_("eat");
    public static final GameEvent f_223705_ = GameEvent.m_157822_("elytra_glide");
    public static final GameEvent f_223706_ = GameEvent.m_157822_("entity_damage");
    public static final GameEvent f_223707_ = GameEvent.m_157822_("entity_die");
    public static final GameEvent f_223708_ = GameEvent.m_157822_("entity_interact");
    public static final GameEvent f_157810_ = GameEvent.m_157822_("entity_place");
    public static final GameEvent f_223709_ = GameEvent.m_157822_("entity_roar");
    public static final GameEvent f_223710_ = GameEvent.m_157822_("entity_shake");
    public static final GameEvent f_157811_ = GameEvent.m_157822_("equip");
    public static final GameEvent f_157812_ = GameEvent.m_157822_("explode");
    public static final GameEvent f_157815_ = GameEvent.m_157822_("flap");
    public static final GameEvent f_157816_ = GameEvent.m_157822_("fluid_pickup");
    public static final GameEvent f_157769_ = GameEvent.m_157822_("fluid_place");
    public static final GameEvent f_157770_ = GameEvent.m_157822_("hit_ground");
    public static final GameEvent f_223696_ = GameEvent.m_157822_("instrument_play");
    public static final GameEvent f_223697_ = GameEvent.m_157822_("item_interact_finish");
    public static final GameEvent f_223698_ = GameEvent.m_157822_("item_interact_start");
    public static final GameEvent f_238690_ = GameEvent.m_157824_("jukebox_play", 10);
    public static final GameEvent f_238649_ = GameEvent.m_157824_("jukebox_stop_play", 10);
    public static final GameEvent f_157772_ = GameEvent.m_157822_("lightning_strike");
    public static final GameEvent f_223699_ = GameEvent.m_157822_("note_block_play");
    public static final GameEvent f_157774_ = GameEvent.m_157822_("piston_contract");
    public static final GameEvent f_157775_ = GameEvent.m_157822_("piston_extend");
    public static final GameEvent f_157776_ = GameEvent.m_157822_("prime_fuse");
    public static final GameEvent f_157777_ = GameEvent.m_157822_("projectile_land");
    public static final GameEvent f_157778_ = GameEvent.m_157822_("projectile_shoot");
    public static final GameEvent f_223700_ = GameEvent.m_157822_("sculk_sensor_tendrils_clicking");
    public static final GameEvent f_157781_ = GameEvent.m_157822_("shear");
    public static final GameEvent f_223701_ = GameEvent.m_157824_("shriek", 32);
    public static final GameEvent f_157784_ = GameEvent.m_157822_("splash");
    public static final GameEvent f_157785_ = GameEvent.m_157822_("step");
    public static final GameEvent f_157786_ = GameEvent.m_157822_("swim");
    public static final GameEvent f_238175_ = GameEvent.m_157822_("teleport");
    public static final int f_157788_ = 16;
    private final String f_157789_;
    private final int f_157790_;
    private final Holder.Reference<GameEvent> f_204527_ = Registry.f_175412_.m_203693_(this);

    public GameEvent(String p_157819_, int p_157820_) {
        this.f_157789_ = p_157819_;
        this.f_157790_ = p_157820_;
    }

    public String m_157821_() {
        return this.f_157789_;
    }

    public int m_157827_() {
        return this.f_157790_;
    }

    private static GameEvent m_157822_(String p_157823_) {
        return GameEvent.m_157824_(p_157823_, 16);
    }

    private static GameEvent m_157824_(String p_157825_, int p_157826_) {
        return Registry.m_122961_(Registry.f_175412_, p_157825_, new GameEvent(p_157825_, p_157826_));
    }

    public String toString() {
        return "Game Event{ " + this.f_157789_ + " , " + this.f_157790_ + "}";
    }

    @Deprecated
    public Holder.Reference<GameEvent> m_204530_() {
        return this.f_204527_;
    }

    public boolean m_204528_(TagKey<GameEvent> p_204529_) {
        return this.f_204527_.m_203656_(p_204529_);
    }

    public static final class Message
    implements Comparable<Message> {
        private final GameEvent f_223729_;
        private final Vec3 f_223730_;
        private final Context f_223731_;
        private final GameEventListener f_223732_;
        private final double f_223733_;

        public Message(GameEvent p_223735_, Vec3 p_223736_, Context p_223737_, GameEventListener p_223738_, Vec3 p_223739_) {
            this.f_223729_ = p_223735_;
            this.f_223730_ = p_223736_;
            this.f_223731_ = p_223737_;
            this.f_223732_ = p_223738_;
            this.f_223733_ = p_223736_.m_82557_(p_223739_);
        }

        @Override
        public int compareTo(Message p_223742_) {
            return Double.compare(this.f_223733_, p_223742_.f_223733_);
        }

        public GameEvent m_223740_() {
            return this.f_223729_;
        }

        public Vec3 m_223743_() {
            return this.f_223730_;
        }

        public Context m_223744_() {
            return this.f_223731_;
        }

        public GameEventListener m_223747_() {
            return this.f_223732_;
        }

        @Override
        public /* synthetic */ int compareTo(Object object) {
            return this.compareTo((Message)object);
        }
    }

    public record Context(@Nullable Entity f_223711_, @Nullable BlockState f_223712_) {
        public static Context m_223717_(@Nullable Entity p_223718_) {
            return new Context(p_223718_, null);
        }

        public static Context m_223722_(@Nullable BlockState p_223723_) {
            return new Context(null, p_223723_);
        }

        public static Context m_223719_(@Nullable Entity p_223720_, @Nullable BlockState p_223721_) {
            return new Context(p_223720_, p_223721_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Context.class, "sourceEntity;affectedState", "f_223711_", "f_223712_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Context.class, "sourceEntity;affectedState", "f_223711_", "f_223712_"}, this);
        }

        @Override
        public final boolean equals(Object p_223726_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Context.class, "sourceEntity;affectedState", "f_223711_", "f_223712_"}, this, p_223726_);
        }
    }
}

