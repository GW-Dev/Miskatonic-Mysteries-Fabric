package com.miskatonicmysteries.common.entity.ai;

import com.miskatonicmysteries.common.entity.HasturCultistEntity;
import com.miskatonicmysteries.common.feature.spell.Spell;
import com.miskatonicmysteries.common.feature.spell.SpellEffect;
import com.miskatonicmysteries.common.feature.spell.SpellMedium;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffects;

import java.util.EnumSet;

public class SpellCastGoal<T extends HasturCultistEntity> extends Goal {
    private T caster;
    private int progress;
    private int castTime;
    public SpellCastGoal(T caster) {
        this.caster = caster;
        this.setControls(EnumSet.of(Control.MOVE));
        progress = 0;
        castTime = 100;
    }


    @Override
    public boolean canStart() {
        return caster.isAscended() && caster.getRandom().nextFloat() < 0.25F && (caster.getAttacking() != null && caster.distanceTo(caster.getAttacking()) >= 3);
    }

    @Override
    public boolean shouldContinue() {
        return caster.currentSpell != null && caster.isCasting() && !caster.isDead();
    }

    @Override
    public void start() {
        castTime = 60;
        SpellEffect effect = SpellEffect.HEAL;
        SpellMedium medium = SpellMedium.GROUP;
        int intensity = 2 + caster.getRandom().nextInt(2);
        if (caster.getAttacking() != null) {
            if (caster.getAttacking().distanceTo(caster) < 6) {
                effect = SpellEffect.KNOCKBACK;
                medium = SpellMedium.MOB_TARGET;
                intensity++;
            }
            if (caster.getAttacking().distanceTo(caster) > 10) {
                effect = SpellEffect.DAMAGE;
                medium = SpellMedium.BOLT;
                intensity++;
            }
        } else if (!caster.hasStatusEffect(StatusEffects.RESISTANCE)) {
            effect = SpellEffect.RESISTANCE;
        }
        caster.currentSpell = new Spell(medium, effect, intensity);
        caster.setCastTime(castTime);
    }

    @Override
    public void stop() {
        progress = 0;
        caster.setCastTime(0);
    }

    @Override
    public void tick() {
        if (caster.getAttacking() != null && caster.currentSpell != null && caster.currentSpell.medium != SpellMedium.GROUP) {
            caster.lookAtEntity(caster.getAttacking(), 40, 40);
        }
        progress++;
        if (++progress >= castTime && caster.currentSpell != null) {
            caster.currentSpell.cast(caster);
            stop();
        }
    }
}
