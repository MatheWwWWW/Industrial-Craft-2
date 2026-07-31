/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.boss.EnderDragonPart
 *  net.minecraft.world.entity.boss.enderdragon.EndCrystal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.item.base;

import ic2.core.IC2;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.plugins.IRegistryProvider;
import ic2.core.utils.tooltips.IAdvancedTooltip;
import ic2.core.utils.tooltips.ILangHelper;
import ic2.core.utils.tooltips.ToolTipHelper;
import ic2.core.utils.tooltips.helper.ITooltipProvider;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class IC2Item
extends Item
implements ILangHelper,
IAdvancedTooltip,
IRegistryProvider {
    static final Predicate<Entity> PREDICATE = EntitySelector.f_20408_.and(T -> T != null && T.m_6087_() && (T instanceof LivingEntity || T instanceof EnderDragonPart || T instanceof EndCrystal));
    public static final boolean SHOULD_STATUS = false;
    public static final RandomSource RANDOM = RandomSource.m_216327_();
    static final Consumer<LivingEntity>[] HAND_BASE = IC2Item.create();
    static final Consumer<LivingEntity>[] SLOT_BASE = IC2Item.createSlots();
    static final Consumer<LivingEntity> EMPTY_BASE = T -> {};
    protected ResourceLocation id;
    Boolean foiled;
    List<ITooltipProvider> providers = null;

    public IC2Item(String itemName, @Nullable PropertiesBuilder properties) {
        super((properties == null ? new PropertiesBuilder() : properties).group(IC2.IC2_MAIN_GROUP).build());
        this.id = GameData.checkPrefix((String)itemName.toLowerCase(), (boolean)false);
        this.foiled = properties == null ? null : properties.isFoiled();
    }

    public IC2Item(String itemName) {
        this(itemName, null);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public static Consumer<LivingEntity> empty() {
        return EMPTY_BASE;
    }

    public static Consumer<LivingEntity> get(InteractionHand hand) {
        return hand == InteractionHand.OFF_HAND ? HAND_BASE[1] : HAND_BASE[0];
    }

    public static Consumer<LivingEntity> get(EquipmentSlot slot) {
        return slot == null ? SLOT_BASE[0] : SLOT_BASE[slot.m_20749_()];
    }

    static Consumer<LivingEntity>[] create() {
        Consumer[] hands = new Consumer[]{entity -> entity.m_21190_(InteractionHand.MAIN_HAND), entity -> entity.m_21190_(InteractionHand.OFF_HAND)};
        return hands;
    }

    static Consumer<LivingEntity>[] createSlots() {
        Consumer[] slots = new Consumer[EquipmentSlot.values().length];
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            slots[slot.m_20749_()] = entity -> entity.m_21166_(slot);
        }
        return slots;
    }

    public IC2Item addTooltip(ITooltipProvider provider) {
        if (provider == null) {
            Thread.dumpStack();
            return this;
        }
        if (this.providers == null) {
            this.providers = CollectionUtils.createList();
        }
        this.providers.add(provider);
        return this;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        if (this.providers == null) {
            return;
        }
        ClientLevel world = Minecraft.m_91087_().f_91073_;
        int m = this.providers.size();
        for (int i = 0; i < m; ++i) {
            this.providers.get(i).addInformation(stack, (BlockGetter)world, helper.getTooltips(), type);
        }
    }

    public boolean m_5812_(ItemStack p_77636_1_) {
        if (this.foiled != null) {
            return this.foiled;
        }
        return super.m_5812_(p_77636_1_);
    }

    public void setDamage(ItemStack stack, int damage) {
        if (damage <= 0) {
            stack.m_41749_("Damage");
            return;
        }
        stack.m_41784_().m_128405_("Damage", damage);
    }

    public static HitResult rayTrace(Level worldIn, LivingEntity playerIn, boolean useLiquids, double maxDistance) {
        float f = playerIn.m_146909_();
        float f1 = playerIn.m_146908_();
        double d0 = playerIn.m_20185_();
        double d1 = playerIn.m_20186_() + (double)playerIn.m_20192_();
        double d2 = playerIn.m_20189_();
        Vec3 Vector3d = new Vec3(d0, d1, d2);
        float f2 = Mth.m_14089_((float)(-f1 * ((float)Math.PI / 180) - (float)Math.PI));
        float f3 = Mth.m_14031_((float)(-f1 * ((float)Math.PI / 180) - (float)Math.PI));
        float f4 = -Mth.m_14089_((float)(-f * ((float)Math.PI / 180)));
        float f5 = Mth.m_14031_((float)(-f * ((float)Math.PI / 180)));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec3d1 = Vector3d.m_82520_((double)f6 * maxDistance, (double)f5 * maxDistance, (double)f7 * maxDistance);
        return worldIn.m_45547_(new ClipContext(Vector3d, vec3d1, ClipContext.Block.COLLIDER, useLiquids ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE, (Entity)playerIn));
    }

    public static EntityHitResult rayTraceEntities(Level worldIn, LivingEntity playerIn, boolean useLiquids, double maxDistance) {
        return IC2Item.rayTraceEntities(worldIn, playerIn, maxDistance);
    }

    public static EntityHitResult rayTraceEntities(Level worldIn, LivingEntity playerIn, double maxDistance) {
        Vec3 angle = playerIn.m_20154_();
        Vec3 start = playerIn.m_20299_(1.0f);
        Vec3 end = start.m_82520_(angle.f_82479_ * maxDistance, angle.f_82480_ * maxDistance, angle.f_82481_ * maxDistance);
        Entity pointedEntity = null;
        Vec3 resultVec = null;
        double closestEntity = maxDistance;
        List list = worldIn.m_6249_((Entity)playerIn, playerIn.m_20191_().m_82363_(angle.f_82479_ * maxDistance, angle.f_82480_ * maxDistance, angle.f_82481_ * maxDistance).m_82400_(1.0), PREDICATE);
        for (int j = 0; j < list.size(); ++j) {
            double distance;
            Entity entity = (Entity)list.get(j);
            Optional result = entity.m_20191_().m_82400_((double)entity.m_6143_()).m_82371_(start, end);
            if (!result.isPresent() || !((distance = start.m_82554_((Vec3)result.get())) < closestEntity) || entity.m_20201_() == playerIn.m_20201_() && !playerIn.canRiderInteract()) continue;
            pointedEntity = entity;
            resultVec = (Vec3)result.get();
            closestEntity = distance;
        }
        return pointedEntity != null ? new EntityHitResult(pointedEntity, resultVec) : null;
    }
}

