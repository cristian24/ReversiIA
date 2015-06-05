package reversi.interfaz;

import javax.swing.JFrame;

import reversi.Tablero;
import reversi.GameController;
import reversi.Auxiliar;
import reversi.ia.ReversiEvaluador.EvaluationMethod;
import ia.AlgoritmosBusqueda;
import ia.Archivo;

import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MainView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameController gameController;
	private Archivo archivo;
	
	private TableroPanel pnlJuego;
	
	private JPanel pnlPrincipal;
	private JMenu mnInicio;
	private JMenuBar menuBar;
	private JMenuItem mntmNuevoJuego;
	private JMenuItem mntmSalir;
	private JPanel pnlOpciones;
	private JPanel pnlControles;
	private JPanel pnlTablero;
	private JLabel lblTipoJugador;
	private JRadioButton rdBtnJugador1;
	private JRadioButton rdBtnJugador2;
	private JLabel lblAlgoritmoBusqueda;
	private JRadioButton rdBtnMinimax;
	private JRadioButton rdBtnHeuristica1;
	private JRadioButton rdBtnHeuristica2;
	private JButton btnJugar;
	private JLabel lblHeuristica;
	private JRadioButton rdbtnPodaAlfabetha;
	private ButtonGroup btnGroupJugador;
	private ButtonGroup btnGroupHeuristica;
	private ButtonGroup btnGroupAlgoritmos;
	private ButtonGroup btnGroupOponentes;
	private JTextField txtArchivoEstado;
	private JLabel lblEstadoDelTablero;
	private JButton btnCargarEstado;
	private JLabel lblOponente;
	private JRadioButton rdBtnOpntIa;
	private JRadioButton rdBtnOpntHumano;
	private JSeparator separator1;
	private JLabel lblProfundidad;
	private JSeparator separator;
	private JSpinner spnProfundidad;
    
    public MainView() {
    	
    	this.archivo = new Archivo();
        initComponents();
        this.setSize(918, 696);
        this.setLocationRelativeTo(null);

        this.gameController = null;      
    }
    
    private void initComponents() {
    	
    	menuBar = new JMenuBar();		
		 
		mnInicio = new JMenu("Inicio");
		menuBar.add(mnInicio);
		 
		mntmNuevoJuego = new JMenuItem("Nuevo Juego");		
		mnInicio.add(mntmNuevoJuego);
		 
		mntmSalir = new JMenuItem("Salir");
		mnInicio.add(mntmSalir);
		
		this.setJMenuBar(menuBar);
    	getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
    	
    	pnlPrincipal = new JPanel();
    	getContentPane().add(pnlPrincipal);
    	pnlPrincipal.setLayout(null);
    	
    	pnlOpciones = new JPanel();
    	pnlOpciones.setBounds(0, 0, 306, 636);
    	pnlPrincipal.add(pnlOpciones);
    	pnlOpciones.setLayout(null);
    	
    	pnlControles = new JPanel();
    	pnlControles.setBounds(0, 0, 308, 636);
    	pnlControles.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Opciones Juego", TitledBorder.RIGHT, TitledBorder.TOP, null, Color.WHITE));
    	pnlOpciones.add(pnlControles);
    	pnlControles.setLayout(null);
    	
    	lblTipoJugador = new JLabel("\u00BFCon que fichas desea jugar?");
    	lblTipoJugador.setBounds(26, 11, 177, 23);
    	pnlControles.add(lblTipoJugador);
    	
    	rdBtnJugador1 = new JRadioButton("Negras (Jug 1)");
    	rdBtnJugador1.setBounds(26, 41, 155, 23);
    	rdBtnJugador1.setSelected(true);
    	pnlControles.add(rdBtnJugador1);
    	
    	rdBtnJugador2 = new JRadioButton("Blancas (Jug 2)");
    	rdBtnJugador2.setBounds(26, 67, 134, 23);
    	pnlControles.add(rdBtnJugador2);
    	
    	btnGroupJugador = new ButtonGroup();
    	btnGroupJugador.add(rdBtnJugador1);
    	btnGroupJugador.add(rdBtnJugador2);
    	
    	lblAlgoritmoBusqueda = new JLabel("Algoritmo de Busqueda");
    	lblAlgoritmoBusqueda.setBounds(26, 199, 134, 23);
    	pnlControles.add(lblAlgoritmoBusqueda);
    	
    	rdBtnMinimax = new JRadioButton("MiniMax");
    	rdBtnMinimax.setBounds(26, 230, 134, 23);
    	pnlControles.add(rdBtnMinimax);
    	
    	rdbtnPodaAlfabetha = new JRadioButton("Poda Alfa-Betha");
    	rdbtnPodaAlfabetha.setBounds(26, 256, 134, 23);
    	rdbtnPodaAlfabetha.setSelected(true);
    	pnlControles.add(rdbtnPodaAlfabetha);
    	
    	btnGroupAlgoritmos = new ButtonGroup();
    	btnGroupAlgoritmos.add(rdBtnMinimax);
    	btnGroupAlgoritmos.add(rdbtnPodaAlfabetha);
    	
    	lblHeuristica = new JLabel("Heuristica");
    	lblHeuristica.setBounds(26, 294, 151, 23);
    	pnlControles.add(lblHeuristica);
    	
    	rdBtnHeuristica1 = new JRadioButton("Prioridad Esquinas");
    	rdBtnHeuristica1.setBounds(26, 324, 134, 23);
    	rdBtnHeuristica1.setSelected(true);
    	pnlControles.add(rdBtnHeuristica1);
    	
    	rdBtnHeuristica2 = new JRadioButton("Prioridad Bordes");
    	rdBtnHeuristica2.setBounds(26, 350, 134, 23);
    	pnlControles.add(rdBtnHeuristica2);
    	
    	btnGroupHeuristica = new ButtonGroup();
    	btnGroupHeuristica.add(rdBtnHeuristica1);
    	btnGroupHeuristica.add(rdBtnHeuristica2);
    	
    	btnJugar = new JButton("Jugar");
    	btnJugar.setBounds(26, 525, 126, 36);
    	pnlControles.add(btnJugar);
    	
    	lblEstadoDelTablero = new JLabel("Estado del tablero");
    	lblEstadoDelTablero.setBounds(26, 445, 134, 23);
    	pnlControles.add(lblEstadoDelTablero);
    	
    	txtArchivoEstado = new JTextField();
    	txtArchivoEstado.setEditable(false);
    	txtArchivoEstado.setText("Default.txt");
    	txtArchivoEstado.setBounds(26, 479, 126, 23);
    	pnlControles.add(txtArchivoEstado);
    	txtArchivoEstado.setColumns(10);
    	
    	btnCargarEstado = new JButton("Cargar Estado");    	
    	btnCargarEstado.setBounds(163, 472, 126, 36);
    	pnlControles.add(btnCargarEstado);
    	
    	lblOponente = new JLabel("Oponente");
    	lblOponente.setBounds(26, 97, 134, 23);
    	pnlControles.add(lblOponente);
    	
    	rdBtnOpntIa = new JRadioButton("IA");
    	rdBtnOpntIa.setSelected(true);
    	rdBtnOpntIa.setBounds(26, 127, 109, 23);
    	pnlControles.add(rdBtnOpntIa);
    	
    	rdBtnOpntHumano = new JRadioButton("Humano");    	
    	rdBtnOpntHumano.setBounds(26, 151, 109, 23);
    	pnlControles.add(rdBtnOpntHumano);
    	
    	btnGroupOponentes = new ButtonGroup();
    	btnGroupOponentes.add(rdBtnOpntIa);
    	btnGroupOponentes.add(rdBtnOpntHumano);
    	
    	separator1 = new JSeparator();
    	separator1.setBounds(10, 185, 261, 50);
    	pnlControles.add(separator1);
    	
    	lblProfundidad = new JLabel("Profundidad");
    	lblProfundidad.setBounds(26, 380, 109, 23);
    	pnlControles.add(lblProfundidad);
    	
    	separator = new JSeparator();
    	separator.setBounds(10, 422, 261, 50);
    	pnlControles.add(separator);
    	
    	spnProfundidad = new JSpinner();
    	spnProfundidad.setBounds(144, 380, 37, 23);
    	spnProfundidad.setModel(new javax.swing.SpinnerNumberModel(3, 2, 5, 1));
    	pnlControles.add(spnProfundidad);
    	
    	pnlTablero = new JPanel();
    	pnlTablero.setBounds(316, 0, 585, 636);
    	pnlPrincipal.add(pnlTablero);
    	pnlTablero.setLayout(new BoxLayout(pnlTablero, BoxLayout.X_AXIS));
    	
    	pnlJuego = new TableroPanel();
    	pnlTablero.add(pnlJuego);
    	
    	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reversi");
        setMinimumSize(new java.awt.Dimension(700, 550));
        
    	eventos();
    }   
    
    
    private void setComputerAIControlsEnabled(boolean isEnabled){
    	this.lblAlgoritmoBusqueda.setEnabled(isEnabled);
        this.lblHeuristica.setEnabled(isEnabled);
        this.lblProfundidad.setEnabled(isEnabled);
        this.rdbtnPodaAlfabetha.setEnabled(isEnabled);
        this.rdBtnMinimax.setEnabled(isEnabled);
        this.rdBtnHeuristica1.setEnabled(isEnabled);
        this.rdBtnHeuristica2.setEnabled(isEnabled);
        this.spnProfundidad.setEnabled(isEnabled);
    }
    
    private void nuevoJuego(){
    	this.gameController = null;

        int colorJugador;
        if (this.rdBtnJugador1.isSelected()) {
            colorJugador = Auxiliar.BLACK;
        } else {
            colorJugador = Auxiliar.WHITE;
        }
        
        boolean singlePlayer;
        if (this.rdBtnOpntHumano.isSelected()) {
            singlePlayer = false;
        } else {
            singlePlayer = true;
        }

        int profundidad = (Integer) this.spnProfundidad.getModel().getValue();
        EvaluationMethod heuristica;
        if (this.rdBtnHeuristica1.isSelected()) {
            heuristica = EvaluationMethod.VALID_MOVES_AND_CORNERS;
        } else {
            heuristica = EvaluationMethod.VALID_MOVES_AND_SIDES_COUNT;
        }

        AlgoritmosBusqueda.Algoritmos algoritmoBusqueda;
        if (this.rdbtnPodaAlfabetha.isSelected()) {
            algoritmoBusqueda = AlgoritmosBusqueda.Algoritmos.PODA_ALPHA_BETA;
        } else {
            algoritmoBusqueda = AlgoritmosBusqueda.Algoritmos.MINIMAX;
        }        
        
        this.pnlTablero.removeAll();
        TableroPanel pnlTablero = new TableroPanel();
        this.pnlTablero.add(pnlTablero);
        int nexTurno = Integer.parseInt(archivo.obtenerTurno());
        this.gameController = new GameController(singlePlayer, nexTurno, new Tablero(this.archivo), pnlTablero, profundidad, algoritmoBusqueda, heuristica, Auxiliar.TIEMPO_RESPUESTA);        
        this.gameController.startGame();        
    }
    
    
    private void eventos()
    {
    	btnCargarEstado.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			String nombreArchivo = archivo.elegirArchivo();
    			txtArchivoEstado.setText(nombreArchivo);
    		}
    	});
    	
    	btnJugar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			pnlPrincipal.setLayout(new BoxLayout(pnlPrincipal, BoxLayout.X_AXIS));    			
    			pnlOpciones.setVisible(false);
    			nuevoJuego();
    		}
    	});
    	
    	mntmNuevoJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlPrincipal.setLayout(null);
				pnlOpciones.setVisible(true);
				pnlTablero.setBounds(316, 0, 585, 636);
				pnlOpciones.setBounds(0, 0, 306, 636);
			}
		});
    	
    	mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
    	
    	rdBtnOpntIa.addItemListener(new ItemListener() {
    		public void itemStateChanged(ItemEvent e) {
    			setComputerAIControlsEnabled(e.getStateChange() == ItemEvent.SELECTED);
    		}
    	});
    }
}
