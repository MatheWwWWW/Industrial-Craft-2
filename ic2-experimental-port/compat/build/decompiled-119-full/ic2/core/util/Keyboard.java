/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.util;

import ic2.api.util.IKeyboard;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.player.Player;

public class Keyboard
implements IKeyboard {
    protected final List<IKeyWatcher> watchers = new ArrayList<IKeyWatcher>();
    private final Map<Player, Set<Key>> playerKeys = new WeakHashMap<Player, Set<Key>>();

    @Override
    public boolean isAltKeyDown(Player player) {
        return this.get(player, Key.alt);
    }

    @Override
    public boolean isBoostKeyDown(Player player) {
        return this.get(player, Key.boost);
    }

    @Override
    public boolean isForwardKeyDown(Player player) {
        return this.get(player, Key.forward);
    }

    @Override
    public boolean isJumpKeyDown(Player player) {
        return this.get(player, Key.jump);
    }

    @Override
    public boolean isModeSwitchKeyDown(Player player) {
        return this.get(player, Key.modeSwitch);
    }

    @Override
    public boolean isSideinventoryKeyDown(Player player) {
        return this.get(player, Key.sideInventory);
    }

    @Override
    public boolean isHudModeKeyDown(Player player) {
        return this.get(player, Key.hubMode);
    }

    @Override
    public boolean isSneakKeyDown(Player player) {
        return player.m_6144_();
    }

    public void sendKeyUpdate() {
    }

    public void processKeyUpdate(Player player, int n) {
        this.playerKeys.put(player, Key.fromInt(n));
    }

    public void removePlayerReferences(Player player) {
        this.playerKeys.remove(player);
    }

    private boolean get(Player player, Key key) {
        Set<Key> set = this.playerKeys.get(player);
        if (set == null) {
            return false;
        }
        return set.contains((Object)key);
    }

    public void addKeyWatcher(IKeyWatcher iKeyWatcher) {
        this.watchers.add(iKeyWatcher);
    }

    public boolean isKeyDown(Player player, IKeyWatcher iKeyWatcher) {
        return this.get(player, iKeyWatcher.getRepresentation());
    }

    protected static enum Key {
        alt,
        boost,
        forward,
        modeSwitch,
        jump,
        sideInventory,
        hubMode;

        public static final Key[] keys;

        public static int toInt(Iterable<Key> iterable) {
            int n = 0;
            for (Key key : iterable) {
                n |= 1 << key.ordinal();
            }
            return n;
        }

        public static Set<Key> fromInt(int n) {
            EnumSet<Key> enumSet = EnumSet.noneOf(Key.class);
            for (int i = 0; n != 0 && i < keys.length; ++i, n >>= 1) {
                if ((n & 1) == 0) continue;
                enumSet.add(keys[i]);
            }
            return enumSet;
        }

        static {
            keys = Key.values();
        }
    }

    public static interface IKeyWatcher {
        @Environment(value=EnvType.CLIENT)
        public void checkForKey(Set<Key> var1);

        public Key getRepresentation();
    }
}

