package domain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import model.EMediumProperties;
import model.lendables.LibraryInterface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Stateless
@WebService
public class LibraryRemoteWS {
	
	@EJB 
	private LibraryInterface library;
	
	private Gson jsonDwarf;
	
	public LibraryRemoteWS() {
		jsonDwarf = new Gson();
	}
	
	@WebMethod
	public String getEMedia() {		
		List<EMediumProperties> items = library.getLendables().stream().map(x -> x.getEMediumProperties())
				.collect(Collectors.toList());
		
		Type listype = new TypeToken<ArrayList<EMediumProperties>>(){}.getType();
		
		return jsonDwarf.toJson(items, listype);
	}
	
}