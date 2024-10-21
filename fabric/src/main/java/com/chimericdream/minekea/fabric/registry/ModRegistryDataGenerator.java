package com.chimericdream.minekea.fabric.registry;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

public class ModRegistryDataGenerator {
    public static void configureTranslations(RegistryWrapper.WrapperLookup registryLookup, FabricLanguageProvider.TranslationBuilder translationBuilder) {
        translationBuilder.add("item_group.minekea.blocks.building.beams", "Minekea: Beams");
        translationBuilder.add("item_group.minekea.blocks.building.compressed", "Minekea: Compressed Blocks");
        translationBuilder.add("item_group.minekea.blocks.building.covers", "Minekea: Covers");
        translationBuilder.add("item_group.minekea.blocks.building.dyed", "Minekea: Dyed Blocks");
        translationBuilder.add("item_group.minekea.blocks.furniture", "Minekea: Furniture");
    }
}
