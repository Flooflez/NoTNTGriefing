package net.no.tnt.griefing.mixin;

import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.no.tnt.griefing.NoTNTGriefing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RespawnAnchorBlock.class)
public class RespawnAnchorBlockMixin {

    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;Lnet/minecraft/util/math/Vec3d;FZLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"))
    private Explosion injected(World world, Entity entity, DamageSource damageSource, ExplosionBehavior behavior, Vec3d vec3d, float power, boolean createFire, World.ExplosionSourceType explosionSourceType) {
        if(!world.getGameRules().getBoolean(NoTNTGriefing.RESPAWN_ANCHOR_GRIEFING)){
            return world.createExplosion(null, DamageSource.badRespawnPoint(vec3d), null, vec3d, 5.0F, false, World.ExplosionSourceType.NONE);
        }
        return world.createExplosion(null, DamageSource.badRespawnPoint(vec3d), null, vec3d, 5.0F, true, World.ExplosionSourceType.BLOCK);
    }
}
