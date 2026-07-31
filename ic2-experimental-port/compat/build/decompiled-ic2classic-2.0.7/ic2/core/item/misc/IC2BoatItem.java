/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.misc;

import ic2.core.IC2;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IC2BoatItem
extends IC2Item
implements ISimpleItemModel {
    private static final Predicate<Entity> PREDICATE = EntitySelector.f_20408_.and(Entity::m_6087_);
    String texture;
    BiFunction<Level, Vec3, Boat> creator;

    public IC2BoatItem(String name, BiFunction<Level, Vec3, Boat> creator) {
        this(name, name, creator);
    }

    public IC2BoatItem(String name, String texture, BiFunction<Level, Vec3, Boat> creator) {
        super(name, new PropertiesBuilder().group(IC2.IC2_MAIN_GROUP).maxStackSize(1));
        this.texture = texture;
        this.creator = creator;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), "boat").get(this.texture);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.m_21120_(handIn);
        BlockHitResult raytraceresult = IC2BoatItem.m_41435_((Level)worldIn, (Player)playerIn, (ClipContext.Fluid)ClipContext.Fluid.ANY);
        if (raytraceresult.m_6662_() == HitResult.Type.MISS) {
            return new InteractionResultHolder(InteractionResult.PASS, (Object)itemstack);
        }
        Vec3 Vector3d = playerIn.m_20252_(1.0f);
        List list = worldIn.m_6249_((Entity)playerIn, playerIn.m_20191_().m_82369_(Vector3d.m_82490_(5.0)).m_82400_(1.0), PREDICATE);
        if (!list.isEmpty()) {
            Vec3 vec3d1 = playerIn.m_20299_(1.0f);
            for (Entity entity : list) {
                AABB axisalignedbb = entity.m_20191_().m_82400_((double)entity.m_6143_());
                if (!axisalignedbb.m_82390_(vec3d1)) continue;
                return new InteractionResultHolder(InteractionResult.PASS, (Object)itemstack);
            }
        }
        if (raytraceresult.m_6662_() == HitResult.Type.BLOCK) {
            Boat boatentity = this.creator.apply(worldIn, raytraceresult.m_82450_());
            boatentity.m_146922_(playerIn.m_146908_());
            if (!worldIn.m_45756_((Entity)boatentity, boatentity.m_20191_().m_82400_(-0.1))) {
                return new InteractionResultHolder(InteractionResult.FAIL, (Object)itemstack);
            }
            if (!worldIn.f_46443_) {
                worldIn.m_7967_((Entity)boatentity);
            }
            if (!playerIn.m_7500_()) {
                itemstack.m_41774_(1);
            }
            playerIn.m_36246_(Stats.f_12982_.m_12902_((Object)this));
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemstack);
        }
        return new InteractionResultHolder(InteractionResult.PASS, (Object)itemstack);
    }
}

