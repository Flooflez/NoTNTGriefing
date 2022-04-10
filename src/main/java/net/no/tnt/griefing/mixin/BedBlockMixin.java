package net.no.tnt.griefing.mixin;

import net.minecraft.block.BedBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.no.tnt.griefing.NoTNTGriefing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BedBlock.class)
public class BedBlockMixin {

    @Redirect(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
    private Explosion injected(World instance, Entity entity, DamageSource damageSource, ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, Explosion.DestructionType destructionType) {
        if(!instance.getGameRules().getBoolean(NoTNTGriefing.BED_GRIEFING)){
            return instance.createExplosion(null, DamageSource.badRespawnPoint(), null, x + 0.5D, y + 0.5D, z + 0.5D, 5.0F, false, Explosion.DestructionType.NONE);
        }
        return instance.createExplosion(null, DamageSource.badRespawnPoint(), null, x + 0.5D, y + 0.5D, z + 0.5D, 5.0F, true, Explosion.DestructionType.DESTROY);
    }
}
