/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.EnchantedBookItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.EnchantmentInstance
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.machines.tiles.hv;

import ic2.api.items.electric.IElectricEnchantable;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.hv.ElectricEnchanterContainer;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.INotifyInventory;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.EnchantUtil;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ElectricEnchanterTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ITileGui,
IProgressMachine,
INotifyInventory,
ITileActivityProvider {
    static final Random RAND = new Random();
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int mode = 0;
    @NetworkInfo
    public boolean checked = false;
    @NetworkInfo
    public boolean valid = false;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int enchantLevel = 0;
    @NetworkInfo
    public int storedExperience = 0;
    @NetworkInfo
    public int neededExperience = 0;
    @NetworkInfo
    public boolean running = false;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int progress = 0;
    public int seed;
    public BookInformation renderData = new BookInformation();

    public ElectricEnchanterTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 7, 512, 1000000);
        this.setFuelSlot(0);
        this.redstoneIsFull = false;
        this.addGuiFields("mode", "checked", "valid", "enchantLevel", "storedExperience", "neededExperience", "running", "progress");
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128344_("mode", (byte)this.mode);
        compound.m_128379_("checked", this.checked);
        compound.m_128379_("isValid", this.valid);
        compound.m_128344_("enchantLevel", (byte)this.enchantLevel);
        compound.m_128376_("storedExp", (short)this.storedExperience);
        compound.m_128376_("neededXP", (short)this.neededExperience);
        compound.m_128379_("running", this.running);
        compound.m_128376_("progress", (short)this.progress);
        compound.m_128405_("seed", this.seed);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.mode = compound.m_128451_("mode");
        this.checked = compound.m_128471_("checked");
        this.valid = compound.m_128471_("isValid");
        this.enchantLevel = compound.m_128451_("enchantLevel");
        this.storedExperience = compound.m_128451_("storedExp");
        this.neededExperience = compound.m_128451_("neededExp");
        this.running = compound.m_128471_("running");
        this.progress = compound.m_128451_("progress");
        this.seed = compound.m_128451_("seed");
    }

    @Override
    public boolean supportsNotify() {
        return true;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.ELECTRIC_ENCHANTER;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ElectricEnchanterContainer(this, player, windowID);
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        switch (key) {
            case 0: {
                ++this.mode;
                this.mode %= 3;
                this.checked = false;
                this.updateGuiField("mode");
                break;
            }
            case 1: {
                if (value < 0 && !this.running) {
                    int removing = Math.min(-value, this.storedExperience);
                    if (!entity.m_7500_()) {
                        entity.m_6756_(removing);
                    }
                    entity.m_7408_(ItemStack.f_41583_, 0);
                    this.storedExperience -= removing;
                    this.updateGuiField("storedExperience");
                    break;
                }
                if (value <= 0) break;
                this.storedExperience += EnchantUtil.drainExperience(entity, Math.min(1000 - this.storedExperience, value));
                this.updateGuiField("storedExperience");
                break;
            }
            case 2: {
                if (!this.valid) break;
                this.running = true;
                this.seed = value;
                if (this.mode == 0) {
                    entity.m_7408_(ItemStack.f_41583_, 0);
                }
                this.updateGuiField("running");
                this.addToTick();
            }
        }
    }

    @Override
    public void onNotify(IHasInventory inv, int slot) {
        if (this.isSimulating() && ((ItemStack)this.inventory.get(6)).m_41619_() && !((ItemStack)this.inventory.get(5)).m_41619_()) {
            this.checked = false;
            this.updateGuiField("checked");
        }
    }

    @Override
    public void onTick() {
        this.handleChargeSlot((int)((double)this.maxEnergy * 0.9));
        if (!((ItemStack)this.inventory.get(6)).m_41619_() || ((ItemStack)this.inventory.get(5)).m_41619_()) {
            if (this.checked) {
                this.checked = false;
                this.valid = false;
                this.running = false;
                this.neededExperience = 0;
                this.progress = 0;
                this.updateGuiFields("checked", "valid", "running", "neededExperience", "progress");
            }
        } else {
            switch (this.mode) {
                case 0: {
                    this.enchant();
                    break;
                }
                case 1: {
                    this.anvil();
                    break;
                }
                case 2: {
                    this.disenchant();
                }
            }
        }
        this.handleComparators();
    }

    public void disenchant() {
        if (!this.checked) {
            this.checked = true;
            this.valid = this.getBookCount(EnchantType.DISENCHANT) > 0 && this.isAdvancedEnchanted((ItemStack)this.inventory.get(5));
            this.neededExperience = this.valid ? 50 : 0;
            this.enchantLevel = 0;
            if (this.neededExperience > this.storedExperience && this.running) {
                this.running = false;
                this.progress = 0;
                this.updateGuiFields("running", "progress");
            }
            this.updateGuiFields("checked", "valid", "enchantLevel", "neededExperience");
        }
        if (!this.valid) {
            this.setActive(false);
            if (this.progress > 0 || this.running) {
                this.progress = 0;
                this.running = false;
                this.updateGuiFields("running", "progress");
            }
            return;
        }
        if (this.neededExperience <= 0 || !this.running) {
            this.setActive(false);
            return;
        }
        if (this.hasEnergy(500)) {
            this.setActive(true);
            this.useEnergy(500);
            ++this.progress;
            if (this.progress >= 250) {
                this.disenchantItem();
                this.storedExperience -= this.neededExperience;
                this.neededExperience = 0;
                this.progress = 0;
                this.running = false;
                this.checked = false;
                this.updateGuiFields("storedExperience", "neededExperience", "running", "checked");
            }
            this.updateGuiField("progress");
        } else {
            this.setActive(false);
        }
    }

    public void disenchantItem() {
        ItemStack stack = new ItemStack((ItemLike)Items.f_42690_);
        EnchantmentInstance data = this.consumeFirstEnchantment((ItemStack)this.inventory.get(5));
        if (data != null) {
            EnchantedBookItem.m_41153_((ItemStack)stack, (EnchantmentInstance)data);
        }
        this.inventory.set(6, (Object)stack);
        for (int i = 2; i < 5; ++i) {
            ItemStack item = (ItemStack)this.inventory.get(i);
            if (item.m_41619_()) continue;
            item.m_41774_(1);
            break;
        }
    }

    public ItemStack getDisenchantedItem() {
        Map map = EnchantmentHelper.m_44831_((ItemStack)((ItemStack)this.inventory.get(5)));
        Iterator iter = map.entrySet().iterator();
        if (!iter.hasNext()) {
            return ItemStack.f_41583_;
        }
        Map.Entry entry = iter.next();
        while (entry != null && !((Enchantment)entry.getKey()).isAllowedOnBooks()) {
            entry = iter.hasNext() ? iter.next() : null;
        }
        if (entry == null) {
            return ItemStack.f_41583_;
        }
        ItemStack stack = new ItemStack((ItemLike)Items.f_42690_);
        EnchantedBookItem.m_41153_((ItemStack)stack, (EnchantmentInstance)new EnchantmentInstance((Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue()));
        return stack;
    }

    public void anvil() {
        if (!this.checked) {
            this.checked = true;
            this.valid = this.getBookCount(EnchantType.ANVIL) > 0 && this.isItemValidToEnchant((ItemStack)this.inventory.get(5));
            this.calculateAnvilCost();
            this.neededExperience = (int)((double)this.neededExperience * (this.valid ? 1.0 : 0.0));
            if (this.neededExperience > this.storedExperience && this.running) {
                this.running = false;
                this.progress = 0;
                this.updateGuiFields("running", "progress");
            }
            this.updateGuiFields("checked", "valid");
        }
        if (!this.valid) {
            this.setActive(false);
            if (this.progress > 0 || this.running) {
                this.progress = 0;
                this.running = false;
                this.updateGuiFields("running", "progress");
            }
            return;
        }
        if (this.neededExperience <= 0 || !this.running) {
            this.setActive(false);
            return;
        }
        if (this.hasEnergy(500)) {
            this.setActive(true);
            this.useEnergy(500);
            ++this.progress;
            if (this.progress >= 100) {
                this.anvilItem();
                this.storedExperience -= this.neededExperience;
                this.neededExperience = 0;
                this.progress = 0;
                this.valid = false;
                this.running = false;
                this.checked = false;
                this.updateGuiFields("storedExperience", "neededExperience", "running", "checked", "valid");
            }
            this.updateGuiField("progress");
        } else {
            this.setActive(false);
        }
    }

    public void anvilItem() {
        this.inventory.set(6, (Object)this.getAnvilItem());
        ((ItemStack)this.inventory.get(5)).m_41774_(1);
        for (int i = 2; i < 5; ++i) {
            ItemStack item = (ItemStack)this.inventory.get(i);
            if (item.m_41619_()) continue;
            item.m_41774_(1);
        }
    }

    public ItemStack getAnvilItem() {
        ItemStack stack = ((ItemStack)this.inventory.get(5)).m_41777_();
        Object2IntLinkedOpenHashMap enchantments = new Object2IntLinkedOpenHashMap(EnchantmentHelper.m_44831_((ItemStack)stack));
        EnchantUtil.mergeEnchantments((Object2IntMap<Enchantment>)enchantments, this.getBookEnchantment(3), stack);
        if (stack.m_41720_() == Items.f_42517_ || stack.m_41720_() == Items.f_42690_) {
            stack = new ItemStack((ItemLike)Items.f_42690_);
        } else {
            stack.m_41742_(stack.m_41610_() + this.f_58857_.f_46441_.m_188503_(1 + enchantments.size()));
        }
        EnchantmentHelper.m_44865_((Map)enchantments, (ItemStack)stack);
        return stack;
    }

    public void enchant() {
        if (!this.checked) {
            this.checked = true;
            this.valid = this.getBookCount(EnchantType.ENCHANT) > 0 && ((ItemStack)this.inventory.get(1)).m_41613_() > 0 && this.isItemValidToEnchant((ItemStack)this.inventory.get(5));
            this.calculateEnchantmentCost();
            this.neededExperience = (int)((double)this.neededExperience * (this.valid ? 1.0 : 0.0));
            if (this.neededExperience > this.storedExperience && this.running) {
                this.running = false;
                this.progress = 0;
                this.updateGuiFields("running", "progress");
            }
            this.updateGuiFields("checked", "valid");
        }
        if (!this.valid) {
            this.setActive(false);
            if (this.progress > 0 || this.running) {
                this.progress = 0;
                this.running = false;
                this.updateGuiFields("running", "progress");
            }
            return;
        }
        if (!this.running && this.clock(128)) {
            this.calculateEnchantmentCost();
        }
        if (this.enchantLevel <= 0 || !this.running) {
            this.setActive(false);
            return;
        }
        if (this.hasEnergy(500)) {
            this.setActive(true);
            this.useEnergy(500);
            ++this.progress;
            if (this.progress >= 100) {
                this.enchantItem();
                this.storedExperience -= this.neededExperience;
                this.neededExperience = 0;
                this.progress = 0;
                this.running = false;
                this.updateGuiFields("storedExperience", "neededExperience", "running", "checked");
            }
            this.updateGuiField("progress");
        } else {
            this.setActive(false);
        }
    }

    protected void enchantItem() {
        Tuple<ItemStack, Integer> result = this.enchantItem(this.seed);
        this.inventory.set(6, (Object)((ItemStack)result.m_14418_()));
        ((ItemStack)this.inventory.get(5)).m_41774_(1);
        ((ItemStack)this.inventory.get(1)).m_41774_(((Integer)result.m_14419_()).intValue());
        for (int i = 2; i < 5; ++i) {
            ItemStack item = (ItemStack)this.inventory.get(i);
            if (item.m_41619_()) continue;
            item.m_41774_(1);
        }
    }

    public List<EnchantmentInstance> getBookEnchantment(int size) {
        ObjectList result = CollectionUtils.createList();
        int m = 0;
        for (int i = 2; i < 5 && m < size; ++i) {
            ItemStack item = (ItemStack)this.inventory.get(i);
            if (item.m_41720_() != Items.f_42690_) continue;
            for (Map.Entry entry : EnchantmentHelper.m_44831_((ItemStack)item).entrySet()) {
                result.add((EnchantmentInstance)new EnchantmentInstance((Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue()));
            }
            ++m;
        }
        return result;
    }

    public int getBookCount(EnchantType enchant) {
        int found = 0;
        for (int i = 2; i < 5; ++i) {
            Item item = ((ItemStack)this.inventory.get(i)).m_41720_();
            if (item == Items.f_41852_) continue;
            if (!enchant.isValid(item)) {
                return -1;
            }
            ++found;
        }
        return found;
    }

    public ItemStack getPotentialOutput(int seed) {
        switch (this.mode) {
            case 1: {
                return this.getAnvilItem();
            }
            case 2: {
                return this.getDisenchantedItem();
            }
        }
        return this.getEnchantedItem(seed);
    }

    private Tuple<ItemStack, Integer> enchantItem(int seed) {
        int count = this.getBookCount(EnchantType.ENCHANT);
        RandomSource rand = this.f_58857_.m_213780_();
        rand.m_188584_((long)(seed + this.enchantLevel));
        ItemStack stack = ((ItemStack)this.inventory.get(5)).m_41777_();
        stack.m_41764_(1);
        stack.m_41749_("Enchantments");
        List<EnchantmentInstance> enchantments = EnchantUtil.createElectricEnchantments(EnchantmentHelper.m_220287_((RandomSource)rand, (int)count, (int)this.enchantLevel, (ItemStack)stack), stack, false, rand);
        List<EnchantmentInstance> books = this.getBookEnchantment(count);
        ObjectList validEnchantments = CollectionUtils.createList();
        rand.m_188584_(rand.m_188505_());
        MathUtils.shuffle(enchantments, rand);
        int m = enchantments.size();
        for (int i = 0; i < m && i < 6; ++i) {
            EnchantmentInstance data = enchantments.get(i);
            if (!EnchantUtil.canInsertEnchantment(data, validEnchantments) || !EnchantUtil.canInsertEnchantment(data, books)) continue;
            validEnchantments.add((EnchantmentInstance)data);
        }
        rand.m_188584_(rand.m_188505_());
        MathUtils.shuffle(enchantments, rand);
        Object2IntOpenHashMap result = new Object2IntOpenHashMap();
        for (EnchantmentInstance data : validEnchantments.subList(0, Math.min(count * 2, validEnchantments.size()))) {
            result.put((Object)data.f_44947_, data.f_44948_);
        }
        EnchantUtil.mergeEnchantments((Object2IntMap<Enchantment>)result, books, stack);
        if (stack.m_41720_() == Items.f_42517_ || stack.m_41720_() == Items.f_42690_) {
            stack = new ItemStack((ItemLike)Items.f_42690_);
        }
        EnchantmentHelper.m_44865_((Map)result, (ItemStack)stack);
        return new Tuple((Object)stack, (Object)count);
    }

    public ItemStack getEnchantedItem(int seed) {
        return (ItemStack)this.enchantItem(seed).m_14418_();
    }

    public void calculateAnvilCost() {
        this.enchantLevel = 0;
        this.neededExperience = this.getAnvilCost() * 10;
        this.updateGuiFields("enchantLevel", "neededExperience");
    }

    public int getAnvilCost() {
        return EnchantUtil.getEnchantmentCount((ItemStack)this.inventory.get(5)) + EnchantUtil.getEnchantmentCount((ItemStack)this.inventory.get(2)) + EnchantUtil.getEnchantmentCount((ItemStack)this.inventory.get(3)) + EnchantUtil.getEnchantmentCount((ItemStack)this.inventory.get(4));
    }

    public void calculateEnchantmentCost() {
        this.enchantLevel = Math.min(this.getBookShelves(), this.getMaxLevel());
        this.neededExperience = (int)Math.pow(7 + (Mth.m_14165_((double)((double)this.enchantLevel / 10.0)) - 1) * 2, ((ItemStack)this.inventory.get(5)).m_41720_() == Items.f_42690_ || ((ItemStack)this.inventory.get(5)).m_41793_() ? 2.6 : 2.3);
        this.updateGuiFields("enchantLevel", "neededExperience");
    }

    public boolean isItemValidToEnchant(ItemStack item) {
        return item.m_41720_() == Items.f_42517_ || item.m_41720_() == Items.f_42690_ || item.m_41720_().m_8120_(item) || item.m_41720_() instanceof IElectricEnchantable;
    }

    public boolean isEnchanted(ItemStack item) {
        return item.m_41720_() == Items.f_42690_ || (item.m_41720_().m_8120_(item) || item.m_41720_() instanceof IElectricEnchantable) && item.m_41793_();
    }

    public boolean isAdvancedEnchanted(ItemStack stack) {
        return this.isEnchanted(stack) && !EnchantmentHelper.m_44831_((ItemStack)stack).isEmpty();
    }

    public EnchantmentInstance consumeFirstEnchantment(ItemStack stack) {
        Map map = EnchantmentHelper.m_44831_((ItemStack)stack);
        Iterator iter = map.entrySet().iterator();
        Map.Entry entry = iter.next();
        while (entry != null && !((Enchantment)entry.getKey()).isAllowedOnBooks()) {
            entry = iter.hasNext() ? iter.next() : null;
        }
        if (entry == null) {
            return null;
        }
        EnchantmentInstance data = new EnchantmentInstance((Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
        iter.remove();
        if (stack.m_41720_() == Items.f_42690_) {
            if (map.isEmpty()) {
                this.inventory.set(5, (Object)new ItemStack((ItemLike)Items.f_42517_));
            } else {
                ItemStack newStack = new ItemStack((ItemLike)Items.f_42690_);
                EnchantmentHelper.m_44865_((Map)map, (ItemStack)newStack);
                this.inventory.set(5, (Object)newStack);
            }
        } else {
            EnchantmentHelper.m_44865_((Map)map, (ItemStack)stack);
        }
        return data;
    }

    public int getBookShelves() {
        int amount = 0;
        BlockPos.MutableBlockPos pos = this.m_58899_().m_122032_();
        for (int y = 0; y < 2; ++y) {
            for (int x = -2; x <= 2; ++x) {
                for (int z = -2; z <= 2; ++z) {
                    pos.m_122190_((Vec3i)this.m_58899_()).m_122184_(x, y, z);
                    if (!this.f_58857_.m_46749_((BlockPos)pos)) continue;
                    amount += (int)this.f_58857_.m_8055_((BlockPos)pos).getEnchantPowerBonus((LevelReader)this.f_58857_, (BlockPos)pos);
                }
            }
        }
        return Math.min(amount, 30);
    }

    public int getMaxLevel() {
        return Mth.m_14045_((int)Math.min(((ItemStack)this.inventory.get(1)).m_41613_(), this.getBookCount(EnchantType.ENCHANT)), (int)0, (int)3) * 10;
    }

    @Override
    public float getProgress() {
        return this.progress;
    }

    @Override
    public float getMaxProgress() {
        return this.mode == 2 ? 250.0f : 100.0f;
    }

    public class BookInformation {
        public int tickCount;
        public float pageFlip;
        public float pageFlipPrev;
        public float flipT;
        public float flipA;
        public float bookSpread;
        public float bookSpreadPrev;
        public float bookRotation;
        public float bookRotationPrev;
        public float tRot;
        public long lastTick = -1L;

        public void tick() {
            double f2;
            Level world = ElectricEnchanterTileEntity.this.m_58904_();
            if (world.m_46467_() == this.lastTick) {
                return;
            }
            this.lastTick = world.m_46467_();
            BlockPos pos = ElectricEnchanterTileEntity.this.m_58899_();
            this.bookSpreadPrev = this.bookSpread;
            this.bookRotationPrev = this.bookRotation;
            Player player = world.m_45924_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 4.0, false);
            if (player != null && Math.sqrt(player.m_20238_(Vec3.m_82528_((Vec3i)pos).m_82520_(0.5, 0.5, 0.5))) <= 3.0) {
                double d0 = player.m_20185_() - (double)((float)pos.m_123341_() + 0.5f);
                double d1 = player.m_20189_() - (double)((float)pos.m_123343_() + 0.5f);
                this.tRot = (float)Mth.m_14136_((double)d1, (double)d0);
                this.bookSpread += 0.1f;
                if (this.bookSpread < 0.5f || RAND.nextInt(40) == 0) {
                    int offset = 0;
                    while ((offset = RAND.nextInt(4) - RAND.nextInt(4)) == 0) {
                    }
                    this.flipT += (float)offset;
                }
            } else {
                this.tRot += 0.02f;
                this.bookSpread -= 0.1f;
            }
            if ((double)this.bookRotation < -Math.PI) {
                this.bookRotation = (float)((double)this.bookRotation + Math.PI * 2);
            }
            if ((double)this.tRot >= Math.PI) {
                this.tRot = (float)((double)this.tRot - Math.PI * 2);
            }
            if ((double)this.tRot < -Math.PI) {
                this.tRot = (float)((double)this.tRot + Math.PI * 2);
            }
            if ((f2 = (double)(this.tRot - this.bookRotation)) >= Math.PI) {
                f2 -= Math.PI * 2;
            }
            if (f2 < -Math.PI) {
                f2 += Math.PI * 2;
            }
            this.bookRotation = (float)((double)this.bookRotation + f2 * (double)0.4f);
            this.bookSpread = Mth.m_14036_((float)this.bookSpread, (float)0.0f, (float)1.0f);
            ++this.tickCount;
            this.pageFlipPrev = this.pageFlip;
            this.flipA += (Mth.m_14036_((float)((this.flipT - this.pageFlip) * 0.4f), (float)-0.2f, (float)0.2f) - this.flipA) * 0.9f;
            this.pageFlip += this.flipA;
        }
    }

    public static enum EnchantType {
        ENCHANT,
        ANVIL,
        DISENCHANT;


        public boolean isValid(Item item) {
            switch (this) {
                case DISENCHANT: {
                    return item == Items.f_42517_;
                }
                case ANVIL: {
                    return item == Items.f_42690_;
                }
            }
            return item == Items.f_42517_ || item == Items.f_42690_;
        }
    }
}

