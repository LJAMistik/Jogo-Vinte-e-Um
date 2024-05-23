import java.util.Random;

public class Aposta {
    private static final double SALDO_INICIAL = 100.00;
    private static final double MIN_APOSTA = 1.00;
    private static final double MAX_APOSTA = 10.00;

    public static void inicializarSaldo(Jogador jogador) {
        jogador.setSaldo(SALDO_INICIAL);
    }

    public static double getSaldoInicial() {
        return SALDO_INICIAL;
    }
    
    public static double realizarAposta(Jogador jogador) {
        Random random = new Random();
        double valorAposta = MIN_APOSTA + (MAX_APOSTA - MIN_APOSTA) * random.nextDouble();
        valorAposta = Math.round(valorAposta * 2) / 2.0; //arredonda
        jogador.setApostaAtual(valorAposta);
        return valorAposta;
    }

    public static void processarResultado(Jogador[] jogadores, Jogador vencedor) {
        for (Jogador jogador : jogadores) {
            double aposta = jogador.getApostaAtual();
            if (jogador.equals(vencedor)) {
                jogador.incrementarSaldo(aposta * 1); // Valor apostado + prêmio
            } else {
                jogador.decrementarSaldo(aposta * 0.5); // Perde metade do valor apostado
            }
        }
    }

    public static boolean verificarSaldo(Jogador jogador) {
        return jogador.getSaldo() > 0;
    }
}
