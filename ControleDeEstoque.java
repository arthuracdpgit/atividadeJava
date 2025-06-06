import java.util.Scanner;

public class ControleDeEstoque {
    private static final int MAX_PRODUTOS = 10;
    private static Produto[] produtos = new Produto[MAX_PRODUTOS];
    private static int qtdProdutos = 0;
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            mostrarMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    filtrarPorCategoria();
                    break;
                case 4:
                    ordenarProdutosMenu();
                    break;
                case 5:
                    removerProduto();
                    break;
                case 6:
                    atualizarPreco();
                    break;
                case 7:
                    subtotalPorCategoria();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n==== MENU ====");
        System.out.println("1. Cadastro do produto");
        System.out.println("2. Listar todos os produtos");
        System.out.println("3. Filtrar por categoria");
        System.out.println("4. Ordenar produtos");
        System.out.println("5. Remover produto");
        System.out.println("6. Atualizar preço");
        System.out.println("7. Ordenar subtotal em estoque por categoria");
        System.out.println("0. Sair");
    }

    private static int lerInt(String msg) {
        System.out.print(msg);
        while (!input.hasNextInt()) {
            input.next(); // limpar entrada inválida
            System.out.print("Digite um número válido: ");
        }
        int val = input.nextInt();
        input.nextLine(); // limpar buffer
        return val;
    }

    private static double lerDouble(String msg) {
        System.out.print(msg);
        while (!input.hasNextDouble()) {
            input.next();
            System.out.print("Digite um número válido: ");
        }
        double val = input.nextDouble();
        input.nextLine();
        return val;
    }

    private static void cadastrarProduto() {
        if (qtdProdutos >= MAX_PRODUTOS) {
            System.out.println("Limite de produtos atingido!");
            return;
        }

        Produto p = new Produto();

        System.out.print("Nome/Descrição: ");
        p.nomeDescricao = input.nextLine();

        System.out.print("Quantidade em estoque: ");
        p.quantidadeEstoque = lerInt("");

        System.out.print("Preço unitário: ");
        p.precoUnitario = lerDouble("");

        System.out.print("Categoria: ");
        p.categoria = input.nextLine();

        System.out.print("Quantidade mínima: ");
        p.quantidadeMinima = lerInt("");

        produtos[qtdProdutos] = p;
        qtdProdutos++;
        System.out.println("Produto cadastrado com sucesso!");
    }

    private static void listarProdutos() {
        if (qtdProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (int i = 0; i < qtdProdutos; i++) {
            Produto p = produtos[i];
            System.out.printf("Nome/Descrição: %s | Qtde: %d | Preço: R$ %.2f | Categoria: %s | Qtde Mín: %d\n",
                    p.nomeDescricao, p.quantidadeEstoque, p.precoUnitario, p.categoria, p.quantidadeMinima);
        }
    }

    private static void filtrarPorCategoria() {
        if (qtdProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.print("Digite a categoria para filtrar: ");
        String cat = input.nextLine();

        boolean achou = false;
        for (int i = 0; i < qtdProdutos; i++) {
            Produto p = produtos[i];
            if (p.categoria.equalsIgnoreCase(cat)) {
                System.out.printf("%s - %d unidades - R$ %.2f\n", p.nomeDescricao, p.quantidadeEstoque, p.precoUnitario);
                achou = true;
            }
        }

        if (!achou) {
            System.out.println("Nenhum produto encontrado para essa categoria.");
        }
    }

    private static void ordenarProdutosMenu() {
        if (qtdProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.println("Ordenar por:");
        System.out.println("1 - Nome/Descrição");
        System.out.println("2 - Quantidade");
        System.out.println("3 - Preço Unitário");
        System.out.println("4 - Categoria");
        System.out.println("5 - Quantidade Mínima");
        int opc = lerInt("Escolha: ");

        ordenarProdutos(opc);
        System.out.println("Produtos ordenados com sucesso!");
    }

    private static void ordenarProdutos(int criterio) {
        // Usando insertion sort como no exemplo do seu professor
        for (int i = 1; i < qtdProdutos; i++) {
            Produto chave = produtos[i];
            int j = i - 1;

            while (j >= 0 && comparar(produtos[j], chave, criterio) > 0) {
                produtos[j + 1] = produtos[j];
                j--;
            }
            produtos[j + 1] = chave;
        }
    }

    private static int comparar(Produto p1, Produto p2, int criterio) {
        switch (criterio) {
            case 1:
                return p1.nomeDescricao.compareToIgnoreCase(p2.nomeDescricao);
            case 2:
                return Integer.compare(p1.quantidadeEstoque, p2.quantidadeEstoque);
            case 3:
                return Double.compare(p1.precoUnitario, p2.precoUnitario);
            case 4:
                return p1.categoria.compareToIgnoreCase(p2.categoria);
            case 5:
                return Integer.compare(p1.quantidadeMinima, p2.quantidadeMinima);
            default:
                return 0;
        }
    }

    private static void removerProduto() {
        if (qtdProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.print("Digite o nome/descrição do produto para remover: ");
        String nome = input.nextLine();

        int pos = -1;
        for (int i = 0; i < qtdProdutos; i++) {
            if (produtos[i].nomeDescricao.equalsIgnoreCase(nome)) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("Produto não encontrado.");
            return;
        }

        for (int i = pos; i < qtdProdutos - 1; i++) {
            produtos[i] = produtos[i + 1];
        }
        produtos[qtdProdutos - 1] = null;
        qtdProdutos--;
        System.out.println("Produto removido com sucesso.");
    }

    private static void atualizarPreco() {
        if (qtdProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.print("Digite o nome/descrição do produto para atualizar preço: ");
        String nome = input.nextLine();

        int pos = -1;
        for (int i = 0; i < qtdProdutos; i++) {
            if (produtos[i].nomeDescricao.equalsIgnoreCase(nome)) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.printf("Preço atual: R$ %.2f\n", produtos[pos].precoUnitario);
        double novoPreco = lerDouble("Digite o novo preço: ");
        produtos[pos].precoUnitario = novoPreco;
        System.out.println("Preço atualizado com sucesso.");
    }

    private static void subtotalPorCategoria() {
        if (qtdProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        // Agrupar produtos por categoria usando arrays auxiliares simples
        // Primeiro obter categorias únicas:
        String[] categorias = new String[MAX_PRODUTOS];
        int qtdCategorias = 0;

        for (int i = 0; i < qtdProdutos; i++) {
            String cat = produtos[i].categoria;
            boolean existe = false;
            for (int j = 0; j < qtdCategorias; j++) {
                if (categorias[j].equalsIgnoreCase(cat)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                categorias[qtdCategorias++] = cat;
            }
        }

        // Para cada categoria, listar produtos e subtotal
        for (int i = 0; i < qtdCategorias; i++) {
            String cat = categorias[i];
            System.out.println("Categoria: " + cat);
            double subtotal = 0;

            for (int j = 0; j < qtdProdutos; j++) {
                if (produtos[j].categoria.equalsIgnoreCase(cat)) {
                    Produto p = produtos[j];
                    System.out.printf("%s - %d unidades - R$ %.2f\n",
                            p.nomeDescricao, p.quantidadeEstoque, p.precoUnitario);
                    subtotal += p.quantidadeEstoque * p.precoUnitario;
                }
            }
            System.out.printf("Subtotal: R$ %.2f\n\n", subtotal);
        }
    }
}
