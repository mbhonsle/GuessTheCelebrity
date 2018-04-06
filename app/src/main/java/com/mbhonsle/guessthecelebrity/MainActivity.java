package com.mbhonsle.guessthecelebrity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mbhonsle.guessthecelebrity.util.MainController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView opt1;
    private TextView opt2;
    private TextView opt3;
    private TextView opt4;
    private ImageView imageView;
    private MainController mainController;
    private String currentImageName="";
    private TextView [] optsViews = null;
    private Random random;

    private void initViews() {
        this.opt1 = findViewById(R.id.option1);
        this.opt2 = findViewById(R.id.option2);
        this.opt3 = findViewById(R.id.option3);
        this.opt4 = findViewById(R.id.option4);
        this.imageView = findViewById(R.id.imageView);
        this.optsViews = new TextView[]{this.opt1, this.opt2, this.opt3, this.opt4};
    }

    public void play(View view) {
        if (!Objects.equals(this.currentImageName, "")) {
            // check the name clicked and display results
            if (((TextView)view).getText() == this.currentImageName) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong, the correct answer is: "
                        + this.currentImageName, Toast.LENGTH_SHORT).show();
            }
        }
        // start the next round
        createGameView();
    }

    private void createGameView() {
        setButtonTexts();
        setImage();
    }

    private void setImage() {
        Bitmap image = this.mainController.getImage(this.currentImageName);
        if (image != null) {
            this.imageView.setImageBitmap(image);
        } else {
            Toast.makeText(this, "Image download failed, choose the same option",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void setButtonTexts() {
        this.currentImageName = this.mainController.chooseARandomImage();
        String [] answers = {this.currentImageName, this.mainController.chooseARandomImage(),
                this.mainController.chooseARandomImage(), this.mainController.chooseARandomImage()};
        List<TextView> options = new ArrayList<>(Arrays.asList(optsViews));
        int i = 0;
        while (!options.isEmpty()) {
            TextView t = options.remove(options.size() == 1 ? 0 : random.nextInt(options.size() - 1));
            t.setText(answers[i++]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        this.mainController = new MainController();
        this.random = new Random();
        play(null);
    }
}
