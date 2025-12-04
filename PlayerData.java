public class PlayerData {
    // Dados essenciais do Player
    public String pokemonNome;
    public String nome;
    public String tipoPersonagem;
    public int ataqueValue;
    
    // Dados do Pokémon do jogador
    public PokemonData pokemonSelecionado;
    
    // Dados do inimigo atual
    public PokemonData inimigoAtual;
    public int indiceInimigoAtual;  // Apenas o índice, não toda a lista
    
    // Níveis
    public int inimigoLv;
    public int playerLv;
    
    public PlayerData() {}
    
    // Classe interna para representar Pokemon de forma compacta
    public static class PokemonData {
        public String nome;
        public int vida;
        public int vidaMaxima;
        public String lado;
        public String nomeAnterior;
        
        public PokemonData() {}
        
        public PokemonData(Pokemon pokemon) {
            this.nome = pokemon.getNome();
            this.vida = pokemon.getVida();
            this.vidaMaxima = pokemon.getVidaMaxima();
            this.lado = pokemon.getLado();
            this.nomeAnterior = pokemon.getNomeAnterior();
        }
        
        public Pokemon toPokemon() {
            Pokemon pokemon = new Pokemon(this.nome, this.lado);
            pokemon.setVida(this.vida);
            pokemon.setVidaMaxima(this.vidaMaxima);
            pokemon.setNomeAnterior(this.nomeAnterior);
            return pokemon;
        }
    }
    
    public static PlayerData fromCurrentState() {
        PlayerData data = new PlayerData();
        data.pokemonNome = Player.pokemonNome;
        data.nome = Player.nome;
        data.tipoPersonagem = Player.tipoPersonagem;
        data.ataqueValue = Player.ataqueValue;
        
        if (Player.pokemonSelecionado != null) {
            data.pokemonSelecionado = new PokemonData(Player.pokemonSelecionado);
        }
        
        if (Enemy.inimigoAtual != null) {
            data.inimigoAtual = new PokemonData(Enemy.inimigoAtual);
            // Salvar apenas o índice do inimigo atual
            data.indiceInimigoAtual = Enemy.inimigos.indexOf(Enemy.inimigoAtual);
        }
        
        data.inimigoLv = PokemonsBatle.inimigoLv;
        data.playerLv = PokemonsBatle.playerLv;
        
        return data;
    }
    
    public void restoreToCurrentState() {
        Player.pokemonNome = this.pokemonNome;
        Player.nome = this.nome;
        Player.tipoPersonagem = this.tipoPersonagem;
        Player.ataqueValue = this.ataqueValue;
        
        if (this.pokemonSelecionado != null) {
            Player.pokemonSelecionado = this.pokemonSelecionado.toPokemon();
        }
        
        if (this.inimigoAtual != null) {
            Enemy.inimigoAtual = this.inimigoAtual.toPokemon();
            // A lista de inimigos será recriada pelo Enemy()
            // Apenas precisamos garantir que o inimigo atual está correto
        }
        
        PokemonsBatle.inimigoLv = this.inimigoLv;
        PokemonsBatle.playerLv = this.playerLv;
    }
}
