package models.SolverBotModules;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;



@Component("board")
public class Block {
    private int[][] blocks; //   поле
    private int zeroX;    // координати 0
    private int zeroY;
    public void setBlocks(int[][] blocks){
        this.blocks = blocks;
    }
    public int[][] getBlocks (){
        return blocks;
    }
    public int getZeroX(){
        return zeroX;
    }

    public int getZeroY(){
        return zeroY;
    }

    private int h;
    // (Кількість не правильних позицій) якщо більше ніж 0 - то треба робити заміни
    //При створенні екземпляру дошки ми знаходимо координату 0 і перевіряємо, чи всі позиціїї зайняті праивльними значеннями
    public Block(int[][] blocks) {
        int[][] blocks2 = deepCopy(blocks);   // копіюєм масив чисел
        this.blocks = blocks2;
        h = 0;
        //проходимся по всім значенням
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                //Якщо позиціє має не правильне значення - добавити 1 до h
                if (blocks[i][j] != (i*4 + j + 1) && blocks[i][j] != 0) {
                    h += 1;
                }
                //Знайшли координати 0 - записали
                if (blocks[i][j] == 0) {
                    zeroX = i;
                    zeroY =  j;
                }
            }
        }
    }

    //повертає I - кількість масивів масивів
    // в нашому випадку - завжди 4
//    public int dimension() {
//        return blocks.length;
//    }

    public int getH() {
        return h;
    }
    //якщо правда - значить всі значення стоять на праильних позиціях
    public boolean isGoal() {
        return h == 0;
    }

    //повертаєм список всіх можливих змін з суссідніми полями від 0
    public Set<Block> neighbors() {  //Постійно треба рухати нулем
        //Всього є 4 варіанти, куди 0 може піти
        //Якщо 0 з краю - повернемо null
        Set<Block> boardList = new HashSet<>();
        boardList.add(change(getNewBlock(), zeroX, zeroY, zeroX, zeroY + 1));
        boardList.add(change(getNewBlock(), zeroX, zeroY, zeroX, zeroY - 1));
        boardList.add(change(getNewBlock(), zeroX, zeroY, zeroX - 1, zeroY));
        boardList.add(change(getNewBlock(), zeroX, zeroY, zeroX + 1, zeroY));

        return boardList;
    }

    //Заміна поля з значенням 0  на сусіда
    private Block change(int[][] blocks2, int x1, int y1, int x2, int y2) {  //  міняємо 2 сусідніх поля
        //Якщо координата сусіда нуля більша ніж 0 і менша ніж розмір, тоді можна поміняти 0 з цим сусідом
        if (x2 > -1 && x2 < 4 && y2 > -1 && y2 < 4) {
            int t = blocks2[x2][y2];
            blocks2[x2][y2] = blocks2[x1][y1];
            blocks2[x1][y1] = t;
            return new Block(blocks2);
        } else
            return null;

    }
    //Щоб правильно відображати масив
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    //Щоб правильно порівнювати
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block board = (Block) o;


        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != board.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    //Копіюєм текущий масив в новий масив, і працюєм з ним, як з текущим

    private int[][] getNewBlock() { //  для незмінності
        return deepCopy(blocks);
    }

    private static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = new int[original[i].length];
            for (int j = 0; j < original[i].length; j++) {
                result[i][j] = original[i][j];
            }
        }
        return result;
    }

}