package greenhouse.c;
public class NutrientService {
 private final NutrientRule rule;private final NutrientsRepository repo;
 public NutrientService(NutrientRule rule,NutrientsRepository repo){this.rule=rule;this.repo=repo;}
 public void execute(String id,int input){if(!rule.allowed(input))throw new IllegalArgumentException("c validation failed");Nutrients e=new Nutrients(id);e.dispense();repo.save(e);}
 public void undo(String id){Nutrients e=repo.find(id);if(e!=null&&e.status()==Nutrients.Status.DISPENSED){e.cancel();repo.save(e);}}
 public Nutrients find(String id){return repo.find(id);}
}
