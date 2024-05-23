// Classe que representa as cartas do jogo e contém os enums de naipe e valores das cartas.

// Enumeração para representar os naipes de um baralho
enum Naipes {
    PAUS, OUROS, COPAS, ESPADAS // Quatro naipes tradicionais
}

// Enumeração para representar os números das cartas e seus valores
enum NumerosCartas {
    AS(1), UM(1), DOIS(2), TRES(3), QUATRO(4), CINCO(5), SEIS(6), SETE(7), OITO(8), NOVE(9), DEZ(10), VALETE(10), DAMA(10), REI(10);

    private final int valor; // Valor numérico da carta

    // Construtor do enum que define o valor da carta
    NumerosCartas(int valor) {
        this.valor = valor;
    }

    // Método para obter o valor da carta
    public int getValor() {
        return valor;
    }
}

// Classe para representar uma carta individual no baralho
public class Cartas {
    public final Naipes naipe; // Naipe da carta
    public final NumerosCartas numero; // Número da carta
    public final int valor; // Valor da carta

    // Construtor que inicializa os atributos da carta
    public Cartas(Naipes naipe, NumerosCartas numero, int valor) {
        this.naipe = naipe;
        this.numero = numero;
        this.valor = valor;
    }

    // Método para obter o nome completo da carta (ex: "Carta REI de ESPADAS")
    public String getNome() {
        return "Carta " + numero + " de " + naipe;
    }

    // Método para obter o valor da carta
    public int getValor() {
        return valor;
    }
}
