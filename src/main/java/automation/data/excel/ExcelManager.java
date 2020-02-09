package automation.data.excel;

/*
 * @author Luiz Felipe Alves de Sousa
 * @version 1.00
 * @since 09/01/2020
 * 
 * Classe Responsável por Gerenciar arquivos de planilhas Excel
*/

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import automation.core.utils.Utils;
import automation.logging.log4j.Log4JSetup;

public class ExcelManager {

	private Logger log = Log4JSetup.getLogger(ExcelManager.class);

	private Workbook workbook;
	private FileInputStream inputstream;

	private int index;
	private String fileName;

	// ******************************
	// Construtores
	// ******************************

	/**
	 * Construtor com nome do arquivo alterável.
	 * 
	 * @param String - Nome do arquivo excel desejado com a extensão correta XLS ou
	 *               XLSX.
	 */
	public ExcelManager(String fileName) {
		setFileName(fileName);
		defaultIndex();
	}

	/**
	 * Construtor com o número do indíce da aba da planilha selecionada alterável.
	 * 
	 * @param int - Número da Aba da Planilha deseja. Inicia por padrão começa no 0.
	 */
	public ExcelManager(int indexSheet) {
		defaultFileName();
		setIndex(indexSheet);
	}

	/**
	 * Construtor com nome do arquivo e indíce da aba da planilha selecionada
	 * alteráveis.
	 * 
	 * @param String - Nome do arquivo excel desejado com a extensão correta XLS ou
	 *               XLSX.
	 * @param int    - Número da Aba da Planilha deseja. Inicia por padrão começa no
	 *               0.
	 */
	public ExcelManager(String fileName, int indexSheet) {
		setFileName(fileName);
		setIndex(indexSheet);
	}

	/**
	 * Construtor Padrão com nome e aba de planilha já configurados e não alteráveis
	 */
	public ExcelManager() {
		defaultFileName();
		defaultIndex();
	}

	// ******************************
	// Configurações Padrões
	// ******************************

	/**
	 * Configura o nome do arquivo excel com base no que está configurado no arquivo
	 * setup.properties.
	 *
	 */
	private void defaultFileName() {
		setFileName(Utils.getProp("file.excel"));
	}

	/**
	 * Configura o arquivo excel para selecionar a primeira aba do arquivo
	 * carregado.
	 *
	 */
	private void defaultIndex() {
		setIndex(0);
	}

	private void setFileName(String newName) {
		fileName = newName;
	}

	private void setIndex(int indexSheet) {
		index = indexSheet;
	}

	// ******************************
	// Configuração de Carregamento
	// ******************************

	/**
	 * Método responsável por carregar o arquivo excel na memória. Deve ser iniciado
	 * sempre que for carregar um arquivo.
	 */
	public void setup() {

		if (!fileName.toLowerCase().contains(".xls")) {
			log.error("O nome do Arquivo está escrito incorretamente, verifique a extensão 'XLS' ou 'XLSX");
			assertTrue(fileName.toLowerCase().contains(".xls"));
		}

		String excelPath = getExcelPath();

		try {
			inputstream = new FileInputStream(new File(excelPath));
			workbook = new XSSFWorkbook(inputstream);
			log.info("Arquivo Excel foi carregado.");
		} catch (IOException e) {
			e.printStackTrace();
			log.fatal("Não foi possível localizar o arquivo Excel, verifique a extensão 'XLS' ou 'XLSX !");
		}

	}

	/**
	 * Método responsável por encerrar a leitura do arquivo excel em memória,
	 * encerrando todos os componentes abertos.
	 */
	public void exit() {
		try {
			workbook.close();
			inputstream.close();
			log.info("Arquivo Excel finalizado corretamente.");
		} catch (IOException e) {
			log.info("Não foi possível fechar o Workbook ou o arquivo Excel!");
			e.printStackTrace();
		}
	}

	/**
	 * Método responsável por obter o caminho do diretório onde estão localizados as
	 * planilhas Excel.
	 * 
	 * @return String - Caminho do diretório onde estão localizados as planilhas
	 *         Excel.
	 */
	private String getExcelPath() {
		String pathExcel = Utils.getProp("file.excel.path") + fileName;
		return pathExcel;
	}

	// ******************************
	// Gerenciar Celulas e Colunas
	// ******************************

	/**
	 * Método privado que retona uma lista de Linhas da planilha Excel que foi
	 * carregada na memória.
	 * 
	 * @return Iterator<Row> - Lista de Linhas da planilha Excel.
	 */
	private Iterator<Row> getRows() {
		Sheet currentSheet = workbook.getSheetAt(index);
		Iterator<Row> rows = currentSheet.iterator();
		return rows;
	}

	/**
	 * Método publico que retona uma lista de células e seus valores da planilha
	 * Excel que foi carregada na memória.
	 * 
	 * @return List<Cell> - Lista de todas as células da planilha Excel.
	 */
	public List<Cell> getCells() {
		setup();
		Iterator<Row> rows = getRows();
		List<Cell> cells = new ArrayList<Cell>();

		while (rows.hasNext()) {
			Row currentRow = rows.next();
			Iterator<Cell> cellIterator = currentRow.cellIterator();

			while (cellIterator.hasNext()) {
				cells.add(cellIterator.next());
			}
		}
		exit();

		return cells;
	}

	// ******************************
	// Demonstração - Padrão
	// ******************************
	public static void main(String[] args) {

		// Instancia do Objeto Manager
		ExcelManager em = new ExcelManager(1);

		// Obtem todas as Células
		List<Cell> cells = em.getCells();

		// Realiza Iteração para printar os valores ou passar como paramentro para um
		// objeto do tipo ExcelObject
		for (Cell cell : cells) {

			System.out.printf("LINHA ATUAL: %s", cell.getRowIndex());
			switch (cell.getCellType()) {
			case STRING:
				System.out.printf(" - CAMPO: %s", cell.getStringCellValue());
				break;

			case BOOLEAN:
				System.out.printf(" - CAMPO: %s", cell.getBooleanCellValue());
				break;

			case NUMERIC:
				System.out.printf(" - CAMPO: %s", cell.getNumericCellValue());
				break;

			default:
				break;
			}
			System.out.println();
		}
	}
}
