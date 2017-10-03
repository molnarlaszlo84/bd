package hu.unideb.inf.bd.model;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlValue;

@javax.xml.bind.annotation.XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(
	propOrder = {
		"title",
		"authors",
		"publisher",
		"date",
		"description",
		"format",
		"pages",
		"dimensions",
		"language",
		"edition",
		"isbn10",
		"isbn13",
		"salePrice",
		"listPrice"
	}
)
public class Book {

	@XmlAttribute(required = true)
	private String uri;

	@XmlElement(name = "author", required = true)
	private ArrayList<Author> authors = new ArrayList<Author>();

	@XmlElement(required = true)
	private String title;

	@XmlElement(required = true)
	private	String publisher;

	@XmlElement(name = "published", required = false)
	@XmlSchemaType(name = "date")
	@javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate date;

	@XmlElement(required = false)
	private String description;

	@XmlElement(required = true)
	private String format;

	@XmlElement(required = false)
	@XmlSchemaType(name = "nonNegativeInteger")
	private Integer pages;

	@XmlElement(required = false)
	private Dimensions dimensions;

	@XmlElement(required = false)
	private String language;

	@XmlElement(required = false)
	private String edition;

	@XmlElement(required = true)
	private String isbn10;

	@XmlElement(required = true)
	private String isbn13;

	@XmlElement(required = false)
	private Price salePrice;

	@XmlElement(required = true)
	private Price listPrice;

	public Book() {
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}

	public Book addAuthor(Author author) {
		authors.add(author);
		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Dimensions getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}

	public String getIsbn10() {
		return isbn10;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public Price getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Price salePrice) {
		this.salePrice = salePrice;
	}

	public Price getListPrice() {
		return listPrice;
	}

	public void setListPrice(Price listPrice) {
		this.listPrice = listPrice;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@javax.xml.bind.annotation.XmlType(namespace="http://www.inf.unideb.hu/BookDepository")
	public static class Author {

		@XmlValue
		private String name;

		@XmlAttribute(name = "role", required = true)
		private String role;

		public Author() {
		}

		public Author(String name, String role) {
			this.name = name;
			this.role = role;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String toString() {
			return ReflectionToStringBuilder.toString(this);
		}

	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@javax.xml.bind.annotation.XmlType(namespace="http://www.inf.unideb.hu/BookDepository")
	public static class Dimensions {

		@XmlAttribute(required = true)
		private BigDecimal width;

		@XmlAttribute(required = true)
		private BigDecimal height;

		@XmlAttribute(required = true)
		private BigDecimal thickness;

		@XmlAttribute(required = true)
		private BigDecimal weight;

		public Dimensions() {}

		public Dimensions(String s) throws ParseException {
			Pattern pattern = Pattern.compile("\\s*([\\d,.]+)\\s*x\\s*([\\d,.]+)\\s*x\\s*([\\d,.]+)mm\\s*\\|\\s*([\\d,.]+)g");
			Matcher matcher = pattern.matcher(s);
			if (! matcher.matches()) throw new ParseException(s, 0);
			DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(java.util.Locale.ENGLISH);
			df.setParseBigDecimal(true);
			width = (BigDecimal) df.parse(matcher.group(1));
			height = (BigDecimal) df.parse(matcher.group(2));
			thickness = (BigDecimal) df.parse(matcher.group(3));
			weight = (BigDecimal) df.parse(matcher.group(4));
		}

		public Dimensions(BigDecimal width, BigDecimal height, BigDecimal thickness, BigDecimal weight) {
			this.width = width;
			this.height = height;
			this.thickness = thickness;
			this.weight = weight;
		}

		public BigDecimal getWidth() {
			return width;
		}

		public void setWidth(BigDecimal width) {
			this.width = width;
		}

		public BigDecimal getHeight() {
			return height;
		}

		public void setHeight(BigDecimal height) {
			this.height = height;
		}

		public BigDecimal getThickness() {
			return thickness;
		}

		public void setThickness(BigDecimal thickness) {
			this.thickness = thickness;
		}

		public BigDecimal getWeight() {
			return weight;
		}

		public void setWeight(BigDecimal weight) {
			this.weight = weight;
		}

		public String toString() {
			return String.format("%.2f x %.2f x %.2fmm | %.2fg", width, height, thickness, weight);
		}

	}

	public static void main(String[] args) throws Exception {
		Book book = new Book();
		book.setUri("http://www.bookdepository.co.uk/Secret-Museum-Molly-Oldfield/9780007455287");
		book.addAuthor(new Author("Molly Oldfield", "By (author)"));
		book.setTitle("The Secret Museum");
		book.setPublisher("Collins");
		book.setDate(LocalDate.of(2013, 3, 1));
		book.setDescription("The Secret Museum is a unique treasure trove of the most intriguing artifacts hidden away in museum archives from all over the world - curated, brought to light, and brought to life by Molly Oldfield in a beautifully illustrated collection.");
		book.setFormat("Hardback");
		book.setPages(352);
		book.setDimensions(new Dimensions("194 x 246 x 30mm | 1,239.98g"));
		book.setLanguage("English");
		book.setIsbn10("0007455283");
		book.setIsbn13("9780007455287");
		book.setSalePrice(new Price(new BigDecimal("24.85"), "EUR"));
		book.setListPrice(new Price(new BigDecimal("27.69"), "EUR"));
		try {
			hu.unideb.inf.jaxb.JAXBUtil.toXML(book, System.out);
		} catch(javax.xml.bind.JAXBException e) {
			e.printStackTrace();
		}
	}

}
