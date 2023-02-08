package top.offsetmonkey538.betterfireaspect.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @ModifyReturnValue(
            method = "isAcceptableItem",
            at = @At("RETURN")
    )
    @SuppressWarnings({"ConstantConditions", "unused"})
    public boolean betterfireaspect$makeToolsAcceptableItemsForFireAspect(boolean original, ItemStack item) {
        return original || ((Object)this instanceof FireAspectEnchantment && item.getItem() instanceof ToolItem);
    }
}
