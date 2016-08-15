package turneros.taquillas;

import java.util.EventListener;

public interface TaquillasEventListener extends EventListener {
	public void taquillaCambiada(TaquillasEvent evt);
}