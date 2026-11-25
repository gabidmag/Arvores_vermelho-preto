/**
 * Árvore Vermelho-Preto (Red-Black Tree) com inserção, busca e verificação das propriedades.
 *
 * Trabalho de Estruturas de Dados 2 - Unipê - Prof. Ed
 *
 * Usa Generics (T extends Comparable<T>) para funcionar com qualquer tipo comparável
 * (Integer, String, etc.).
 *
 * As 5 propriedades:
 *  1. Todo nó é vermelho ou preto.
 *  2. A raiz é sempre preta.
 *  3. Toda folha NIL (referência null) conta como preta.
 *  4. Um nó vermelho nunca tem filho vermelho.
 *  5. Todo caminho de um nó até as folhas NIL tem o mesmo número de nós pretos (altura negra).
 *
 * Observação: a remoção ficou fora deste trabalho, para manter o foco na inserção.
 */
public class ArvoreVermelhoPreto<T extends Comparable<T>> {

    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;

    /** Nó da árvore: valor, cor e referências para filhos e pai. */
    private class No {
        T valor;
        boolean cor = VERMELHO;  // todo nó novo entra vermelho (não altera a altura negra)
        No esq, dir;
        No pai;                  // o pai é necessário para achar o tio e o avô na correção

        No(T valor) {
            this.valor = valor;
        }
    }

    private No raiz;
    private int tamanho;

    // ---------------------------------------------------------------------
    // Inserção
    // ---------------------------------------------------------------------

    /** Insere um valor. Valores repetidos são ignorados. */
    public void inserir(T valor) {
        // 1) Inserção normal de árvore binária de busca
        No pai = null;
        No atual = raiz;
        while (atual != null) {
            pai = atual;
            int cmp = valor.compareTo(atual.valor);
            if (cmp < 0) {
                atual = atual.esq;
            } else if (cmp > 0) {
                atual = atual.dir;
            } else {
                return; // valor já existe
            }
        }

        No novo = new No(valor); // nasce vermelho
        novo.pai = pai;
        if (pai == null) {
            raiz = novo;
        } else if (valor.compareTo(pai.valor) < 0) {
            pai.esq = novo;
        } else {
            pai.dir = novo;
        }
        tamanho++;

        // 2) Conserta as propriedades que podem ter sido quebradas
        corrigirInsercao(novo);
    }

    /**
     * Restaura as propriedades após a inserção.
     * Só a propriedade 4 (dois vermelhos seguidos) ou a 2 (raiz vermelha) podem quebrar.
     */
    private void corrigirInsercao(No n) {
        // Enquanto o pai for vermelho, há dois vermelhos seguidos (propriedade 4 quebrada)
        while (n.pai != null && n.pai.cor == VERMELHO) {
            No pai = n.pai;
            No avo = pai.pai; // nunca é null: pai vermelho não pode ser a raiz (raiz é preta)

            if (pai == avo.esq) {
                No tio = avo.dir;

                if (corDe(tio) == VERMELHO) {
                    // CASO 2: pai e tio vermelhos -> recolorir e continuar subindo
                    pai.cor = PRETO;
                    tio.cor = PRETO;
                    avo.cor = VERMELHO;
                    n = avo;
                } else {
                    // CASO 3: tio preto (ou NIL) -> rotacionar
                    if (n == pai.dir) {
                        // triângulo (esquerda-direita): primeiro rotação para virar linha reta
                        n = pai;
                        rotacaoEsquerda(n);
                        pai = n.pai;
                    }
                    // linha reta (esquerda-esquerda): uma rotação à direita no avô
                    pai.cor = PRETO;
                    avo.cor = VERMELHO;
                    rotacaoDireita(avo);
                }
            } else {
                // Caso espelhado: o pai é filho direito do avô
                No tio = avo.esq;

                if (corDe(tio) == VERMELHO) {
                    // CASO 2 (espelhado)
                    pai.cor = PRETO;
                    tio.cor = PRETO;
                    avo.cor = VERMELHO;
                    n = avo;
                } else {
                    // CASO 3 (espelhado)
                    if (n == pai.esq) {
                        // triângulo (direita-esquerda): rotação à direita no pai
                        n = pai;
                        rotacaoDireita(n);
                        pai = n.pai;
                    }
                    // linha reta (direita-direita): rotação à esquerda no avô
                    pai.cor = PRETO;
                    avo.cor = VERMELHO;
                    rotacaoEsquerda(avo);
                }
            }
        }
        // Propriedade 2: a raiz sempre termina preta
        raiz.cor = PRETO;
    }

    /** Cor de um nó; NIL (null) conta como preto (propriedade 3). */
    private boolean corDe(No n) {
        return n == null ? PRETO : n.cor;
    }

    // ---------------------------------------------------------------------
    // Rotações (mudam a estrutura, mas mantêm a ordem da árvore de busca)
    // ---------------------------------------------------------------------

    /**
     * Rotação à esquerda em x: o filho direito (y) sobe e x desce para a esquerda de y.
     *
     *      x                y
     *     / \              / \
     *    a   y    -->     x   c
     *       / \          / \
     *      b   c        a   b
     */
    private void rotacaoEsquerda(No x) {
        No y = x.dir;          // y vai subir
        x.dir = y.esq;         // a subárvore b troca de pai: passa a ser filha direita de x
        if (y.esq != null) {
            y.esq.pai = x;
        }
        y.pai = x.pai;         // y assume o lugar de x, ligando-se ao pai de x
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.esq) {
            x.pai.esq = y;
        } else {
            x.pai.dir = y;
        }
        y.esq = x;             // x desce para a esquerda de y
        x.pai = y;
    }

    /** Rotação à direita em x: espelho da rotação à esquerda. */
    private void rotacaoDireita(No x) {
        No y = x.esq;          // y vai subir
        x.esq = y.dir;         // a subárvore do meio troca de pai
        if (y.dir != null) {
            y.dir.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.dir) {
            x.pai.dir = y;
        } else {
            x.pai.esq = y;
        }
        y.dir = x;             // x desce para a direita de y
        x.pai = y;
    }

    // ---------------------------------------------------------------------
    // Busca e consultas
    // ---------------------------------------------------------------------

    /** Retorna true se o valor está na árvore. Custa O(log n) no pior caso. */
    public boolean contem(T valor) {
        No atual = raiz;
        while (atual != null) {
            int cmp = valor.compareTo(atual.valor);
            if (cmp == 0) return true;
            atual = (cmp < 0) ? atual.esq : atual.dir;
        }
        return false;
    }

    public int tamanho() {
        return tamanho;
    }

    /** Altura da árvore (número de níveis). Árvore vazia tem altura 0. */
    public int altura() {
        return altura(raiz);
    }

    private int altura(No n) {
        if (n == null) return 0;
        return 1 + Math.max(altura(n.esq), altura(n.dir));
    }

    // ---------------------------------------------------------------------
    // Verificação das propriedades (útil para testar e para mostrar no vídeo)
    // ---------------------------------------------------------------------

    /** Confere as propriedades 2, 4 e 5. As propriedades 1 e 3 valem por construção. */
    public boolean valida() {
        if (raiz == null) return true;
        if (raiz.cor != PRETO) return false;      // propriedade 2
        return alturaNegra(raiz) != -1;           // propriedades 4 e 5
    }

    /** Retorna a altura negra da subárvore, ou -1 se alguma propriedade estiver violada. */
    private int alturaNegra(No n) {
        if (n == null) return 1; // NIL conta como um nó preto
        if (n.cor == VERMELHO
                && (corDe(n.esq) == VERMELHO || corDe(n.dir) == VERMELHO)) {
            return -1;           // propriedade 4 violada
        }
        int e = alturaNegra(n.esq);
        int d = alturaNegra(n.dir);
        if (e == -1 || d == -1 || e != d) {
            return -1;           // propriedade 5 violada
        }
        return e + (n.cor == PRETO ? 1 : 0);
    }

    // ---------------------------------------------------------------------
    // Impressão
    // ---------------------------------------------------------------------

    /** Percurso em ordem (esquerda, raiz, direita): imprime os valores ordenados com a cor. */
    public String emOrdem() {
        StringBuilder sb = new StringBuilder();
        emOrdem(raiz, sb);
        return sb.toString().trim();
    }

    private void emOrdem(No n, StringBuilder sb) {
        if (n == null) return;
        emOrdem(n.esq, sb);
        sb.append(n.valor).append(n.cor == PRETO ? "(P) " : "(V) ");
        emOrdem(n.dir, sb);
    }

    /**
     * Desenha a árvore "deitada" no terminal: a raiz fica à esquerda,
     * o filho direito aparece em cima e o esquerdo embaixo. (P) = preto, (V) = vermelho.
     */
    public void imprimir() {
        if (raiz == null) {
            System.out.println("(árvore vazia)");
            return;
        }
        imprimir(raiz, 0);
    }

    private void imprimir(No n, int nivel) {
        if (n == null) return;
        imprimir(n.dir, nivel + 1);
        System.out.println("    ".repeat(nivel) + n.valor + (n.cor == PRETO ? "(P)" : "(V)"));
        imprimir(n.esq, nivel + 1);
    }
}
