/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.util.Map;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.AllayRenderer;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.AxolotlRenderer;
import net.minecraft.client.renderer.entity.BatRenderer;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.CaveSpiderRenderer;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.client.renderer.entity.DragonFireballRenderer;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EndermiteRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.client.renderer.entity.EvokerRenderer;
import net.minecraft.client.renderer.entity.ExperienceOrbRenderer;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.FireworkEntityRenderer;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.FrogRenderer;
import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.client.renderer.entity.GiantMobRenderer;
import net.minecraft.client.renderer.entity.GlowSquidRenderer;
import net.minecraft.client.renderer.entity.GoatRenderer;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.client.renderer.entity.HoglinRenderer;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.client.renderer.entity.IllusionerRenderer;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.LeashKnotRenderer;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.client.renderer.entity.MagmaCubeRenderer;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.MushroomCowRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.OcelotRenderer;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.renderer.entity.PandaRenderer;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.PhantomRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.RavagerRenderer;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.ShulkerBulletRenderer;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.SilverfishRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.client.renderer.entity.SpectralArrowRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.SquidRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.client.renderer.entity.TadpoleRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.TurtleRenderer;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.VindicatorRenderer;
import net.minecraft.client.renderer.entity.WanderingTraderRenderer;
import net.minecraft.client.renderer.entity.WardenRenderer;
import net.minecraft.client.renderer.entity.WitchRenderer;
import net.minecraft.client.renderer.entity.WitherBossRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.ZoglinRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

public class EntityRenderers {
    private static final Logger f_174030_ = LogUtils.getLogger();
    public static final String f_174029_ = "default";
    private static final Map<EntityType<?>, EntityRendererProvider<?>> f_174031_ = Maps.newHashMap();
    private static final Map<String, EntityRendererProvider<AbstractClientPlayer>> f_174032_ = ImmutableMap.of((Object)"default", p_174098_ -> new PlayerRenderer(p_174098_, false), (Object)"slim", p_174096_ -> new PlayerRenderer(p_174096_, true));

    private static <T extends Entity> void m_174036_(EntityType<? extends T> p_174037_, EntityRendererProvider<T> p_174038_) {
        f_174031_.put(p_174037_, p_174038_);
    }

    public static Map<EntityType<?>, EntityRenderer<?>> m_174049_(EntityRendererProvider.Context p_174050_) {
        ImmutableMap.Builder $$1 = ImmutableMap.builder();
        f_174031_.forEach((p_234602_, p_234603_) -> {
            try {
                $$1.put(p_234602_, p_234603_.m_174009_(p_174050_));
            }
            catch (Exception $$4) {
                throw new IllegalArgumentException("Failed to create model for " + Registry.f_122826_.m_7981_((EntityType<?>)p_234602_), $$4);
            }
        });
        return $$1.build();
    }

    public static Map<String, EntityRenderer<? extends Player>> m_174051_(EntityRendererProvider.Context p_174052_) {
        ImmutableMap.Builder $$1 = ImmutableMap.builder();
        f_174032_.forEach((p_234607_, p_234608_) -> {
            try {
                $$1.put(p_234607_, p_234608_.m_174009_(p_174052_));
            }
            catch (Exception $$4) {
                throw new IllegalArgumentException("Failed to create player model for " + p_234607_, $$4);
            }
        });
        return $$1.build();
    }

    public static boolean m_174035_() {
        boolean $$0 = true;
        for (EntityType entityType : Registry.f_122826_) {
            if (entityType == EntityType.f_20532_ || f_174031_.containsKey(entityType)) continue;
            f_174030_.warn("No renderer registered for {}", (Object)Registry.f_122826_.m_7981_(entityType));
            $$0 = false;
        }
        return !$$0;
    }

    static {
        EntityRenderers.m_174036_(EntityType.f_217014_, AllayRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20476_, NoopRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20529_, ArmorStandRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20548_, TippableArrowRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_147039_, AxolotlRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20549_, BatRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20550_, BeeRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20551_, BlazeRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20552_, p_174094_ -> new BoatRenderer(p_174094_, false));
        EntityRenderers.m_174036_(EntityType.f_20553_, CatRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20554_, CaveSpiderRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_217016_, p_174092_ -> new BoatRenderer(p_174092_, true));
        EntityRenderers.m_174036_(EntityType.f_20470_, p_174090_ -> new MinecartRenderer(p_174090_, ModelLayers.f_171276_));
        EntityRenderers.m_174036_(EntityType.f_20555_, ChickenRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20556_, CodRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20471_, p_174088_ -> new MinecartRenderer(p_174088_, ModelLayers.f_171279_));
        EntityRenderers.m_174036_(EntityType.f_20557_, CowRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20558_, CreeperRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20559_, DolphinRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20560_, p_174086_ -> new ChestedHorseRenderer(p_174086_, 0.87f, ModelLayers.f_171132_));
        EntityRenderers.m_174036_(EntityType.f_20561_, DragonFireballRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20562_, DrownedRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20483_, ThrownItemRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20563_, ElderGuardianRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20566_, EndermanRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20567_, EndermiteRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20565_, EnderDragonRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20484_, ThrownItemRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20564_, EndCrystalRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20568_, EvokerRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20569_, EvokerFangsRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20485_, ThrownItemRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20570_, ExperienceOrbRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20571_, p_174084_ -> new ThrownItemRenderer(p_174084_, 1.0f, true));
        EntityRenderers.m_174036_(EntityType.f_20450_, FallingBlockRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20463_, p_174082_ -> new ThrownItemRenderer(p_174082_, 3.0f, true));
        EntityRenderers.m_174036_(EntityType.f_20451_, FireworkEntityRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20533_, FishingHookRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20452_, FoxRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_217012_, FrogRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20472_, p_174080_ -> new MinecartRenderer(p_174080_, ModelLayers.f_171149_));
        EntityRenderers.m_174036_(EntityType.f_20453_, GhastRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20454_, p_174078_ -> new GiantMobRenderer(p_174078_, 6.0f));
        EntityRenderers.m_174036_(EntityType.f_147033_, ItemFrameRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_147034_, p_174076_ -> new GlowSquidRenderer(p_174076_, new SquidModel<GlowSquid>(p_174076_.m_174023_(ModelLayers.f_171154_))));
        EntityRenderers.m_174036_(EntityType.f_147035_, GoatRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20455_, GuardianRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20456_, HoglinRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20473_, p_174074_ -> new MinecartRenderer(p_174074_, ModelLayers.f_171185_));
        EntityRenderers.m_174036_(EntityType.f_20457_, HorseRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20458_, HuskRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20459_, IllusionerRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20460_, IronGolemRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20461_, ItemEntityRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20462_, ItemFrameRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20464_, LeashKnotRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20465_, LightningBoltRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20466_, p_174072_ -> new LlamaRenderer(p_174072_, ModelLayers.f_171194_));
        EntityRenderers.m_174036_(EntityType.f_20467_, LlamaSpitRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20468_, MagmaCubeRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_147036_, NoopRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20469_, p_174070_ -> new MinecartRenderer(p_174070_, ModelLayers.f_171198_));
        EntityRenderers.m_174036_(EntityType.f_20504_, MushroomCowRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20503_, p_174068_ -> new ChestedHorseRenderer(p_174068_, 0.92f, ModelLayers.f_171200_));
        EntityRenderers.m_174036_(EntityType.f_20505_, OcelotRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20506_, PaintingRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20507_, PandaRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20508_, ParrotRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20509_, PhantomRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20510_, PigRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20511_, p_174066_ -> new PiglinRenderer(p_174066_, ModelLayers.f_171206_, ModelLayers.f_171158_, ModelLayers.f_171159_, false));
        EntityRenderers.m_174036_(EntityType.f_20512_, p_174064_ -> new PiglinRenderer(p_174064_, ModelLayers.f_171207_, ModelLayers.f_171156_, ModelLayers.f_171157_, false));
        EntityRenderers.m_174036_(EntityType.f_20513_, PillagerRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20514_, PolarBearRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20486_, ThrownItemRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20516_, PufferfishRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20517_, RabbitRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20518_, RavagerRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20519_, SalmonRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20520_, SheepRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20521_, ShulkerRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20522_, ShulkerBulletRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20523_, SilverfishRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20524_, SkeletonRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20525_, p_174062_ -> new UndeadHorseRenderer(p_174062_, ModelLayers.f_171237_));
        EntityRenderers.m_174036_(EntityType.f_20526_, SlimeRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20527_, p_174060_ -> new ThrownItemRenderer(p_174060_, 0.75f, true));
        EntityRenderers.m_174036_(EntityType.f_20477_, ThrownItemRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20528_, SnowGolemRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20474_, p_174058_ -> new MinecartRenderer(p_174058_, ModelLayers.f_171244_));
        EntityRenderers.m_174036_(EntityType.f_20478_, SpectralArrowRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20479_, SpiderRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20480_, p_174056_ -> new SquidRenderer(p_174056_, new SquidModel(p_174056_.m_174023_(ModelLayers.f_171246_))));
        EntityRenderers.m_174036_(EntityType.f_20481_, StrayRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20482_, StriderRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_217013_, TadpoleRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20515_, TntRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20475_, TntMinecartRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20488_, p_174054_ -> new LlamaRenderer(p_174054_, ModelLayers.f_171254_));
        EntityRenderers.m_174036_(EntityType.f_20487_, ThrownTridentRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20489_, TropicalFishRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20490_, TurtleRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20491_, VexRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20492_, VillagerRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20493_, VindicatorRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_217015_, WardenRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20494_, WanderingTraderRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20495_, WitchRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20496_, WitherBossRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20497_, WitherSkeletonRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20498_, WitherSkullRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20499_, WolfRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20500_, ZoglinRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20501_, ZombieRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20502_, p_234612_ -> new UndeadHorseRenderer(p_234612_, ModelLayers.f_171225_));
        EntityRenderers.m_174036_(EntityType.f_20530_, ZombieVillagerRenderer::new);
        EntityRenderers.m_174036_(EntityType.f_20531_, p_234610_ -> new PiglinRenderer(p_234610_, ModelLayers.f_171231_, ModelLayers.f_171232_, ModelLayers.f_171233_, true));
    }
}

