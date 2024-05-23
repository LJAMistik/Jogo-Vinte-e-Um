// Classe  que implementa o baralho do jogo e possui as regras de semaforo.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Baralho {
    /* Classe composta por 52 cartas distintas formadas por 4 naipes e 13 numeros */
    
    private final List<Cartas> baralho = new ArrayList<>(); // Lista de cartas que compõem o baralho
    private static Baralho instance; // Instância única (singleton) do baralho
    private final Semaphore semaphore = new Semaphore(1); // Semáforo para controlar o acesso ao método pegarCarta

    // Método estático para obter a instância única do baralho
    public static Baralho getInstance() {
        if (instance == null) {
            instance = new Baralho();
        }
        return instance;
    }

    // Construtor privado para garantir que a classe só possa ser instanciada internamente
    private Baralho() {
        // Inicializa o baralho com 52 cartas (4 naipes, 13 números cada)
        for (Naipes naipe : Naipes.values()) {
            for (NumerosCartas numero : NumerosCartas.values()) {
                baralho.add(new Cartas(naipe, numero, numero.getValor()));
            }
        }
        Collections.shuffle(baralho); // Embaralha o baralho
    }

    // Método para pegar uma carta do baralho
    public String pegarCarta() {
        try {
            semaphore.acquire(); // Adquire o semáforo para garantir acesso exclusivo ao método
            if (baralho.size() > 0) { // Verifica se ainda há cartas no baralho
                return baralho.remove(0).getNome(); // Remove e retorna a primeira carta do baralho
            } else {
                return null; // Retorna null se o baralho estiver vazio
            }
        } catch (InterruptedException e) { // Trata a interrupção do semáforo
            Thread.currentThread().interrupt(); // Reinterrompe a thread
            return null;
        } finally {
            semaphore.release(); // Libera o semáforo para permitir que outros acessem o método
        }
    }

    // Método para obter a quantidade de cartas restantes no baralho
    public int getQuantidadeCartas() {
        return baralho.size();
    }
    
    // Adicionei este método para permitir o acesso ao baralho
    public Cartas pegarProximaCarta() {
        try {
            semaphore.acquire();
            if (baralho.size() > 0) {
                return baralho.remove(0);
            } else {
                return null;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            semaphore.release();
        }
    }
}
