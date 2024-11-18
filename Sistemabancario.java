package sistemabancario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistemabancario {

    // Método para depositar
    public static int depositar(int valorDeposito, int saldoAtual) {
        saldoAtual = valorDeposito + saldoAtual;
        System.out.println("Depósito realizado com sucesso. Seu saldo atual é: " + saldoAtual);
        return saldoAtual;
    }

    // Método para pagar fatura
    public static int pagar_fatura(int valor_fatura, int saldoAtual) {
        if (valor_fatura > saldoAtual) {
            System.out.println("Saldo insuficiente. Não é possível pagar a fatura.");
        } else {
            saldoAtual -= valor_fatura;
            System.out.println("Fatura paga com sucesso. Seu saldo atual é: " + saldoAtual);
        }
        return saldoAtual;
    }

    // Método para transferir valor
    public static int transferir_valor(int valor_transferencia, int saldoAtual) {
        if (valor_transferencia > saldoAtual) {
            System.out.println("Impossível realizar transferência. Saldo insuficiente.");
        } else {
            saldoAtual -= valor_transferencia;
            System.out.println("Transferência realizada com sucesso. Seu saldo atual é: " + saldoAtual);
        }
        return saldoAtual;
    }

    // Método para sacar valor
    public static int sacar_valor(int valor_saque, int saldoAtual) {
        if (valor_saque > saldoAtual) {
            System.out.println("Impossível realizar saque. Saldo insuficiente.");
        } else {
            saldoAtual -= valor_saque;
            System.out.println("Saque efetuado com sucesso. Seu saldo atual é: " + saldoAtual);
        }
        return saldoAtual;
    }

    // Método para gravar lista de contas em um arquivo
    public static void gravarContasEmArquivo(List<clienteesaldo> contas, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (clienteesaldo conta : contas) {
                writer.write("Nome: " + conta.nome + ", Conta: " + conta.conta + ", Saldo: " + conta.saldo);
                writer.newLine();
            }
            System.out.println("Lista de contas gravada com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
        }
    }

    // Método para ler contas de um arquivo e carregar como objetos clienteesaldo
    public static List<clienteesaldo> lerContasDeArquivo(String nomeArquivo) {
        List<clienteesaldo> contas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Supondo que o formato é: Nome: Vagner, Conta: 12345, Saldo: 1200
                String[] partes = linha.split(", ");
                clienteesaldo conta = new clienteesaldo();

                // Extraindo os dados de cada parte
                conta.nome = partes[0].split(": ")[1];
                conta.conta = Integer.parseInt(partes[1].split(": ")[1]);
                conta.saldo = Integer.parseInt(partes[2].split(": ")[1]);

                // Adicionando o objeto à lista
                contas.add(conta);
            }
            System.out.println("Contas carregadas com sucesso do arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return contas;
    }

    // Método principal
    public static void main(String[] args) {
        Scanner leia = new Scanner(System.in);
        List<clienteesaldo> contas = new ArrayList<>();

        // Criando e manipulando um cliente
        clienteesaldo usuario = new clienteesaldo();
        usuario.saldo = 1200; // Saldo inicial
        usuario.nome = "Vagner";
        usuario.conta = 12345; // Número da conta
        contas.add(usuario);

        // Resolução do método depositar
        System.out.println("Digite o valor do depósito:");
        int deposito = leia.nextInt();
        usuario.saldo = depositar(deposito, usuario.saldo);

        // Resolução Pagamento de fatura
        System.out.println("Digite o valor da fatura:");
        int fatura = leia.nextInt();
        usuario.saldo = pagar_fatura(fatura, usuario.saldo);

        // Resolução Transferência
        System.out.println("Digite o valor que deseja transferir:");
        int transferencia = leia.nextInt();
        usuario.saldo = transferir_valor(transferencia, usuario.saldo);

        // Resolução Saque
        System.out.println("Digite a quantia que quer sacar:");
        int saque = leia.nextInt();
        usuario.saldo = sacar_valor(saque, usuario.saldo);

        // Gravar lista de contas no arquivo
        String nomeArquivo = "contas_bancarias.txt";
        gravarContasEmArquivo(contas, nomeArquivo);

        // Ler as contas de um arquivo
        List<clienteesaldo> contasLidas = lerContasDeArquivo(nomeArquivo);
        for (clienteesaldo conta : contasLidas) {
            System.out.println("Conta carregada: Nome: " + conta.nome + ", Conta: " + conta.conta + ", Saldo: " + conta.saldo);
        }

        leia.close();
    }
}
