package com.chimericdream.minekea.block.building;

import com.chimericdream.lib.blocks.BlockDataGenerator;
import com.chimericdream.lib.blocks.RegisterableBlock;
import com.chimericdream.lib.fabric.blocks.FabricBlockDataGenerator;
import com.chimericdream.minekea.block.building.beams.Beams;
import com.chimericdream.minekea.block.building.compressed.CompressedBlocks;
import com.chimericdream.minekea.block.building.covers.Covers;
import com.chimericdream.minekea.block.building.dyed.DyedBlocks;
import com.chimericdream.minekea.block.building.framed.FramedBlocks;
import com.chimericdream.minekea.block.building.general.BasaltBricksBlock;
import com.chimericdream.minekea.block.building.general.ChiseledBasaltBricksBlock;
import com.chimericdream.minekea.block.building.general.CrackedBasaltBricksBlock;
import com.chimericdream.minekea.block.building.general.CrimsonBasaltBricksBlock;
import com.chimericdream.minekea.block.building.general.MossyBasaltBricksBlock;
import com.chimericdream.minekea.block.building.general.WarpedBasaltBricksBlock;
import com.chimericdream.minekea.block.building.general.WarpedNetherBricksBlock;
import com.chimericdream.minekea.block.building.general.WaxBlock;
import com.chimericdream.minekea.block.building.slabs.Slabs;
import com.chimericdream.minekea.block.building.stairs.Stairs;
import com.chimericdream.minekea.block.building.storage.StorageBlocks;
import com.chimericdream.minekea.block.building.walls.Walls;
import com.chimericdream.minekea.util.MinekeaBlockCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BuildingBlocks implements MinekeaBlockCategory {
    public static final BasaltBricksBlock BASALT_BRICKS_BLOCK;
    public static final ChiseledBasaltBricksBlock CHISELED_BASALT_BRICKS_BLOCK;
    public static final CrackedBasaltBricksBlock CRACKED_BASALT_BRICKS_BLOCK;
    public static final CrimsonBasaltBricksBlock CRIMSON_BASALT_BRICKS_BLOCK;
    public static final MossyBasaltBricksBlock MOSSY_BASALT_BRICKS_BLOCK;
    public static final WarpedBasaltBricksBlock WARPED_BASALT_BRICKS_BLOCK;
    public static final WarpedNetherBricksBlock WARPED_NETHER_BRICKS_BLOCK;

    public static final Beams BEAMS;
    public static final CompressedBlocks COMPRESSED_BLOCKS;
    public static final Covers COVERS;
    public static final DyedBlocks DYED_BLOCKS;
    public static final FramedBlocks FRAMED_BLOCKS;
    public static final Slabs SLABS;
    public static final Stairs STAIRS;
    public static final StorageBlocks STORAGE_BLOCKS;
    public static final Walls WALLS;
    public static final Map<String, Block> WAX_BLOCKS = new LinkedHashMap<>();

    private static final List<Block> BLOCKS = new ArrayList<>();
    private static final List<MinekeaBlockCategory> BLOCK_GROUPS = new ArrayList<>();

    static {
        BASALT_BRICKS_BLOCK = new BasaltBricksBlock();
        CHISELED_BASALT_BRICKS_BLOCK = new ChiseledBasaltBricksBlock();
        CRACKED_BASALT_BRICKS_BLOCK = new CrackedBasaltBricksBlock();
        CRIMSON_BASALT_BRICKS_BLOCK = new CrimsonBasaltBricksBlock();
        MOSSY_BASALT_BRICKS_BLOCK = new MossyBasaltBricksBlock();
        WARPED_BASALT_BRICKS_BLOCK = new WarpedBasaltBricksBlock();
        WARPED_NETHER_BRICKS_BLOCK = new WarpedNetherBricksBlock();

        BLOCKS.addAll(List.of(BASALT_BRICKS_BLOCK, CHISELED_BASALT_BRICKS_BLOCK));

        BLOCKS.addAll(List.of(
            CRACKED_BASALT_BRICKS_BLOCK,
            CRIMSON_BASALT_BRICKS_BLOCK,
            MOSSY_BASALT_BRICKS_BLOCK,
            WARPED_BASALT_BRICKS_BLOCK,
            WARPED_NETHER_BRICKS_BLOCK
        ));

        BEAMS = new Beams();
        BLOCK_GROUPS.add(BEAMS);

        COMPRESSED_BLOCKS = new CompressedBlocks();
        BLOCK_GROUPS.add(COMPRESSED_BLOCKS);

        COVERS = new Covers();
        BLOCK_GROUPS.add(COVERS);

        DYED_BLOCKS = new DyedBlocks();
        BLOCK_GROUPS.add(DYED_BLOCKS);

        FRAMED_BLOCKS = new FramedBlocks();
        BLOCK_GROUPS.add(FRAMED_BLOCKS);

        SLABS = new Slabs();
        BLOCK_GROUPS.add(SLABS);

        STAIRS = new Stairs();
        BLOCK_GROUPS.add(STAIRS);

        STORAGE_BLOCKS = new StorageBlocks();
        BLOCK_GROUPS.add(STORAGE_BLOCKS);

        WALLS = new Walls();
        BLOCK_GROUPS.add(WALLS);

        WAX_BLOCKS.put("plain", new WaxBlock("plain"));
        WAX_BLOCKS.put("white", new WaxBlock("white"));
        WAX_BLOCKS.put("light_gray", new WaxBlock("light_gray"));
        WAX_BLOCKS.put("gray", new WaxBlock("gray"));
        WAX_BLOCKS.put("black", new WaxBlock("black"));
        WAX_BLOCKS.put("brown", new WaxBlock("brown"));
        WAX_BLOCKS.put("red", new WaxBlock("red"));
        WAX_BLOCKS.put("orange", new WaxBlock("orange"));
        WAX_BLOCKS.put("yellow", new WaxBlock("yellow"));
        WAX_BLOCKS.put("lime", new WaxBlock("lime"));
        WAX_BLOCKS.put("green", new WaxBlock("green"));
        WAX_BLOCKS.put("cyan", new WaxBlock("cyan"));
        WAX_BLOCKS.put("light_blue", new WaxBlock("light_blue"));
        WAX_BLOCKS.put("blue", new WaxBlock("blue"));
        WAX_BLOCKS.put("purple", new WaxBlock("purple"));
        WAX_BLOCKS.put("magenta", new WaxBlock("magenta"));
        WAX_BLOCKS.put("pink", new WaxBlock("pink"));

        BLOCKS.addAll(WAX_BLOCKS.values());
    }

    @Environment(EnvType.CLIENT)
    public void initializeClient() {
        BLOCK_GROUPS.forEach(MinekeaBlockCategory::initializeClient);
    }

    @Override
    public void registerBlocks() {
        BLOCKS.forEach(block -> ((RegisterableBlock) block).register());
        BLOCK_GROUPS.forEach(MinekeaBlockCategory::registerBlocks);
    }

    @Override
    public void registerBlockEntities() {
        BLOCK_GROUPS.forEach(MinekeaBlockCategory::registerBlockEntities);
    }

    @Override
    public void registerEntities() {
        BLOCK_GROUPS.forEach(MinekeaBlockCategory::registerEntities);
    }

    @Override
    public void configureBlockTags(RegistryWrapper.WrapperLookup registryLookup, Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> getBuilder) {
        BLOCKS.forEach(block -> ((FabricBlockDataGenerator) block).configureBlockTags(registryLookup, getBuilder));
        BLOCK_GROUPS.forEach(group -> group.configureBlockTags(registryLookup, getBuilder));
    }

    @Override
    public void configureItemTags(RegistryWrapper.WrapperLookup registryLookup, Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> getBuilder) {
        BLOCKS.forEach(block -> ((FabricBlockDataGenerator) block).configureItemTags(registryLookup, getBuilder));
        BLOCK_GROUPS.forEach(group -> group.configureItemTags(registryLookup, getBuilder));
    }

    @Override
    public void configureRecipes(RecipeExporter exporter) {
        BLOCKS.forEach(block -> ((BlockDataGenerator) block).configureRecipes(exporter));
        BLOCK_GROUPS.forEach(group -> group.configureRecipes(exporter));
    }

    @Override
    public void configureBlockLootTables(RegistryWrapper.WrapperLookup registryLookup, BlockLootTableGenerator generator) {
        BLOCKS.forEach(block -> ((BlockDataGenerator) block).configureBlockLootTables(registryLookup, generator));
        BLOCK_GROUPS.forEach(group -> group.configureBlockLootTables(registryLookup, generator));
    }

    @Override
    public void configureTranslations(RegistryWrapper.WrapperLookup registryLookup, FabricLanguageProvider.TranslationBuilder translationBuilder) {
        BLOCKS.forEach(block -> ((FabricBlockDataGenerator) block).configureTranslations(registryLookup, translationBuilder));
        BLOCK_GROUPS.forEach(group -> group.configureTranslations(registryLookup, translationBuilder));
    }

    @Override
    public void configureBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BLOCKS.forEach(block -> ((BlockDataGenerator) block).configureBlockStateModels(blockStateModelGenerator));
        BLOCK_GROUPS.forEach(group -> group.configureBlockStateModels(blockStateModelGenerator));
    }

    @Override
    public void configureItemModels(ItemModelGenerator itemModelGenerator) {
        BLOCKS.forEach(block -> ((BlockDataGenerator) block).configureItemModels(itemModelGenerator));
        BLOCK_GROUPS.forEach(group -> group.configureItemModels(itemModelGenerator));
    }

    @Override
    public void generateTextures() {
        BLOCKS.forEach(block -> ((BlockDataGenerator) block).generateTextures());
        BLOCK_GROUPS.forEach(MinekeaBlockCategory::generateTextures);
    }
}
