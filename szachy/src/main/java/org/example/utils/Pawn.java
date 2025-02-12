package org.example.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Pawn implements Piece {
    private Position pos;
    private String name;
    private String color;
    private boolean firstMove = true;
    private boolean enPassant = false;
    private int id;
    private boolean possibleEnPassant = false;

    Pawn(String color, Position pos){
        this.color = color;
        this.name = color + " pawn";
        this.pos = pos;
    }

    public Position getPosition(){
        return pos;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public String getSymbol(){
        return "P";
    }
    public String getColor(){
        return color;
    }
    public void setFirstMove(boolean firstMove){
        this.firstMove = firstMove;
    }
    public boolean getFirstMove(){
        return firstMove;
    }
    public boolean getEnPassant(){
        return enPassant;
    }
    public void setEnPassant(boolean enPassant){
        this.enPassant = enPassant;
    }

    public void setPossibleEnPassant(boolean possibleEnPassant) {
        this.possibleEnPassant = possibleEnPassant;
    }
    public boolean getPossibleEnPassant(){
        return possibleEnPassant;
    }

    public void move(Position pos){
        if(firstMove) {
            firstMove = false;
            if(this.pos.x == pos.x+2 || this.pos.x == pos.x-2){
                enPassant = true;
            }
        }
        this.pos = pos;
    };
    public List<Position> getPossibleMoves(){
        List<Position> possibleMoves = new java.util.ArrayList<Position>();
        if(Objects.equals(color, "Black")){
            possibleMoves.add(new Position(pos.x+1,pos.y));
        }
        else{
            possibleMoves.add(new Position(pos.x-1,pos.y));
        }
        if(firstMove){
            if(Objects.equals(color, "Black")){
                possibleMoves.add(new Position(pos.x+2,pos.y));
            }
            else{
                possibleMoves.add(new Position(pos.x-2,pos.y));
            }
        }
        return possibleMoves;
    };
}
