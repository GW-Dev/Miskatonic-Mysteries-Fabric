package com.miskatonicmysteries.common.registry;

import com.google.common.collect.ImmutableList;
import com.miskatonicmysteries.api.MiskatonicMysteriesAPI;
import com.miskatonicmysteries.common.MiskatonicMysteries;
import com.miskatonicmysteries.common.feature.world.biome.BiomeEffect;
import com.miskatonicmysteries.common.feature.world.biome.HasturBiomeEffect;
import com.miskatonicmysteries.common.feature.world.processor.PsychonautHouseProcessor;
import com.miskatonicmysteries.common.util.Constants;
import com.miskatonicmysteries.common.util.RegistryUtil;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.BlockStateMatchRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class MMWorld {

	public static final StructureProcessorType<PsychonautHouseProcessor> PSYCHONAUT_PROCESSOR =
		StructureProcessorType.register(Constants.MOD_ID + ":psychonaut_house", PsychonautHouseProcessor.CODEC);
	public static final StructureProcessorList ZOMBIE_PROCESSOR =
		new StructureProcessorList(Arrays.asList(new PsychonautHouseProcessor(10091940),
			new RuleStructureProcessor(ImmutableList
				.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.8F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.MOSSY_COBBLESTONE
							.getDefaultState()),
					new StructureProcessorRule(new TagMatchRuleTest(BlockTags.DOORS), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR
						.getDefaultState()),
					new StructureProcessorRule(new BlockMatchRuleTest(Blocks.TORCH), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR
						.getDefaultState()),
					new StructureProcessorRule(new BlockMatchRuleTest(Blocks.WALL_TORCH), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR
						.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.07F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB
							.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.MOSSY_COBBLESTONE, 0.07F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB
							.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.WHITE_TERRACOTTA, 0.07F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB
							.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.OAK_LOG, 0.05F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB
							.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.OAK_PLANKS, 0.1F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB
							.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.OAK_STAIRS, 0.1F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB
							.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.STRIPPED_OAK_LOG, 0.02F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB
							.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.GLASS_PANE, 0.5F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB
							.getDefaultState()), new StructureProcessorRule(new BlockStateMatchRuleTest(Blocks.GLASS_PANE
						.getDefaultState().with(PaneBlock.NORTH, true)
						.with(PaneBlock.SOUTH, true)), AlwaysTrueRuleTest.INSTANCE, Blocks.BROWN_STAINED_GLASS_PANE
						.getDefaultState().with(PaneBlock.NORTH, true)
						.with(PaneBlock.SOUTH, true)), new StructureProcessorRule(new BlockStateMatchRuleTest(Blocks.GLASS_PANE
						.getDefaultState().with(PaneBlock.EAST, true)
						.with(PaneBlock.WEST, true)), AlwaysTrueRuleTest.INSTANCE, Blocks.BROWN_STAINED_GLASS_PANE
						.getDefaultState().with(PaneBlock.EAST, true).with(PaneBlock.WEST, true))))));
	public static final StructureProcessorList NORMAL_PROCESSOR =
		new StructureProcessorList(Arrays.asList(new PsychonautHouseProcessor(12081980),
			new RuleStructureProcessor(ImmutableList
				.of(new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.1F), AlwaysTrueRuleTest.INSTANCE,
					Blocks.MOSSY_COBBLESTONE
						.getDefaultState())))));

	public static final Biome HASTUR_BIOME;
	public static final BiomeEffect HASTUR_BIOME_EFFECT = new HasturBiomeEffect();

	static {
		HASTUR_BIOME = new Biome.Builder().temperature(0.75f).precipitation(Biome.Precipitation.RAIN)
			.category(Biome.Category.NONE).depth(0).scale(0).temperatureModifier(Biome.TemperatureModifier.NONE)
			.downfall(0.3F).spawnSettings(new SpawnSettings.Builder().build())
			.generationSettings(new GenerationSettings.Builder()
				.surfaceBuilder(SurfaceBuilder.DEFAULT.withConfig(SurfaceBuilder.END_CONFIG)).build())
			.effects(new BiomeEffects.Builder()
				.fogColor(0xEFC91F).skyColor(0x000000)
				.waterColor(0x1199C6).waterFogColor(0x1199C6)
				.grassColor(0xF2C709).foliageColor(0xE58E03)
				.build())
			.build();
	}

	public static void init() {
		StructurePools.register(new StructurePool(new Identifier(Constants.MOD_ID, "village/common/hastur_cultist"),
			new Identifier("empty"),
			ImmutableList.of(Pair.of(StructurePoolElement.ofLegacySingle(Constants.MOD_ID + ":village/common" +
				"/hastur_cultist"), 1)), StructurePool.Projection.RIGID));
		StructurePools.register(new StructurePool(new Identifier(Constants.MOD_ID, "village/common" +
			"/hastur_cultist_ascended"), new Identifier("empty"),
			ImmutableList.of(Pair.of(StructurePoolElement.ofLegacySingle(Constants.MOD_ID + ":village/common" +
				"/hastur_cultist_ascended"), 1)), StructurePool.Projection.RIGID));

		RegistryUtil.register(BuiltinRegistries.BIOME, "hastur", HASTUR_BIOME);
		BuiltinRegistries.BIOME.getKey(HASTUR_BIOME)
			.ifPresent(key -> MiskatonicMysteriesAPI.associateBiomeEffect(key, HASTUR_BIOME_EFFECT));
	}

	public static StructurePool specialInject(StructurePool pool) {
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/plains/houses"), pool, Constants.MOD_ID +
				":village/plains/houses/plains_psychonaut", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.psychonautHouseWeight, MMWorld.NORMAL_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/savanna/houses"), pool, Constants.MOD_ID +
				":village/savanna/houses/savanna_psychonaut", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.psychonautHouseWeight, MMWorld.NORMAL_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/snowy/houses"), pool, Constants.MOD_ID +
				":village/snowy/houses/snowy_psychonaut", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.psychonautHouseWeight, MMWorld.NORMAL_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/desert/houses"), pool, Constants.MOD_ID +
				":village/desert/houses/desert_psychonaut", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.psychonautHouseWeight, MMWorld.NORMAL_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/taiga/houses"), pool, Constants.MOD_ID +
				":village/taiga/houses/taiga_psychonaut", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.psychonautHouseWeight, MMWorld.NORMAL_PROCESSOR);

		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/plains/zombie/houses"), pool,
			Constants.MOD_ID + ":village/plains/zombie/houses/plains_psychonaut", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.psychonautHouseWeight, MMWorld.ZOMBIE_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/savanna/zombie/houses"), pool,
			Constants.MOD_ID + ":village/savanna/zombie/houses/savanna_psychonaut", StructurePool.Projection.RIGID
			, MiskatonicMysteries.config.world.psychonautHouseWeight, MMWorld.ZOMBIE_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/snowy/zombie/houses"), pool,
			Constants.MOD_ID + ":village/snowy/zombie/houses/snowy_psychonaut", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.psychonautHouseWeight, MMWorld.ZOMBIE_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/desert/zombie/houses"), pool,
			Constants.MOD_ID + ":village/desert/zombie/houses/desert_psychonaut", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.psychonautHouseWeight, MMWorld.ZOMBIE_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/taiga/zombie/houses"), pool,
			Constants.MOD_ID + ":village/taiga/zombie/houses/taiga_psychonaut", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.psychonautHouseWeight, MMWorld.ZOMBIE_PROCESSOR);

		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/plains/town_centers"), pool,
			Constants.MOD_ID + ":village/plains/town_centers/plains_shrine", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.hasturShrineWeight, MMWorld.NORMAL_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/savanna/town_centers"), pool,
			Constants.MOD_ID + ":village/savanna/town_centers/savanna_shrine", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.hasturShrineWeight, MMWorld.NORMAL_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/snowy/town_centers"), pool,
			Constants.MOD_ID + ":village/snowy/town_centers/snowy_shrine", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.hasturShrineWeight, MMWorld.NORMAL_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/taiga/town_centers"), pool,
			Constants.MOD_ID + ":village/taiga/town_centers/taiga_shrine", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.hasturShrineWeight, MMWorld.NORMAL_PROCESSOR);
		pool = RegistryUtil.tryAddElementToPool(new Identifier("village/desert/town_centers"), pool,
			Constants.MOD_ID + ":village/desert/town_centers/desert_shrine", StructurePool.Projection.RIGID,
			MiskatonicMysteries.config.world.hasturShrineWeight, MMWorld.NORMAL_PROCESSOR);
		return pool;
	}
}
