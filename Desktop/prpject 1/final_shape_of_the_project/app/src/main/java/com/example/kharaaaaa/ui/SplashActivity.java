package com.example.kharaaaaa.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.kharaaaaa.R;
import com.example.kharaaaaa.adapters.DatabaseAdapter;
import com.example.kharaaaaa.models.Direction;
import com.example.kharaaaaa.models.Ingredient;
import com.example.kharaaaaa.models.Recipe;
import com.example.kharaaaaa.models.User;
import com.example.kharaaaaa.utils.UserPreferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_TO_WRITE = 1;
    private DatabaseAdapter databaseAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseAdapter = DatabaseAdapter.getInstance(this);

        if (UserPreferences.isFirstRun(this)) {
            UserPreferences.setIsFirstRun(this, false);

            databaseAdapter.addNewUser(new User("testrun",
                    "test runner", "testrun@recipeapp.com", "password"));

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_TO_WRITE);
            } else {
                loadDefaultRecipes();
                navigateToLogin();
            }
        } else {
            if (UserPreferences.isUserLoggedIn(this))
                navigateToMainPage();
            else
                navigateToLogin();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_TO_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    loadDefaultRecipes();
                else
                    Toast.makeText(this, "Permission denied to write default recipes.", Toast.LENGTH_LONG)
                            .show();

                navigateToLogin();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadDefaultRecipes() {
        try {
            loadAmericanRecipes();
            loadVeganRecipes();
            loadEuropeanRecipes();
            loadMediterraneanRecipes();
            loadAsianRecipes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadAsianRecipes() throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.asian_chicken);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "asian_chicken.PNG");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        outStream.flush();
        outStream.close();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("1 Pound thinly sliced Chicken Breasts, or boneless chicken thighs ",12,1,"g"));

        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Heat a large skillet over medium high heat and add 1 Tbs of olive oil. Add the chicken and salt and pepper. Cook chicken about 3 minutes on each side or until thermometer reads 165 degrees and brown on each side. Set chicken aside on plate"));
        directions.add(new Direction("In the skillet whisk together brown sugar, soy sauce, hoisin sauce, sweet chili sauce, ginger, red pepper flakes, garlic and lime juice. Bring to a boil over medium heat for 1-2 minutes until sauce thickens"));
        directions.add(new Direction("Add chicken back to the sauce and coat each side with the sauce. Garnish with sesame seeds and chopped green onions.\n"));
        databaseAdapter.addNewRecipe(new Recipe("Asian chicken",
                "Asian", "Tender and juicy chicken breasts that get coated in a sticky sweet asian sauce.  This meal is ready in just thirty minutes and the flavor is awesome!", ingredients, directions, file.getAbsolutePath()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadMediterraneanRecipes() throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.mediterranean_steak);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "mediterranean_steak.jpg");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("2 Tbs. extra-virgin olive oil" ,12,2,"g"));
//        ingredients.add(new Ingredient("2 Tbs. extra-virgin olive oil" ,"12",2,"g"));
//        ingredients.add(new Ingredient("2 Tbs. extra-virgin olive oil" ,"12",2,"g"));
//        ingredients.add(new Ingredient("2 Tbs. extra-virgin olive oil" ,"12",2,"g"));
//        ingredients.add(new Ingredient("2 Tbs. extra-virgin olive oil" ,"12",2,"g"));
//        ingredients.add(new Ingredient("2 Tbs. extra-virgin olive oil" ,"12",2,"g"));
        ingredients.add(new Ingredient("1-1/2- to 2-lb. flank steak, trimmed of any excess fat and membrane",12,21,"g"));
        ingredients.add(new Ingredient("1 recipe Chunky Tomato-Basil Vinaigrette",12,12,"g"));
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Mix the oil, garlic, herbs, salt, and pepper in a small bowl. Rub all over the steak and let sit for about 20 min. at room temperature. Meanwhile, heat a gas grill to medium-high (you should be able to hold your hand 2 inches above the grate for 3 to 4 seconds) or prepare a medium-hot charcoal fire"));
        directions.add(new Direction("If your grill has a hot spot, position the thicker end of the flank steak nearer the hottest part of the fire. Grill until medium rare, 12 to 15 min., turning the steak every 3 to 4 min. to ensure even cooking. The thickest part of the steak will register 135°F to 140°F on an instant-read thermometer"));
        directions.add(new Direction("Transfer the steak to a cutting board and let it rest for 3 to 5 min. Slice across the grain, portion onto dinner plates, spoon on the vinaigrette, and serve."));
        databaseAdapter.addNewRecipe(new Recipe("Mediterranean steak",
                "Mediterranean", "This steak gets a wet rub before grilling; the oil helps the other flavors spread.", ingredients, directions, file.getAbsolutePath()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadEuropeanRecipes() throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.european_lasagna);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "european_lasagna.jpg");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();


        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("5 tablespoons unsalted butter, plus 2 tablespoons for the lasagna",12,88,"g"));
//        ingredients.add(new Ingredient("4 cups whole milk at room temperature","12",12,"g"));
//        ingredients.add(new Ingredient("4 cups whole milk at room temperature","12",12,"g"));
//        ingredients.add(new Ingredient("4 cups whole milk at room temperature","12",12,"g"));
//        ingredients.add(new Ingredient("4 cups whole milk at room temperature","12",12,"g"));
//        ingredients.add(new Ingredient("4 cups whole milk at room temperature","12",12,"g"));
//        ingredients.add(new Ingredient("4 cups whole milk at room temperature","12",12,"g"));


        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("In a 2-quart pot, melt 5 tablespoons of butter over medium heat. When butter has completely melted, add the flour and whisk until smooth, about 2 minutes. Gradually add the milk, whisking constantly to prevent any lumps from forming." +
                "Spread the thin slices of your favorite salamis over the sauce"));
        directions.add(new Direction("In a saute pan, heat extra-virgin olive oil. When almost smoking, add the ground beef and season with salt and pepper. Brown meat, breaking any large lumps, until it is no longer pink." +
                "Bake at 450 degrees for 8-10 minutes"));
        directions.add(new Direction("Line a large baking sheet with aluminum foil. Place lasagna dish on top, cover and put on the middle rack of the oven and bake until top is bubbling, about 30 minutes. Remove cover and continue to bake for about 15 minutes"));
        databaseAdapter.addNewRecipe(new Recipe("European lasagna",
                "European", "Classic Italian Lasagna", ingredients, directions, file.getAbsolutePath()));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadVeganRecipes() throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.vegan_sunflower);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "vegan_sunflower.jpg");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();



        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("0.5 cup Sunflower Seeds",12,12,"g"));
//        ingredients.add(new Ingredient("0.25 cup Filtered Water","12",12,"g"));
//        ingredients.add(new Ingredient("0.25 cup Scallions","12",45,"g"));
//        ingredients.add(new Ingredient("1 tbs Miso paste","12",78,"g"));
//        ingredients.add(new Ingredient("0.25 tsp coarse celtic sea salt or to taste","12",78,"g"));

        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Place all of the ingredients in a blender and process until creamy. Done!"));
        databaseAdapter.addNewRecipe(new Recipe("Vegan sunflower",
                "Vegan", "This vegan sunflower seed cream cheese is like a french onion dip. It is rich in flavor and perfect as a sandwich spread or on crackers for some party fun!"
                , ingredients, directions, file.getAbsolutePath()));


        bm = BitmapFactory.decodeResource(getResources(), R.drawable.vegan_vegetables);
        extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        file = new File(extStorageDirectory, "vegan_vegetables.jpg");
        outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();

        List<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredient("1 medium cauliflower, quartered, cored and cut into 1-inch florets",12,78,"g"));
//        ingredients1.add(new Ingredient("2 cups (1 pint) Brussels sprouts, halved lengthwise","12",12,"g"));
//        ingredients1.add(new Ingredient("3 Tbs. olive oil","12",78,"g"));
//        ingredients1.add(new Ingredient("1 1/2 tsp. chopped fresh rosemary or 1/2 tsp. dried, crumbled","12",12,"g"));
//        ingredients1.add(new Ingredient("1/2 tsp. freshly ground pepper","12",78,"g"));

        List<Direction> directions1 = new ArrayList<>();
        directions1.add(new Direction("In large bowl, combine cauliflower and Brussels sprouts. Drizzle oil on top. Add garlic, rosemary and pepper and toss well. Cover tightly and refrigerate overnight."));
        directions1.add(new Direction("Preheat oven to 450°F. Spread vegetables in single layer on large baking sheet with sides. Sprinkle with salt. Roast until vegetables are crisp-tender and beginning to brown at edges, 15 to 20 minutes. Serve hot or at room temperature"
        ));
        databaseAdapter.addNewRecipe(new Recipe("Vegan vegetables",
                "Vegan", "Roasted Cauliflower and Brussels Sprouts", ingredients1, directions1, file.getAbsolutePath()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadAmericanRecipes() throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.american_pancakes);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "american_pancakes.jpg");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();


        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("1 cup milk",12,77,"g"));
//        ingredients.add(new Ingredient("1 large egg","12",12,"g"));
//        ingredients.add(new Ingredient("2 tbsp melted butter or vegetable oil","12",99,"g"));
//        ingredients.add(new Ingredient("1 cup all-purpose flour","12",55,"g"));
//        ingredients.add(new Ingredient("2 tsp baking powder","12",66,"g"));
//        ingredients.add(new Ingredient("2 tbsp sugar","12",22,"g"));
//        ingredients.add(new Ingredient("1 pinch of salt","12",77,"g"));
//        ingredients.add(new Ingredient("Assorting toppings such as maple syrup, fresh berries, etc.","12",88,"g"));

        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Preheat the oven to 200F (90C), with a heatproof platter ready to keep cooked pancakes warm in the oven."));
        directions.add(new Direction("In a small bowl, whisk together flour, sugar, baking powder, and salt. Set aside."));
        directions.add(new Direction("Pour the dry ingredients to the milk mixture, and stir (do not overmix)"));
        directions.add(new Direction("Heat a large skillet or griddle over medium heat, and coat generously with vegetable oil"));
        directions.add(new Direction("For each pancake, spoon 2 or 3 tablespoons of batter onto skillet. Cook until the surface of pancakes have some bubbles, about 1 minute. Flip carefully with a thin spatula, and cook until brown on the underside, 1 to 2 minutes more."));
        directions.add(new Direction("Transfer to the heatproof platter, cover with foil and keep warm in the oven until serving. Serve warm, with desired topping such as maple syrup, fresh berries or banana slices."));


        databaseAdapter.addNewRecipe(new Recipe("American pancakes",
                "American", "Back to basics with the easiest pancakes recipe ever. With only 6 ingredients and 2 minutes preparation, you get the perfect fluffy American pancakes for breakfast!"
                , ingredients, directions, file.getAbsolutePath()));
    }

    private void navigateToLogin() {
        Intent startIntent = new Intent(this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void navigateToMainPage() {
        Intent startIntent = new Intent(this, MainActivity.class);
        startActivity(startIntent);
        finish();
    }
}
