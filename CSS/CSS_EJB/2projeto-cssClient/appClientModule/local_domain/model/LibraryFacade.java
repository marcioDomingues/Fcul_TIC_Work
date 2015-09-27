package local_domain.model;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import libraryWS.LibraryRemoteWS;
import libraryWS.LibraryRemoteWSService;
import local_domain.model.lendables.Lendable;
import local_domain.model.lendables.Library;

public class LibraryFacade {
	
		private Library library;
		
		//webservices remote classes
		LibraryRemoteWS libraryWS = new LibraryRemoteWSService().getLibraryRemoteWSPort();
		
		private Gson jsonDwarf = new Gson();


		
		
		public LibraryFacade (Library library) {
			this.library = library;
		}
		
		public Iterable<EMedium> getEMedia () {
		
			String jsonLendables = libraryWS.getEMedia();
					
			Type listype = new TypeToken<ArrayList<EMediumPropertiesData>>(){}.getType();
			Iterable<EMediumPropertiesData> properties = jsonDwarf.fromJson(jsonLendables, listype);
			List<EMedium> items = new ArrayList<>();
			for ( EMediumPropertiesData p : properties )
				items.add(new Lendable(p));
			
			System.out.println("ITEMS SAO: " + items.toString());
			return items;
			
			
		}

		public boolean addLendable(EMediumType type, EMediumPropertiesData lendableProperties) {
			return library.addLendable(type, lendableProperties);
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
