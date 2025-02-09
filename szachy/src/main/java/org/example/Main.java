package org.example;

import org.example.utils.Board;
import org.example.utils.ChessEngine;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ChessEngine engine = new ChessEngine();
        boolean choosed = false;
        while(!choosed) {
            System.out.println("Choose side:\n1.White\n2.Black\n3.Random\n?> ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    choosed = true;
                    engine.setBotSide("Black");
                    break;
                case 2:
                    choosed = true;
                    engine.setBotSide("White");
                    break;
                case 3:
                    Random randomNumber = new Random();
                    int number = randomNumber.nextInt() % 2;
                    if (number == 0) {
                        System.out.println("You are playing White");
                        engine.setBotSide("Black");
                    } else {
                        System.out.println("You are playing Black");
                        engine.setBotSide("White");
                    }
                    choosed = true;
                    break;
                default:
                    System.out.println("Wrong input.");
                    break;
            }
        }
        engine.startGame();
    }
}