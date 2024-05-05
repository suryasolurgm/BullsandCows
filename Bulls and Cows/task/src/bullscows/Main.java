package bullscows;

import java.util.*;

public class Main {
    static int cows = 0;
    static int bulls = 0;
    static HashMap<Character, Integer> secretMap;
    static char[] characters;
    static char[] secretCode;
    static int turnCounter = 1;
    public static void printGrade(){
        if(bulls==0 && cows==0){
            System.out.println("Grade: None");
        } else if (cows==0) {
            if(bulls==1) {
                System.out.printf("Grade: %d Bull\n",bulls);
            }else {
                System.out.printf("Grade: %d Bulls\n",bulls);
            }

        } else if (bulls==0) {
            if(cows==1) {
                System.out.printf("Grade: %d cow\n",cows);
            }else{
                System.out.printf("Grade: %d cows\n",cows);
            }

        }else {
            if(cows==1 && bulls==1) {
                System.out.printf("Grade: %d bull and %d cow\n", bulls, cows);
            } else if (bulls==1) {
                System.out.printf("Grade: %d bull and %d cows\n", bulls, cows);
            } else if (cows==1) {
                System.out.printf("Grade: %d bulls and %d cow\n", bulls, cows);
            }else {
                System.out.printf("Grade: %d bulls and %d cows\n", bulls, cows);
            }
        }
    }
    public static void countBullsAndCows() {
        for (int i = 0; i < characters.length; i++) {
            if (secretMap.containsKey(characters[i]) && secretCode[i] == characters[i]) {
                bulls++;
            } else if (secretMap.containsKey(characters[i])) {
                cows++;
            }
        }
    }
    public static String generateSecretCode(int length,int range) {
        StringBuilder secret = new StringBuilder();
        Random random = new Random();
        HashSet<Character> characterHashSet =new HashSet<>();
        char[] tempcharacters = new char[range];
        for (int i = 0; i < range; i++) {
            if (i < 10) {
                tempcharacters[i] = (char) ('0' + i);
            } else {
                tempcharacters[i] = (char) ('a' + (i - 10));
            }
        }
        while (secret.length() < length) {
            int index = random.nextInt(range);
            if(!characterHashSet.contains(tempcharacters[index])) {
                characterHashSet.add(tempcharacters[index]);
                secret.append(tempcharacters[index]);
            }
        }
        return secret.toString();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        int length=0;
        String lengthString="";
        try {
            lengthString = scanner.nextLine();
            length = Integer.parseInt(lengthString);
        } catch (Exception e) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n",lengthString);
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");
        int range =0;
        String rangeString="";
        try {
            rangeString = scanner.nextLine();
            range = Integer.parseInt(rangeString);
        } catch (Exception e) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n",rangeString);
            return;
        }
        secretCode = new char[length];
        characters = new char[length];
        if (length > range || length > 36 ||(length==0 && range!=0)) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n",length,range);
            return;
        }
        if(range > 36) {
            System.out.printf("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).\n");
            return;
        }

        String secretCodeString = generateSecretCode(length, range);
        System.out.printf("The secret is prepared: %s (%d-%d,%s-%s).\n", "*".repeat(length), 0, 9,'a',(char) ('a' + range - 11));
        System.out.println("Okay, let's start a game!");
       // System.out.println("secret code: " + secretCodeString);
        for (int i = 0; i < length; i++) {
            secretCode[i] = secretCodeString.charAt(i);
        }
        secretMap = new HashMap<>();
        for (int i = 0; i < length; i++) {
            secretMap.put(secretCode[i], 1);
        }

        while (bulls != length) {
            cows = 0;
            bulls = 0;
            System.out.printf("Turn %d:\n", turnCounter++);
            String input = scanner.next().toLowerCase(); // Convert input to lowercase
            for (int i = 0; i < input.length(); i++) {
                characters[i]=input.charAt(i);
            }
            countBullsAndCows();
            printGrade();
        }
        System.out.println("Congratulations! You guessed the secret code.");

    }
}
