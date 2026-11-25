import java.util.Random;

/**
 * Demonstração da Árvore Vermelho-Preto.
 *
 * Trabalho de Estruturas de Dados 2 - Unipê - Prof. Ed
 */
public class Main {

    public static void main(String[] args) {
        demonstracaoPassoAPasso();
        testeComSequenciaOrdenada(1000);
        testeComValoresAleatorios(10000);
    }

    /** Insere 10, 20, 30, 40, 50 e mostra a árvore depois de cada inserção. */
    private static void demonstracaoPassoAPasso() {
        System.out.println("=== 1) Inserindo 10, 20, 30, 40, 50 (a sequência que destrói uma BST comum) ===");
        ArvoreVermelhoPreto<Integer> arvore = new ArvoreVermelhoPreto<>();
        int[] valores = {10, 20, 30, 40, 50};

        for (int v : valores) {
            arvore.inserir(v);
            System.out.println("\nDepois de inserir " + v + ":");
            arvore.imprimir();
            System.out.println("Altura: " + arvore.altura() + " | Válida: " + arvore.valida());
        }

        System.out.println("\nEm ordem: " + arvore.emOrdem());
        System.out.println("Contém 30? " + arvore.contem(30));
        System.out.println("Contém 99? " + arvore.contem(99));
    }

    /** Insere n valores já ordenados (o pior caso para uma BST comum). */
    private static void testeComSequenciaOrdenada(int n) {
        System.out.println("\n=== 2) Inserindo " + n + " valores em ordem crescente ===");
        ArvoreVermelhoPreto<Integer> arvore = new ArvoreVermelhoPreto<>();
        for (int i = 1; i <= n; i++) {
            arvore.inserir(i);
        }
        double limite = 2 * (Math.log(n + 1) / Math.log(2));
        System.out.println("Tamanho: " + arvore.tamanho());
        System.out.println("Altura da Vermelho-Preto: " + arvore.altura());
        System.out.println("Altura de uma BST comum: " + n);
        System.out.printf("Limite teórico (2 * log2(n+1)): %.1f%n", limite);
        System.out.println("Propriedades válidas? " + arvore.valida());
    }

    /** Insere valores aleatórios e confere as propriedades. */
    private static void testeComValoresAleatorios(int n) {
        System.out.println("\n=== 3) Inserindo " + n + " valores aleatórios ===");
        ArvoreVermelhoPreto<Integer> arvore = new ArvoreVermelhoPreto<>();
        Random random = new Random(42); // semente fixa: resultado reproduzível
        for (int i = 0; i < n; i++) {
            arvore.inserir(random.nextInt(1_000_000));
        }
        System.out.println("Tamanho (sem repetidos): " + arvore.tamanho());
        System.out.println("Altura: " + arvore.altura());
        System.out.println("Propriedades válidas? " + arvore.valida());
    }
}
