package net.no.tnt.griefing.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.world.GameRules;

import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.no.tnt.griefing.NoTNTGriefing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TntMinecartEntity.class)
public abstract class TntMinecartEntityMixin extends Entity {
    public TntMinecartEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyArg(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"), index = 5)
    private Explosion.DestructionType modifyDestructionType(Explosion.DestructionType destructionType) {
        GameRules gameRules = this.world.getGameRules();
        if (!gameRules.getBoolean(NoTNTGriefing.TNT_GRIEFING)) {
            return Explosion.DestructionType.NONE;
        }
        return Explosion.DestructionType.BREAK;
    }
}
