import javax.swing.*;
import java.awt.*;

public class InterfaceCaixa extends JPanel {
    CaixaDialogo caixaDialogo = new CaixaDialogo();
    CardLayout maincardLayout = new CardLayout(); 
    JPanel mainCardPanel = new JPanel(maincardLayout);
    JPanel battleLayoutPanel = new JPanel(new GridLayout(1, 2)); 
    CardLayout leftcardLayout = new CardLayout();
    JPanel leftComponent = new JPanel(leftcardLayout);
    JPanel rightComponent = new JPanel();

    public InterfaceCaixa() {
        TelasRef.interfaceCaixa = this;
        
        mainCardPanel.add(battleLayoutPanel, "BattleLayoutPanel");
        mainCardPanel.add(caixaDialogo, "caixaDialogo");

        leftComponent.add(caixaDialogo, "caixaDeTexto");
        leftComponent.add(new Poderes(), "poderes");
        
        rightComponent.setBackground(Color.RED);
        Options options = new Options();
        rightComponent.add(options);

        // Adicionando os componentes ao painel principal
        battleLayoutPanel.add(leftComponent);
        battleLayoutPanel.add(rightComponent);

        // Defina um layout para o JPanel principal
        setLayout(new BorderLayout());
        add(mainCardPanel, BorderLayout.CENTER);

        caixaDialogo.dialogar(new String[] { "Batalha iniciada.", "Escolha sua ação." });
    }

    public void mudarInterfaceLayout(String nomeLayout) {
        System.out.println("Mudando interface para modo " + nomeLayout);
        maincardLayout.show(mainCardPanel, nomeLayout);
    }

    public void mudarInterfaceBattleLayout(String nomeLayout) {
        System.out.println("Mudando interface para modo " + nomeLayout);
        leftcardLayout.show(leftComponent, nomeLayout);
    }
}
