import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class InterfaceCaixa extends JPanel {
    private CardLayout maincardLayout = new CardLayout(); 
    private JPanel mainCardPanel = new JPanel(maincardLayout);
    private JPanel battleLayoutPanel = new JPanel(new GridLayout(1, 2)); 
    private CardLayout leftcardLayout = new CardLayout();
    private JPanel leftComponent = new JPanel(leftcardLayout);
    private JLabel msgLabel = new JLabel();
    private Font Fonte = DefinirFonte.fonte();
    private Runnable onMessageAdvance = null;
    private boolean aguardandoInput = false;

    public InterfaceCaixa(Game frame) {
        Player.setInterfaceCaixa(this);
        Enemy.setInterfaceCaixa(this);
        Enemy.setGameFrame(frame);
        
        // Configurar atalho de teclado SPACE para avançar mensagens
        mainCardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "spacePressed");
        mainCardPanel.getActionMap().put("spacePressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (aguardandoInput) {
                    avancarMensagem();
                }
            }
        });
        
        // Permitir clique no msgLabel para avançar
        msgLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (aguardandoInput) {
                    avancarMensagem();
                }
            }
        });
        
        msgTexto("O  que  " + Player.pokemonSelecionado.getNome().toUpperCase(), "vai  fazer?");
        msgLabel.setIcon(new ImageIcon("assets/battleElements/ataque background.png")); 
        leftComponent.add(msgLabel, "MessageLabel"); 
    
        mainCardPanel.add(battleLayoutPanel, "BattleLayoutPanel");

        leftComponent.add(new Poderes(this), "poderes");
    
        Options options = new Options(frame, this);
    
        // Adicionando os componentes ao painel principal
        battleLayoutPanel.add(leftComponent);
        battleLayoutPanel.add(options);
    
        // Defina um layout para o JPanel principal
        setLayout(new BorderLayout());
        add(mainCardPanel, BorderLayout.CENTER);
    }
    
    public void mudarInterfaceLayout(String nomeLayout) {
        System.out.println("Mudando interface para modo " + nomeLayout);
        maincardLayout.show(mainCardPanel, nomeLayout);
    }
    
    public void mudarInterfaceBattleLayout(String nomeLayout) {
        System.out.println("Mudando interface para modo " + nomeLayout);
        leftcardLayout.show(leftComponent, nomeLayout);
    }
    
    // mostra qual ataque foi usado e aguarda SPACE ou clique para continuar
    public void mostrarAtaque(Runnable callback) {
        msgLabel.setIcon(new ImageIcon("assets/battleElements/ataque background.png"));
        mainCardPanel.add(msgLabel, "MsgLabel");
        maincardLayout.show(mainCardPanel, "MsgLabel"); 
        revalidate();
        
        aguardandoInput = true;
        onMessageAdvance = () -> {
            mainCardPanel.remove(msgLabel);
            msgLabel.removeAll(); 
            maincardLayout.show(mainCardPanel, "BattleLayoutPanel"); 
            revalidate();
            repaint();
            aguardandoInput = false;
            if (callback != null) {
                callback.run();
            }
        };
    }
    
    private void avancarMensagem() {
        if (aguardandoInput && onMessageAdvance != null) {
            Runnable action = onMessageAdvance;
            onMessageAdvance = null;
            aguardandoInput = false;
            action.run();
        }
    }

    public void mostrarAtaqueInimigo(Runnable callback) {
        String[] poderes = {"scratch", "quick attack", "tackle", "bite"};
        String nomeAtaque = poderes[(int) (Math.random() * poderes.length)];

        msgTexto(Enemy.inimigoAtual.getNome().toUpperCase() + " usou", nomeAtaque.toUpperCase() + "!");
        msgLabel.setIcon(new ImageIcon("assets/battleElements/ataque background.png"));
        mainCardPanel.add(msgLabel, "MsgLabel");
        maincardLayout.show(mainCardPanel, "MsgLabel"); 
        revalidate();
        
        aguardandoInput = true;
        onMessageAdvance = () -> {
            mainCardPanel.remove(msgLabel);
            msgLabel.removeAll(); 
            maincardLayout.show(mainCardPanel, "BattleLayoutPanel"); 
            revalidate();
            repaint();
            aguardandoInput = false;
            if (callback != null) {
                callback.run();
            }
        };
    }
    
    public void mostrarDerrotaInimigo(Runnable callback) {
        msgTexto(Enemy.inimigoAtual.getNome().toUpperCase() + "  inimigo", "foi  derrotado!");
        
        msgLabel.setIcon(new ImageIcon("assets/battleElements/ataque background.png"));
        mainCardPanel.add(msgLabel, "MsgLabel");
        maincardLayout.show(mainCardPanel, "MsgLabel"); 
        revalidate();
        
        aguardandoInput = true;
        onMessageAdvance = () -> {
            mainCardPanel.remove(msgLabel);
            msgLabel.removeAll(); 
            maincardLayout.show(mainCardPanel, "BattleLayoutPanel"); 
            revalidate();
            repaint();
            aguardandoInput = false;
            if (callback != null) {
                callback.run();
            }
        };
    }

    public void mostrarDerrotaPlayer(Runnable callback) {
        msgTexto(Player.pokemonSelecionado.getNome().toUpperCase() + "  foi  derrotado!", "");
        
        msgLabel.setIcon(new ImageIcon("assets/battleElements/ataque background.png"));
        mainCardPanel.add(msgLabel, "MsgLabel");
        maincardLayout.show(mainCardPanel, "MsgLabel"); 
        revalidate();
        
        aguardandoInput = true;
        onMessageAdvance = () -> {
            mainCardPanel.remove(msgLabel);
            msgLabel.removeAll(); 
            maincardLayout.show(mainCardPanel, "BattleLayoutPanel"); 
            revalidate();
            repaint();
            aguardandoInput = false;
            if (callback != null) {
                callback.run();
            }
        };
    }

    public void limparMsgTexto() {
        msgLabel.removeAll();
        revalidate();
        repaint();
    }

    public void msgTexto(String texto0, String texto1) {
        JLabel linha0 = new JLabel(texto0);
        JLabel linha1 = new JLabel(texto1);
        
        linha0.setFont(Fonte.deriveFont(Font.PLAIN,60f));
        linha1.setFont(Fonte.deriveFont(Font.PLAIN,60f));
        
        linha0.setBounds(50, 35, 800, 60);
        linha1.setBounds(50, 95, 800, 60);
        
        linha0.setForeground(Color.WHITE);
        linha1.setForeground(Color.WHITE);
        
        msgLabel.add(linha0);
        msgLabel.add(linha1);
    }
    
    public JLabel getMsgLabel() {
        return msgLabel;
    }
}