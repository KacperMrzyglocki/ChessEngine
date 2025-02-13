package org.example.utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.utils.*;

import java.util.*;

public class ChessGUI extends Application {
    private static final int TILE_SIZE = 80;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private Board board;
    private Position selectedPosition = null;
    private GridPane gridPane;
    private String currentTurn = "White";

    @Override
    public void start(Stage primaryStage) {
        board = new Board();
        board.initializeBoard();
        gridPane = new GridPane();
        drawBoard();

        Scene scene = new Scene(gridPane, WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawBoard() {
        if(currentTurn.equals("White")){
            if(board.checkMate("White")==2){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("Black wins!");
                alert.showAndWait();
            }
            if(board.checkMate("Black")==1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("Stalemate!");
                alert.showAndWait();
            }
        }else{
            if(board.checkMate("Black")==2){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("White Wins!");
                alert.showAndWait();
            }
            if(board.checkMate("Black")==1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("Stalemate!");
                alert.showAndWait();
            }
        }
        gridPane.getChildren().clear();
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE);
                square.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.BROWN);
                square.setStroke(Color.BLACK);
                final int r = row, c = col;
                square.setOnMouseClicked(e -> handleTileClick(e, r, c));
                gridPane.add(square, col, row);
                addPieceImage(row, col);
            }
        }
    }

    private void addPieceImage(int row, int col) {
        for (Piece piece : board.whitePieces) {
            if (piece.getPosition().x == row && piece.getPosition().y == col) {
                Image image = loadImage(piece);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                imageView.setMouseTransparent(true);
                gridPane.add(imageView, col, row);
                return;
            }
        }
        for (Piece piece : board.blackPieces) {
            if (piece.getPosition().x == row && piece.getPosition().y == col) {
                Image image = loadImage(piece);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                imageView.setMouseTransparent(true);
                gridPane.add(imageView, col, row);
                return;
            }
        }
    }

    private Image loadImage(Piece piece) {
        String color = piece.getColor().toLowerCase();
        String type = piece.getClass().getSimpleName().toLowerCase();
        String imagePath = String.format("/images/%s_%s.png", color, type);
        return new Image(getClass().getResourceAsStream(imagePath));
    }

    private void highlightPossibleMoves(List<Position> possibleMoves) {
        for (Position pos : possibleMoves) {
            Rectangle highlight = new Rectangle(TILE_SIZE, TILE_SIZE);
            highlight.setFill(Color.LIGHTGREEN);
            highlight.setOpacity(0.5);
            highlight.setMouseTransparent(true);
            gridPane.add(highlight, pos.y, pos.x);
        }
    }

    private void handleTileClick(MouseEvent event, int row, int col) {
        Position clickedPosition = new Position(row, col);
        if (selectedPosition == null) {
            if (isPieceAt(clickedPosition) && isCurrentPlayerPiece(clickedPosition)) {
                selectedPosition = clickedPosition;
                highlightSelectedTile(row, col, Color.LIGHTBLUE);
                List<Position> possibleMoves = getPossibleMovesForPieceAt(clickedPosition);
                highlightPossibleMoves(possibleMoves);
            }
        } else {
            if (isValidMove(selectedPosition, clickedPosition)) {
                movePiece(selectedPosition, clickedPosition);
                switchTurn();
            }
            selectedPosition = null;
            drawBoard();
        }
    }

    private List<Position> getPossibleMovesForPieceAt(Position pos) {
        for (Piece piece : board.whitePieces) {
            if (piece.getPosition().equals(pos)) {
                return board.getLegalMoves(piece.getPossibleMoves(), piece);
            }
        }
        for (Piece piece : board.blackPieces) {
            if (piece.getPosition().equals(pos)) {
                return board.getLegalMoves(piece.getPossibleMoves(), piece);
            }
        }
        return new ArrayList<>();
    }

    private boolean isPieceAt(Position pos) {
        return board.whitePieces.stream().anyMatch(p -> p.getPosition().equals(pos)) ||
                board.blackPieces.stream().anyMatch(p -> p.getPosition().equals(pos));
    }

    private boolean isCurrentPlayerPiece(Position pos) {
        if (currentTurn.equals("White")) {
            return board.whitePieces.stream().anyMatch(p -> p.getPosition().equals(pos));
        } else {
            return board.blackPieces.stream().anyMatch(p -> p.getPosition().equals(pos));
        }
    }

    private boolean isValidMove(Position from, Position to) {
        for (Piece piece : board.whitePieces) {
            if (piece.getPosition().equals(from)) {
                List<Position> legalMoves = board.getLegalMoves(piece.getPossibleMoves(), piece);
                return legalMoves.contains(to);
            }
        }
        for (Piece piece : board.blackPieces) {
            if (piece.getPosition().equals(from)) {
                List<Position> legalMoves = board.getLegalMoves(piece.getPossibleMoves(), piece);
                return legalMoves.contains(to);
            }
        }
        return false;
    }

    private void movePiece(Position from, Position to) {
        for (Piece piece : board.whitePieces) {
            if (piece.getPosition().equals(from)) {
                setEnPassant(board);
                handleCastle(piece, to);
                piece.move(to);
                board.deleteTakenPieces(piece);
                if(piece instanceof Pawn){
                    if(board.checkPromotion(piece)){
                        promotePawn((Pawn) piece);
                    }
                }
                drawBoard();
                return;
            }
        }
        for (Piece piece : board.blackPieces) {
            if (piece.getPosition().equals(from)) {
                setEnPassant(board);
                handleCastle(piece, to);
                piece.move(to);
                board.deleteTakenPieces(piece);
                if(piece instanceof Pawn){
                    if(board.checkPromotion(piece)){
                        promotePawn((Pawn) piece);
                    }
                }
                drawBoard();
                return;
            }
        }
    }

    private void setEnPassant(Board board){
        for(Piece piece : board.blackPieces){
            if(Objects.equals(piece.getSymbol(), "P")){
                if(((Pawn) piece).getEnPassant()){
                    ((Pawn) piece).setEnPassant(false);
                }
            }
        }
        for(Piece piece : board.whitePieces){
            if(Objects.equals(piece.getSymbol(), "P")){
                if(((Pawn) piece).getEnPassant()){
                    ((Pawn) piece).setEnPassant(false);
                }
            }
        }
    }

    private void handleCastle(Piece piece, Position to) {
        if (piece instanceof King) {
            if (Math.abs(piece.getPosition().y - to.y) == 2) {
                String side = (to.y > piece.getPosition().y) ? "Right" : "Left";
                board.castle(piece, side);
            }
        }
    }

    private void promotePawn(Pawn pawn) {
        List<String> choices = Arrays.asList("Queen", "Rook", "Bishop", "Knight");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Queen", choices);
        dialog.setTitle("Pawn Promotion");
        dialog.setHeaderText("Promote your pawn");
        dialog.setContentText("Choose a piece:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(choice -> board.promotePawn(pawn, choice));
    }

    private void switchTurn() {
        currentTurn = currentTurn.equals("White") ? "Black" : "White";
        System.out.println("Turn: " + currentTurn);
    }

    private void highlightSelectedTile(int row, int col, Color color) {
        Rectangle highlight = new Rectangle(TILE_SIZE, TILE_SIZE);
        highlight.setFill(color);
        highlight.setOpacity(0.5);
        gridPane.add(highlight, col, row);
    }

    public static void main(String[] args) {
        launch(args);
    }
}