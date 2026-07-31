/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package ic2.core.item;

import ic2.core.block.state.IIdProvider;
import ic2.core.item.EntityBoatCarbon;
import ic2.core.item.EntityBoatElectric;
import ic2.core.item.EntityBoatRubber;
import ic2.core.item.EntityIC2Boat;
import ic2.core.item.ItemMulti;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.util.Vector3;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemIC2Boat
extends ItemMulti<BoatType> {
    public ItemIC2Boat() {
        super(ItemName.boat, BoatType.class);
    }

    @Override
    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = StackUtil.get(player, hand);
        EntityIC2Boat boat = this.makeBoat(stack, world, player);
        if (boat == null) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        Vector3 lookVec = Util.getLookScaled((Entity)player);
        Vector3 start = Util.getEyePosition((Entity)player);
        Vec3d startMc = start.toVec3();
        RayTraceResult hitPos = world.func_72901_a(startMc, start.add(lookVec).toVec3(), true);
        if (hitPos == null) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        boolean inEntity = false;
        float border = 1.0f;
        List list = world.func_72839_b((Entity)player, player.func_174813_aQ().func_72321_a(lookVec.x, lookVec.y, lookVec.z).func_186662_g((double)border));
        for (Entity entity : list) {
            if (!entity.func_70067_L()) continue;
            border = entity.func_70111_Y();
            AxisAlignedBB aabb = entity.func_174813_aQ().func_186662_g((double)border);
            if (!aabb.func_72318_a(startMc)) continue;
            inEntity = true;
            break;
        }
        if (inEntity) {
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
        if (hitPos.field_72313_a == RayTraceResult.Type.BLOCK) {
            BlockPos pos = hitPos.func_178782_a();
            if (world.func_180495_p(pos).func_177230_c() == Blocks.field_150431_aC) {
                pos = pos.func_177977_b();
            }
            boat.func_70107_b((double)pos.func_177958_n() + 0.5, pos.func_177956_o() + 1, (double)pos.func_177952_p() + 0.5);
            boat.field_70177_z = ((MathHelper.func_76128_c((double)((double)(player.field_70177_z * 4.0f / 360.0f) + 0.5)) & 3) - 1) * 90;
            if (!world.func_184144_a((Entity)boat, boat.func_70046_E().func_72321_a(-0.1, -0.1, -0.1)).isEmpty()) {
                return new ActionResult(EnumActionResult.PASS, (Object)stack);
            }
            if (!world.field_72995_K) {
                world.func_72838_d((Entity)boat);
            }
            if (!player.field_71075_bZ.field_75098_d) {
                stack = StackUtil.decSize(stack);
            }
        }
        return new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
    }

    protected EntityIC2Boat makeBoat(ItemStack stack, World world, EntityPlayer player) {
        BoatType type = (BoatType)this.getType(stack);
        if (type == null) {
            return null;
        }
        switch (type) {
            case carbon: {
                return new EntityBoatCarbon(world);
            }
            case rubber: {
                return new EntityBoatRubber(world);
            }
            case electric: {
                return new EntityBoatElectric(world);
            }
        }
        return null;
    }

    public boolean hasCustomEntity(ItemStack stack) {
        return this.getType(stack) == BoatType.electric;
    }

    public Entity createEntity(World world, Entity location, ItemStack stack) {
        assert (this.hasCustomEntity(stack));
        assert (!world.field_72995_K);
        FireproofItem item = new FireproofItem(world, location.field_70165_t, location.field_70163_u, location.field_70161_v, stack);
        item.func_174869_p();
        item.field_70159_w = location.field_70159_w;
        item.field_70181_x = location.field_70181_x;
        item.field_70179_y = location.field_70179_y;
        return item;
    }

    public static class FireproofItem
    extends EntityItem {
        public FireproofItem(World world, double x, double y, double z, ItemStack stack) {
            super(world, x, y, z, stack);
            this.field_70178_ae = true;
        }

        public FireproofItem(World world, double x, double y, double z) {
            super(world, x, y, z);
            this.field_70178_ae = true;
        }

        public FireproofItem(World world) {
            super(world);
            this.field_70178_ae = true;
        }

        public void func_70071_h_() {
            super.func_70071_h_();
            this.func_70066_B();
        }

        protected void func_70081_e(int amount) {
        }

        public void func_70015_d(int seconds) {
            this.func_70066_B();
        }
    }

    public static enum BoatType implements IIdProvider
    {
        broken_rubber,
        rubber,
        carbon,
        electric;


        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public int getId() {
            return this.ordinal();
        }
    }
}

