package turneros.taquillas;

import java.util.EventObject;

import turneros.entidades.Taquilla;

public class TaquillasEvent extends EventObject {
	public TaquillasEvent(Taquilla source) {
		super(source);
	}
}