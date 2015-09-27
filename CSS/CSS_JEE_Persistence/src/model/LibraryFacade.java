package model;

import java.util.LinkedList;

import model.lendables.Lendable;
import model.lendables.Library;

public class LibraryFacade {
	
		private Library library;
		
		public LibraryFacade (Library library) {
			this.library = library;
		}
		
		public Iterable<EMedium> getEMedia () {
			LinkedList<EMedium> emedia = new LinkedList<EMedium> ();
			for (Lendable r : library)
				emedia.add(r);
			return emedia; 
		}

		public boolean addLendable(EMediumType type, EMediumPropertiesData lendableProperties) {
			return library.addLendable(type, lendableProperties);
		}
		public void returnLendable(EMedium eMedium) {
			library.returnLendable(eMedium);
		}
		public EMedium getLastAddedLendable() {
			return library.getLastAddedLendable();
		}

		public boolean canBeRent(EMedium eMedium) {
			return library.canBeRent(eMedium);
		}
		
		// pre: canBeRent(eMedium)
		public void rent(EMedium eMedium) {
			library.rent(eMedium);
		}
}
