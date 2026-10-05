package ru.mot.ic2exfidelity.iu;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

/** Filter modules record block ids by using them on a block; no block is broken. */
public final class QuarryModule extends Item {
    public final String kind;
    public QuarryModule(Properties properties, String kind) { super(properties); this.kind = kind; }
    @Override public InteractionResult m_6225_(UseOnContext context) {
        if (!kind.equals("blacklist") && !kind.equals("whitelist")) return InteractionResult.PASS;
        if (!context.m_43725_().f_46443_) {
            ResourceLocation block = ForgeRegistries.BLOCKS.getKey(context.m_43725_().m_8055_(context.m_8083_()).m_60734_());
            String value = block.toString();
            String old = context.m_43722_().m_41784_().m_128461_("filter");
            if (context.m_43723_() != null && context.m_43723_().m_6144_()) old = "";
            java.util.Set<String> entries = new java.util.LinkedHashSet<>(java.util.Arrays.asList(old.split(",")));
            entries.remove(""); entries.add(value);
            context.m_43722_().m_41784_().m_128359_("filter", String.join(",", entries));
        }
        return InteractionResult.SUCCESS;
    }
    @Override public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        if (kind.equals("blacklist") || kind.equals("whitelist")) {
            tooltip.add(Component.m_237115_("iu_native.filter.info"));
            if (stack.m_41783_() != null) tooltip.add(Component.m_237113_(stack.m_41783_().m_128461_("filter")));
        }
    }
}
