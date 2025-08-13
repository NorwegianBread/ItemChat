package com.bread7.item_chat.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.regex.Pattern;

public class StackText {

    public static Text handlePatternText(PlayerEntity player, String message, CallbackInfo ci) {
        String pattern = Config.getPattern();
        if (!message.contains(pattern) || player == null) return null;

        ItemStack stack = player.getMainHandStack();

        if (Objects.equals(Registries.ITEM.getId(stack.getItem()).toString(), "minecraft:air")) {
            Text msg = Text.translatable("commands.enchant.failed.itemless", player.getCustomName()).formatted(Formatting.RED);
            player.sendMessage(msg, false);
            return null;
        }

        ci.cancel();
        String[] parts = message.split(Pattern.quote(pattern), 2);
        MutableText beforeText = Text.literal(parts[0]);
        MutableText afterText = Text.literal(parts[1]);
        Text hoverableText = getStackText(stack);

        // Build chat message manually so no <PlayerName> brackets
        MutableText nameText = Text.literal(player.getName().getString())
                .append(Text.literal(": ").formatted(Formatting.WHITE));

        return nameText.append(beforeText).append(hoverableText).append(afterText);
    }

    public static Text getStackText(ItemStack stack) {
        // Base item name
        MutableText mutableText = Text.translatable("").append("").append(stack.getName());

        // Add count if more than 1, with colored "×" and number
        if (stack.getCount() > 1) {
            mutableText
                    .append(" ")
                    .append(Text.literal("×").formatted(Formatting.YELLOW))
                    .append(Text.literal(String.valueOf(stack.getCount())).formatted(Formatting.AQUA));
        }

        // Italics if custom name
        if (stack.hasCustomName()) {
            mutableText.formatted(Formatting.ITALIC);
        }

        // Wrap in brackets
        MutableText mutableText2 = Texts.bracketed(mutableText);

        // Apply rarity color + hover
        if (!stack.isEmpty()) {
            mutableText2.formatted(stack.getRarity().formatting)
                    .styled(style -> style.withHoverEvent(new HoverEvent(
                            HoverEvent.Action.SHOW_ITEM,
                            new HoverEvent.ItemStackContent(stack)
                    )));
        }

        return mutableText2;
    }
}
