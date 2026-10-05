package ru.mot.ic2exfidelity.iu;

import com.mojang.authlib.GameProfile;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

/** Ore-only scan. Drops are reserved before changing the world or consuming QE. */
public final class NativeQuarry {
    private static final GameProfile PROFILE = new GameProfile(UUID.fromString("d6266b04-7c61-4b96-a572-492513e82c70"), "[IC2 Quarry]");
    private NativeQuarry() {}
    public static void tick(NativeMachine machine) {
        if (!machine.running || machine.complete || !(machine.m_58904_() instanceof ServerLevel level)) return;
        int fortune = 0, widthChunks = 1;
        boolean furnace = false, macerator = false, washer = false;
        double cost = 450 * machine.specification.coefficient();
        List<ItemStack> filters = new ArrayList<>();
        for (int slot = 0; slot < machine.modules.size(); slot++) {
            ItemStack stack = machine.modules.get(slot);
            if (!(stack.m_41720_() instanceof QuarryModule module)) continue;
            String kind = module.kind;
            switch (kind) {
                case "speed_i", "speed_ii", "speed_iii", "speed_iv", "speed_v" -> cost -= 450 * machine.specification.coefficient() * .03 * rank(kind);
                case "depth_i", "depth_ii", "depth_iii" -> { if (widthChunks == 1) widthChunks = 1 + rank(kind) * 2; cost += 450 * machine.specification.coefficient() * Math.pow(4, rank(kind)) / 100; }
                case "lucky_i", "lucky_ii", "lucky_iii" -> { fortune = Math.max(fortune, rank(kind)); cost += 450 * machine.specification.coefficient() * rank(kind) * .02; }
                case "furnace" -> { furnace = true; cost += 450 * machine.specification.coefficient() * .05; }
                case "macerator", "combmac" -> { if (!macerator) { macerator = true; washer = kind.equals("combmac"); } cost += 450 * machine.specification.coefficient() * .05; }
                case "blacklist", "whitelist" -> filters.add(stack);
                default -> {}
            }
        }
        cost = Math.max(1, cost);
        int minY = Math.max(level.m_141937_(), machine.minY);
        int maxY = Math.min(level.m_151558_() - 1, machine.maxY);
        int width = 16 * widthChunks;
        int startX = (machine.m_58899_().m_123341_() >> 4) * 16 - 16 * (widthChunks - 1);
        int startZ = (machine.m_58899_().m_123343_() >> 4) * 16 - 16 * (widthChunks - 1);
        long total = (long)width * width * Math.max(0, maxY - minY + 1);
        FakePlayer player = FakePlayerFactory.get(level, PROFILE);
        ItemStack tool = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", "netherite_pickaxe")));
        if (fortune > 0) tool.m_41663_(Enchantments.f_44987_, fortune);
        player.m_21008_(InteractionHand.MAIN_HAND, tool);
        for (int scan = 0; scan < (1 << (machine.specification.tier() - 1)); scan++) {
            if (machine.cursor >= total) { machine.complete = true; machine.m_6596_(); return; }
            if (!machine.energy.canUseEnergy(cost)) return;
            BlockPos pos = new BlockPos(startX + (int)(machine.cursor % width), minY + (int)(machine.cursor / ((long)width * width)), startZ + (int)(machine.cursor / width % width));
            if (!level.m_46805_(pos)) return;
            BlockState state = level.m_8055_(pos);
            boolean ore = state.m_204336_(Tags.Blocks.ORES) || state.m_60734_() instanceof DiamondDeposit;
            if (ore && state.m_60800_(level, pos) >= 0 && !excluded(state, filters)) {
                BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(level, pos, state, player);
                if (!MinecraftForge.EVENT_BUS.post(event)) {
                    List<ItemStack> drops = Block.m_49874_(state, level, pos, level.m_7702_(pos), player, tool);
                    List<ItemStack> results = process(drops, level, macerator, washer, furnace);
                    boolean bonus = machine.quantum >= 80 && level.f_46441_.m_188503_(101) > 85;
                    if (bonus) { List<ItemStack> extra = new ArrayList<>(); for (ItemStack stack : results) extra.add(stack.m_41777_()); results.addAll(extra); }
                    if (!machine.output.canAdd(results)) return;
                    if (!level.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3)) return;
                    machine.output.add(results);
                    if (machine.quantum >= 80) machine.quantum -= 80;
                    machine.experience = Math.min(5000, machine.experience + Math.max(0, event.getExpToDrop()));
                    machine.m_6596_();
                }
            }
            machine.energy.useEnergy(cost);
            machine.cursor++;
        }
    }
    private static boolean excluded(BlockState state, List<ItemStack> filters) {
        String id = ForgeRegistries.BLOCKS.getKey(state.m_60734_()).toString();
        for (ItemStack stack : filters) {
            QuarryModule module = (QuarryModule)stack.m_41720_();
            boolean matches = stack.m_41783_() != null && Arrays.asList(stack.m_41783_().m_128461_("filter").split(",")).contains(id);
            if (module.kind.equals("whitelist") ? !matches : matches) return true;
        }
        return false;
    }
    private static List<ItemStack> process(List<ItemStack> drops, ServerLevel level, boolean macerator, boolean washer, boolean furnace) {
        List<ItemStack> result = new ArrayList<>();
        for (ItemStack drop : drops) {
            List<ItemStack> current = new ArrayList<>();
            if (macerator && Recipes.macerator != null) {
                var recipe = Recipes.macerator.get(level).apply(drop, false);
                if (recipe != null) {
                    int inputAmount = Math.max(1, recipe.getRecipe().getInput().getAmount());
                    int batches = drop.m_41613_() / inputAmount;
                    for (ItemStack stack : recipe.getOutput()) {
                        ItemStack product = stack.m_41777_();
                        product.m_41764_(product.m_41613_() * batches);
                        current.add(product);
                    }
                    if (drop.m_41613_() % inputAmount > 0) { ItemStack remainder = drop.m_41777_(); remainder.m_41764_(drop.m_41613_() % inputAmount); current.add(remainder); }
                }
            }
            if (current.isEmpty()) current.add(drop.m_41777_());
            if (washer && Recipes.oreWashing != null) {
                List<ItemStack> washed = new ArrayList<>();
                for (ItemStack stack : current) {
                    var recipe = Recipes.oreWashing.get(level).apply(stack, false);
                    if (recipe == null) washed.add(stack);
                    else {
                        int inputAmount = Math.max(1, recipe.getRecipe().getInput().getAmount());
                        int batches = stack.m_41613_() / inputAmount;
                        for (ItemStack out : recipe.getOutput()) { ItemStack product = out.m_41777_(); product.m_41764_(product.m_41613_() * batches); washed.add(product); }
                        if (stack.m_41613_() % inputAmount > 0) { ItemStack remainder = stack.m_41777_(); remainder.m_41764_(stack.m_41613_() % inputAmount); washed.add(remainder); }
                    }
                }
                current = washed;
            }
            for (ItemStack stack : current) {
                if (furnace && Recipes.furnace != null) {
                    var recipe = Recipes.furnace.apply(stack, false);
                    if (recipe != null) { ItemStack cooked = recipe.getOutput().m_41777_(); cooked.m_41764_(cooked.m_41613_() * stack.m_41613_()); stack = cooked; }
                }
                result.add(stack);
            }
        }
        return result;
    }
    private static int rank(String kind) { return switch (kind.substring(kind.lastIndexOf('_') + 1)) { case "i" -> 1; case "ii" -> 2; case "iii" -> 3; case "iv" -> 4; case "v" -> 5; default -> 1; }; }
}
