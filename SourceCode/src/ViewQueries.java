import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class ViewQueries {
    private final Scanner input;
    ViewQueries() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    public String getNomeComerciante(){
        System.out.println("Introduza o nome do comerciante");
        return input.next();
    }

    public static LocalDate convertStringToLocalDate (String n) {
        LocalDate data = LocalDate.parse(n);
        return data;
    }
    public LocalDate getDate(){
        System.out.println("Introduza o ano-mes-dia");
        String n1 = input.next();
        String [] date = n1.split("-");
        LocalDate d1 =LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt (date[1]), Integer.parseInt (date[2]));
        return d1;
    }

    public void success(){
        System.out.println("Os dados necessários para estatísticas do estado do programa  foram introduzidos com sucesso!");
    }

        /*public int getConsulta (char c) {
            if (c == 'c') System.out.println("O que pretende consultar? 1 - Nome, 2-NIF");
            return input.nextInt();
        }*/

    public int inserInteger(){
        System.out.println("Insira um número");
        return input.nextInt();
    }

    public String insertString() {
        System.out.println("Insira uma String");
        return input.next();
    }

    public int consultarOsDados()
    {
        System.out.println("O que pretende consultar??");
        System.out.println("1 - Qual o comercializador com maior volume de faturação");
        System.out.println("2 - A casa que gastou mais naquele período");
        System.out.println("3 - Dar uma ordenação dos maiores consumidores de energia num período a determinar");
        System.out.println("4 - Desejo avançar dias");
        System.out.println("5 - Nada");
        return input.nextInt();
    }
    public int atividadeConsultar()
    {
        System.out.println("Pretende consultar  estatísticas sobre o estado do programa?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não ");
        return input.nextInt();
    }

    public void printMessage(String message)
    {
        System.out.println(message);
    }

}