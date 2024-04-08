import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;


public class Battle extends JPanel {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    

    public Battle() {
        TelasRef.battle = this;

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Criando e adicionando os "cartões"
        JPanel pokemonsSelectionPanel = new JPanel();
        pokemonsSelectionPanel.add(new JLabel("seleção de pokemons"));
        JButton buttonPokemonsSelection = new JButton("cancelar");
        buttonPokemonsSelection.addActionListener(e -> mudarTela("battle"));
        pokemonsSelectionPanel.add(buttonPokemonsSelection);

        JPanel battlePanel = new JPanel();
        battlePanel.add(new PokemonsBatle());

        JPanel inventarioPanel = new JPanel();
        inventarioPanel.add(new JLabel("inventario de itens pokemon"));
        JButton buttonInventario = new JButton("cancelar");
        buttonInventario.addActionListener(e -> mudarTela("battle"));
        inventarioPanel.add(buttonInventario);

        cardPanel.add(battlePanel, "battle"); // Corrigido aqui
        cardPanel.add(pokemonsSelectionPanel, "pokemonsSelection");
        cardPanel.add(inventarioPanel, "inventario");

        add(cardPanel);
        setVisible(true);
    }

    public void mudarTela(String nomeTela) {
        System.out.println("Mudando para a tela " + nomeTela);
        cardLayout.show(cardPanel, nomeTela);
    }

}