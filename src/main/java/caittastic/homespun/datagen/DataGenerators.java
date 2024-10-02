package caittastic.homespun.datagen;

import caittastic.homespun.Homespun;
import caittastic.homespun.datagen.loot.ModBlockLootTables;
import caittastic.homespun.datagen.models.ModBlockStatesAndModels;
import caittastic.homespun.datagen.models.ModItemModels;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = Homespun.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators{
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event){
    ExistingFileHelper helper = event.getExistingFileHelper();
    DataGenerator generator = event.getGenerator();
    ModBlockTags blockTags = new ModBlockTags(generator.getPackOutput(), event.getLookupProvider(), helper);

    /*     server     */
    boolean isIncludeServer = event.includeServer();
    generator.addProvider(isIncludeServer, new LootTableProvider(generator.getPackOutput(), Collections.emptySet(), List.of(
            new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)
    ), event.getLookupProvider()));

    generator.addProvider(isIncludeServer, blockTags);
    generator.addProvider(isIncludeServer, new ModRecipes(generator.getPackOutput(), event.getLookupProvider()));
    generator.addProvider(isIncludeServer, new ModItemTags(generator.getPackOutput(), event.getLookupProvider(), blockTags.contentsGetter()));
    generator.addProvider(isIncludeServer, new ModWorldGenProvider(generator.getPackOutput(), event.getLookupProvider()));

    /*     client     */
    boolean isIncludeClient = event.includeClient();
    generator.addProvider(isIncludeClient, new ModBlockStatesAndModels(generator.getPackOutput(), helper));
    generator.addProvider(isIncludeClient, new ModItemModels(generator.getPackOutput(), helper));
    generator.addProvider(isIncludeClient, new ModEnUsLanguageProvider(generator.getPackOutput(), "en_us"));
  }
}
