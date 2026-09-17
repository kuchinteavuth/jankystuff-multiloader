package com.inteavuthkuch.jankystuff.data;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "JankyStuff Recipe Provider";
        }
    }
    private String getItemNameForMod(ItemLike item, String extra){
        if (extra != null) {
            return String.format("%s:%s_%s", Constants.MOD_ID, getItemName(item), extra);
        }
        return String.format("%s:%s", Constants.MOD_ID, getItemName(item));
    }

    private void pickaxeLikeRecipe(ItemLike topLeft, ItemLike topCenter, ItemLike topRight, ItemLike result, RecipeCategory category) {
        shaped(category, result)
                .pattern("LCR")
                .pattern(" S ")
                .pattern(" S ")
                .define('L', topLeft)
                .define('C', topCenter)
                .define('R', topRight)
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
                .save(output);
    }

    @Override
    protected void buildRecipes() {

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(ModItems.DIAMOND_PAXEL.get()),
                        this.tag(ItemTags.NETHERITE_TOOL_MATERIALS),
                        RecipeCategory.TOOLS,
                        ModItems.NETHERITE_PAXEL.get()
                )
                .unlocks("has_netherite_ingot", this.has(ItemTags.NETHERITE_TOOL_MATERIALS))
                .save(this.output, getItemNameForMod(ModItems.NETHERITE_PAXEL.get(), "smithing"));

        pickaxeLikeRecipe(Items.IRON_AXE, Items.IRON_PICKAXE, Items.IRON_SHOVEL, ModItems.IRON_PAXEL.get(), RecipeCategory.TOOLS);
        pickaxeLikeRecipe(Items.DIAMOND_AXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_SHOVEL, ModItems.DIAMOND_PAXEL.get(), RecipeCategory.TOOLS);
    }
}
