package top.offsetmonkey538.betterfireaspect.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.monkeylib538.utils.EnchantmentUtils;
import top.offsetmonkey538.monkeylib538.utils.RecipeManagerUtils;

import java.util.List;

@Mixin(Block.class)
public abstract class BlockMixin {

    @ModifyReturnValue(
            method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
            at = @At("RETURN")
    )
    private static List<ItemStack> betterfireaspect$smeltSmeltableItemsIfPlayerUsesFireAspect(List<ItemStack> itemStacks, BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity) {
        if (!(entity instanceof PlayerEntity player)) return itemStacks;
        if (EnchantmentUtils.INSTANCE.getLevel("fire_aspect", world, player.getMainHandStack()) == 0) return itemStacks;
        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack currentStack = itemStacks.get(i);

            ItemStack smeltedCurrentStack = RecipeManagerUtils.INSTANCE.getSmeltingResult(currentStack, world);
            if (smeltedCurrentStack == null) continue;
            smeltedCurrentStack.setCount(currentStack.getCount());

            itemStacks.set(i, smeltedCurrentStack);
        }
        return itemStacks;
    }
}
