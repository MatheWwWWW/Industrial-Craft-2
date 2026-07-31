/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 */
package net.minecraft.gametest.framework;

import com.mojang.authlib.GameProfile;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestAssertPosException;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.GameTestSequence;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class GameTestHelper {
    private final GameTestInfo f_127595_;
    private boolean f_177099_;

    public GameTestHelper(GameTestInfo p_127597_) {
        this.f_127595_ = p_127597_;
    }

    public ServerLevel m_177100_() {
        return this.f_127595_.m_127637_();
    }

    public BlockState m_177232_(BlockPos p_177233_) {
        return this.m_177100_().m_8055_(this.m_177449_(p_177233_));
    }

    @Nullable
    public BlockEntity m_177347_(BlockPos p_177348_) {
        return this.m_177100_().m_7702_(this.m_177449_(p_177348_));
    }

    public void m_177301_() {
        AABB $$0 = this.m_177448_();
        List<Entity> $$1 = this.m_177100_().m_6443_(Entity.class, $$0.m_82400_(1.0), p_177131_ -> !(p_177131_ instanceof Player));
        $$1.forEach(Entity::m_6074_);
    }

    public ItemEntity m_177189_(Item p_177190_, float p_177191_, float p_177192_, float p_177193_) {
        ServerLevel $$4 = this.m_177100_();
        Vec3 $$5 = this.m_177227_(new Vec3(p_177191_, p_177192_, p_177193_));
        ItemEntity $$6 = new ItemEntity($$4, $$5.f_82479_, $$5.f_82480_, $$5.f_82481_, new ItemStack(p_177190_, 1));
        $$6.m_20334_(0.0, 0.0, 0.0);
        $$4.m_7967_($$6);
        return $$6;
    }

    public <E extends Entity> E m_177176_(EntityType<E> p_177177_, BlockPos p_177178_) {
        return this.m_177173_(p_177177_, Vec3.m_82539_(p_177178_));
    }

    public <E extends Entity> E m_177173_(EntityType<E> p_177174_, Vec3 p_177175_) {
        ServerLevel $$2 = this.m_177100_();
        E $$3 = p_177174_.m_20615_($$2);
        if ($$3 instanceof Mob) {
            ((Mob)$$3).m_21530_();
        }
        Vec3 $$4 = this.m_177227_(p_177175_);
        ((Entity)$$3).m_7678_($$4.f_82479_, $$4.f_82480_, $$4.f_82481_, ((Entity)$$3).m_146908_(), ((Entity)$$3).m_146909_());
        $$2.m_7967_((Entity)$$3);
        return $$3;
    }

    public <E extends Entity> E m_177168_(EntityType<E> p_177169_, int p_177170_, int p_177171_, int p_177172_) {
        return this.m_177176_(p_177169_, new BlockPos(p_177170_, p_177171_, p_177172_));
    }

    public <E extends Entity> E m_177163_(EntityType<E> p_177164_, float p_177165_, float p_177166_, float p_177167_) {
        return this.m_177173_(p_177164_, new Vec3(p_177165_, p_177166_, p_177167_));
    }

    public <E extends Mob> E m_177329_(EntityType<E> p_177330_, BlockPos p_177331_) {
        Mob $$2 = (Mob)this.m_177176_(p_177330_, p_177331_);
        $$2.m_147272_();
        return (E)$$2;
    }

    public <E extends Mob> E m_177321_(EntityType<E> p_177322_, int p_177323_, int p_177324_, int p_177325_) {
        return this.m_177329_(p_177322_, new BlockPos(p_177323_, p_177324_, p_177325_));
    }

    public <E extends Mob> E m_177326_(EntityType<E> p_177327_, Vec3 p_177328_) {
        Mob $$2 = (Mob)this.m_177173_(p_177327_, p_177328_);
        $$2.m_147272_();
        return (E)$$2;
    }

    public <E extends Mob> E m_177316_(EntityType<E> p_177317_, float p_177318_, float p_177319_, float p_177320_) {
        return this.m_177326_(p_177317_, new Vec3(p_177318_, p_177319_, p_177320_));
    }

    public GameTestSequence m_177185_(Mob p_177186_, BlockPos p_177187_, float p_177188_) {
        return this.m_177425_().m_177546_(2, () -> {
            Path $$3 = p_177186_.m_21573_().m_7864_(this.m_177449_(p_177187_), 0);
            p_177186_.m_21573_().m_26536_($$3, p_177188_);
        });
    }

    public void m_177103_(int p_177104_, int p_177105_, int p_177106_) {
        this.m_177385_(new BlockPos(p_177104_, p_177105_, p_177106_));
    }

    public void m_177385_(BlockPos p_177386_) {
        this.m_177357_(p_177386_, p_177212_ -> p_177212_.m_204336_(BlockTags.f_13093_), () -> "Expected button");
        BlockPos $$1 = this.m_177449_(p_177386_);
        BlockState $$2 = this.m_177100_().m_8055_($$1);
        ButtonBlock $$3 = (ButtonBlock)$$2.m_60734_();
        $$3.m_51116_($$2, this.m_177100_(), $$1);
    }

    public void m_177408_(BlockPos p_177409_) {
        BlockPos $$1 = this.m_177449_(p_177409_);
        BlockState $$2 = this.m_177100_().m_8055_($$1);
        $$2.m_60664_(this.m_177100_(), this.m_177368_(), InteractionHand.MAIN_HAND, new BlockHitResult(Vec3.m_82512_($$1), Direction.NORTH, $$1, true));
    }

    public LivingEntity m_177183_(LivingEntity p_177184_) {
        p_177184_.m_20301_(0);
        p_177184_.m_21153_(0.25f);
        return p_177184_;
    }

    public Player m_177368_() {
        return new Player(this.m_177100_(), BlockPos.f_121853_, 0.0f, new GameProfile(UUID.randomUUID(), "test-mock-player"), null){

            @Override
            public boolean m_5833_() {
                return false;
            }

            @Override
            public boolean m_7500_() {
                return true;
            }
        };
    }

    public void m_177302_(int p_177303_, int p_177304_, int p_177305_) {
        this.m_177421_(new BlockPos(p_177303_, p_177304_, p_177305_));
    }

    public void m_177421_(BlockPos p_177422_) {
        this.m_177208_(Blocks.f_50164_, p_177422_);
        BlockPos $$1 = this.m_177449_(p_177422_);
        BlockState $$2 = this.m_177100_().m_8055_($$1);
        LeverBlock $$3 = (LeverBlock)$$2.m_60734_();
        $$3.m_54676_($$2, this.m_177100_(), $$1);
    }

    public void m_177234_(BlockPos p_177235_, long p_177236_) {
        this.m_177245_(p_177235_, Blocks.f_50330_);
        this.m_177306_(p_177236_, () -> this.m_177245_(p_177235_, Blocks.f_50016_));
    }

    public void m_177434_(BlockPos p_177435_) {
        this.m_177100_().m_46953_(this.m_177449_(p_177435_), false, null);
    }

    public void m_177107_(int p_177108_, int p_177109_, int p_177110_, Block p_177111_) {
        this.m_177245_(new BlockPos(p_177108_, p_177109_, p_177110_), p_177111_);
    }

    public void m_177112_(int p_177113_, int p_177114_, int p_177115_, BlockState p_177116_) {
        this.m_177252_(new BlockPos(p_177113_, p_177114_, p_177115_), p_177116_);
    }

    public void m_177245_(BlockPos p_177246_, Block p_177247_) {
        this.m_177252_(p_177246_, p_177247_.m_49966_());
    }

    public void m_177252_(BlockPos p_177253_, BlockState p_177254_) {
        this.m_177100_().m_7731_(this.m_177449_(p_177253_), p_177254_, 3);
    }

    public void m_177396_() {
        this.m_177101_(13000);
    }

    public void m_177101_(int p_177102_) {
        this.m_177100_().m_8615_(p_177102_);
    }

    public void m_177203_(Block p_177204_, int p_177205_, int p_177206_, int p_177207_) {
        this.m_177208_(p_177204_, new BlockPos(p_177205_, p_177206_, p_177207_));
    }

    public void m_177208_(Block p_177209_, BlockPos p_177210_) {
        BlockState $$2 = this.m_177232_(p_177210_);
        this.m_177271_(p_177210_, p_177216_ -> $$2.m_60713_(p_177209_), "Expected " + p_177209_.m_49954_().getString() + ", got " + $$2.m_60734_().m_49954_().getString());
    }

    public void m_177336_(Block p_177337_, int p_177338_, int p_177339_, int p_177340_) {
        this.m_177341_(p_177337_, new BlockPos(p_177338_, p_177339_, p_177340_));
    }

    public void m_177341_(Block p_177342_, BlockPos p_177343_) {
        this.m_177271_(p_177343_, p_177251_ -> !this.m_177232_(p_177343_).m_60713_(p_177342_), "Did not expect " + p_177342_.m_49954_().getString());
    }

    public void m_177377_(Block p_177378_, int p_177379_, int p_177380_, int p_177381_) {
        this.m_177382_(p_177378_, new BlockPos(p_177379_, p_177380_, p_177381_));
    }

    public void m_177382_(Block p_177383_, BlockPos p_177384_) {
        this.m_177361_(() -> this.m_177208_(p_177383_, p_177384_));
    }

    public void m_177271_(BlockPos p_177272_, Predicate<Block> p_177273_, String p_177274_) {
        this.m_177275_(p_177272_, p_177273_, () -> p_177274_);
    }

    public void m_177275_(BlockPos p_177276_, Predicate<Block> p_177277_, Supplier<String> p_177278_) {
        this.m_177357_(p_177276_, p_177296_ -> p_177277_.test(p_177296_.m_60734_()), p_177278_);
    }

    public <T extends Comparable<T>> void m_177255_(BlockPos p_177256_, Property<T> p_177257_, T p_177258_) {
        this.m_177357_(p_177256_, p_177223_ -> p_177223_.m_61138_(p_177257_) && p_177223_.m_61143_(p_177257_).equals(p_177258_), () -> "Expected property " + p_177257_.m_61708_() + " to be " + p_177258_);
    }

    public <T extends Comparable<T>> void m_177259_(BlockPos p_177260_, Property<T> p_177261_, Predicate<T> p_177262_, String p_177263_) {
        this.m_177357_(p_177260_, p_177300_ -> p_177262_.test(p_177300_.m_61143_(p_177261_)), () -> p_177263_);
    }

    public void m_177357_(BlockPos p_177358_, Predicate<BlockState> p_177359_, Supplier<String> p_177360_) {
        BlockState $$3 = this.m_177232_(p_177358_);
        if (!p_177359_.test($$3)) {
            throw new GameTestAssertPosException(p_177360_.get(), this.m_177449_(p_177358_), p_177358_, this.f_127595_.m_177488_());
        }
    }

    public void m_177156_(EntityType<?> p_177157_) {
        List<Entity> $$1 = this.m_177100_().m_142425_(p_177157_, this.m_177448_(), Entity::m_6084_);
        if ($$1.isEmpty()) {
            throw new GameTestAssertException("Expected " + p_177157_.m_147048_() + " to exist");
        }
    }

    public void m_177369_(EntityType<?> p_177370_, int p_177371_, int p_177372_, int p_177373_) {
        this.m_177374_(p_177370_, new BlockPos(p_177371_, p_177372_, p_177373_));
    }

    public void m_177374_(EntityType<?> p_177375_, BlockPos p_177376_) {
        BlockPos $$2 = this.m_177449_(p_177376_);
        List<Entity> $$3 = this.m_177100_().m_142425_(p_177375_, new AABB($$2), Entity::m_6084_);
        if ($$3.isEmpty()) {
            throw new GameTestAssertPosException("Expected " + p_177375_.m_147048_(), $$2, p_177376_, this.f_127595_.m_177488_());
        }
    }

    public void m_239371_(EntityType<?> p_239372_, BlockPos p_239373_, int p_239374_, double p_239375_) {
        BlockPos $$4 = this.m_177449_(p_239373_);
        List<?> $$5 = this.m_238399_(p_239372_, p_239373_, p_239375_);
        if ($$5.size() != p_239374_) {
            throw new GameTestAssertPosException("Expected " + p_239374_ + " entities of type " + p_239372_.m_147048_() + ", actual number of entities found=" + $$5.size(), $$4, p_239373_, this.f_127595_.m_177488_());
        }
    }

    public void m_177179_(EntityType<?> p_177180_, BlockPos p_177181_, double p_177182_) {
        List<?> $$3 = this.m_238399_(p_177180_, p_177181_, p_177182_);
        if ($$3.isEmpty()) {
            BlockPos $$4 = this.m_177449_(p_177181_);
            throw new GameTestAssertPosException("Expected " + p_177180_.m_147048_(), $$4, p_177181_, this.f_127595_.m_177488_());
        }
    }

    public <T extends Entity> List<T> m_238399_(EntityType<T> p_238400_, BlockPos p_238401_, double p_238402_) {
        BlockPos $$3 = this.m_177449_(p_238401_);
        return this.m_177100_().m_142425_(p_238400_, new AABB($$3).m_82400_(p_238402_), Entity::m_6084_);
    }

    public void m_177132_(Entity p_177133_, int p_177134_, int p_177135_, int p_177136_) {
        this.m_177140_(p_177133_, new BlockPos(p_177134_, p_177135_, p_177136_));
    }

    public void m_177140_(Entity p_177141_, BlockPos p_177142_) {
        BlockPos $$2 = this.m_177449_(p_177142_);
        List<Entity> $$3 = this.m_177100_().m_142425_(p_177141_.m_6095_(), new AABB($$2), Entity::m_6084_);
        $$3.stream().filter(p_177139_ -> p_177139_ == p_177141_).findFirst().orElseThrow(() -> new GameTestAssertPosException("Expected " + p_177141_.m_6095_().m_147048_(), $$2, p_177142_, this.f_127595_.m_177488_()));
    }

    public void m_177198_(Item p_177199_, BlockPos p_177200_, double p_177201_, int p_177202_) {
        BlockPos $$4 = this.m_177449_(p_177200_);
        List<ItemEntity> $$5 = this.m_177100_().m_142425_(EntityType.f_20461_, new AABB($$4).m_82400_(p_177201_), Entity::m_6084_);
        int $$6 = 0;
        for (Entity entity : $$5) {
            ItemEntity $$8 = (ItemEntity)entity;
            if (!$$8.m_32055_().m_41720_().equals(p_177199_)) continue;
            $$6 += $$8.m_32055_().m_41613_();
        }
        if ($$6 != p_177202_) {
            throw new GameTestAssertPosException("Expected " + p_177202_ + " " + p_177199_.m_41466_().getString() + " items to exist (found " + $$6 + ")", $$4, p_177200_, this.f_127595_.m_177488_());
        }
    }

    public void m_177194_(Item p_177195_, BlockPos p_177196_, double p_177197_) {
        BlockPos $$3 = this.m_177449_(p_177196_);
        List<ItemEntity> $$4 = this.m_177100_().m_142425_(EntityType.f_20461_, new AABB($$3).m_82400_(p_177197_), Entity::m_6084_);
        for (Entity entity : $$4) {
            ItemEntity $$6 = (ItemEntity)entity;
            if (!$$6.m_32055_().m_41720_().equals(p_177195_)) continue;
            return;
        }
        throw new GameTestAssertPosException("Expected " + p_177195_.m_41466_().getString() + " item", $$3, p_177196_, this.f_127595_.m_177488_());
    }

    public void m_236778_(Item p_236779_, BlockPos p_236780_, double p_236781_) {
        BlockPos $$3 = this.m_177449_(p_236780_);
        List<ItemEntity> $$4 = this.m_177100_().m_142425_(EntityType.f_20461_, new AABB($$3).m_82400_(p_236781_), Entity::m_6084_);
        for (Entity entity : $$4) {
            ItemEntity $$6 = (ItemEntity)entity;
            if (!$$6.m_32055_().m_41720_().equals(p_236779_)) continue;
            throw new GameTestAssertPosException("Did not expect " + p_236779_.m_41466_().getString() + " item", $$3, p_236780_, this.f_127595_.m_177488_());
        }
    }

    public void m_177309_(EntityType<?> p_177310_) {
        List<Entity> $$1 = this.m_177100_().m_142425_(p_177310_, this.m_177448_(), Entity::m_6084_);
        if (!$$1.isEmpty()) {
            throw new GameTestAssertException("Did not expect " + p_177310_.m_147048_() + " to exist");
        }
    }

    public void m_177397_(EntityType<?> p_177398_, int p_177399_, int p_177400_, int p_177401_) {
        this.m_177402_(p_177398_, new BlockPos(p_177399_, p_177400_, p_177401_));
    }

    public void m_177402_(EntityType<?> p_177403_, BlockPos p_177404_) {
        BlockPos $$2 = this.m_177449_(p_177404_);
        List<Entity> $$3 = this.m_177100_().m_142425_(p_177403_, new AABB($$2), Entity::m_6084_);
        if (!$$3.isEmpty()) {
            throw new GameTestAssertPosException("Did not expect " + p_177403_.m_147048_(), $$2, p_177404_, this.f_127595_.m_177488_());
        }
    }

    public void m_177158_(EntityType<?> p_177159_, double p_177160_, double p_177161_, double p_177162_) {
        Vec3 $$4 = new Vec3(p_177160_, p_177161_, p_177162_);
        Vec3 $$5 = this.m_177227_($$4);
        Predicate<Entity> $$6 = p_177346_ -> p_177346_.m_20191_().m_82335_($$5, $$5);
        List<Entity> $$7 = this.m_177100_().m_142425_(p_177159_, this.m_177448_(), $$6);
        if ($$7.isEmpty()) {
            throw new GameTestAssertException("Expected " + p_177159_.m_147048_() + " to touch " + $$5 + " (relative " + $$4 + ")");
        }
    }

    public void m_177311_(EntityType<?> p_177312_, double p_177313_, double p_177314_, double p_177315_) {
        Vec3 $$4 = new Vec3(p_177313_, p_177314_, p_177315_);
        Vec3 $$5 = this.m_177227_($$4);
        Predicate<Entity> $$6 = p_177231_ -> !p_177231_.m_20191_().m_82335_($$5, $$5);
        List<Entity> $$7 = this.m_177100_().m_142425_(p_177312_, this.m_177448_(), $$6);
        if ($$7.isEmpty()) {
            throw new GameTestAssertException("Did not expect " + p_177312_.m_147048_() + " to touch " + $$5 + " (relative " + $$4 + ")");
        }
    }

    public <E extends Entity, T> void m_177237_(BlockPos p_177238_, EntityType<E> p_177239_, Function<? super E, T> p_177240_, @Nullable T p_177241_) {
        BlockPos $$4 = this.m_177449_(p_177238_);
        List<Entity> $$5 = this.m_177100_().m_142425_(p_177239_, new AABB($$4), Entity::m_6084_);
        if ($$5.isEmpty()) {
            throw new GameTestAssertPosException("Expected " + p_177239_.m_147048_(), $$4, p_177238_, this.f_127595_.m_177488_());
        }
        for (Entity $$6 : $$5) {
            T $$7 = p_177240_.apply($$6);
            if (!($$7 == null ? p_177241_ != null : !$$7.equals(p_177241_))) continue;
            throw new GameTestAssertException("Expected entity data to be: " + p_177241_ + ", but was: " + $$7);
        }
    }

    public void m_177440_(BlockPos p_177441_) {
        BlockPos $$1 = this.m_177449_(p_177441_);
        BlockEntity $$2 = this.m_177100_().m_7702_($$1);
        if ($$2 instanceof BaseContainerBlockEntity && !((BaseContainerBlockEntity)$$2).m_7983_()) {
            throw new GameTestAssertException("Container should be empty");
        }
    }

    public void m_177242_(BlockPos p_177243_, Item p_177244_) {
        BlockPos $$2 = this.m_177449_(p_177243_);
        BlockEntity $$3 = this.m_177100_().m_7702_($$2);
        if (!($$3 instanceof BaseContainerBlockEntity)) {
            throw new GameTestAssertException("Expected a container at " + p_177243_ + ", found " + Registry.f_122830_.m_7981_($$3.m_58903_()));
        }
        if (((BaseContainerBlockEntity)$$3).m_18947_(p_177244_) != 1) {
            throw new GameTestAssertException("Container should contain: " + p_177244_);
        }
    }

    public void m_177224_(BoundingBox p_177225_, BlockPos p_177226_) {
        BlockPos.m_121919_(p_177225_).forEach(p_177267_ -> {
            BlockPos $$3 = p_177226_.m_7918_(p_177267_.m_123341_() - p_177225_.m_162395_(), p_177267_.m_123342_() - p_177225_.m_162396_(), p_177267_.m_123343_() - p_177225_.m_162398_());
            this.m_177268_((BlockPos)p_177267_, $$3);
        });
    }

    public void m_177268_(BlockPos p_177269_, BlockPos p_177270_) {
        BlockState $$3;
        BlockState $$2 = this.m_177232_(p_177269_);
        if ($$2 != ($$3 = this.m_177232_(p_177270_))) {
            this.m_177289_("Incorrect state. Expected " + $$3 + ", got " + $$2, p_177269_);
        }
    }

    public void m_177123_(long p_177124_, BlockPos p_177125_, Item p_177126_) {
        this.m_177127_(p_177124_, () -> this.m_177242_(p_177125_, p_177126_));
    }

    public void m_177120_(long p_177121_, BlockPos p_177122_) {
        this.m_177127_(p_177121_, () -> this.m_177440_(p_177122_));
    }

    public <E extends Entity, T> void m_177349_(BlockPos p_177350_, EntityType<E> p_177351_, Function<E, T> p_177352_, T p_177353_) {
        this.m_177361_(() -> this.m_177237_(p_177350_, p_177351_, p_177352_, p_177353_));
    }

    public <E extends Entity> void m_177152_(E p_177153_, Predicate<E> p_177154_, String p_177155_) {
        if (!p_177154_.test(p_177153_)) {
            throw new GameTestAssertException("Entity " + p_177153_ + " failed " + p_177155_ + " test");
        }
    }

    public <E extends Entity, T> void m_177147_(E p_177148_, Function<E, T> p_177149_, String p_177150_, T p_177151_) {
        T $$4 = p_177149_.apply(p_177148_);
        if (!$$4.equals(p_177151_)) {
            throw new GameTestAssertException("Entity " + p_177148_ + " value " + p_177150_ + "=" + $$4 + " is not equal to expected " + p_177151_);
        }
    }

    public void m_177413_(EntityType<?> p_177414_, int p_177415_, int p_177416_, int p_177417_) {
        this.m_177418_(p_177414_, new BlockPos(p_177415_, p_177416_, p_177417_));
    }

    public void m_177418_(EntityType<?> p_177419_, BlockPos p_177420_) {
        this.m_177361_(() -> this.m_177374_(p_177419_, p_177420_));
    }

    public void m_177426_(EntityType<?> p_177427_, int p_177428_, int p_177429_, int p_177430_) {
        this.m_177431_(p_177427_, new BlockPos(p_177428_, p_177429_, p_177430_));
    }

    public void m_177431_(EntityType<?> p_177432_, BlockPos p_177433_) {
        this.m_177361_(() -> this.m_177402_(p_177432_, p_177433_));
    }

    public void m_177412_() {
        this.f_127595_.m_177486_();
    }

    private void m_177442_() {
        if (this.f_177099_) {
            throw new IllegalStateException("This test already has final clause");
        }
        this.f_177099_ = true;
    }

    public void m_177279_(Runnable p_177280_) {
        this.m_177442_();
        this.f_127595_.m_177489_().m_177549_(0L, p_177280_).m_177543_();
    }

    public void m_177361_(Runnable p_177362_) {
        this.m_177442_();
        this.f_127595_.m_177489_().m_177552_(p_177362_).m_177543_();
    }

    public void m_177117_(int p_177118_, Runnable p_177119_) {
        this.m_177442_();
        this.f_127595_.m_177489_().m_177549_(p_177118_, p_177119_).m_177543_();
    }

    public void m_177127_(long p_177128_, Runnable p_177129_) {
        this.f_127595_.m_177472_(p_177128_, p_177129_);
    }

    public void m_177306_(long p_177307_, Runnable p_177308_) {
        this.m_177127_(this.f_127595_.m_177488_() + p_177307_, p_177308_);
    }

    public void m_177446_(BlockPos p_177447_) {
        BlockPos $$1 = this.m_177449_(p_177447_);
        ServerLevel $$2 = this.m_177100_();
        $$2.m_8055_($$1).m_222972_($$2, $$1, $$2.f_46441_);
    }

    public int m_236774_(Heightmap.Types p_236775_, int p_236776_, int p_236777_) {
        BlockPos $$3 = this.m_177449_(new BlockPos(p_236776_, 0, p_236777_));
        return this.m_177452_(this.m_177100_().m_5452_(p_236775_, $$3)).m_123342_();
    }

    public void m_177289_(String p_177290_, BlockPos p_177291_) {
        throw new GameTestAssertPosException(p_177290_, this.m_177449_(p_177291_), p_177291_, this.m_177436_());
    }

    public void m_177286_(String p_177287_, Entity p_177288_) {
        throw new GameTestAssertPosException(p_177287_, p_177288_.m_20183_(), this.m_177452_(p_177288_.m_20183_()), this.m_177436_());
    }

    public void m_177284_(String p_177285_) {
        throw new GameTestAssertException(p_177285_);
    }

    public void m_177392_(Runnable p_177393_) {
        this.f_127595_.m_177489_().m_177552_(p_177393_).m_177554_(() -> new GameTestAssertException("Fail conditions met"));
    }

    public void m_177410_(Runnable p_177411_) {
        LongStream.range(this.f_127595_.m_177488_(), this.f_127595_.m_177490_()).forEach(p_177365_ -> this.f_127595_.m_177472_(p_177365_, p_177411_::run));
    }

    public GameTestSequence m_177425_() {
        return this.f_127595_.m_177489_();
    }

    public BlockPos m_177449_(BlockPos p_177450_) {
        BlockPos $$1 = this.f_127595_.m_127636_();
        BlockPos $$2 = $$1.m_121955_(p_177450_);
        return StructureTemplate.m_74593_($$2, Mirror.NONE, this.f_127595_.m_127646_(), $$1);
    }

    public BlockPos m_177452_(BlockPos p_177453_) {
        BlockPos $$1 = this.f_127595_.m_127636_();
        Rotation $$2 = this.f_127595_.m_127646_().m_55952_(Rotation.CLOCKWISE_180);
        BlockPos $$3 = StructureTemplate.m_74593_(p_177453_, Mirror.NONE, $$2, $$1);
        return $$3.m_121996_($$1);
    }

    public Vec3 m_177227_(Vec3 p_177228_) {
        Vec3 $$1 = Vec3.m_82528_(this.f_127595_.m_127636_());
        return StructureTemplate.m_74578_($$1.m_82549_(p_177228_), Mirror.NONE, this.f_127595_.m_127646_(), this.f_127595_.m_127636_());
    }

    public long m_177436_() {
        return this.f_127595_.m_177488_();
    }

    private AABB m_177448_() {
        return this.f_127595_.m_177484_();
    }

    private AABB m_177451_() {
        AABB $$0 = this.f_127595_.m_177484_();
        return $$0.m_82338_(BlockPos.f_121853_.m_121996_(this.m_177449_(BlockPos.f_121853_)));
    }

    public void m_177292_(Consumer<BlockPos> p_177293_) {
        AABB $$1 = this.m_177451_();
        BlockPos.MutableBlockPos.m_121921_($$1.m_82386_(0.0, 1.0, 0.0)).forEach(p_177293_);
    }

    public void m_177423_(Runnable p_177424_) {
        LongStream.range(this.f_127595_.m_177488_(), this.f_127595_.m_177490_()).forEach(p_177283_ -> this.f_127595_.m_177472_(p_177283_, p_177424_::run));
    }
}

