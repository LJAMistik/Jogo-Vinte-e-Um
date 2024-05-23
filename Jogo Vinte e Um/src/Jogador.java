import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogador {
    private String nome;
    private List<Cartas> mao;
    private boolean ativo;
    private Random random = new Random();
    private int vitorias;
    private double saldo;
    private double apostaAtual;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new ArrayList<>();
        this.ativo = true;
        this.vitorias = 0;
    }

    public int calcularPontos() {
        int soma = 0;
        for (Cartas carta : mao) {
            soma += carta.getValor();
        }
        return soma;
    }

    public void pegarCarta() {
        Cartas carta = Baralho.getInstance().pegarProximaCarta();
        if (carta != null) {
            mao.add(carta);
        }
    }

    public void jogar() throws InterruptedException {
        mao.clear();
        pegarCarta();
        pegarCarta();

        System.out.println("* " + nome + " recebeu as seguintes cartas: " + maoToString() + " *\n");

        // Realiza a aposta
        double aposta = Aposta.realizarAposta(this);
        System.out.println("| " + nome + " apostou R$ " + String.format("%.2f", aposta));

        while (calcularPontos() <= 21 && random.nextBoolean()) {
            pegarCarta();
            System.out.println("- " + nome + " pegou uma carta: " + mao.get(mao.size() - 1).getNome());
        }

        int pontos = calcularPontos();
        if (pontos > 21) {
            System.out.println(nome + " estourou com " + pontos + " pontos.");
            ativo = false;
            decrementarSaldo(aposta / 2);
        } else {
            System.out.println(nome + " terminou a rodada com " + pontos + " pontos.");
        }

        // Verifica se o jogador ainda tem saldo
        if (getSaldo() <= 0) {
            System.out.println(nome + " esta fora do jogo devido ao saldo insuficiente.");
            ativo = false;
        }
    }

    private String maoToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mao.size(); i++) {
            stringBuilder.append(mao.get(i).getNome());
            if (i < mao.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean valor) {
        this.ativo = valor;
    }

    public String getNome() {
        return nome;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void incrementarVitorias() {
        vitorias++;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void incrementarSaldo(double valor) {
        this.saldo += valor;
    }

    public void decrementarSaldo(double valor) {
        this.saldo -= valor;
    }

    public double getApostaAtual() {
        return apostaAtual;
    }

    public void setApostaAtual(double apostaAtual) {
        this.apostaAtual = apostaAtual;
    }
}
