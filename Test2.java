//В файле хранятся координаты вершин четырехугольника в порядке обхода фигуры по часовой стрелке в виде:
//<координата x1> <координата y1>
//<координата x2> <координата y2>
//<координата x3> <координата y3>
//<координата x4> <координата y4>
//
//Считаем, что полученные из файла вершины гарантированно образуют выпуклый четырехугольник. Написать программу, которая считывает координаты из файла. 
//При запуске ждет от пользователя ввода координат некой точки и выводит один из четырех возможных результатов: 
//точка внутри четырехугольника
//точка лежит на сторонах четырехугольника
//точка - вершина четырехугольника
//точка снаружи четырехугольника
package test2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author Александр Машьянов, mashyanov1987@gmail.com, +7(981)784-79-67
 */
public class Test2 {

    public static void main(String[] args) {
        /**Коллекция, которая будет содержать координаты вершин четырёхугольника*/
        ArrayList<Integer> coordinates = new ArrayList<>();
        try {
            //ЗАПРАШИВАЕМ У ПОЛЬЗОВАТЕЛЯ ПУТЬ К ФАЙЛУ
            File file = new File(JOptionPane.showInputDialog("Введите адрес к файлу", "test.txt"));
            if(!file.exists()) throw new FileNotFoundException("File was not found");
            
            //СЧИТЫВАЕМ КООРДИНАТЫ ИЗ ФАЙЛА, ЗАДАННОГО ПОЛЬЗОВАТЕЛЕМ
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()) coordinates.add(scanner.nextInt());
            if(coordinates.size()!=8) throw new IOException("Неверное количество данных в файле");
            }catch (IOException ex) {
                System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(new JPanel(), "Exception:\n" + ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
        do{
            try{
                //ЗАПРАШИВАЕМ У ПОЛЬЗОВАТЕЛЯ КООРДИНАТЫ ТОЧКИ
                int x = Integer.parseInt(JOptionPane.showInputDialog(new JPanel(), "Input X"));
                int y = Integer.parseInt(JOptionPane.showInputDialog(new JPanel(), "Input Y"));
                System.out.println(checkThePoint(x, y, coordinates));
            }
            catch(NumberFormatException e)  {
                System.err.println("Неверный формат ввода координаты");
                JOptionPane.showMessageDialog(new JPanel(), "NumberFormatException:\n" + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                return;
            }    
        }
        while (JOptionPane.showConfirmDialog(new JPanel(), "Check another point?", "Again?", JOptionPane.YES_NO_OPTION)==0);
    }
    /**Метод, проверяющий точку
     * @param x - Х-координата точки
     * @param y - Y-координата точки
     * @param coordinates коллекция, соодержащая координаты вершин четырёхугольника
     * @return строку, содержащую один из ответов, представленных в задании */
    static final String checkThePoint(int x, int y, ArrayList<Integer> coordinates){
        float x1, x2;
        int minY, maxY, minX, maxX;
               
        //ПРОВЕРЯЕМ, НЕ ВЕРШИНУ ЛИ НАМ ВВЕЛ ПОЛЬЗОВАТЕЛЬ
        if((x == coordinates.get(0) && y == coordinates.get(1)) || 
                (x == coordinates.get(2) && y == coordinates.get(3)) || 
                (x == coordinates.get(4) && y == coordinates.get(5)) || 
                (x == coordinates.get(6) && y == coordinates.get(7)))
            return "точка - вершина четырехугольника";
        
        //НАХОДИМ САМОЕ ВЫСОКОЕ И САМОЕ НИЗКОЕ ЗНАЧЕНИЯ ПО X И ПО Y ДЛЯ ЧЕТЫРЁХУГОЛЬНИКА
        minY = maxY = coordinates.get(1);
        minX = maxX = coordinates.get(0);
        for (int i = 1; i < coordinates.size(); i+=2) {
            if(coordinates.get(i) > maxY) maxY = coordinates.get(i);
            if(coordinates.get(i) < minY) minY = coordinates.get(i);
        }
        for (int i = 0; i < coordinates.size(); i+=2) {
            if(coordinates.get(i) > maxX) maxX = coordinates.get(i);
            if(coordinates.get(i) < minX) minX = coordinates.get(i);
        }
        //ЛЕГКАЯ ПРОВЕРКА НА ПРЕДМЕТ ТОГО, ВДРУГ НАША ТОЧКА ТОЧНО ЗА ПРЕДЕЛАМИ ЧЕТЫРЁХУГОЛЬНИКА:
        if(x > maxX || x < minX || y > maxY || y < minY)
            return "точка снаружи четырехугольника";
       
        //ПРОВОДИМ ЧЕРЕЗ ТОЧКУ ЛУЧ, ПАРАЛЛЕЛЬНЫЙ ОСИ Y. НАХОДИМ ТОЧКИ ПЕРЕСЕЧЕНИЯ
        //СТОРОН НАШЕГО ЧЕТЫРЁХУГОЛЬНИКА ЛУЧОМ, СТРОЯ УРОВНЕНИЯ ПРЯМЫХ, ЯВЛЯЮЩИСХСЯ 
        //"ВЕРТИКАЛЬНЫМИ" ГРАНЯМИ ЧЕТЫРЁХУГОЛЬНИКА 
        x1 = ((((float)y - coordinates.get(1)) * (coordinates.get(6) - coordinates.get(0)) / 
                (coordinates.get(7) - coordinates.get(1))) + coordinates.get(0));
        x2 = ((((float)y - coordinates.get(3)) * (coordinates.get(4) - coordinates.get(2)) / 
                (coordinates.get(5) - coordinates.get(3))) + coordinates.get(2));
        //СРАВНИВАЕМ ПОЛУЧЕНЫЫЕ ТОЧКИ ПЕРЕСЕЧЕНИЯ С Х-КООРДИНАТОЙ ЗАДАННОЙ ТОЧКИ, 
        //ДЕЛАЕМ ОКОНЧАТЕЛЬНЫЕ ВЫВОДЫ.
        if((float)x > x1 && (float)x < x2) return "точка внутри четырехугольника";
        if((float)x < x1 || (float)x > x2) return "точка снаружи четырехугольника";
   
        return "точка лежит на сторонах четырехугольника";
    }
}       
