package com.sandipbhattacharya.spaceshooter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class SpaceShooter extends View {
    Context context;
    Bitmap background, lifeImage;
    Handler handler;
    long UPDATE_MILLIS = 30;
    static int screenWidth, screenHeight;
    int points = 0;
    int life = 3;
    Paint scorePaint;
    int TEXT_SIZE = 80;
    boolean paused = false;
    OurShip ourShip;
    EnemyShip enemyShip;
    Random random;
    ArrayList<Shot> enemyShots, ourShots;
    Explosion explosion;
    ArrayList<Explosion> explosions;
    boolean enemyShotAction = false;
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
           invalidate();
        }
    };


    public SpaceShooter(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        enemyShots = new ArrayList<>();
        ourShots = new ArrayList<>();
        explosions = new ArrayList<>();
        ourShip = new OurShip(context);
        enemyShip = new EnemyShip(context);
        handler = new Handler();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw background, Points and life on Canvas
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Pt: " + points, 0, TEXT_SIZE, scorePaint);
        for(int i=life; i>=1; i--){
            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth() * i, 0, null);
        }
        // When life becomes 0, stop game and launch GameOver Activity with points
        if(life == 0){
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
        // Move enemySpaceship
        enemyShip.ex += enemyShip.enemyVelocity;
        // If enemySpaceship collides with right wall, reverse enemyVelocity
        if(enemyShip.ex + enemyShip.getEnemySpaceshipWidth() >= screenWidth){
            enemyShip.enemyVelocity *= -1;
        }
        // If enemySpaceship collides with left wall, again reverse enemyVelocity
        if(enemyShip.ex <=0){
            enemyShip.enemyVelocity *= -1;
        }
        // Till enemyShotAction is false, enemy should fire shots from random travelled distance
        if(enemyShotAction == false){
            if(enemyShip.ex >= 200 + random.nextInt(400)){
                Shot enemyShot = new Shot(context, enemyShip.ex + enemyShip.getEnemySpaceshipWidth() / 2, enemyShip.ey );
                enemyShots.add(enemyShot);
                // We're making enemyShotAction to true so that enemy can take a short at a time
                enemyShotAction = true;
            }
            if(enemyShip.ex >= 400 + random.nextInt(800)){
                Shot enemyShot = new Shot(context, enemyShip.ex + enemyShip.getEnemySpaceshipWidth() / 2, enemyShip.ey );
                enemyShots.add(enemyShot);
                // We're making enemyShotAction to true so that enemy can take a short at a time
                enemyShotAction = true;
            }
            else{
                Shot enemyShot = new Shot(context, enemyShip.ex + enemyShip.getEnemySpaceshipWidth() / 2, enemyShip.ey );
                enemyShots.add(enemyShot);
                // We're making enemyShotAction to true so that enemy can take a short at a time
                enemyShotAction = true;
            }
        }
        // Draw the enemy Spaceship
        canvas.drawBitmap(enemyShip.getEnemyShip(), enemyShip.ex, enemyShip.ey, null);
        // Draw our spaceship between the left and right edge of the screen
        if(ourShip.ox > screenWidth - ourShip.getOurShipWidth()){
            ourShip.ox = screenWidth - ourShip.getOurShipWidth();
        }else if(ourShip.ox < 0){
            ourShip.ox = 0;
        }
        // Draw our Spaceship
        canvas.drawBitmap(ourShip.getOurShip(), ourShip.ox, ourShip.oy, null);
        // Draw the enemy shot downwards our spaceship
        for(int i=0; i < enemyShots.size(); i++){
            enemyShots.get(i).shy += 15;
            canvas.drawBitmap(enemyShots.get(i).getShot(), enemyShots.get(i).shx, enemyShots.get(i).shy, null);
            if((enemyShots.get(i).shx >= ourShip.ox)
                && enemyShots.get(i).shx <= ourShip.ox + ourShip.getOurShipWidth()
                && enemyShots.get(i).shy >= ourShip.oy
                && enemyShots.get(i).shy <= screenHeight){
                life--;
                enemyShots.remove(i);
                explosion = new Explosion(context, ourShip.ox, ourShip.oy);
                explosions.add(explosion);
            }else if(enemyShots.get(i).shy >= screenHeight){
                enemyShots.remove(i);
            }
            if(enemyShots.size() < 1){
                enemyShotAction = false;
            }
        }
        // Draw our spaceship shots towards the enemy.
        for(int i=0; i < ourShots.size(); i++){
            ourShots.get(i).shy -= 15;
            canvas.drawBitmap(ourShots.get(i).getShot(), ourShots.get(i).shx, ourShots.get(i).shy, null);
            if((ourShots.get(i).shx >= enemyShip.ex)
               && ourShots.get(i).shx <= enemyShip.ex + enemyShip.getEnemySpaceshipWidth()
               && ourShots.get(i).shy <= enemyShip.getEnemySpaceshipWidth()
               && ourShots.get(i).shy >= enemyShip.ey){
                points++;
                ourShots.remove(i);
                explosion = new Explosion(context, enemyShip.ex, enemyShip.ey);
                explosions.add(explosion);
            }else if(ourShots.get(i).shy <=0){
                ourShots.remove(i);
            }
        }
        // Do the explosion
        for(int i=0; i < explosions.size(); i++){
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).eX, explosions.get(i).eY, null);
            explosions.get(i).explosionFrame++;
            if(explosions.get(i).explosionFrame > 8){
                explosions.remove(i);
            }
        }
        // If not paused, we’ll call the postDelayed() method
        if(!paused)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();

        // create a new Shot.
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(ourShots.size() < 1){
                Shot ourShot = new Shot(context, ourShip.ox + ourShip.getOurShipWidth() / 2, ourShip.oy);
                ourShots.add(ourShot);
            }
        }
        // When event.getAction() is MotionEvent.ACTION_DOWN, control ourSpaceship
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            ourShip.ox = touchX;
        }
        // When event.getAction() is MotionEvent.ACTION_MOVE, control ourSpaceship
        // along with the touch.
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            ourShip.ox = touchX;
        }
        // Returning true in an onTouchEvent() tells Android system that you already handled
        // the touch event and no further handling is required.
        return true;
    }
}
