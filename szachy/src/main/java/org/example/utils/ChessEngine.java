package org.example.utils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChessEngine {
    private String turn = "White";
    private String side;
    private boolean gameInProgress = true;

    public void printTurn(){
        System.out.println(turn + "'s move");
    }
    public void setBotSide(String side){
        this.side = side;
    }

    public void startGame(){
        Board board = new Board();
        board.initializeBoard();
        while(gameInProgress){
            board.printBoard();
            printTurn();
            if(Objects.equals(turn, side)){
                gameInProgress = playerMove(board);
                //bot
                if(Objects.equals(turn, "White")){
                    turn = "Black";
                }else{
                    turn = "White";
                }
            } else{
                gameInProgress = playerMove(board);
                if(Objects.equals(turn, "White")){
                    turn = "Black";
                }else{
                    turn = "White";
                }
            }
        }
    }

    private boolean playerMove(Board board) {
        boolean choosedPiece = false;
        int choicePiece;
        String choiceMove;
        while (!choosedPiece) {
            Piece king = null;
            List<Piece> allPieces;
            List<Piece> availablePieces = new ArrayList<>();
            System.out.println("Choose piece: ");
            if (Objects.equals(turn, "Black")) {
                allPieces = board.blackPieces;
                for (Piece piece : allPieces) {
                    if (!board.getLegalMoves(piece.getPossibleMoves(), piece).isEmpty()) {
                        availablePieces.add(piece);
                    }
                    if (piece instanceof King) {
                        king = piece;
                    }
                }
                printAvailablePieces(availablePieces);
                if (availablePieces.isEmpty()) {
                    if (board.isCheck(board.whitePieces, king)) {
                        System.out.println("White checkmated");
                        return false;
                    } else {
                        System.out.println("Stalemate");
                        return false;
                    }
                }
            } else {
                allPieces = board.whitePieces;
                for (Piece piece : allPieces) {
                    if (!board.getLegalMoves(piece.getPossibleMoves(), piece).isEmpty()) {
                        availablePieces.add(piece);
                    }
                    if (piece instanceof King) {
                        king = piece;
                    }
                }
                printAvailablePieces(availablePieces);
                if (availablePieces.isEmpty()) {
                    if (board.isCheck(board.blackPieces, king)) {
                        System.out.println("Black checkmated");
                        return false;
                    } else {
                        System.out.println("Stalemate");
                        return false;
                    }
                }
            }
            System.out.print("?>");
            Scanner scanner = new Scanner(System.in);
            choicePiece = scanner.nextInt();
            List<Piece> availablePiecesCopy = new ArrayList<>(availablePieces);
            for (Piece piece : availablePiecesCopy) {
                if (piece.getId() == choicePiece) {
                    choosedPiece = true;
                    boolean choosedMove = false;
                    List<Position> legalMoves = new ArrayList<>();
                    while (!choosedMove) {
                        legalMoves = board.getLegalMoves(piece.getPossibleMoves(), piece);
                        printAvailableMoves(legalMoves);
                        System.out.print("?>");
                        Scanner scanner2 = new Scanner(System.in);
                        choiceMove = scanner2.nextLine();
                        choosedMove = makeMove(choiceMove, legalMoves, piece, board);
                        if ((Objects.equals(piece.getSymbol(), "K")) && (Objects.equals(choiceMove, "g1") || Objects.equals(choiceMove, "g8"))) {
                            board.castle(piece, "Right");
                        }
                        if ((Objects.equals(piece.getSymbol(), "K")) && (Objects.equals(choiceMove, "c1") || Objects.equals(choiceMove, "c8"))) {
                            board.castle(piece, "Left");
                        }
                    }
                }
            }
            if (!choosedPiece) {
                System.out.println("There is no such piece");
            }
        }
        return true;
    }

//    private boolean playerMove(Board board){
//        boolean choosedPiece = false;
//        int choicePiece;
//        String choiceMove;
//        while(!choosedPiece) {
//            Piece king = null;
//            List<Piece> allPieces;
//            List<Piece> availablePieces = new ArrayList<Piece>();
//            System.out.println("Choose piece: ");
//            if (Objects.equals(turn, "Black")) {
//                allPieces = board.blackPieces;
//                for(Piece piece : allPieces){
//                    if(!board.getLegalMoves(piece.getPossibleMoves(),piece).isEmpty()){
//                        availablePieces.add(piece);
//                    }
//                    if(piece instanceof King){
//                        king = piece;
//                    }
//                }
//                printAvailablePieces(availablePieces);
//                if(availablePieces.isEmpty()){
//                    if(board.isCheck(board.whitePieces, king)){
//                        System.out.println("White checkmated");
//                        return false;
//                    } else{
//                        System.out.println("Stalemate");
//                        return false;
//                    }
//                }
//            } else {
//                allPieces = board.whitePieces;
//                for(Piece piece : allPieces){
//                    if(!board.getLegalMoves(piece.getPossibleMoves(),piece).isEmpty()){
//                        availablePieces.add(piece);
//                    }
//                    if(piece instanceof King){
//                        king = piece;
//                    }
//                }
//                printAvailablePieces(availablePieces);
//                if(availablePieces.isEmpty()){
//                    if(board.isCheck(board.blackPieces, king)){
//                        System.out.println("Black checkmated");
//                        return false;
//                    } else{
//                        System.out.println("Stalemate");
//                        return false;
//                    }
//                }
//            }
//            System.out.print("?>");
//            Scanner scanner = new Scanner(System.in);
//            choicePiece = scanner.nextInt();
//            for(Piece piece : availablePieces){
//                if(piece.getId() == choicePiece){
//                    choosedPiece = true;
//                    boolean choosedMove = false;
//                    List<Position> legalMoves = new java.util.ArrayList<Position>();
//                    while(!choosedMove){
//                        legalMoves = board.getLegalMoves(piece.getPossibleMoves(), piece);
//                        printAvailableMoves(legalMoves);
//                        System.out.print("?>");
//                        Scanner scanner2 = new Scanner(System.in);
//                        choiceMove = scanner2.nextLine();
//                        choosedMove = makeMove(choiceMove, legalMoves, piece, board);
//                        if ((Objects.equals(piece.getSymbol(), "K")) && (Objects.equals(choiceMove, "g1") || Objects.equals(choiceMove, "g8"))) {
//                            board.castle(piece, "Right");
//                        }
//                        if ((Objects.equals(piece.getSymbol(), "K")) && (Objects.equals(choiceMove, "c1") || Objects.equals(choiceMove, "c8"))) {
//                            board.castle(piece, "Left");
//                        }
//                    }
//                }
//            }
//            if(!choosedPiece){
//                System.out.println("There is no such piece");
//            }
//        }
//        return true;
//    }
    private boolean makeMove(String choice, List<Position> legalMoves, Piece piece, Board board){
        for(Position legalMove : legalMoves){
            String translatedPosition = legalMove.translatePosition();
            if(translatedPosition.equals(choice)){
                piece.move(legalMove);
                board.deleteTakenPieces(piece);
                return true;
            }
        }
        System.out.println("There is no such move available");
        return false;
    }
    private void printAvailablePieces(List<Piece> availablePieces){
        for(Piece piece : availablePieces) {
            Position pos = piece.getPosition();
            String translatedPosition = pos.translatePosition();
            System.out.println(piece.getId() + ". " + piece.getSymbol() + " on " + translatedPosition);
        }
    }
    private void printAvailableMoves(List<Position> legalMoves){
        for(Position legalMove : legalMoves){
            String translatedPosition = legalMove.translatePosition();
            System.out.println(translatedPosition);
        }
    }
}
