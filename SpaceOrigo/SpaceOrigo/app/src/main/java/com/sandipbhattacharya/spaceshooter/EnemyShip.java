package com.sandipbhattacharya.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class EnemyShip {
    Context context;
    Bitmap enemyShip;
    int ex, ey;
    int enemyVelocity;
    Random random;

    public EnemyShip(Context context) {
        this.context = context;
        enemyShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket2);
        random = new Random();
        ex = 200 + random.nextInt(400);
        ey = 0;
        enemyVelocity = 14 + random.nextInt(10);
    }

    public Bitmap getEnemyShip(){
        return enemyShip;
    }

    int getEnemySpaceshipWidth(){
        return enemyShip.getWidth();
    }

    int getEnemySpaceshipHeight(){
        return enemyShip.getHeight();
    }
}
