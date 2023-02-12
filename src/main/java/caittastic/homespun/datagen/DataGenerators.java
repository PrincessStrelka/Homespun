package caittastic.homespun.datagen;

import caittastic.homespun.Homespun;
import caittastic.homespun.datagen.loot.ModLootTableProvider;
import caittastic.homespun.datagen.models.ModBlockStatesAndModels;
import caittastic.homespun.datagen.models.ModItemModels;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Homespun.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators{
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event){
    ExistingFileHelper helper = event.getExistingFileHelper();
    DataGenerator generator = event.getGenerator();
    ModBlockTags blockTags = new ModBlockTags(generator, helper);

    /*     server     */
    boolean isIncludeServer = event.includeServer();
    generator.addProvider(isIncludeServer, new ModLootTableProvider(generator));

    generator.addProvider(isIncludeServer, blockTags);
    generator.addProvider(isIncludeServer, new ModRecipes(generator));
    generator.addProvider(isIncludeServer, new ModItemTags(generator, blockTags, helper));

    /*     client     */
    boolean isIncludeClient = event.includeClient();
    generator.addProvider(isIncludeClient, new ModBlockStatesAndModels(generator, helper));
    generator.addProvider(isIncludeClient, new ModItemModels(generator, helper));
    generator.addProvider(isIncludeClient, new ModEnUsLanguageProvider(generator, "en_us"));
  }
}
