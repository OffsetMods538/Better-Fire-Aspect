package top.offsetmonkey538.betterfireaspect.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            )
    )
    private void betterfireaspect$applyFireAspectOnSweepingEdgeTargets(Entity target, CallbackInfo ci, @Local(index = 20) LivingEntity sweepingTarget) {
        final int fireAspectLevel = EnchantmentHelper.getFireAspect((PlayerEntity) (Object) this);
        if (fireAspectLevel == 0) return;

        sweepingTarget.setOnFireFor(4 * fireAspectLevel);
    }
}
