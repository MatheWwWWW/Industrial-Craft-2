/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.advancements.CriterionTrigger
 *  net.minecraft.advancements.CriterionTrigger$Listener
 *  net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance
 *  net.minecraft.advancements.critereon.DeserializationContext
 *  net.minecraft.advancements.critereon.EntityPredicate$Composite
 *  net.minecraft.advancements.critereon.SerializationContext
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.PlayerAdvancements
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.platform.advancements;

import com.google.gson.JsonObject;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class IdTrigger
implements CriterionTrigger<Instance> {
    static final ResourceLocation ID = new ResourceLocation("ic2", "id");
    private final Map<PlayerAdvancements, Listeners> listeners = CollectionUtils.createLinkedMap();

    public ResourceLocation m_7295_() {
        return ID;
    }

    public void m_6467_(PlayerAdvancements playerAdvancementsIn, CriterionTrigger.Listener<Instance> listener) {
        Listeners playerListeners = this.listeners.get(playerAdvancementsIn);
        if (playerListeners == null) {
            playerListeners = new Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, playerListeners);
        }
        playerListeners.add(listener);
    }

    public void m_6468_(PlayerAdvancements playerAdvancementsIn, CriterionTrigger.Listener<Instance> listener) {
        Listeners playerListeners = this.listeners.get(playerAdvancementsIn);
        if (playerListeners != null && playerListeners.remove(listener) && playerListeners.isEmpty()) {
            this.listeners.remove(playerAdvancementsIn);
        }
    }

    public void m_5656_(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    public Instance createInstance(JsonObject object, DeserializationContext conditions) {
        return new Instance(new ResourceLocation(object.get("location").getAsString()), EntityPredicate.Composite.m_36677_((JsonObject)object, (String)"player", (DeserializationContext)conditions));
    }

    public void onTrigger(Player player, ResourceLocation id) {
        Listeners listener;
        if (player instanceof ServerPlayer && (listener = this.listeners.get(((ServerPlayer)player).m_8960_())) != null) {
            listener.trigger(id);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<CriterionTrigger.Listener<Instance>> listeners = CollectionUtils.createLinkedSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(CriterionTrigger.Listener<Instance> listener) {
            this.listeners.add(listener);
        }

        public boolean remove(CriterionTrigger.Listener<Instance> listener) {
            return this.listeners.remove(listener);
        }

        public void trigger(ResourceLocation location) {
            ObjectList list = CollectionUtils.createList();
            for (CriterionTrigger.Listener<Instance> listener : this.listeners) {
                if (!((Instance)listener.m_13685_()).test(location)) continue;
                list.add(listener);
            }
            if (list.size() > 0) {
                int m = list.size();
                for (int i = 0; i < m; ++i) {
                    ((CriterionTrigger.Listener)list.get(i)).m_13686_(this.playerAdvancements);
                }
            }
        }
    }

    public static class Instance
    extends AbstractCriterionTriggerInstance {
        ResourceLocation location;

        public Instance(ResourceLocation location) {
            super(ID, EntityPredicate.Composite.f_36667_);
            this.location = location;
        }

        public Instance(ResourceLocation location, EntityPredicate.Composite pre) {
            super(ID, pre);
            this.location = location;
        }

        public boolean test(ResourceLocation id) {
            return this.location.equals((Object)id);
        }

        public JsonObject m_7683_(SerializationContext conditions) {
            JsonObject object = super.m_7683_(conditions);
            object.addProperty("location", this.location.toString());
            return object;
        }
    }
}

