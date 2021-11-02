package com.miskatonicmysteries.common.feature.world;

import static com.miskatonicmysteries.common.util.Constants.NBT.WARDING_MARKS;

import com.miskatonicmysteries.common.util.Constants;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;

public class MMDimensionalWorldState extends PersistentState {

	private final Set<BlockPos> wardingMarks = new HashSet<>();

	public static MMDimensionalWorldState fromNbt(NbtCompound tag) {
		MMDimensionalWorldState state = new MMDimensionalWorldState();
		NbtList wardingMarksList = (NbtList) tag.get(WARDING_MARKS);
		if (wardingMarksList != null) {
			for (NbtElement blockTag : wardingMarksList) {
				state.wardingMarks.add(NbtHelper.toBlockPos((NbtCompound) blockTag));
			}
		}
		return state;
	}

	public static MMDimensionalWorldState get(ServerWorld world) {
		return world.getPersistentStateManager()
			.getOrCreate(MMDimensionalWorldState::fromNbt, MMDimensionalWorldState::new, Constants.MOD_ID + "_dimensional");
	}

	public void addMark(BlockPos markPos) {
		wardingMarks.add(markPos);
		markDirty();
	}

	public void removeMark(BlockPos markPos) {
		wardingMarks.remove(markPos);
		markDirty();
	}

	public boolean isMarkNear(BlockPos pos, int radius) {
		for (BlockPos wardingMark : wardingMarks) {
			if (wardingMark.isWithinDistance(pos, radius)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public NbtCompound writeNbt(NbtCompound tag) {
		NbtList wardingMarksList = new NbtList();
		for (BlockPos wardingMark : wardingMarks) {
			wardingMarksList.add(NbtHelper.fromBlockPos(wardingMark));
		}
		tag.put(WARDING_MARKS, wardingMarksList);

		return tag;
	}
}
