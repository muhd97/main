package cookbuddy.model.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cookbuddy.model.ReadOnlyRecipeBook;
import cookbuddy.model.RecipeBook;
import cookbuddy.model.recipe.ImagePath;
import cookbuddy.model.recipe.Recipe;
import cookbuddy.model.recipe.attribute.Calorie;
import cookbuddy.model.recipe.attribute.Difficulty;
import cookbuddy.model.recipe.attribute.Ingredient;
import cookbuddy.model.recipe.attribute.IngredientList;
import cookbuddy.model.recipe.attribute.Instruction;
import cookbuddy.model.recipe.attribute.InstructionList;
import cookbuddy.model.recipe.attribute.Name;
import cookbuddy.model.recipe.attribute.Rating;
import cookbuddy.model.recipe.attribute.Serving;
import cookbuddy.model.recipe.attribute.Tag;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleDataUtil {

    /**
     * Returns the absolute path of an image from its given relative path.
     *
     * @param maybeRelative relative path of an image.
     * @return the absolute path of an image.
     */
    public static String toAbsolutePath(String maybeRelative) {
        Path path = Paths.get(maybeRelative);
        Path effectivePath = path;
        if (!path.isAbsolute()) {
            Path base = Paths.get("");
            effectivePath = base.resolve(path).toAbsolutePath();
        }
        return effectivePath.normalize().toString();
    }

    public static Recipe[] getSampleRecipes() {
        Name name1 = new Name("Ham Sandwich");
        IngredientList ingList1 = new IngredientList(
            List.of(new Ingredient("bread, 2 slices"), new Ingredient("ham, 1 slice")));
        InstructionList insList1 = new InstructionList(
            List.of(new Instruction("put ham between bread"), new Instruction("serve on plate")));
        ImagePath imagePath1 = new ImagePath(toAbsolutePath("src/main/resources/recipes/hamsandwich_recipe.jpg"));
        Calorie calorie1 = new Calorie("169");
        Serving serving1 = new Serving(3);
        Rating rating1 = new Rating(2);
        Difficulty difficulty1 = new Difficulty(3);
        Set<Tag> tagSet1 = getTagSet("breakfast", "lunch");

        Recipe recipe1 = new Recipe(name1, ingList1, insList1, imagePath1, calorie1, serving1, rating1, difficulty1,
            tagSet1);

        Name name2 = new Name("Idiot Sandwich");
        IngredientList ingList2 = new IngredientList(List.of(new Ingredient("bread, 2 slices")));
        InstructionList insList2 = new InstructionList(List.of(new Instruction("put bread to opposite sides of head"),
            new Instruction("Yell 'I am an idiot sandwich!'")));
        ImagePath imagePath2 = new ImagePath(toAbsolutePath("src/main/resources/recipes/idiotsandwich_recipe.jpg"));
        Calorie calorie2 = new Calorie("0");
        Serving serving2 = new Serving(2);
        Rating rating2 = new Rating(4);
        Difficulty difficulty2 = new Difficulty(1);
        Set<Tag> tagSet2 = getTagSet("lunch", "dinner");

        Recipe recipe2 = new Recipe(name2, ingList2, insList2, imagePath2, calorie2, serving2, rating2, difficulty2,
            tagSet2);

        return new Recipe[] {recipe1, recipe2};
    }

    public static ReadOnlyRecipeBook getSampleRecipeBook() {
        RecipeBook sampleAb = new RecipeBook();
        for (Recipe sampleRecipe : getSampleRecipes()) {
            sampleAb.addRecipe(sampleRecipe);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }
}
