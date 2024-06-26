import javax.swing.*;

public class Game extends JFrame implements Runnable {
    private JPanel painelAtual;
    private Player currentPlayer;
    static Game game;

    public void run() {
        Game.game = this;
        editar();
    }

    public void editar() {
        setTitle("pokeBattle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(976, 679);
        setResizable(false);
        setLocationRelativeTo(null);
        new Enemy();

        setVisible(true);

        boolean saveExists = Player.carregarDados();

        if (saveExists) {
            Home home = new Home(this);
            mudarTela(home);
        } else {
            Intro intro = new Intro(this);
            mudarTela(intro);
        }
        
    }

    public void setPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Player getPlayer() {
        return this.currentPlayer;
    }
   
    public void mudarTela(JPanel novoPanel) {
        if (painelAtual != null) {
            remove(painelAtual); 
            painelAtual = null; 
        }
        painelAtual = novoPanel;
        add(painelAtual);
        
        if (painelAtual instanceof PokemonsBatle) {
            PokemonsBatle battlePanel = (PokemonsBatle) painelAtual;
            battlePanel.atualizarVidaPlayer();
            battlePanel.atualizarVidaInimigo();
        } else if (painelAtual instanceof EvolucaoPage) {
            EvolucaoPage evolucaoPage = (EvolucaoPage) painelAtual;
            evolucaoPage.revalidate();
            evolucaoPage.repaint();
        }

        revalidate();
        repaint();
    }
}
