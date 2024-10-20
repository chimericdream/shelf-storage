package com.chimericdream.minekea.block.building.general;

import com.chimericdream.lib.blocks.RegisterableBlock;
import com.chimericdream.lib.fabric.blocks.FabricBlockDataGenerator;
import com.chimericdream.lib.fabric.blocks.FabricItemGroupEventHelpers;
import com.chimericdream.lib.registries.RegistryHelpers;
import com.chimericdream.minekea.ModInfo;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemGroups;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static com.chimericdream.minekea.block.building.BuildingBlocks.BASALT_BRICKS_BLOCK;

public class ChiseledBasaltBricksBlock extends Block implements FabricBlockDataGenerator, RegisterableBlock {
    public static final Identifier BLOCK_ID = Identifier.of(ModInfo.MOD_ID, "building/general/chiseled_basalt_bricks");

    public ChiseledBasaltBricksBlock() {
        super(AbstractBlock.Settings.copy(Blocks.SMOOTH_BASALT));
    }

    public void register() {
        RegistryHelpers.registerBlockWithItem(this, BLOCK_ID);
        FabricItemGroupEventHelpers.addBlockToItemGroup(this, ItemGroups.BUILDING_BLOCKS);
    }

    public void configureBlockTags(RegistryWrapper.WrapperLookup registryLookup, Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> getBuilder) {
        getBuilder.apply(BlockTags.PICKAXE_MINEABLE)
            .setReplace(false)
            .add(this);
    }

    public void configureRecipes(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, this, 4)
            .pattern("##")
            .pattern("##")
            .input('#', BASALT_BRICKS_BLOCK)
            .criterion(FabricRecipeProvider.hasItem(BASALT_BRICKS_BLOCK),
                FabricRecipeProvider.conditionsFromItem(BASALT_BRICKS_BLOCK))
            .offerTo(exporter);

        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this, BASALT_BRICKS_BLOCK, 1);
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, BASALT_BRICKS_BLOCK, this, 1);
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_BASALT, this, 1);
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this, Blocks.SMOOTH_BASALT, 1);
    }

    public void configureTranslations(RegistryWrapper.WrapperLookup registryLookup, FabricLanguageProvider.TranslationBuilder translationBuilder) {
        translationBuilder.add(this, "Chiseled Basalt Bricks");
    }

    public void configureBlockLootTables(RegistryWrapper.WrapperLookup registryLookup, BlockLootTableGenerator generator) {
        generator.addDrop(this);
    }

    public void configureBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(this);
    }
}
