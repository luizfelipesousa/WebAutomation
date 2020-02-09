package automation.data.hibernate;

/*
 * @author Luiz Felipe Alves de Sousa
 * @version 1.00
 * @since 09/01/2020
 * 
 * Classe Responsável por Gerenciar arquivos de planilhas Excel
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import automation.core.utils.Utils;
import automation.logging.log4j.Log4JSetup;
import automation.data.hibernate.DBObject;

public class HibernateUtil {

	private Logger log = Log4JSetup.getLogger(HibernateUtil.class);
	protected SessionFactory sf;

	/**
	 * Responsável por criar uma sessão de conexão ao banco de dados.
	 */
	public void setup() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	public void exit() {
		sf.close();
	}

	// ******************************
	// CRUD
	// ******************************

	/**
	 * Método responsável por adicionar objetos no banco de dados. s
	 */
	public void create(DBObject object) {
		Session session = sf.openSession();
		session.beginTransaction();

		session.save(object);

		session.getTransaction().commit();
		session.close();
		log.info("Cadastro no banco de dados efetuado com sucesso.");
	}

	/**
	 * Método responsável por ler todos os dados da tabela no banco de dados.
	 * 
	 * @param Class - Classe responsável por definer o padrão de objetos na Lista de
	 *              retorno.
	 * @return List - Lista de objetos da classe informada no paramento do método.
	 */
	public <T> List<T> readAll(Class<T> type) {
		Session session = sf.openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		criteria.from(type);
		List<T> data = session.createQuery(criteria).getResultList();

		session.close();

		log.info("Consulta no banco de dados efetuada com sucesso. Retorno em forma de Lista");
		return data;
	}

	/**
	 * Método responsável por realizar a consulta de um objeto no banco de dados.
	 * 
	 * @param DBObject - Objeto que deseja verificar se está no banco de dados.
	 *                 retorno.
	 * @return DBObject - Objeto alvo da consulta.
	 */
	public DBObject read(DBObject object) {
		Session session = sf.openSession();

		DBObject dbObject = session.get(object.getClass(), object.getId());

		log.info("Consulta no banco de dados efetuada com sucesso.[ " + object.toString() + "]");

		session.close();

		return dbObject;
	}

	/**
	 * Método responsável por realizar o update de um objeto no banco de dados. Não
	 * deve ser usado durante automação.
	 * 
	 * @param DBObject - Objeto que deseja atualizar no banco de dados.
	 */
	@SuppressWarnings("unused")
	private void update(DBObject object) {
		Session session = sf.openSession();
		session.beginTransaction();

		session.update(object);

		session.getTransaction().commit();
		session.close();

		log.info("Atualização no banco de dados efetuada com sucesso.");
	}

	/**
	 * Método responsável por realizar o delete de um objeto no banco de dados. Não
	 * deve ser usado durante automação.
	 * 
	 * @param DBObject - Objeto que deseja deletar no banco de dados.
	 */
	@SuppressWarnings("unused")
	private void delete(DBObject object) {
		Session session = sf.openSession();
		session.beginTransaction();

		session.delete(object);

		session.getTransaction().commit();
		session.close();

		log.info("Remoção do banco de dados efetuada com sucesso.");
	}

	/**
	 * Método responsável por executar queries escritas num arquivo txt padrão.
	 */
	@SuppressWarnings("unused")
	private void executeQueriesFromText() {
		Session session = sf.openSession();
		session.beginTransaction();

		BufferedReader Queries = Utils.getDataBaseQueriesFromTextFile();
		String queryString;

		try {
			while ((queryString = Queries.readLine()) != null) {
				Query<?> createQuery = session.createQuery(queryString);
				createQuery.executeUpdate();
			}
		} catch (IOException e) {
			log.error("Falha ao ler arquivo de texto com as queries do banco de dados.");
			e.printStackTrace();
		}

		session.getTransaction().commit();
		session.close();
		log.info("Execução de queries no banco de dados efetuada com sucesso.");
	}
}
