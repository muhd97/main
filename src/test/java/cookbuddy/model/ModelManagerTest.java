package cookbuddy.model;

import static cookbuddy.model.Model.PREDICATE_SHOW_ALL_RECIPES;
import static cookbuddy.testutil.Assert.assertThrows;
import static cookbuddy.testutil.TypicalRecipes.EGGS_ON_TOAST;
import static cookbuddy.testutil.TypicalRecipes.HAM_SANDWICH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import cookbuddy.commons.core.GuiSettings;
import cookbuddy.model.recipe.NameContainsKeywordsPredicate;
import cookbuddy.testutil.RecipeBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new RecipeBook(), new RecipeBook(modelManager.getRecipeBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setDataFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setDataFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setRecipeBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setRecipeBookFilePath(null));
    }

    @Test
    public void setRecipeBookFilePath_validPath_setsRecipeBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setRecipeBookFilePath(path);
        assertEquals(path, modelManager.getRecipeBookFilePath());
    }

    @Test
    public void hasRecipe_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasRecipe(null));
    }

    @Test
    public void hasRecipe_recipeNotInRecipeBook_returnsFalse() {
        assertFalse(modelManager.hasRecipe(HAM_SANDWICH));
    }

    @Test
    public void hasRecipe_recipeInRecipeBook_returnsTrue() {
        modelManager.addRecipe(HAM_SANDWICH);
        assertTrue(modelManager.hasRecipe(HAM_SANDWICH));
    }

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredRecipeList().remove(0));
    }

    @Test
    public void equals() {
        RecipeBook recipeBook = new RecipeBookBuilder().withRecipe(HAM_SANDWICH).withRecipe(EGGS_ON_TOAST).build();
        RecipeBook differentAddressBook = new RecipeBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(recipeBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(recipeBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different recipeBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = HAM_SANDWICH.getName().getName().split("\\s+");
        modelManager.updateFilteredRecipeList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(recipeBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setDataFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(recipeBook, differentUserPrefs)));
    }
}
