package ru.mot.ic2exfidelity.legacy;

import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Items;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Exact IC2 2.8.222 beer/rum composition, maturation and tap behaviour. */
public final class LegacyBarrelBlockEntity extends Ic2TileEntity {
    private static final int CAPACITY = 32;
    private int type;
    private int boozeAmount;
    private int age;
    private boolean opened;
    private byte hopsCount;
    private byte wheatCount;
    private byte solidRatio;
    private byte hopsRatio;
    private byte timeRatio;

    public LegacyBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(RestoredLegacyContent.BARREL_BLOCK_ENTITY.get(), pos, state);
    }

    public void loadFromItem(ItemStack stack) {
        int value = LegacyBarrelItem.getValue(stack);
        type = LegacyBoozeItem.getTypeOfValue(value);
        boozeAmount = type > 0 ? LegacyBoozeItem.getAmountOfValue(value) : 0;
        if (type == 1) {
            opened = true;
            hopsRatio = (byte) LegacyBoozeItem.getHopsRatioOfBeerValue(value);
            solidRatio = (byte) LegacyBoozeItem.getSolidRatioOfBeerValue(value);
            timeRatio = (byte) LegacyBoozeItem.getTimeRatioOfBeerValue(value);
        } else if (type == 2) {
            opened = false;
            age = timeNeededForRum(boozeAmount)
                    * LegacyBoozeItem.getProgressOfRumValue(value) / 100;
        }
        m_6596_();
    }

    @Override
    public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        type = tag.m_128445_("type");
        boozeAmount = tag.m_128445_("waterCount");
        age = tag.m_128451_("age");
        opened = tag.m_128471_("opened");
        if (type == 1) {
            if (!opened) {
                hopsCount = tag.m_128445_("hopsCount");
                wheatCount = tag.m_128445_("wheatCount");
            }
            solidRatio = tag.m_128445_("solidRatio");
            hopsRatio = tag.m_128445_("hopsRatio");
            timeRatio = tag.m_128445_("timeRatio");
        }
    }

    @Override
    public void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        tag.m_128344_("type", (byte) type);
        tag.m_128344_("waterCount", (byte) boozeAmount);
        tag.m_128405_("age", age);
        tag.m_128379_("opened", opened);
        if (type == 1) {
            if (!opened) {
                tag.m_128344_("hopsCount", hopsCount);
                tag.m_128344_("wheatCount", wheatCount);
            }
            tag.m_128344_("solidRatio", solidRatio);
            tag.m_128344_("hopsRatio", hopsRatio);
            tag.m_128344_("timeRatio", timeRatio);
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (isEmpty() || getActive()) {
            return;
        }
        age++;
        if (type == 1 && timeRatio < 5) {
            int ratio = timeRatio;
            if (ratio == 4) {
                ratio += 2;
            }
            if ((double) age >= 24_000.0D * Math.pow(3.0D, ratio)) {
                age = 0;
                timeRatio++;
                m_6596_();
            }
        }
    }

    public boolean isEmpty() {
        return type == 0 || boozeAmount <= 0;
    }

    @Override
    protected InteractionResult onActivated(
            Player player, InteractionHand hand, Direction side, Vec3 hit) {
        Level level = m_58904_();
        if (level == null) {
            return InteractionResult.PASS;
        }
        ItemStack held = player.m_21120_(hand);

        if (!side.m_122434_().m_122478_() && !getActive()
                && held.m_41720_() == Ic2Items.TREETAP) {
            if (!level.f_46443_) {
                if (!player.m_150110_().f_35937_) {
                    held.m_41774_(1);
                }
                setActive(true);
                if (getFacing() != side) {
                    setFacing(level, side);
                }
            }
            return InteractionResult.SUCCESS;
        }

        if (getActive() && side == getFacing() && held.m_41720_() == Ic2Items.EMPTY_MUG) {
            if (!level.f_46443_ && !isEmpty()) {
                ItemStack filled = LegacyBoozeItem.create(calculateValue());
                if (!drainLiquid(1)) {
                    return InteractionResult.PASS;
                }
                held.m_41774_(1);
                if (held.m_41619_()) {
                    player.m_21008_(hand, filled);
                } else if (!player.m_150109_().m_36054_(filled)) {
                    player.m_36176_(filled, false);
                }
            }
            return InteractionResult.SUCCESS;
        }

        if (!opened && (type == 0 || type == 1)) {
            if (tryAddWater(player, hand, held)) {
                return InteractionResult.SUCCESS;
            }
            if (held.m_41720_() == vanillaItem("wheat")) {
                return addSolid(player, held, false);
            }
            if (held.m_41720_() == Ic2Items.HOPS) {
                return addSolid(player, held, true);
            }
        }

        if (!opened && (type == 0 || type == 2)
                && held.m_41720_() == vanillaItem("sugar_cane") && age <= 600) {
            int amount = player.m_6144_() ? 1 : held.m_41613_();
            amount = Math.min(amount, CAPACITY - boozeAmount);
            if (amount <= 0) {
                return InteractionResult.PASS;
            }
            if (!level.f_46443_) {
                type = 2;
                boozeAmount += amount;
                if (!player.m_150110_().f_35937_) {
                    held.m_41774_(amount);
                }
                m_6596_();
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult addSolid(Player player, ItemStack held, boolean hops) {
        int current = hops ? hopsCount : wheatCount;
        int amount = player.m_6144_() ? 1 : held.m_41613_();
        amount = Math.min(amount, 64 - current);
        if (amount <= 0) {
            return InteractionResult.PASS;
        }
        Level level = m_58904_();
        if (level != null && !level.f_46443_) {
            type = 1;
            if (hops) {
                hopsCount = (byte) (hopsCount + amount);
            } else {
                wheatCount = (byte) (wheatCount + amount);
            }
            if (!player.m_150110_().f_35937_) {
                held.m_41774_(amount);
            }
            alterComposition();
            m_6596_();
        }
        return InteractionResult.SUCCESS;
    }

    private boolean tryAddWater(Player player, InteractionHand hand, ItemStack held) {
        if (boozeAmount >= CAPACITY || held.m_41619_()) {
            return false;
        }
        ItemStack unit = held.m_41777_();
        unit.m_41764_(1);
        IFluidHandlerItem handler = FluidUtil.getFluidHandler(unit).orElse(null);
        if (handler == null) {
            return false;
        }
        FluidStack request = new FluidStack(net.minecraft.world.level.material.Fluids.f_76193_, 1_000);
        FluidStack simulated = handler.drain(request, IFluidHandler.FluidAction.SIMULATE);
        if (simulated.getAmount() < 1_000
                || simulated.getFluid() != net.minecraft.world.level.material.Fluids.f_76193_) {
            return false;
        }

        Level level = m_58904_();
        if (level == null || level.f_46443_) {
            return true;
        }
        FluidStack drained = handler.drain(request, IFluidHandler.FluidAction.EXECUTE);
        if (drained.getAmount() != 1_000) {
            return false;
        }
        type = 1;
        boozeAmount++;
        if (!player.m_150110_().f_35937_) {
            ItemStack remainder = handler.getContainer();
            held.m_41774_(1);
            if (held.m_41619_()) {
                player.m_21008_(hand, remainder);
            } else if (!remainder.m_41619_() && !player.m_150109_().m_36054_(remainder)) {
                player.m_36176_(remainder, false);
            }
        }
        m_6596_();
        return true;
    }

    @Override
    protected InteractionResult onClicked(Player player) {
        Level level = m_58904_();
        if (level == null) {
            return InteractionResult.PASS;
        }
        if (!level.f_46443_) {
            if (getActive()) {
                Block.m_49840_(level, m_58899_(), new ItemStack(Ic2Items.TREETAP));
                setActive(false);
                drainLiquid(1);
            } else {
                Block.m_49840_(level, m_58899_(), LegacyBarrelItem.create(calculateValue()));
                level.m_7731_(m_58899_(), Ic2Blocks.WOODEN_SCAFFOLD.m_49966_(), 3);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void alterComposition() {
        Level level = m_58904_();
        if (timeRatio <= 0) {
            age = 0;
        } else if (timeRatio == 1 && level != null) {
            if (level.f_46441_.m_188499_()) {
                timeRatio = 0;
            } else if (level.f_46441_.m_188499_()) {
                timeRatio = 5;
            }
        } else if (timeRatio == 2 && level != null) {
            if (level.f_46441_.m_188499_()) {
                timeRatio = 5;
            }
        } else {
            timeRatio = 5;
        }
    }

    public boolean drainLiquid(int amount) {
        if (isEmpty() || amount > boozeAmount) {
            return false;
        }
        open();
        if (type == 2) {
            int progress = age * 100 / timeNeededForRum(boozeAmount);
            boozeAmount -= amount;
            age = progress / 100 * timeNeededForRum(boozeAmount);
        } else {
            boozeAmount -= amount;
        }
        if (boozeAmount <= 0) {
            if (type == 1) {
                hopsCount = 0;
                wheatCount = 0;
                hopsRatio = 0;
                solidRatio = 0;
                timeRatio = 0;
            }
            type = 0;
            opened = false;
            boozeAmount = 0;
        }
        m_6596_();
        return true;
    }

    private void open() {
        if (opened) {
            return;
        }
        opened = true;
        if (type == 1) {
            float ratio = hopsCount <= 0 ? 0.0F : (float) hopsCount / (float) wheatCount;
            if (ratio <= 0.25F) {
                hopsRatio = 0;
            } else if (ratio <= 1.0F / 3.0F) {
                hopsRatio = 1;
            } else if (ratio <= 0.5F) {
                hopsRatio = 2;
            } else if (ratio < 2.0F) {
                hopsRatio = 3;
            } else {
                hopsRatio = (byte) Math.min(6.0D, Math.floor(ratio) + 2.0D);
                if (ratio >= 5.0F) {
                    timeRatio = 5;
                }
            }

            float solid = boozeAmount <= 0
                    ? Float.POSITIVE_INFINITY
                    : (float) (hopsCount + wheatCount) / (float) boozeAmount;
            if (solid <= 5.0F / 12.0F) {
                solidRatio = 0;
            } else if (solid <= 0.5F) {
                solidRatio = 1;
            } else if (solid < 1.0F) {
                solidRatio = 2;
            } else if (solid == 1.0F) {
                solidRatio = 3;
            } else if (solid < 2.0F) {
                solidRatio = 4;
            } else if (solid < 2.4F) {
                solidRatio = 5;
            } else {
                solidRatio = 6;
                if (solid >= 4.0F) {
                    timeRatio = 5;
                }
            }
        }
    }

    public int calculateValue() {
        if (isEmpty()) {
            return 0;
        }
        open();
        if (type == 1) {
            int value = timeRatio;
            value = value << 3 | hopsRatio;
            value = value << 3 | solidRatio;
            value = value << 5 | boozeAmount - 1;
            return value << 2 | type;
        }
        if (type == 2) {
            int progress = Math.min(100, age * 100 / timeNeededForRum(boozeAmount));
            int value = progress;
            value = value << 5 | boozeAmount - 1;
            return value << 2 | type;
        }
        return 0;
    }

    public static int timeNeededForRum(int amount) {
        return (int) (1_200 * amount * Math.pow(0.95D, amount - 1));
    }

    private static Item vanillaItem(String path) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", path));
        if (item == null) {
            throw new IllegalStateException("Missing vanilla item: " + path);
        }
        return item;
    }

    @Override
    protected ItemStack getPickBlock(Player player, BlockHitResult hit) {
        return new ItemStack(Ic2Blocks.WOODEN_SCAFFOLD);
    }

    @Override
    protected List<ItemStack> getAuxDrops(int fortune) {
        List<ItemStack> result = new ArrayList<>(super.getAuxDrops(fortune));
        result.add(LegacyBarrelItem.create(0));
        return result;
    }

    public void debugSetBeer(int water, int wheat, int hops, int maturation) {
        type = 1;
        boozeAmount = water;
        wheatCount = (byte) wheat;
        hopsCount = (byte) hops;
        timeRatio = (byte) maturation;
        opened = false;
        age = 0;
    }

    public void debugSetRum(int amount, int progress) {
        type = 2;
        boozeAmount = amount;
        opened = false;
        age = timeNeededForRum(amount) * progress / 100;
    }
}
