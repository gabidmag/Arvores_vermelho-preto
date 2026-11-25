# Árvores Vermelho-Preto em Java

Trabalho de **Estruturas de Dados 2** · **Unipê – Centro Universitário de João Pessoa** · **Prof. Ed**

**Integrantes:** Gabriela Dias 
**Tema:** Árvores Vermelho-Preto (Red-Black Trees)
**Vídeo:** https://youtu.be/8wp2gkgliN4

## O que é

A árvore Vermelho-Preto é uma árvore binária de busca **auto-balanceada**. Cada nó guarda uma cor extra (vermelha ou preta) e, seguindo 5 regras de coloração, a árvore garante altura de no máximo `2 · log2(n + 1)`. Assim, busca e inserção custam **O(log n) no pior caso**, mesmo com valores inseridos em ordem crescente (o caso que transforma uma BST comum em uma lista ligada).

## As 5 propriedades

1. Todo nó é vermelho ou preto.
2. A raiz é sempre preta.
3. Toda folha NIL (referência `null`) conta como preta.
4. Um nó vermelho nunca tem filho vermelho.
5. Todo caminho de um nó até as folhas NIL tem o mesmo número de nós pretos (altura negra).

## Como a inserção se mantém balanceada

Todo nó novo entra **vermelho**. Se uma regra quebrar, olhamos o pai, o tio e o avô do nó novo:

| Caso | Situação | Solução |
|------|----------|---------|
| 1 | Pai preto | Nada a fazer |
| 2 | Pai e tio vermelhos | Recolorir (pai e tio pretos, avô vermelho) e continuar subindo |
| 3 | Pai vermelho, tio preto/NIL | Rotacionar: uma rotação se estiver em linha reta, duas se formar um triângulo |

No final, a raiz é sempre pintada de preto.

## Estrutura do repositório

```
.
├── README.md
├── .gitignore
└── src
    ├── ArvoreVermelhoPreto.java   # a árvore (inserção, busca, rotações, validação)
    └── Main.java                  # demonstração e testes
```

- `ArvoreVermelhoPreto<T extends Comparable<T>>` usa **Generics**, então funciona com `Integer`, `String` etc.
- O método `valida()` confere as propriedades 2, 4 e 5, e é usado nos testes.
- A **remoção** não está implementada: o trabalho foca na inserção.

## Como compilar e executar

Requisito: **JDK 11 ou superior** (testado com Java 21).

```bash
# 1. clonar o repositório
git clone [link-do-repositório]
cd [nome-do-repositório]

# 2. compilar
mkdir out
javac -d out src/*.java

# 3. executar
java -cp out Main
```

## O que o programa mostra

1. **Passo a passo:** insere `10, 20, 30, 40, 50` e desenha a árvore depois de cada inserção (`P` = preto, `V` = vermelho). A árvore é desenhada "deitada": a raiz fica à esquerda, o filho direito em cima e o esquerdo embaixo.
2. **Sequência ordenada:** insere 1000 valores crescentes. A altura fica em torno de 17, contra 1000 de uma BST comum.
3. **Valores aleatórios:** insere 10000 valores e confirma que todas as propriedades continuam válidas.

Resultado final do passo a passo (`10, 20, 30, 40, 50`):

```
        50(V)
    40(P)
        30(V)
20(P)
    10(P)
```


