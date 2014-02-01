package codingdojo.rest;

import java.util.ArrayList;
import java.util.List;


public class SavingExpedientsRepository {


	
	public void saveOrUpdate(SavingExpedient expedient){
	}
	
	public SavingExpedient findBy(String reference){
        return new SavingExpedient();
	}

	public List<SavingExpedient> allExpedients(){
        return new ArrayList<SavingExpedient>();
    }
}