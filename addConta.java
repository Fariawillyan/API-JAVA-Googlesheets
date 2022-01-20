import java.util.Scanner;

import static java.lang.System.in;

public class addConta {
    /**
     * Classe criada somente para acessar os atributos do scanner.
     * Reforçando a implementação dessa classe, os dados primitivos usado abaixo foram criado apenas para rodar
     * e testar a conexão com o DB da aplicação, visto que, Necessita de fazer alteração para float, int e double
     * para a aplicação ser mais exata.  */


            static Scanner entrada = new Scanner(in);
            static String nome =  entrada.next();
            static String agencia = entrada.next();
            static String contaCorrente = entrada.next();
            static String banco = entrada.next();
            static  String cpf = entrada.next();
            static  String ativoDigital = entrada.next();
            static  String saldo = entrada.next();
}