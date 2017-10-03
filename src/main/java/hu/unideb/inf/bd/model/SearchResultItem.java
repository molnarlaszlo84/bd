package hu.unideb.inf.bd.model;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	propOrder = {
		"author",
		"title",
		"date",
		"format"
	}
)
public class SearchResultItem {

	@XmlAttribute(required = true)
	private String uri;

	@XmlElement(required = false)
	private String author;

	@XmlElement(required = true)
	private String title;

	@XmlElement(name = "published", required = false)
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate date;

	@XmlElement(required = true)
	private String format;

	public SearchResultItem() {
	}

	public SearchResultItem(String uri, String author, String title, LocalDate date, String format) {
		this.uri = uri;
		this.author = author;
		this.title = title;
		this.date = date;
		this.format = format;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public static void main(String[] args) {
		SearchResultItem item = new SearchResultItem();
		item.setUri("http://www.bookdepository.com/Wonderbook-Jeff-Vandermeer/9781419704420");
		item.setAuthor("Jeff Vandermeer");
		item.setTitle("Wonderbook");
		item.setDate(LocalDate.of(2013, 10, 15));
		item.setFormat("Paperback");
		System.out.println(item);
		try {
			hu.unideb.inf.jaxb.JAXBUtil.toXML(item, System.out);
		} catch(javax.xml.bind.JAXBException e) {
			e.printStackTrace();
		}
	}

}
