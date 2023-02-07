package br.com.temdetudo.lanchonete.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.temdetudo.lanchonete.controller.ProductController;
import br.com.temdetudo.lanchonete.modelo.Product;
import br.com.temdetudo.lanchonete.modelo.enums.Category;

public class Products extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textProduct;
	private JLabel lblPrice;
	private JTextField textPrice;
	private JLabel lblCategory;
	private JComboBox<Object> comboBoxCategory;
	private JButton btnCadastrar;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel;
	private ProductController productController;
	private JTextField textId;
	private JTextField txtPesquisa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Products frame = new Products();
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
	public Products() {
		super("Produtos");
		this.productController = new ProductController();

		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(25, 25, 25, 25));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("Id:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId.setBounds(59, 34, 17, 14);
		contentPane.add(lblId);

		textId = new JTextField();
		textId.setEditable(false);
		textId.setBounds(83, 31, 38, 20);
		contentPane.add(textId);
		textId.setColumns(10);

		JLabel lblProduct = new JLabel("Produto:");
		lblProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProduct.setBounds(139, 34, 55, 14);
		contentPane.add(lblProduct);

		textProduct = new JTextField();
		textProduct.setBounds(204, 31, 300, 20);
		contentPane.add(textProduct);
		textProduct.setColumns(10);

		lblPrice = new JLabel("Preço:");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPrice.setBounds(59, 63, 38, 14);
		contentPane.add(lblPrice);

		textPrice = new JTextField();
		textPrice.setColumns(10);
		textPrice.setBounds(107, 60, 232, 20);
		contentPane.add(textPrice);

		lblCategory = new JLabel("Categoria:");
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCategory.setBounds(359, 63, 80, 14);
		contentPane.add(lblCategory);

		comboBoxCategory = new JComboBox<Object>();
		comboBoxCategory.setBounds(435, 59, 290, 22);
		contentPane.add(comboBoxCategory);

		Object[] opcoes = Category.values();
		comboBoxCategory.setModel(new DefaultComboBoxModel<Object>(opcoes));

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Product product = new Product(textProduct.getText(), Double.parseDouble(textPrice.getText()),
							Category.valueOf((comboBoxCategory.getSelectedItem().toString())));
					if (productController.listar().contains(product)) {
						JOptionPane.showMessageDialog(null, "Erro: Produto já existe", "Erro",
								JOptionPane.ERROR_MESSAGE);
					} else {
						productController.save(product);
						clearFields();
						listProducts(p -> p.getId() > 0); // atualiza tela
					}
				} catch (IllegalStateException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Erro ao instanciar! Preencha todos os campos.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCadastrar.setBounds(57, 113, 121, 35);
		contentPane.add(btnCadastrar);

		btnLimpar = new JButton("Limpar");
		btnLimpar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		btnLimpar.setBounds(188, 113, 121, 35);
		contentPane.add(btnLimpar);

		txtPesquisa = new JTextField();
		txtPesquisa.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (txtPesquisa.getText().equals("")) {
					listProducts(p -> p.getId() > 0);
				} else {
					listProducts(p -> p.getName().contains(txtPesquisa.getText().toUpperCase()));
				}
			}
		});
		txtPesquisa.setToolTipText("Digite o nome do produto");
		txtPesquisa.setBounds(359, 113, 220, 35);
		contentPane.add(txtPesquisa);
		txtPesquisa.setColumns(10);

		JButton btnPesquisar = new JButton("Limpar pesquisa");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtPesquisa.setText("");
				listProducts(p -> p.getId() > 0);
			}
		});
		btnPesquisar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPesquisar.setBounds(589, 113, 136, 35);
		contentPane.add(btnPesquisar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(57, 164, 666, 176);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Produto", "Pre\u00E7o", "Categoria" }) {

					private static final long serialVersionUID = 1L;
					boolean[] columnEditables = new boolean[] { false, false, false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
				JOptionPane.showMessageDialog(null, "Deletado com sucesso", "Sucesso", JOptionPane.DEFAULT_OPTION);
			}
		});
		btnExcluir.setBounds(470, 358, 121, 35);
		contentPane.add(btnExcluir);

		btnEditar = new JButton("Editar");
		btnEditar.setEnabled(false);
		btnEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRows().length > 0) {
					Product product = new Product(textProduct.getText(), Double.parseDouble(textPrice.getText()),
							Category.valueOf((comboBoxCategory.getSelectedItem().toString())));
					product.setId(Integer.parseInt(textId.getText()));
					edit(product);
					listProducts(p -> p.getId() > 0);
					clearFields();
					JOptionPane.showMessageDialog(null, "Editado com sucesso", "Sucesso", JOptionPane.DEFAULT_OPTION);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma linha", "Erro", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnEditar.setBounds(602, 358, 121, 35);
		contentPane.add(btnEditar);

		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(5);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setMinWidth(10);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.getColumnModel().getColumn(3).setResizable(false);

		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					copyValuesToEdit();
					btnExcluir.setEnabled(true);
					btnEditar.setEnabled(true);
				} else {
					btnExcluir.setEnabled(false);
					btnEditar.setEnabled(false);
				}
			}
		});
		// carrega todos os produtos já cadastrados
		listProducts(p -> p.getId() > 0);
	}

	private void listProducts(Predicate<Product> predicate) {
		clearTable();
		List<Product> products = new ArrayList<>(this.productController.listar());

		products.stream().filter(predicate).sorted((p1, p2) -> Integer.compare(p1.getId(), p2.getId())).forEach(p -> {
			tableModel = (DefaultTableModel) table.getModel();
			tableModel.addRow(new Object[] { p.getId(), p.getName(),
					NumberFormat.getCurrencyInstance().format(p.getPrice()), p.getCategory() });
		});
	}

	private void delete() {
		int rows = table.getSelectedRows().length;
		for (int i = 0; i < rows; i++) {
			int id = ((int) ((DefaultTableModel) table.getModel()).getValueAt(table.getSelectedRow(), 0));
			productController.delete(id);

			((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
		}

		textId.setText("");
		textProduct.setText("");
		textPrice.setText("");
	}

	private void edit(Product product) {
		productController.update(product);
	}

	private void copyValuesToEdit() {
		int rows = table.getSelectedRows().length;
		for (int i = 0; i < rows; i++) {
			textId.setText(
					(String.valueOf(((DefaultTableModel) table.getModel()).getValueAt(table.getSelectedRow(), 0))));
			textProduct
					.setText(((String) ((DefaultTableModel) table.getModel()).getValueAt(table.getSelectedRow(), 1)));
			String price = ((String) ((DefaultTableModel) table.getModel()).getValueAt(table.getSelectedRow(), 2));
			textPrice.setText(price.substring(3).replaceAll(",", ".").trim());
			comboBoxCategory.setSelectedItem((Category.valueOf(
					String.valueOf(((DefaultTableModel) table.getModel()).getValueAt(table.getSelectedRow(), 3)))));
		}
	}

	private void clearTable() {
		((DefaultTableModel) table.getModel()).getDataVector().clear();
		table.updateUI();
	}

	private void clearFields() {
		textId.setText("");
		textProduct.setText("");
		textPrice.setText("");
		comboBoxCategory.setSelectedIndex(0);
	}
}
