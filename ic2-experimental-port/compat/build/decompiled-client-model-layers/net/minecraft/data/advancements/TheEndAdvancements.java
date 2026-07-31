/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.advancements;

import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LevitationTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public class TheEndAdvancements
implements Consumer<Consumer<Advancement>> {
    @Override
    public void accept(Consumer<Advancement> p_124030_) {
        Advancement $$1 = Advancement.Builder.m_138353_().m_138371_(Blocks.f_50259_, Component.m_237115_("advancements.end.root.title"), Component.m_237115_("advancements.end.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/end.png"), FrameType.TASK, false, false, false).m_138386_("entered_end", ChangeDimensionTrigger.TriggerInstance.m_19782_(Level.f_46430_)).m_138389_(p_124030_, "end/root");
        Advancement $$2 = Advancement.Builder.m_138353_().m_138398_($$1).m_138371_(Blocks.f_50320_, Component.m_237115_("advancements.end.kill_dragon.title"), Component.m_237115_("advancements.end.kill_dragon.description"), null, FrameType.TASK, true, true, false).m_138386_("killed_dragon", KilledTrigger.TriggerInstance.m_48134_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20565_))).m_138389_(p_124030_, "end/kill_dragon");
        Advancement $$3 = Advancement.Builder.m_138353_().m_138398_($$2).m_138371_(Items.f_42584_, Component.m_237115_("advancements.end.enter_end_gateway.title"), Component.m_237115_("advancements.end.enter_end_gateway.description"), null, FrameType.TASK, true, true, false).m_138386_("entered_end_gateway", EnterBlockTrigger.TriggerInstance.m_31297_(Blocks.f_50446_)).m_138389_(p_124030_, "end/enter_end_gateway");
        Advancement.Builder.m_138353_().m_138398_($$2).m_138371_(Items.f_42729_, Component.m_237115_("advancements.end.respawn_dragon.title"), Component.m_237115_("advancements.end.respawn_dragon.description"), null, FrameType.GOAL, true, true, false).m_138386_("summoned_dragon", SummonedEntityTrigger.TriggerInstance.m_68275_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20565_))).m_138389_(p_124030_, "end/respawn_dragon");
        Advancement $$4 = Advancement.Builder.m_138353_().m_138398_($$3).m_138371_(Blocks.f_50492_, Component.m_237115_("advancements.end.find_end_city.title"), Component.m_237115_("advancements.end.find_end_city.description"), null, FrameType.TASK, true, true, false).m_138386_("in_city", PlayerTrigger.TriggerInstance.m_222635_(LocationPredicate.m_220589_(BuiltinStructures.f_209861_))).m_138389_(p_124030_, "end/find_end_city");
        Advancement.Builder.m_138353_().m_138398_($$2).m_138371_(Items.f_42735_, Component.m_237115_("advancements.end.dragon_breath.title"), Component.m_237115_("advancements.end.dragon_breath.description"), null, FrameType.GOAL, true, true, false).m_138386_("dragon_breath", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42735_)).m_138389_(p_124030_, "end/dragon_breath");
        Advancement.Builder.m_138353_().m_138398_($$4).m_138371_(Items.f_42748_, Component.m_237115_("advancements.end.levitate.title"), Component.m_237115_("advancements.end.levitate.description"), null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(50)).m_138386_("levitated", LevitationTrigger.TriggerInstance.m_49144_(DistancePredicate.m_148838_(MinMaxBounds.Doubles.m_154804_(50.0)))).m_138389_(p_124030_, "end/levitate");
        Advancement.Builder.m_138353_().m_138398_($$4).m_138371_(Items.f_42741_, Component.m_237115_("advancements.end.elytra.title"), Component.m_237115_("advancements.end.elytra.description"), null, FrameType.GOAL, true, true, false).m_138386_("elytra", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42741_)).m_138389_(p_124030_, "end/elytra");
        Advancement.Builder.m_138353_().m_138398_($$2).m_138371_(Blocks.f_50260_, Component.m_237115_("advancements.end.dragon_egg.title"), Component.m_237115_("advancements.end.dragon_egg.description"), null, FrameType.GOAL, true, true, false).m_138386_("dragon_egg", InventoryChangeTrigger.TriggerInstance.m_43199_(Blocks.f_50260_)).m_138389_(p_124030_, "end/dragon_egg");
    }

    @Override
    public /* synthetic */ void accept(Object object) {
        this.accept((Consumer)object);
    }
}

