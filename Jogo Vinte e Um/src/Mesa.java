
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mesa {
    private static Jogador vencedor = null;
    private static int numeroRodada = 1;
    private static final int NUMERO_MAXIMO_RODADAS = 5;
    private static int numJogadores;

    public static void main(String[] args) throws InterruptedException {
        exibirBoasVindas();

        Jogador p1 = new Jogador("Lucas");
        Jogador p2 = new Jogador("Pedro");
        Jogador p3 = new Jogador("Joao");
        Jogador p4 = new Jogador("Thiago");

        Jogador[] jogadores = {p1, p2, p3, p4};

        // Inicializa o saldo dos jogadores
        for (Jogador jogador : jogadores) {
            Aposta.inicializarSaldo(jogador);
        }

        numJogadores = jogadores.length;
        for (int i = 0; i < NUMERO_MAXIMO_RODADAS; i++) {
            System.out.println("\n=================== Inicio da Rodada " + (i + 1) + " ===================\n");
            jogarRodada(jogadores);
            numeroRodada++;
        }

        exibirRankingFinal(jogadores);
    }

    private static void exibirBoasVindas() {
        System.out.println("************************************");
        System.out.println("*                                  *");
        System.out.println("*    Bem-vindo ao meu jogo de      *");
        System.out.println("*            Vinte e Um!!!         *");
        System.out.println("*                                  *");
        System.out.println("************************************");

        System.out.println("============================================");
        System.out.println("|                                          |");
        System.out.println("|          INICIO DO JOGO                  |");
        System.out.println("|                                          |");
        System.out.println("|  Todos os jogadores iniciam com um       |");
        System.out.println("|           saldo inicial de " + Aposta.getSaldoInicial() + " reais!  |");
        System.out.println("|                                          |");
        System.out.println("============================================");
    }

    private static void jogarRodada(Jogador[] jogadores) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                jogadores[0].jogar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                jogadores[1].jogar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                jogadores[2].jogar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Thread t4 = new Thread(() -> {
            try {
                jogadores[3].jogar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        encontrarVencedor(jogadores);
        Aposta.processarResultado(jogadores, vencedor);
        anunciarVencedor();
        exibirRanking(jogadores);

        System.out.println();
        System.out.println("=================== Fim da Rodada =======================");
    }


    private static void encontrarVencedor(Jogador[] jogadores) {
        vencedor = null;
        int maiorPontuacao = 0;
        boolean empate = false;

        for (Jogador jogador : jogadores) {
            if (jogador.isAtivo()) {
                int pontos = jogador.calcularPontos();
                if (pontos > maiorPontuacao && pontos <= 21) {
                    vencedor = jogador;
                    maiorPontuacao = pontos;
                    empate = false; // Se um jogador tem uma pontuação maior, não é mais um empate
                } else if (pontos == maiorPontuacao && pontos <= 21) {
                    empate = true; // Se um jogador tem a mesma pontuação que o líder, é um empate
                }
            }
        }

        if (empate) {
            vencedor = null; // Se houve um empate, nenhum jogador é o vencedor
        } else if (vencedor != null) {
            vencedor.incrementarVitorias();
        } else {
            System.out.println("\n**** EMPATE! Ninguem vence nesta rodada! ****");
        }
    }

    private static void anunciarVencedor() {
        if (vencedor != null) {
            System.out.println("\n=======================================");
            System.out.println("|                                      |");
             System.out.println("| O VENCEDOR DA RODADA FOI O " + vencedor.getNome().toUpperCase() + "!!!  |");
            System.out.println("|                                      |");
            System.out.println("========================================\n");
            System.out.println(vencedor.getNome() + " venceu a rodada com " + vencedor.calcularPontos() + " pontos!");
        } else {
            System.out.println("\nNenhum vencedor nesta rodada!");
        }
    }

    public static void exibirRanking(Jogador[] jogadores) {
        System.out.println("\n===== Ranking dos Jogadores =====");
        Arrays.sort(jogadores, Comparator.comparingInt(Jogador::getVitorias).reversed());
        for (Jogador jogador : jogadores) {
            System.out.println(jogador.getNome() + " - Vitorias: " + jogador.getVitorias() + " - Saldo: R$ " + String.format("%.2f", jogador.getSaldo()));
        }
    }

    private static void exibirRankingFinal(Jogador[] jogadores) {
        System.out.println("\n===== Ranking Final dos Jogadores =====");
        Arrays.sort(jogadores, Comparator.comparingInt(Jogador::getVitorias).reversed());
        for (Jogador jogador : jogadores) {
            System.out.println(jogador.getNome() + " - Vitorias: " + jogador.getVitorias() + " - Saldo: R$ " + String.format("%.2f", jogador.getSaldo()));
        }

        if (jogadores[0].getVitorias() > jogadores[1].getVitorias()) {
            System.out.println("======================================================");
            System.out.println("|                                                    |");
            System.out.println("|  PARABENS AO GRANDE VENCEDOR,                      |");
            System.out.println("|            " + jogadores[0].getNome().toUpperCase() + ", POR CONQUISTAR O JOGO!           |");
            System.out.println("|                                                    |");
            System.out.println("|               .-=========-.                        |");
            System.out.println("|               \\'-=======-'//                       |");
            System.out.println("|               _|   .=.   |_                        |");
            System.out.println("|              ((|  {{1}}  |))                       |");
            System.out.println("|               \\|   /|\\   |/                        |");
            System.out.println("|                \\__ '`' __/                         |");
            System.out.println("|                  _`) (`_                           |");
            System.out.println("|                _/_______\\_                         |");
            System.out.println("|               /___________\\                        |");
            System.out.println("|                                                    |");
            System.out.println("======================================================");

        } else {
            System.out.println("\nO jogo terminou em empate!");
        }
    }
}