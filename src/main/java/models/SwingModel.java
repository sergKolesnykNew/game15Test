package models;

import models.SolverBotModules.Block;
import models.SolverBotModules.BotSolver;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


@Component
public class SwingModel extends JFrame {

    private JPanel panel = new JPanel(new GridLayout(6, 4));
    private static int[][] numbers = new int[4][4]; //Для збереження розміщення цифр
    private static int[][] gamePlatformAfterShuffle = new int[4][4];//останній стан гри
    private static ArrayList<int[][]> gamePlatformAfterShuffleList = new ArrayList<>();//Звідси дістаєм попереднє значення

    private static Font font = new Font("TimesRoman", Font.BOLD, 25);
    private static Font smallFont = new Font("TimesRoman", Font.BOLD, 20);

    private BotSolverListener botSolverListener = new BotSolverListener();
    private ClickListener clickListener = new ClickListener();
    private RandomShuffleListener randomShuffleListener = new RandomShuffleListener();
    private Timer botTimer = new Timer(1000, botSolverListener);

    public SwingModel() {
        setBounds(400, 400, 600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.add(panel);
        repaintField();
    }
    //Заповняємо панель потрібними кнопками
    public void repaintField() {
        panel.removeAll();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JButton button = new JButton(Integer.toString(numbers[i][j]));
                panel.add(button);
                button.setFont(font);
                if (numbers[i][j] == 0) {
                    button.setVisible(false);
                    //button.setFont(font);
                } else
                    button.addActionListener(clickListener);
            }
        }
        JButton b1 = new JButton("UP");
        panel.add(b1);
        b1.addActionListener(clickListener);
        b1.setFont(font);

        JButton b2 = new JButton("DOWN");
        panel.add(b2);
        b2.addActionListener(clickListener);
        b2.setFont(font);

        JButton b3 = new JButton("RIGHT");
        panel.add(b3);
        b3.addActionListener(clickListener);
        b3.setFont(font);

        JButton b4 = new JButton("LEFT");
        panel.add(b4);
        b4.addActionListener(clickListener);
        b4.setFont(font);

        JButton easy = new JButton("EASY");
        panel.add(easy);
        easy.addActionListener(randomShuffleListener);
        easy.setFont(smallFont);
        easy.setBackground(Color.RED);

        JButton middle = new JButton("MIDDLE");
        panel.add(middle);
        middle.addActionListener(randomShuffleListener);
        middle.setFont(smallFont);
        middle.setBackground(Color.RED);

        JButton hard = new JButton("HARD");
        panel.add(hard);
        hard.addActionListener(randomShuffleListener);
        hard.setFont(smallFont);
        hard.setBackground(Color.RED);

        JButton botSolver = new JButton("BOT");
        panel.add(botSolver);
        botSolver.addActionListener(botSolverListener);
        botSolver.setFont(font);
        botSolver.setBackground(Color.YELLOW);

        panel.repaint();
    }

    //інжектимо початкове розміщення
    public void setValues(int[][] ints) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                numbers[i][j] = ints[i][j];
            }
        }
        saveToFile(createCurrectString(ints));
    }

    private class RandomShuffleListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                String name = button.getText();
                try {
                    randomShuffle(name);
                    saveToFile(createCurrectString(gamePlatformAfterShuffle));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        //Рандомно переміщує значення кнопок
        public void randomShuffle(String name) throws IOException {
            int countOfRandomShuffle = 0;
            Random random = new Random();

            if(name.equals("EASY")){
                countOfRandomShuffle = 20;
            }
            if (name.equals("MIDDLE")){
                countOfRandomShuffle = 50;
            }
            if(name.equals("HARD")){
                countOfRandomShuffle = 100;
            }

            for (int i = 0; i < countOfRandomShuffle; i++) {
                int randomValue = random.nextInt(4);

                if (randomValue == 0) {
                    clickButton("UP");
                }

                if (randomValue == 1) {
                    clickButton("DOWN");
                }
                if (randomValue == 2) {
                    clickButton("RIGHT");
                }

                if (randomValue == 3) {
                    clickButton("LEFT");
                }
            }
            gamePlatformAfterShuffle  = gamePlatformAfterShuffleList.get(gamePlatformAfterShuffleList.size()-1);
            System.out.print(" last position of game mode");
            printArrays(gamePlatformAfterShuffle);
        }

        public class BotSolverListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    gamePlatformAfterShuffle  = gamePlatformAfterShuffleList.get(gamePlatformAfterShuffleList.size()-1);
                    System.out.print(" last position of game mode");
                    printArrays(gamePlatformAfterShuffle);

                    botTimer.start();
                    showBotSolution();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void showBotSolution() throws IOException {

            Block block = new Block(gamePlatformAfterShuffle);
            BotSolver solver = new BotSolver(block);
            for(String s : solver.getCorrectMoves()){
                System.out.println(s);
            }
            java.util.List<String> correctMoves = solver.getCorrectMoves();
            int size = correctMoves.size();
            System.out.println("SIze - " + size);

            if (size>0){
                String s = correctMoves.get(0);
                System.out.println(s + " currect moveeeeeeeeeee");
                correctMoves.remove(0);
                clickButton(s);
            }
            if (size == 0) botTimer.stop();

        }

    public void printArrays(int[][] array) {
        System.out.println(createCurrectString(array));
        }

        private class ClickListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                String name = button.getText();
                if (name.equals("UP") || name.equals("DOWN") || name.equals("RIGHT") || name.equals("LEFT")) {

                    try {
                        clickButton(name);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                //Перехоплюємо значення нажатої кнопки і передаємо його в метод зміни послідовності
                // clickButton(Integer.parseInt(name));
                else {
                    change(Integer.parseInt(name));
                }
            }
        }
        //Натискання на кнопку
        public void clickButton(String n) throws IOException {
            int vert = 0;
            int horuz = 0;
            int revers;
            //Шукаємо координати зі значенням 0 і передаєм їх в попередні філди
            for (int i = 0; i < 4; i++) {
                for (int x = 0; x < 4; x++) {
                    if (numbers[i][x] == 0) {
                        vert = i;
                        horuz = x;
                    }
                }
            }

            if (n.equals("UP")) {
                if (vert < 3) {
                    revers = numbers[vert + 1][horuz];
                    numbers[vert][horuz] = revers;
                    numbers[vert + 1][horuz] = 0;

                    addNewPositionOfGameMode(numbers);
                    printArrays(numbers);
                    saveToFile(createCurrectString(numbers));
                }
            }

            if (n.equals("DOWN")) {
                if (vert > 0) {
                    //listOfRandomShufle.add("DOWN");
                    revers = numbers[vert - 1][horuz];
                    numbers[vert][horuz] = revers;
                    numbers[vert - 1][horuz] = 0;
                    addNewPositionOfGameMode(numbers);
                    printArrays(numbers);
                }
            }
            if (n.equals("RIGHT")) {
                if (horuz > 0) {
                    //listOfRandomShufle.add("RIGHT");
                    revers = numbers[vert][horuz - 1];
                    numbers[vert][horuz] = revers;
                    numbers[vert][horuz - 1] = 0;
                    addNewPositionOfGameMode(numbers);
                    printArrays(numbers);
                    }
            }
            if (n.equals("LEFT")) {

                if (horuz < 3) {
                    //listOfRandomShufle.add("LEFT");
                    revers = numbers[vert][horuz + 1];
                    numbers[vert][horuz] = revers;
                    numbers[vert][horuz + 1] = 0;
                    addNewPositionOfGameMode(numbers);
                    printArrays(numbers);
                }
            }
            repaintField();
        }
        //Після зміни порядку чисел , ми добавляємо нову позицію в список
        public void addNewPositionOfGameMode(int[][] a) {
         int[][] newList = new int[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    newList[i][j] = a[i][j];
                }
            }
            System.out.println( " this is new List added in gamePlatformList");
            printArrays(newList);
            gamePlatformAfterShuffleList.add(newList);
        }


        public void change(int num) {
            int i = 0;
            int j = 0;
            //find clicked button
            for (int k = 0; k < 4; k++) {
                for (int l = 0; l < 4; l++) {
                    if (numbers[k][l] == num) {
                        i = k;
                        j = l;
                    }
                }
            }
            //num - значення нажатої кнопки
            //Відповідно і - координата вертикалі j - координата горизонталі
            if (i > 0) {
                //перевірка попереднього значення по горизонталі
                if (numbers[i - 1][j] == 0) {
                    //заміна значення в таблиці
                    numbers[i - 1][j] = num;
                    numbers[i][j] = 0;
                    addNewPositionOfGameMode(numbers);
                }
            }
            //Перевірка наступного значення по горизонталі
            if (i < 3) {
                if (numbers[i + 1][j] == 0) {
                    numbers[i + 1][j] = num;
                    numbers[i][j] = 0;
                    addNewPositionOfGameMode(numbers);
                }
            }
            //Перевірка попереднього значення по вертикалі
            if (j > 0) {
                if (numbers[i][j - 1] == 0) {
                    numbers[i][j - 1] = num;
                    numbers[i][j] = 0;
                    addNewPositionOfGameMode(numbers);
                }
            }
            //Перевірка наступного значення по вертикалі
            if (j < 3) {
                if (numbers[i][j + 1] == 0) {
                    numbers[i][j + 1] = num;
                    numbers[i][j] = 0;
                    addNewPositionOfGameMode(numbers);
                }
            }
            repaintField();
        }

        public static String createCurrectString(int[][] num) {
            String newLine = System.getProperty("line.separator");
            String s = "";
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    s = s.concat(String.valueOf(num[i][j]) + ", ");
                }
                s = s.concat(newLine);
            }
            return s;
        }

    public static void saveToFile(String text) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter("moveListener.txt"));
            out.println("I dont know");
            out.println(text);
            System.out.println("I saved this string - " + text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
        public static void main(String[] args) throws IOException {
            String currectString = createCurrectString(gamePlatformAfterShuffle);
            System.out.println(currectString);
            saveToFile(currectString);
        }


}

