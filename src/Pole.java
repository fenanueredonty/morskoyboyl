import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Pole extends JPanel {
    // Таймера отрисовки и изменения логики игры
    private Timer tmDraw;

    // Загрузка изображений
    private Image fon;
    private Image paluba;
    private Image ubit;
    private Image ranen;
    private Image end1; // win
    private Image end2; // lose
    private Image bomb;

    // переменная для реализации логики игры
    private Game myGame;

    // кнопки
    private JButton button1;
    private JButton button2;

    // координаты курсора мышки
    private int mX;
    private int mY;


    // nested class
    public class MyMouse1 implements MouseListener {
        // при нажатии на кнопки мыши
        @Override
        public void mousePressed(MouseEvent e) {
            // если сделано одиночное нажатие ЛКМ
            if ((e.getButton() == 1) && (e.getClickCount() == 1)) {
                // текущие координаты мышки
                mX = e.getX();
                mY = e.getY();

                // если внутри игрового поля компа
                if ((mX > 100) && (mY > 100) && (mX < 400) && (mY < 400)) {
                    // если ход игрока и игра не закончена
                    if ((myGame.compHod == false) && (myGame.endg == 0)) {
                        // вычисляем номер строки в массиве
                        int i = (mY - 100) / 30;
                        // вычисляем номер элемента в строке
                        int j = (mX - 100) / 30;
                        // если ячейка подходит для выстрела
                        if (myGame.masComp[i][j] <= 4) {
                            myGame.vistrelPlay(i, j);
                        }
                    }
                }

            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }
    }

    public class MyMouse2 implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // получаем координаты курсора
            mX = e.getX();
            mY = e.getY();

            if ((mX >= 100) && (mY >= 100) && (mX <= 400) && (mY <= 400)) {
                setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    public Pole() {

        // подключаем обработчики событий для мышки к панели
        addMouseListener(new MyMouse1());
        addMouseMotionListener(new MyMouse2());
        // фокус передаем панели
        setFocusable(true);
        // создаем объект новой игры
        myGame = new Game();
        // запуск
        myGame.start();


        try {
            fon = ImageIO.read(new File("C:\\Users\\kurs_java1\\Desktop\\background-seaBattle.jpg"));
            paluba = ImageIO.read(new File("C:\\Users\\kurs_java1\\Desktop\\paluba.jfif"));
            ranen = ImageIO.read(new File("C:\\Users\\kurs_java1\\Desktop\\ranen.jfif"));
            ubit = ImageIO.read(new File("C:\\Users\\kurs_java1\\Desktop\\ubit.jpeg"));
            end1 = ImageIO.read(new File("C:\\Users\\kurs_java1\\Desktop\\win.jpg"));
            end2 = ImageIO.read(new File("C:\\Users\\kurs_java1\\Desktop\\you_lose.png"));
            bomb = ImageIO.read(new File("C:\\Users\\kurs_java1\\Desktop\\bomb.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // таймер для отрисовки
        tmDraw = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // перерисовка
                repaint();
            }
        });
        tmDraw.start();

        // размещение элементов интерфейса
        setLayout(null);

        button1 = new JButton();
        button1.setText("Новая игра");
        button1.setForeground(Color.BLUE);
        button1.setFont(new Font("serif", 0, 30));
        button1.setBounds(130, 450, 200, 80);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Запуск - начало новой игры
                myGame.start();
            }
        });
        add(button1);

        button2 = new JButton();
        button2.setText("Выход");
        button2.setForeground(Color.RED);
        button2.setFont(new Font("serif", 0, 30));
        button2.setBounds(530, 450, 200, 80);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(button2);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.red);
        if ((mX > 100) && (mY > 100) && (mX < 400) && (mY < 400)) {
            if ((myGame.endg == 0) && (myGame.compHod == false)) {
                int i = (mY - 100) / 30;
                int j = (mY - 100) / 30;
                if (myGame.masComp[i][j] < 4)
                    graphics.fillRect(100 + j * 30, 100 + i * 30, 30, 30);
            }
        }
        // очищает игровое поле
        super.paintComponent(graphics);
        // установка фона
        graphics.drawImage(fon, 0, 0, 900, 600, null);
        // установка шрифта
        graphics.setFont(new Font("serif", 3, 40));
        // установка цвета
        graphics.setColor(Color.BLACK);
        // выведение надписей
        graphics.drawString("Компьютер", 150, 50);
        graphics.drawString("Игрок", 590, 50);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (myGame.masComp[i][j] != 0) {
                    if ((myGame.masComp[i][j] >= 8) && (myGame.masComp[i][j] <= 11)) {
                        graphics.drawImage(ranen, 100 + j * 30, 100 + i * 30, 30, 30, null);
                    } else if (myGame.masComp[i][j] >= 15) {
                        graphics.drawImage(ubit, 100 + j * 30, 100 + i * 30, 30, 30, null);
                    }
                    if (myGame.masComp[i][j] >= 5) {
                        graphics.drawImage(bomb, 100 + j * 30, 100 + i * 30, 30, 30, null);
                    }
                    if ((myGame.masPlay[i][j] >= 1) && (myGame.masPlay[i][j] <= 4)) {
                        graphics.drawImage(paluba, 500 + j * 30, 100 + i * 30, 30, 30, null);
                    }
                    if (myGame.masPlay[i][j] != 0) {
                        if ((myGame.masPlay[i][j] >= 8) && (myGame.masPlay[i][j] <= 11)) {
                            graphics.drawImage(ranen, 500 + j * 30, 100 + i * 30, 30, 30, null);
                        } else if (myGame.masPlay[i][j] >= 15) {
                            graphics.drawImage(ubit, 500 + j * 30, 100 + i * 30, 30, 30, null);
                        }
                        if (myGame.masPlay[i][j] >= 5) {
                            graphics.drawImage(bomb, 500 + j * 30, 100 + i * 30, 30, 30, null);
                        }
                    }
                }
            }
        }

// красный цвет для мышки
        graphics.setColor(Color.RED);

                // внутри игрового поля конструктора
                if((mX>100)&&(mY>100)&&(mX< 400)&&(mY<400)){
        // если не конец игры и ход игрока
        if((myGame.endg==0)&&(myGame.compHod==false)){
        // вычисляем номер строки в массиве
        int i=(mY-100)/30;
        // вычисляем номер элемента в строке в массиве
        int j=(mX-100)/30;

        // если подходит для выстрела
        if(myGame.masComp[i][j]<=4){
        graphics.fillRect(100+j*30,100+i*30,30,30);
        }
        }

        }

        graphics.setColor(Color.BLACK);

        for(int i=0;i<=10;i++){
        // рисование линий сетки игрового поля Компьютера
        graphics.drawLine(100+i*30,100,100+i*30,400);
        graphics.drawLine(100,100+i*30,400,100+i*30);

        // рисование линий сетки игрового поля
        graphics.drawLine(500+i*30,100,500+i*30,400);
        graphics.drawLine(500,100+i*30,800,100+i*30);
        }

        // установка шрифта
        graphics.setFont(new Font("serif",0,20));
        // установка цвета
        graphics.setColor(Color.RED);

        for(int i=1;i<=10;i++){
        // вывод цифр

        graphics.drawString(""+i,73,93+i*30);
        graphics.drawString(""+i,473,93+i*30);

        // вывод букв
        graphics.drawString(""+(char)('A'+i-1),78+i*30,93);
        graphics.drawString(""+(char)('A'+i-1),478+i*30,93);
        if(myGame.endg==1)
        {
        graphics.drawImage(end1,300,200,300,100,null);
        }
        else if(myGame.endg==2)
        {
        graphics.drawImage(end2,300,200,300,100,null);
        }

        }
        }

        }
