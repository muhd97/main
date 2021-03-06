package cookbuddy.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import cookbuddy.commons.core.GuiSettings;
import cookbuddy.model.recipe.Recipe;
import cookbuddy.model.recipe.attribute.Time;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Recipe> PREDICATE_SHOW_ALL_RECIPES = unused -> true;
    /** {@code Predicate} that always evaluates to false */
    Predicate<Recipe> PREDICATE_SHOW_NO_RECIPES = unused -> false;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' recipe book file path.
     */
    Path getRecipeBookFilePath();

    /**
     * Sets the user prefs' recipe book file path.
     */
    void setRecipeBookFilePath(Path recipeBookFilePath);

    /**
     * Replaces recipe book data with the data in {@code recipeBook}.
     */
    void setRecipeBook(ReadOnlyRecipeBook recipeBook);

    /** Returns the RecipeBook */
    ReadOnlyRecipeBook getRecipeBook();

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in the recipe book.
     */
    boolean hasRecipe(Recipe recipe);

    /**
     * Returns the total number of recipes in the recipe book.
     */
    long count();


    /**
     * Marks a recipe as attempted/done
     * @param recipe the recipe to be marked.
     */
    void attemptRecipe(Recipe recipe);

    /**
     * Un-Makrs a recipe as attempted/done
     * @param recipe the recipe to be un-marked.
     */
    void unAttemptRecipe(Recipe recipe);

    /**
     *
     * Favourites the recipe
     */
    void favRecipe(Recipe recipe);

    /**
     * Un-Favourites the recipe
     *
     */
    void unFavRecipe(Recipe recipe);

    /**
     * Sets a time to the recipe
     * @param recipe the recipe to be set
     * @param time the prep time of the recipe.
     */
    void setTime(Recipe recipe, Time time);

    /**
     * Deletes the given recipe.
     * The recipe must exist in the recipe book.
     */
    void deleteRecipe(Recipe target);

    /**
     * Adds the given recipe.
     * {@code recipe} must not already exist in the recipe book.
     */
    void addRecipe(Recipe recipe);

    /**
     * Replaces the given recipe {@code target} with {@code editedRecipe}.
     * {@code target} must exist in the recipe book.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the recipe book.
     */
    void setRecipe(Recipe target, Recipe editedRecipe);

    /** Returns an unmodifiable view of the filtered recipe list */
    ObservableList<Recipe> getFilteredRecipeList();

    /**
     * Updates the filter of the filtered recipe list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecipeList(Predicate<Recipe> predicate);
}
