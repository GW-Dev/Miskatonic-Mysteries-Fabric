package com.miskatonicmysteries.common.feature.spell.medium;

import com.miskatonicmysteries.common.feature.spell.SpellEffect;
import com.miskatonicmysteries.common.feature.spell.SpellMedium;
import com.miskatonicmysteries.common.lib.Constants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SelfSpellMedium extends SpellMedium {
    public SelfSpellMedium() {
        super(new Identifier(Constants.MOD_ID, "self"));
    }

    @Override
    public boolean cast(World world, LivingEntity caster, SpellEffect effect, int intensity) {
        return !caster.isDead() && effect.effect(world, caster, caster, caster.getPos(), this, intensity, caster);
    }
}
