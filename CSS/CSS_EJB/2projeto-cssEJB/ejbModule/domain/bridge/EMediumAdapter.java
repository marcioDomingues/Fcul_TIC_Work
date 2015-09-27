package domain.bridge;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.lendables.Lendable;
import model.rentals.Rental;
import model.shelves.EMedium;
import domain.EMediumDTO;
import domain.EMediumRemote;

public class EMediumAdapter {
		
		public Lendable findLendableFromDTO(EMediumRemote dto, EntityManager em) {
			// find lendable by ID
			TypedQuery<Lendable> query = em.createNamedQuery(Lendable.FIND_BY_ID,
					Lendable.class);
			query.setParameter(Lendable.FIND_ID, dto.getId());
			
			List<Lendable> lendable = query.getResultList();
			
			return  lendable.isEmpty() ? null : lendable.get(0);
		}
		public Rental findRentalFromDTO(EMediumRemote dto, EntityManager em) {
			Lendable lendable = findLendableFromDTO(dto, em);
			
			// find rental by lendable ID
			TypedQuery<Rental> query = em.createNamedQuery(Rental.FIND_BY_LENDABLE,
					Rental.class);
			query.setParameter(Rental.FIND_LENDABLE, lendable);
			
			List<Rental> rental = query.getResultList();
			
			return rental.isEmpty() ? null : rental.get(0);
		}


		public List<EMediumRemote> eMediumToDTOList(Iterable<? extends EMedium> items) {
			List<EMediumRemote> dtos = new ArrayList<>();

			for(EMedium item : items) {
				EMediumDTO dto = new EMediumDTO((EMediumRemote)item);
				dto.setId(item.getLendable().getId());
				dtos.add(dto);
			}

			return dtos; 
		}
}
