package top.offsetmonkey538.betterfireaspect.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.monkeylib538.utils.EnchantmentUtils;
import top.offsetmonkey538.monkeylib538.utils.EntityUtils;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @SuppressWarnings("UnreachableCode")
    @WrapOperation(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            )
    )
    private boolean betterfireaspect$applyFireAspectOnSweepingEdgeTargets(LivingEntity sweepingTarget, DamageSource source, float amount, Operation<Boolean> original) {
        final PlayerEntity self = (PlayerEntity) (Object) this;
        final int fireAspectLevel = EnchantmentUtils.INSTANCE.getLevel("fire_aspect", self.getWorld(), self.getMainHandStack());
        if (fireAspectLevel == 0) return original.call(sweepingTarget, source, amount);

        EntityUtils.INSTANCE.setOnFireFor(sweepingTarget, 4 * fireAspectLevel);

        return original.call(sweepingTarget, source, amount);
    }
}
