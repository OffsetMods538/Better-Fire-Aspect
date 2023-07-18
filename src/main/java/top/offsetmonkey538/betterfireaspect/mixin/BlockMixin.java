package top.offsetmonkey538.betterfireaspect.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Unique
    private static final RecipeManager.MatchGetter<Inventory, SmeltingRecipe> betterfireaspect$CACHED_MATCH_GETTER = RecipeManager.createCachedMatchGetter(RecipeType.SMELTING);

    @ModifyReturnValue(
            method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
            at = @At("RETURN")
    )
    private static List<ItemStack> betterfireaspect$smeltSmeltableItemsIfPlayerUsesFireAspect(List<ItemStack> itemStacks, BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity) {
        if (!(entity instanceof PlayerEntity player)) return itemStacks;
        if (EnchantmentHelper.getLevel(Enchantments.FIRE_ASPECT, player.getMainHandStack()) == 0) return itemStacks;
        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack currentStack = itemStacks.get(i);

            Optional<SmeltingRecipe> recipe = betterfireaspect$CACHED_MATCH_GETTER.getFirstMatch(new SimpleInventory(currentStack), world);
            if (recipe.isEmpty()) continue;

            ItemStack smeltedCurrentStack = recipe.get().getOutput(world.getRegistryManager());
            smeltedCurrentStack.setCount(currentStack.getCount());

            itemStacks.set(i, smeltedCurrentStack);
        }
        return itemStacks;
    }
}
