package com.miskatonicmysteries.mixin.entity;

import com.google.common.collect.ImmutableSet;
import com.miskatonicmysteries.api.MiskatonicMysteriesAPI;
import com.miskatonicmysteries.api.interfaces.Appeasable;
import com.miskatonicmysteries.api.interfaces.HiddenEntity;
import com.miskatonicmysteries.common.util.Constants;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity implements HiddenEntity {

	@Unique
	private static final Set<Potion> FORBIDDEN_POTIONS = ImmutableSet.of(Potions.WATER, Potions.EMPTY, Potions.MUNDANE
		, Potions.AWKWARD, Potions.THICK);
	@Unique
	private static final TrackedData<Boolean> HIDDEN = DataTracker.registerData(MobEntity.class,
		TrackedDataHandlerRegistry.BOOLEAN);

	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void initDataTracker(CallbackInfo ci) {
		this.dataTracker.startTracking(HIDDEN, false);
	}

	@Override
	public boolean isHidden() {
		return this.dataTracker.get(HIDDEN);
	}

	@Override
	public void setHidden(boolean hide) {
		this.dataTracker.set(HIDDEN, hide);
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putBoolean(Constants.NBT.HIDDEN, isHidden());
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		setHidden(nbt.getBoolean(Constants.NBT.HIDDEN));
	}

	@Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		Appeasable.of(this).ifPresent(appeasable -> {
			if ((Object) this instanceof WitchEntity) {
				if (!appeasable.isAppeased() && player.getStackInHand(hand).getItem() == Items.POTION && !FORBIDDEN_POTIONS
					.contains(PotionUtil.getPotion(player.getStackInHand(hand)))) {
					setStackInHand(Hand.MAIN_HAND, player.getStackInHand(hand).split(1));
					appeasable.setAppeasedTicks(200 + player.getRandom().nextInt(200));
					playAmbientSound();
					player.world.sendEntityStatus(this, (byte) 14);
					MiskatonicMysteriesAPI.addKnowledge(Constants.Misc.WITCH_KNOWLEDGE, player);
				}
			}
		});
	}

	@Shadow
	public abstract void playAmbientSound();
}
