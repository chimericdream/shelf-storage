package com.chimericdream.minekea.block.furniture.seating;

import com.chimericdream.lib.blocks.BlockConfig;
import com.chimericdream.lib.blocks.BlockDataGenerator;
import com.chimericdream.lib.blocks.RegisterableBlock;
import com.chimericdream.lib.entities.SimpleSeatEntity;
import com.chimericdream.lib.fabric.blocks.FabricBlockDataGenerator;
import com.chimericdream.lib.fabric.blocks.FabricItemGroupEventHelpers;
import com.chimericdream.lib.fabric.registries.FabricRegistryHelpers;
import com.chimericdream.lib.registries.RegistryHelpers;
import com.chimericdream.lib.util.ModConfigurable;
import com.chimericdream.minekea.ModInfo;
import com.chimericdream.minekea.util.MinekeaTextures;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.chimericdream.minekea.item.MinekeaItemGroups.FURNITURE_ITEM_GROUP_KEY;

public class GenericStool extends Block implements BlockDataGenerator, FabricBlockDataGenerator, ModConfigurable, RegisterableBlock, Waterloggable {
    protected static final Model STOOL_MODEL = new Model(
        Optional.of(Identifier.of(ModInfo.MOD_ID, "block/furniture/seating/stool")),
        Optional.empty(),
        MinekeaTextures.LOG,
        MinekeaTextures.PLANKS
    );

    public final Identifier BLOCK_ID;
    public final BlockConfig config;

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape SEAT_SHAPE;
    private static final VoxelShape[] LEG_SHAPES;

    static {
        SEAT_SHAPE = Block.createCuboidShape(3.0, 6.0, 3.0, 13.0, 8.0, 13.0);
        LEG_SHAPES = new VoxelShape[]{
            Block.createCuboidShape(4.0, 0.0, 4.0, 6.0, 6.0, 6.0), // north-west
            Block.createCuboidShape(10.0, 0.0, 4.0, 12.0, 6.0, 6.0), // north-east
            Block.createCuboidShape(10.0, 0.0, 10.0, 12.0, 6.0, 12.0), // south-east
            Block.createCuboidShape(4.0, 0.0, 10.0, 6.0, 6.0, 12.0) // south-west
        };
    }

    public GenericStool(BlockConfig config) {
        super(config.getBaseSettings());

        this.setDefaultState(
            this.stateManager.getDefaultState()
                .with(WATERLOGGED, false)
        );

        BLOCK_ID = Identifier.of(ModInfo.MOD_ID, String.format("furniture/seating/stools/%s", config.getMaterial()));
        this.config = config;
    }

    public BlockConfig getConfig() {
        return config;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public void register() {
        RegistryHelpers.registerBlockWithItem(this, BLOCK_ID);
        FabricItemGroupEventHelpers.addBlockToItemGroup(this, FURNITURE_ITEM_GROUP_KEY);

        if (config.isFlammable()) {
            FabricRegistryHelpers.registerFlammableBlock(this);
        }
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        List<SimpleSeatEntity> seats = world.getEntitiesByClass(SimpleSeatEntity.class, new Box(pos), (Object) -> true);

        if (!seats.isEmpty()) {
            return ActionResult.PASS;
        }

        SimpleSeatEntity seat = Seats.SEAT_ENTITY.create(world);
        // @TODO: The y-value will need to change to -1.25d when I switch from `FabricEntityTypeBuilder.create` to `EntityType.Builder.create` in `Seats.java`
        Vec3d seatPos = new Vec3d(hit.getBlockPos().getX() + 0.5d, hit.getBlockPos().getY() + 1.5d, hit.getBlockPos().getZ() + 0.5d);

        seat.updatePosition(seatPos.getX(), seatPos.getY(), seatPos.getZ());
        world.spawnEntity(seat);
        player.startRiding(seat);

        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(SEAT_SHAPE, LEG_SHAPES);
    }

    public void configureRecipes(RecipeExporter exporter) {
        Block plankIngredient = config.getIngredient();
        Block logIngredient = config.getIngredient("log");

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, this, 2)
            .pattern("PP")
            .pattern("LL")
            .input('P', plankIngredient)
            .input('L', logIngredient)
            .criterion(FabricRecipeProvider.hasItem(plankIngredient),
                FabricRecipeProvider.conditionsFromItem(plankIngredient))
            .criterion(FabricRecipeProvider.hasItem(logIngredient),
                FabricRecipeProvider.conditionsFromItem(logIngredient))
            .offerTo(exporter);
    }

    public void configureBlockTags(RegistryWrapper.WrapperLookup registryLookup, Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> getBuilder) {
        getBuilder.apply(BlockTags.AXE_MINEABLE).setReplace(false).add(this);
    }

    public void configureTranslations(RegistryWrapper.WrapperLookup registryLookup, FabricLanguageProvider.TranslationBuilder translationBuilder) {
        translationBuilder.add(this, String.format("%s Stool", config.getMaterialName()));
    }

    public void configureBlockLootTables(RegistryWrapper.WrapperLookup registryLookup, BlockLootTableGenerator generator) {
        generator.addDrop(this);
    }

    public void configureBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        Block plankIngredient = config.getIngredient();
        Block logIngredient = config.getIngredient("log");

        TextureMap textures = new TextureMap()
            .put(MinekeaTextures.LOG, Registries.BLOCK.getId(logIngredient).withPrefixedPath("block/"))
            .put(MinekeaTextures.PLANKS, Registries.BLOCK.getId(plankIngredient).withPrefixedPath("block/"));

        blockStateModelGenerator.registerSingleton(
            this,
            textures,
            STOOL_MODEL
        );
    }
}
