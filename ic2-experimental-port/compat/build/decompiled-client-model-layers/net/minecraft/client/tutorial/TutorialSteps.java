/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.tutorial;

import java.util.function.Function;
import net.minecraft.client.tutorial.CompletedTutorialStepInstance;
import net.minecraft.client.tutorial.CraftPlanksTutorialStep;
import net.minecraft.client.tutorial.FindTreeTutorialStepInstance;
import net.minecraft.client.tutorial.MovementTutorialStepInstance;
import net.minecraft.client.tutorial.OpenInventoryTutorialStep;
import net.minecraft.client.tutorial.PunchTreeTutorialStepInstance;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialStepInstance;

public final class TutorialSteps
extends Enum<TutorialSteps> {
    public static final /* enum */ TutorialSteps MOVEMENT = new TutorialSteps("movement", MovementTutorialStepInstance::new);
    public static final /* enum */ TutorialSteps FIND_TREE = new TutorialSteps("find_tree", FindTreeTutorialStepInstance::new);
    public static final /* enum */ TutorialSteps PUNCH_TREE = new TutorialSteps("punch_tree", PunchTreeTutorialStepInstance::new);
    public static final /* enum */ TutorialSteps OPEN_INVENTORY = new TutorialSteps("open_inventory", OpenInventoryTutorialStep::new);
    public static final /* enum */ TutorialSteps CRAFT_PLANKS = new TutorialSteps("craft_planks", CraftPlanksTutorialStep::new);
    public static final /* enum */ TutorialSteps NONE = new TutorialSteps("none", CompletedTutorialStepInstance::new);
    private final String f_120630_;
    private final Function<Tutorial, ? extends TutorialStepInstance> f_120631_;
    private static final /* synthetic */ TutorialSteps[] $VALUES;

    public static TutorialSteps[] values() {
        return (TutorialSteps[])$VALUES.clone();
    }

    public static TutorialSteps valueOf(String p_120645_) {
        return Enum.valueOf(TutorialSteps.class, p_120645_);
    }

    private <T extends TutorialStepInstance> TutorialSteps(String p_120637_, Function<Tutorial, T> p_120638_) {
        this.f_120630_ = p_120637_;
        this.f_120631_ = p_120638_;
    }

    public TutorialStepInstance m_120640_(Tutorial p_120641_) {
        return this.f_120631_.apply(p_120641_);
    }

    public String m_120639_() {
        return this.f_120630_;
    }

    public static TutorialSteps m_120642_(String p_120643_) {
        for (TutorialSteps $$1 : TutorialSteps.values()) {
            if (!$$1.f_120630_.equals(p_120643_)) continue;
            return $$1;
        }
        return NONE;
    }

    private static /* synthetic */ TutorialSteps[] m_175029_() {
        return new TutorialSteps[]{MOVEMENT, FIND_TREE, PUNCH_TREE, OPEN_INVENTORY, CRAFT_PLANKS, NONE};
    }

    static {
        $VALUES = TutorialSteps.m_175029_();
    }
}

