package team1.intelligentcookingapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class selected_recipe extends AppCompatActivity {
    ImageView recipeImg;

    LinearLayout LL;
    TextView titleHolder;
    TextView numOfIngred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recipe);

        String title = getIntent().getStringExtra("rTitle");
        String[] ingredients = getIntent().getStringArrayExtra("rIngredients");
        String imageUrl = getIntent().getStringExtra("rImgUrl");
        String webUrl = getIntent().getStringExtra("rUrl");

        LL = (LinearLayout)findViewById(R.id.rlinlay);
        titleHolder = (TextView)findViewById(R.id.recipeTitle);
        numOfIngred = (TextView)findViewById(R.id.numOfIngred);
        recipeImg = (ImageView)findViewById(R.id.imageView);

        Picasso.with(LL.getContext()).load(imageUrl).resize(410, 240)
                .into(recipeImg);

        titleHolder.setText(title);

        String ingredientSize = ingredients.length + " ingredients";
        numOfIngred.setText(ingredientSize);
        for (String i : ingredients)
        {
            TextView ingred = new TextView(LL.getContext());
            String format = "- " + i;
            ingred.setText(format);
            ingred.setTextSize(24);
            LL.addView(ingred);
        }

        Space s = new Space(LL.getContext());
        s.setMinimumHeight(50);
        s.setMinimumWidth(LL.getWidth());
        LL.addView(s);

        TextView stepHead = new TextView(LL.getContext());
        stepHead.setText("Steps");
        stepHead.setTextSize(24);
        stepHead.setTextColor(Color.BLACK);
        LL.addView(stepHead);

        TextView stepURL = new TextView(LL.getContext());
        String stepDescript = "View on " + webUrl;
        stepURL.setText(stepDescript);
        stepURL.setTextSize(24);
        LL.addView(stepURL);

    }
}
