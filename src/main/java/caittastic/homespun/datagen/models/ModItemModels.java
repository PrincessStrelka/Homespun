package caittastic.homespun.datagen.models;

import caittastic.homespun.Homespun;
import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModels extends ItemModelProvider {

    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Homespun.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //------------------------------------- items -------------------------------------//
        simpleFlatItemModel(ModItems.IRONBERRIES);
        simpleFlatItemModel(ModItems.TINY_IRON_DUST);

        //------------------------------------- blocks -------------------------------------//
        /*     ironwood     */
        flatBlockModel(ModBlocks.IRONWOOD_SAPLING, "block/ironwood_sapling"); //IRONWOOD_SAPLING
        simpleExistingBlockParent(ModBlocks.IRONWOOD_LEAVES); //IRONWOOD_LEAVES
        simpleExistingBlockParent(ModBlocks.IRONWOOD_LOG); //IRONWOOD_LOG
        simpleExistingBlockParent(ModBlocks.STRIPPED_IRONWOOD_LOG); //STRIPPED_IRONWOOD_LOG
        simpleExistingBlockParent(ModBlocks.IRONWOOD_WOOD); //IRONWOOD_WOOD
        simpleExistingBlockParent(ModBlocks.STRIPPED_IRONWOOD_WOOD); //STRIPPED_IRONWOOD_WOOD
        simpleExistingBlockParent(ModBlocks.IRONWOOD_PLANKS); //IRONWOOD_PLANKS
        simpleExistingBlockParent(ModBlocks.IRONWOOD_STAIRS); //IRONWOOD_STAIRS
        simpleExistingBlockParent(ModBlocks.IRONWOOD_SLAB); //IRONWOOD_SLAB
        flatBlockModel(ModBlocks.IRONWOOD_DOOR, "item/ironwood_door"); //IRONWOOD_DOOR
        shortExistingBlockParent(ModBlocks.IRONWOOD_TRAPDOOR, "block/ironwood_trapdoor_bottom"); //IRONWOOD_TRAPDOOR
        shortExistingBlockParent(ModBlocks.IRONWOOD_FENCE, "item/ironwood_fence_inventory"); //IRONWOOD_FENCE
        simpleExistingBlockParent(ModBlocks.IRONWOOD_FENCE_GATE); //IRONWOOD_FENCE_GATE
        shortExistingBlockParent(ModBlocks.IRONWOOD_BUTTON, "item/ironwood_button_inventory"); //IRONWOOD_BUTTON
        simpleExistingBlockParent(ModBlocks.IRONWOOD_PRESSURE_PLATE); //IRONWOOD_PRESSURE_PLATE
    }

    //------------------------------------- methods -------------------------------------//
    private void simpleFlatItemModel(RegistryObject<Item> itemToModel) {
        ResourceLocation resourceLocation = new ResourceLocation(Homespun.MOD_ID, "item/" + itemToModel.getId().getPath());
        singleTexture(itemToModel.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", resourceLocation);
    }

    private void flatBlockModel(RegistryObject<Block> blockToModel, String registryLocation) {
        singleTexture(blockToModel.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc(registryLocation));
    }

    private ItemModelBuilder shortExistingBlockParent(RegistryObject<Block> blockToModel, String registryLocation) {
        return withExistingParent(blockToModel.getId().getPath(), modLoc(registryLocation));
    }

    private ItemModelBuilder simpleExistingBlockParent(RegistryObject<Block> blockToModel) {
        String registryLocation = "block/" + blockToModel.getId().getPath();
        return shortExistingBlockParent(blockToModel, registryLocation);
    }
}

