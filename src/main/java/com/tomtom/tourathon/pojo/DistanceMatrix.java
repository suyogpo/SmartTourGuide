package com.tomtom.tourathon.pojo;

import java.util.List;
import lombok.*;


@Data
public class DistanceMatrix {
	List<Location> destination_addresses;
	List<Location> origin_addresses;
	Element[][] elements = null;
	public List<Location> getDestination_addresses() {
		return destination_addresses;
	}
	public void setDestination_addresses(List<Location> destination_addresses) {
		this.destination_addresses = destination_addresses;
	}
	public List<Location> getOrigin_addresses() {
		return origin_addresses;
	}
	public void setOrigin_addresses(List<Location> origin_addresses) {
		this.origin_addresses = origin_addresses;
	}
	public Element[][] getElements() {
		return elements;
	}
	public void setElements(Element[][] elements) {
		this.elements = elements;
	}
}
