package hu.unideb.inf.bd.parser;

import java.io.File;
import java.io.IOException;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.jaxb.JAXBUtil;
import hu.unideb.inf.bd.model.Book;
import hu.unideb.inf.bd.model.Price;

public class BookParser {

	private static Logger logger = LoggerFactory.getLogger(BookParser.class);

	public BookParser() {
	}

	public Book parse(String url) throws IOException {
		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
		Book book = parse(doc);
		book.setUri(url);
		return book;
	}

	public Book parse(File file) throws IOException {
		Document doc = Jsoup.parse(file, null);
		Book book = parse(doc);
		book.setUri(file.toURI().toString());
		return book;
	}

	public Book parse(Document doc) throws IOException {
		Book book = new Book();
		ArrayList<Book.Author>	authors = new ArrayList<Book.Author>();
		try {
			for (Element e : doc.select("div.item-info > div.author-info > a[itemprop=author]")) {
				Book.Author author = new Book.Author();
				author.setName(e.text().trim());
				String role = "By (author)";
				if (e.previousSibling() instanceof TextNode) {
					role = ((TextNode) e.previousSibling()).text().trim();
					role = role.replaceFirst("^,\\s*", "");
				}
				author.setRole(role);
				authors.add(author);
			}
		} catch(Exception e) {
			throw new IOException("Malformed document");
		}
		book.setAuthors(authors);

		String title = null;
		try {
			title = doc.select("div.item-info > h1[itemprop=name]").first().text().trim();
			logger.info("Title: {}", title);
		} catch(Exception e) {
			throw new IOException("Malformed document");
		}
		book.setTitle(title);

		String publisher = null;
		try {
			publisher = doc.select("ul.biblio-info > li > span >a[itemprop=publisher]").first().text().trim();
			logger.info("Publisher: {}", publisher);
		} catch(Exception e) {
			throw new IOException("Malformed document");
		}
		book.setPublisher(publisher);

		LocalDate date = null;
		try {
			// TODO (Bódis Tünde)
			logger.info("Date: {}", date);
		} catch(Exception e) {
		}
		book.setDate(date);

		String description = null;
		try {
			description = doc.select("div.item-description > div[itemprop=description]").first().childNode(0).toString().trim();
			logger.info("Description: {}", description);
		} catch(Exception e) {
		}
		book.setDescription(description);

		String format = null;
		Integer	pages = null;
		try {
			// TODO (Molnár László)
			logger.info("Format: {}", format);
			logger.info("Pages: {}", pages);
		} catch(Exception e) {
			throw new IOException("Malformed document");
		}
		book.setFormat(format);
		book.setPages(pages);

		Book.Dimensions	dimensions = new Book.Dimensions();
		try {
			// TODO (Váradi Sándor)
			logger.info("Dimensions: {}", dimensions);
			book.setDimensions(dimensions);
		} catch(Exception e) {
			throw new IOException("Malformed document");
		}

		String language = null;
		try {
			// TODO (Szabó Dávid)
			logger.info("Language: {}", language);
		} catch(Exception e) {
		}
		book.setLanguage(language);

		String edition = null;
		try {
			// TODO (Barta Ferenc)
			logger.info("Edition: {}", edition);
		} catch(Exception e) {
		}
		book.setEdition(edition);

		String isbn10 = null;
		try {
			// TODO (Orbán István)
			for(Element e : doc.select("ul.biblio-info > li")) {
				Optional<Element> feltetelElement = e.getElementsByTag("label")
						.stream().filter(it -> "ISBN10".equals(it.text())).findFirst();
				if (feltetelElement.isPresent()) {
					isbn10 = feltetelElement.get().nextElementSibling().text().trim();
				}
			}

			logger.info("ISBN10: {}", isbn10);
		} catch(Exception e) {
			throw new IOException("Malformed document");
		}
		book.setIsbn10(isbn10);

		String isbn13 = null;
		try {
			// TODO (Burai Péter)
			logger.info("ISBN13: {}", isbn13);
		} catch(Exception e) {
			throw new IOException("Malformed document");
		}
		book.setIsbn13(isbn13);

		String currency = null;
		logger.info("Currency: [{}]", currency);

		Price salePrice = null;
		Price listPrice = null;
		try {
			// TODO (Benőcs Péter, Gecző Gergő)  
				Element eSalePrice = doc.select(" div.price > span[class=sale-price]").first(); 
				Element eListPrice = doc.select(" div.price > span[class=list-price]").first();
				String selectedCurrency = doc.select("div.currency-selector").first().attributes().get("title"); 
				String sP = eSalePrice.text().trim();
				String lP = eListPrice.text().trim(); 
				sP = sP.replaceAll(" ", "").replaceAll("[^\\d.,]", "").trim();
				lP = lP.replaceAll(" ", "").replaceAll("[^\\d.,]", "").trim();
				salePrice = new Price(new BigDecimal(sP),selectedCurrency);	 
				listPrice = new Price(new BigDecimal(lP),selectedCurrency); 
				
				logger.info("Sale price: {}", salePrice);
				logger.info("List price: {}", listPrice);
			} catch(Exception e) {
				throw new IOException("Malformed document");
			}
		book.setSalePrice(salePrice);
		book.setListPrice(listPrice);
		return book;
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.printf("Usage: java %s <url>\n", BookParser.class.getName());
			System.exit(1);
		}
		try {
			Book book = new BookParser().parse(args[0]);
			System.out.println(book);
			JAXBUtil.toXML(book, System.out);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
