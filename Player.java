import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Player implements Serializable  {
    private static final long serialVersionUID = 1L;
    public static String pokemonNome;
    public static Pokemon pokemonSelecionado;
    public static String nome;
    public static String tipoPersonagem = "boy";
    public static InterfaceCaixa painel;
    public static Game frame;
    public static boolean resultado;
    public static int ataqueValue = 15;

    public Player(String pokemon, Game frame) {
        Player.pokemonNome = pokemon; 
        Player.frame = frame;
        pokemonSelecionado = new Pokemon(pokemon,  "back");
    }

    public String getPokemonNome(){
        return pokemonNome;
    }
    public void setPokemonNome(String pokemonNome){
        Player.pokemonNome = pokemonNome;
    }
    public static void setInterfaceCaixa(InterfaceCaixa painel) {
        Player.painel = painel; // Adicione este método
    }

    public static void atacar() {
        Enemy.inimigoAtual.setVida(Enemy.inimigoAtual.getVida() - ataqueValue);
        System.out.println("O pokemon do player atacou o pokemon inimigo e causou " + ataqueValue + " de dano");
        if (Enemy.inimigoAtual.getVida() <= 0) {
            System.out.println("O pokemon inimigo foi derrotado");
            painel.mostrarDerrotaInimigo(() -> {
                Enemy.trocarInimigo();
                PokemonsBatle.instance.atualizarInimigo();
                PokemonsBatle.instance.atualizarVidaInimigo(); 
                pokemonSelecionado.evoluir(PokemonsBatle.playerLv, frame);
            });
        } else {
            PokemonsBatle.instance.atualizarVidaInimigo();
        }
    }

    public static void curar(String curaValor) {
        pokemonSelecionado.setVida(pokemonSelecionado.getVida() + Integer.parseInt(curaValor));

        if (pokemonSelecionado.getVida() > pokemonSelecionado.getVidaMaxima()) {
            pokemonSelecionado.setVida(pokemonSelecionado.getVidaMaxima());
        }

        System.out.println("O pokemon do player foi curado e recuperou " + curaValor + " de vida");
        PokemonsBatle.instance.atualizarVidaPlayer();
    }

    public static void salvarDados() {
        try {
            // Criar pasta saves se não existir
            File savesDir = new File("saves");
            if (!savesDir.exists()) {
                savesDir.mkdir();
            }
            
            PlayerData data = PlayerData.fromCurrentState();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(data);
            Files.write(Paths.get("saves/player_data.json"), json.getBytes());
            System.out.println("Dados salvos com sucesso em JSON! Tamanho reduzido significativamente.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public static boolean carregarDados() {
        File file = new File("saves/player_data.json");
        if (!file.exists()) {
            System.out.println("Nenhum save do jogo encontrado.");
            return false;
        }
    
        try {
            String json = new String(Files.readAllBytes(Paths.get("saves/player_data.json")));
            Gson gson = new Gson();
            PlayerData data = gson.fromJson(json, PlayerData.class);
            data.restoreToCurrentState();
            
            System.out.println("Dados carregados com sucesso do JSON!");
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
            return false;
        }
    }
    
    public static int getAlturaPokemon() {
        switch (pokemonSelecionado.getNome()) {
            case "Bulbasaur":
                return 256; 
            case "Ivysaur":
                return 232; 
            case "Venusaur": 
                return 232; 
            case "Squirtle":
                return 252; 
            case "Wartortle":
                return 232;
            case "Blastoise":
                return 232; 
            case "Charmander":
                return 232; 
            case "Charmeleon":
                return 220; 
            case "Charizard":
                return 208; 
            default:
                return 256;
        }
    }
}