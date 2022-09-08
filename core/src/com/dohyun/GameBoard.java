package com.dohyun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GameBoard {
    private int[][] board;
    private boolean firstClick = true;
    private Texture emptyTile;
    private Texture questionTile;
    private Texture bombTile;
    private Texture emptyFloor;
    private Texture oneTile,twoTile,threeTile,fourTile,fiveTile,sixTile,sevenTile,eightTile;

    private Texture bomb;

    private static final int BOMB = 9,EmptyTile = 10, FLAGGEDTILE = 20, QUESTIONTILE = 30;
    public GameBoard(){
        board = new int[16][30];
        initEmptyBoard();

        //load all textures
        emptyTile = new Texture("emptyTile.jpg");
        bomb = new Texture("bomb.jpg");
        oneTile = new Texture("oneTile.jpg");
        twoTile = new Texture("twoTile.jpg");
        threeTile = new Texture("threeTile.jpg");
        fourTile = new Texture("fourTile.jpg");
        fiveTile = new Texture("fiveTile.jpg");
        sixTile = new Texture("sixTile.jpg");
        sevenTile = new Texture("sevenTile.jpg");
        eightTile = new Texture("eightTile.jpg");

    }


    private ArrayList<Location> getNeighbors(int row, int col){
        ArrayList<Location> locs = new ArrayList<>();
        for(int i= -1; i <2; i++){
            for(int j = -1; j<2; j++){
                if(isValidLoc(row-i,col-j) && (row -i != row && col -j != col)) locs.add(new Location(row-i,col-j));
            }
        }
        return locs;
    }
    public void prints(){
        for(Location x: getNeighbors(3,5)){
            System.out.println(x.getRow() +", " + x.getCol());
        }
    }

    public boolean isValidLoc(int row, int col){
        return row>=0 && row< board.length&& col>=0 && col<board[0].length;
    }
    public void handleClick(int x,int y){
        //change windows(x,y) coordinate to 2D array loc
        int rowClicked = (y-10)/25;
        int colClicked = (x-10)/25;

        if(isValidLoc(rowClicked,colClicked)){
            board[rowClicked][colClicked]=board[rowClicked][colClicked] % 10;
            if(firstClick){
                firstClick = false;
                placeBombs(rowClicked,colClicked);
            }
        }
    }
    private void placeBombs(int rowClicked, int colClicked){
        int bombCount =0;

        while(bombCount<99){
            int randomRow = (int)(Math.random()* board.length);
            int randomCol = (int)(Math.random()* board[0].length);

            //if the random location is not equal to the first click
            if(randomRow != rowClicked && randomCol != colClicked){
                if(board[randomRow][randomCol] == EmptyTile){
                    board[randomRow][randomCol] = BOMB+10;
                    bombCount++;
                }
            }
        }
        System.out.println("bombs placed: " + bombCount);

    }
    public void draw(SpriteBatch spriteBatch){
        for(int row = 0; row < board.length; row++){
            for(int col=0; col< board[row].length; col++){
                //if we have an empty tile
                if(board[row][col] >= EmptyTile && board[row][col] < FLAGGEDTILE){
                    spriteBatch.draw(emptyTile, 10+(col*25),600-35-(row*25));
                }else if(board[row][col] == BOMB){
                    spriteBatch.draw(bomb, 10+(col*25),600-35-(row*25));
                }
            }
        }
    }
    private void initEmptyBoard(){
        for(int i=0; i< board.length; i++){
            for(int j=0; j<board[i].length; j++){
                board[i][j] = 10;
            }
        }
    }
}
