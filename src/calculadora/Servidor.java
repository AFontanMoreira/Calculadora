package calculadora;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Servidor {

    public static void main(String[] args) throws IOException {

            System.out.println("Creando socket servidor");

            ServerSocket serverSocket = new ServerSocket();

            System.out.println("Realizando el bind");


            InetSocketAddress addr = new InetSocketAddress("localhost",6000);
            serverSocket.bind(addr);
            Socket newSocket = serverSocket.accept();
            System.out.println("Aceptando conexiones");
            DataInputStream in = new DataInputStream(newSocket.getInputStream());
            DataOutputStream ou = new DataOutputStream(newSocket.getOutputStream());

            while (true) {

                    System.out.println("Antes de la operacion");
                    String operacionCliente = in.readUTF();

                    System.out.println("Operación " + operacionCliente);
                    int resultado;

                    StringTokenizer st = new StringTokenizer(operacionCliente);

                    int operando1 ;
                    String operador ;
                    int operando2 ;

                    operando1 = Integer.parseInt(st.nextToken());
                    operador = st.nextToken();
                    try {
                            operando2 = Integer.parseInt(st.nextToken());
                    } catch (NoSuchElementException e) {
                            if (operador.equals("/")) {
                                    operando2 = 1;
                            } else {
                                    operando2 = 0;
                            }
                    }

                    if (operador.equals("+")) {
                            resultado = operando1+operando2;
                    } else if (operador.equals("-")) {
                            resultado = operando1-operando2;
                    } else if (operador.equals("*")) {
                            resultado = operando1*operando2;
                    } else {
                            resultado = operando1/operando2;
                    }


                    ou.writeUTF(Integer.toString(resultado));
            }
    }
}
