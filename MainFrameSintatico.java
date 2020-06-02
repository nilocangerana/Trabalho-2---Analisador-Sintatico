import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainFrameSintatico extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		Analisador_lexico.inicializarTabelas();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrameSintatico frame = new MainFrameSintatico();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrameSintatico() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 537, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblcoloqueOArquivo = new JLabel("1-Coloque o arquivo na mesma pasta do trabalho2.jar");
		lblcoloqueOArquivo.setBounds(10, 26, 480, 20);
		contentPane.add(lblcoloqueOArquivo);
		
		JLabel label = new JLabel("2-Digite o nome do arquivo na caixa de texto");
		label.setBounds(10, 57, 375, 14);
		contentPane.add(label);
		
		JLabel lblcliqueNoBoto = new JLabel("3-Clique no bot\u00E3o An\u00E1lise Sint\u00E1tica");
		lblcliqueNoBoto.setBounds(10, 86, 191, 14);
		contentPane.add(lblcliqueNoBoto);
		
		textField = new JTextField();
		textField.setBounds(115, 142, 265, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("An\u00E1lise Sint\u00E1tica");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String codigoEntrada="";
				try
				{
					 codigoEntrada=Manip_arquivos.leitor(textField.getText());
				}
				catch (FileNotFoundException e1)
				{
					JOptionPane.showMessageDialog (null, "Erro. Arquivo não encontrado.");
					System.exit(0);
				}
				catch (IOException e2)
				{
					JOptionPane.showMessageDialog (null, "Erro na abertura do arquivo.");
					System.exit(0);
				}
				
				Analisador_sintatico.getSaidaLexico(Analisador_lexico.analisadorLexico(codigoEntrada)); //pega a saida do analisador lexico e passa para o sintatico
				Analisador_sintatico.ASD();
				
				try
				{
					 Manip_arquivos.escritor("src\\saida.txt",Analisador_sintatico.getSaidaSintatico());
				}
				catch (IOException e3)
				{
					JOptionPane.showMessageDialog (null, "Erro na escrita do arquivo de saída.");
					System.exit(0);
				}
				JOptionPane.showMessageDialog (null, "Finalizado com Sucesso. Saída armazenada no arquivo src\\saida.txt");
				System.exit(0);
			}
		});
		btnNewButton.setBounds(168, 173, 152, 50);
		contentPane.add(btnNewButton);
	}
}
