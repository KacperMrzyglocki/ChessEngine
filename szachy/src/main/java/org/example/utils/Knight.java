package org.example.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Knight implements Piece {
    private Position pos;
    private String name;
    private String color;
    private int id;

    Knight(String color, Position pos){
        this.color = color;
        this.name = color + " knight";
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
        return "N";
    }
    public String getColor(){
        return color;
    }

    public void move(Position pos){
        this.pos = pos;
    };

    public List<Position> getPossibleMoves(){
        Position[] possibleMoves = {
                new Position(pos.x-2,pos.y-1),
                new Position(pos.x-2,pos.y+1),
                new Position(pos.x+2,pos.y-1),
                new Position(pos.x+2,pos.y+1),
                new Position(pos.x-1,pos.y-2),
                new Position(pos.x+1,pos.y-2),
                new Position(pos.x-1,pos.y+2),
                new Position(pos.x+1,pos.y+2),
        };
        List<Position> legalMoves = new java.util.ArrayList<Position>();
        Collections.addAll(legalMoves, possibleMoves);
        if(pos.x <= 1){
            legalMoves = legalMoves.stream().filter(p -> p.x != pos.x - 2).collect(Collectors.toList());
        }
        if(pos.x >= 6){
            legalMoves = legalMoves.stream().filter(p -> p.x != pos.x+2).toList();
        }
        if(pos.y <= 1){
            legalMoves = legalMoves.stream().filter(p -> p.y != pos.y-2).toList();
        }
        if(pos.y >= 6){
            legalMoves = legalMoves.stream().filter(p -> p.y != pos.y+2).toList();
        }
        if(pos.x == 0){
            legalMoves = legalMoves.stream().filter(p -> p.x != pos.x-1).toList();
        }
        if(pos.x == 7){
            legalMoves = legalMoves.stream().filter(p -> p.x != pos.x+1).toList();
        }
        if(pos.y == 0){
            legalMoves = legalMoves.stream().filter(p -> p.y != pos.y-1).toList();
        }
        if(pos.y == 7){
            legalMoves = legalMoves.stream().filter(p -> p.y != pos.y+1).toList();
        }
        return legalMoves;
    };
}
