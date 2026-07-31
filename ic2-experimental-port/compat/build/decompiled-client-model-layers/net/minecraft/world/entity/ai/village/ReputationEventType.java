/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.village;

public interface ReputationEventType {
    public static final ReputationEventType f_26985_ = ReputationEventType.m_26991_("zombie_villager_cured");
    public static final ReputationEventType f_26986_ = ReputationEventType.m_26991_("golem_killed");
    public static final ReputationEventType f_26987_ = ReputationEventType.m_26991_("villager_hurt");
    public static final ReputationEventType f_26988_ = ReputationEventType.m_26991_("villager_killed");
    public static final ReputationEventType f_26989_ = ReputationEventType.m_26991_("trade");

    public static ReputationEventType m_26991_(final String p_26992_) {
        return new ReputationEventType(){

            public String toString() {
                return p_26992_;
            }
        };
    }
}

