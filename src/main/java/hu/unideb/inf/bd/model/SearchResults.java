package hu.unideb.inf.bd.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResults {

	@XmlAttribute(required = true)
	private int itemsTotal;

	@XmlAttribute(required = true)
	private int from;

	@XmlAttribute(required = true)
	private int to;

	@XmlElement(name = "item")
	private List<SearchResultItem> items;

	public SearchResults() {
	}

	public SearchResults(int itemsTotal, int from, int to, List<SearchResultItem> items) {
		this.itemsTotal = itemsTotal;
		this.from = from;
		this.to = to;
		this.items = items;
	}

	public int getItemsTotal() {
		return itemsTotal;
	}

	public void setItemsTotal(int itemsTotal) {
		this.itemsTotal = itemsTotal;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public List<SearchResultItem> getItems() {
		return items;
	}

	public void setItems(List<SearchResultItem> items) {
		this.items = items;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public static void main(String[] args) {
		SearchResultItem item = new SearchResultItem();
		item.setUri("http://www.bookdepository.com/Wonderbook-Jeff-Vandermeer/9781419704420");
		item.setAuthor("Jeff Vandermeer");
		item.setTitle("Wonderbook");
		item.setDate(java.time.LocalDate.of(2013, 10, 15));
		item.setFormat("Paperback");

		java.util.ArrayList<SearchResultItem> items = new java.util.ArrayList<SearchResultItem>();
		items.add(item);
		items.add(item);

		SearchResults	results = new SearchResults(2, 1, 2, items);
		System.out.println(results);
		try {
			hu.unideb.inf.jaxb.JAXBUtil.toXML(results, System.out);
		} catch(javax.xml.bind.JAXBException e) {
			e.printStackTrace();
		}
	}

}
