package org.example.utils;

import java.util.Objects;

public class Position {
    public int x;
    public int y;

    Position(int x, int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String translatePosition(){
        int horizontal = 0;
        String vertical = null;
        switch (x) {
            case 0:
                horizontal = 8;
                break;
            case 1:
                horizontal = 7;
                break;
            case 2:
                horizontal = 6;
                break;
            case 3:
                horizontal = 5;
                break;
            case 4:
                horizontal = 4;
                break;
            case 5:
                horizontal = 3;
                break;
            case 6:
                horizontal = 2;
                break;
            case 7:
                horizontal = 1;
                break;
        }
        switch (y) {
            case 0:
                vertical = "a";
                break;
            case 1:
                vertical = "b";
                break;
            case 2:
                vertical = "c";
                break;
            case 3:
                vertical = "d";
                break;
            case 4:
                vertical = "e";
                break;
            case 5:
                vertical = "f";
                break;
            case 6:
                vertical = "g";
                break;
            case 7:
                vertical = "h";
                break;
        }
        return vertical+horizontal;
    }
}
