package models.SolverBotModules;

import org.springframework.stereotype.Component;

import java.util.*;

@Component("solver")
public class BotSolver {

    //екземпляр 15шки, яку треба розвязати
    private Block initial;
    public void setInitial(Block board){
        this.initial = board;
    }
    //список ходів, для розвязку завдання
    private List<Block> result = new ArrayList<>();

    //Тут ми будемо зберігати пройдені позиції, для того, щоб не повертатись у попередній стан
    //+ ми можемо дізнатись кількість ходів для розвязку

    private class Instance{
        //Штучно створюємо цепочку ходів, щоб легко добратись до ссамого початку
        private Instance prevBlock;  // екземпляр перед перестановкою
        private Block block;   // теперішній стан

        private Instance(Instance prevBlock, Block block) {
            this.prevBlock = prevBlock;
            this.block = block;
        }

        public Block getBlock() {
            return block;
        }
    }
    //При створені екземпляру бота, який розяже нам завдання - ми загружаєм в нього доску з
    //масивом значень які стоять там після замішування
    public BotSolver(Block initial) {
        this.initial = initial;
        //  Для знаходження пріоритетного завдання ми просто порівнюємо міри
        // В цьому списку будуть зберігатись всі можливі ходи від всіх можливих ходів. ДУЖЕ БАГАТО ЗНАЧЕНЬ
        PriorityQueue<Instance> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(measure(o1), measure(o2)));

        //Добавляєм початкове значення
        priorityQueue.add(new Instance(null, initial));

        while (true){
            //Дістаєм і видаляєм
            //З найменшим значенням h
            //Чим менше значення ми отримали - тим блище до розвязку ми є
            Instance item = priorityQueue.poll();
            //   перевіряєм, чи всі значення стоять на правильних позиціях
            //Якщо ми знайшли розвязок - зберігаємо всю цепочку в список
            if(item.block.isGoal()) {
                saveToList(new Instance(item, item.block));
                return;
            }

            Set<Block> it = item.block.neighbors();
            //Обираємо оптимальну перестановку
            for (Block b: it) {
                //Якщо потрібна зміна не нулл, і не було такої зміни перед тим - тоді добавляєм її в список змін
                if (b!=null && isUnique(item, b))
                    priorityQueue.add(new Instance(item, b));
            }
        }
    }
    //  Знаходим міру (Чим більша міра, тим дальше значення від правильної позиції)
    //Передаєм екземпляр. Дістаєм значення міри для цього екземпляру
    private static int measure(Instance item){
        Instance item2 = item;
        int c = 0;
        //Кількість значень, які стоять в не правильній позиції
        int measure = item.getBlock().getH();
        while (true){
            //Кількість ходів починаючи від значення, які ми передаєм
            c++;
            item2 = item2.prevBlock;
            //шукаєм, коли попередня розстановка стане нулем.
            if(item2 == null) {
                //Повертаємо кількість ходів + міру
                return measure + c;
            }
        }
    }
    //  зберігаєм список ходів в пріоритетності хід
    private void saveToList(Instance item){
        Instance item2 = item;
        while (true){
            item2 = item2.prevBlock;
            if(item2 == null) {
                //Якщо початкова позиція - робим зміну
                Collections.reverse(result);
                return;
            }
            result.add(item2.block);
        }
    }

    // Перевіряєм, чи була така позиція
    private boolean isUnique(Instance item, Block board) {
        Instance item2 = item;
        while (true) {
            if (item2.block.equals(board)) return false;
            item2 = item2.prevBlock;
            if (item2 == null) return true;
        }
    }

    // повертаємо список з потрібними ходами, щоб бот виграв в гру
    public List<Block> getResult() {
        return result;
    }

    public List<String> getCorrectMoves(){
        ArrayList<String> c = new ArrayList<>();

        for (int i = 0; i < result.size()-1; i++) {
            Block board = result.get(i);
            final Block board1 = result.get(i + 1);

            int zeroX = board.getZeroX();
            int zeroY = board.getZeroY();
            int zeroX1 = board1.getZeroX();
            int zeroY1 = board1.getZeroY();

            if (zeroX1>zeroX && zeroY1 == zeroY){
                c.add("UP");
            }
            if (zeroX1<zeroX && zeroY1 == zeroY){
                c.add("DOWN");
            }
            if (zeroX1==zeroX && zeroY1>zeroY){
                c.add("LEFT");
            }
            if (zeroX1 == zeroX && zeroY1< zeroY){
                c.add("RIGHT");
            }
        }
        return c;
    }
}

