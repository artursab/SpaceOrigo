package com.sandipbhattacharya.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class OurShip {
    Context context;
    Bitmap ourSpaceship;
    int ox, oy;
    Random random;

    public OurShip(Context context) {
        this.context = context;
        ourSpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket1);
        random = new Random();
        ox = random.nextInt(SpaceShooter.screenWidth);
        oy = SpaceShooter.screenHeight - ourSpaceship.getHeight();
    }

    public Bitmap getOurShip(){
        return ourSpaceship;
    }

    int getOurShipWidth(){
        return ourSpaceship.getWidth();
    }
}
