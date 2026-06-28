import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args){
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int roundsWon = 0;
        int max_attempts = 8;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println(" You have max " + max_attempts + " attempts to guess the number.");

        boolean playAgain = true;
        while (playAgain) {
            int randomNum = random.nextInt(1, 101);
            int attempts = 0;
            int guess = 0;
            boolean guessedCorrectly = false;
            System.out.println("New round! Guess a number between 1 and 100.");
        do{
            System.out.print("Enter your guess: ");
            guess = scanner.nextInt();
            attempts++; 

            if(guess < randomNum) {
                System.out.println("Too low! Try again.");
            } else if (guess > randomNum) {
                System.out.println("Too high! Try again.");
            } else {
                System.out.println("Congratulations! You've guessed the number and the number was : " + randomNum);
                guessedCorrectly = true;
            }
            

        } while(guess != randomNum && attempts < max_attempts && !guessedCorrectly);
        if(guess == randomNum) {
            roundsWon++;
        }else if(attempts >= max_attempts) {
            System.out.println("Sorry, you've used all your attempts. The number was: " + randomNum);
        }
        else {
            System.out.println("Game over! The number was: " + randomNum);
        }
        
        System.out.println("You made " + attempts + " attempts.");
        System.out.println("Rounds won: " + roundsWon);
        System.out.print("Do you want to play again? (yes/no): ");
        playAgain = scanner.next().equalsIgnoreCase("yes");
    }
    System.out.println("Thank you for playing! Total rounds won: " + roundsWon);
    scanner.close();
}
}
