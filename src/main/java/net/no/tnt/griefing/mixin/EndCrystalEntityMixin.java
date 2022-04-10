package net.no.tnt.griefing.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.no.tnt.griefing.NoTNTGriefing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EndCrystalEntity.class)
public abstract class EndCrystalEntityMixin extends Entity{
    public EndCrystalEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @ModifyArg(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"), index = 5)
    private Explosion.DestructionType modifyDestructionType(Explosion.DestructionType destructionType) {
        GameRules gameRules = this.world.getGameRules();
        if (!gameRules.getBoolean(NoTNTGriefing.END_CRYSTAL_GRIEFING)) {
            return Explosion.DestructionType.NONE;
        }
        return Explosion.DestructionType.BREAK;
    }

}
