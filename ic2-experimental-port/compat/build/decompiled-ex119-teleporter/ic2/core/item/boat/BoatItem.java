/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.item.boat;

import ic2.core.IC2;
import ic2.core.util.LogCategory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BoatItem
extends Item {
    private static final Predicate<Entity> RIDERS = EntitySelector.f_20408_.and(Entity::m_6087_);
    private final Class<? extends Boat> boatEntityClass;
    private final EntityType<? extends Boat> boatEntityType;

    public BoatItem(Class<? extends Boat> clazz, EntityType<? extends Boat> entityType, Item.Properties properties) {
        super(properties);
        this.boatEntityClass = clazz;
        this.boatEntityType = entityType;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        Vec3 vec3;
        ItemStack itemStack = player.m_21120_(interactionHand);
        BlockHitResult blockHitResult = BoatItem.m_41435_((Level)level, (Player)player, (ClipContext.Fluid)ClipContext.Fluid.ANY);
        if (blockHitResult.m_6662_() == HitResult.Type.MISS) {
            return InteractionResultHolder.m_19098_((Object)itemStack);
        }
        Vec3 vec32 = player.m_20252_(1.0f);
        List list = level.m_6249_((Entity)player, player.m_20191_().m_82369_(vec32.m_82490_(5.0)).m_82400_(1.0), RIDERS);
        if (!list.isEmpty()) {
            vec3 = player.m_146892_();
            for (Entity entity : list) {
                AABB aABB = entity.m_20191_().m_82400_((double)entity.m_6143_());
                if (!aABB.m_82390_(vec3)) continue;
                return InteractionResultHolder.m_19098_((Object)itemStack);
            }
        }
        if (blockHitResult.m_6662_() == HitResult.Type.BLOCK) {
            vec3 = this.createEntity(level, (HitResult)blockHitResult);
            vec3.m_146922_(player.m_146908_());
            if (!level.m_45756_((Entity)vec3, vec3.m_20191_())) {
                return InteractionResultHolder.m_19100_((Object)itemStack);
            }
            if (!level.f_46443_) {
                level.m_7967_((Entity)vec3);
                level.m_220400_((Entity)player, GameEvent.f_157810_, blockHitResult.m_82450_());
                if (!player.m_150110_().f_35937_) {
                    itemStack.m_41774_(1);
                }
            }
            player.m_36246_(Stats.f_12982_.m_12902_((Object)this));
            return InteractionResultHolder.m_19092_((Object)itemStack, (boolean)level.m_5776_());
        }
        return InteractionResultHolder.m_19098_((Object)itemStack);
    }

    private Boat createEntity(Level level, HitResult hitResult) {
        if (this.boatEntityClass == null) {
            IC2.log.error(LogCategory.General, "Boat entity class doesn't exist");
            return null;
        }
        try {
            Constructor<? extends Boat> constructor = this.boatEntityClass.getConstructor(EntityType.class, Level.class, Double.TYPE, Double.TYPE, Double.TYPE);
            return constructor.newInstance(this.boatEntityType, level, hitResult.m_82450_().f_82479_, hitResult.m_82450_().f_82480_, hitResult.m_82450_().f_82481_);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }
}

