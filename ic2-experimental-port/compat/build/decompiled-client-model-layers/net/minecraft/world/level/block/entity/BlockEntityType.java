/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.types.Type
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.block.entity;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;
import com.mojang.logging.LogUtils;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.entity.DaylightDetectorBlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

public class BlockEntityType<T extends BlockEntity> {
    private static final Logger f_58913_ = LogUtils.getLogger();
    public static final BlockEntityType<FurnaceBlockEntity> f_58917_ = BlockEntityType.m_58956_("furnace", Builder.m_155273_(FurnaceBlockEntity::new, Blocks.f_50094_));
    public static final BlockEntityType<ChestBlockEntity> f_58918_ = BlockEntityType.m_58956_("chest", Builder.m_155273_(ChestBlockEntity::new, Blocks.f_50087_));
    public static final BlockEntityType<TrappedChestBlockEntity> f_58919_ = BlockEntityType.m_58956_("trapped_chest", Builder.m_155273_(TrappedChestBlockEntity::new, Blocks.f_50325_));
    public static final BlockEntityType<EnderChestBlockEntity> f_58920_ = BlockEntityType.m_58956_("ender_chest", Builder.m_155273_(EnderChestBlockEntity::new, Blocks.f_50265_));
    public static final BlockEntityType<JukeboxBlockEntity> f_58921_ = BlockEntityType.m_58956_("jukebox", Builder.m_155273_(JukeboxBlockEntity::new, Blocks.f_50131_));
    public static final BlockEntityType<DispenserBlockEntity> f_58922_ = BlockEntityType.m_58956_("dispenser", Builder.m_155273_(DispenserBlockEntity::new, Blocks.f_50061_));
    public static final BlockEntityType<DropperBlockEntity> f_58923_ = BlockEntityType.m_58956_("dropper", Builder.m_155273_(DropperBlockEntity::new, Blocks.f_50286_));
    public static final BlockEntityType<SignBlockEntity> f_58924_ = BlockEntityType.m_58956_("sign", Builder.m_155273_(SignBlockEntity::new, Blocks.f_50095_, Blocks.f_50149_, Blocks.f_50150_, Blocks.f_50151_, Blocks.f_50152_, Blocks.f_50153_, Blocks.f_50158_, Blocks.f_50159_, Blocks.f_50160_, Blocks.f_50161_, Blocks.f_50162_, Blocks.f_50163_, Blocks.f_50673_, Blocks.f_50675_, Blocks.f_50674_, Blocks.f_50676_, Blocks.f_220841_, Blocks.f_220839_));
    public static final BlockEntityType<SpawnerBlockEntity> f_58925_ = BlockEntityType.m_58956_("mob_spawner", Builder.m_155273_(SpawnerBlockEntity::new, Blocks.f_50085_));
    public static final BlockEntityType<PistonMovingBlockEntity> f_58926_ = BlockEntityType.m_58956_("piston", Builder.m_155273_(PistonMovingBlockEntity::new, Blocks.f_50110_));
    public static final BlockEntityType<BrewingStandBlockEntity> f_58927_ = BlockEntityType.m_58956_("brewing_stand", Builder.m_155273_(BrewingStandBlockEntity::new, Blocks.f_50255_));
    public static final BlockEntityType<EnchantmentTableBlockEntity> f_58928_ = BlockEntityType.m_58956_("enchanting_table", Builder.m_155273_(EnchantmentTableBlockEntity::new, Blocks.f_50201_));
    public static final BlockEntityType<TheEndPortalBlockEntity> f_58929_ = BlockEntityType.m_58956_("end_portal", Builder.m_155273_(TheEndPortalBlockEntity::new, Blocks.f_50257_));
    public static final BlockEntityType<BeaconBlockEntity> f_58930_ = BlockEntityType.m_58956_("beacon", Builder.m_155273_(BeaconBlockEntity::new, Blocks.f_50273_));
    public static final BlockEntityType<SkullBlockEntity> f_58931_ = BlockEntityType.m_58956_("skull", Builder.m_155273_(SkullBlockEntity::new, Blocks.f_50310_, Blocks.f_50311_, Blocks.f_50318_, Blocks.f_50319_, Blocks.f_50320_, Blocks.f_50321_, Blocks.f_50314_, Blocks.f_50315_, Blocks.f_50312_, Blocks.f_50313_, Blocks.f_50316_, Blocks.f_50317_));
    public static final BlockEntityType<DaylightDetectorBlockEntity> f_58932_ = BlockEntityType.m_58956_("daylight_detector", Builder.m_155273_(DaylightDetectorBlockEntity::new, Blocks.f_50329_));
    public static final BlockEntityType<HopperBlockEntity> f_58933_ = BlockEntityType.m_58956_("hopper", Builder.m_155273_(HopperBlockEntity::new, Blocks.f_50332_));
    public static final BlockEntityType<ComparatorBlockEntity> f_58934_ = BlockEntityType.m_58956_("comparator", Builder.m_155273_(ComparatorBlockEntity::new, Blocks.f_50328_));
    public static final BlockEntityType<BannerBlockEntity> f_58935_ = BlockEntityType.m_58956_("banner", Builder.m_155273_(BannerBlockEntity::new, Blocks.f_50414_, Blocks.f_50415_, Blocks.f_50416_, Blocks.f_50417_, Blocks.f_50418_, Blocks.f_50419_, Blocks.f_50420_, Blocks.f_50421_, Blocks.f_50422_, Blocks.f_50423_, Blocks.f_50424_, Blocks.f_50425_, Blocks.f_50426_, Blocks.f_50427_, Blocks.f_50428_, Blocks.f_50429_, Blocks.f_50430_, Blocks.f_50431_, Blocks.f_50432_, Blocks.f_50433_, Blocks.f_50434_, Blocks.f_50435_, Blocks.f_50436_, Blocks.f_50437_, Blocks.f_50438_, Blocks.f_50439_, Blocks.f_50388_, Blocks.f_50389_, Blocks.f_50390_, Blocks.f_50391_, Blocks.f_50392_, Blocks.f_50393_));
    public static final BlockEntityType<StructureBlockEntity> f_58936_ = BlockEntityType.m_58956_("structure_block", Builder.m_155273_(StructureBlockEntity::new, Blocks.f_50677_));
    public static final BlockEntityType<TheEndGatewayBlockEntity> f_58937_ = BlockEntityType.m_58956_("end_gateway", Builder.m_155273_(TheEndGatewayBlockEntity::new, Blocks.f_50446_));
    public static final BlockEntityType<CommandBlockEntity> f_58938_ = BlockEntityType.m_58956_("command_block", Builder.m_155273_(CommandBlockEntity::new, Blocks.f_50272_, Blocks.f_50448_, Blocks.f_50447_));
    public static final BlockEntityType<ShulkerBoxBlockEntity> f_58939_ = BlockEntityType.m_58956_("shulker_box", Builder.m_155273_(ShulkerBoxBlockEntity::new, Blocks.f_50456_, Blocks.f_50525_, Blocks.f_50521_, Blocks.f_50522_, Blocks.f_50466_, Blocks.f_50464_, Blocks.f_50523_, Blocks.f_50460_, Blocks.f_50465_, Blocks.f_50462_, Blocks.f_50459_, Blocks.f_50458_, Blocks.f_50463_, Blocks.f_50520_, Blocks.f_50524_, Blocks.f_50457_, Blocks.f_50461_));
    public static final BlockEntityType<BedBlockEntity> f_58940_ = BlockEntityType.m_58956_("bed", Builder.m_155273_(BedBlockEntity::new, Blocks.f_50028_, Blocks.f_50029_, Blocks.f_50025_, Blocks.f_50026_, Blocks.f_50023_, Blocks.f_50021_, Blocks.f_50027_, Blocks.f_50017_, Blocks.f_50022_, Blocks.f_50019_, Blocks.f_50068_, Blocks.f_50067_, Blocks.f_50020_, Blocks.f_50024_, Blocks.f_50066_, Blocks.f_50018_));
    public static final BlockEntityType<ConduitBlockEntity> f_58941_ = BlockEntityType.m_58956_("conduit", Builder.m_155273_(ConduitBlockEntity::new, Blocks.f_50569_));
    public static final BlockEntityType<BarrelBlockEntity> f_58942_ = BlockEntityType.m_58956_("barrel", Builder.m_155273_(BarrelBlockEntity::new, Blocks.f_50618_));
    public static final BlockEntityType<SmokerBlockEntity> f_58906_ = BlockEntityType.m_58956_("smoker", Builder.m_155273_(SmokerBlockEntity::new, Blocks.f_50619_));
    public static final BlockEntityType<BlastFurnaceBlockEntity> f_58907_ = BlockEntityType.m_58956_("blast_furnace", Builder.m_155273_(BlastFurnaceBlockEntity::new, Blocks.f_50620_));
    public static final BlockEntityType<LecternBlockEntity> f_58908_ = BlockEntityType.m_58956_("lectern", Builder.m_155273_(LecternBlockEntity::new, Blocks.f_50624_));
    public static final BlockEntityType<BellBlockEntity> f_58909_ = BlockEntityType.m_58956_("bell", Builder.m_155273_(BellBlockEntity::new, Blocks.f_50680_));
    public static final BlockEntityType<JigsawBlockEntity> f_58910_ = BlockEntityType.m_58956_("jigsaw", Builder.m_155273_(JigsawBlockEntity::new, Blocks.f_50678_));
    public static final BlockEntityType<CampfireBlockEntity> f_58911_ = BlockEntityType.m_58956_("campfire", Builder.m_155273_(CampfireBlockEntity::new, Blocks.f_50683_, Blocks.f_50684_));
    public static final BlockEntityType<BeehiveBlockEntity> f_58912_ = BlockEntityType.m_58956_("beehive", Builder.m_155273_(BeehiveBlockEntity::new, Blocks.f_50717_, Blocks.f_50718_));
    public static final BlockEntityType<SculkSensorBlockEntity> f_155257_ = BlockEntityType.m_58956_("sculk_sensor", Builder.m_155273_(SculkSensorBlockEntity::new, Blocks.f_152500_));
    public static final BlockEntityType<SculkCatalystBlockEntity> f_222758_ = BlockEntityType.m_58956_("sculk_catalyst", Builder.m_155273_(SculkCatalystBlockEntity::new, Blocks.f_220857_));
    public static final BlockEntityType<SculkShriekerBlockEntity> f_222759_ = BlockEntityType.m_58956_("sculk_shrieker", Builder.m_155273_(SculkShriekerBlockEntity::new, Blocks.f_220858_));
    private final BlockEntitySupplier<? extends T> f_58914_;
    private final Set<Block> f_58915_;
    private final Type<?> f_58916_;

    @Nullable
    public static ResourceLocation m_58954_(BlockEntityType<?> p_58955_) {
        return Registry.f_122830_.m_7981_(p_58955_);
    }

    private static <T extends BlockEntity> BlockEntityType<T> m_58956_(String p_58957_, Builder<T> p_58958_) {
        if (p_58958_.f_58960_.isEmpty()) {
            f_58913_.warn("Block entity type {} requires at least one valid block to be defined!", (Object)p_58957_);
        }
        Type<?> $$2 = Util.m_137456_(References.f_16781_, p_58957_);
        return Registry.m_122961_(Registry.f_122830_, p_58957_, p_58958_.m_58966_($$2));
    }

    public BlockEntityType(BlockEntitySupplier<? extends T> p_155259_, Set<Block> p_155260_, Type<?> p_155261_) {
        this.f_58914_ = p_155259_;
        this.f_58915_ = p_155260_;
        this.f_58916_ = p_155261_;
    }

    @Nullable
    public T m_155264_(BlockPos p_155265_, BlockState p_155266_) {
        return this.f_58914_.m_155267_(p_155265_, p_155266_);
    }

    public boolean m_155262_(BlockState p_155263_) {
        return this.f_58915_.contains(p_155263_.m_60734_());
    }

    @Nullable
    public T m_58949_(BlockGetter p_58950_, BlockPos p_58951_) {
        BlockEntity $$2 = p_58950_.m_7702_(p_58951_);
        if ($$2 == null || $$2.m_58903_() != this) {
            return null;
        }
        return (T)$$2;
    }

    public static final class Builder<T extends BlockEntity> {
        private final BlockEntitySupplier<? extends T> f_58959_;
        final Set<Block> f_58960_;

        private Builder(BlockEntitySupplier<? extends T> p_155271_, Set<Block> p_155272_) {
            this.f_58959_ = p_155271_;
            this.f_58960_ = p_155272_;
        }

        public static <T extends BlockEntity> Builder<T> m_155273_(BlockEntitySupplier<? extends T> p_155274_, Block ... p_155275_) {
            return new Builder<T>(p_155274_, (Set<Block>)ImmutableSet.copyOf((Object[])p_155275_));
        }

        public BlockEntityType<T> m_58966_(Type<?> p_58967_) {
            return new BlockEntityType<T>(this.f_58959_, this.f_58960_, p_58967_);
        }
    }

    @FunctionalInterface
    static interface BlockEntitySupplier<T extends BlockEntity> {
        public T m_155267_(BlockPos var1, BlockState var2);
    }
}

