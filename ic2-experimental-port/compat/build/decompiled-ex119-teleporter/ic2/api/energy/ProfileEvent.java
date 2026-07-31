/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.api.energy;

import java.util.Set;
import net.minecraftforge.eventbus.api.Event;

public abstract class ProfileEvent
extends Event {

    public static class Switch
    extends ProfileEvent {
        public final String from;
        public final String to;

        public Switch(String string, String string2) {
            this.from = string;
            this.to = string2;
        }
    }

    public static class Load
    extends ProfileEvent {
        public final Set<String> loaded;
        public final String active;

        public Load(Set<String> set, String string) {
            this.loaded = set;
            this.active = string;
        }
    }
}

