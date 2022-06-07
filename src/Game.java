import java.awt.desktop.AboutEvent;

public class Game {
    // массив для игрового поля игрока
    public int[][] masPlay;
    // массив для игрового поля компьютера
    public int[][] masComp;
    // признак хода компьютера (false - ходит игрок)
    public boolean compHod;
    // Признак конца игры
    public int endg; // 0 - игра идет, 1 - победил игрок, 2 - победил комп

    // конструктор
    public Game() {
        // инициализация массива
        masPlay = new int[10][10];

        // инициализация массива для компьютера
        masComp = new int[10][10];
    }

    // начало игры
    public void start() {
        // очищаем поле
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                masPlay[i][j] = 0;
                masComp[i][j] = 0;
            }
        }

        // игра идёт
        endg = 0;
        // первый ход игрока
        compHod = false;

        // метод для расстановки кораблей
        rasstanovkaKorabley(masPlay);
        rasstanovkaKorabley(masComp);

        // создание 4-хпалубного корабля


        // рисует однопалубник
        //make1P(masPlay);
    }

    private void rasstanovkaKorabley(int[][] mas) {
        //  add second parameter (mas, 4)
        make4P(mas, 4);

        for (int i = 0; i < 2; i++) {
            //  создаем трехпалубники
            make4P(mas, 3);
        }

        for (int i = 0; i < 3; i++) {
            //  создаем 2 палубники
            make4P(mas, 2);
        }
        // создаем однопалубники
        make1P(mas);

    }

    // проверка невыхода за границы массива
    private boolean testMasPoz(int i, int j) {
        if (((i >= 0) && (i <= 9)) && ((j >= 0) && (j <= 9))) {
            return true;
        } else {
            return false;
        }
    }

    // запись значения в массив с проверкой границ массива
    private void setMasValue(int[][] mas, int i, int j, int val) {
        if (testMasPoz(i, j) == true) {
            // записываем значение в массив
            mas[i][j] = val;
        }
    }

    // установить элемент
    private void setOkr(int[][] mas, int i, int j, int val) {
        if (testMasPoz(i, j) && (mas[i][j] == 0)) {
            setMasValue(mas, i, j, val);
        }
    }

    // окружение клетки
    private void okrBegin(int[][] mas, int i, int j, int val) {
        setOkr(mas, i - 1, j - 1, val); // сверху слева
        setOkr(mas, i - 1, j, val); // сверху
        setOkr(mas, i - 1, j + 1, val); // сверху справа
        setOkr(mas, i, j + 1, val); // справа
        setOkr(mas, i + 1, j + 1, val); // снизу справа
        setOkr(mas, i + 1, j, val); // снизу
        setOkr(mas, i + 1, j - 1, val); // снизу слева
        setOkr(mas, i, j - 1, val); // слева
    }

    // сделай однопалубник
    private void make1P(int[][] mas) {
        for (int k = 1; k <= 4; k++) { // 4 однопалубника
            // бесконечный цикл
            while (true) {
                int i = (int) (Math.random() * 10);
                int j = (int) (Math.random() * 10);

                if (mas[i][j] == 0) {
                    // размести однопалубник
                    mas[i][j] = 1;
                    okrBegin(mas, i, j, -1);
                    break; // прерывает цикл
                }
            }
        }
    }

    // конечное окружение
    public void okrEnd(int[][] mas) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mas[i][j] == -2) { // когда строится, значение -2
                    mas[i][j] = -1;
                }
            }
        }
    }

    // проверка ячейки для размещения в ней палубы корабля
    private boolean testNewPaluba(int[][] mas, int i, int j) {
        if (testMasPoz(i, j) == false) {
            return false;
        }
        if ((mas[i][j] == 0 || (mas[i][j] == -2))) {
            return true;
        }
        return false;
    }

    private void make4P(int[][] mas, int kolPaluba) {
        while (true) {
            boolean flag = false;

            // голова корабля
            int i = 0;
            int j = 0;

            // куда будет строиться корабль
            int napr = (int) (Math.random() * 4);

            // создание первой палубы - головы
            i = (int) (Math.random() * 10);
            j = (int) (Math.random() * 10);

            if (testNewPaluba(mas, i, j) == true) {
                if (napr == 0) { // вверх
                    if (testNewPaluba(mas, i - (kolPaluba - 1), j) == true) {
                        flag = true;
                    }
                } else if (napr == 1) { // right
                    //  можно ли расположить палубу
                    if (testNewPaluba(mas, i, j + (kolPaluba - 1)) == true) {
                        flag = true;
                    }
                } else if (napr == 2) { // down
                    if (testNewPaluba(mas, i + (kolPaluba - 1), j) == true) {
                        flag = true;
                    }
                } else if (napr == 3) { // left
                    if (testNewPaluba(mas, i, j - (kolPaluba - 1)) == true) {
                        flag = true;
                    }
                }
            }

            if (flag == true) {
                // помещаем в ячейку число палуб
                mas[i][j] = kolPaluba;

                // окружаем минус двойками
                okrBegin(mas, i, j, -2);

                if (napr == 0) {// up
                    for (int k = kolPaluba - 1; k >= 1; k--) {
                        // помещаем в ячейку число палуб
                        mas[i - k][j] = kolPaluba;
                        // окружаем минус двойками
                        okrBegin(mas, i - k, j, -2);
                    }
                } else if (napr == 1) {
                    for (int k = kolPaluba - 1; k >= 1; k--) {
                        // помещаем в ячейку число палуб
                        mas[i][j + k] = kolPaluba;
                        // окружаем минус двойками
                        okrBegin(mas, i - k, j, -2);
                    }
                } else if (napr == 2) {
                    for (int k = kolPaluba - 1; k >= 1; k--) {
                        mas[i + k][j] = kolPaluba;
                        okrBegin(mas, i + k, j, -2);
                    }
                } else if (napr == 3) {
                    for (int k = kolPaluba - 1; k >= 1; k--) {
                        mas[i][j - k] = kolPaluba;
                        okrBegin(mas, i, j - k, -2);
                    }
                }
                break;
            }
        }
        okrEnd(mas);
    }

    public void vistrelPlay(int i, int j) {
        masComp[i][j] += 7;
        testUbit(masComp, i, j);
        testEndGame();
        if (masComp[i][j] < 8) {
            compHod = true;
            while (compHod == true) compHod = compHodit();
        }

    }

    public void testUbit(int[][] mas, int i, int j) {
        if (mas[i][j] == 8) {
            mas[i][j] += 7;
            okrPodbit(mas, i, j);
        } else if (mas[i][j] == 9) analizUbit(mas, i, j, 2);
        else if (mas[i][j] == 10) analizUbit(mas, i, j, 3);
        else if (mas[i][j] == 11) analizUbit(mas, i, j, 4);
    }

    public void analizUbit(int[][] mas, int i, int j, int kolPalub) {
        int kolRanen = 0;
        for (int k = i - (kolPalub - 1); k <= i + (kolPalub - 1); k++) {
            for (int g = j - (kolPalub - 1); g <= j + (kolPalub - 1); g++) {
                if (testMasPoz(k, g) && (mas[k][g] == kolPalub + 7)) kolRanen++;
            }
        }
        if (kolRanen == kolPalub) {
            for (int k = i - (kolPalub - 1); k <= i + (kolPalub - 1); k++) {
                for (int g = j - (kolPalub - 1); g <= j + (kolPalub - 1); g++) {
                    if(testMasPoz(k,g)&&(mas[k][g]==kolPalub+7)) {
                        mas[k][g] += 7;
                        okrPodbit(mas, k, g);
                    }
                }
            }


    }

}

    private void testEndGame() {
        int testNumber = 330;
        int kolComp = 0;
        int kolPlay = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (masPlay[i][j] >= 15) kolPlay += masPlay[i][j];
                if (masComp[i][j] >= 15) kolComp += masComp[i][j];
            }
        }
        if (kolPlay == testNumber) endg = 2;
        else if (kolComp == testNumber) endg = 1;
    }


    private void setOkrPodbit(int[][] mas, int i, int j) {
        if (testMasPoz(i, j) == true) {
            if ((mas[i][j] == -1) || (mas[i][j] == 6)) mas[i][j]--;
        }
    }

    private void okrPodbit(int[][] mas, int i, int j) {
        setOkrPodbit(mas, i - 1, j - 1);
        setOkrPodbit(mas, i - 1, j);
        setOkrPodbit(mas, i - 1, j + 1);
        setOkrPodbit(mas, i, j + 1);
        setOkrPodbit(mas, i + 1, j + 1);
        setOkrPodbit(mas, i + 1, j - 1);
        setOkrPodbit(mas, i, j - 1);
    }

    private boolean compHodit() {
        boolean rez = false;
        boolean flag = false;
        _for1:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((masPlay[i][j] >= 9) && (masPlay[i][j] <= 11)) {
                    flag = true;
                    if (testMasPoz(i - 1, j) && (masPlay[i - 1][j] <= 4) && (masPlay[i - 1][j] != -2)) {
                        masPlay[i - 1][j] += 7;
                        testUbit(masPlay, i - 1, j);
                        if (masPlay[i - 1][j] >= 8) rez = true;
                        break _for1;
                    } else if (testMasPoz(i + 1, j) && (masPlay[i + 1][j] <= 4) && (masPlay[i + 1][j] != -2)) {
                        masPlay[i + 1][j] += 7;
                        testUbit(masPlay, i, j-1);
                        if (masPlay[i][j-1] >= 8) rez = true;
                        break _for1;
                    }
                    if (testMasPoz(i, j - 1) && (masPlay[i][j - 1] <= 4) && (masPlay[i][j - 1] != -2)) {
                        masPlay[i][j - 1] += 7;
                        testUbit(masPlay, i, j - 1);
                        if (masPlay[i][j - 1] >= 8) rez = true;
                        break _for1;
                    } else if (testMasPoz(i, j + 1) && (masPlay[i][j + 1] <= 4) && (masPlay[i][j + 1] != -2)) {
                        masPlay[i][j + 1] += 7;
                        if(masPlay[i][j + 1]>=8) rez = true;
                        }
                        break _for1;
                    }
                }
            }

        if (flag == false) {
            for (int l = 1; l <= 100; l++) {
                int i = (int) (Math.random() * 10);
                int j = (int) (Math.random() * 10);
                if ((masPlay[i][j] <= 4) && (masPlay[i][j] != -2)) {
                    masPlay[i][j] += 7;
                    testUbit(masPlay, i, j);
                    if (masPlay[i][j] >= 8)
                        rez = true;
                    break;
                }

            }
            if (flag == false) {
                _for2:
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if ((masPlay[i][j] <= 4) && (masPlay[i][j] != -2)) {
                            masPlay[i][j] += 7;
                            testUbit(masPlay, i, j);
                            if (masPlay[i][j] >= 8)
                                rez = true;

                            break _for2;
                        }
                    }
                }
            }
        }

        testEndGame();
        return rez;
    }
}
