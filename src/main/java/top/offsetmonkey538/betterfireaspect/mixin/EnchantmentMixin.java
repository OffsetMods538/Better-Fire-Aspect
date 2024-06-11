package top.offsetmonkey538.betterfireaspect.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
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
        try {
            Class<?> fireAspectEnchantment = this.getClass().getClassLoader().loadClass(FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_1892"));
            return original || (fireAspectEnchantment.isInstance(this) && item.getItem() instanceof ToolItem);
        } catch (ClassNotFoundException e) {
            return original;
        }
    }
}
