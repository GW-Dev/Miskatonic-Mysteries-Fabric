package com.miskatonicmysteries.common.feature.spell.effect;

import com.miskatonicmysteries.common.feature.spell.SpellEffect;
import com.miskatonicmysteries.common.feature.spell.SpellMedium;
import com.miskatonicmysteries.common.lib.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class KnockBackSpellEffect extends SpellEffect {
    public KnockBackSpellEffect() {
        super(new Identifier(Constants.MOD_ID, "knockback"), null, 0xDDDDDD);
    }

    @Override
    public boolean effect(World world, LivingEntity caster, @Nullable Entity target, @Nullable Vec3d pos, SpellMedium medium, int intensity) {
        if (world.isClient && target != null) {
            spawnParticleEffectsOnTarget(this, target);
        }
        applyKnockBack(Math.min(intensity + 1, 20) * 0.75F, caster, target);
        return true;
    }

    private void applyKnockBack(float strength, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            strength = (float) ((double) strength * (1.0D - ((LivingEntity) target).getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)));
        }
        if (strength > 0.0F) {
            target.velocityDirty = true;
            Vec3d speed = target.getVelocity();
            Vec3d direction = (new Vec3d(
                    MathHelper.sin(caster.yaw * 0.017453292F), Math.sin(caster.pitch * 0.017453292F), (-MathHelper.cos(caster.yaw * 0.017453292F))
            ).normalize().multiply(strength));

            target.setVelocity(speed.x / 2.0D - direction.x, speed.y / 2.0D - direction.y, speed.z / 2.0D - direction.z);
        }
    }

    @Override
    public int getMaxDistance(LivingEntity caster) {
        return 48;
    }
}
