package com.exemple.flappybirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import javax.swing.Timer;

public class FlappyBirds extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture [] birds;
	Texture tubetop;
	Texture tubedown;
	Texture gameOver;
	BitmapFont font;

	ShapeRenderer shapeRenderer;
	Circle birdCircle;
	Rectangle top;
	Rectangle down;

	Random random;
	int flapeState=0;
	int score=0;
	int highScore=0;
	int setScrore=0;
	int pause=0;
	int pause2=0;
	int birdY=0;
	int birdX=0;
	int tubex=0;
	int htubeTop=0;
	float htubeDown=0;
	float velocity=0;
	float gravity=0.8f;
	float gap=0;
	int gameState=0;
	int nbrClick=0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background=new Texture("bg.png");
		tubetop=new Texture("tubetop.png");
		gameOver=new Texture("go.png");
		tubedown=new Texture("tubedown.png");
		birds=new Texture[2];
		birds[0]=new Texture("birdup.png");
		birds[1]=new Texture("birddown.png");
		birdY=Gdx.graphics.getHeight() / 2 - (147 / 2);
		birdX=Gdx.graphics.getWidth() / 2 - 300;
		tubex=Gdx.graphics.getWidth()-350;
		htubeDown=600;
		htubeTop=600;
		random=new Random();
		font= new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(10);

		birdCircle=new Circle();
		shapeRenderer=new ShapeRenderer();
		top=new Rectangle();
		down=new Rectangle();
	}

	@Override
	public void render () {
		font.setColor(Color.BLACK);

		//draw
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


		if(gameState==1){



			if(Gdx.input.justTouched()){
				nbrClick++;
				if(velocity>0 ){
					velocity-=20;
				}else if(velocity<0|| birdY>=Gdx.graphics.getHeight()/2){
					velocity-=10;

				}
				if(pause2>16){
					pause2++;
				}else{
					pause2=0;
				}
			}


			if(birdY>20 || birdY>=Gdx.graphics.getHeight()-147/2 || velocity<0) {
				velocity += gravity;
				birdY -= velocity;
			}

			if(birdY>=Gdx.graphics.getHeight()-147/2) {
				birdY = Gdx.graphics.getHeight() - 147/2 ;
				birdY -= velocity;

			}



			tubex-=6;

			if(tubex<-200){
				tubex=Gdx.graphics.getWidth();
				htubeTop=random.nextInt(1100-400+1)+400;
				gap=random.nextInt(60);
				htubeDown=(1200-htubeTop)+gap;
				setScrore=0;


			}

			if(tubex<birdX-147 && setScrore==0) {
				score++;
				setScrore=1;
			}




		}
		//the bird at the begin is in center of screen
		else if(gameState==0){
			if(Gdx.input.justTouched()){
				gameState=1;

			}
		}else{
			if(Gdx.input.justTouched()){
				gameState=0;
				birdY=Gdx.graphics.getHeight() / 2 - (147 / 2);
				birdX=Gdx.graphics.getWidth() / 2 - 300;
				velocity=0;
				tubex=Gdx.graphics.getWidth()-350;

				initForCollision();

			}

		}
		batch.draw(birds[flapeState], birdX, birdY, 200, 147);
		batch.draw(tubetop,tubex,0,200,htubeTop);
		batch.draw(tubedown,tubex,Gdx.graphics.getHeight()-htubeDown,200,htubeDown);
		font.draw(batch,String.valueOf(score),40,Gdx.graphics.getHeight()-50);

		initForCollision();

		if(Intersector.overlaps(birdCircle,top)||Intersector.overlaps(birdCircle,down)){
			gameState=2;
			batch.draw(gameOver,Gdx.graphics.getWidth()/2-400,Gdx.graphics.getHeight()/2,800,300);
			birdY=15;
			batch.draw(birds[flapeState], birdX, birdY, 200, 147);
			if(score>highScore)
				highScore=score;
			score=0;
			font.setColor(Color.RED);
			font.draw(batch,"Hight score: "+String.valueOf(highScore),Gdx.graphics.getWidth()/2-400,Gdx.graphics.getHeight()/2);

		}

		//flapping action with the two picture
		if(pause<10){
			pause++;
		}else{
			pause=0;
			if(flapeState==0){
				flapeState=1;
			}else{
				flapeState=0;
			}
		}









		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(birdX+105,birdY+60,birdCircle.radius);
	//	shapeRenderer.rect(tubex,0,200,htubeTop);
		//shapeRenderer.rect(tubex,Gdx.graphics.getHeight()-htubeDown,200,htubeDown);




		//shapeRenderer.end();
		batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
	public void initForCollision(){
		birdCircle.set(birdX+105,birdY+60,90);
		top=new Rectangle(tubex,0,200,htubeTop);
		down=new Rectangle(tubex,Gdx.graphics.getHeight()-htubeDown,200,htubeDown);
	}
}
