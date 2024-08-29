import java.util.HashMap;
import java.util.Map;
import java.util.InputMismatchException;
import java.util.Scanner;

// Classe Produto
class Produto {
    private String nome;
    private int quantidade;
    private double preco;

    public Produto(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto: " + nome + ", Quantidade: " + quantidade + ", Preço: " + preco;
    }
}

// Classe Estoque
class Estoque {
    private Map<String, Produto> produtos;

    public Estoque() {
        this.produtos = new HashMap<>();
    }

    public void adicionarProduto(String nome, int quantidade, double preco) {
        if (produtos.containsKey(nome)) {
            Produto produto = produtos.get(nome);
            produto.setQuantidade(produto.getQuantidade() + quantidade);
        } else {
            Produto novoProduto = new Produto(nome, quantidade, preco);
            produtos.put(nome, novoProduto);
        }
    }

    public void removerProduto(String nome, int quantidade) {
        if (produtos.containsKey(nome)) {
            Produto produto = produtos.get(nome);
            int quantidadeAtual = produto.getQuantidade();
            if (quantidadeAtual >= quantidade) {
                produto.setQuantidade(quantidadeAtual - quantidade);
            } else {
                System.out.println("Quantidade insuficiente no estoque!");
            }
        } else {
            System.out.println("Produto não encontrado no estoque!");
        }
    }

    public void listarProdutos() {
        for (Produto produto : produtos.values()) {
            System.out.println(produto);
        }
    }

    public double obterPrecoProduto(String nome) {
        if (produtos.containsKey(nome)) {
            return produtos.get(nome).getPreco();
        }
        return 0.0; // Retorna 0.0 se o produto não for encontrado
    }
}

// Classe Adega
public class Adega {
    private Estoque estoque;

    public Adega() {
        this.estoque = new Estoque();
    }

    public void registrarEntrada(String nome, int quantidade) {
        double preco = estoque.obterPrecoProduto(nome);
        if (preco == 0.0) {
            Scanner scanner = new Scanner(System.in);
            boolean entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.print("Preço do produto " + nome + ": ");
                    preco = scanner.nextDouble();
                    entradaValida = true;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, insira um valor numérico para o preço.");
                    scanner.next(); // Limpar a entrada inválida
                }
            }
        }
        estoque.adicionarProduto(nome, quantidade, preco);
        System.out.println("Entrada registrada: " + quantidade + " unidades de " + nome + " ao preço de " + preco);
    }

    public void registrarSaida(String nome, int quantidade) {
        estoque.removerProduto(nome, quantidade);
        System.out.println("Saída registrada: " + quantidade + " unidades de " + nome);
    }

    public void mostrarEstoque() {
        System.out.println("Estoque atual:");
        estoque.listarProdutos();
    }

    public static void main(String[] args) {
        Adega adega = new Adega();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Registrar Entrada");
            System.out.println("2. Registrar Saída");
            System.out.println("3. Mostrar Estoque");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            if (opcao == 1) {
                System.out.print("Nome do Produto: ");
                String nome = scanner.next();
                System.out.print("Quantidade: ");
                int quantidade = scanner.nextInt();
                adega.registrarEntrada(nome, quantidade);
            } else if (opcao == 2) {
                System.out.print("Nome do Produto: ");
                String nome = scanner.next();
                System.out.print("Quantidade: ");
                int quantidade = scanner.nextInt();
                adega.registrarSaida(nome, quantidade);
            } else if (opcao == 3) {
                adega.mostrarEstoque();
            } else if (opcao == 4) {
                break;
            } else {
                System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}
